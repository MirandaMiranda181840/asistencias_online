/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Curso;
import entidades.Unidad;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgEliminarCurso extends javax.swing.JDialog {

    
    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    //private JTable tabla = null;
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    ArrayList<Unidad> unidades = new ArrayList();
    ArrayList<Curso> cursos = new ArrayList();
    
    /**
     * Creates new form DlgEliminarCurso
     * @param parent
     * @param modal
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public DlgEliminarCurso(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        llenarTabla();
    }
    
    private void leerDatosCursos() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM cursos";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultados = comando.executeQuery(instruccion);
    }
    
    private void llenarTabla() throws SQLException, ClassNotFoundException{
         cursos.removeAll(cursos);
         this.leerDatosCursos();
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Nombre", "Periodo", "Días", "Hora"};
        modelo = new DefaultTableModel();
        int id;
        String nombre, periodo, dias, hora;
        
        this.setLayout(new BorderLayout());       
        modelo.setColumnIdentifiers(columnas);      
        tabla.setModel(modelo);
        
            try {

                    while(resultados.next() == true) {
                        id = resultados.getInt("id");
                        nombre = resultados.getString("nombre");
                        periodo = resultados.getString("periodo");
                        dias = resultados.getString("dias");
                        hora = resultados.getString("hora");

                        cursos.add(new Curso(id, nombre, periodo, dias, hora));
                        System.out.println("Se agregó: " + new Curso(id, nombre, periodo, dias, hora).toString() );
                        
                    }

                    if (cursos.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay cursos registrados");
                    }else{
                        for(Curso elemento:cursos){
                            modelo.addRow( new Object[] {elemento.getNombre(), elemento.getPeriodo(), elemento.getDias(), elemento.getHora()} );     
                        }
                    }
                    

                //this.cerrar();

            } catch (SQLException e) {
                System.out.println("Error de lectura de BD\n\n");            
                e.printStackTrace();
            }
            
    
            
        
    }
    
     public void eliminar(Connection conexion, int id) throws SQLException{
      try{
         PreparedStatement consulta;

            consulta = conexion.prepareStatement("DELETE FROM cursos WHERE cursos.id = " + id);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Se eliminó el curso con exito!");
            llenarTabla();
            //conexion.close();
            
        
           
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(this, "Error con el registro");
         throw new SQLException(ex);
      } catch (ClassNotFoundException ex) {
            Logger.getLogger(DlgEliminarCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Eliminar curso");

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione el curso: ");

        jScrollPane1.setViewportView(tabla);

        btnRegresar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(btnRegresar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnEliminar))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if(tabla.getSelectedRow() >= 0){
            try {
            Curso cursito = cursos.get(tabla.getSelectedRow());
            String mensaje = "¿Seguro que quiere eliminar el curso: "+cursito.getNombre() + "?";
            int reply = JOptionPane.showConfirmDialog(null, mensaje, "Eliminar curso", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                eliminar(Conexion.obtener(), cursito.getId());
            } else {
              } 
            } catch (SQLException ex) {
                Logger.getLogger(DlgEliminarCurso.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DlgEliminarCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Elija un curso");
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
