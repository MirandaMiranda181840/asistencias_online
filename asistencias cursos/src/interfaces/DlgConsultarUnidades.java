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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgConsultarUnidades extends javax.swing.JDialog {

    
    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    //private JTable tabla = null;
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    ArrayList<Unidad> unidades = new ArrayList();
    ArrayList<Curso> cursos = new ArrayList();
    
    /**
     * Creates new form DlgConsultarUnidades
     */
    public DlgConsultarUnidades(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        llenarComboBox();
        setLocationRelativeTo(null);
    }
    
     private void leerDatosCursos() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM cursos";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultados = comando.executeQuery(instruccion);
    }
    
    private void llenarComboBox() {
        int id;
        String nombre, periodo, dias, hora;

        // Ponemos los datos en la tabla
        try {
            // Obtener datos de la tabla
            this.leerDatosCursos();

            while (resultados.next() == true) {

                id = resultados.getInt("id");
                nombre = resultados.getString("nombre");
                periodo = resultados.getString("periodo");
                dias = resultados.getString("dias");
                hora = resultados.getString("hora");

                cbCursos.addItem("Curso: " + nombre + ", Periodo: " + periodo + ", Días: " + dias + ", Hora: " + hora);
                Curso cursito = new Curso(id, nombre, periodo, dias, hora);
                cursos.add(cursito);
            }

            //this.cerrar();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de lectura de BD\n\n");
            e.printStackTrace();
        }
    }
    
    private void leerDatosUnidades(String idCurso) throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM unidades WHERE idCurso="+idCurso;
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultados = comando.executeQuery(instruccion);
       
    }
    
    private void llenarTabla() throws SQLException{
         unidades.removeAll(unidades);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Indice", "Nombre"};
        modelo = new DefaultTableModel();
        int indice, idCurso;
        String nombre, descripcion;
        
        this.setLayout(new BorderLayout());       
        modelo.setColumnIdentifiers(columnas);      
        tabla.setModel(modelo);
        
            try {

                    while(resultados.next() == true) {

                        indice = resultados.getInt("indice");
                        nombre = resultados.getString("nombre");
                        descripcion= resultados.getString("descripcion");
                        idCurso = resultados.getInt("idCurso");

                        unidades.add(new Unidad(nombre, indice, descripcion, idCurso));
                        System.out.println("Se agregó: " + new Unidad(nombre, indice, descripcion, idCurso).toString());
                        
                    }

                    if (unidades.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay unidades registradas para esa clase");
                    }else{
                        Collections.sort(unidades);
                        for(Unidad elemento:unidades){
                            modelo.addRow( new Object[] {elemento.getIndice(), elemento.getNombre()} );     
                            System.out.println(elemento);
                        }
                         unidades.removeAll(unidades);
                    }
                    

                //this.cerrar();

            } catch (SQLException e) {
                System.out.println("Error de lectura de BD\n\n");            
                e.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        cbCursos = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnConsultar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultar unidades");

        jScrollPane1.setViewportView(tabla);

        cbCursos.setFont(new java.awt.Font("Lato", 2, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione el curso: ");

        btnConsultar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(btnConsultar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(btnRegresar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnConsultar)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        try {
            // TODO add your handling code here:
            int curso = cursos.get(cbCursos.getSelectedIndex()).getId();
            leerDatosUnidades(String.valueOf(curso));
            llenarTabla();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DlgConsultarUnidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
