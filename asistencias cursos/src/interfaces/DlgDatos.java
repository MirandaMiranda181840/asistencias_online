/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Curso;
import entidades.ModeloExcel;
import entidades.Unidad;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import servicios.Conexion;

/**
 *
 * @author loslolis
 */
public class DlgDatos extends javax.swing.JFrame {

    ModeloExcel modeloE = new ModeloExcel();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion = 0;
    Conexion conn = new Conexion();
    ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Unidad> unidades = new ArrayList();
    
     private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private ResultSet resultadosUnidades = null;
    /**
     * Creates new form DlgDatos2
     */
    public DlgDatos() {
        initComponents();
        setLocationRelativeTo(null);
        llenarComboBox();
        
        lblAsistencia1.setVisible(false);
        fechaLista.setVisible(false);
    }

    public void AgregarFiltro() {
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("(*.csv)", "csv"));
       
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

    private void llenarComboBox(){
        int id;
        String nombre, periodo, dias, hora;
        
        try {
            // Obtener datos de la tabla
            this.leerDatos();
            
            while(resultados.next() == true) {
                
                id = resultados.getInt("id");
                nombre = resultados.getString("nombre");
                periodo= resultados.getString("periodo");
                dias = resultados.getString("dias");
                hora = resultados.getString("hora");
                
                cbCursos.addItem("Curso: " + nombre + ", Periodo: " + periodo + ", Días: " + dias + ", Hora: " + hora);
                Curso cursito = new Curso(id, nombre, periodo, dias, hora);
                cursos.add(cursito);
            }
            
            //this.cerrar();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de lectura de BD (cursos):\n\n" + e + "\n\n") ;  
        }
    }
    
    private void llenarComboBoxUnidades(int cursoSeleccionado){
        
        unidades.removeAll(unidades);
        cbUnidad.removeAllItems();

        int indice, idCurso;
        String nombre, descripcion;
        
        try {
            
            this.leerUnidades(cursoSeleccionado);
            
            while(resultadosUnidades.next() == true) {
                
                indice = resultadosUnidades.getInt("indice");
                nombre = resultadosUnidades.getString("nombre");
                descripcion = resultadosUnidades.getString("descripcion");
                idCurso = resultadosUnidades.getInt("idCurso");
                
                
                Unidad unidadsita = new Unidad(nombre, indice, descripcion, idCurso);
                unidades.add(unidadsita);
            }
            
            if (unidades.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay unidades registradas para esa clase");
                    }else{
                        Collections.sort(unidades);
                        for(Unidad elemento:unidades){
                            cbUnidad.addItem("Indice: " + elemento.getIndice() + ", Nombre: " + elemento.getNombre());    
                            System.out.println(elemento);
                        }
                    }
            
            //this.cerrar();
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de lectura de BD (unidades):\n\n" + e + "\n\n") ;            
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

        btnImportar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbCursos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblAsistencia1 = new javax.swing.JLabel();
        fechaLista = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbUnidad = new javax.swing.JComboBox<>();
        btnBuscarUnidades = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Importar Asistencias");

        btnImportar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnImportar.setText("Importar");
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });

        jtDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jtDatos);

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona curso:");

        cbCursos.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCursosActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel2.setText("Selecciona archivo:");

        jButton1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblAsistencia1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        lblAsistencia1.setText("Asistencia de la Fecha:");

        fechaLista.setFont(new java.awt.Font("Lato", 3, 14)); // NOI18N
        fechaLista.setText("0");

        btnRegresar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel3.setText("Selecciona unidad:");

        cbUnidad.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUnidadActionPerformed(evt);
            }
        });

        btnBuscarUnidades.setFont(new java.awt.Font("Lato", 1, 12)); // NOI18N
        btnBuscarUnidades.setText("Buscar unidades");
        btnBuscarUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUnidadesActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel2)
                                .addGap(8, 8, 8)
                                .addComponent(btnImportar)
                                .addGap(62, 62, 62)
                                .addComponent(lblAsistencia1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fechaLista))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbUnidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarUnidades)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegresar)
                .addGap(276, 276, 276))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImportar)
                        .addComponent(lblAsistencia1)
                        .addComponent(fechaLista))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBuscarUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnRegresar))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        contAccion++;
        if (contAccion == 1) {
            AgregarFiltro();
        }
        if (evt.getSource() == btnImportar) {
            if (selecArchivo.showDialog(null, "Seleccionar archivo") == JFileChooser.APPROVE_OPTION) {
                archivo = selecArchivo.getSelectedFile();
                if (archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx") || archivo.getName().endsWith("csv")){
                    try {
                       
                        JOptionPane.showMessageDialog(null, modeloE.importar(archivo, jtDatos, fechaLista));
                        
                        lblAsistencia1.setVisible(true);
                        fechaLista.setVisible(true);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(DlgDatos.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Elija un formato valido.");
                }

            }
        }
        
    }//GEN-LAST:event_btnImportarActionPerformed

    private void cbCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCursosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCursosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     try{
         //cambiar ese 1 por el id del curso seleccionado en el combobox
         int claseSeleccionada = cbCursos.getSelectedIndex();
         String idClaseSeleccionada = String.valueOf(cursos.get(claseSeleccionada).getId());
         System.out.println("ID CLASE SELECCIONADA: "+idClaseSeleccionada);
         
         int intUnidadSeleccionada = cbUnidad.getSelectedIndex();
         if(intUnidadSeleccionada < 0){
             JOptionPane.showMessageDialog(this, "Seleccione una unidad");
         }else{
            Unidad unidadSeleccionada = unidades.get(intUnidadSeleccionada);
            modeloE.guardar(Conexion.obtener(), idClaseSeleccionada, String.valueOf(unidadSeleccionada.getIndice()), unidadSeleccionada.getNombre());
            JOptionPane.showMessageDialog(this, "¡Se registró la lista de asistencia con éxito!");
         }
         
     }catch(SQLException ex){
             JOptionPane.showMessageDialog(this, "Error con el registro de lista de asistencia");
     }  
     catch (ClassNotFoundException ex) {
            Logger.getLogger(DlgDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void cbUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUnidadActionPerformed
        // TODO add your handling code here:
        
       // cbUnidad.addItem("agregó");
    }//GEN-LAST:event_cbUnidadActionPerformed

    private void btnBuscarUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUnidadesActionPerformed
        // TODO add your handling code here:
        
        int claseSeleccionada = cbCursos.getSelectedIndex();
        try{
            Curso cursoS = cursos.get(claseSeleccionada);
            llenarComboBoxUnidades(cursoS.getId());
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(this, "Seleccione un curso");
        }
        
    }//GEN-LAST:event_btnBuscarUnidadesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarUnidades;
    public javax.swing.JButton btnImportar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JComboBox<String> cbUnidad;
    private javax.swing.JLabel fechaLista;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtDatos;
    private javax.swing.JLabel lblAsistencia1;
    // End of variables declaration//GEN-END:variables
}
