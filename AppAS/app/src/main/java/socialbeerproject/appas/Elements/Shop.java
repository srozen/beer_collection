package socialbeerproject.appas.Elements;

/**
 * Created by Pierret on 01-12-15.
 */
public class Shop {
    private String id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    public  BarShop contact;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public BarShop getContact() {
        return contact;
    }

    public void setContact(BarShop contact) {
        this.contact = contact;
    }

    public Shop(String id, String name, String description, Double latitude, Double longitude, BarShop bs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = bs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLattitude() {
        return latitude;
    }

    public void setLattitude(Double lattitude) {
        this.latitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
