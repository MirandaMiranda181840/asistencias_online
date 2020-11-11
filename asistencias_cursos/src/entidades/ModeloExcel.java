/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author loslolis
 */
public class ModeloExcel {

    public File archivo;
     BufferedReader br = null;
    public static final String SEPARATOR = ",";

    public String importar(File archivo, JTable tablaD) throws IOException {

        String respuesta = "No se pudo realizar la importación.";
        DefaultTableModel modelo = new DefaultTableModel();
        tablaD.setModel(modelo);
        tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        modelo.addColumn("Nombre");
        modelo.addColumn("Asistencia");
        modelo.addColumn("Hora Entrada");
        modelo.addColumn("Tiempo en clase");
        modelo.addColumn("Hora de salida");
        String[] info = new String[4];
        this.archivo=archivo;
        int i = 0;
        int contadorLineasFile=0;
        try {
         
            br = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
            String line = br.readLine();
            while (null != line) {
                if(contadorLineasFile>3){
                    //splitea el nombre y la asistencia, pero las horas aun quedan pegadas
                String[] firstSplit = line.split(",");
               //splitea las horas, en valores individuales (hora entrada, duracion, salida)
                String [] seconSplit=splitData(firstSplit[2]);
                String[] row={firstSplit[0], firstSplit [1], seconSplit [0], seconSplit [1], seconSplit [2]};   
                System.out.println(Arrays.toString(row));
                modelo.addRow(row);
                }
                
                line = br.readLine();
                contadorLineasFile++;
            }
            respuesta = "importación exitosa";
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (null != br) {
                br.close();
            }
            return respuesta;

        }

    }
    
    
    //para splitear las horas
    private String[] splitData(String line){
        
       String horaLlegada, duracion, horaSalida;
       String splitHoraLlegada[]=line.split("\\(",2);
       horaLlegada=splitHoraLlegada[0];
       String splitDuracion[]=splitHoraLlegada[1].split("\\[",2);
       duracion=removeLastChar(splitDuracion[0]);
       horaSalida=removeLastChar(splitDuracion[1]);
       String horas[]={horaLlegada,duracion,horaSalida};
           return horas;
       }
    // estos dos para borar los parentesis y corchetes de las horas
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
     
    
    //aqui se guardara en la bdd
    public void guardar(int idCurso){
        //leer todo lo que esta en la tabla e interpretar cada uno como un objeto de tipo "asistencia"
        //posteriormente guardarlo en la bd
    }

}
