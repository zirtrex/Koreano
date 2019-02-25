package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.DetallePersona;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class BolDetallePersona {

    private ObservableList<DetallePersona> olDetallePersona = FXCollections.observableArrayList();
    private final JdbcConexion jdbcConexion;

    public BolDetallePersona() {
            jdbcConexion = new JdbcConexion();
    }

    public DetallePersona fetchDetallePersona(String _codDetallePersona) {
        DetallePersona detallePersona = new DetallePersona();

        try {
            String query = "SELECT * FROM DetallePersona WHERE  codDetallePersona=" + _codDetallePersona;
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.first()) {
                detallePersona.setCodDetallePersona(rs.getString("codDetallePersona").trim());
                detallePersona.setNombres(rs.getString("nombres").trim());
                detallePersona.setApellidos(rs.getString("apellidos").trim());
                detallePersona.setDireccion(rs.getString("direccion").trim());
                detallePersona.setSexo(rs.getString("sexo").trim());
                detallePersona.setTelefono(rs.getString("telefono").trim());
                detallePersona.setEmail(rs.getString("email").trim());
                detallePersona.setFechaIngreso(rs.getString("fechaIngreso").trim());
                detallePersona.setExperiencia(rs.getString("experiencia").trim());
                detallePersona.setEstadoDetPer(rs.getString("estadoDetPer").trim());

                return detallePersona;
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
        return detallePersona;
    }

    public ObservableList<DetallePersona> fetchDetallePersonas() {
        DetallePersona detallePersona;

        try {
            String query = "SELECT * FROM DetallePersona";
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            while (rs.next()) {
                detallePersona = new DetallePersona();

                detallePersona.setCodDetallePersona(rs.getString("codDetallePersona").trim());
                detallePersona.setNombres(rs.getString("nombres").trim());
                detallePersona.setApellidos(rs.getString("apellidos").trim());
                detallePersona.setDireccion(rs.getString("direccion").trim());
                detallePersona.setSexo(rs.getString("sexo").trim());
                detallePersona.setTelefono(rs.getString("telefono").trim());
                detallePersona.setEmail(rs.getString("email").trim());
                detallePersona.setFechaIngreso(rs.getString("fechaIngreso").trim());
                detallePersona.setExperiencia(rs.getString("experiencia").trim());
                detallePersona.setEstadoDetPer(rs.getString("estadoDetPer").trim());

                olDetallePersona.add(detallePersona);
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
        return olDetallePersona;
    }
}
