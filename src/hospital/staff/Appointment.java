package hospital.staff;

import java.util.ArrayList;
import java.util.List;

public class Appointment {

	private static long counter = 0;

	private long id;

	private long startTime;

	private long endTime;

	private String room;

	private String treatmentType;

	private List<Professional> professionals;

	public Appointment() {
		this.id = counter++;
		this.startTime = -1;
		this.endTime = -1;
		this.room = "<undefined>";
		this.treatmentType = "<undefined>";
		this.professionals = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
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