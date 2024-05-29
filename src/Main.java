import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Declarar globalmente el sistema y los doctores, pacientes y citas

        // Iniciar el sistema
        
        SistemaCitas sistema = new SistemaCitas();
        sistema.iniciarSistema();
    }
}

class Doctor {
    String id;
    String nombreCompleto;
    String especialidad;

    Doctor(String id, String nombreCompleto, String especialidad) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    String obtenerId() {
        return id;
    }

    String obtenerNombreCompleto() {
        return nombreCompleto;
    }

    String obtenerEspecialidad() {
        return especialidad;
    }
}

class Paciente {
    String id;
    String nombreCompleto;

    Paciente(String id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    String obtenerId() {
        return id;
    }

    String obtenerNombreCompleto() {
        return nombreCompleto;
    }
}

class Cita {
    String id;
    LocalDateTime fechaHora;
    String motivo;
    Doctor doctor;
    Paciente paciente;

    Cita(String id, LocalDateTime fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    String obtenerId() {
        return id;
    }

    LocalDateTime obtenerFechaHora() {
        return fechaHora;
    }

    String obtenerMotivo() {
        return motivo;
    }

    Doctor obtenerDoctor() {
        return doctor;
    }

    Paciente obtenerPaciente() {
        return paciente;
    }
}

class Administrador {
    String id;
    String password;

    Administrador(String id, String password) {
        this.id = id;
        this.password = password;
    }

    String obtenerId() {
        return id;
    }

    String obtenerPassword() {
        return password;
    }
}

class SistemaCitas {
    List<Doctor> doctores;
    List<Paciente> pacientes;
    List<Cita> citas;
    List<Administrador> administradores;

    SistemaCitas() {
        doctores = new ArrayList<>();
        pacientes = new ArrayList<>();
        citas = new ArrayList<>();
        administradores = new ArrayList<>();
    }

    void iniciarSistema() {
        System.out.println("Iniciando sistema... En proceso de desarrollo.");
    }

    void altaDoctor(String id, String nombreCompleto, String especialidad) {
        Doctor doctor = new Doctor(id, nombreCompleto, especialidad);
        doctores.add(doctor);
    }

    void altaPaciente(String id, String nombreCompleto) {
        Paciente paciente = new Paciente(id, nombreCompleto);
        pacientes.add(paciente);
    }

    void crearCita(String id, LocalDateTime fechaHora, String motivo, String doctorId, String pacienteId) {
        Doctor doctor = buscarDoctorPorId(doctorId);
        Paciente paciente = buscarPacientePorId(pacienteId);
        Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
        citas.add(cita);
    }

