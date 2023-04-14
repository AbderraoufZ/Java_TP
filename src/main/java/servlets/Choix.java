package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Abonner;
import beans.Personne;
import beans.Sites;
import dao.AbonnerDAO;
import dao.DAOConfigurationException;
import dao.DAOException;
import dao.DAOFactory;
import dao.PersonneDAO;
import dao.SitesDAO;
import forms.ChoixForm;
/**
 * Servlet implementation class Choix
 */
@WebServlet("/Choix")
public class Choix extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String ATT_DAO_FACTORY = "daofactory";
	public static final String ATT_FORM = "form";
	public static final String ATT_ABONNER = "abonners";
	public static final String ATT_SITES = "sitesList";
	public static final String VUE = "/WEB-INF/choix.jsp";
	
	private PersonneDAO personneDao;
	private SitesDAO sitesDao;
	private AbonnerDAO abonnerDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Choix() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recevoir la session
		HttpSession session = request.getSession(true);
		int iduser = (int)(session.getAttribute("user") != null ? session.getAttribute("user") : 0);
		if(iduser == 0)
		{
			response.sendRedirect(request.getContextPath() + "/Connexion");
		}
		else {
			try {
				//récuperation des abonnement et des sites pour les envoyer a la jsp
				ArrayList<Abonner> abonners = this.abonnerDao.getAbonner();
				String personne = this.personneDao.getPersonnee(iduser).getLogin();
				ArrayList<Sites> sitess = this.sitesDao.getSitess();
				request.setAttribute("personne", personne);
				request.setAttribute(ATT_ABONNER, abonners);

				request.setAttribute(ATT_SITES, sitess);
			} catch (DAOException | DAOConfigurationException e) {
				e.printStackTrace();
			}
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChoixForm form = new ChoixForm( personneDao, sitesDao, abonnerDao);
		HttpSession session = request.getSession(true);
		int iduser = (int)(session.getAttribute("user") != null ? session.getAttribute("user") : 0);
		Abonner abonner = form.abonnez(request);

		request.setAttribute( ATT_FORM, form );
		try {
			//récuperation des abonnement et des sites pour les envoyer a la jsp
			ArrayList<Abonner> abonners = this.abonnerDao.getAbonner();
				ArrayList<Sites> sitess = this.sitesDao.getSitess();
				String personne = this.personneDao.getPersonnee(iduser).getLogin();
				request.setAttribute("personne", personne);
				request.setAttribute(ATT_ABONNER, abonners);
				request.setAttribute(ATT_SITES, sitess);
		} catch (DAOException | DAOConfigurationException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
	
	public void init() throws ServletException {
		this.personneDao = ( (DAOFactory) getServletContext().getAttribute( ATT_DAO_FACTORY ) ).getPersonneDAO();
		this.sitesDao = ( (DAOFactory) getServletContext().getAttribute( ATT_DAO_FACTORY ) ).getSitesDAO();
		this.abonnerDao = ( (DAOFactory) getServletContext().getAttribute( ATT_DAO_FACTORY ) ).getAbonnerDAO();
	}

}
