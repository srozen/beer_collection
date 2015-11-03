package socialbeerproject.appas.Elements;

public class ElementCollection {

    private String nom;
    private int icon;
    private String id;

    public ElementCollection(String nom, int icon, String id) {
        this.nom = nom;
        this.icon = icon;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
