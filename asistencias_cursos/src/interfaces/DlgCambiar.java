/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Asistencia;
import entidades.Curso;
import entidades.Unidad;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgCambiar extends javax.swing.JFrame  {

     Conexion conn = new Conexion();
    ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Asistencia> asistencias = new ArrayList();
    ArrayList<String> info = new ArrayList<String>();
    ArrayList<Unidad> unidades = new ArrayList();

    DlgModificar modificarC;
    DlgAgregarAsistenciaAlumno agregarAsistenciaAlumno;
    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private ResultSet resultadosCursos = null;
    private ResultSet resultadosUnidades = null;
    private boolean tablaLlena = false;
    
    /**
     * Creates new form DlgCambiar
     */
    public DlgCambiar(java.awt.Frame parent, boolean modal) {
        //super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        llenarComboBox();
    }

       /**
     * Obtenemos todos los datos de la tabla juegos;
     *
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
     * Obtenemos todos los datos de la tabla asistencia según los parámetros
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void leerCursos(String fecha, int idCurso, int unidadIndice) throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM asistencias WHERE idCurso=" + idCurso + " AND fecha='" + fecha + "' AND unidadIndice="+ String.valueOf(unidadIndice);
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosCursos = comando.executeQuery(instruccion);
    }

    private void llenaTabla() throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();
        jtDatos.setModel(modelo);
        //jtDatos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        modelo.addColumn("Nombre");
        modelo.addColumn("Hora de entrada");
        modelo.addColumn("Duración");

        int idC, asistencia;
        String nomb, horaLlegada, duracion, horaSalida, fechaC;

        if (resultadosCursos.next() == false) {
            JOptionPane.showMessageDialog(this, "No hay registros para ese curso con esa fecha");
            tablaLlena = false;
        } else {
            while (resultadosCursos.next() == true) {

                idC = resultadosCursos.getInt("idCurso");
                nomb = resultadosCursos.getString("nombre");
                horaLlegada = resultadosCursos.getString("horaLlegada");
                duracion = resultadosCursos.getString("duracion");
                horaSalida = resultadosCursos.getString("horaSalida");
                asistencia = resultadosCursos.getInt("asistencia");
                fechaC = resultadosCursos.getString("fecha");
                boolean auxi;
                String estuvo;
                if (asistencia == 1) {
                    auxi = true;
                    estuvo = "Asistió";
                } else {
                    auxi = false;
                    estuvo = "No asistió";
                }
                Asistencia asis = new Asistencia(String.valueOf(idC), nomb, horaLlegada, duracion, horaSalida, fechaC, auxi);
                asistencias.add(asis);
                System.out.println("Se añadió: " + asis.toString());
                modelo.addRow(new Object[]{nomb, horaLlegada, duracion});
            }
            tablaLlena = true;
        }
        
    }

    private void llenarComboBox() {
        int id;
        String nombre, periodo, dias, hora;

        // Ponemos los datos en la tabla
        try {
            // Obtener datos de la tabla
            this.leerDatos();

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

        btnRegresar = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbCursos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnConsultarLista = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        btnAgregarNuevo = new javax.swing.JButton();
        cbUnidad = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnBuscarUnidades = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar Asistencias");

        btnRegresar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        botonGuardar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        botonGuardar.setText("Modificar");
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione el curso: ");

        cbCursos.setFont(new java.awt.Font("Lato", 2, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel2.setText("Ingrese fecha:");

        txtFecha.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel3.setText("Formato: aaaa-mm-dd");

        btnConsultarLista.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnConsultarLista.setText("Consultar lista");
        btnConsultarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarListaActionPerformed(evt);
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

        btnAgregarNuevo.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnAgregarNuevo.setText("Agregar nuevo");
        btnAgregarNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoActionPerformed(evt);
            }
        });

        cbUnidad.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUnidadActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel4.setText("Selecciona unidad:");

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel3))
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarUnidades))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConsultarLista)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegresar)
                        .addGap(138, 138, 138))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnConsultarLista)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(botonGuardar)
                    .addComponent(btnAgregarNuevo))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        if(tablaLlena){
            
            if(jtDatos.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(this, "Seleccione un alumno");
            }else{
                Asistencia asistenciaSeleccionada = asistencias.get(jtDatos.getSelectedRow());
                modificarC = new DlgModificar(asistenciaSeleccionada);
                modificarC.setVisible(true);
                this.setVisible(false);
            }
                      
        }else{
            JOptionPane.showMessageDialog(this, "Consulte una lista primero");
        }
        

    }//GEN-LAST:event_botonGuardarActionPerformed

    private void btnConsultarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarListaActionPerformed
        // TODO add your handling code here:
        String regex = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        String fechaTexto = txtFecha.getText();
        if (Pattern.matches(regex, fechaTexto)) {
            try {
                int claseSeleccionada = cbCursos.getSelectedIndex();
                int idClaseSeleccionada = cursos.get(claseSeleccionada).getId();
                int unidadSeleccionada = unidades.get(cbUnidad.getSelectedIndex()).getIndice();
                
                System.out.println("ID DE CLASE: " + idClaseSeleccionada);
                leerCursos(fechaTexto, idClaseSeleccionada, unidadSeleccionada);
                llenaTabla();
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DlgConsultarAsistencias.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese la fecha con el formato indicado");
        }
    }//GEN-LAST:event_btnConsultarListaActionPerformed

    private void btnAgregarNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoActionPerformed
      if(tablaLlena){
          int claseSeleccionada = cbCursos.getSelectedIndex();
          String idClaseSeleccionada = String.valueOf(cursos.get(claseSeleccionada).getId());
          agregarAsistenciaAlumno = new DlgAgregarAsistenciaAlumno(this, true, Integer.parseInt(idClaseSeleccionada),txtFecha.getText(), unidades.get(cbUnidad.getSelectedIndex()));
          agregarAsistenciaAlumno.setVisible(true);
          //this.setVisible(false);
      }else{
          JOptionPane.showMessageDialog(this, "Consulte una lista primero");
      }
        
        
        
    }//GEN-LAST:event_btnAgregarNuevoActionPerformed

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
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton btnAgregarNuevo;
    private javax.swing.JButton btnBuscarUnidades;
    private javax.swing.JButton btnConsultarLista;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JComboBox<String> cbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtDatos;
    private javax.swing.JTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
