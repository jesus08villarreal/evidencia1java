import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientView extends JFrame {
    private final AppointmentsSystem appointmentsSystem;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public PatientView(AppointmentsSystem appointmentsSystem) {
        this.appointmentsSystem = appointmentsSystem;

        setTitle("Gestión de Pacientes");
        setSize(800, 600);
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

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        table = new JTable(tableModel);
        loadPatients();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Name

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        constraints.gridx = 0;
        constraints.gridy = 4;
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
            if (appointmentsSystem.searchPatientById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = nameField.getText();
            appointmentsSystem.createPatient(id, name);
            appointmentsSystem.savePatients();
            tableModel.addRow(new Object[]{id, name});
            clearFields();
        });

        updateButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = idField.getText();
                String name = nameField.getText();
                Patient patient = appointmentsSystem.searchPatientById(id);
                if (patient != null) {
                    patient.fullName = name;
                    tableModel.setValueAt(name, selectedRow, 1);
                    appointmentsSystem.savePatients();
                    clearFields();
                }
            }
        });

        deleteButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = tableModel.getValueAt(selectedRow, 0).toString();
                appointmentsSystem.patients.removeIf(patient -> patient.getId().equals(id));
                appointmentsSystem.savePatients();
                tableModel.removeRow(selectedRow);
                clearFields();
            }
        });
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
    }

    private void loadPatients() {
        for (Patient patient : appointmentsSystem.patients) {
            tableModel.addRow(new Object[]{patient.getId(), patient.getFullName()});
        }
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
