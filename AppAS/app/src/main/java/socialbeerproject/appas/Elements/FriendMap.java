package socialbeerproject.appas.Elements;

/**
 * Classe FriendMap, cette classe représente la position d'un ami
 * @author Voet Rémy, Faignaert Florian, Pierret Cyril
 */
public class FriendMap {

    private String id;
    private boolean valid;
    private String login;
    private Double latitude;
    private Double longitude;


    private int dernierCon;

    public FriendMap(String id, boolean valid, String login, Double latitude, Double longitude, int dernierCon) {
        this.id = id;
        this.valid = valid;
        this.login = login;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dernierCon = dernierCon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDernierCon() {
        return dernierCon;
    }

    public void setDernierCon(int dernierCon) {
        this.dernierCon = dernierCon;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
