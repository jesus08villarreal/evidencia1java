public class Patient {
    String id;
    String fullName;

    Patient(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    String getId() {
        return id;
    }

    String getFullName() {
        return fullName;
    }
}