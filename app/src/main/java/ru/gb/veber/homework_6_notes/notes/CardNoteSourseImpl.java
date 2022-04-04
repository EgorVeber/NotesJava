package ru.gb.veber.homework_6_notes.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.java.AppContext;

public class CardNoteSourseImpl implements CardNoteSourse {

    private int counter = 0;
    static CardNoteSourseImpl sourse;
    private ArrayList<CardNote> notes ;
    private  Calendar calendar = new GregorianCalendar();

    private SharedPreferences prefs;

    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String KEY_NOTES ="KEY_NOTES";
    private Gson gson = new Gson();

    public static CardNoteSourseImpl getInstance()
    {
        if(sourse==null)
        {
            sourse= new CardNoteSourseImpl();
        }
        return sourse;
    }
    public CardNoteSourseImpl()
    {
        prefs= AppContext.getInstance().getSharedPreferences(FILE_PROFILE, Context.MODE_PRIVATE);

        notes= (ArrayList<CardNote>) getAll();
        calendar.set(2022, Calendar.MARCH, 23);
    }
    @Override
    public int create(CardNote note) {
        int id= counter++;
        note.setId(id);
        notes.add(note);
        updateProfileFile();
        return id;
    }
    @Override
    public int res_create(CardNote note,int pos) {
        int id= counter++;
        note.setId(id);
        notes.add(pos,note);
        updateProfileFile();
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
        updateProfileFile();
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i <notes.size() ; i++) {
            if(notes.get(i).getId()==id)
            {
                notes.remove(i);
                break;
            }
        }
        updateProfileFile();
    }
    public void updateProfileFile()
    {
        String data =gson.toJson(notes);//будет сериализован
        prefs.edit().putString(KEY_NOTES,data).apply();
    }
    @Override
    public List<CardNote> getAll() {
        String data = prefs.getString(KEY_NOTES,"{}");
        try {
            notes = gson.fromJson(data,new TypeToken<List<CardNote>>(){}.getType());
        }
        catch (Exception e)
        {
            Log.d("happy", "Exception: " + e.getMessage());
        }
        if(notes==null)
        {
            notes= new ArrayList<>();
        }
        return notes;
    }
    @Override
    public int getSize() {
        return notes.size();
    }

    @Override
    public void sortReverse() {
        Collections.reverse(notes);
        updateProfileFile();
    }
    @Override
    public void sortName() {
        Collections.sort(notes);
        updateProfileFile();
    }
    @Override
    public void sortId() {
        Collections.sort(notes,new ComparatorId());
        updateProfileFile();
    }
}
class ComparatorId implements Comparator<CardNote>{

    public int compare(CardNote a, CardNote b){

        if(a.getId()> b.getId())
            return 1;
        else if(a.getId()< b.getId())
            return -1;
        else
            return 0;
    }
}
