package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.Contratista;
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
public class BolContratista {

    private ObservableList<Contratista> olContratista = FXCollections.observableArrayList();
    private final JdbcConexion jdbcConexion;

    public BolContratista() {
        jdbcConexion = new JdbcConexion();
    }

    public Contratista fetchContratista(String codContratista) {
        Contratista contratista = new Contratista();

        try {
            String preQuery = "SELECT C.codContratista, C.nombreEncargado, C.tipoEmpresa, C.estadoContratista, DE.codDetalleEmpresa, DE.razonSocial, DE.ruc, DE.telefono, DE.email, DE.direccion, DE.estadoDetEmp FROM Contratista C INNER JOIN DetalleEmpresa DE ON C.codDetalleEmpresa = DE.codDetalleEmpresa ";

            String query = String.format(preQuery.concat("WHERE C.estadoContratista='1' AND C.codContratista='%s'"), codContratista);

            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.next()) {

                contratista.setCodContratista(rs.getString("codContratista"));
                contratista.setNombreEncargado(rs.getString("nombreEncargado"));
                contratista.setTipoEmpresa(rs.getString("tipoEmpresa").trim());
                contratista.setEstadoContratista(rs.getString("estadoContratista"));

                contratista.setCodDetalleEmpresa(rs.getString("codDetalleEmpresa"));
                contratista.setRazonSocial(rs.getString("razonSocial"));
                contratista.setRuc(rs.getString("ruc"));
                contratista.setTelefono(rs.getString("telefono"));
                contratista.setEmail(rs.getString("email"));
                contratista.setDireccion(rs.getString("direccion"));
                contratista.setEstadoDetEmp(rs.getString("estadoDetEmp"));

                return contratista;
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
        return contratista;
    }

    public ObservableList<Contratista> fetchContratistas(String text, int op) {

        String preQuery = "SELECT C.codContratista, C.nombreEncargado, C.tipoEmpresa, C.estadoContratista, DE.codDetalleEmpresa, DE.razonSocial, DE.ruc, DE.telefono, DE.email, DE.direccion, DE.estadoDetEmp FROM Contratista C INNER JOIN DetalleEmpresa DE ON C.codDetalleEmpresa = DE.codDetalleEmpresa WHERE C.estadoContratista='1' ";
        String condicion = "";

        switch (op) {
            case 1:
                condicion = "AND C.codContratista LIKE '%' || '" + text + "' || '%'";
                break;
            case 2:
                condicion = "AND DE.razonSocial LIKE '%' || '" + text + "' || '%'";
                break;
            case 3:
                condicion = "AND DE.ruc LIKE '%' || '" + text + "' || '%'";
                break;
            default:
                break;
        }

        try {
            String query = preQuery.concat(condicion);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            populateContratista(rs);

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
        return olContratista;
    }
    
    public int insertarContratista(Contratista contratista) {
        try {
            String query = "INSERT INTO Contratista(codContratista, codDetalleEmpresa, nombreEncargado, tipoEmpresa, estadoContratista) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, contratista.getCodContratista());
            estado.setString(2, contratista.getCodDetalleEmpresa());
            estado.setString(3, contratista.getNombreEncargado());
            estado.setString(4, contratista.getTipoEmpresa());
            estado.setString(5, contratista.getEstadoContratista());
            
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

    public int modificarContratista(Contratista contratista) {
        try {
            String query = "UPDATE Contratista "
                    + "SET nombreEncargado=?, "
                    + "tipoEmpresa=?, "
                    + "estadoContratista=? "
                    + "WHERE codContratista = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, contratista.getNombreEncargado());
            estado.setString(2, contratista.getTipoEmpresa());
            estado.setString(3, contratista.getEstadoContratista());
            estado.setString(4, contratista.getCodContratista());

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

    public int eliminarContratista(String codContratista) {
        try {
            String query = "UPDATE Contratista "
                    + "SET "
                    + "estadoContratista='0' "
                    + "WHERE codContratista = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, codContratista);

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

    private void populateContratista(ResultSet rs) throws SQLException {

        Contratista contratista;

        while (rs.next()) {
            contratista = new Contratista();

            contratista.setCodContratista(rs.getString("codContratista"));
            contratista.setNombreEncargado(rs.getString("nombreEncargado"));
            contratista.setTipoEmpresa(rs.getString("tipoEmpresa").trim());
            contratista.setEstadoContratista(rs.getString("estadoContratista"));

            contratista.setCodDetalleEmpresa(rs.getString("codDetalleEmpresa"));
            contratista.setRazonSocial(rs.getString("razonSocial"));
            contratista.setRuc(rs.getString("ruc"));
            contratista.setTelefono(rs.getString("telefono"));
            contratista.setEmail(rs.getString("email"));
            contratista.setDireccion(rs.getString("direccion"));
            contratista.setEstadoDetEmp(rs.getString("estadoDetEmp"));

            olContratista.add(contratista);
        }
    }

    public int getTotalContratistas() {
        int filas = 0;
        try {
            String query = "SELECT * FROM Contratista";
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
