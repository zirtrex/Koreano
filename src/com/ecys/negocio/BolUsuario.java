package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.Usuario;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class BolUsuario {

    private ObservableList<Usuario> olUsuario = FXCollections.observableArrayList();

    private final JdbcConexion jdbcConexion;

    public BolUsuario() {
        jdbcConexion = new JdbcConexion();
    }

    public Usuario fetchUsuario(String nombreUsuario) {
        Usuario usuario = new Usuario();

        try {
            String query = String.format("SELECT * FROM Usuario WHERE nombreUsuario = '%s'", nombreUsuario);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.next()) {
                usuario.setCodUsuario(rs.getString("codUsuario").trim());
                usuario.setCodDetallePersona(rs.getString("codDetallePersona").trim());
                usuario.setNombreUsuario(rs.getString("nombreUsuario").trim());
                usuario.setClaveUsuario(rs.getString("claveUsuario").trim());
                usuario.setRol(rs.getString("rol").trim());
                usuario.setEstadoUsuario(rs.getString("estadoUsuario").trim());

                return usuario;
            }

        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - " + this.getClass())
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

        } catch (Exception ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Crítico")
                    .masthead(this.getClass().toString())
                    .message("Ha Ocurrido un error inesperado. \nDetalles:\n " + ex)
                    .showWarning();
        }
        return usuario;
    }

    public ObservableList<Usuario> fetchTodosUsuarios() {
        Usuario usuario;

        try {
            String query = "SELECT * FROM Usuario";
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            while (rs.next()) {
                usuario = new Usuario();

                usuario.setCodUsuario(rs.getString("codUsuario").trim());
                usuario.setCodDetallePersona(rs.getString("codDetallePersona").trim());
                usuario.setNombreUsuario(rs.getString("nombreUsuario").trim());
                usuario.setClaveUsuario(rs.getString("claveUsuario").trim());
                usuario.setRol(rs.getString("rol").trim());
                usuario.setEstadoUsuario(rs.getString("estadoUsuario").trim());

                olUsuario.add(usuario);
            }
        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - " + this.getClass())
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

        } catch (Exception ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Crítico")
                    .masthead(this.getClass().toString())
                    .message("Ha Ocurrido un error inesperado. \nDetalles:\n " + ex)
                    .showWarning();
        }
        return olUsuario;
    }

    public int insertarUsuario(Usuario usuario) {
        try {
            String query = "INSERT INTO Usuario"
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, usuario.getCodUsuario());
            estado.setString(2, usuario.getCodDetallePersona());
            estado.setString(3, usuario.getNombreUsuario());
            estado.setString(4, usuario.getClaveUsuario());
            estado.setString(5, usuario.getRol());
            estado.setString(6, usuario.getEstadoUsuario());
            return estado.executeUpdate();

        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - " + this.getClass())
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

        } catch (Exception ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Crítico")
                    .masthead(this.getClass().toString())
                    .message("Ha Ocurrido un error inesperado. \nDetalles:\n " + ex)
                    .showWarning();
        }
        return 0;
    }

    public void eliminarUsuario(String _codUsuario) {

        try {
            String query = String.format("UPDATE Usuario SET estadoUsuario = '0' WHERE codUsuario = '%s' ", _codUsuario);
            jdbcConexion.ejecutarUpdate(query);

        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - " + this.getClass())
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

        } catch (Exception ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Crítico")
                    .masthead(this.getClass().toString())
                    .message("Ha Ocurrido un error inesperado. \nDetalles:\n " + ex)
                    .showWarning();
        }
    }

    public int modificarUsuario(Usuario usuario) {
        try {
            String query = "UPDATE Usuario"
                    + "SET codDetallePersona=?, "
                    + "nombreUsuario=?, "
                    + "claveUsuario=?, "
                    + "rol=?, "
                    + "estadoUsuario=? "
                    + "WHERE codUsuario = ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(2, usuario.getCodUsuario());
            estado.setString(3, usuario.getCodDetallePersona());
            estado.setString(4, usuario.getNombreUsuario());
            estado.setString(5, usuario.getClaveUsuario());
            estado.setString(6, usuario.getRol());
            estado.setString(1, usuario.getEstadoUsuario());
            return estado.executeUpdate();

        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - " + this.getClass())
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

        } catch (Exception ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Crítico")
                    .masthead(this.getClass().toString())
                    .message("Ha Ocurrido un error inesperado. \nDetalles:\n " + ex)
                    .showWarning();
        }
        return 0;
    }
}
