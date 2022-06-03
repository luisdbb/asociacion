package principal;

import entidades.Ave;
import entidades.Categoria;
import entidades.Mascota;
import entidades.Socio;

public class Principal {

	public static void main(String[] args) {
		Categoria.mostrarTodos();
		Mascota m = new Ave();
		
		int[] idsMasjovenMasvieja = Mascota.getIdsMasViejoMasJoven();
		
	 m = Mascota.nuevaMascota();
		
		Socio s1 = new Socio(1, "Luis", null, null, Categoria.VIP, null);
		Socio s2 = s1;
		
	}

}
