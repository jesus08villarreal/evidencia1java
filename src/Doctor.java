public class Doctor {
    String id;
    String fullName;
    String specialization;

    Doctor(String id, String fullName, String specialization) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    String getId() {
        return id;
    }

    String getFullName() {
        return fullName;
    }

    String getSpecialization() {
        return specialization;
    }
}