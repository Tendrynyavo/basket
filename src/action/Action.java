package action;

import connection.BddObject;

public class Action extends BddObject {

    String idAction;
    String nom;

    public String getIdAction() {
        return idAction;
    }

    public void setIdAction(String idAction) {
        this.idAction = idAction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Action() {
        setTable("action_match");
        setPrefix("A");
        setCountPK(4);
        setFunctionPK("getseqaction()");
    }

    public Action(String idAction, String nom) {
        this();
        setIdAction(idAction);
        setNom(nom);
    }

    public Action(String nom) throws Exception {
        this();
        setIdAction(buildPrimaryKey(getPostgreSQL()));
        setNom(nom);
    }
}