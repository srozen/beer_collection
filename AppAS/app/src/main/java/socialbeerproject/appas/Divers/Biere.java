package socialbeerproject.appas.Divers;

import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rémy on 01-11-15.
 * Défini une bière
 */
public class Biere {

    private String nom;
    private String brasserie;
    private double alcool;
    private String description;
    private String type;
    private String couleur;
    private int nbConsommateur;
    private ImageView photo;
    private double ratingGlo;
    private double ratingPer;

    public Biere(){

    }

    public void setBiereColl(JSONObject biere){
        try {
            this.ratingPer = biere.getDouble("perRating");
            this.ratingGlo = biere.getDouble("gloRating");
            this.alcool = biere.getDouble("alcool");
            this.nom = biere.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            this.nom ="N/a";
        }
    }

    public void setBiereCata(JSONObject biere){

    }

    public void setBiereProfil(JSONObject biere){

    }



    public double getAlcool() {
        return alcool;
    }

    public void setAlcool(double alcool) {
        this.alcool = alcool;
    }

    public String getBrasserie() {
        return brasserie;
    }

    public void setBrasserie(String brasserie) {
        this.brasserie = brasserie;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbConsommateur() {
        return nbConsommateur;
    }

    public void setNbConsommateur(int nbConsommateur) {
        this.nbConsommateur = nbConsommateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public double getRatingGlo() {
        return ratingGlo;
    }

    public void setRatingGlo(double ratingGlo) {
        this.ratingGlo = ratingGlo;
    }

    public double getRatingPer() {
        return ratingPer;
    }

    public void setRatingPer(double ratingPer) {
        this.ratingPer = ratingPer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
