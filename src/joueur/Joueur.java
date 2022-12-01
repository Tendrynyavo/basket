package joueur;

import action.Passe;
import connection.BddObject;
import equipe.Equipe;
import match.Match;
import statistique.Statistique;

public class Joueur extends BddObject {

    String idJoueur;
    String nom;
    String poste;
    String idEquipe;
    Equipe equipe;
    Statistique tir;
    Statistique rebond;
    boolean possession = false;
    boolean marque = false;

    public boolean isMarque() {
        return marque;
    }

    public void setMarque(boolean marque) {
        this.marque = marque;
    }

    public Statistique getRebond() {
        return rebond;
    }

    public void setRebond(Statistique rebond) {
        this.rebond = rebond;
    }

    public Statistique getTir() {
        return tir;
    }

    public void setTir(Statistique tir) {
        this.tir = tir;
    }
    public boolean isPossession() {
        return possession;
    }

    public void setPossession(boolean possession) {
        this.possession = possession;
    }

    public String getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(String idJoueur) throws Exception {
        if (!idJoueur.contains(this.getPrefix()) || idJoueur.length() != this.getCountPK())
            throw new Exception("Invalid ID Joueur");
        this.idJoueur = idJoueur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(String idEquipe) throws Exception {
        Equipe equipe = new Equipe();
        if (!idEquipe.contains(equipe.getPrefix()) || idEquipe.length() != equipe.getCountPK())
            throw new Exception("ID Equipe invalid");
        this.idEquipe = idEquipe;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Joueur() {
        this.setTable("joueur");
        this.setPrefix("JOU");
        this.setCountPK(7);
        this.setFunctionPK("getseqjoueur()");
    }

    public Joueur(String nom, String idEquipe, String poste) throws Exception {
        this();
        this.setIdJoueur(buildPrimaryKey(getPostgreSQL()));
        this.setNom(nom);
        this.setIdEquipe(idEquipe);
        this.setPoste(poste);
    }

    public void setEquipe() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(getIdEquipe());
        setEquipe(Equipe.convert(equipe.getData(BddObject.getPostgreSQL(), null, "idEquipe"))[0]);
    }

    public static Joueur[] convert(Object[] objects) {
        Joueur[] joueurs = new Joueur[objects.length];
        for (int i = 0; i < joueurs.length; i++)
            joueurs[i] = (Joueur) objects[i];
        return joueurs;
    }

    public void changePossession(Joueur joueur, Match match) throws Exception {
        if (joueur.isMarque()) throw new Exception(getEquipe().getNom() + " a marquée balle rendue à l'adversaire");
        if (joueur.isPossession()) throw new Exception(joueur.getNom() + " a déja le ballon");
        match.initMarque();
        this.setPossession(false);
        joueur.setPossession(true);
        if (getTir() != null) {
            getTir().insert(null);
            setTir(null);
            joueur.makeRebond(match, this);
        } else if (!this.equals(joueur)) passe(match);
    }

    public void makeRebond(Match match, Joueur joueurShoot) throws Exception {
        Statistique rebond = new Statistique(match.getIdMatch(), this.getIdJoueur(), "A040", 1);
        if (joueurShoot.getIdEquipe().equals(getIdEquipe())) rebond.setIdAction("A030");
        rebond.insert(null);
        setRebond(rebond);
    }

    public void passe(Match match) throws Exception {
        setRebond(null);
        Passe passe = new Passe(this.getIdJoueur(), match.getIdMatch());
        passe.insert(null);
    }

    public void shoot(Match match) throws Exception {
        if (!isPossession()) throw new Exception(this.getNom() + " n'a pas encore la possession");
        setTir(new Statistique(match.getIdMatch(), this.getIdJoueur(), "A010", 0));
    }

    public void marque(Match match) throws Exception {
        if (getTir() == null) throw new Exception(this.getNom() + " n'a pas encore shooter");
        getTir().setNombre(2);
        getTir().insert(null);
        setTir(null);
        Joueur[] previous = getLastPasse(match);
        if ((previous.length > 0) && (previous[0].getIdEquipe().equals(this.getIdEquipe())) && this.getRebond() == null) {
            Statistique statistique = new Statistique(match.getIdMatch(), previous[0].getIdJoueur(), "A020", 1);
            statistique.insert(null);
        }
        setRebond(null);
        getEquipe().setMarques(true);
    }

    public Joueur[] getLastPasse(Match match) throws Exception {
        Joueur joueur = new Joueur();
        joueur.setTable("get_last_passe('" + match.getIdMatch() + "')");
        return Joueur.convert(joueur.getData(BddObject.getPostgreSQL(), null));
    }
}