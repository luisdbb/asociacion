package entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import utils.Utilidades;
import validaciones.Validaciones;

public abstract class Mascota implements Comparable<Mascota> {

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

	@Override
	public int compareTo(Mascota o) {
		int ret = 0;
		ret = this.getFechanac().compareTo(o.getFechanac());
		if (ret == 0) {
			/// hay que desempatar
			ret = this.getNombre().compareTo(o.getNombre());
			return ret;
		} else
			return ret;
	}

	public void importarMascotas() {
		File fichaves = new File("aves.txt");
		File fichmamiferos = new File("mamiferos.txt");
		FileReader fr = null;
		BufferedReader buffer = null;
		String linea;
		Set<Mascota> aves = new TreeSet<Mascota>();
		Set<Mascota> mamiferos = new TreeSet<Mascota>();
		try {
			fr = new FileReader(fichaves);
			buffer = new BufferedReader(fr);
			while ((linea = buffer.readLine()) != null) {
				String[] campos = linea.split("\\|");
				int idave = Integer.parseInt(campos[0]);
				String nombre = campos[1];
				String fechaStr = campos[2];

				String[] camposfecha = fechaStr.split("\\");
				int dia = Integer.parseInt(camposfecha[0]);
				int mes = Integer.parseInt(camposfecha[1]);
				int anio = Integer.parseInt(camposfecha[2]);
				LocalDate fecha = LocalDate.of(dia, mes, anio);
//				LocalDate fechaaux = LocalDate.parse(fechaStr);
				boolean salvaje = Boolean.parseBoolean(campos[3]);
				String vacunas = campos[4];
				CarnetVacunas carnet = null;
				if (vacunas != null && !vacunas.isEmpty()) {
					carnet = new CarnetVacunas();
					carnet.setVacunas(vacunas);
				}
				Ave ave = new Ave();
				ave.setId(idave);
				ave.setNombre(nombre);
				ave.setFechanac(fecha);
				ave.setSalvaje(salvaje);
				if (carnet != null)
					ave.setCarnet(carnet);
				aves.add(ave);
			}
			buffer.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fr = new FileReader(fichmamiferos);
			buffer = new BufferedReader(fr);
			while ((linea = buffer.readLine()) != null) {
				String[] campos = linea.split("\\|");
				int idmamifero = Integer.parseInt(campos[0]);
				String nombre = campos[1];
				String fechaStr = campos[2];

				String[] camposfecha = fechaStr.split("\\");
				int dia = Integer.parseInt(camposfecha[0]);
				int mes = Integer.parseInt(camposfecha[1]);
				int anio = Integer.parseInt(camposfecha[2]);
				LocalDate fecha = LocalDate.of(dia, mes, anio);
//				LocalDate fechaaux = LocalDate.parse(fechaStr);
				double peso = Double.parseDouble(campos[3]);
				String vacunas = campos[4];
				CarnetVacunas carnet = null;
				if (vacunas != null && !vacunas.isEmpty()) {
					carnet = new CarnetVacunas();
					carnet.setVacunas(vacunas);
				}

				Mamifero mamif = new Mamifero();
				mamif.setId(idmamifero);
				mamif.setNombre(nombre);
				mamif.setFechanac(fecha);
				mamif.setPeso(peso);
				if (carnet != null)
					mamif.setCarnet(carnet);
				mamiferos.add(mamif);
			}
			buffer.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Mascota> todas = new LinkedList<Mascota>();
		Iterator it = aves.iterator();
		while(it.hasNext()) {
			Ave a = (Ave) it.next();
			if(!a.getFechanac().isBefore(LocalDate.now().minus(6, ChronoUnit.MONTHS)))
				todas.add(a);
		}
		it = mamiferos.iterator();
		while(it.hasNext()) {
			Mamifero m = (Mamifero) it.next();
			if(!m.getFechanac().isBefore(LocalDate.now().minus(6, ChronoUnit.MONTHS)))
				todas.add(m);
		}
		
		File fichbinario = new File("neonatos.dat");
		FileOutputStream fop = null;
		ObjectOutputStream oos = null;
		try {
			fop = new FileOutputStream(fichbinario);
			oos = new ObjectOutputStream(fop);
			oos.writeObject( (LinkedList<Mascota>) todas);
			oos.flush();
			oos.close();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
