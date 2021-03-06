package interfaces;

import java.util.Collection;

public interface OperacionesCRUD<T> {

	/***
	 * Este método inserta en la tabla correspondiente de la BD bdfederacion un
	 * nuevo registro
	 * 
	 * @param elemento del tipo que se quiere insertar como nuevo elemento completo
	 *                 (sin ID, que es autocalculado)
	 * @return id del nuevo elemento insertado si tuvo éxito, o -1 en caso contarrio
	 */
	public long insertarSinID(T elemento);

	/***
	 * Funcion que busca en la tabla correspondiente si hay un elemento cuyo id
	 * coincide con el que se pasa como parámero
	 * 
	 * @param id identificador del elemento a buscar
	 * @return el elemento si existe o null si no
	 */
	public T buscarPorID(long id);

	/**
	 * Funcion que devuelva la coleccion de todos los elementos de un tipo
	 * 
	 * @return la coleccion de elementos que puede ser vacía
	 */
	public Collection<T> buscarTodos();

}
