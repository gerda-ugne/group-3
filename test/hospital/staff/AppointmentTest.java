package hospital.staff;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    // TODO recreate test with java.time package
//    @Test
//    void TestCompareTo() {
//        Calendar calendar = new GregorianCalendar(2020, Calendar.APRIL, 25);
//        Date before = calendar.getTime();
//        calendar.set(Calendar.DAY_OF_MONTH, 26);
//        Date middle = calendar.getTime();
//        calendar.set(Calendar.DAY_OF_MONTH, 27);
//        Date after = calendar.getTime();
//        Appointment first = new Appointment(before, new Date(before.getTime() + Appointment.TREATMENT_DURATION));
//        Appointment second = new Appointment(middle, new Date(middle.getTime() + Appointment.TREATMENT_DURATION));
//        Appointment third = new Appointment(after, new Date(after.getTime() + Appointment.TREATMENT_DURATION));
//        Appointment same = new Appointment(middle, new Date(middle.getTime() + 1000000));
//        assertAll(
//                () -> assertTrue(second.compareTo(first) > 0),
//                () -> assertTrue(second.compareTo(third) < 0),
//                () -> assertTrue(second.compareTo(same) == 0)
//        );
//    }
}