/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Asistencia;
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
public class DlgEliminarUnidad extends javax.swing.JDialog {

    Conexion conn = new Conexion();
    
    
    ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Unidad> unidades = new ArrayList();
    ArrayList<Asistencia> asistencias = new ArrayList();
    
    
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    
     private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultadosCursos = null;
    private ResultSet resultadosUnidades = null;
    private ResultSet resultadosTabla = null;
    
    /**
     * Creates new form DlgEliminarUnidad
     */
    public DlgEliminarUnidad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        llenarComboBoxCursos();
        setLocationRelativeTo(null);
    }
    
       
    /**
     * Obtenemos todos los datos de la tabla juegos;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void leerDatosCursos() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM cursos";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosCursos = comando.executeQuery(instruccion);
    }
    
    
    /**
     * Obtenemos todos los datos de la tabla juegos;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void leerUnidades(int idCurso) throws ClassNotFoundException, SQLException {
        String idC = String.valueOf(idCurso);
        String instruccion = "SELECT * FROM unidades WHERE idCurso=" + idC;
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosUnidades = comando.executeQuery(instruccion);
    }

    private void llenarComboBoxCursos(){
        int id;
        String nombre, periodo, dias, hora;
        
        try {
            // Obtener datos de la tabla
            this.leerDatosCursos();
            
            while(resultadosCursos.next() == true) {
                
                id = resultadosCursos.getInt("id");
                nombre = resultadosCursos.getString("nombre");
                periodo= resultadosCursos.getString("periodo");
                dias = resultadosCursos.getString("dias");
                hora = resultadosCursos.getString("hora");
                
                cbCursos.addItem("Curso: " + nombre + ", Periodo: " + periodo + ", Días: " + dias + ", Hora: " + hora);
                Curso cursito = new Curso(id, nombre, periodo, dias, hora);
                cursos.add(cursito);
            }
            
            //this.cerrar();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de lectura de BD (cursos):\n\n" + e + "\n\n") ;  
        }
    }
    
    private void llenarTabla(int cursoSeleccionado) throws SQLException, ClassNotFoundException{
        
        this.leerUnidades(cursoSeleccionado); 
        
        unidades.removeAll(unidades);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Indice","Nombre", "Descripción"};
        modelo = new DefaultTableModel();
        
         int indice, idCurso;
         String nombre, descripcion;
         
          
        
        this.setLayout(new BorderLayout());       
        modelo.setColumnIdentifiers(columnas);      
        tabla.setModel(modelo);
        
            try {

                    while(resultadosUnidades.next() == true) {

                        indice = resultadosUnidades.getInt("indice");
                        nombre = resultadosUnidades.getString("nombre");
                        descripcion = resultadosUnidades.getString("descripcion");
                        idCurso = resultadosUnidades.getInt("idCurso");


                        Unidad unidadsita = new Unidad(nombre, indice, descripcion, idCurso);
                        System.out.println("Se añadió: " + unidadsita.toString());
                        unidades.add(unidadsita);
                        
                    }
                    
                    if (unidades.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay unidades para este curso");
                    }else{
                        Collections.sort(unidades);
                        //Se agregan a la tabla las asistencias
                        for(Unidad elemento:unidades){
                            modelo.addRow( new Object[] {elemento.getIndice(), elemento.getNombre(), elemento.getDescripcion()} );     
                            System.out.println("Se agregó a la tabla: "+elemento.toString());
                        }
                    }
            } catch (SQLException e) {
                System.out.println("Error de lectura de BD\n\n");            
                e.printStackTrace();
            }
    }
    
    public void eliminar(Connection conexion, Unidad unidad) throws SQLException{
      try{
         PreparedStatement consulta;

            consulta = conexion.prepareStatement("DELETE FROM unidades WHERE idCurso=" + unidad.getIdCurso() + " AND indice=" + unidad.getIndice());
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Se eliminó la unidad con exito!");
            llenarTabla(unidad.getIdCurso());
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

        cbCursos = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnBuscarUnidades = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Eliminar unidad");

        cbCursos.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCursosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona curso:");

        btnBuscarUnidades.setFont(new java.awt.Font("Lato", 1, 12)); // NOI18N
        btnBuscarUnidades.setText("Buscar unidades");
        btnBuscarUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUnidadesActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tabla);

        btnEliminar.setBackground(new java.awt.Color(255, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegresar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(btnBuscarUnidades)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(btnRegresar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscarUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnEliminar))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCursosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCursosActionPerformed

    private void btnBuscarUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUnidadesActionPerformed
        // TODO add your handling code here:

        int claseSeleccionada = cbCursos.getSelectedIndex();
        try{
            
            Curso cursoS = cursos.get(claseSeleccionada);
            llenarTabla(cursoS.getId());
            
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(this, "Seleccione un curso");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DlgEliminarUnidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarUnidadesActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if(tabla.getSelectedRow() >= 0){
            try {
                Unidad unidadsita = unidades.get(tabla.getSelectedRow());
                Curso cursito = cursos.get(cbCursos.getSelectedIndex());
                String mensaje = "¿Seguro que quiere eliminar la unidad \""+ unidadsita.getNombre() + "\" del curso \"" + cursito.getNombre()+"\"?";
                int reply = JOptionPane.showConfirmDialog(null, mensaje, "Eliminar unidad", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    eliminar(Conexion.obtener(), unidadsita);
                } else {
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DlgEliminarCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Elija una unidad a eliminar");
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarUnidades;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
