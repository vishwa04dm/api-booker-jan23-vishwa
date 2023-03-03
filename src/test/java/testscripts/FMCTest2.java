package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService;
import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import services.GenerateTokenService;
import utilities.Datagenerator;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class FMCTest2 {
	BaseService baseService = new BaseService();
	String token, otp;
	Response res;
	String emailId = Datagenerator.getEmailId();
	String fullname = Datagenerator.getFullname();
	String phoneNumber = Datagenerator.getPhoneNumber();
	String password = "pass@123";
	GenerateTokenService generateTokenService = new GenerateTokenService();
	@Test(priority = 1)
	public void createToken() {

		
		token = generateTokenService.getToken();
		res = generateTokenService.getTokenResponse();
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		Assert.assertTrue(token.length()>0);
		Assert.assertEquals(res.jsonPath().get("tokenType"),"bearer");

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void emailSignup() {
		JSONObject emailSignupPayload = new JSONObject();
		emailSignupPayload.put("email_id", emailId);
		res=generateTokenService.getEmailSignupResponse(emailSignupPayload);

		System.out.println(res.asPrettyString());
		otp = res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.getStatusCode(),Status_Code.CREATED);
		
	}

	@Test(priority = 3)
	@SuppressWarnings("unchecked")
	public void verifyOtp() {
		if(otp == null) {
			JSONObject emailSignUpPayLoad = new JSONObject();
			emailSignUpPayLoad.put("email_id", emailId);
			otp = generateTokenService.getOtpFromEmailSignupResponse(emailSignUpPayLoad);
		}
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id",emailId);
		verifyOtpPayload.put("full_name", fullname);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		res = generateTokenService.getVerifyOtpResponse(verifyOtpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		int userId = res.jsonPath().getInt("content.userId");
		System.out.println(userId);
		
		
		//userId = generateTokenService.getUserId(emailId, "pass123");
		//System.out.println(userId);
	}

}
