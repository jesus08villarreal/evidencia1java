public class Administrator {
    String id;
    String password;

    Administrator(String id, String password) {
        this.id = id;
        this.password = password;
    }

    String getId() {
        return id;
    }

    String getPassword() {
        return password;
    }
}