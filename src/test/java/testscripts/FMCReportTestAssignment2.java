package testscripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Status_Code;
import io.restassured.response.Response;
import pojo.request.createbooking.fmc.AddReport;
import services.LoginService;
import services.ReportService;
import utilities.Datagenerator;

public class FMCReportTestAssignment2 {
	String emailId = Datagenerator.getEmailId();
	String password = "vishwa1";
	Response res;
	int userId,reportId;
	 
	ReportService reportService = new ReportService();

	LoginService loginService=new LoginService();
	AddReport addReport;

	@BeforeClass
	public void login() {
		res = loginService.login(emailId, password);
		userId = res.jsonPath().getInt("content.userId");
	}
	@Test
	public void verifyAddReportAPI() {
		addReport=reportService.addReportPayload(userId);
		res = reportService.getAddReportResponse(userId, addReport);

		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertEquals(res.jsonPath().getString("status"),"Success");
		Assert.assertEquals(res.jsonPath().getString("message"),"Repost created successfully");
		reportId=res.jsonPath().getInt("content");
	}
	@Test(priority=1)
	public void verifgetReportAPI() {
		res=reportService.getGetReportResponse(userId);
		Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.report_id"),reportId);
		validateResponse(res,addReport);
	}
	@Test(priority=2)
	public void verifyDeleteRepostAPI() {
		res=reportService.getDeleteReportResponse(reportId, userId);
		Assert.assertEquals(res.getStatusCode(),Status_Code.OK);
	}
	private void validateResponse(Response res,AddReport reportPayload) {
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.report_id"), reportId);
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.request_id"),
				reportPayload.getReporter_details().getRequest_id());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.report_date"),
				reportPayload.getReporter_details().getReport_date());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.reporter_fullname"),
				reportPayload.getReporter_details().getReporter_fullname());
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.reporter_age"),
				reportPayload.getReporter_details().getReporter_age());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.primary_country_code"),
				reportPayload.getReporter_details().getPrimary_country_code());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.primary_contact_number"),
				reportPayload.getReporter_details().getPrimary_contact_number());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.secondary_country_code"),
				reportPayload.getReporter_details().getSecondary_country_code());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.secondary_contact_number"),
				reportPayload.getReporter_details().getSecondary_contact_number());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.reporter_gender"),
				reportPayload.getReporter_details().getReporter_gender());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.reporter_relation"),
				reportPayload.getReporter_details().getReporter_relation());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.parenting_type"),
				reportPayload.getReporter_details().getParenting_type());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.contact_address_type"),
				reportPayload.getReporter_details().getContact_address_type());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.contact_address_line_1"),
				reportPayload.getReporter_details().getContact_address_line_1());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.contact_address_line_2"),
				reportPayload.getReporter_details().getContact_address_line_2());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.pincode"),
				reportPayload.getReporter_details().getPincode());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.country"),
				reportPayload.getReporter_details().getCountry());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.communication_language"),
				reportPayload.getReporter_details().getCommunication_language());
		Assert.assertEquals(res.jsonPath().getString("content[0].reporter_details.status"),
				reportPayload.getReporter_details().getStatus());
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.child_id"),
				res.jsonPath().getInt("content[0].child_details.child_id"));
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.incident_id"),
				res.jsonPath().getInt("content[0].incident_details.incident_id"));
		Assert.assertEquals(res.jsonPath().getInt("content[0].reporter_details.user_id"), userId);
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.fullname"),
				reportPayload.getChild_details().getFullname());
		Assert.assertEquals(res.jsonPath().getInt("content[0].child_details.age"),
				reportPayload.getChild_details().getAge());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.gender"),
				reportPayload.getChild_details().getGender());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.height"),
				reportPayload.getChild_details().getHeight());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.weight"),
				reportPayload.getChild_details().getWeight());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.complexion"),
				reportPayload.getChild_details().getComplexion());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.clothing"),
				reportPayload.getChild_details().getClothing());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.birth_signs"),
				reportPayload.getChild_details().getBirth_signs());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.other_details"),
				reportPayload.getChild_details().getOther_details());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.image_file_key"),
				reportPayload.getChild_details().getImage_file_key());
		Assert.assertEquals(res.jsonPath().getString("content[0].child_details.nickname"),
				reportPayload.getChild_details().getNickname());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.incident_date"),
				reportPayload.getIncident_details().getIncident_date());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.incident_brief"),
				reportPayload.getIncident_details().getIncident_brief());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.location"),
				reportPayload.getIncident_details().getLocation());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.landmark_signs"),
				reportPayload.getIncident_details().getLandmark_signs());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.nearby_police_station"),
				reportPayload.getIncident_details().getNearby_police_station());
		Assert.assertEquals(res.jsonPath().getString("content[0].incident_details.nearby_NGO"),
				reportPayload.getIncident_details().getNearby_NGO());
		Assert.assertEquals(res.jsonPath().getBoolean("content[0].incident_details.allow_connect_police_NGO"),
				reportPayload.getIncident_details().isAllow_connect_police_NGO());
		Assert.assertEquals(res.jsonPath().getBoolean("content[0].incident_details.self_verification"),
				reportPayload.getIncident_details().isSelf_verification());
		Assert.assertEquals(res.jsonPath().getBoolean("content[0].incident_details.community_terms"),
				reportPayload.getIncident_details().isCommunity_terms());

	}
}