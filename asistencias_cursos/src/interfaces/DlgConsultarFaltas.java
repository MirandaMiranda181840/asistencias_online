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
public class DlgConsultarFaltas extends javax.swing.JDialog {

    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultadosAsistencias = null;
    private ResultSet resultadosFaltas = null;
    private ResultSet resultadosCursos = null;
    
    ArrayList<Asistencia> asistencias = new ArrayList();
    ArrayList<Asistencia> asistenciasF = new ArrayList();
    
    DefaultTableModel modeloAlumnos = null;
    DefaultTableModel modeloFaltas = null;
    JScrollPane desplazamiento = null;
    
    Curso cursoInd;
    
    
    /**
     * Creates new form DlgConsultarFaltas
     */
    public DlgConsultarFaltas(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        llenarTablaAsistencias();
        setLocationRelativeTo(null);
    }
    
    private void leerDatosAsistencias() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM asistencias;";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosAsistencias = comando.executeQuery(instruccion);
    }
    
    private boolean leerDatosFaltas(Asistencia asistencia) throws ClassNotFoundException, SQLException {
        if(!asistencia.isAsistencia()){
            String instruccion = "SELECT * FROM asistencias WHERE nombre='"+asistencia.getNombre()+"' AND asistencia=0";
            conexion = Conexion.obtener();
            comando = conexion.createStatement();
            resultadosFaltas = comando.executeQuery(instruccion);
        }else{
            return false;
        }
        return true;
    }
    
    private void leerDatosCursos(int idCurso) throws ClassNotFoundException, SQLException {
        String idC = String.valueOf(idCurso);
        String instruccion = "SELECT * FROM cursos WHERE id="+idC;
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosCursos = comando.executeQuery(instruccion);
        
        int id;
        String nombre, periodo, dias, hora;
        try {
            // Obtener datos de la tabla
            while (resultadosCursos.next() == true) {

                id = resultadosCursos.getInt("id");
                nombre = resultadosCursos.getString("nombre");
                periodo = resultadosCursos.getString("periodo");
                dias = resultadosCursos.getString("dias");
                hora = resultadosCursos.getString("hora");

                Curso cursito = new Curso(id, nombre, periodo, dias, hora);
                cursoInd = cursito;
            }

            //this.cerrar();
        } catch (SQLException e) {
            System.out.println("Error de lectura de BD\n\n");
            e.printStackTrace();
        }
        
    }
    
    private void llenarTablaAsistencias() throws SQLException, ClassNotFoundException{
         asistencias.removeAll(asistencias);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Nombre"};
        modeloAlumnos = new DefaultTableModel();
        
        int idC, asistencia, unidadIndice;
        String nomb, horaLlegada, duracion, horaSalida, fechaC, unidadNombre;
        
        this.setLayout(new BorderLayout());       
        modeloAlumnos.setColumnIdentifiers(columnas);      
        tablaAlumnos.setModel(modeloAlumnos);
        
        this.leerDatosAsistencias();
        
            try {

                    while(resultadosAsistencias.next() == true) {

                        
                         idC = resultadosAsistencias.getInt("idCurso");
                        nomb = resultadosAsistencias.getString("nombre");
                        horaLlegada = resultadosAsistencias.getString("horaLlegada");
                        duracion = resultadosAsistencias.getString("duracion");
                        horaSalida = resultadosAsistencias.getString("horaSalida");
                        asistencia = resultadosAsistencias.getInt("asistencia");
                        fechaC = resultadosAsistencias.getString("fecha");
                        unidadIndice = resultadosAsistencias.getInt("unidadIndice");
                        unidadNombre = resultadosAsistencias.getString("unidadNombre");

                        
                        
                        boolean auxi;
                        if (asistencia == 1) {
                            //Asistió
                            auxi = true;
                        } else {
                            //No asistió
                            auxi = false;
                        }
                        
                        
                        Asistencia asis = new Asistencia(String.valueOf(idC), nomb, horaLlegada, duracion, horaSalida, fechaC, String.valueOf(unidadIndice), unidadNombre, auxi);
                            asistencias.add(asis);
                            System.out.println("Se añadió: " + asis.toString());

                        
                    }

                    if (asistencias.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay datos para este alumno");
                    }else{
                        for(Asistencia elemento:asistencias){
                            modeloAlumnos.addRow( new Object[] {elemento.getNombre()} );     
//                            System.out.println(elemento);
                        }
                    }
                    

                //this.cerrar();

            } catch (SQLException e) {
                System.out.println("Error de lectura de BD\n\n");            
                e.printStackTrace();
            }
    }
    
    private void llenarTablaFaltas(Asistencia asis) throws SQLException, ClassNotFoundException{
         asistenciasF.removeAll(asistenciasF);
//         asistencias.removeAll(asistencias);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Nombre del curso", "Fecha de inasistencia"};
        modeloFaltas = new DefaultTableModel();
        
        int idC, asistencia;
        String nomb, horaLlegada, duracion, horaSalida, fechaC;
        
        this.setLayout(new BorderLayout());       
        modeloFaltas.setColumnIdentifiers(columnas);      
        tablaFaltas.setModel(modeloFaltas);
        
        if(leerDatosFaltas(asis)){
            
        
            try {

                    while(resultadosFaltas.next() == true) {

                        
                         idC = resultadosFaltas.getInt("idCurso");
                        nomb = resultadosFaltas.getString("nombre");
                        horaLlegada = resultadosFaltas.getString("horaLlegada");
                        duracion = resultadosFaltas.getString("duracion");
                        horaSalida = resultadosFaltas.getString("horaSalida");
                        asistencia = resultadosFaltas.getInt("asistencia");
                        fechaC = resultadosFaltas.getString("fecha");
                        
                        Asistencia asis2 = new Asistencia(String.valueOf(idC), nomb, horaLlegada, duracion, horaSalida, fechaC, false);
                            asistenciasF.add(asis2);
                            System.out.println("Se añadió en FALTAS: " + asis2.toString());
                        
                    }
                    
                    if (asistenciasF.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay inasistencias para este alumno");
                    }else{
                        JOptionPane.showMessageDialog(this, "¡Se encontraron inasistencias para este alumno!");
                        for(Asistencia elemento:asistenciasF){
                            this.leerDatosCursos(Integer.parseInt(elemento.getIdCurso()));
                            modeloFaltas.addRow( new Object[] {cursoInd.getNombre(), elemento.getFecha()} );     
                        }
                    }
                    

                //this.cerrar();

            } catch (SQLException e) {
                System.out.println("Error de lectura de BD\n\n");            
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "No hay inasistencias para este alumno");
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
        tablaAlumnos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaFaltas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnConsultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultar Faltas");

        jScrollPane1.setViewportView(tablaAlumnos);

        jScrollPane2.setViewportView(tablaFaltas);

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona al alumno:");

        btnConsultar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnConsultar.setText("Consultar faltas");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel2.setText("Faltas de: ");

        lblNombre.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(0, 153, 153));
        lblNombre.setText("nombre");

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
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(btnRegresar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(btnConsultar)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConsultar)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        try {
            // TODO add your handling code here:
            
            Asistencia aux = asistencias.get(tablaAlumnos.getSelectedRow());
            lblNombre.setText(aux.getNombre());
            llenarTablaFaltas(aux);
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DlgConsultarFaltas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tablaAlumnos;
    private javax.swing.JTable tablaFaltas;
    // End of variables declaration//GEN-END:variables
}
