package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import entidades.Categoria;
import entidades.Socio;
import interfaces.OperacionesCRUD;
import utils.ConexBD;

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
		Categoria ret = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM categorias WHERE id=?";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			pstmt.setLong(1, id);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				int idBD = result.getInt("id");
				String nombre = result.getString("nombre");
				int orden = result.getInt("orden");
				for(Categoria c: Categoria.values()) {
					if(c.getId()==idBD) {
						ret = c; break;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Collection<Categoria> buscarTodos() {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

}
