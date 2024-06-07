import java.time.LocalDateTime;

public class Appointment {
    String id;
    LocalDateTime dateTime;
    String motive;
    Doctor doctor;
    Patient patient;

    Appointment(String id, LocalDateTime dateTime, String motive, Doctor doctor, Patient patient) {
        this.id = id;
        this.dateTime = dateTime;
        this.motive = motive;
        this.doctor = doctor;
        this.patient = patient;
    }

    String getId() {
        return id;
    }

    LocalDateTime getDateTime() {
        return dateTime;
    }

    String getMotive() {
        return motive;
    }

    Doctor getDoctor() {
        return doctor;
    }

    Patient getPatient() {
        return patient;
    }
}