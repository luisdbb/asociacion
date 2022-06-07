package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import entidades.Categoria;
import entidades.Socio;
import interfaces.OperacionesCRUD;
import utils.ConexBD;


public class SocioDAO implements OperacionesCRUD<Socio> {

	Connection conex;

	public SocioDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}

	@Override
	public long insertarSinID(Socio elemento) {
		long ret = -1;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "insert into socios(nombre, fechanac, fechaalta, idcategoria) values (?,?,?,?)";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);

			pstmt.setString(1, elemento.getNombre());
			pstmt.setDate(2, java.sql.Date.valueOf(elemento.getFechanac()));
			pstmt.setDate(3, java.sql.Date.valueOf(elemento.getFechaalta()));
			pstmt.setInt(4, elemento.getCategoria().getId());
			
			int resultadoInsercion = pstmt.executeUpdate();
			if (resultadoInsercion == 1) {
				String consultaSelect = "SELECT id FROM socios WHERE (idcategoria=? AND nombre=?)";
				PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
				pstmt2.setInt(1, elemento.getCategoria().getId());
				pstmt2.setString(2, elemento.getNombre());
				ResultSet result = pstmt2.executeQuery();
				while (result.next()) {
					long id = result.getLong("id");
					if (id != -1)
						ret = id;
				}
				result.close();
				pstmt2.close();
			}
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
			return -1;
		}

		return ret;
	}

	@Override
	public Socio buscarPorID(long id) {
		Socio ret = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM socios WHERE id=?";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			pstmt.setLong(1, id);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				int idBD = result.getInt("id");
				int idcat = result.getInt("idcategoria");
				String nombre = result.getString("nombre");
				LocalDate fechanac = result.getDate("fechanac").toLocalDate();
				LocalDate fechaalta = result.getDate("fechaalta").toLocalDate();
				ret = new Socio();
				ret.setId(idBD);
				ret.setNombre(nombre);
				ret.setFechaalta(fechanac);
				ret.setFechaalta(fechaalta);
				CategoriaDAO catDAO = new CategoriaDAO(this.conex);
				ret.setCategoria(catDAO.buscarPorID(idcat));
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
	public Collection<Socio> buscarTodos() {

		List<Socio> todos = new ArrayList<>();
		String consultaInsertStr = "select * FROM socios";
		try {
			if (this.conex == null || this.conex.isClosed())
				this.conex = ConexBD.establecerConexion();
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Socio s = new Socio();
				int idBD = result.getInt("id");
				String nombre = result.getString("nombre");
				java.sql.Date fechanac = result.getDate("fechanac");
				java.sql.Date fechaalta = result.getDate("fechaalta");
				int idcat = result.getInt("idcategoria");
				s.setId(idBD);
				s.setNombre(nombre);
				LocalDate fechaaltaLD = fechaalta.toLocalDate();
				s.setFechaalta(fechaaltaLD);
				LocalDate fechanacLD = fechanac.toLocalDate();
				s.setFechanac(fechanacLD);
				CategoriaDAO catDAO = new CategoriaDAO(ConexBD.establecerConexion());
				Categoria cat = catDAO.buscarPorID(idcat);
				s.setCategoria(cat);
				todos.add(s);
			}
			if (conex != null)
				conex.close();
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return todos;
	}

}
