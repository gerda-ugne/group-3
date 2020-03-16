package hospital.staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointment {

	private static long counter = 0;

	private long id;

	private Date startTime;

	private Date endTime;

	private String room;

	private String treatmentType;

	private List<Professional> professionals;

	public Appointment() {
		// TODO check for existing ids or make sure that counter is restored too with the appointments
		this.id = counter++;
		this.startTime = null;
		this.endTime = null;
		this.room = "<undefined>";
		this.treatmentType = "<undefined>";
		this.professionals = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public List<Professional> getProfessionals() {
		return professionals;
	}

	public void setProfessionals(List<Professional> professionals) {
		this.professionals = professionals;
	}
}