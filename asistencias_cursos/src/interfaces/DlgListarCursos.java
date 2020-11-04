/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgListarCursos extends javax.swing.JDialog {

    
     private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private JTable tabla = null;
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    
    /**
     * Creates new form DlgListarCursos
     * @param parent
     * @param modal
     */
    public DlgListarCursos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
         try {
             leerDatos();
             llenarTabla();
         } catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(DlgListarCursos.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
     /**
     * Obtenemos todos los datos de la tabla juegos;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void leerDatos() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM cursos";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultados = comando.executeQuery(instruccion);
    }
    
    private void llenarTabla(){
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Nombre", "Periodo", "Dias", "Hora"};
        tabla = new JTable();
        modelo = new DefaultTableModel();
        desplazamiento = new JScrollPane(tabla);
        int id;
        String nombre, periodo, dias, hora;
        
        // Parametros de la ventana
        this.setTitle("Cursos registrados");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        modelo.setColumnIdentifiers(columnas);
        
         // Barras de desplazamiento
        desplazamiento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        desplazamiento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Propiedades de la tabla
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);               
        tabla.setModel(modelo);
        
        // Agregando elementos a la ventana
        this.getContentPane().add(desplazamiento, BorderLayout.NORTH);    
        this.pack();
        
        // Ponemos los datos en la tabla
        
        try {
            // Obtener datos de la tabla
            this.leerDatos();
            
            while(resultados.next() == true) {
                
                id = resultados.getInt("id");
                nombre = resultados.getString("nombre");
                periodo= resultados.getString("periodo");
                dias = resultados.getString("dias");
                hora = resultados.getString("hora");
                
                modelo.addRow( new Object[] {nombre, periodo, dias, hora} );                
            }
            
            //this.cerrar();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de lectura de BD\n\n");            
            e.printStackTrace();
        }
    }
    
    /**
     * Cierra la conexion con base de datos
     * @throws SQLException
     */
    private void cerrar() throws SQLException {
        conexion.close();        
    }   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRegresar = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(btnRegresar)
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(262, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnRegresar;
    // End of variables declaration//GEN-END:variables
}
