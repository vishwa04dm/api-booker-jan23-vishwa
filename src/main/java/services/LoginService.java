package services;

import java.util.Map;

import org.json.simple.JSONObject;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;

public class LoginService extends BaseService {
	
	GenerateTokenService generateTokenService=new GenerateTokenService();
	@SuppressWarnings("unchecked")
	public Response login(String emailId,String password) {
		JSONObject loginPayload=new JSONObject();
		loginPayload.put("emailId",emailId);
		loginPayload.put("password",password);
		return login(loginPayload);
	}
	public Response login(JSONObject payload) {
		String password=payload.get("password").toString();
		if(password=="" || password==null) {
			password="temp";
			
		}
		generateTokenService.getUserId(payload.get("emailId").toString(),password);
		Map<String,String> headerMap=getHeaderHavignAuth(generateTokenService.getToken());
		return executePostAPI(APIEndPoints.LOGIN, headerMap, payload);
	}
	//public String loginAndGetUserId(JSONObject loginpayload) {
		
		
	}


