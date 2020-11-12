/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Asistencia;
import entidades.Curso;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgConsultarAsistencias extends javax.swing.JDialog {

    Conexion conn = new Conexion();
    ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Asistencia> asistencias = new ArrayList();
    ArrayList<String> info = new ArrayList<String>();

    DlgModificar modificarC;

    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private ResultSet resultadosCursos = null;

    /**
     * Creates new form DlgConsultarAsistencias
     *
     * @param parent
     * @param modal
     */
    public DlgConsultarAsistencias(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        llenarComboBox();

    }

    private DlgConsultarAsistencias(ArrayList<String> info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    private void leerCursos(String fecha, int idCurso) throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM asistencias WHERE idCurso=" + idCurso + " AND fecha='" + fecha + "'";
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbCursos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnConsultarLista = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Seleccione el curso: ");

        jLabel2.setText("Ingrese fecha:");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel3.setText("Formato: aaaa/mm/dd");

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

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        botonGuardar.setText("Modificar");
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel3))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(btnConsultarLista)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(botonGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(98, 98, 98))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(37, 37, 37)
                .addComponent(btnConsultarLista)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(botonGuardar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarListaActionPerformed
        // TODO add your handling code here:
        String regex = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        String fechaTexto = txtFecha.getText();
        if (Pattern.matches(regex, fechaTexto)) {
            try {
                int claseSeleccionada = cbCursos.getSelectedIndex();
                int idClaseSeleccionada = cursos.get(claseSeleccionada).getId();
                System.out.println("ID DE CLASE: " + idClaseSeleccionada);
                leerCursos(fechaTexto, idClaseSeleccionada);
                llenaTabla();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DlgConsultarAsistencias.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese la fecha con el formato indicado");
        }
    }//GEN-LAST:event_btnConsultarListaActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        for (int i = 0; i < jtDatos.getRowCount(); i++) {
            if (i == jtDatos.getSelectedRow()) {
                info.add(0, (String) jtDatos.getValueAt(i, 0));
                info.add(1, (String) jtDatos.getValueAt(i, 1));
                info.add(2, (String) jtDatos.getValueAt(i, 2));
            }

        }       
        modificarC = new DlgModificar(info);
        modificarC.setVisible(true);
    }//GEN-LAST:event_botonGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton btnConsultarLista;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtDatos;
    private javax.swing.JTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
