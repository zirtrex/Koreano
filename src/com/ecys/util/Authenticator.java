package com.ecys.util;

import com.ecys.entidades.Usuario;
import com.ecys.negocio.BolUsuario;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;

public class Authenticator {

    private static final Map<String, String> USERS = new HashMap<String, String>();

    static {
        BolUsuario bolUsuario = new BolUsuario();
        ObservableList<Usuario> usuarios = bolUsuario.fetchTodosUsuarios();
        int totalUsuarios = usuarios.size();
        for (int i = 0; i < totalUsuarios; i++) {
            USERS.put(usuarios.get(i).getNombreUsuario(), usuarios.get(i).getClaveUsuario());
        }
    }

    public static boolean validate(String usuario, String clave) {
        String validUserPassword = USERS.get(usuario);
        return validUserPassword != null && validUserPassword.equals(clave);
    }
}
