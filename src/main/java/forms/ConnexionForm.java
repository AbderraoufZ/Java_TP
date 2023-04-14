package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Personne;
import dao.DAOException;
import dao.PersonneDAO;

public class ConnexionForm {
	
	private static final String CHAMP_LOGIN      = "login";
    private static final String CHAMP_PASSWORD       = "password";

	
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
	private PersonneDAO personneDao;
	
	
	public ConnexionForm( PersonneDAO personneDao ) {
	    this.personneDao = personneDao;
	}

	public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }
 // enregistre la personne apres la validation des information
	public Personne connexionPersonne( HttpServletRequest request ) {
	    String login = getValeurChamp( request, CHAMP_LOGIN );
	    String password = getValeurChamp( request, CHAMP_PASSWORD );
	  
	  //créer la personne
	    Personne personne = new Personne();

	    try {
	    	
    	    
	    	//vérifier les information
	        validerLogin( login );
	        validerPassword( password );

	        if ( erreurs.isEmpty() ) {
	        	// récuperation des données de la personne
	        	personne = personneDao.getPersonne( login, password );
	        	resultat = "Vérifier votre login ou mot de passe";
	        } else {
	            resultat = "échec de la connexion.";
	        	personne = null;
	        }
	    } catch ( DAOException e ) {
	        resultat = "échec de la connexion. ";
	        e.printStackTrace();
	    }

	    return personne;
	}

	// validation du champ login
	private void validerLogin( String login ) {
        try {
            validationLogin( login );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
    }
	// validation du champ password
	private void validerPassword( String password) {
        try {
            validationPassword( password );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASSWORD, e.getMessage() );
        }
    }

	// vérification de nombre de caracter.
	private void validationLogin( String login ) throws FormValidationException {
        if ( login != null && login.length() < 4 ) {
            throw new FormValidationException( "Le login doit contenir au moins 4 caractères." );
        }
    }
	
	private void validationPassword( String password) throws FormValidationException {
        if ( password != null && password.length() < 6 ) {
            throw new FormValidationException( "Le mot de passe doit contenir au moins 6 caractères." );

        }
    }
	
	// récupiration des erreurs
	private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
        System.out.println("erreur champ : " + champ + " --> " + message);
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
