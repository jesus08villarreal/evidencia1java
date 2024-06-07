import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoctorView extends JFrame {
    private final AppointmentsSystem appointmentsSystem;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField specialtyField;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public DoctorView(AppointmentsSystem appointmentsSystem) {
        this.appointmentsSystem = appointmentsSystem;

        setTitle("Gestión de Doctores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Especialidad:"), constraints);

        specialtyField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(specialtyField, constraints);

        JButton addButton = new JButton("Agregar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(addButton, constraints);

        JButton updateButton = new JButton("Actualizar");
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(updateButton, constraints);

        JButton deleteButton = new JButton("Eliminar");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(deleteButton, constraints);

        JButton backButton = new JButton("Regresar");
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(backButton, constraints);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Especialidad"}, 0);
        table = new JTable(tableModel);
        loadDoctors();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                specialtyField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Specialization

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(scrollPane, constraints);

        add(panel, BorderLayout.CENTER);

        backButton.addActionListener(_ -> {
            MainMenuView mainMenuView = new MainMenuView(appointmentsSystem);
            mainMenuView.setVisible(true);
            dispose();
        });

        addButton.addActionListener(_ -> {
            String id = idField.getText();
            if (appointmentsSystem.searchDoctorById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = nameField.getText();
            String specialty = specialtyField.getText();
            appointmentsSystem.createDoctor(id, name, specialty);
            appointmentsSystem.saveDoctors();
            tableModel.addRow(new Object[]{id, name, specialty});
            clearFields();
        });

        updateButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = idField.getText();
                String name = nameField.getText();
                String specialty = specialtyField.getText();
                Doctor doctor = appointmentsSystem.searchDoctorById(id);
                if (doctor != null) {
                    doctor.fullName = name;
                    doctor.specialization = specialty;
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(specialty, selectedRow, 2);
                    clearFields();
                }
                appointmentsSystem.saveDoctors();
            }
        });

        deleteButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = tableModel.getValueAt(selectedRow, 0).toString();
                appointmentsSystem.doctors.removeIf(doctor -> doctor.getId().equals(id));
                appointmentsSystem.saveDoctors();
                tableModel.removeRow(selectedRow);
                clearFields();}
        });
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        specialtyField.setText("");
    }

    private void loadDoctors() {
        for (Doctor doctor : appointmentsSystem.doctors) {
            tableModel.addRow(new Object[]{doctor.getId(), doctor.getFullName(), doctor.getSpecialization()});
        }
    }

    public static void main(String[] args) {
        AppointmentsSystem appointmentsSystem = new AppointmentsSystem();
        appointmentsSystem.startSystem();

        SwingUtilities.invokeLater(() -> {
            DoctorView doctorView = new DoctorView(appointmentsSystem);
            doctorView.setVisible(true);
        });
    }
}
