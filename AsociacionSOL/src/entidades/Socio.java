package entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Socio {
	private int id;
	private String nombre;
	private LocalDate fechanac;
	private LocalDate fechaalta;
	private Categoria categoria;
	private Set<Mascota> mascotas = new HashSet<Mascota>();

	public Socio() {
	}

	public Socio(int id, String nombre, LocalDate fechanac, LocalDate fechaalta, Categoria categoria,
			Set<Mascota> mascotas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechanac = fechanac;
		this.fechaalta = fechaalta;
		this.categoria = categoria;
		this.mascotas = mascotas;
	}

	public Socio(Socio s) {
		this.id = s.id;
		this.nombre = s.nombre;
		this.fechanac = s.fechanac;
		this.fechaalta = s.fechaalta;
		this.categoria = s.categoria;
		this.mascotas = s.mascotas;
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

	public LocalDate getFechanac() {
		return fechanac;
	}

	public void setFechanac(LocalDate fechanac) {
		this.fechanac = fechanac;
	}

	public LocalDate getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(LocalDate fechaalta) {
		this.fechaalta = fechaalta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Mascota> getMascotas() {
		return mascotas;
	}

	public void setMascotas(Set<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public String data() {
		String ret = "";
		ret += this.getId() + "|" + this.getNombre() + "|"
				+ this.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|";
		ret += this.getFechaalta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|";
		ret += this.getCategoria().getId();
		return ret;
	}

	public String mostrarCompleto() {
		String ret = "";
		ret += this.getId() + ". " + this.getNombre() + " ("
				+ this.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ") ";
		ret += " (" + this.getFechaalta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ") ";
		ret += "cat=" + this.getCategoria().getId();
		if (this.getMascotas().isEmpty())
			ret += "\nEste socio NO tiene mascotas.";
		else {
			for (Mascota m : mascotas) {
				if (m.getClass().equals(Ave.class)) {
					// puedo asegurar que m es un Ave
					Ave aux = (Ave) m;
					ret += "" + aux.getId() + ", " + aux.getNombre() + ", idave=" + aux.getIdave() + "\n";
				} else {
					if (m.getClass().equals(Mamifero.class)) {
						// puedo asegurar que m es un Mamifero
						Mamifero aux = (Mamifero) m;
						ret += "" + aux.getId() + ", " + aux.getNombre() + ", idmamifero=" + aux.getIdmamifero() + "\n";
					}
				}
			}
		}

		return ret;
	}

	public static Socio nuevoSocio() {
		Socio ret = null;

		return ret;
	}

}
