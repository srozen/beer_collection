package socialbeerproject.appas.Elements;

/**
 * Created by Pierret on 22-11-15.
 */
public class ElementPlan {

    // TODO : IMPLEMENTER LES DERNIERS ATTRIBUTS EN FONCTION DE LA DB

    /*
        t.string   "name"
        t.string   "nameBeer"
        t.text     "description"
        t.string   "categorie"
        t.datetime "datedebut"
        t.datetime "datefin"
        t.float    "prix"
        t.float    "reference"
        t.float    "reduction"
     */

    private String title;
    private String nameBeer;
    private String nomBiere
    private String description;
    private String categorie;
    private String prix;
    private String dateDebut;
    private String dateFin;
    private String reference;
    private String id;

    public ElementPlan(String title, String nameBeer, String descr, String categorie, String prix, String dD, String dE, String ref, String id) {
        this.title = title;
        this.nameBeer = nameBeer;
        this.description = descr;
        this.categorie = categorie;
        this.prix = prix;
        this.dateDebut = dD;
        this.dateFin = dE;
        this.reference = ref;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String descr){
        this.description = descr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
