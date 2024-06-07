import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsSystem {
    List<Doctor> doctors;
    List<Patient> patients;
    List<Appointment> appointments;
    List<Administrator> administrators;

    AppointmentsSystem() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        administrators = new ArrayList<>();
    }

    void startSystem() {
        System.out.println("Iniciando sistema... En proceso de desarrollo.");
        loadData();
    }

    void createDoctor(String id, String fullName, String specialization) {
        Doctor doctor = new Doctor(id, fullName, specialization);
        doctors.add(doctor);
    }

    void createPatient(String id, String fullName) {
        Patient patient = new Patient(id, fullName);
        patients.add(patient);
    }

    void createAppointment(String id, LocalDateTime dateTime, String motive, String doctorId, String patientId) {
        Doctor doctor = searchDoctorById(doctorId);
        Patient patient = searchPatientById(patientId);
        Appointment appointment = new Appointment(id, dateTime, motive, doctor, patient);
        appointments.add(appointment);
    }

    boolean login(String adminId, String password) {
        for (Administrator admin : administrators) {
            if (admin.getId().equals(adminId) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    Doctor searchDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    Patient searchPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    Appointment searchAppointmentById(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId().equals(id)) {
                return appointment;
            }
        }
        return null;
    }

    void saveData() {
        saveDoctors();
        savePatients();
        saveAppointments();
        saveAdministrators();
    }

    void saveDoctors() {
        try (FileWriter writer = new FileWriter("src/db/doctors.csv")) {
            if (!doctors.isEmpty()) {
                writer.append("ID,Nombre Completo,Especialidad\n");
            }
            for (Doctor doctor : doctors) {
                writer.append(doctor.getId())
                        .append(',')
                        .append(doctor.getFullName())
                        .append(',')
                        .append(doctor.getSpecialization())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void savePatients() {
        try (FileWriter writer = new FileWriter("src/db/patients.csv")) {
            if (!patients.isEmpty()) {
                writer.append("ID,Nombre Completo\n");
            }
            for (Patient patient : patients) {
                writer.append(patient.getId())
                        .append(',')
                        .append(patient.getFullName())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveAppointments() {
        try (FileWriter writer = new FileWriter("src/db/appointments.csv")) {
            if (!appointments.isEmpty()) {
                writer.append("ID,Fecha/Hora,Motivo,ID Doctor,ID Patient\n");
            }
            for (Appointment appointment : appointments) {
                writer.append(appointment.getId())
                        .append(',')
                        .append(appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .append(',')
                        .append(appointment.getMotive())
                        .append(',')
                        .append(appointment.getDoctor().getId())
                        .append(',')
                        .append(appointment.getPatient().getId())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveAdministrators() {
        try (FileWriter writer = new FileWriter("src/db/administrators.csv")) {
            if (!administrators.isEmpty()) {
                writer.append("ID,password\n");
            }
            for (Administrator admin : administrators) {
                writer.append(admin.getId())
                        .append(',')
                        .append(admin.getPassword())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadData() {

        Path dbDirectory = Paths.get("src/db");
        if (!Files.exists(dbDirectory)) {
            try {
                Files.createDirectory(dbDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] csvFiles = {"doctors.csv", "patients.csv", "appointments.csv", "administrators.csv"};
        for (String csvFile : csvFiles) {
            Path csvFilePath = Paths.get("src/db/" + csvFile);
            if (!Files.exists(csvFilePath)) {
                try {
                    Files.createFile(csvFilePath);
                    if (csvFile.equals("administrators.csv")) {
                        loadAdministrators();
                        administrators.add(new Administrator("admin", "admin"));
                        saveAdministrators();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        loadDoctors();
        loadAdministrators();
        loadAppointments();
        loadPatients();
    }

    void loadDoctors() {
        try (FileReader reader = new FileReader("src/db/doctors.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder fullName = new StringBuilder();
            StringBuilder specialization = new StringBuilder();
            int field = 0;
            boolean firstLine = true;
            while ((c = reader.read()) != -1) {
                if (c == ',') {
                    field++;
                    continue;
                }
                if (c == '\n') {
                    if (firstLine) {
                        firstLine = false;
                        id.setLength(0);
                        fullName.setLength(0);
                        specialization.setLength(0);
                        field = 0;
                        continue;
                    }
                    createDoctor(id.toString(), fullName.toString(), specialization.toString());
                    id.setLength(0);
                    fullName.setLength(0);
                    specialization.setLength(0);
                    field = 0;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        fullName.append((char) c);
                        break;
                    case 2:
                        specialization.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void loadPatients() {
        try (FileReader reader = new FileReader("src/db/patients.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder fullName = new StringBuilder();
            int field = 0;
            boolean firstLine = true;
            while ((c = reader.read()) != -1) {
                if (c == ',') {
                    field++;
                    continue;
                }
                if (c == '\n') {
                    if (firstLine) {
                        firstLine = false;
                        id.setLength(0);
                        fullName.setLength(0);
                        field = 0;
                        continue;
                    }
                    createPatient(id.toString(), fullName.toString());
                    id.setLength(0);
                    fullName.setLength(0);
                    field = 0;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        fullName.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadAppointments() {
        try (FileReader reader = new FileReader("src/db/appointments.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder dateTime = new StringBuilder();
            StringBuilder motive = new StringBuilder();
            StringBuilder doctorId = new StringBuilder();
            StringBuilder patientId = new StringBuilder();
            int field = 0;
            boolean firstLine = true;
            while ((c = reader.read()) != -1) {
                if (c == '\n') {
                    if (firstLine) {
                        firstLine = false;
                        id.setLength(0);
                        dateTime.setLength(0);
                        motive.setLength(0);
                        doctorId.setLength(0);
                        patientId.setLength(0);
                        field = 0;
                        continue;
                    }
                    if (!dateTime.toString().isEmpty()) {
                        DateTimeFormatter formatter;
                        LocalDateTime parsedDateTime;
                        try {
                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            parsedDateTime = LocalDateTime.parse(dateTime.toString(), formatter);
                        } catch (Exception e) {
                            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                            parsedDateTime = LocalDateTime.parse(dateTime.toString(), formatter);
                        }
                        createAppointment(id.toString(), parsedDateTime, motive.toString(), doctorId.toString(), patientId.toString());
                    }
                    id.setLength(0);
                    dateTime.setLength(0);
                    motive.setLength(0);
                    doctorId.setLength(0);
                    patientId.setLength(0);
                    field = 0;
                    continue;
                }
                if (c == ',') {
                    field++;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        dateTime.append((char) c);
                        break;
                    case 2:
                        motive.append((char) c);
                        break;
                    case 3:
                        doctorId.append((char) c);
                        break;
                    case 4:
                        patientId.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadAdministrators() {
        try (FileReader reader = new FileReader("src/db/administrators.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder password = new StringBuilder();
            int field = 0;
            boolean firstLine = true;
            while ((c = reader.read()) != -1) {
                if (c == ',') {
                    field++;
                    continue;
                }
                if (c == '\n') {
                    if (firstLine) {
                        firstLine = false;
                        id.setLength(0);
                        password.setLength(0);
                        field = 0;
                        continue;
                    }
                    administrators.add(new Administrator(id.toString(), password.toString()));
                    id.setLength(0);
                    password.setLength(0);
                    field = 0;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        password.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNextDoctorId() {
        int maxId = doctors.stream()
                .mapToInt(doctor -> Integer.parseInt(doctor.getId()))
                .max()
                .orElse(0);
        return String.valueOf(maxId + 1);
    }

    public String getNextPatientId() {
        int maxId = patients.stream()
                .mapToInt(patient -> Integer.parseInt(patient.getId()))
                .max()
                .orElse(0);
        return String.valueOf(maxId + 1);
    }

    public String getNextAppointmentId() {
        int maxId = appointments.stream()
                .mapToInt(appointment -> Integer.parseInt(appointment.getId()))
                .max()
                .orElse(0);
        return String.valueOf(maxId + 1);
    }

}
