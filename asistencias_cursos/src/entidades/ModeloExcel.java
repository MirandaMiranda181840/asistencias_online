/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author loslolis
 */
public class ModeloExcel {

    public File archivo;
     BufferedReader br = null;
     private ArrayList <Asistencia> asistencias;
     Conexion conn = new Conexion();
     
    public ModeloExcel(){
    this.asistencias=new ArrayList <Asistencia>();
}
    public String importar(File archivo, JTable tablaD,JLabel labelFecha) throws IOException {

        String respuesta = "No se pudo realizar la importación.";
        DefaultTableModel modelo = new DefaultTableModel();
        tablaD.setModel(modelo);
        //tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        modelo.addColumn("Nombre");
        modelo.addColumn("Asistencia");
        modelo.addColumn("Hora Entrada");
        modelo.addColumn("Tiempo en clase");
        modelo.addColumn("Hora de salida");
        String[] info = new String[4];
        this.archivo=archivo;
        int i = 0;
        int contadorLineasFile=0;
        String fecha="";
        Asistencia asistencia;
        try {
         
            br = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
            String line = br.readLine();
            
            while (null != line) {
                //para obtener la fecha
                if(contadorLineasFile==2){
                    String encabezado[]=line.split(",");
                    fecha=encabezado[1];
                }
                if(contadorLineasFile>3){
                    //splitea el nombre y la asistencia, pero las horas aun quedan pegadas
                String[] firstSplit = line.split(",");
               //splitea las horas, en valores individuales (hora entrada, duracion, salida)
                    
               if(firstSplit.length == 1){                  
                    labelFecha.setText(fecha);
                    String[] row2={firstSplit[0], "", "", "", "",fecha};   
                    System.out.println(Arrays.toString(row2));
                    modelo.addRow(row2);
               }else{
                    String [] secondSplit=splitData(firstSplit[2]);
                    String[] row={firstSplit[0], firstSplit [1], secondSplit [0], secondSplit [1], secondSplit [2]};   
                    agregarCurso(firstSplit[0], firstSplit [1], secondSplit [0], secondSplit [1], secondSplit [2],fecha);
                     System.out.println(Arrays.toString(row));
                     labelFecha.setText(fecha);
                     modelo.addRow(row);
               }          
              
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
     
   
    private void agregarCurso(String nombre, String asistir, String horaLlegada, String duracion, String horaSalida ,String fecha){
        boolean asistio;
        if(asistir.contains("✔")){
            asistio=true;
        }else{
            asistio=false;
        }
        Asistencia asistencia= new Asistencia(nombre, horaLlegada, duracion, horaSalida, fecha ,asistio);
        asistencias.add(asistencia);
    }
    //aqui se guardara en la bdd
    public void guardar(Connection conexion, String idCurso, String unidadIndice, String unidadNombre){
         PreparedStatement consulta;
        for (int i = 0; i < asistencias.size(); i++) {
            asistencias.get(i).setIdCurso(idCurso);
            asistencias.get(i).setUnidadIndice(unidadIndice);
            asistencias.get(i).setUnidadNombre(unidadNombre);
            try{
                consulta=(PreparedStatement) conexion.prepareStatement("INSERT INTO asistencias(idCurso, nombre, horaLlegada, duracion, horaSalida, asistencia, fecha, unidadIndice, unidadNombre) VALUES (?,?,?,?,?,?,?,?,?);");
                consulta.setInt(1,Integer.parseInt(idCurso) );
                consulta.setString(2,asistencias.get(i).getNombre());
                consulta.setString(3,asistencias.get(i).getHoraLlegada());
                consulta.setString(4,asistencias.get(i).getDuracion());
                consulta.setString(5,asistencias.get(i).getHoraSalida());
                consulta.setBoolean(6,asistencias.get(i).isAsistencia());
                consulta.setString(7, asistencias.get(i).getFecha());
                consulta.setInt(8, Integer.parseInt(asistencias.get(i).getUnidadIndice()));
                consulta.setString(9, asistencias.get(i).getUnidadNombre());
                consulta.executeUpdate();
            
            } catch (SQLException ex) {
                 Logger.getLogger(ModeloExcel.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }

    
    public void guardarNuevaAsistenciaAlumno(Connection conexion, Asistencia asistencia){
         PreparedStatement consulta;
    
            try{
                consulta=(PreparedStatement) conexion.prepareStatement("INSERT INTO asistencias (idCurso,nombre,horaLlegada,duracion,horaSalida,asistencia,fecha, unidadIndice, unidadNombre) VALUES(?,?,?,?,?,?,?,?,?);");
                consulta.setInt(1,Integer.parseInt(asistencia.getIdCurso()) );
                consulta.setString(2,asistencia.getNombre());
                consulta.setString(3,asistencia.getHoraLlegada());
                consulta.setString(4,asistencia.getDuracion());
                consulta.setString(5,asistencia.getHoraSalida());
                consulta.setBoolean(6,asistencia.isAsistencia());
                consulta.setString(7, asistencia.getFecha());
                consulta.setInt(8, Integer.parseInt(asistencia.getUnidadIndice()));
                consulta.setString(9, asistencia.getUnidadNombre());
                consulta.executeUpdate();
            
            } catch (SQLException ex) {
                 Logger.getLogger(ModeloExcel.class.getName()).log(Level.SEVERE, null, ex);
             }
        
    }
}
