package comparadores;

import java.util.Comparator;

import entidades.Mascota;

public class ComparadorMascotas implements Comparator<Mascota> {

	@Override
	public int compare(Mascota o1, Mascota o2) {
		//-1 si o1 es menor que o2 (va antes que)
		// 0 si o1 es igual que o2
		//+1 si o1 es mayor que o2 (va después de) 
		return 0;
	}

}
