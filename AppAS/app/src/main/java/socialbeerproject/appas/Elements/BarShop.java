package socialbeerproject.appas.Elements;

/**
 * Created by Pierret on 01-12-15.
 */
public class BarShop {
    private String id;
    private String telephone;
    private String website;
    private String street;
    private String number;
    private String zipcode;
    private String city;
    private String country;
    private String beer_place;
    private String place_id;

    public BarShop(String id, String telephone, String website, String street, String number, String zipcode, String city, String country,String place_id , String beer_place) {
        this.id = id;
        this.telephone = telephone;
        this.website = website;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.beer_place = beer_place;
        this.place_id = place_id;
    }

    @Override
    public String toString() {
        return  telephone + '\'' +
                website + '\'' +
                number + ", " + street + '\n' +
                zipcode + "  " + city + '\n' +
                country;
    }

    /* END : GETTERS AND SETTERS */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBeer_place() {
        return beer_place;
    }

    public void setBeer_place(String beer_type) {
        this.beer_place = beer_type;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
