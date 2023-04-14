package dao;

import beans.Personne;

public interface PersonneDAO {
	// Création d'une personne 
	void create( Personne personne ) throws DAOException;
	// Trouver une personne 
	Personne getPersonne( String login, String password ) throws DAOException;
	Personne getPersonnee( int id ) throws DAOException;
}
