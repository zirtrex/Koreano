package com.ecys.negocio;

import com.ecys.datos.JdbcConexion;
import com.ecys.entidades.Equipo;
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
public class BolEquipo {

    private ObservableList<Equipo> olEquipo = FXCollections.observableArrayList();
    private final JdbcConexion jdbcConexion;

    public BolEquipo() {
        jdbcConexion = new JdbcConexion();
    }

    public Equipo fetchEquipo(String codEquipo) {
        Equipo equipo = new Equipo();

        try {

            String query = String.format("SELECT * FROM Equipo WHERE estadoEquipo = '1' AND codEquipo='%s'", codEquipo);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);

            if (rs.next()) {

                equipo.setCodEquipo(rs.getString("codEquipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setMarca(rs.getString("marca"));
                equipo.setModelo(rs.getString("modelo"));
                equipo.setPlaca(rs.getString("placa"));
                equipo.setCategoria(rs.getString("categoria"));
                equipo.setHorometro(rs.getDouble("horometro"));
                equipo.setKilometraje(rs.getInt("kilometraje"));
                equipo.setAnioFabricacion(rs.getString("anioFabricacion"));
                equipo.setOperativo(rs.getString("operativo"));
                equipo.setObservaciones(rs.getString("observaciones"));
                equipo.setDescripcion(rs.getString("descripcion"));
                equipo.setEstadoEquipo(rs.getString("estadoEquipo"));

                return equipo;
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
        return equipo;
    }

    public ObservableList<Equipo> fetchEquipos(String text, int op) {
        String preQuery = "SELECT * FROM Equipo WHERE estadoEquipo = '1' ";
        String condicion = "";

        switch (op) {
            case 1:
                condicion = "AND codEquipo LIKE '%' || '" + text + "' || '%'";
                break;
            case 2:
                condicion = "AND nombre LIKE '%' || '" + text + "' || '%'";
                break;
            case 3:
                condicion = "AND marca LIKE '%' || '" + text + "' || '%'";
                break;
            default:
                break;
        }

        try {
            String query = preQuery.concat(condicion);
            ResultSet rs = jdbcConexion.ejecutarConsulta(query);
            populateEquipo(rs);

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
        return olEquipo;
    }

    public int insertarEquipo(Equipo equipo) {
        try {
            String query = "INSERT INTO Equipo(codEquipo, nombre, marca, modelo, placa, categoria, horometro, kilometraje, anioFabricacion, operativo, observaciones, descripcion, estadoEquipo) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            
            estado.setString(1, equipo.getCodEquipo());
            estado.setString(2, equipo.getNombre());
            estado.setString(3, equipo.getMarca());
            estado.setString(4, equipo.getModelo());
            estado.setString(5, equipo.getPlaca());
            estado.setString(6, equipo.getCategoria());
            estado.setDouble(7, equipo.getHorometro());
            estado.setInt(8, equipo.getKilometraje());
            estado.setString(9, equipo.getAnioFabricacion());
            estado.setString(10, equipo.getOperativo());
            estado.setString(11, equipo.getObservaciones());
            estado.setString(12, equipo.getDescripcion());
            estado.setString(13, equipo.getEstadoEquipo());
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

    public int modificarEquipo(Equipo equipo) {
        try {
            String query = "UPDATE Equipo "
                    + "SET nombre=?, "
                    + "marca=?, "
                    + "modelo=?, "
                    + "placa=?, "
                    + "categoria=?, "
                    + "horometro=?, "
                    + "kilometraje=?, "
                    + "anioFabricacion=?, "
                    + "operativo=?, "
                    + "observaciones=?, "
                    + "descripcion=?, "
                    + "estadoEquipo=? "
                    + "WHERE codEquipo = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            
            estado.setString(1, equipo.getNombre());
            estado.setString(2, equipo.getMarca());
            estado.setString(3, equipo.getModelo());
            estado.setString(4, equipo.getPlaca());
            estado.setString(5, equipo.getCategoria());
            estado.setDouble(6, equipo.getHorometro());
            estado.setInt(7, equipo.getKilometraje());
            estado.setString(8, equipo.getAnioFabricacion());
            estado.setString(9, equipo.getOperativo());
            estado.setString(10, equipo.getObservaciones());
            estado.setString(11, equipo.getDescripcion());
            estado.setString(12, equipo.getEstadoEquipo());
            estado.setString(13, equipo.getCodEquipo());

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

    public int eliminarEquipo(String _codEquipo) {
        try {
            String query = "UPDATE Equipo "
                    + "SET "
                    + "estadoEquipo='0' "
                    + "WHERE codEquipo = ?";

            PreparedStatement estado = jdbcConexion.getConeccion().prepareStatement(query);
            estado.setString(1, _codEquipo);

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

    private void populateEquipo(ResultSet rs) throws SQLException {

        Equipo equipo;

        while (rs.next()) {
            equipo = new Equipo();

            equipo.setCodEquipo(rs.getString("codEquipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setMarca(rs.getString("marca"));
            equipo.setModelo(rs.getString("modelo"));
            equipo.setPlaca(rs.getString("placa"));
            equipo.setCategoria(rs.getString("categoria"));
            equipo.setHorometro(rs.getDouble("horometro"));
            equipo.setKilometraje(rs.getInt("kilometraje"));
            equipo.setAnioFabricacion(rs.getString("anioFabricacion"));
            equipo.setOperativo(rs.getString("operativo"));
            equipo.setObservaciones(rs.getString("observaciones"));
            equipo.setDescripcion(rs.getString("descripcion"));
            equipo.setEstadoEquipo(rs.getString("estadoEquipo"));

            olEquipo.add(equipo);
        }
    }

    public int getTotalEquipos() {
        int filas = 0;
        try {
            String query = "SELECT * FROM Equipo";
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
