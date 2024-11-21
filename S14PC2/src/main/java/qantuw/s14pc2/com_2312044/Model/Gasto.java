/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qantuw.s14pc2.com_2312044.Model;

import java.time.LocalDate;

public class Gasto {
    private int id;
    private String descripcion;
    private String categoria;
    private double monto;
    private LocalDate fechaGasto;

    private Gasto(Builder builder) {
        this.id = builder.id;
        this.descripcion = builder.descripcion;
        this.categoria = builder.categoria;
        this.monto = builder.monto;
        this.fechaGasto = builder.fechaGasto;
    }

    public static class Builder {
        private int id;
        private String descripcion;
        private String categoria;
        private double monto;
        private LocalDate fechaGasto;
        
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder setCategoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder setMonto(double monto) {
            this.monto = monto;
            return this;
        }

        public Builder setFechaGasto(LocalDate fechaGasto) {
            this.fechaGasto = fechaGasto;
            return this;
        }

        public Gasto build() {
            return new Gasto(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(LocalDate fechaGasto) {
        this.fechaGasto = fechaGasto;
    }
    
    
}
