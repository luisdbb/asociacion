package entidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import interfaces.Exportable;
import utils.Utilidades;
import validaciones.Validaciones;

public class Socio implements Exportable<Socio>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 460939272535361888L;
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
		Scanner in = new Scanner(System.in);
		boolean valido = false;

		String nombre = "";
		do {
			System.out.println("Introduzca el nombre del nuevo socio: (solo letras entre 3 y 150 caracteres)");
			try {
				nombre = in.next();
				valido = Validaciones.validarNombreSocio(nombre);
			} catch (NoSuchElementException ex) {
				System.out.println("Se ha producido una NoSuchElementException" + ex.getMessage());
				ex.printStackTrace();
			} catch (IllegalStateException ex) {
				System.out.println("Se ha producido una IllegalStateException" + ex.getMessage());
				ex.printStackTrace();
			} catch (PatternSyntaxException ex) {
				System.out.println("Se ha producido una PatternSyntaxException" + ex.getMessage());
				ex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("Se ha producido una Exception" + ex.getMessage());
				ex.printStackTrace();
			}
			if (!valido)
				System.out.println("Valor para el nombre del nuevo socio NO válido.");
		} while (!valido);

		LocalDate fechanac = null;
		do {
			System.out.println("Introduzca la fecha de nacimiento del nuevo socio: (>16años)");
			fechanac = Utilidades.leerFecha();
			valido = Validaciones.validarFechaNacimientoSocio(fechanac);
			if (!valido)
				System.out.println("Valor para la fecha de naimiento del nuevo socio NO válida. Ha de ser > 16años.");
		} while (!valido);

		valido = false;
		int elegido = -1;
		Categoria cat = null;
		do {
			System.out.println("Introduzca el id de la categoria de entre las siguientes:");
			Categoria.mostrarTodos();
			elegido = in.nextInt();
			switch (elegido) {
			case 1:
				cat = Categoria.NORMAL;
				break;
			case 2:
				cat = Categoria.PREMIUM;
				break;
			case 3:
				cat = Categoria.VIP;
				break;
			default:
				cat = null;
				break;
			}
			valido = (cat != null);
			if (!valido)
				System.out.println("El valor elegido para el id de la categroia no es válido.");
		} while (!valido);

		boolean seguir = true;
		System.out.println("¿Desea introducir las mascotas del nuevo socio?");
		seguir = Utilidades.leerBoolean();
		Set<Mascota> mascotas = new HashSet<Mascota>();
		if (seguir) {
			// pedir los datos de las mascotas
			do {
				System.out.println("Introduzca los datos de la mascota");
				Mascota m = Mascota.nuevaMascota();
				if (m != null)
					mascotas.add(m);
				System.out.println("¿Desea introducir otra nueva mascota?");
				seguir = Utilidades.leerBoolean();
			} while (seguir);
		}
		ret = new Socio();
		ret = new Socio(-1, nombre, fechanac, LocalDate.now(), cat, mascotas);

		return ret;
	}

	@Override
	public boolean exportarCaracteres(Socio elemento, String rutafich) {
		boolean ret = false;
		File f = new File(rutafich);
		FileWriter fo = null;
		try {
			fo = new FileWriter(f);
			fo.write(elemento.mostrarCompleto());
			fo.flush();
			fo.close();
			System.out.println("Se han exportado correctamente el socio.");
			ret = true;
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public boolean exportarBinario(Socio elemento, String rutafich) {
		boolean ret = false;
		File f = new File(rutafich);
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fo);
			oos.writeObject((Socio) elemento);
			oos.flush();
			oos.close();
			fo.close();
			System.out.println("Se han exportado correctamente el socio.");
			ret = true;
		} catch (FileNotFoundException e) {
			System.out.println("Se ha producido una FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Se ha producido una IOException:" + e.getMessage());
			e.printStackTrace();
		}

		return ret;
	}

}
