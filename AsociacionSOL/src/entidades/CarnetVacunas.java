package entidades;

public class CarnetVacunas {
	private int id;
	private String vacunas;

	public CarnetVacunas() {
	}

	public CarnetVacunas(int id, String vacunas) {
		super();
		this.id = id;
		this.vacunas = vacunas;
	}
	
	
	public CarnetVacunas(CarnetVacunas cv) {
		this.id = cv.id;
		this.vacunas = cv.vacunas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVacunas() {
		return vacunas;
	}

	public void setVacunas(String vacunas) {
		this.vacunas = vacunas;
	}

}
