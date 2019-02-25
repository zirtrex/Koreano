package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.Alquiler;
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
public class BolAlquiler {

    private ObservableList<Alquiler> olAlquiler = FXCollections.observableArrayList();

    private final JdbcConexion jdbcConexion;

    public BolAlquiler() {
        jdbcConexion = new JdbcConexion();
    }

    public Alquiler fetchAlquiler(String _codAlquiler) {
        Alquiler alquiler = new Alquiler();
        try {
            String query = String.format("SELECT * FROM Alquiler WHERE codAlquiler='%s'", _codAlquiler);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            while (rs.next()) {

                alquiler.setCodAlquiler(rs.getString("codAlquiler"));
                alquiler.setCodContratista(rs.getString("codContratista"));
                alquiler.setCodUsuario(rs.getString("codUsuario"));
                alquiler.setTipoDocumento(rs.getString("tipoDocumento"));
                alquiler.setNumeroSerie(Integer.parseInt(rs.getString("numeroSerie")));
                alquiler.setNumeroDocumento(Integer.parseInt(rs.getString("numeroDocumento")));
                alquiler.setSubTotal(rs.getDouble("subTotal"));
                alquiler.setIgv(rs.getDouble("igv"));
                alquiler.setTotal(rs.getDouble("total"));
                alquiler.setEstadoAlquiler(rs.getString("estadoAlquiler"));

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
        return alquiler;
    }

    public ObservableList<Alquiler> fetchAlquileres(String text, int op) {

        String preQuery = "SELECT DE.razonSocial, A.* FROM Alquiler A INNER JOIN Contratista C ON A.codContratista = C.codContratista INNER JOIN DetalleEmpresa DE ON C.codDetalleEmpresa = DE.codDetalleEmpresa WHERE estadoAlquiler = '1'";
        String condicion = "";

        switch (op) {
            case 1:
                condicion = "AND A.codAlquiler LIKE '%' || '" + text + "' || '%'";
                break;
            case 2:
                condicion = "AND DE.razonSocial LIKE '%' || '" + text + "' || '%'";
                break;
            default:
                break;
        }

        try {
            String query = preQuery.concat(condicion);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            populateAlquiler(rs);

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
        return olAlquiler;
    }

    public int insertarAlquiler(Alquiler alquiler) {
        try {
            String query = "INSERT INTO Alquiler(codAlquiler, codContratista, codUsuario, fecha, tipoDocumento, numeroSerie, numeroDocumento, subTotal, igv, total, estadoAlquiler) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);

            estado.setString(1, alquiler.getCodAlquiler());
            estado.setString(2, alquiler.getCodContratista());
            estado.setString(3, alquiler.getCodUsuario());
            estado.setDate(4, alquiler.getFecha());
            estado.setString(5, alquiler.getTipoDocumento());
            estado.setInt(6, alquiler.getNumeroSerie());
            estado.setInt(7, alquiler.getNumeroDocumento());
            estado.setDouble(8, alquiler.getSubTotal());
            estado.setDouble(9, alquiler.getIgv());
            estado.setDouble(10, alquiler.getTotal());
            estado.setString(11, alquiler.getEstadoAlquiler());

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

    public int modificarAlquiler(Alquiler alquiler) {
        try {
            String query = "UPDATE Alquiler "
                    + "SET codContratista=?, "
                    + "codUsuario=?, "
                    + "fecha=?, "
                    + "tipoDocumento=?, "
                    + "numeroSerie=?, "
                    + "numeroDocumento=?, "
                    + "subTotal=?, "
                    + "igv=?, "
                    + "total=?, "
                    + "estadoAlquiler=? "
                    + "WHERE codAlquiler = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);

            estado.setString(1, alquiler.getCodContratista());
            estado.setString(2, alquiler.getCodUsuario());
            estado.setDate(3, alquiler.getFecha());
            estado.setString(4, alquiler.getTipoDocumento());
            estado.setInt(5, alquiler.getNumeroSerie());
            estado.setInt(6, alquiler.getNumeroDocumento());
            estado.setDouble(7, alquiler.getSubTotal());
            estado.setDouble(8, alquiler.getIgv());
            estado.setDouble(9, alquiler.getTotal());
            estado.setString(10, alquiler.getEstadoAlquiler());
            estado.setString(11, alquiler.getCodAlquiler());

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

    public int eliminarAlquiler(String codAlquiler) {
        try {
            String query = "UPDATE Alquiler "
                    + "SET "
                    + "estadoAlquiler='0' "
                    + "WHERE codAlquiler = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, codAlquiler);

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

    private void populateAlquiler(ResultSet rs) throws SQLException {

        Alquiler alquiler;

        while (rs.next()) {
            alquiler = new Alquiler();

            alquiler.setCodAlquiler(rs.getString("codAlquiler"));
            alquiler.setCodContratista(rs.getString("codContratista"));
            alquiler.setContratista(rs.getString("razonSocial"));
            alquiler.setCodUsuario(rs.getString("codUsuario"));
            alquiler.setTipoDocumento(rs.getString("tipoDocumento"));
            alquiler.setNumeroSerie(rs.getInt("numeroSerie"));
            alquiler.setNumeroDocumento(rs.getInt("numeroDocumento"));
            alquiler.setSubTotal(rs.getDouble("subTotal"));
            alquiler.setIgv(rs.getDouble("igv"));
            alquiler.setTotal(rs.getDouble("total"));
            alquiler.setEstadoAlquiler(rs.getString("estadoAlquiler"));

            olAlquiler.add(alquiler);
        }
    }

    public int getTotalAlquiler() {
        int filas = 0;
        try {
            String query = "SELECT * FROM Alquiler";
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            while (rs.next()) {
                filas++;
            }
        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - La Aplicación se cerrará")
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + ex)
                    .showError();

            System.exit(0);

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
