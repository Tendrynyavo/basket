package action;

import connection.BddObject;

public class Passe extends BddObject {

    String idPasse;
    String idJoueur;
    String idMatch;

    public String getIdPasse() {
        return idPasse;
    }

    public void setIdPasse(String idPasse) {
        this.idPasse = idPasse;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }

    public Passe() {
        this.setTable("passe");
        this.setPrefix("PAS");
        this.setCountPK(10);
        this.setFunctionPK("getseqpasse()");
    }

    public Passe(String idJoueur, String idMatch) throws Exception {
        this();
        setIdPasse(buildPrimaryKey(getPostgreSQL()));
        setIdJoueur(idJoueur);
        setIdMatch(idMatch);
    }
}
