package services;

import java.util.Map;

import javax.xml.crypto.Data;

import base.BaseService;
import constants.APIEndPoints;
import io.restassured.response.Response;
import pojo.request.createbooking.fmc.AddReport;
import pojo.request.createbooking.fmc.ChildDetails;
import pojo.request.createbooking.fmc.IncidentDetails;
import pojo.request.createbooking.fmc.ReporterDetails;
import utilities.Datagenerator;

public class ReportService extends BaseService {

	Response res;
	String requestId=Datagenerator.getNumber(6);
	String reportDate=Datagenerator.getDate();
	String reporterFullName=Datagenerator.getFullname();
	String reporterAge=Datagenerator.getNumber(2);
	String reporterGender=Datagenerator.getGender();
	String reporterRelation = Datagenerator.getRelationship();
	String parentingType = Datagenerator.getRelationship();
	String contactAddressType = Datagenerator.getContactAddressType();
	String contactAddressLine1 = Datagenerator.getContactAdd1();
	String contactAddressLine2 = Datagenerator.getContactAdd2();
	String pincode = Datagenerator.getPinCode();
	String country = Datagenerator.getCountry();
	String countryCode = Datagenerator.getCountryCode();
	String phoneNumber = Datagenerator.getNumber(10);
	String language = Datagenerator.getLanguage();
	
	String incidentDate = Datagenerator.getDate();
	String incidentCity = Datagenerator.getContactAdd1();
	
	String childFullName = Datagenerator.getFullname();
	String childAge = Datagenerator.getNumber(2);
	String childGender = Datagenerator.getGender();
	String childNickname = Datagenerator.getFirstName();
	
	GenerateTokenService generateTokenService =new GenerateTokenService();
	
	public AddReport addReportPayload(int userId) {
		ReporterDetails reporterDetails=new ReporterDetails();
		reporterDetails.setRequest_id(requestId);
		reporterDetails.setUser_id(userId);
		reporterDetails.setReport_date(reportDate);
		reporterDetails.setReporter_fullname(reporterFullName);
		reporterDetails.setReporter_age(Integer.parseInt(reporterAge));
		reporterDetails.setReporter_gender(reporterGender);
		reporterDetails.setReporter_relation("Father");
		reporterDetails.setParenting_type("Own Child");
		reporterDetails.setContact_address_type("Home");
		reporterDetails.setContact_address_line_1(contactAddressLine1);
		reporterDetails.setContact_address_line_2(contactAddressLine2);
		reporterDetails.setPincode(pincode);
		reporterDetails.setCountry(country);
		reporterDetails.setPrimary_country_code(countryCode);
		reporterDetails.setPrimary_contact_number(phoneNumber);
		reporterDetails.setSecondary_country_code(countryCode);
		reporterDetails.setSecondary_contact_number(phoneNumber);
		reporterDetails.setCommunication_language("Gujarati");
		reporterDetails.setStatus("INCOMPLETE");
		
		IncidentDetails incidentDetails =new IncidentDetails();
		incidentDetails.setIncident_date(incidentDate);
		incidentDetails.setIncident_brief("Missing from school.");
		incidentDetails.setLocation(incidentCity);
		incidentDetails.setLandmark_signs("opp. temple");
		incidentDetails.setNearby_police_station("city police station.");
		incidentDetails.setNearby_NGO("Sanskriti NGO");
		incidentDetails.setAllow_connect_police_NGO(true);
		incidentDetails.setSelf_verification(true);
		incidentDetails.setCommunity_terms(true);
		
		ChildDetails childDetails=new ChildDetails();
		childDetails.setFullname(childFullName);
		childDetails.setAge(Integer.parseInt(childAge));
		childDetails.setGender(childGender);
		childDetails.setHeight("5ft");
		childDetails.setWeight("45KG");
		childDetails.setComplexion("Fair");
		childDetails.setClothing("Dress");
		childDetails.setBirth_signs("mark on right hand");
		childDetails.setOther_details("spects");
		childDetails.setImage_file_key(null);
		childDetails.setNickname(childNickname);
		
		AddReport addReport=new AddReport();
		addReport.setChild_details(childDetails);
		addReport.setIncident_details(incidentDetails);
		addReport.setReporter_details(reporterDetails);
		
		return addReport;
	}
	public Response getAddReportResponse(int userId,AddReport addReport) {
		Map<String,String> headerMap=generateTokenService.getHeaderHavignAuth(generateTokenService.getToken());
		return executePostAPI(APIEndPoints.ADDREPORT, headerMap,addReport);
	}
	public Response getGetReportResponse(int userId) {
		Map<String,String> headerMap=generateTokenService.getHeaderHavignAuth(generateTokenService.getToken());
		return executeGetAPI(APIEndPoints.GETREPORT, headerMap);
	}
	public Response getDeleteReportResponse(int content,int userId) {
		Map<String,String> headerMap=generateTokenService.getHeaderHavignAuth(generateTokenService.getToken());
		return executeDeleteAPI(APIEndPoints.GETREPORT + String.valueOf(content)+"/users"+userId,headerMap);
	}
}

















