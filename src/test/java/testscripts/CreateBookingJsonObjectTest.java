package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import net.datafaker.Faker;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingJsonObjectTest {
	public void createBookingTest() {
		Response res=RestAssured.given().header("Content-Type","application/json").headers("Accept","application/json").body("{\r\n"
				+ "    \"firstname\" : \"Vishwa\",\r\n"
				+ "    \"lastname\" : \"Ardeshana\",\r\n"
				+ "    \"totalprice\" : 111,\r\n"
				+ "    \"depositpaid\" : true,\r\n"
				+ "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \"2023-02-01\",\r\n"
				+ "        \"checkout\" : \"2023-03-01\"\r\n"
				+ "    },\r\n"
				+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
				+ "}").when().post("/booking");
		Assert.assertEquals(res.statusCode(),200);
		Assert.assertEquals(res.statusCode(),Status_Code.OK); //static var OK=200 in status_code
		System.out.println(res.getStatusCode());//both same
		System.out.println(res.statusCode());
		System.out.println(res.getStatusLine());//will print 200 OK
		System.out.println(res.prettyPrint());
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createBookingTestWithPOJO() {
		Faker faker=new Faker();
		
		RestAssured.baseURI="https://restful-booker.herokuapp.com";
		JSONObject jsonBookingDate=new JSONObject();
		jsonBookingDate.put("checkin","2023-02-01");
		jsonBookingDate.put("checkout","2023-03-01");
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("firstname",faker.name().firstName());
		jsonObject.put("lastname",faker.name().lastName());
		jsonObject.put("totalprice",faker.number().digit());
		jsonObject.put("depositpaid",true);
		jsonObject.put("bookingdates",jsonBookingDate);
		jsonObject.put("additionalneeds","Breakfast");
		
		
		
		Response res=RestAssured.given(
				).header("Content-Type","application/json")
				.headers("Accept","application/json")
				.body(jsonObject)
				.log().all()
				.when()
				.post("/booking");
		int bookingId=res.jsonPath().getInt("bookingid");
		Assert.assertEquals(res.statusCode(),Status_Code.OK); //static var OK=200 in status_code
		
		Assert.assertTrue(bookingId>0);
		//Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingId")) instanceof Integer);
		
		
		System.out.println(res.prettyPrint());
		System.out.println(bookingId);
		
		
		
	}

}
