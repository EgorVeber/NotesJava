package ru.gb.veber.homework_6_notes.notes;

import java.io.Serializable;
import java.util.Objects;

public class CardNote implements Serializable {

    public static final String NOTE="NOTE";
    private int id;
    private String country;
    private String capital;
    private String population;

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public CardNote(String country, String capital, String population) {
        this.country = country;
        this.capital = capital;
        this.population = population;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }


    @Override
    public String toString() {
        return "CardNote{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", capital='" + capital + '\'' +
                ", population='" + population + '\'' +
                '}';
    }
}
