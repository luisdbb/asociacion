package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import entidades.Ave;
import entidades.CarnetVacunas;
import entidades.Mamifero;
import entidades.Mascota;
import entidades.Socio;
import interfaces.OperacionesCRUD;
import utils.ConexBD;

public class CarnetDAO implements OperacionesCRUD<CarnetVacunas> {

	Connection conex;

	public CarnetDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}

	@Override
	public long insertarSinID(CarnetVacunas elemento) {
		long ret = -1;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "insert into carnets(vacunas) values (?)";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			pstmt.setString(1, elemento.getVacunas());
			int resultadoInsercion = pstmt.executeUpdate();
			if (resultadoInsercion == 1) {
				String consultaSelect = "SELECT id FROM carnets WHERE (vacunas=?)";
				PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
				pstmt2.setString(1, elemento.getVacunas());
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
	public CarnetVacunas buscarPorID(long id) {
		CarnetVacunas ret = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM carnets WHERE id=?";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			pstmt.setLong(1, id);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				long idBD = result.getLong("id");
				String vacunas = result.getString("vacunas");
				ret = new CarnetVacunas();
				ret.setId(Integer.valueOf("" + idBD));
				ret.setVacunas(vacunas);
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
	public Collection<CarnetVacunas> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
