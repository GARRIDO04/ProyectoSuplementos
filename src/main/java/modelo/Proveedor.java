package modelo;

public class Proveedor {
    // 1. Atributos (Columnas de tu tabla en MySQL)
    private int idProveedor;
    private String nombre;
    private String correo;
    private String direccion;
    private String contacto;
    private String rfc;

    // 2. Constructor vacío (Necesario para inicializar objetos limpios)
    public Proveedor() {
    }

    // 3. Constructor lleno (Para cuando extraigamos la info de la BD)
    public Proveedor(int idProveedor, String nombre, String correo, String direccion, String contacto, String rfc) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.contacto = contacto;
        this.rfc = rfc;
    }

    // 4. Getters y Setters (Para leer o inyectar los datos)
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}