package match;

import connection.BddObject;
import equipe.Equipe;
import formulaire.Button;
import formulaire.Formulaire;
import joueur.Joueur;
import mouse.Start;
import time.Chrono;
import type.TypeListener;
import java.sql.Date;

public class Match extends BddObject {

    String idMatch;
    String idEquipe1;
    String idEquipe2;
    Date date;
    Equipe[] equipes = new Equipe[2];
    TypeListener type;
    Chrono chrono;
    int debut;

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

    public TypeListener getType() {
        return type;
    }

    public void setType(TypeListener type) {
        this.type = type;
    }

    public String getIdEquipe1() {
        return idEquipe1;
    }

    public void setIdEquipe1(String idEquipe1) throws Exception {
        Equipe equipe = new Equipe();
        if (!idEquipe1.contains(equipe.getPrefix()) || idEquipe1.length() != equipe.getCountPK())
            throw new Exception("ID Equipe 1 invalid");
        this.idEquipe1 = idEquipe1;
    }

    public String getIdEquipe2() {
        return idEquipe2;
    }

    public void setIdEquipe2(String idEquipe2) throws Exception {
        Equipe equipe = new Equipe();
        if (!idEquipe2.contains(equipe.getPrefix()) || idEquipe2.length() != equipe.getCountPK())
            throw new Exception("ID Equipe 2 invalid");
        this.idEquipe2 = idEquipe2;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) throws Exception {
        if (!idMatch.contains(this.getPrefix()) || idMatch.length() != this.getCountPK())
            throw new Exception("ID Match invalid");
        this.idMatch = idMatch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Equipe[] getEquipes() {
        return equipes;
    }

    public void setEquipes(Equipe[] equipes) {
        this.equipes = equipes;
    }

    public Match() {
        this.setTable("match");
        this.setPrefix("MAT");
        this.setCountPK(7);
        this.setFunctionPK("getseqmatch()");
    }

    public Match(String idEquipe1, String idEquipe2, Date date) throws Exception {
        this();
        this.setIdMatch(buildPrimaryKey(getPostgreSQL()));
        this.setIdEquipe1(idEquipe1);
        this.setIdEquipe2(idEquipe2);
        this.setDate(date);
    }

    public static Match getMatch(String idMatch) throws Exception {
        Match match = new Match();
        match.setIdMatch(idMatch);
        match = Match.convert(match.getData(getPostgreSQL(), null, "idMatch"))[0];
        match.setEquipes();
        return match;
    }

    public void setEquipes() throws Exception {
        Equipe equipe = new Equipe();
        equipe.setTable("score_final");
        equipe.setIdMatch(getIdMatch());
        Equipe[] equipes = Equipe.convert(equipe.getData(BddObject.getPostgreSQL(), "idequipe", "idMatch"));
        for (Equipe team : equipes) {
            team.setJoueurs();  
            team.setTimes(this);
            for (Joueur player : team.getJoueurs())
                player.setStatIndivual(this);
        }
        for (int i = 0; i < equipes.length; i++)
            equipes[i].getTime().setPourcentageTotal(equipes[0].getTime().getTime() + equipes[1].getTime().getTime());
        setEquipes(equipes);
    }

    public Joueur havePossession() {
        for (Equipe equipe : equipes) {
            for (Joueur joueur : equipe.getJoueurs())
                if (joueur.isPossession()) return joueur;
        }
        return null;
    }

    public static Formulaire getFormulaire() throws Exception {
        Formulaire form = Formulaire.createFormulaire(new Match());
        form.getListeChamp()[0].setVisible(false, "");
        Equipe[] equipes = Equipe.convert(new Equipe().getData(BddObject.getPostgreSQL(), null));
        String[] noms = Equipe.getNom(equipes);
        String[] ids = Equipe.getIdEquipe(equipes);
        for (int i = 1; i <= 2; i++) {
            form.getListeChamp()[i].setLabel("Equipe " + i + " :");
            form.getListeChamp()[i].changeToDrop(noms, ids);
        }
        for (int j = 3; j <= 7; j++) form.getListeChamp()[j].setVisible(false, "");
        form.addButton(new Button(new Start(form), "OK"));
        form.setPosition();
        return form;
    }

    public void initMarque() {
        equipes[0].setMarques(false);
        equipes[1].setMarques(false);
    }

    public static Match[] convert(Object[] objects) {
        Match[] matchs = new Match[objects.length];
        for (int i = 0; i < matchs.length; i++)
            matchs[i] = (Match) objects[i];
        return matchs;
    }
}