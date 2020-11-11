/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.Objects;

/**
 *
 * @author crisb
 */
public class Asistencia {
    private String idCurso, nombre, horaLlegada, duracion, horaSalida;
    private boolean asistencia;

    public Asistencia(String idCurso, String nombre, String horaLlegada, String duracion, String horaSalida, boolean asistencia) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.horaLlegada = horaLlegada;
        this.duracion = duracion;
        this.horaSalida = horaSalida;
        this.asistencia = asistencia;
    }

    
    
    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String toString() {
        return "Asistencia{" + "idCurso=" + idCurso + ", nombre=" + nombre + ", horaLlegada=" + horaLlegada + ", duracion=" + duracion + ", horaSalida=" + horaSalida + ", asistencia=" + asistencia + '}';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idCurso);
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.horaLlegada);
        hash = 59 * hash + Objects.hashCode(this.duracion);
        hash = 59 * hash + Objects.hashCode(this.horaSalida);
        hash = 59 * hash + (this.asistencia ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Asistencia other = (Asistencia) obj;
        if (this.asistencia != other.asistencia) {
            return false;
        }
        if (!Objects.equals(this.idCurso, other.idCurso)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.horaLlegada, other.horaLlegada)) {
            return false;
        }
        if (!Objects.equals(this.duracion, other.duracion)) {
            return false;
        }
        if (!Objects.equals(this.horaSalida, other.horaSalida)) {
            return false;
        }
        return true;
    }

   
    
}
