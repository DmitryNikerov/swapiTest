package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class People implements Serializable {
    public String name;

    @SerializedName("birth_year")
    public String birthYear;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    public String gender;

    @SerializedName("hair_color")
    public String hairColor;

    public String height;

    @SerializedName("homeworld")
    public String homeWorldUrl;

    public String mass;

    @SerializedName("skin_color")
    public String skinColor;

    public String created;
    public String edited;
    public String url;
    @SerializedName("species")
    public ArrayList<String> speciesUrls;

    @SerializedName("starships")
    public ArrayList<String> starshipsUrls;

    @SerializedName("vehicles")
    public ArrayList<String> vehiclesUrls;
}
