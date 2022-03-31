package ru.gb.veber.homework_6_notes.notes;

import java.io.Serializable;
import java.util.Date;

public class CardNote implements Serializable,Comparable<CardNote> {

    public static final String NOTE="NOTE";
    private int id;
    private String name;
    private String dateText;
    private String description;
    private Date dateDate;

    public Date getDateDate() {
        return dateDate;
    }

    public void setDateDate(Date dateDate) {
        this.dateDate = dateDate;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CardNote(String name, String dateText, String description, Date dateDate) {
        this.name = name;
        this.dateText = dateText;
        this.description = description;
        this.dateDate = dateDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "CardNote{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateText='" + dateText + '\'' +
                ", description='" + description + '\'' +
                ", dateDate=" + dateDate +
                '}';
    }

    @Override
    public int compareTo(CardNote note) {
        return name.compareTo(note.getName());
    }
}
