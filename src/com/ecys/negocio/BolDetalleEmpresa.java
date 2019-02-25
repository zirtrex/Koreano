package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.Contratista;
import com.ecys.entidades.DetalleEmpresa;
import com.ecys.util.Validation;
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
public class BolDetalleEmpresa {

    private ObservableList<DetalleEmpresa> olDetalleEmpresa = FXCollections.observableArrayList();
    private final JdbcConexion jdbcConexion;

    public BolDetalleEmpresa() {
        jdbcConexion = new JdbcConexion();
    }

    public DetalleEmpresa fetchDetalleEmpresa(String _codDetalleEmpresa) {
        DetalleEmpresa detalleEmpresa = new DetalleEmpresa();

        try {
            String query = String.format("SELECT * FROM DetalleEmpresa WHERE estadoDetEmp = '1' AND codDetalleEmpresa='%s'", _codDetalleEmpresa);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.next()) {

                detalleEmpresa.setCodDetalleEmpresa(rs.getString("codDetalleEmpresa"));
                detalleEmpresa.setRazonSocial(rs.getString("razonSocial"));
                detalleEmpresa.setRuc(rs.getString("ruc"));
                detalleEmpresa.setTelefono(rs.getString("telefono"));
                detalleEmpresa.setEmail(rs.getString("email"));
                detalleEmpresa.setDireccion(rs.getString("direccion"));
                detalleEmpresa.setEstadoDetEmp(rs.getString("estadoDetEmp"));

                return detalleEmpresa;
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
        return detalleEmpresa;
    }

    public ObservableList<DetalleEmpresa> fetchDetalleEmpresas(String text, int op) {
        String preQuery = "SELECT * FROM DetallePersona WHERE estadoDetEmp='1' ";
        String condicion = "";

        switch (op) {
            case 1:
                condicion = "AND codDetalleEmpresa LIKE '%' || '" + text + "' || '%'";
                break;
            case 2:
                condicion = "AND razonSocial LIKE '%' || '" + text + "' || '%'";
                break;
            case 3:
                condicion = "AND ruc LIKE '%' || '" + text + "' || '%'";
                break;
            default:
                break;
        }

        try {

            String query = preQuery.concat(condicion);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            populateDetalleEmpresa(rs);

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
        return olDetalleEmpresa;
    }

    public int insertarDetalleEmpresa(Contratista contratista) {
        try {
            String query = "INSERT INTO DetalleEmpresa(codDetalleEmpresa, razonSocial, ruc, telefono, email, direccion, estadoDetEmp) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, contratista.getCodDetalleEmpresa());
            estado.setString(2, contratista.getRazonSocial());
            estado.setString(3, contratista.getRuc());
            estado.setString(4, contratista.getTelefono());
            estado.setString(5, contratista.getEmail());
            estado.setString(6, contratista.getDireccion());
            estado.setString(7, contratista.getEstadoDetEmp());

            return estado.executeUpdate();

        } catch (SQLException ex) {
            Validation validation = new Validation(ex.getMessage(), ex.getErrorCode());
            Dialogs.create()
                    .owner(null)
                    .title("Advertencia")
                    .masthead(null)
                    .message(validation.getErrorMessage())
                    .showWarning();

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

    public int modificarDetalleEmpresa(DetalleEmpresa detalleEmpresa) {
        try {
            String query = "UPDATE DetalleEmpresa "
                    + "SET razonSocial=?, "
                    + "ruc=?, "
                    + "telefono=?, "
                    + "email=?, "
                    + "direccion=?, "
                    + "estadoDetEmp=? "
                    + "WHERE codDetalleEmpresa = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);

            estado.setString(1, detalleEmpresa.getRazonSocial());
            estado.setString(2, detalleEmpresa.getRuc());
            estado.setString(3, detalleEmpresa.getTelefono());
            estado.setString(4, detalleEmpresa.getEmail());
            estado.setString(5, detalleEmpresa.getDireccion());
            estado.setString(6, detalleEmpresa.getEstadoDetEmp());
            estado.setString(7, detalleEmpresa.getCodDetalleEmpresa());

            return estado.executeUpdate();

        } catch (SQLException ex) {
            Validation validation = new Validation(ex.getMessage(), ex.getErrorCode());
            Dialogs.create()
                    .owner(null)
                    .title("Advertencia")
                    .masthead(null)
                    .message(validation.getErrorMessage())
                    .showWarning();

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

    public int eliminarDetalleEmpresa(String codDetalleEmpresa) {
        try {
            String query = "UPDATE DetalleEmpresa "
                    + "SET "
                    + "estadoDetEmp='0' "
                    + "WHERE codDetalleEmpresa = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, codDetalleEmpresa);

            return estado.executeUpdate();

        } catch (SQLException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - "  + this.getClass())
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

    private void populateDetalleEmpresa(ResultSet rs) throws SQLException {

        DetalleEmpresa detalleEmpresa;

        while (rs.next()) {
            detalleEmpresa = new DetalleEmpresa();

            detalleEmpresa.setCodDetalleEmpresa(rs.getString("codDetalleEmpresa"));
            detalleEmpresa.setRazonSocial(rs.getString("razonSocial"));
            detalleEmpresa.setRuc(rs.getString("ruc"));
            detalleEmpresa.setTelefono(rs.getString("telefono"));
            detalleEmpresa.setEmail(rs.getString("email"));
            detalleEmpresa.setDireccion(rs.getString("direccion"));
            detalleEmpresa.setEstadoDetEmp(rs.getString("estadoDetEmp"));

            olDetalleEmpresa.add(detalleEmpresa);
        }
    }

    public int getTotalDetalleEmpresa() {
        int filas = 0;
        try {
            String query = "SELECT * FROM DetalleEmpresa";
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
