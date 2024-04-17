package dao;

import bd.Conexion;
import dominio.Expediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteDAO {
    private final Connection conexion;

    public ExpedienteDAO() {
        this.conexion = new Conexion().getConexion();
    }

    // Método para agregar un nuevo expediente
    public boolean agregarExpediente(Expediente expediente) {
        try {
            String query = "INSERT INTO expedientes (idPaciente, imagenes, textos, documentos, medicosAcceso, acceso) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, expediente.getIdPaciente());
            statement.setString(2, expediente.getImagenes());
            statement.setString(3, expediente.getTextos());
            statement.setString(4, expediente.getDocumentos());
            statement.setString(5, expediente.getMedicos());
            statement.setBoolean(6, expediente.getAcceso());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar expediente: " + e.getMessage());
        }
        return false;
    }

    // Método para actualizar un expediente existente
    public boolean actualizarExpediente(Expediente expediente) {
        try {
            String query = "UPDATE expedientes SET imagenes=?, textos=?, documentos=?, medicosAcceso=?, tipoAcceso=? WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, expediente.getImagenes());
            statement.setString(2, expediente.getTextos());
            statement.setString(3, expediente.getDocumentos());
            statement.setString(4, expediente.getMedicos());
            statement.setBoolean(5, expediente.getAcceso());
            statement.setInt(6, expediente.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar expediente: " + e.getMessage());
        }
        return false;
    }

    // Método para eliminar un expediente existente
    public boolean eliminarExpediente(int id) {
        try {
            String query = "DELETE FROM expedientes WHERE id=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar expediente: " + e.getMessage());
        }
        return false;
    }

    // Método para obtener todos los expedientes
    public List<Expediente> obtenerExpedientes() {
        List<Expediente> expedientes = new ArrayList<>();
        try {
            String query = "SELECT * FROM expedientes";
            PreparedStatement statement = conexion.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Expediente expediente = new Expediente();
                expediente.setId(result.getInt("id"));
                expediente.setIdPaciente(result.getInt("idPaciente"));
                expediente.setImagenes(result.getString("imagenes"));
                expediente.setTextos(result.getString("textos"));
                expediente.setDocumentos(result.getString("documentos"));
                expediente.setMedicos(result.getString("medicosAcceso"));
                expediente.setAcceso(result.getBoolean("tipoAcceso"));
                expedientes.add(expediente);
            }
            statement.close();
            result.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener expedientes: " + e.getMessage());
        }
        return expedientes;
    }
}