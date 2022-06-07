package daos;

import java.sql.Connection;
import java.util.Collection;

import entidades.Categoria;
import interfaces.OperacionesCRUD;

public class CategoriaDAO implements OperacionesCRUD<Categoria> {
	Connection conex;

	public CategoriaDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}

	@Override
	public long insertarSinID(Categoria elemento) {
		// TODO Esbozo de método generado automáticamente
		return 0;
	}

	@Override
	public Categoria buscarPorID(long id) {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

	@Override
	public Collection<Categoria> buscarTodos() {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

}
