package entidades;

import java.time.LocalDate;

public class Mamifero extends Mascota {

	private int idmamifero;
	private double peso;

	public Mamifero(int id, String nombre, LocalDate fechanac, boolean adoptada, CarnetVacunas carnet, Socio socio,
			int idmamifero, double peso) {
		super(id, nombre, fechanac, adoptada, carnet, socio);
		this.idmamifero = idmamifero;
		this.peso = peso;
	}
	
	
	public Mamifero(Mamifero a) {
		super(a.id, a.nombre, a.fechanac, a.adoptada, 
				(a.carnet != null) ? a.carnet : null,
				(a.socio != null) ? a.socio : null);
		this.idmamifero = a.idmamifero;
		this.peso = a.peso;
	}

	public Mamifero() {
		super();
	}

	public int getIdmamifero() {
		return idmamifero;
	}

	public void setIdmamifero(int idmamifero) {
		this.idmamifero = idmamifero;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

}
