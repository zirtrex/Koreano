package com.ecys.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author ecys
 */
public class Miscelanea {

    public static String getFechaActual(Boolean fullDate) {

        Calendar gc = new GregorianCalendar();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};
        String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        int segundo = gc.get(Calendar.SECOND);
        int minuto = gc.get(Calendar.MINUTE);
        int hora = gc.get(Calendar.HOUR_OF_DAY);
        int dia = gc.get(Calendar.DAY_OF_MONTH);
        int diaSemana = gc.get(Calendar.DAY_OF_WEEK);
        int mes = gc.get(Calendar.MONTH);
        int año = gc.get(Calendar.YEAR);

        String fecha = fullDate ? "Hoy es:  " + dias[diaSemana - 1] + " " + dia + " " + meses[mes] + " " + año + ",  " + hora + ":" + minuto + ":" + segundo : "Hoy es:  " + dias[diaSemana - 1] + " " + dia + " " + meses[mes] + " " + año;

        return fecha;
    }
}
