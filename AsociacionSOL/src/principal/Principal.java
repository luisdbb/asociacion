package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import comparadores.ComparadorMascotas;
import daos.MascotaDAO;
import daos.SocioDAO;
import entidades.Ave;
import entidades.Categoria;
import entidades.Mamifero;
import entidades.Mascota;
import entidades.Socio;
import utils.ConexBD;
import utils.Utilidades;

public class Principal {

	public static void main(String[] args) {
		Categoria.mostrarTodos();
		Mascota m = new Ave();

		int[] idsMasjovenMasvieja = Mascota.getIdsMasViejoMasJoven();

		m = Mascota.nuevaMascota();

		Socio s1 = new Socio(1, "Luis", null, null, Categoria.VIP, null);
		s1.exportarCaracteres(s1, "socioLuis.txt");
		s1.exportarBinario(s1, "socioBinario.dat");

		List<Mascota> mascotas = new ArrayList<Mascota>();
		boolean seguir = true;
		do {
			System.out.println("Introduzca los datos de una nueva mascota:");
			mascotas.add(Mascota.nuevaMascota());
			System.out.println("¿Otra mascota más?");
			seguir = Utilidades.leerBoolean();
		} while (seguir);
		Iterator it = mascotas.iterator();
		while (it.hasNext()) {
			Mascota masco = (Mascota) it.next();
			System.out.println("idMascota: " + masco.getId() + "\tnombre: " + masco.getNombre() + "\tFechnac:"
					+ masco.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
		}
		Collections.sort(mascotas, new ComparadorMascotas());

		it = mascotas.iterator();
		while (it.hasNext()) {
			Mascota masco = (Mascota) it.next();
			System.out.println("idMascota: " + masco.getId() + "\tnombre: " + masco.getNombre() + "\tFechnac:"
					+ masco.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
		}

		Socio jose = new Socio(1, "José Carlos García", LocalDate.of(1988, 11, 19), LocalDate.of(2022, 6, 7),
				Categoria.VIP, null);
		Socio antonia = new Socio(2, "Antonia del Pozo", LocalDate.of(1985, 12, 20), LocalDate.of(2022, 6, 7),
				Categoria.VIP, null);
		SocioDAO socDAO = new SocioDAO(ConexBD.getCon());
		socDAO.insertarSinID(jose);
		socDAO.insertarSinID(antonia);
		List<Socio> todosSocios = (List<Socio>) socDAO.buscarTodos();
		Socio socioDeId3 = socDAO.buscarPorID(3);

		/// Ejercicio 11
		/*
		 * Luego, probar este método desde la función principal, recorriendo cada
		 * elemento del array resultante mediante un bucle de tipo for y mostrando por
		 * la pantalla los siguientes datos para cada uno en una línea distinta:
		 * idMamifero=<idmamifero> . <nombre>, <fechanac(dd/MM/yyyy)>,
		 * peso=<peso(xx.xxx)> Kg. Además, si la mascota tiene carnet de vacunación,
		 * añadir: idcarnet=<idcarnet> . vacunas=<vacunas>
		 */
		MascotaDAO masdao = new MascotaDAO(ConexBD.getCon());
		Mamifero[] mamiferos = masdao.buscarSoloMamiferos();
		for (Mamifero mamif : mamiferos) {
			System.out.println("idMamifero=" + mamif.getIdmamifero() + ". " + mamif.getNombre() + ", "
					+ mamif.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", peso="
					+ Utilidades.formatearDecimales(mamif.getPeso(), 3) + "Kgs.");
			if (mamif.getCarnet() != null)
				System.out.println(
						"idcarnet=" + mamif.getCarnet().getId() + ". vacunas=" + mamif.getCarnet().getVacunas());
		}

	}

}
