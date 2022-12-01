package statistique;

import action.Action;
import connection.BddObject;
import joueur.Joueur;
import match.Match;

public class Statistique extends BddObject {

    String idStat;
    String idMatch;
    String idJoueur;
    String idAction;
    int nombre;

    public String getIdStat() {
        return idStat;
    }

    public void setIdStat(String idStat) throws Exception {
        if (!idStat.contains(this.getPrefix()) || idStat.length() != this.getCountPK())
            throw new Exception("ID Stat invalid");
        this.idStat = idStat;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) throws Exception {
        Match match = new Match();
        if (!idMatch.contains(match.getPrefix()) || idMatch.length() != match.getCountPK())
            throw new Exception("ID Match invalid");
        this.idMatch = idMatch;
    }

    public String getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(String idJoueur) throws Exception {
        Joueur joueur = new Joueur();
        if (!idJoueur.contains(joueur.getPrefix()) || idJoueur.length() != joueur.getCountPK())
            throw new Exception("ID Joueur invalid");
        this.idJoueur = idJoueur;
    }

    public String getIdAction() {
        return idAction;
    }

    public void setIdAction(String idAction) throws Exception {
        Action action = new Action();
        if (!idAction.contains(action.getPrefix()) || idAction.length() != action.getCountPK())
            throw  new Exception("ID Action invalid");
        this.idAction = idAction;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) throws Exception {
        if (nombre > 3)
            throw new Exception("Nombre invalid");
        this.nombre = nombre;
    }

    public Statistique() {
        setTable("statistique");
        setPrefix("STAT");
        setCountPK(9);
        setFunctionPK("getseqstatistique()");
    }

    public Statistique(String idMatch, String idJoueur, String idAction, int nombre) throws Exception {
        this();
        setIdStat(buildPrimaryKey(getPostgreSQL()));
        setIdMatch(idMatch);
        setIdJoueur(idJoueur);
        setIdAction(idAction);
        setNombre(nombre);
    }

    public static Statistique[] convert(Object[] objects) {
        Statistique[] stats = new Statistique[objects.length];
        for (int i = 0; i < stats.length; i++)
            stats[i] = (Statistique) objects[i];
        return stats;
    }
}