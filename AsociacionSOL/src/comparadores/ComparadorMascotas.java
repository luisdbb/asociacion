package comparadores;

import java.util.Comparator;

import entidades.Mascota;

public class ComparadorMascotas implements Comparator<Mascota> {

	@Override
	public int compare(Mascota o1, Mascota o2) {
		// -1 si o1 es menor que o2 (va antes que)
		// 0 si o1 es igual que o2
		// +1 si o1 es mayor que o2 (va después de)
		int ret = 0;
		ret = o1.getFechanac().compareTo(o2.getFechanac());
		if (ret == 0) {
			/// hay que desempatar
			ret = o1.getNombre().compareTo(o2.getNombre());
			return ret;
		}

		else
			return ret;
	}

}
