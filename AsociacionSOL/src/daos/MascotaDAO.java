package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import entidades.*;
import interfaces.OperacionesCRUD;
import utils.ConexBD;

public class MascotaDAO implements OperacionesCRUD<Mascota> {
	Connection conex;

	public MascotaDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}

	@Override
	public long insertarSinID(Mascota m) {

		long ret = -1;
		String consultaInsertStr1 = "insert into mascotas(nombre, fechanac, adoptada, idave, idmamifero, salvaje, peso, idcarnet, idsocio ) values (?,?,?,?,?,?,?,?,?)";
		try {
			if (this.conex == null || this.conex.isClosed())
				this.conex = ConexBD.establecerConexion();
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr1);
			pstmt.setString(1, m.getNombre());
			pstmt.setDate(2,java.sql.Date.valueOf(m.getFechanac()));
			pstmt.setBoolean(3, m.isAdoptada());
			if (m.getClass().equals(Ave.class)) {
				m = (Ave) m;
				pstmt.setLong(4, ((Ave) m).getIdave());
				pstmt.setNull(5, java.sql.Types.INTEGER);
				pstmt.setBoolean(6, ((Ave) m).isSalvaje());
				pstmt.setNull(7, java.sql.Types.DECIMAL);
			} else if (m.getClass().equals(Mamifero.class)) {
				m = (Mamifero) m;
				pstmt.setNull(4, java.sql.Types.INTEGER);
				pstmt.setLong(5, ((Mamifero) m).getIdmamifero());
				pstmt.setNull(6, java.sql.Types.BOOLEAN);
				pstmt.setDouble(7, ((Mamifero) m).getPeso());
			}
			if (m.getCarnet() != null) {
				if (m.getCarnet().getId() > 0) {
					pstmt.setLong(8, m.getCarnet().getId());
				} else {
					CarnetDAO carnetdao = new CarnetDAO(conex);
					CarnetVacunas carnet = new CarnetVacunas();
					carnet.setVacunas(m.getCarnet().getVacunas());
					long idcarnetnuevo = carnetdao.insertarSinID(carnet);
					pstmt.setLong(8, idcarnetnuevo);
				}
			} else
				pstmt.setNull(8, java.sql.Types.INTEGER);
			if (m.getSocio() != null)
				pstmt.setLong(9, m.getSocio().getId());
			else
				pstmt.setNull(9, java.sql.Types.INTEGER);

			int resultadoInsercion = pstmt.executeUpdate();
			if (resultadoInsercion == 1) {
				String consultaSelect = "SELECT id FROM mascotas WHERE ";
				if (m.getClass().equals(Ave.class)) {
					consultaSelect += " idave=?";
				} else if (m.getClass().equals(Mamifero.class)) {
					consultaSelect += " idmamifero=?";
				}
				PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
				if (m.getClass().equals(Ave.class)) {
					pstmt2.setLong(1, ((Ave) m).getIdave());
				} else if (m.getClass().equals(Mamifero.class)) {
					pstmt2.setLong(1, ((Mamifero) m).getIdmamifero());
				}

				ResultSet result = pstmt2.executeQuery();
				while (result.next()) {
					long idmetal = result.getLong("id");
					if (idmetal != -1)
						ret = idmetal;
				}
				result.close();
				pstmt2.close();
			}
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
			return -1;
		}

		return ret;
	}

	@Override
	public Mascota buscarPorID(long id) {
		Mascota ret = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM mascotas WHERE id=?";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			pstmt.setLong(1, id);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				// nombre, fechanac, adoptada, idave, idmamifero, salvaje, peso, idcarnet,
				// idsocio
				long idBD = result.getLong("id");
				String nombre = result.getString("nombre");
				java.sql.Date fecha = result.getDate("fechanac");
				LocalDate fechaLD = fecha.toLocalDate();
				boolean adoptada = result.getBoolean("adoptada");
				Long idave = Long.valueOf(result.getLong("idave"));
				Long idmamifero = Long.valueOf(result.getLong("idmamifero"));
				Boolean salvaje = result.getBoolean("salvaje");
				Double peso = result.getDouble("peso");
				Long idcarnet = Long.valueOf(result.getLong("idcarnet"));
				Long idsocio = Long.valueOf(result.getLong("idsocio"));

				if (idave != null) {
					ret = new Ave();
					((Ave) ret).setIdave(Integer.valueOf("" + idave));
					((Ave) ret).setSalvaje(salvaje);
				} else if (idmamifero != null) {
					ret = new Mamifero();
					((Mamifero) ret).setIdmamifero(Integer.valueOf("" + idmamifero));
					((Mamifero) ret).setPeso(peso);
				}
				ret.setId(Integer.valueOf("" + idBD));
				ret.setNombre(nombre);
				ret.setAdoptada(adoptada);
				ret.setFechanac(fechaLD);
				if (idcarnet != null) {
					CarnetDAO carnetdao = new CarnetDAO(conex);
					CarnetVacunas carnet = carnetdao.buscarPorID(idsocio);
					ret.setCarnet(carnet);
				}
				if (idsocio != null) {
					SocioDAO sociodao = new SocioDAO(conex);
					Socio socio = sociodao.buscarPorID(idsocio);
					ret.setSocio(socio);
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
	public Collection<Mascota> buscarTodos() {
		Set<Mascota> todas = new HashSet<Mascota>();
		Mascota ret = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM mascotas";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				// nombre, fechanac, adoptada, idave, idmamifero, salvaje, peso, idcarnet,
				// idsocio
				long idBD = result.getLong("id");
				String nombre = result.getString("nombre");
				java.sql.Date fecha = result.getDate("fechanac");
				LocalDate fechaLD = fecha.toLocalDate();
				boolean adoptada = result.getBoolean("adoptada");
				Long idave = Long.valueOf(result.getLong("idave"));
				Long idmamifero = Long.valueOf(result.getLong("idmamifero"));
				Boolean salvaje = result.getBoolean("salvaje");
				Double peso = result.getDouble("peso");
				Long idcarnet = Long.valueOf(result.getLong("idcarnet"));
				Long idsocio = Long.valueOf(result.getLong("idsocio"));

				if (idave != null) {
					ret = new Ave();
					((Ave) ret).setIdave(Integer.valueOf("" + idave));
					((Ave) ret).setSalvaje(salvaje);
				} else if (idmamifero != null) {
					ret = new Mamifero();
					((Mamifero) ret).setIdmamifero(Integer.valueOf("" + idmamifero));
					((Mamifero) ret).setPeso(peso);
				}
				ret.setId(Integer.valueOf("" + idBD));
				ret.setNombre(nombre);
				ret.setAdoptada(adoptada);
				ret.setFechanac(fechaLD);
				if (idcarnet != null) {
					CarnetDAO carnetdao = new CarnetDAO(conex);
					CarnetVacunas carnet = carnetdao.buscarPorID(idsocio);
					ret.setCarnet(carnet);
				}
				if (idsocio != null) {
					SocioDAO sociodao = new SocioDAO(conex);
					Socio socio = sociodao.buscarPorID(idsocio);
					ret.setSocio(socio);
				}
				todas.add(ret);
			}
		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return todas;
	}

	/*
	 * Ejercicio 11 Mamiferos[] buscarSoloMamiferos() a la clase anterior para que
	 * devuelva un array de objetos de tipo Mamifero con la colección de las filas
	 * de la tabla mascotas de la BD que corresponden a mamíferos únicamente.
	 */

	public Mamifero[] buscarSoloMamiferos() {
		Mamifero[] ret;
		Set<Mascota> todas = new HashSet<Mascota>();
		Mascota mascota = null;
		Connection conex = ConexBD.establecerConexion();
		String consultaInsertStr = "select * FROM mascotas where idmamifero is not NULL";
		try {
			PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				// nombre, fechanac, adoptada, idave, idmamifero, salvaje, peso, idcarnet,
				// idsocio
				long idBD = result.getLong("id");
				String nombre = result.getString("nombre");
				java.sql.Date fecha = result.getDate("fechanac");
				LocalDate fechaLD = fecha.toLocalDate();
				boolean adoptada = result.getBoolean("adoptada");
				Long idave = Long.valueOf(result.getLong("idave"));
				Long idmamifero = Long.valueOf(result.getLong("idmamifero"));
				Boolean salvaje = result.getBoolean("salvaje");
				Double peso = result.getDouble("peso");
				Long idcarnet = Long.valueOf(result.getLong("idcarnet"));
				Long idsocio = Long.valueOf(result.getLong("idsocio"));

				if (idave != null) {
					mascota = new Ave();
					((Ave) mascota).setIdave(Integer.valueOf("" + idave));
					((Ave) mascota).setSalvaje(salvaje);
				} else if (idmamifero != null) {
					mascota = new Mamifero();
					((Mamifero) mascota).setIdmamifero(Integer.valueOf("" + idmamifero));
					((Mamifero) mascota).setPeso(peso);
				}
				mascota.setId(Integer.valueOf("" + idBD));
				mascota.setNombre(nombre);
				mascota.setAdoptada(adoptada);
				mascota.setFechanac(fechaLD);
				if (idcarnet != null) {
					CarnetDAO carnetdao = new CarnetDAO(conex);
					CarnetVacunas carnet = carnetdao.buscarPorID(idsocio);
					mascota.setCarnet(carnet);
				}
				if (idsocio != null) {
					SocioDAO sociodao = new SocioDAO(conex);
					Socio socio = sociodao.buscarPorID(idsocio);
					mascota.setSocio(socio);
				}
				todas.add(mascota);
			}

		} catch (SQLException e) {
			System.out.println("Se ha producido una SQLException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception:" + e.getMessage());
			e.printStackTrace();
		}
		ret = (Mamifero[]) todas.toArray();
		return ret;
	}

}
