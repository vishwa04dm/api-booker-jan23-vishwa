package testscripts;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.response.Response;
import services.LoginService;

public class FMCLoginTest {
	LoginService loginService=new LoginService();
	@Test(dataProvider = "loginDataDetails")
	public void loginAPITest(String emailId,String password,String result) {
		
		Response res=loginService.login(emailId,password);
		if(result.equals("success"))
			Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		else {
			Assert.assertEquals(res.getStatusCode(),Status_Code.BADREQUEST);
			//Assert.assertEquals(res.jsonPath().getString("errors[0].message"),"Password is required.");
		}
	}
	@DataProvider(name="loginDataDetails")
	public String[][] getLoginData(){
		String[][] data=new String[2][3];
		
		data[0][0] ="vxncccdm@gmail.com";
		data[0][1]="";
		data[0][2]="fail";

		data[1][0] ="vxmncccv@gmail.com";
		data[1][1]="";
		data[1][2]="fail";
		return data;
	}

}
