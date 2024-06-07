import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Iniciar el sistema
        AppointmentsSystem sistema = new AppointmentsSystem();
        sistema.startSystem();

        // Crear y mostrar la vista de login
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(sistema);
            loginView.setVisible(true);
        });
    }
}
