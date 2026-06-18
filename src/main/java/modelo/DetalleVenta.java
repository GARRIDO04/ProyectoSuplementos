package modelo;

public class DetalleVenta {
    private int idDetalleVenta;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;

    // Constructor vacío
    public DetalleVenta() {
    }

    // Constructor con parámetros (esencial para armar el carrito de compras en memoria)
    public DetalleVenta(int idProducto, int cantidad, double precioUnitario) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public int getIdDetalleVenta() { return idDetalleVenta; }
    public void setIdDetalleVenta(int idDetalleVenta) { this.idDetalleVenta = idDetalleVenta; }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}