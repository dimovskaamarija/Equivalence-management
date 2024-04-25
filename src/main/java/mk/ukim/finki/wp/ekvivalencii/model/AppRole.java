package mk.ukim.finki.wp.ekvivalencii.model;

public enum AppRole {
    ADMIN,PROFESSOR,GUEST;
    public String roleName(){
        return "ROLE_"+this.name();
    }
}
