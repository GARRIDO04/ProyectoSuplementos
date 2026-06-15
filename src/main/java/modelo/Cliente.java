package modelo;

public class Cliente {
    // 1. Atributos (Tus columnas de MySQL)
    private int idCliente;
    private String nombre;
    private String telefono;
    private String correo;

    // 2. Constructor vacío
    public Cliente() {
    }

    // 3. Constructor lleno
    public Cliente(int idCliente, String nombre, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    // 4. Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}