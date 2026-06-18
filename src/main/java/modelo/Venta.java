package modelo;

public class Venta {
    private int idVenta;
    private int idCliente;
    private String fecha; // Se puede manejar como String para simplificar el parseo de MySQL
    private double subtotal;
    private double iva;
    private double total;
    private String regimenFiscal;

    // Constructor vacío
    public Venta() {
    }

    // Constructor con parámetros (sin ID para inserciones)
    public Venta(int idCliente, String fecha, double subtotal, double iva, double total, String regimenFiscal) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.regimenFiscal = regimenFiscal;
    }

    // Getters y Setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getRegimenFiscal() { return regimenFiscal; }
    public void setRegimenFiscal(String regimenFiscal) { this.regimenFiscal = regimenFiscal; }
}