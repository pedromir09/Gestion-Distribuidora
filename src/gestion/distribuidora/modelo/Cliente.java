package gestion.distribuidora.modelo;

/**
 * Cliente de la distribuidora.
 */
public class Cliente {

    private Long id;   // lo genera la base de datos
    private String nombreApellido;
    private String direccion;
    private String cuit;
    private String telefono;

    /**
     * Crea un cliente nuevo, todavia sin id.
     * @param nombreApellido nombre y apellido o razon social
     * @param direccion direccion de entrega
     * @param cuit CUIT, con o sin guiones
     * @param telefono telefono de contacto
     */
    public Cliente(String nombreApellido, String direccion, String cuit, String telefono) {
        this.nombreApellido = nombreApellido;
        this.direccion = direccion;
        setCuit(cuit);
        this.telefono = telefono;
    }

    /**
     * Devuelve el id que asigno la base, o null si todavia no se guardo.
     * @return el id que asigno la base, o null si todavia no se guardo
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id que asigno la base.
     * @param id el id que asigno la base
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre y apellido.
     * @return el nombre y apellido
     */
    public String getNombreApellido() {
        return nombreApellido;
    }

    /**
     * Asigna el nuevo nombre y apellido.
     * @param nombreApellido el nuevo nombre y apellido
     */
    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    /**
     * Devuelve la direccion.
     * @return la direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Asigna la nueva direccion.
     * @param direccion la nueva direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve el CUIT con formato XX-XXXXXXXX-X.
     * @return el CUIT con formato XX-XXXXXXXX-X
     */
    public String getCuit() {
        return cuit;
    }

    /**
     * Guarda el CUIT. Si viene con 11 digitos sin guiones, se los agrega
     * para que siempre quede con el mismo formato.
     * @param cuit el CUIT, con o sin guiones
     */
    public void setCuit(String cuit) {
        if (cuit != null && cuit.matches("\\d{11}")) {
            cuit = cuit.substring(0, 2) + "-" + cuit.substring(2, 10) + "-" + cuit.substring(10);
        }
        this.cuit = cuit;
    }

    /**
     * Devuelve el telefono.
     * @return el telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna el nuevo telefono.
     * @param telefono el nuevo telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Verifica que el cliente cumpla las reglas de la distribuidora.
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void validar() {
        if (nombreApellido == null || nombreApellido.isBlank()) {
            throw new IllegalArgumentException("El nombre y apellido es obligatorio");
        }
        if (cuit == null || !cuit.matches("\\d{2}-\\d{8}-\\d")) {
            throw new IllegalArgumentException("El CUIT debe tener 11 numeros (por ejemplo 20-12345678-9)");
        }
    }

    /**
     * Devuelve el nombre y apellido, como se muestra en los desplegables.
     * @return el nombre y apellido, como se muestra en los desplegables
     */
    @Override
    public String toString() {
        return nombreApellido;
    }
}
