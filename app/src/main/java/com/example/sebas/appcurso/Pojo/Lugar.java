package com.example.sebas.appcurso.Pojo;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Lugar implements Parcelable {

    private long id;
    private double latitud, longitud;
    private String localidad, pais;
    private String comentario;
    private int puntos;
    private String fecha;
    public String nombre;

    public Lugar() {
        this(0, "", 0, 0, "", "", "", 0, "");
    }

    public Lugar(long id, String nombre, double latitud, double longitud, String localidad, String pais, String comentario, int puntos, String fecha) {
        this.id = id;
        this. nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.localidad = localidad;
        this.pais = pais;
        this.comentario = comentario;
        this.puntos = puntos;
        this.fecha = fecha;
    }

    protected Lugar(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        localidad = in.readString();
        pais = in.readString();
        comentario = in.readString();
        puntos = in.readInt();
        fecha = in.readString();
    }

    public static final Creator<Lugar> CREATOR = new Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel in) {
            return new Lugar(in);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getNombre() { return nombre;}

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getPais() {
        return pais;
    }

    public String getComentario() {
        return comentario;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre){this.nombre = nombre;}

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lugar lugar = (Lugar) o;

        if (id != lugar.id) return false;
        if (Double.compare(lugar.latitud, latitud) != 0) return false;
        if (Double.compare(lugar.longitud, longitud) != 0) return false;
        if (puntos != lugar.puntos) return false;
        if (localidad != null ? !localidad.equals(lugar.localidad) : lugar.localidad != null)
            return false;
        if (pais != null ? !pais.equals(lugar.pais) : lugar.pais != null) return false;
        if (comentario != null ? !comentario.equals(lugar.comentario) : lugar.comentario != null)
            return false;
        return fecha != null ? fecha.equals(lugar.fecha) : lugar.fecha == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(latitud);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitud);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (localidad != null ? localidad.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        result = 31 * result + (comentario != null ? comentario.hashCode() : 0);
        result = 31 * result + puntos;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", localidad='" + localidad + '\'' +
                ", pais='" + pais + '\'' +
                ", comentario='" + comentario + '\'' +
                ", puntos=" + puntos +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(localidad);
        dest.writeString(pais);
        dest.writeString(comentario);
        dest.writeInt(puntos);
        dest.writeString(fecha);
    }
}

