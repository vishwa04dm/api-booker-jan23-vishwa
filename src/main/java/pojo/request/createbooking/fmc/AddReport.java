package pojo.request.createbooking.fmc;

public class AddReport {
	public ReporterDetails reporter_details;
	public ChildDetails child_details;
	public IncidentDetails incident_details;
	
	public ReporterDetails getReporter_details() {
		return reporter_details;
	}
	public void setReporter_details(ReporterDetails reporter_details) {
		this.reporter_details = reporter_details;
	}
	public ChildDetails getChild_details() {
		return child_details;
	}
	public void setChild_details(ChildDetails child_details) {
		this.child_details = child_details;
	}
	public IncidentDetails getIncident_details() {
		return incident_details;
	}
	public void setIncident_details(IncidentDetails incident_details) {
		this.incident_details = incident_details;
	}

}
