package pojo;

public class Repuesto {
	private int id;
	private String cod;
	private String nombre;
	private int stock;
	private double precio;

	public Repuesto() {
	}

	public Repuesto(int id, String cod, String nombre, int stock, double precio) {
		this.id = id;
		this.cod = cod;
		this.nombre = nombre;
		this.stock = stock;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
