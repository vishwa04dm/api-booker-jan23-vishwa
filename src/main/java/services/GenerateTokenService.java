package services;

import java.util.Map;

import org.json.simple.JSONObject;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;
import utilities.Datagenerator;

public class GenerateTokenService extends BaseService{
	String emailId=Datagenerator.getEmailId();
	
	public Response getTokenResponse() {
		Map<String,String> headerMap=getHeaderWithoutAuth();
		Response res=executeGetAPI(APIEndPoints.TOKEN,headerMap);
		return res;
	}
	public String getToken() {
		Response res=getTokenResponse();
		return res.jsonPath().getString("accessToken");
	}
	public Response getEmailSignupResponse(JSONObject emailSignupPayload) {
		Map<String, String> headerMap = getHeaderHavignAuth(getToken());
		return executePostAPI(APIEndPoints.EMAIL_SIGNUP, headerMap,emailSignupPayload);
	}
	@SuppressWarnings("unchecked")
	public Response getEmailSignupResponse() {
		JSONObject emailSignupPayload = new JSONObject();
		emailSignupPayload.put("email_id", emailId);
		Map<String, String> headerMap = getHeaderHavignAuth(getToken());
		return executePostAPI(APIEndPoints.EMAIL_SIGNUP, headerMap,emailSignupPayload);
	}
	public String getOtpFromEmailSignupResponse(JSONObject emailSignupPayload) {
		Response res=getEmailSignupResponse(emailSignupPayload);
		String otp=res.jsonPath().getString("content.otp");
		return otp;
	}
	public Response getVerifyOtpResponse(JSONObject verifyOtpPayload) {
		Map<String, String> headerMap = getHeaderHavignAuth(getToken());
		return executePutAPI(APIEndPoints.VERIFY_OTP, headerMap, verifyOtpPayload);
		
	}
	public int getUserIdFromVerifyOtpResponse(JSONObject verifyOtpPayload) {
		Map<String, String> headerMap = getHeaderHavignAuth(getToken());
		Response res = executePutAPI(APIEndPoints.VERIFY_OTP, headerMap, verifyOtpPayload);
		return res.jsonPath().getInt("content.userId");
	}
	@SuppressWarnings("unchecked")
	public int getUserId(String emailId,String password) {
		JSONObject emailSignupPayload = new JSONObject();
		emailSignupPayload.put("email_id", emailId);
		String otp=getOtpFromEmailSignupResponse(emailSignupPayload);
		
		String fullname=Datagenerator.getFullname();
		String phoneNumber=Datagenerator.getPhoneNumber();
		
		JSONObject verifyOtpPayload = new JSONObject();
		verifyOtpPayload.put("email_id", emailId);
		verifyOtpPayload.put("full_name", fullname);
		verifyOtpPayload.put("phone_number", phoneNumber);
		verifyOtpPayload.put("password", password);
		verifyOtpPayload.put("otp", otp);
		
		return getUserIdFromVerifyOtpResponse(verifyOtpPayload);
		
	}

}
