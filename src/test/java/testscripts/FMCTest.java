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

public class FMCTest {
	BaseService baseService=new BaseService();
	String token,otp;
	Response res;
	String emailId=Datagenerator.getEmailId();
	String fullname=Datagenerator.getFullname();
	String phoneNumber=Datagenerator.getPhoneNumber();
	String password="pass@123";	
	
	@Test(priority=1)
	public void createToken() {
			//Map<String,String> headerMap=baseService.getHeaderWithoutAuth();
			//res=baseService.executeGetAPI("/fmc/token",headerMap);
			GenerateTokenService generateTokenService=new GenerateTokenService();
			token=generateTokenService.getToken();
		res=generateTokenService.getTokenResponse();
		System.out.println(res);
		
//		res=RestAssured.given()
//				.headers("Accept","application/json")
//				.when()
//				.get("/fmc/token");
//				token=res.jsonPath().get("accessToken");
//				
//				System.out.println(res.asPrettyString());
//				System.out.println("Token-->"+token);
	}
	@Test(priority=2)
	public void emailSignup() {
		JSONObject jObject=new JSONObject();
		jObject.put("email_id",emailId);
		Map<String,String> headerMap=baseService.getHeaderHavignAuth(token);
		res=baseService.executePostAPI("/fmc/email-signup-automation", headerMap,jObject);
		
		
//		res=given().headers("Content-Type","application/json")
//				.headers("Authorization","Bearer "+token)
//				.body(jObject)
//				.when().post("/fmc/email-signup-automation");
//		System.out.println(res.asPrettyString());
		otp= res.jsonPath().getString("content.otp");
		
	}
	@Test(priority=3)
	@SuppressWarnings("unchecked")
	public void verifyOtp() {
		Map<String,String> headerMap=baseService.getHeaderHavignAuth(token);
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id",emailId);
		verifyOtpPayload.put("full_name", fullname);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		res=baseService.executePutAPI("/fmc/verify-otp",headerMap, verifyOtpPayload);
//		res=given().headers("Content-Type","application/json")
//		.headers("Authorization","Bearer "+token)
//		.body(verifyOtpPayload).log().all()
//		.when()
//		.put("/fmc/verify-otp");
		System.out.println(res.asPrettyString());
		int userId=res.jsonPath().getInt("content.userId");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		
		
	}

}
