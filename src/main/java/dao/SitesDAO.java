package dao;

import java.util.ArrayList;

import beans.Sites;

public interface SitesDAO {

	public void create(Sites Sites) throws DAOException;
	public Sites getSites(int id) throws DAOException;
	public ArrayList<Sites> getSitess() throws DAOException,DAOConfigurationException;
}
