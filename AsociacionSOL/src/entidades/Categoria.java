package entidades;

public enum Categoria {

	NORMAL( 1, "normal", 3),
	PREMIUM (2, "premium", 2),
	VIP (3, "V.I.P.", 1);

	int id;
	String nombre;
	int orden;

	private Categoria(int id, String nombre, int orden) {
		this.id = id;
		this.nombre = nombre;
		this.orden = orden;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public static void mostrarTodos() {
		for (Categoria c : Categoria.values()) {
			System.out.println(c.getId() + "\t" + c.getOrden() + "\t" + c.getNombre());
		}
	}
	
	
}
