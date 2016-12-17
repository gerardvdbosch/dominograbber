package model;

public class Location {
    private String streetName;
    private int streetNumber;
    private String postcode;
    private String city;
    private String country;

    public Location() {
        streetName = "Undefined";
        streetNumber = 0;
        postcode = "Undefined";
        city = "Undefined";
        country = "Undefined";
    }

    public Location(String streetName, int streetNumber, String postcode, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
}
