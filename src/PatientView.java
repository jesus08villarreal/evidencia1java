import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class PatientView extends JFrame {
    private final JTextField idField;
    private final JTextField nameField;

    public PatientView(AppointmentsSystem appointmentsSystem) {

        setTitle("Gestión de Pacientes");
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
        panel.add(new JLabel("Nombre:"), constraints);

        nameField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(nameField, constraints);

        JButton addButton = new JButton("Agregar");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(addButton, constraints);

        JButton updateButton = new JButton("Actualizar");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(updateButton, constraints);

        JButton deleteButton = new JButton("Eliminar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(deleteButton, constraints);

        JButton backButton = new JButton("Regresar");
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(backButton, constraints);

        add(panel, BorderLayout.CENTER);

        // Agregar listeners para cada botón
        backButton.addActionListener(_ -> {
            MainMenuView mainMenuView = new MainMenuView(appointmentsSystem);
            mainMenuView.setVisible(true);
            dispose();
        });

        // Otros listeners para agregar, actualizar y eliminar pacientes...
        addButton.addActionListener(_ -> {
            String id = idField.getText();
            String name = nameField.getText();
            appointmentsSystem.createPatient(id, name);
            JOptionPane.showMessageDialog(PatientView.this, "Paciente agregado exitosamente!");
        });

        updateButton.addActionListener(_ -> {
            // Lógica para actualizar paciente
        });

        deleteButton.addActionListener(_ -> {
            // Lógica para eliminar paciente
        });
    }

    public static void main(String[] args) {
        AppointmentsSystem appointmentsSystem = new AppointmentsSystem();
        appointmentsSystem.startSystem();

        SwingUtilities.invokeLater(() -> {
            PatientView patientView = new PatientView(appointmentsSystem);
            patientView.setVisible(true);
        });
    }
}
