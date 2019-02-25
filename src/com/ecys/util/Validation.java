package com.ecys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ecys
 */
public class Validation {

    private static Pattern pattern;
    private static Matcher matcher;
    private String string;
    private int errorCode;
    private String error;
    private String errorMessage;
    public static final String UNQ_TEL = "UNQ_TEL";
    public static final String UNQ_EMAIL = "UNQ_EMAIL";
    public static final String UNQ_NOMUSU = "UNQ_NOMUSU";
    public static final String UNQ_NUMLICENCIA = "UNQ_NUMLICENCIA";
    public static final String UNQ_RAZSOC = "UNQ_RAZSOC";
    public static final String UNQ_RUC = "UNQ_RUC";
    public static final String UNQ_TELE = "UNQ_TELE";
    public static final String UNQ_EMAILE = "UNQ_EMAILE";
    public static final String UNQ_NOMREP = "UNQ_NOMREP";

    public Validation() {
    }

    public Validation(String string, int errorCode) {
        this.string = string;
        this.errorCode = errorCode;
    }

    private void composerError() {
        if (errorCode == 1) {
            int in = string.indexOf(".");
            int out = string.indexOf(")");
            error = string.substring(in + 1, out);
        } else if (errorCode == 12899) {
            String[] temp1 = string.split("\\.");
            String[] temp2 = temp1[2].split("\"");
            error = "El Campo " + temp2[1] + ", Sobrepasa su valor " + temp2[2];
        } else if (errorCode == 2290) {
            int in = string.indexOf(".");
            int out = string.indexOf(")");
            error = string.substring(in + 1, out);
        } else {
            error = string;
        }
    }

    public String getErrorMessage() {
        composerError();
        switch (error) {
            case Validation.UNQ_TEL:
                errorMessage = "El Teléfono ya existe";
                break;
            case Validation.UNQ_EMAIL:
                errorMessage = "El correo ya existe";
                break;
            case Validation.UNQ_NOMUSU:
                errorMessage = "El usuario ya existe";
                break;
            case Validation.UNQ_NUMLICENCIA:
                errorMessage = "El nombre número de licencia ya existe";
                break;
            case Validation.UNQ_RAZSOC:
                errorMessage = "Razón Social/Nombres ya existe";
                break;
            case Validation.UNQ_RUC:
                errorMessage = "Ruc/Dni ya existe";
                break;
            case Validation.UNQ_TELE:
                errorMessage = "El Teléfono ya existe";
                break;
            case Validation.UNQ_EMAILE:
                errorMessage = "El correo ya existe";
                break;
            case Validation.UNQ_NOMREP:
                errorMessage = "El nombre nombre de repuesto ya existe";
                break;
            default:
                errorMessage = error;
                break;
        }
        return errorMessage;
    }

    public static boolean validateVoidFields(String data) {

        return data.isEmpty();
    }

    public static boolean validateRange(String data, int min, int max) {

        if (!data.isEmpty()) {
            if (data.length() >= min || data.length() <= max) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateEmail(String email) {

        pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}$");
        matcher = pattern.matcher(email);

        if (!email.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateNumberPhone(String data) {

        pattern = Pattern.compile("^[1-9]{1}[0-9]{8}$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean validateRUC(String data) {

        pattern = Pattern.compile("^[1-2]{1}[0-9]{10}$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean validateDNI(String data) {

        pattern = Pattern.compile("^[1-9]{1}[0-9]{7}$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean validateYear(String data) {

        pattern = Pattern.compile("^[1-2]{1}[0-9]{3}$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateStringOnly(String data) {

        pattern = Pattern.compile("^[a-zA-Z]*$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateNumberOnly(String data) {

        pattern = Pattern.compile("^[0-9]*$");
        matcher = pattern.matcher(data);
        if (!data.isEmpty()) {
            if (!matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateIntegerFormat(String data) {
        if (data != null) {
            if (data.length() != 0) {
                try {
                    Integer.parseInt(data);
                } catch (NumberFormatException e) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateDecimalFormat(String data) {
        if (data == null || data.length() == 0) {
            return false;
        } else {
            try {
                Double.parseDouble(data);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }
}
