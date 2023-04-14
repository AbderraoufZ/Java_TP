package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Personne;

public class PersonneDAOImpl implements PersonneDAO {
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_PAR_LOGIN = "SELECT id, login, password, inscription_date FROM Personne WHERE login = ? AND password= ?";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, login, password, inscription_date FROM Personne WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO Personne (login, password, inscription_date) VALUES (?, ?, NOW())";
	
	PersonneDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
	public void create(Personne personne) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_INSERT, true, personne.getLogin(), personne.getPassword() );
	        int statut = preparedStatement.executeUpdate();
	        // Analyse du statut retourné par la requéte d'insertion 
	        if ( statut == 0 ) {
	            throw new DAOException( "Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        // RÃ©cupÃ©ration de l'id auto-gÃ©nÃ©rÃ© par la requÃªte d'insertion 
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	        	personne.setId( valeursAutoGenerees.getInt( 1 ) );
	        } else {
	            throw new DAOException( "Echec de la création de l'utilisateur en base, pas d'ID auto-généré retournÃ©." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        DAOutils.close( valeursAutoGenerees, preparedStatement, connexion );
	    }

	}

	// connexion avec la base de données et appel à la fonction "loadPersonneFromDb"
	public Personne getPersonne(String login, String password) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Personne personne = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_LOGIN, false, login, password);
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	        	personne = loadPersonneFromDb( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        DAOutils.close( resultSet, preparedStatement, connexion );
	    }

	    return personne;
	}
	
	//recuperation des information de la personne via sont id
	@Override
	public Personne getPersonnee(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Personne personne = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				personne = loadPersonneFromDb( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOutils.close( resultSet, preparedStatement, connexion );
		}
		
		return personne;
	}
	
// récuperation des information de la personne depuis la base de données. 
	private static Personne loadPersonneFromDb( ResultSet resultSet ) throws SQLException {
		Personne personne = new Personne();
		personne.setId( resultSet.getInt( "id" ) );
		personne.setLogin( resultSet.getString( "login" ) );
		personne.setPassword( resultSet.getString( "password" ) );
		personne.setInscriptionDate( resultSet.getTimestamp( "inscription_date" ) );
	    return personne;
	}
}
