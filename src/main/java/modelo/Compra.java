package modelo;

public class Compra {
    private int idCompra;
    private int idProveedor;
    private String fecha;

    public Compra() {
    }

    public Compra(int idProveedor, String fecha) {
        this.idProveedor = idProveedor;
        this.fecha = fecha;
    }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}