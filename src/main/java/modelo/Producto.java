package modelo;

public class Producto {
    // 1. Atributos (Columnas de tabla SQL)
    private int idProducto;
    private String nombre;
    private String categoria;
    private String descripcion;
    private double precioVenta;
    private int stock;

    // 2. Constructor vacío (Necesario para inicializar objetos sin datos previos)
    public Producto() {
    }

    // 3. Guarda información cuando consultemos la BD
    public Producto(int idProducto, String nombre, String categoria, String descripcion, double precioVenta, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    // 4. Get y Set. Para leer o modificar la información
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}