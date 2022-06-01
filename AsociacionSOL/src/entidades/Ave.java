package entidades;

import java.time.LocalDate;

public class Ave extends Mascota {

	private int idave;
	private boolean salvaje;

	public Ave(int id, String nombre, LocalDate fechanac, boolean adoptada, CarnetVacunas carnet, Socio socio,
			int idave, boolean salvaje) {
		super(id, nombre, fechanac, adoptada, carnet, socio);
		this.idave = idave;
		this.salvaje = salvaje;
	}

	public Ave() {
		super();
	}

	public Ave(Ave a) {
		super(a.id, a.nombre, a.fechanac, a.adoptada, 
				(a.carnet != null) ? a.carnet : null,
				(a.socio != null) ? a.socio : null);
		this.idave = a.idave;
		this.salvaje = a.salvaje;
	}

	public int getIdave() {
		return idave;
	}

	public void setIdave(int idave) {
		this.idave = idave;
	}

	public boolean isSalvaje() {
		return salvaje;
	}

	public void setSalvaje(boolean salvaje) {
		this.salvaje = salvaje;
	}

}
