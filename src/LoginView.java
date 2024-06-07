import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField userField;
    private final JPasswordField passwordField;

    public LoginView(AppointmentsSystem appointmentsSystem) {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establecer FlatLaf como Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Usuario:"), constraints);

        userField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(userField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Contraseña:"), constraints);

        passwordField = new JPasswordField(15);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(loginButton, constraints);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(_ -> {
            String user = userField.getText();
            String password = new String(passwordField.getPassword());
            if (appointmentsSystem.login(user, password)) {
                MainMenuView mainMenuView = new MainMenuView(appointmentsSystem);
                mainMenuView.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginView.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        AppointmentsSystem appointmentsSystem = new AppointmentsSystem();
        appointmentsSystem.startSystem();

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(appointmentsSystem);
            loginView.setVisible(true);
        });
    }
}
