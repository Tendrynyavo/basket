package statistique;

import connection.BddObject;

public class Possession extends BddObject {
    
    String idJoueur;
    String idMatch;
    int temps;

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public String getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public Possession() {
        setTable("Possession");
    }

    public Possession(String idJoueur, String idMatch, int temps) {
        this();
        setIdJoueur(idJoueur);
        setIdMatch(idMatch);
        setTemps(temps);
    }
}
