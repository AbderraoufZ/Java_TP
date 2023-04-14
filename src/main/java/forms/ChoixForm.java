package forms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Abonner;
import dao.AbonnerDAO;
import dao.DAOConfigurationException;
import dao.DAOException;
import dao.PersonneDAO;
import dao.SitesDAO;

public class ChoixForm {

	private static final String CHAMP_SITES = "sitesList";
	private static final String SESSION_ARG = "user";

	private String resultat;
	private PersonneDAO personneDao;
	private SitesDAO sitesDao;
	private AbonnerDAO abonnerDao;
	
	public ChoixForm(PersonneDAO personneDao, SitesDAO sitesDao, AbonnerDAO abonnerDao)
	{
		this.personneDao = personneDao;
		this.sitesDao = sitesDao;
		this.abonnerDao = abonnerDao;
	}
	// fonction pour liée une personne a un site
	public Abonner abonnez(HttpServletRequest request)
	
	{
		HttpSession session = request.getSession(true);
		int id_sites = Integer.valueOf(getValeurChamp(request,CHAMP_SITES));
		int id_utilisateur = (int)session.getAttribute(SESSION_ARG);

		if(id_utilisateur == 0)
		{
			return null;
		}
		
		//créer un abonnement laison entre la personne et le site
		Abonner abonner = new Abonner();

		abonner.setPersonne(personneDao.getPersonnee(id_utilisateur));
		abonner.setSites(sitesDao.getSites(id_sites));

		if(abonner.getPersonne() != null && abonner.getSites() != null)
		{
			try {
				//créer un abonnement 
				abonnerDao.create(abonner);
				resultat = "Succées de l'inscription.";
			} catch (DAOException | DAOConfigurationException e) {
				resultat = " ́Echec de l'inscription : une erreur imprévue est survenue, merci de r ́eessayer dans quelques instants.";
				e.printStackTrace();
			}
		}
		else
		{
			resultat = " ́Ehec de l'inscription.";
		}
		return abonner;
	}
	
	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
		String valeur = request.getParameter( nomChamp );
		if ( valeur == null || valeur.trim().length() == 0 ) {
			return null;
		} else {
			return valeur;
		}
	}
}
