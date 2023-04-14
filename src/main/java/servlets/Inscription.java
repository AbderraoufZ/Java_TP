package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Personne;
import dao.DAOFactory;
import dao.PersonneDAO;
import forms.InscriptionForm;

/**
 * Servlet implementation class for Servlet: Inscription
 *
 */
@WebServlet("/Inscription")
 public class Inscription extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public static final String ATT_DAO_FACTORY = "daofactory";
   public static final String ATT_PERSONNE = "personne";
   public static final String ATT_FORM = "form";
   public static final String VUE = "/WEB-INF/inscription.jsp";
   
   private PersonneDAO     personneDao;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Inscription() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        InscriptionForm form = new InscriptionForm( personneDao );
      // inscrire une personne
        Personne personne = form.inscrirePersonne( request );

        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_PERSONNE, personne);

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}   	  	  
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		this.personneDao = ( (DAOFactory) getServletContext().getAttribute( ATT_DAO_FACTORY ) ).getPersonneDAO();
	}   
}