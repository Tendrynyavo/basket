package time;

import connection.BddObject;
import equipe.Equipe;
import match.Match;

public class Temps extends BddObject {
    
    String nom;
    String idMatch;
    String idEquipe;
    int time;
    double pourcentage;
    Match match;
    Equipe equipe;

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setPourcentageTotal(double seconde) {
        double pourcentage = (getTime() * 100.0) / seconde;
        setPourcentage((double) Math.round(pourcentage * 100) / 100);
    }

    public Temps() {
        setTable("temps_final");
    }

    public Temps(Match match, Equipe equipe) {
        this();
        setMatch(match);
        setEquipe(equipe);
        setIdEquipe(equipe.getIdEquipe());
        setIdMatch(match.getIdMatch());
    }

    public static Temps[] convert(Object[] objects) {
        Temps[] temps = new Temps[objects.length];
        for (int i = 0; i < temps.length; i++) temps[i] = (Temps) objects[i];
        return temps;
    }
}
