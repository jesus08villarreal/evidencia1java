import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class Main {
    private JPanel Login;
    private JButton ingresarButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        // Declarar globalmente el sistema y los doctores, patients y appointments

        // Iniciar el sistema
        
        AppointmentsSystem sistema = new AppointmentsSystem();
        sistema.startSystem();

        // Crear un nuevo JFrame y agregar el panel de login
        JFrame frame = new JFrame("Login");
        Main main = new Main();
        frame.setContentPane(main.Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        // Agregar un ActionListener al botón ingresar
        main.ingresarButton.addActionListener(e -> {
            // Obtener el nombre de usuario y la contraseña ingresados
            String username = main.usernameField.getText();
            String password = new String(main.passwordField.getPassword());

            // Verificar las credenciales del usuario
            if (sistema.login(username, password)) {
                // Si las credenciales son correctas, mostrar el formulario principal
                JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso!");
                // Aquí puedes agregar el código para mostrar el formulario principal
            } else {
                // Si las credenciales son incorrectas, mostrar un mensaje de error
                JOptionPane.showMessageDialog(frame, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

