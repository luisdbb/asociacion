package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import comparadores.ComparadorMascotas;
import daos.SocioDAO;
import entidades.Ave;
import entidades.Categoria;
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
		}
		while(seguir);
		Iterator it = mascotas.iterator();
		while(it.hasNext()) {
			Mascota masco = (Mascota) it.next();
			System.out.println("idMascota: "+masco.getId()+"\tnombre: "+masco.getNombre()+ "\tFechnac:"+ masco.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
		}
		Collections.sort(mascotas, new ComparadorMascotas());
		
		it = mascotas.iterator();
		while(it.hasNext()) {
			Mascota masco = (Mascota) it.next();
			System.out.println("idMascota: "+masco.getId()+"\tnombre: "+masco.getNombre()+ "\tFechnac:"+ masco.getFechanac().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
		}
		
		
		Socio jose = new Socio(1, "José Carlos García", LocalDate.of(1988, 11, 19), LocalDate.of(2022, 6, 7), Categoria.VIP, null );
		Socio antonia = new Socio(2, "Antonia del Pozo", LocalDate.of(1985, 12, 20), LocalDate.of(2022, 6, 7), Categoria.VIP, null );
		SocioDAO socDAO = new SocioDAO(ConexBD.getCon());
		socDAO.insertarSinID(jose);
		socDAO.insertarSinID(antonia);
		List<Socio> todosSocios = (List<Socio>) socDAO.buscarTodos();
		Socio socioDeId3 = socDAO.buscarPorID(3);
		
		
		
		
		
	}

}
