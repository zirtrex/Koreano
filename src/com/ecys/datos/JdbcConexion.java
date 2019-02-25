package com.ecys.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OracleDriver;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author ecys
 */
public class JdbcConexion {

    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
    private static final String USER = "Koreano";
    private static final String PASS = "Koreano";

    private Connection conexion;
    private Statement sentencia;
    private String query;
    private ResultSet resultado;

    public JdbcConexion() {
        try {
            DriverManager.registerDriver(new OracleDriver());
            conexion = DriverManager.getConnection(URL, USER, PASS);
            sentencia = conexion.createStatement();

        } catch (SQLException ex) {
            
            String errorMessage = "";
            
            switch(ex.getErrorCode()){
                case 17002:
                    errorMessage = "No se puede establecer una conexión con el servidor";
                    errorMessage = errorMessage.concat("\nAsegúrese que Oracle XE está instalado y sé está ejecutando.");
                    break;
                default:
                    errorMessage = ex.getMessage();
                    break;
            }
            
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - La Aplicación se cerrará")
                    .masthead("Código de error: " + ex.getErrorCode())
                    .message("Detalles: \n" + errorMessage)
                    .showError();
            
            System.exit(0);

        } catch (NullPointerException ex) {
            Dialogs.create()
                    .owner(null)
                    .title("Error Grave - La Aplicación se cerrará")
                    .masthead(null)
                    .message("Ha ocurrido un error inesperado: \n" + ex)
                    .showError();
            
            System.exit(0);
        }
    }

    public Connection getConeccion() {
        return conexion;
    }

    public ResultSet ejecutarConsulta(String q) throws SQLException, Exception {
        query = q;
        resultado = sentencia.executeQuery(query);
        return resultado;
    }

    public int ejecutarUpdate(String q) throws SQLException, Exception {
        query = q;
        return sentencia.executeUpdate(query);
    }

    public void Cerrar() throws SQLException, Exception {
        conexion.close();
    }
}
