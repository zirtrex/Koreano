package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.DetalleAlquiler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class BolDetalleAlquiler {

    private ObservableList<DetalleAlquiler> olDetalleAlquiler = FXCollections.observableArrayList();

    private final JdbcConexion jdbcConexion;

    public BolDetalleAlquiler() {
        jdbcConexion = new JdbcConexion();
    }

    public DetalleAlquiler fetchDetalleAlquiler(String codDetalleAlquiler) {
        DetalleAlquiler detalleAlquiler = new DetalleAlquiler();

        try {
            String query = String.format("SELECT * FROM DetalleAlquiler WHERE codDetalleAlquiler='%s'", codDetalleAlquiler);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.next()) {
                detalleAlquiler.setCodDetalleAlquiler(rs.getString("codDetalleAlquiler"));
                detalleAlquiler.setCodAlquiler(rs.getString("codAlquiler"));
                detalleAlquiler.setCodEquipo(rs.getString("codEquipo"));
                detalleAlquiler.setPrecio(rs.getDouble("precio"));
                detalleAlquiler.setHoras(rs.getDouble("horas"));
                detalleAlquiler.setSubTotal(rs.getDouble("subTotal"));
                detalleAlquiler.setEstadoDetAlq(rs.getString("estadoDetAlq"));

                return detalleAlquiler;
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
        return detalleAlquiler;
    }

    public ObservableList<DetalleAlquiler> fetchDetalleAlquilerByCodAlquiler(String codAlquiler) {

        try {
            String query = String.format("SELECT DA.*, E.Nombre FROM DetalleAlquiler DA INNER JOIN Equipo E ON DA.codEquipo = E.codEquipo WHERE DA.codAlquiler='%s'", codAlquiler);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            populateDetalleAlquiler(rs);

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
        return olDetalleAlquiler;
    }

    public int insertarDetalleAlquiler(DetalleAlquiler detalleAlquiler) {
        try {
            String query = "INSERT INTO DetalleAlquiler(codDetalleAlquiler, codAlquiler, codEquipo, precio, horas, subTotal, estadoDetAlq) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, detalleAlquiler.getCodDetalleAlquiler());
            estado.setString(2, detalleAlquiler.getCodAlquiler());
            estado.setString(3, detalleAlquiler.getCodEquipo());
            estado.setDouble(4, detalleAlquiler.getPrecio());
            estado.setDouble(5, detalleAlquiler.getHoras());
            estado.setDouble(6, detalleAlquiler.getSubTotal());
            estado.setString(7, detalleAlquiler.getEstadoDetAlq());

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

    public int modificarDetalleAlquiler(DetalleAlquiler detalleAlquiler) {
        try {
            String query = "UPDATE DetalleAlquiler "
                    + "SET codAlquiler=?, "
                    + "codEquipo=?, "
                    + "precio=?, "
                    + "horas=?, "
                    + "subTotal=?, "
                    + "estadoDetAlq=? "
                    + "WHERE codDetalleAlquiler = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);

            estado.setString(1, detalleAlquiler.getCodAlquiler());
            estado.setString(2, detalleAlquiler.getCodEquipo());
            estado.setDouble(3, detalleAlquiler.getPrecio());
            estado.setDouble(4, detalleAlquiler.getHoras());
            estado.setDouble(5, detalleAlquiler.getSubTotal());
            estado.setString(6, detalleAlquiler.getEstadoDetAlq());
            estado.setString(7, detalleAlquiler.getCodDetalleAlquiler());

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

    public int eliminarDetalleAlquiler(String codDetalleAlquiler) {
        try {
            String query = "UPDATE DetalleAlquiler "
                    + "SET "
                    + "estadoDetAlq='0' "
                    + "WHERE codDetalleAlquiler = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, codDetalleAlquiler);

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

    private void populateDetalleAlquiler(ResultSet rs) throws SQLException {

        DetalleAlquiler detalleAlquiler;

        while (rs.next()) {
            detalleAlquiler = new DetalleAlquiler();

            detalleAlquiler.setCodDetalleAlquiler(rs.getString("codDetalleAlquiler"));
            detalleAlquiler.setCodAlquiler(rs.getString("codAlquiler"));
            detalleAlquiler.setCodEquipo(rs.getString("codEquipo"));
            detalleAlquiler.setNombreEquipo(rs.getString("nombre"));
            detalleAlquiler.setPrecio(rs.getDouble("precio"));
            detalleAlquiler.setHoras(rs.getDouble("horas"));
            detalleAlquiler.setSubTotal(rs.getDouble("subTotal"));
            detalleAlquiler.setEstadoDetAlq(rs.getString("estadoDetAlq"));

            olDetalleAlquiler.add(detalleAlquiler);
        }
    }

    public int getTotalDetalleAlquiler() {
        int filas = 0;
        try {
            String query = "SELECT * FROM DetalleAlquiler";
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            while (rs.next()) {
                filas++;
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
        return filas;
    }
}
