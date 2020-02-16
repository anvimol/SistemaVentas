/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author avice
 */
public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String stock;
    private String stockMin;
    private String precioCosto;
    private String precioVenta;
    private String utilidad;
    private String estado;
    private String departamento;
    private String categoria;
    private byte[] imagen;

    public Productos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStockMin() {
        return stockMin;
    }

    public void setStockMin(String stockMin) {
        this.stockMin = stockMin;
    }

    public String getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(String precioCosto) {
        this.precioCosto = precioCosto;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(String utilidad) {
        this.utilidad = utilidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
