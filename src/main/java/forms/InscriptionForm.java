package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Personne;
import dao.DAOException;
import dao.PersonneDAO;

public class InscriptionForm {
	private static final String CHAMP_LOGIN      = "login";
    private static final String CHAMP_PASSWORD       = "password";
    private static final String CHAMP_CONFPASSWORD       = "confPassword";
	
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
	private PersonneDAO personneDao;
	
	public InscriptionForm( PersonneDAO personneDao ) {
	    this.personneDao = personneDao;
	}
	
	public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }
	
    // fonction pour l'inscription
	public Personne inscrirePersonne( HttpServletRequest request ) {
	    String login = getValeurChamp( request, CHAMP_LOGIN );
	    String password = getValeurChamp( request, CHAMP_PASSWORD );
	    String confPassword = getValeurChamp( request, CHAMP_CONFPASSWORD );
	    
	    Personne personne = new Personne();
	    try {
	    	//vérification des données et création de la personne
	        validerLogin( login, personne );
	        validerPassword(confPassword, password, personne );

	        if ( erreurs.isEmpty() ) {
	            personneDao.create( personne );
	            resultat = "Succés de l'inscription.";
	        } else {
	            resultat = "échec de l'inscription.";
	        }
	    } catch ( DAOException e ) {
	        resultat = "échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	        e.printStackTrace();
	    }

	    return personne;
	}
	
	private void validerLogin( String login, Personne personne ) {
        try {
            validationLogin( login );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
        personne.setLogin( login );
    }
	
	private void validerPassword( String confPassword, String password, Personne personne ) {
        try {
            validationPassword( password );
            validationconfPassword(password, confPassword);
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASSWORD, e.getMessage() );
        }
        personne.setPassword(password);
    }

	
	private void validationLogin( String login ) throws FormValidationException {
        if ( login != null && login.length() < 4 ) {
            throw new FormValidationException( "Le login doit contenir au moins 4 caractÃ¨res." );
        }
    }
	
	private void validationPassword( String password) throws FormValidationException {
        if ( password != null && password.length() < 6 ) {
            throw new FormValidationException( "Le mot de passe doit contenir au moins 6 caractÃ¨res." );

        }
    }
	
	private void validationconfPassword( String password, String confPassword) throws FormValidationException {
        if ( confPassword == null || !password.equals(confPassword)) {
            throw new FormValidationException( "Verifier votre mot de passe ." );

        }
    }
	
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
