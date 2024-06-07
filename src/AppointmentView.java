import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppointmentView extends JFrame {
    private final AppointmentsSystem appointmentsSystem;
    private final JTextField idField;
    private final JDateChooser dateChooser;
    private final JTextField timeField;
    private final JTextField motiveField;
    private final SearchableComboBox doctorComboBox;
    private final SearchableComboBox patientComboBox;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public AppointmentView(AppointmentsSystem appointmentsSystem) {
        this.appointmentsSystem = appointmentsSystem;

        setTitle("Gestión de Citas");
        setSize(800, 700);
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
        idField.setText(appointmentsSystem.getNextAppointmentId());
        constraints.gridx = 1;
        panel.add(idField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Fecha:"), constraints);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        constraints.gridx = 1;
        panel.add(dateChooser, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Hora (HH:mm):"), constraints);

        timeField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(timeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("Motivo:"), constraints);

        motiveField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(motiveField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(new JLabel("ID Doctor:"), constraints);

        List<ComboBoxItem> doctorItems = appointmentsSystem.doctors.stream()
                .map(doctor -> new ComboBoxItem(doctor.getId(), doctor.getFullName()))
                .collect(Collectors.toList());
        doctorComboBox = new SearchableComboBox(doctorItems);
        doctorComboBox.setPreferredSize(new Dimension(150, 24));
        constraints.gridx = 1;
        panel.add(doctorComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(new JLabel("ID Paciente:"), constraints);

        List<ComboBoxItem> patientItems = appointmentsSystem.patients.stream()
                .map(patient -> new ComboBoxItem(patient.getId(), patient.getFullName()))
                .collect(Collectors.toList());
        patientComboBox = new SearchableComboBox(patientItems);
        patientComboBox.setPreferredSize(new Dimension(150, 24));
        constraints.gridx = 1;
        panel.add(patientComboBox, constraints);

        JButton addButton = new JButton("Agregar");
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(addButton, constraints);

        JButton updateButton = new JButton("Actualizar");
        constraints.gridx = 1;
        constraints.gridy = 6;
        panel.add(updateButton, constraints);

        JButton deleteButton = new JButton("Eliminar");
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(deleteButton, constraints);

        JButton backButton = new JButton("Regresar");
        constraints.gridx = 1;
        constraints.gridy = 7;
        panel.add(backButton, constraints);

        tableModel = new DefaultTableModel(new String[]{"ID", "Fecha y Hora", "Motivo", "ID Doctor", "ID Paciente"}, 0);
        table = new JTable(tableModel);
        loadAppointments();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                String[] dateTimeParts = tableModel.getValueAt(selectedRow, 1).toString().split(" ");
                if (dateTimeParts.length == 2) {
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeParts[0]);
                        dateChooser.setDate(date);
                        timeField.setText(dateTimeParts[1]);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                motiveField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                doctorComboBox.setSelectedItem(new ComboBoxItem(
                        tableModel.getValueAt(selectedRow, 3).toString(),
                        appointmentsSystem.searchDoctorById(tableModel.getValueAt(selectedRow, 3).toString()).getFullName()
                ));
                patientComboBox.setSelectedItem(new ComboBoxItem(
                        tableModel.getValueAt(selectedRow, 4).toString(),
                        appointmentsSystem.searchPatientById(tableModel.getValueAt(selectedRow, 4).toString()).getFullName()
                ));
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        constraints.gridx = 0;
        constraints.gridy = 8;
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
            if (appointmentsSystem.searchAppointmentById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date date = dateChooser.getDate();
            String time = timeField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + time, formatter);
            String motive = motiveField.getText();
            String doctorId = ((ComboBoxItem) Objects.requireNonNull(doctorComboBox.getSelectedItem())).getId();
            String patientId = ((ComboBoxItem) Objects.requireNonNull(patientComboBox.getSelectedItem())).getId();
            appointmentsSystem.createAppointment(id, dateTime, motive, doctorId, patientId);
            appointmentsSystem.saveAppointments();
            tableModel.addRow(new Object[]{id, dateTime.toString(), motive, doctorId, patientId});
            clearFields();
        });

        updateButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = idField.getText();
                Date date = dateChooser.getDate();
                String time = timeField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + time, formatter);
                String motive = motiveField.getText();
                String doctorId = ((ComboBoxItem) Objects.requireNonNull(doctorComboBox.getSelectedItem())).getId();
                String patientId = ((ComboBoxItem) Objects.requireNonNull(patientComboBox.getSelectedItem())).getId();
                Appointment appointment = appointmentsSystem.searchAppointmentById(id);
                if (appointment != null) {
                    appointment.dateTime = dateTime;
                    appointment.motive = motive;
                    appointment.doctor = appointmentsSystem.searchDoctorById(doctorId);
                    appointment.patient = appointmentsSystem.searchPatientById(patientId);
                    tableModel.setValueAt(dateTime.toString(), selectedRow, 1);
                    tableModel.setValueAt(motive, selectedRow, 2);
                    tableModel.setValueAt(doctorId, selectedRow, 3);
                    tableModel.setValueAt(patientId, selectedRow, 4);
                    clearFields();
                }
                appointmentsSystem.saveAppointments();
            }
        });

        deleteButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = tableModel.getValueAt(selectedRow, 0).toString();
                appointmentsSystem.appointments.removeIf(appointment -> appointment.getId().equals(id));
                appointmentsSystem.saveAppointments();
                tableModel.removeRow(selectedRow);
                clearFields();
            }
        });
    }

    private void clearFields() {
        idField.setText(appointmentsSystem.getNextAppointmentId());
        dateChooser.setDate(null);
        timeField.setText("");
        motiveField.setText("");
        doctorComboBox.setSelectedItem(null);
        patientComboBox.setSelectedItem(null);
    }

    private void loadAppointments() {
        for (Appointment appointment : appointmentsSystem.appointments) {
            String doctorId = (appointment.getDoctor() != null) ? appointment.getDoctor().getId() : "N/A";
            String patientId = (appointment.getPatient() != null) ? appointment.getPatient().getId() : "N/A";
            tableModel.addRow(new Object[]{appointment.getId(), appointment.getDateTime().toString(), appointment.getMotive(), doctorId, patientId});
        }
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
