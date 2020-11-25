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
public class DlgConsultarAsistenciasAlumno extends javax.swing.JDialog {

     ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Asistencia> alumnos = new ArrayList();
    ArrayList<Asistencia> asistencias = new ArrayList();
    
    
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    
     private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private ResultSet resultadosAlumnos = null;
    private ResultSet resultadosAsistencias = null;
    
    /**
     * Creates new form DlgConsultarAsistenciasAlumno
     */
    public DlgConsultarAsistenciasAlumno(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        llenarComboBoxCursos();
        setLocationRelativeTo(null);
    }
    
    private void leerDatosCursos() throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM cursos";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultados = comando.executeQuery(instruccion);
    }
    
    private void llenarComboBoxCursos(){
        int id;
        String nombre, periodo, dias, hora;
        
        try {
            // Obtener datos de la tabla
            this.leerDatosCursos();
            
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
    
    private void leerDatosAlumnos(String idCurso) throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM asistencias WHERE idCurso="+idCurso;
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosAlumnos = comando.executeQuery(instruccion);
    }
    
    private void llenarComboBoxAlumnos(){
        boolean asistencia;
        String nombre,fecha, horaLlegada, duracion;
        
        try {
            
            
            while(resultadosAlumnos.next() == true) {
                
                nombre = resultadosAlumnos.getString("nombre");
                fecha = resultadosAlumnos.getString("fecha");
                horaLlegada = resultadosAlumnos.getString("horaLlegada");
                duracion = resultadosAlumnos.getString("duracion");
                asistencia = resultadosAlumnos.getBoolean("asistencia");
                
                cbAlumnos.addItem(nombre);
                Asistencia asis = new Asistencia(nombre, fecha, horaLlegada, duracion);
                alumnos.add(asis);
                
            }
            
            //this.cerrar();
            
        } catch (SQLException e) {
            System.out.println("Error de lectura de BD (cursos):\n\n" + e + "\n\n") ;  
        }
    }
    
    private void leerDatosAsistencia(String idCurso, String nombre) throws SQLException, ClassNotFoundException{
        String instruccion = "SELECT * FROM asistencias WHERE idCurso="+idCurso+" AND nombre='"+nombre+"';";
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosAsistencias = comando.executeQuery(instruccion);
    }
    
    private void llenarTabla(){
        asistencias.removeAll(asistencias);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Fecha", "Hora de entrada", "Duración"};
        modelo = new DefaultTableModel();
        
        int idCurso, unidadIndice;
        String nombre, horaLlegada, duracion, horaSalida, fecha, unidadNombre;
        boolean asistencia;
        
        this.setLayout(new BorderLayout());       
        modelo.setColumnIdentifiers(columnas);      
        tabla.setModel(modelo);
        
            try {

                    while(resultadosAsistencias.next() == true) {

                        idCurso = resultadosAsistencias.getInt("idCurso");
                        nombre = resultadosAsistencias.getString("nombre");
                        horaLlegada = resultadosAsistencias.getString("horaLlegada");
                        duracion = resultadosAsistencias.getString("duracion");
                        horaSalida = resultadosAsistencias.getString("horaSalida");
                        asistencia = resultadosAsistencias.getBoolean("asistencia");
                        fecha = resultadosAsistencias.getString("fecha");
                        nombre =  resultadosAsistencias.getString("nombre");
                        unidadIndice = resultadosAsistencias.getInt("unidadIndice");
                        unidadNombre = resultadosAsistencias.getString("unidadNombre");

                        Asistencia asistencita = new Asistencia(String.valueOf(idCurso), nombre, horaLlegada, duracion, horaSalida, fecha, String.valueOf(unidadIndice), unidadNombre, asistencia);
                        asistencias.add(asistencita);
                        
                    }
                    
                    if (alumnos.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay asistencias para este alumno en esta clase");
                    }else{                        
                        //Se agregan a la tabla las alumnos
                        for(Asistencia elemento:asistencias){
                            modelo.addRow( new Object[] {elemento.getFecha(), elemento.getHoraLlegada(), elemento.getDuracion()} );     
                            System.out.println(elemento);
                        }
                         asistencias.removeAll(asistencias);
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cbCursos = new javax.swing.JComboBox<>();
        btnConsultar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbAlumnos = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultar asistencias");

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona curso:");

        cbCursos.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCursosActionPerformed(evt);
            }
        });

        btnConsultar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnMostrar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnMostrar.setText("Mostrar alumnos");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel2.setText("Selecciona alumno:");

        cbAlumnos.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        jScrollPane2.setViewportView(tabla);

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
                .addGap(187, 187, 187)
                .addComponent(btnMostrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(btnRegresar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(197, 197, 197)
                                .addComponent(btnConsultar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnMostrar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnConsultar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCursosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCursosActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
         try {
             // TODO add your handling code here:
             Curso cursoSelec = cursos.get(cbCursos.getSelectedIndex());
             leerDatosAlumnos(String.valueOf(cursoSelec.getId()));
             llenarComboBoxAlumnos();
             System.out.println("Llega aqui");
         } catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(DlgConsultarAsistenciasAlumno.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        Curso cursoSelec = cursos.get(cbCursos.getSelectedIndex());
        Asistencia alumnoSelec = alumnos.get(cbAlumnos.getSelectedIndex());
        
         try {
             leerDatosAsistencia(String.valueOf(cursoSelec.getId()), alumnoSelec.getNombre());
             llenarTabla();
             JOptionPane.showMessageDialog(this, "¡Se encontraron los registros con exito!");
         } catch (SQLException | ClassNotFoundException ex) {
             Logger.getLogger(DlgConsultarAsistenciasAlumno.class.getName()).log(Level.SEVERE, null, ex);
         }
         
    }//GEN-LAST:event_btnConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbAlumnos;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
