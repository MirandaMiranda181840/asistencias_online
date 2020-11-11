/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author miran
 */
public class Curso {
    
    private int id;
    private String nombre;
    private String periodo;
    private String dias;
    private String hora;

    public Curso(String nombre, String periodo, String dias, String hora) {
        this.nombre = nombre;
        this.periodo = periodo;
        this.dias = dias;
        this.hora = hora;
    }
    
    public Curso(int id, String nombre, String periodo, String dias, String hora) {
        this.id = id;
        this.nombre = nombre;
        this.periodo = periodo;
        this.dias = dias;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    
    
}
