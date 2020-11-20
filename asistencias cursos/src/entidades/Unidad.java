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
public class Unidad implements Comparable<Unidad> {
    
    private String nombre;
    private int indice;
    private String descripcion;
    private int idCurso;

    public Unidad(String nombre, int indice, String descripcion, int idCurso) {
        this.nombre = nombre;
        this.indice = indice;
        this.descripcion = descripcion;
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public String toString() {
        return "Unidad{" + "nombre=" + nombre + ", indice=" + indice + ", descripcion=" + descripcion + ", idCurso=" + idCurso + '}'+" \n";
    }

    @Override
    public int compareTo(Unidad t) {
        return String.valueOf(indice).compareTo(String.valueOf(t.getIndice()));
    }
    
    
    
}
