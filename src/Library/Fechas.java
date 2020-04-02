/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author avice
 */
public class Fechas {
    
    public String getFecha(JDateChooser jd) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (jd.getDate() != null) {
            return formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    
    public Date StringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaE = null;
        try {
            fechaE = formato.parse(fecha);
            return fechaE;
        } catch (Exception e) {
            return null;
        }
    }

    public String getFechaMySQL(JDateChooser jd) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        if (jd.getDate() != null) {
            return formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    
    public Date StringADateMySQL(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaE = null;
        try {
            fechaE = formato.parse(fecha);
            return fechaE;
        } catch (Exception e) {
            return null;
        }
    }
}

