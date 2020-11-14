/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Asistencia;
import entidades.ModeloExcel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import servicios.Conexion;

/**
 *
 * @author crisb
 */
public class DlgAgregarAsistenciaAlumno extends javax.swing.JFrame {

    /**
     * Creates new form DlgAgregarAlumno
     */
     Conexion conn = new Conexion();
    ModeloExcel modelo;
    private int idCurso;
    private String fecha;
    public DlgAgregarAsistenciaAlumno(int idCurso, String fecha) {
        initComponents();
        setLocationRelativeTo(null);
        modelo= new ModeloExcel();
        this.idCurso=idCurso;
        this.fecha=fecha;
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JtxtFieldNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldHoraLlegada = new javax.swing.JTextField();
        jTextFieldDuracion = new javax.swing.JTextField();
        jTextFieldHoraSalida = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agregar Asistencia");

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel3.setText("Hora Llegada:");

        jLabel4.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel4.setText("Hora Salida:");

        JtxtFieldNombre.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        JtxtFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JtxtFieldNombreActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel6.setText("Duración:");

        jTextFieldHoraLlegada.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        jTextFieldDuracion.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        jTextFieldHoraSalida.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        btnAceptar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lato", 0, 10)); // NOI18N
        jLabel2.setText("Duración en minutos");

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
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldHoraSalida)
                                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(46, 46, 46))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JtxtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRegresar)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(JtxtFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnRegresar))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JtxtFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JtxtFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JtxtFieldNombreActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
         
        boolean nombre = Pattern.matches("[a-zA-Z áéíóú]+\\.?", JtxtFieldNombre.getText());
        boolean horaLlegada = Pattern.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$", jTextFieldHoraLlegada.getText());
        boolean duracion = Pattern.matches("^[0-9]*", jTextFieldDuracion.getText());
        boolean horaSalida = Pattern.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$", jTextFieldHoraSalida.getText());
        
        if(nombre && horaLlegada && duracion && horaSalida){
            
                try {
                 Asistencia asistencia= new Asistencia(Integer.toString(idCurso), JtxtFieldNombre.getText(),
                         jTextFieldHoraLlegada.getText(), jTextFieldDuracion.getText()+"min",
                         jTextFieldHoraSalida.getText(), fecha, true);
                 modelo.guardarNuevaAsistenciaAlumno(Conexion.obtener(), asistencia);
             JOptionPane.showMessageDialog(this, "¡Se registró la asistencia con exito!");
             this.setVisible(false);
             } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(this, "Error con el registro");
                 Logger.getLogger(DlgAgregarAsistenciaAlumno.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 JOptionPane.showMessageDialog(this, "Error con el registro");
                 Logger.getLogger(DlgAgregarAsistenciaAlumno.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        } else{
            JOptionPane.showMessageDialog(this, "Ingrese todos los datos correctamente");
        }
        
        
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        DlgCambiar cambiar = new DlgCambiar(this,true);
        cambiar.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JtxtFieldNombre;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextFieldDuracion;
    private javax.swing.JTextField jTextFieldHoraLlegada;
    private javax.swing.JTextField jTextFieldHoraSalida;
    // End of variables declaration//GEN-END:variables
}