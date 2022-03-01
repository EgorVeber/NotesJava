package ru.gb.veber.homework_6_notes.notes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;

public class CardNoteSourseImpl implements CardNoteSourse {

    private int counter = 0;
    private Resources resources;
//    static CardNoteSourseImpl sourse;
    private ArrayList<CardNote> notes = new ArrayList<>();

//    public static CardNoteSourseImpl getInstance()
//    {
//        if(sourse==null)
//        {
//            sourse= new CardNoteSourseImpl();
//        }
//        return sourse;
//    }

    public CardNoteSourseImpl()
    {

    }
    public CardNoteSourseImpl init(Resources resources){
        this.resources = resources;
        String[] country = resources.getStringArray(R.array.country);
        String[] capital = resources.getStringArray(R.array.capital);
        String[] population = resources.getStringArray(R.array.population);
        for (int i = 0; i < country.length; i++) {
            create(new CardNote(country[i],capital[i],population[i]));
        }
        return this;
    }
    @Override
    public int create(CardNote note) {
        int id= counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public CardNote read(int id) {
        for (int i = 0; i <notes.size() ; i++) {
            if(notes.get(i).getId()==id)
            {
                return notes.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(CardNote note) {
        for (int i = 0; i <notes.size() ; i++) {
            if(notes.get(i).getId()==note.getId())
            {
                notes.set(i,note);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i <notes.size() ; i++) {
            if(notes.get(i).getId()==id)
            {
                notes.remove(id);
                break;
            }
        }
    }
    @Override
    public List<CardNote> getAll() {
        return notes;
    }
}
