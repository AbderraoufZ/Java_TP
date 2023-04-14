package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Personne;
import dao.DAOFactory;
import dao.PersonneDAO;
import forms.ConnexionForm;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final String ATT_DAO_FACTORY = "daofactory";
	public static final String ATT_PERSONNE = "personne";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/WEB-INF/Connexion.jsp";
	public static final String VUE_SUCCESS = "/WEB-INF/choix.jsp";
	   
	
	 private PersonneDAO     personneDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// traitement des information au niveau de ConnexionFrom
		ConnexionForm form = new ConnexionForm( personneDao);
		// valider le format des les information et vérifier la compatibilite au niveau de la BDD avec la fonction
		Personne personne = form.connexionPersonne(request);

		request.setAttribute( ATT_FORM, form );


		if(personne != null) {
			// créer une session de connexion pour la personne 
			HttpSession session = request.getSession(true);
			session.setAttribute("user", personne.getId());
			//envoyer pour le faire le choix des sites 
			response.sendRedirect(request.getContextPath() +"/Choix");
		}
		else {
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
		

	}

		/* (non-Javadoc)
		* @see javax.servlet.GenericServlet#init()
		*/
	
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		this.personneDao = ( (DAOFactory) getServletContext().getAttribute( ATT_DAO_FACTORY ) ).getPersonneDAO();
	}


	

}
