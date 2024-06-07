import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    public MainMenuView(AppointmentsSystem appointmentsSystem) {

        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establecer FlatLaf como Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton doctorsButton = new JButton("Gestión de Doctores");
        JButton patientsButton = new JButton("Gestión de Pacientes");
        JButton appointmentsButton = new JButton("Gestión de Citas");
        JButton logoutButton = new JButton("Cerrar Sesión");

        doctorsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        patientsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        appointmentsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(doctorsButton);
        panel.add(patientsButton);
        panel.add(appointmentsButton);
        panel.add(logoutButton);

        add(panel, BorderLayout.CENTER);

        // Agregar listeners para cada botón
        doctorsButton.addActionListener(_ -> {
            DoctorView doctorView = new DoctorView(appointmentsSystem);
            doctorView.setVisible(true);
            dispose();
        });

        patientsButton.addActionListener(_ -> {
            PatientView patientView = new PatientView(appointmentsSystem);
            patientView.setVisible(true);
            dispose();
        });

        appointmentsButton.addActionListener(_ -> {
            AppointmentView appointmentView = new AppointmentView(appointmentsSystem);
            appointmentView.setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(_ -> {
            LoginView loginView = new LoginView(appointmentsSystem);
            loginView.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        AppointmentsSystem appointmentsSystem = new AppointmentsSystem();
        appointmentsSystem.startSystem();

        SwingUtilities.invokeLater(() -> {
            MainMenuView mainMenuView = new MainMenuView(appointmentsSystem);
            mainMenuView.setVisible(true);
        });
    }
}
