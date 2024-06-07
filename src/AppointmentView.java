import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentView extends JFrame {
    private final JTextField idField;
    private final JTextField dateTimeField;
    private final JTextField motiveField;
    private final JTextField doctorIdField;
    private final JTextField patientIdField;

    public AppointmentView(AppointmentsSystem appointmentsSystem) {

        setTitle("Gestión de Citas");
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
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("ID:"), constraints);

        idField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(idField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Fecha y Hora (yyyy-MM-dd HH:mm:ss):"), constraints);

        dateTimeField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(dateTimeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Motivo:"), constraints);

        motiveField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(motiveField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("ID Doctor:"), constraints);

        doctorIdField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(doctorIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(new JLabel("ID Paciente:"), constraints);

        patientIdField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(patientIdField, constraints);

        JButton addButton = new JButton("Agregar");
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(addButton, constraints);

        JButton updateButton = new JButton("Actualizar");
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(updateButton, constraints);

        JButton deleteButton = new JButton("Eliminar");
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(deleteButton, constraints);

        JButton backButton = new JButton("Regresar");
        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(backButton, constraints);

        add(panel, BorderLayout.CENTER);

        // Agregar listeners para cada botón
        backButton.addActionListener(_ -> {
            MainMenuView mainMenuView = new MainMenuView(appointmentsSystem);
            mainMenuView.setVisible(true);
            dispose();
        });

        // Otros listeners para agregar, actualizar y eliminar citas...
        addButton.addActionListener(_ -> {
            String id = idField.getText();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String motive = motiveField.getText();
            String doctorId = doctorIdField.getText();
            String patientId = patientIdField.getText();
            appointmentsSystem.createAppointment(id, dateTime, motive, doctorId, patientId);
            JOptionPane.showMessageDialog(AppointmentView.this, "Cita agregada exitosamente!");
        });

        updateButton.addActionListener(_ -> {
            // Lógica para actualizar cita
        });

        deleteButton.addActionListener(_ -> {
            // Lógica para eliminar cita
        });
    }

    public static void main(String[] args) {
        AppointmentsSystem appointmentsSystem = new AppointmentsSystem();
        appointmentsSystem.startSystem();

        SwingUtilities.invokeLater(() -> {
            AppointmentView appointmentView = new AppointmentView(appointmentsSystem);
            appointmentView.setVisible(true);
        });
    }
}
