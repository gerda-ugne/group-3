package hospital.staff;

import java.time.DayOfWeek;
import java.util.Date;

public class WorkingHours {

	private Date startHour;

	private Date endHour;

	public WorkingHours(DayOfWeek day, Date startHour, Date endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public Date getStartHour() {
		return startHour;
	}

	public void setStartHour(Date startHour) {
		this.startHour = startHour;
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}
}