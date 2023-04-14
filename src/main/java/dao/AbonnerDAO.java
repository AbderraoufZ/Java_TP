package dao;

import java.util.ArrayList;

import beans.Abonner;
import beans.Personne;
import beans.Sites;

public interface AbonnerDAO {
	//interface pour les fonctions de AbonnerDAOImpl
	public void create( Abonner abonner ) throws DAOException, DAOConfigurationException;

	public Abonner getAbonnerParCouple( Personne personne, Sites sites ) throws DAOException, DAOConfigurationException;
	public ArrayList<Abonner> getAbonnerParPersonne( Personne personne ) throws DAOException, DAOConfigurationException;
	public ArrayList<Abonner> getAbonnerParSites( Sites sites ) throws DAOException, DAOConfigurationException;
	public ArrayList<Abonner> getAbonner() throws DAOException, DAOConfigurationException;
}
