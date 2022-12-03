package equipe;

import connection.BddObject;
import joueur.Joueur;

public class Equipe extends BddObject {

    String idEquipe;
    String nom;
    int score;
    String idMatch;
    Joueur PG;

    public Joueur getPG() {
        return PG;
    }

    public void setPG(Joueur PG) {
        this.PG = PG;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    Joueur[] joueurs;

    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public void setJoueurs() throws Exception {
        Joueur joueur = new Joueur();
        joueur.setIdEquipe(getIdEquipe());
        setJoueurs(Joueur.convert(joueur.getData(BddObject.getPostgreSQL(), "idJoueur", "idEquipe")));
    }

    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    public static Equipe[] convert(Object[] objects) {
        Equipe[] equipes = new Equipe[objects.length];
        for (int i = 0; i < equipes.length; i++)
            equipes[i] = (Equipe) objects[i];
        return equipes;
    }

    public static String[] getNom(Equipe[] equipes) {
        String[] noms = new String[equipes.length];
        for (int i = 0; i < noms.length; i++)
            noms[i] = equipes[i].getNom();
        return noms;
    }

    public static String[] getIdEquipe(Equipe[] equipes) {
        String[] noms = new String[equipes.length];
        for (int i = 0; i < noms.length; i++)
            noms[i] = equipes[i].getIdEquipe();
        return noms;
    }

    public Equipe() {
        this.setTable("equipe");
        this.setCountPK(7);
        this.setPrefix("EQU");
        this.setFunctionPK("getseqequipe()");
    }

    public Equipe(String nom) throws Exception {
        this();
        this.setIdEquipe(buildPrimaryKey(getPostgreSQL()));
        this.setNom(nom);
    }

    public void setMarques(boolean marque) {
        for (Joueur joueur: getJoueurs())
            joueur.setMarque(marque);
    }
}