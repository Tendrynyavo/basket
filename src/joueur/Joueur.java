package joueur;

// import java.time.LocalTime;
import action.Passe;
import connection.BddObject;
import equipe.Equipe;
import match.Match;
import statistique.Individuel;
import statistique.Possession;
import statistique.Statistique;

public class Joueur extends BddObject {

    String idJoueur;
    String nom;
    String poste;
    String idEquipe;
    Individuel individuel;
    Statistique tir;
    Statistique rebond;
    Equipe equipe;
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
        if (!idJoueur.contains(this.getPrefix()) || idJoueur.length() != this.getCountPK()) throw new Exception("Invalid ID Joueur");
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
        if (!idEquipe.contains(equipe.getPrefix()) || idEquipe.length() != equipe.getCountPK()) throw new Exception("ID Equipe invalid");
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

    public Individuel getIndividuel() {
        return individuel;
    }

    public void setIndividuel(Individuel statistique) {
        this.individuel = statistique;
    }

    public void setStatIndivual(Match match) throws Exception {
        Statistique statistique = new Statistique(match.getIdMatch(), getIdJoueur());
        statistique.setTable("statistique_individuel");
        setIndividuel(new Individuel(this, Statistique.convert(statistique.getData(getPostgreSQL(), "nom DESC", "idMatch", "idJoueur"))));
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

    public static Joueur[] convert(Object[] objects) {
        Joueur[] joueurs = new Joueur[objects.length];
        for (int i = 0; i < joueurs.length; i++) joueurs[i] = (Joueur) objects[i];
        return joueurs;
    }

    public void makePasse(Joueur joueur, Match match) throws Exception {
        if (joueur.isMarque()) throw new Exception(getEquipe().getNom() + " a marquée balle rendue à l'adversaire");
        if (joueur.isPossession()) throw new Exception(joueur.getNom() + " a déja le ballon");
        changePossession(joueur, match);
        if (getTir() != null) {
            getTir().insert(null);
            setTir(null);
            joueur.makeRebond(match, this);
        } else if (!this.equals(joueur)) insertPasse(match);
    }

    public void savePossession(Match match) throws Exception {
        match.getChrono().stop();
        Possession possession = new Possession(this.getIdJoueur(), match.getIdMatch(), (int) match.getChrono().getDureeSec());
        possession.insert(null);
        match.getChrono().start();
    }

    public void makeRebond(Match match, Joueur joueurShoot) throws Exception {
        setRebond(new Statistique(match.getIdMatch(), getIdJoueur(), "A040", 1));
        if (joueurShoot.getIdEquipe().equals(getIdEquipe())) getRebond().setIdAction("A030");
        getRebond().insert(null);
    }

    public void insertPasse(Match match) throws Exception {
        savePossession(match);
        setRebond(null);
        Passe passe = new Passe(getIdJoueur(), match.getIdMatch()); // create Passe BddObject with ID player and match
        passe.insert(null);
    }

    public void shoot(Match match) throws Exception {
        if (!isPossession()) throw new Exception(getNom() + " n'a pas encore la possession");
        savePossession(match);
        setTir(new Statistique(match.getIdMatch(), this.getIdJoueur(), "A010", 0));
    }

    public void marque(Match match) throws Exception {
        if (getTir() == null) throw new Exception(getNom() + " n'a pas encore shooter");
        getTir().setNombre(2); // change to two points this tir
        getTir().insert(null);
        setTir(null); // reload tir in this player
        Joueur[] previous = getLastPasse(match); // get last player who pass this ballon
        // * if this player make rebond anyone get Passe Décisif
        if ((previous.length > 0) && (previous[0].getIdEquipe().equals(getIdEquipe())) && getRebond() == null)
            new Statistique(match.getIdMatch(), previous[0].getIdJoueur(), "A020", 1).insert(null);
        setRebond(null); // reload rebond of this player
        getEquipe().setMarques(true); // anyone in equipe of this player can't have ballon after this shoot
        // * Change automatically possession in PG of adversaire
        changePossession(((!match.getEquipes()[0].getIdEquipe().equals(this.getIdEquipe()))) ? match.getEquipes()[0].getPG() : match.getEquipes()[1].getPG(), match);
    }

    public Joueur[] getLastPasse(Match match) throws Exception {
        Joueur joueur = new Joueur();
        joueur.setTable("get_last_passe('" + match.getIdMatch() + "')");
        return Joueur.convert(joueur.getData(BddObject.getPostgreSQL(), null));
    }

    public void changePossession(Joueur joueur, Match match)  {
        match.initMarque();
        this.setPossession(false);
        joueur.setPossession(true);
        match.getType().setPrevious(joueur);
    }
}