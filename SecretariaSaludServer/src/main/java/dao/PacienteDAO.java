package dao;

import bd.Conexion;
import dominio.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteDAO {
    private final Connection conexion;

    public PacienteDAO() {
        this.conexion = new Conexion().getConexion();
    }

    public boolean agregarPaciente(Paciente paciente){
        try (PreparedStatement statement = conexion.prepareStatement(
                "INSERT INTO pacientes (nombre, curp, fechaNacimiento, tutor, pass) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, paciente.getNombre());
            statement.setString(2, paciente.getCurp());
            statement.setDate(3, paciente.getFechaNacimiento());
            statement.setString(4, paciente.getTutor());
            statement.setString(5, paciente.getPass());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean actualizarPaciente(Paciente paciente) {
        try (PreparedStatement statement = conexion.prepareStatement(
                "UPDATE pacientes SET nombre=?, fechaNacimiento=?, tutor=?, pass=? WHERE curp=?")) {
            statement.setString(1, paciente.getNombre());
            statement.setDate(2, paciente.getFechaNacimiento());
            statement.setString(3, paciente.getTutor());
            statement.setString(4, paciente.getPass());
            statement.setString(5, paciente.getCurp());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminarPaciente(String curp){
        try (PreparedStatement statement = conexion.prepareStatement("DELETE FROM pacientes WHERE curp=?")) {
            statement.setString(1, curp);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Paciente> obtenerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        try (PreparedStatement statement = conexion.prepareStatement("SELECT * FROM pacientes");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(result.getInt("id"));
                paciente.setNombre(result.getString("nombre"));
                paciente.setCurp(result.getString("curp"));
                paciente.setFechaNacimiento(result.getDate("fechaNacimiento"));
                paciente.setTutor(result.getString("tutor"));
                paciente.setPass(result.getString("pass"));
                pacientes.add(paciente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pacientes;
    }
}
