package testscripts;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;
	@BeforeMethod
	public void generateToken() {
		RestAssured.baseURI="https://restful-booker.herokuapp.com";
		Response res=RestAssured.given().log().all().headers("Content-Type","application/json").body("{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}").when().post("/auth").then().assertThat().statusCode(200).extract().response();
		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.statusCode(), 200);
		// System.out.println(res.asPrettyString());
		token= res.jsonPath().getString("token"); 
		 System.out.println("token is : "+token);
	}
	@Test(enabled=false)
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
	@Test
	public void createBookingTestWithPOJO() {
		Bookingdates bookingdates=new Bookingdates();
		bookingdates.setCheckin("2023-02-01");
		bookingdates.setCheckout("2023-03-01");
		payload=new CreateBookingRequest();
		payload.setFirstname("Vishwa21");
		payload.setLastname("ArdeshanaA21");
		payload.setDepositpaid(true);
		payload.setTotalprice(2000);
		payload.setAdditionalneeds("breakfast");
		payload.setBookingdates(bookingdates);
		
		Response res=RestAssured.given(
				).header("Content-Type","application/json")
				.headers("Accept","application/json")
				.body(payload)
				.when()
				.post("/booking");
		bookingId=res.jsonPath().getInt("bookingid");
		Assert.assertEquals(res.statusCode(),Status_Code.OK); //static var OK=200 in status_code
		
		Assert.assertTrue(bookingId>0);
		//Assert.assertTrue(Integer.valueOf(res.jsonPath().getInt("bookingId")) instanceof Integer);
		
		
		System.out.println(res.prettyPrint());
		
		validateResponse(res, payload,"booking.");
		
	}
	private void validateResponse(Response res,CreateBookingRequest payload,String object) {
		 Assert.assertEquals(res.jsonPath().getString(object+"firstname"),payload.getFirstname());
		 Assert.assertEquals(res.jsonPath().getString(object+"lastname"),payload.getLastname());
		 Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"),payload.getTotalprice());
		 Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"),payload.getBookingdates().getCheckin());
		 Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"),payload.getBookingdates().getCheckout());
		 Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"),payload.getAdditionalneeds());
		 Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"),payload.isDepositpaid());
	}
	@Test(priority=1,enabled=false)
	public void getAllBookingTest() {
		int bookingId=6886; 
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/booking");
		 Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		//System.out.println(res.asPrettyString());
		List<Integer> listOfBookingIds=res.jsonPath().getList("bookingid");
		//System.out.println(listOfBookingIds.size());
		//System.out.println(res.asPrettyString());
		Assert.assertTrue(listOfBookingIds.contains(bookingId));
		
	}
	@Test(priority=2,enabled=false)
	public void getBookingIdTest() {
		//int bookingId=6886; //any random number
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/booking/"+bookingId);
		 Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		//System.out.println(res.asPrettyString());
		 validateResponse(res,payload,"");
		
	}
	@Test(priority=2,enabled=false)
	public void getBookingIdDeserializedTest() {
		//bookingId=2291; //any random number
		Response res=RestAssured.given()
				.headers("Accept","application/json")
				.when()
				.get("/booking/"+bookingId);
		 Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		//System.out.println(res.asPrettyString());
		 CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);
		System.out.println(responseBody);
		Assert.assertTrue(responseBody.equals(payload));
	}
	@Test(priority=3)
	public void updateBookingIdTest() {
		payload.setFirstname("vishwa2");
		//bookingId=2291; //any random number
		Response res=RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.header("Cookie","token="+token)
				.log().all().body(payload)			
				.when()
				.put("/booking/"+bookingId);
		 Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		//System.out.println(res.asPrettyString());
		 CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);
		Assert.assertTrue(responseBody.equals(payload));
	}
	@Test(priority=4)
	public void updatePrtialBookingTest() {
		payload.setFirstname("vishwa-partial update");
		payload.setLastname("ardeshana-partial update");
		Response res=RestAssured.given()
				.headers("Content-Type","application/json")
				.headers("Accept","application/json")
				.headers("Cookie","token="+token)
				.body(payload)
				.when()
				.patch("/booking/"+bookingId);
		Assert.assertEquals(res.statusCode(),Status_Code.OK);
		CreateBookingRequest responseBody=res.as(CreateBookingRequest.class);
		Assert.assertTrue(responseBody.equals(payload));
	}
	@Test(priority=5)
	public void deleteBookingTest() {
		Response res=RestAssured.given()
				.headers("Cookie","token="+token)
				.when()
				.delete("/booking/"+bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		verifyBookingIsDeleted();
	}
	public void verifyBookingIsDeleted() {
		Response res = RestAssured.given().headers("Accept", "application/json").when().get("/booking");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		List<Integer> listOfBookingId = res.jsonPath().getList("bookingid");
		Assert.assertFalse(listOfBookingId.contains(bookingId));
	}
	@Test(enabled=false)
	
	public void createBookinTestInPlanMode() {
		//RestAssured.baseURI="https://restful-booker.herokuapp.com";
		String payload="{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}";
		RequestSpecification reqSpec=RestAssured.given();
		reqSpec.baseUri("https://restful-booker.herokuapp.com");
		reqSpec.headers("Content-Type","application/json");
		reqSpec.body(payload);
		Response res=reqSpec.post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		 System.out.println(res.asPrettyString());
		
		
	}

}
