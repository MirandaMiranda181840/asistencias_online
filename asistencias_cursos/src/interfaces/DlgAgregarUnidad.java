/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entidades.Curso;
import entidades.Unidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import servicios.Conexion;

/**
 *
 * @author miran
 */
public class DlgAgregarUnidad extends javax.swing.JDialog {

    ArrayList<Curso> cursos = new ArrayList();
    private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;

    Conexion conn = new Conexion();
    /**
     * Creates new form DlgAgregarUnidad
     */
    public DlgAgregarUnidad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        cbCursos = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtIndice = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar unidad");

        cbCursos.setFont(new java.awt.Font("Lato", 2, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione el curso: ");

        jLabel2.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel2.setText("Nombre de la unidad:");

        txtNombre.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel3.setText("Descripción:");

        taDescripcion.setColumns(20);
        taDescripcion.setRows(5);
        jScrollPane1.setViewportView(taDescripcion);

        jLabel4.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel4.setText("Índice:");

        txtIndice.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N

        btnRegresar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lato", 0, 10)); // NOI18N
        jLabel5.setText("(Opcional)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel5)))
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnRegresar)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregar)))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnAgregar))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        
        //boolean nombre = Pattern.matches("[a-zA-Z áéíóú]+\\.?", txtNombre.getText());
        boolean indice = Pattern.matches("^[0-9]*", txtIndice.getText());
        
            if(!"".equals(txtNombre.getText())  &&  !"".equals(txtIndice.getText()) ){
                if(indice){
                    try {
                        int curso = cursos.get(cbCursos.getSelectedIndex()).getId();
                        Unidad unidad = new Unidad(txtNombre.getText(), Integer.parseInt(txtIndice.getText()), taDescripcion.getText(), curso);
                        guardarUnidad(Conexion.obtener(), unidad);
                        txtNombre.setText("");
                        txtIndice.setText("");
                        taDescripcion.setText("");
                    
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(DlgAgregarUnidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                }else{
                    JOptionPane.showMessageDialog(this, "Ingrese un valor numerico al índice");
                }
            }else{
                JOptionPane.showMessageDialog(this, "Llene todos los campos");
            }
            
            
            
        
        
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    
    public void guardarUnidad(Connection conexion, Unidad unidad) throws SQLException{
      try{
         PreparedStatement consulta;
         
         if ( conn.ifExistsUnidad ("Select * from unidades where indice = ? and idCurso= ?", String.valueOf( unidad.getIndice()), String.valueOf( unidad.getIdCurso() ) ) ){
        //Existe registro
            JOptionPane.showMessageDialog(this, "El índice ya está registrado");
        }else{
        //No hay registros
            consulta = conexion.prepareStatement("INSERT INTO unidades(indice, nombre, descripcion, idCurso) VALUES (?,?,?,?);");
            consulta.setInt(1, unidad.getIndice());
            consulta.setString(2, unidad.getNombre());
            consulta.setString(3, unidad.getDescripcion());
            consulta.setInt(4, unidad.getIdCurso());
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Se registró la unidad con exito!");
            //conexion.close();
            
        }
           
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(this, "Error con el registro");
         throw new SQLException(ex);
      }
      
      
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTextField txtIndice;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}