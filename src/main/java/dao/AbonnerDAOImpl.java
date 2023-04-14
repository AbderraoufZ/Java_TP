package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Abonner;
import beans.Personne;
import beans.Sites;

public class AbonnerDAOImpl implements AbonnerDAO {
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_PAR_COUPLE = "SELECT * FROM Abonner WHERE id_personne = ? AND id_sites = ?";
	private static final String SQL_SELECT_PAR_PERSONNE = "SELECT * FROM Abonner WHERE id_personne = ?";
	private static final String SQL_SELECT_PAR_COOKIE = "SELECT * FROM Abonner WHERE id_sites = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Abonner;";
	private static final String SQL_INSERT = "INSERT INTO Abonner VALUE (?,?)";

		AbonnerDAOImpl( DAOFactory daoFactory ) {
			this.daoFactory = daoFactory;
		}

		@Override
		public void create(Abonner abonner) throws DAOException, DAOConfigurationException {
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet valeursAutoGenerees = null;
			
			try {
				//connexion a la BDD
				connexion = daoFactory.getConnection();
				// insertion dans la base de donnée l'abonnement enregistre
				preparedStatement = DAOutils.initialisationRequetePreparee(connexion, SQL_INSERT, true, abonner.getPersonne().getId(),abonner.getSites().getId());
				System.out.println(abonner.getPersonne().getId() + " - " + abonner.getSites().getId());
				int statut = preparedStatement.executeUpdate();
				if ( statut == 0 ) {
					throw new DAOException( "Echec de la cr ́eation du lien, aucune ligne ajout ́ee dans la table." );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				DAOutils.close( valeursAutoGenerees, preparedStatement, connexion );
			}
		}

		
		@Override
		public Abonner getAbonnerParCouple(Personne personne, Sites sites) throws DAOException, DAOConfigurationException {
			// TODO Auto-generated method stub
			Connection connexion = null;

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Abonner comporte = null;

			try {
				connexion = daoFactory.getConnection();
				preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_COUPLE, false, personne.getId(), sites.getId() );
				resultSet = preparedStatement.executeQuery();
				if ( resultSet.next() ) {
					comporte = loadAbonnerFromDb( resultSet );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				DAOutils.close( resultSet, preparedStatement, connexion );
			}


			return comporte;
		}

		// récuprer les abonnements d'une personne
		@Override
		public ArrayList<Abonner> getAbonnerParPersonne(Personne personne) throws DAOException, DAOConfigurationException {
			// TODO Auto-generated method stub
				Connection connexion = null;
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				ArrayList<Abonner> abonner = new ArrayList<>();

				try {
					connexion = daoFactory.getConnection();
					preparedStatement = DAOutils.initialisationRequetePreparee(
							connexion,
							SQL_SELECT_PAR_PERSONNE,
							false,
							personne.getId()
							);
					resultSet = preparedStatement.executeQuery();
					while ( resultSet.next() ) {
						abonner.add(loadAbonnerFromDb( resultSet ));
					}
				} catch ( SQLException e ) {
					throw new DAOException( e );
				} finally {
					DAOutils.close( resultSet, preparedStatement, connexion );
				}


				return abonner;
		}

		// récuprer les abonnements d'un site
		@Override
		public ArrayList<Abonner> getAbonnerParSites(Sites sites) throws DAOException, DAOConfigurationException {
			// TODO Auto-generated method stub
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			ArrayList<Abonner> abonner = new ArrayList<>();
			
			try {
				connexion = daoFactory.getConnection();
				preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_COOKIE, false, sites.getId());
				resultSet = preparedStatement.executeQuery();
				while ( resultSet.next() ) {
					abonner.add(loadAbonnerFromDb( resultSet ));
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				DAOutils.close( resultSet, preparedStatement, connexion );
			}

			
			return abonner;
		}
		
		// récuprer tout les abonnements
		@Override
		public ArrayList<Abonner> getAbonner() throws DAOException, DAOConfigurationException {
			// TODO Auto-generated method stub
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			ArrayList<Abonner> abonner = new ArrayList<>();
			
			try {
				connexion = daoFactory.getConnection();
				preparedStatement = DAOutils.initialisationRequetePreparee( connexion, SQL_SELECT_ALL, false);
				resultSet = preparedStatement.executeQuery();
				while ( resultSet.next() ) {
					abonner.add(loadAbonnerFromDb( resultSet ));
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				DAOutils.close( resultSet, preparedStatement, connexion );
			}
			return abonner;
		}

		
		private static Abonner loadAbonnerFromDb( ResultSet resultSet ) throws SQLException, DAOException, DAOConfigurationException {
			Abonner abonner = new Abonner();
			abonner.setPersonne(DAOFactory.getInstance().getPersonneDAO().getPersonnee(resultSet.getInt("id_personne")));
			abonner.setSites(DAOFactory.getInstance().getSitesDAO().getSites(resultSet.getInt("id_sites")));
			return abonner;
		}
}
