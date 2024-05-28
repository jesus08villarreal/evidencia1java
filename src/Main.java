import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
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
    String contraseña;

    Administrador(String id, String contraseña) {
        this.id = id;
        this.contraseña = contraseña;
    }

    String obtenerId() {
        return id;
    }

    String obtenerContraseña() {
        return contraseña;
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
        // Implementación para iniciar el sistema, mostrando el menú principal
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

    boolean login(String adminId, String contraseña) {
        for (Administrador admin : administradores) {
            if (admin.obtenerId().equals(adminId) && admin.obtenerContraseña().equals(contraseña)) {
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
        try (FileWriter writer = new FileWriter("doctores.csv")) {
            writer.append("ID,Nombre Completo,Especialidad\n");
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
        try (FileWriter writer = new FileWriter("pacientes.csv")) {
            writer.append("ID,Nombre Completo\n");
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
        try (FileWriter writer = new FileWriter("citas.csv")) {
            writer.append("ID,Fecha/Hora,Motivo,ID Doctor,ID Paciente\n");
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
        try (FileWriter writer = new FileWriter("administradores.csv")) {
            writer.append("ID,Contraseña\n");
            for (Administrador admin : administradores) {
                writer.append(admin.obtenerId())
                    .append(',')
                    .append(admin.obtenerContraseña())
                    .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}