package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Sites;

public class SitesDAOImpl implements SitesDAO {

		
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Sites WHERE id = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Sites;";
	private static final String SQL_INSERT = "INSERT INTO Sites (name) VALUES (?, ?)";
	
	SitesDAOImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}
	
	public void create(Sites sites) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
	
		try {
	
			connexion = daoFactory.getConnection();
			preparedStatement = DAOutils.initialisationRequetePreparee(
					connexion,
					SQL_INSERT,
					true,
					sites.getName()
					);
			int statut = preparedStatement.executeUpdate();
			if ( statut == 0 ) {
				throw new DAOException( "Echec de la cr ́eation de l'utilisateur, aucune ligne ajout ́ee dans la table." );
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				sites.setId( valeursAutoGenerees.getInt( 1 ) );
			} else {
				throw new DAOException( "Echec de la cr ́eation de l'utilisateur en base, pas d'ID auto-g ́en ́er ́e retourn ́ee." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOutils.close( valeursAutoGenerees, preparedStatement, connexion );
		}
	
	}
	
	@Override
	public Sites getSites(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Sites sites = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				sites = loadSitesFromDb( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOutils.close( resultSet, preparedStatement, connexion );
		}

		return sites;
	}

		@Override
		public ArrayList<Sites> getSitess() throws DAOException, DAOConfigurationException {
			// TODO Auto-generated method stub
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Sites> sitess = new ArrayList<>();

		try {
			connexion = daoFactory.getConnection();
		preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
		resultSet = preparedStatement.executeQuery();
		while ( resultSet.next() ) {
			sitess.add(loadSitesFromDb( resultSet ));
		}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOutils.close( resultSet, preparedStatement, connexion );
		}
		return sitess;
		}
		
		private static Sites loadSitesFromDb( ResultSet resultSet ) throws SQLException {
			Sites sites = new Sites();
			sites.setId( resultSet.getInt( "id" ) );
			sites.setName( resultSet.getString( "name" ) );

			return sites;
		}
}
