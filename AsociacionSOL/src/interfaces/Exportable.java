package interfaces;

public interface Exportable<T> {

	public boolean exportarCaracteres(T elemento, String rutafich) ;

	public boolean exportarBinario(T elemento, String rutafich);

}
