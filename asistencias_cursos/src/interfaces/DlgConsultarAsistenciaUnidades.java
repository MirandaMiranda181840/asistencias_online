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
public class DlgConsultarAsistenciaUnidades extends javax.swing.JDialog {

    
    Conexion conn = new Conexion();
    
    
    ArrayList<Curso> cursos = new ArrayList();
    ArrayList<Unidad> unidades = new ArrayList();
    ArrayList<Asistencia> asistencias = new ArrayList();
    
    
    DefaultTableModel modelo = null;
    JScrollPane desplazamiento = null;
    
     private Connection conexion = null;
    private Statement comando = null;
    private ResultSet resultados = null;
    private ResultSet resultadosUnidades = null;
    private ResultSet resultadosTabla = null;
    
    
    /**
     * Creates new form DlgConsultarAsistenciaUnidades
     */
    public DlgConsultarAsistenciaUnidades(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(null);
        initComponents();
        llenarComboBox();
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
                        //Remueve las filas anteriores
                        int filas = tabla.getRowCount();
                        if (filas != 0){
                            for (int i = 0; i<=tabla.getRowCount(); i++){
                                modelo.removeRow(i);
                            }
                        }
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
    
    private void leerDatosAsistencias(String idCurso, String indiceUnidad) throws ClassNotFoundException, SQLException {
        String instruccion = "SELECT * FROM asistencias WHERE idCurso="+idCurso+" AND unidadIndice = "+indiceUnidad;
        conexion = Conexion.obtener();
        comando = conexion.createStatement();
        resultadosTabla = comando.executeQuery(instruccion);
       
    }
    
    private void llenarTabla() throws SQLException{
         asistencias.removeAll(asistencias);
         // Nombre de las columnas como apareceran en la tabla
        String[] columnas = {"Nombre", "Asistencias"};
        modelo = new DefaultTableModel();
        int idCurso, unidadIndice;
        String nombre, horaLlegada, duracion, horaSalida, fecha, unidadNombre;
        boolean asistencia;
        
        this.setLayout(new BorderLayout());       
        modelo.setColumnIdentifiers(columnas);      
        tabla.setModel(modelo);
        
            try {

                    while(resultadosTabla.next() == true) {

                        idCurso = resultadosTabla.getInt("idCurso");
                        nombre = resultadosTabla.getString("nombre");
                        horaLlegada = resultadosTabla.getString("horaLlegada");
                        duracion = resultadosTabla.getString("duracion");
                        horaSalida = resultadosTabla.getString("horaSalida");
                        asistencia = resultadosTabla.getBoolean("asistencia");
                        fecha = resultadosTabla.getString("fecha");
                        nombre = resultadosTabla.getString("nombre");
                        unidadIndice = resultadosTabla.getInt("unidadIndice");
                        unidadNombre = resultadosTabla.getString("unidadNombre");

                        System.out.println("Llego esto: "+ nombre + ",  idCurso: "+idCurso+", unidadIndice: "+unidadIndice);
                        Asistencia asistencita = new Asistencia(nombre, 1);
                        asistencias.add(asistencita);
                        
                    }
                    
                    if (asistencias.isEmpty()){
                        JOptionPane.showMessageDialog(this, "No hay asistencias para esta unidad en esta clase");

                        
                    }else{
                        
                        //Se suman las asistencias
                        sumarAsistencias();
                        
                        //Se agregan a la tabla las asistencias
                        for(Asistencia elemento:asistencias){
                            modelo.addRow( new Object[] {elemento.getNombre(), elemento.getAsistenciasUnidad()} );     
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
    
    public void sumarAsistencias(){
        
        for(int i=0;i<asistencias.size()-1;i++){
            
            for(int j=i+1;j<asistencias.size();j++){
                
                if(asistencias.get(i).getNombre() == null ? asistencias.get(j).getNombre() == null : asistencias.get(i).getNombre().equals(asistencias.get(j).getNombre())){
                    System.out.println("Elemento repetido: " + asistencias.get(i));
                    System.out.println("Si son iguales");
                    int aux = asistencias.get(i).getAsistenciasUnidad();
                    asistencias.get(i).setAsistenciasUnidad(aux+1);
                    asistencias.remove(j);
                }
                
            }
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
        jLabel3 = new javax.swing.JLabel();
        cbUnidad = new javax.swing.JComboBox<>();
        cbCursos = new javax.swing.JComboBox<>();
        btnBuscarUnidades = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel1.setText("Selecciona curso:");

        jLabel3.setFont(new java.awt.Font("Lato", 1, 14)); // NOI18N
        jLabel3.setText("Selecciona unidad:");

        cbUnidad.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUnidadActionPerformed(evt);
            }
        });

        cbCursos.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        cbCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCursosActionPerformed(evt);
            }
        });

        btnBuscarUnidades.setFont(new java.awt.Font("Lato", 1, 12)); // NOI18N
        btnBuscarUnidades.setText("Buscar unidades");
        btnBuscarUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUnidadesActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla);

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscarUnidades)
                .addGap(169, 169, 169))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbUnidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(btnRegresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(btnConsultar)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscarUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnConsultar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnRegresar)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUnidadActionPerformed
        // TODO add your handling code here:

        // cbUnidad.addItem("agregó");
    }//GEN-LAST:event_cbUnidadActionPerformed

    private void cbCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCursosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCursosActionPerformed

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

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:.
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        
        int idCurso = cursos.get(cbCursos.getSelectedIndex()).getId();
        
        if(cbUnidad.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(this, "Seleccione una unidad");
        }else{
            
            try {
                
                int indiceUnidad = unidades.get(cbUnidad.getSelectedIndex()).getIndice();
                leerDatosAsistencias(String.valueOf(idCurso), String.valueOf(indiceUnidad));
                llenarTabla();
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DlgConsultarAsistenciaUnidades.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }//GEN-LAST:event_btnConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarUnidades;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbCursos;
    private javax.swing.JComboBox<String> cbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