    boolean login(String adminId, String password) {
        for (Administrador admin : administradores) {
            if (admin.obtenerId().equals(adminId) && admin.obtenerPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    Doctor buscarDoctorPorId(String id) {
        for (Doctor doctor : doctores) {
            if (doctor.obtenerId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    Paciente buscarPacientePorId(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.obtenerId().equals(id)) {
                return paciente;
            }
        }
        return null;
    }

    Cita buscarCitaPorId(String id) {
        for (Cita cita : citas) {
            if (cita.obtenerId().equals(id)) {
                return cita;
            }
        }
        return null;
    }

    void guardarDatos() {
        guardarDoctores();
        guardarPacientes();
        guardarCitas();
        guardarAdministradores();
    }

    void guardarDoctores() {
        try (FileWriter writer = new FileWriter("src/db/doctores.csv")) {
            // Valida si los encabezados ya existen
            if (doctores.size() > 0) {
                writer.append("ID,Nombre Completo,Especialidad\n");
            }
            for (Doctor doctor : doctores) {
                writer.append(doctor.obtenerId())
                    .append(',')
                    .append(doctor.obtenerNombreCompleto())
                    .append(',')
                    .append(doctor.obtenerEspecialidad())
                    .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void guardarPacientes() {
        try (FileWriter writer = new FileWriter("src/db/pacientes.csv")) {
            // Valida si los encabezados ya existen
            if (pacientes.size() > 0) {
                writer.append("ID,Nombre Completo\n");
            }
            for (Paciente paciente : pacientes) {
                writer.append(paciente.obtenerId())
                    .append(',')
                    .append(paciente.obtenerNombreCompleto())
                    .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void guardarCitas() {
        try (FileWriter writer = new FileWriter("src/db/citas.csv")) {
            // Valida si los encabezados ya existen
            if (citas.size() > 0) {
                writer.append("ID,Fecha/Hora,Motivo,ID Doctor,ID Paciente\n");
            }
            for (Cita cita : citas) {
                writer.append(cita.obtenerId())
                    .append(',')
                    .append(cita.obtenerFechaHora().toString())
                    .append(',')
                    .append(cita.obtenerMotivo())
                    .append(',')
                    .append(cita.obtenerDoctor().obtenerId())
                    .append(',')
                    .append(cita.obtenerPaciente().obtenerId())
                    .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void guardarAdministradores() {
        try (FileWriter writer = new FileWriter("src/db/administradores.csv")) {
            // Valida si los encabezados ya existen
            if (administradores.size() > 0) {
                writer.append("ID,password\n");
            }
            for (Administrador admin : administradores) {
                writer.append(admin.obtenerId())
                    .append(',')
                    .append(admin.obtenerPassword())
                    .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void cargarDoctores() {
        // Cargar doctores desde el archivo doctores.csv
        try (FileReader reader = new FileReader("src/db/doctores.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder nombreCompleto = new StringBuilder();
            StringBuilder especialidad = new StringBuilder();
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
                        nombreCompleto.setLength(0);
                        especialidad.setLength(0);
                        field = 0;
                        continue;
                    }
                    altaDoctor(id.toString(), nombreCompleto.toString(), especialidad.toString());
                    id.setLength(0);
                    nombreCompleto.setLength(0);
                    especialidad.setLength(0);
                    field = 0;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        nombreCompleto.append((char) c);
                        break;
                    case 2:
                        especialidad.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void cargarPacientes() {
        // Cargar pacientes desde el archivo pacientes.csv
        try (FileReader reader = new FileReader("src/db/pacientes.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder nombreCompleto = new StringBuilder();
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
                        nombreCompleto.setLength(0);
                        field = 0;
                        continue;
                    }
                    altaPaciente(id.toString(), nombreCompleto.toString());
                    id.setLength(0);
                    nombreCompleto.setLength(0);
                    field = 0;
                    continue;
                }
                switch (field) {
                    case 0:
                        id.append((char) c);
                        break;
                    case 1:
                        nombreCompleto.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void cargarCitas() {
        // Cargar citas desde el archivo citas.csv
        try (FileReader reader = new FileReader("src/db/citas.csv")) {
            int c;
            StringBuilder id = new StringBuilder();
            StringBuilder fechaHora = new StringBuilder();
            StringBuilder motivo = new StringBuilder();
            StringBuilder doctorId = new StringBuilder();
            StringBuilder pacienteId = new StringBuilder();
            int field = 0;
            boolean firstLine = true;
            while ((c = reader.read()) != -1) {
                if (c == '\n') {
                    if (firstLine) {
                        firstLine = false;
                        id.setLength(0);
                        fechaHora.setLength(0);
                        motivo.setLength(0);
                        doctorId.setLength(0);
                        pacienteId.setLength(0);
                        field = 0;
                        continue;
                    }
                    if (!fechaHora.toString().isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(fechaHora.toString(), formatter);
                        crearCita(id.toString(), dateTime, motivo.toString(), doctorId.toString(), pacienteId.toString());
                    }
                    id.setLength(0);
                    fechaHora.setLength(0);
                    motivo.setLength(0);
                    doctorId.setLength(0);
                    pacienteId.setLength(0);
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
                        fechaHora.append((char) c);
                        break;
                    case 2:
                        motivo.append((char) c);
                        break;
                    case 3:
                        doctorId.append((char) c);
                        break;
                    case 4:
                        pacienteId.append((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void cargarAdministradores() {
        // Cargar administradores desde el archivo administradores.csv
        try (FileReader reader = new FileReader("src/db/administradores.csv")) {
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
                    administradores.add(new Administrador(id.toString(), password.toString()));
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

}