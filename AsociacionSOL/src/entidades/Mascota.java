package entidades;

import java.time.LocalDate;
import java.util.Scanner;

import utils.Utilidades;
import validaciones.Validaciones;

public abstract class Mascota {

	protected int id;
	protected String nombre;
	protected LocalDate fechanac;
	protected boolean adoptada = false;
	protected CarnetVacunas carnet = null;
	protected Socio socio = null;

	private static int[] idsMasViejoMasJoven = new int[2];
//	idsMasViejoMasJoven[0] --> idMascota más vieja
//	idsMasViejoMasJoven[1] --> idMascota más joven
	private static int idMasViejo;
	private static int idMasJoven;
	private static LocalDate fechaMasVieja;
	private static LocalDate fechaMasJoven;

	public Mascota() {
	}

	public Mascota(Mascota m) {
		this.id = m.id;
		this.nombre = m.nombre;
		this.fechanac = m.fechanac;
		this.adoptada = m.adoptada;
		if (m.carnet != null)
			this.carnet = m.carnet;
		if (m.socio != null)
			this.socio = m.socio;
	}

	public static Mascota nuevaMascota() {
		Mascota ret = null;
		Scanner in = new Scanner(System.in);
		boolean valido = false;

		String nombre = "";
		do {
			System.out.println("Introduzca el nombre de la nueva mascota: (solo letras entre 3 y 150 caracteres)");
			nombre = in.next();
			valido = Validaciones.validarNombreMascota(nombre);
			if (!valido)
				System.out.println("Valor para el nombre de la nueva mascota NO válido.");
		} while (!valido);

		LocalDate fechanac = null;
		do {
			System.out.println("Introduzca la fecha de nacimiento de la nueva mascota: (no posterior a hoy)");
			fechanac = Utilidades.leerFecha();
			valido = Validaciones.validarFechaNacimientoMascota(fechanac);
			if (!valido)
				System.out.println(
						"Valor para la fecha de naimiento de la nueva mascota NO válida. Ha de ser posterior a hoy.");
		} while (!valido);

		System.out.println("¿Es la nueva mascota adoptada?");
		boolean adoptada = Utilidades.leerBoolean();

		System.out.println("¿La nueva mascota es un Ave?");
		boolean esave = Utilidades.leerBoolean();
		int idave = 0;
		int idmamifero = 0;
		boolean salvaje = false;
		double peso = 0.0;
		if (esave) {
			ret = new Ave();
			do {
				System.out.println("Introduzca el id del ave:");
				idave = in.nextInt();
				valido = Validaciones.validarId(idave);
				if (!valido)
					System.out.println("Valo del id de la nueva ave NO válida. Ha de ser > 0.");
			} while (!valido);
			((Ave) ret).setIdave(idave);

			System.out.println("¿Es el nuevo ave salvaje?");
			salvaje = Utilidades.leerBoolean();
			((Ave) ret).setSalvaje(salvaje);

		} else {
			ret = new Mamifero();
			do {
				System.out.println("Introduzca el id del mamifero:");
				idmamifero = in.nextInt();
				valido = Validaciones.validarId(idmamifero);
				if (!valido)
					System.out.println("Valo del id del nuevo mamifero NO válida. Ha de ser > 0.");
			} while (!valido);
			((Mamifero) ret).setIdmamifero(idmamifero);

			do {
				System.out.println("Introduzca el peso del mamifero:");
				peso = Utilidades.leerDouble();
				valido = Validaciones.validarPeso(peso);
				if (!valido)
					System.out.println("Valo del peso del nuevo mamifero NO válido. Ha de ser > 0.");
			} while (!valido);
			((Mamifero) ret).setPeso(peso);
		}

		ret.setNombre(nombre);
		ret.setAdoptada(adoptada);
		ret.setFechanac(fechanac);

		System.out.println("¿Vas a introducir los datos de vacunacion de la nueva mascota?");
		boolean respuesta = Utilidades.leerBoolean();
		CarnetVacunas carnet = null;
		if (respuesta) {
			String vacunas = "";
			valido = false;
			do {
				System.out.println("Introduzca los datos de vacunacion:");
				vacunas = in.nextLine();
				valido = Validaciones.validarVacunas(vacunas);
				if (!valido)
					System.out.println("Valo de las vacunas de la nueva mascota NO válido.");
			} while (!valido);
		}
		ret.setCarnet(carnet);
		Socio socio = null;
		System.out.println("¿Vas a introducir los datos un nuevo socio para la nueva mascota?");
		respuesta = Utilidades.leerBoolean();
		if (respuesta) {
			socio = Socio.nuevoSocio();
		}
		ret.setSocio(socio);

		return ret;
	}

	public static int[] getIdsMasViejoMasJoven() {
		return idsMasViejoMasJoven;
	}

	public Mascota(int id, String nombre, LocalDate fechanac, boolean adoptada, CarnetVacunas carnet, Socio socio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechanac = fechanac;
		this.adoptada = adoptada;
		this.carnet = carnet;
		this.socio = socio;

		if (fechanac.isAfter(this.fechaMasJoven)) {
			this.idMasJoven = id;
			this.fechaMasJoven = fechanac;
			idsMasViejoMasJoven[1] = idMasJoven;

		}
		if (fechanac.isBefore(this.fechaMasVieja)) {
			this.idMasViejo = id;
			this.fechaMasVieja = fechanac;
			idsMasViejoMasJoven[0] = idMasViejo;
		}

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

	public boolean isAdoptada() {
		return adoptada;
	}

	public void setAdoptada(boolean adoptada) {
		this.adoptada = adoptada;
	}

	public CarnetVacunas getCarnet() {
		return carnet;
	}

	public void setCarnet(CarnetVacunas carnet) {
		this.carnet = carnet;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}
