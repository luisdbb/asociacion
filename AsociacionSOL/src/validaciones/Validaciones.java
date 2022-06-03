package validaciones;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

	/**
	 * Valida que una cadena de caracteres contiene letras o espacios únicamente,
	 * longitud entre 3 y 50 caractreres
	 * 
	 * @param nombre cadena con el nombre a validar
	 * @return true si es un nombre válido o false en caso contrario
	 */
	public static boolean validarNombreSocio(String nombre) {
		Pattern patron = Pattern.compile("[A-Za-zñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]{3,150}");
		Matcher comprobacion = patron.matcher(nombre);
		return comprobacion.matches();
	}

	public static boolean validarFechaNacimientoMascota(LocalDate fechanac) {
		return fechanac.isBefore(LocalDate.now());
	}

	public static boolean validarId(int idave) {
		return idave > 0;
	}

	public static boolean validarPeso(double peso) {
		return peso > 0;
	}

	public static boolean validarVacunas(String vacunas) {
		return vacunas.length() <= 500;
	}

	public static boolean validarNombreMascota(String nombre) {
		Pattern patron = Pattern.compile("[A-Za-zñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0123456789]{2,30}");
		Matcher comprobacion = patron.matcher(nombre);
		return comprobacion.matches();
	}

	public static boolean validarFechaNacimientoSocio(LocalDate fechanac) {
		LocalDate hoyMenos16Anios = LocalDate.now().minusYears(16);
		return fechanac.isBefore(hoyMenos16Anios);
	}

}
