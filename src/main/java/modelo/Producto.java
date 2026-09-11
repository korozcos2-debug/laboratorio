package modelo;

public class Producto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Categoría: " + categoria + 
               " | Precio: $" + precio + " | Stock: " + stock;
    }
}