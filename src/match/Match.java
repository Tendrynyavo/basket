package match;

import connection.BddObject;
import equipe.Equipe;
import formulaire.Button;
import formulaire.Formulaire;
import joueur.Joueur;
import mouse.Start;
import type.TypeListener;

import java.sql.Date;

public class Match extends BddObject {

    String idMatch;
    String idEquipe1;
    String idEquipe2;
    Date date;
    Equipe[] equipes = new Equipe[2];
    TypeListener type;

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

    public void setEquipes() throws Exception {
        Equipe equipe1 = new Equipe();
        equipe1.setIdEquipe(getIdEquipe1());
        Equipe equipe2 = new Equipe();
        equipe2.setIdEquipe(getIdEquipe2());
        this.equipes[0] = Equipe.convert(equipe1.getData(BddObject.getPostgreSQL(), null, "idEquipe"))[0];
        this.equipes[1] = Equipe.convert(equipe2.getData(BddObject.getPostgreSQL(), null, "idEquipe"))[0];
    }

    public Joueur havePossession() {
        for (Joueur joueur: equipes[0].getJoueurs()) {
            if (joueur.isPossession()) return joueur;
        }
        for (Joueur joueur: equipes[1].getJoueurs()) {
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
        form.getListeChamp()[1].setLabel("Equipe 1 :");
        form.getListeChamp()[1].changeToDrop(noms, ids);
        form.getListeChamp()[2].setLabel("Equipe 2 :");
        form.getListeChamp()[2].changeToDrop(noms, ids);
        form.getListeChamp()[3].setVisible(false, "");
        form.getListeChamp()[4].setVisible(false, "");
        form.getListeChamp()[5].setVisible(false, "");
        form.addButton(new Button(new Start(form), "OK"));
        form.setPosition();
        return form;
    }
    public void initMarque() {
        equipes[0].setMarques(false);
        equipes[1].setMarques(false);
    }
}