/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author loslolis
 */
public class ModeloExcel {

    public static final String SEPARATOR = " ";

    public String importar(File archivo, JTable tablaD) throws IOException {

        String respuesta = "No se pudo realizar la importación.";
        DefaultTableModel modelo = new DefaultTableModel();
        tablaD.setModel(modelo);
        tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        BufferedReader br = null;
        modelo.addColumn("Nombre");
        modelo.addColumn("Asistencia");
        modelo.addColumn("Hora Entrada");
        modelo.addColumn("Tiempo en clase");
        modelo.addColumn("Hora de salida");
        String[] info = new String[4];
        int i = 0;
        try {

            br = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
            String line = br.readLine();
            while (null != line) {

                String[] fields = line.split(SEPARATOR,20);

                System.out.println(Arrays.toString(fields));

                modelo.addRow(fields);

                line = br.readLine();
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

}
