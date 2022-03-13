package ru.gb.veber.homework_6_notes.notes;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;

public class CardNoteSourseImpl implements CardNoteSourse {

    private static final String TAG = "sdfdsf";
    private int counter = 0;
    private Resources resources;
    static CardNoteSourseImpl sourse;
    private ArrayList<CardNote> notes = new ArrayList<>();

    public static CardNoteSourseImpl getInstance()
    {
        if(sourse==null)
        {
            sourse= new CardNoteSourseImpl();
        }
        Log.d(TAG, "getInstance() called");
        return sourse;
    }
    public CardNoteSourseImpl()
    {
        create(new CardNote("Дом","06.03.22","Убратся дома хотябы раз за неделю"));
        create(new CardNote("Стоматолог","09.03.22","Записан на 12:00"));
        create(new CardNote("Кино","15.03.22","Сходить на что нибудь"));
        create(new CardNote("Спортзал","99.99.99","Мб когда нибудь"));
        create(new CardNote("Android","10.03.22","Заниматся разработкой, по практиковатся с Intent и приступать к фрагментам + почитать и сделать примеи TextInputLayout "));
        create(new CardNote("Вебинар","04.03.22","Успеть к  20:00"));
        create(new CardNote("Работа","11.03.22","Не забыть что отпуск закончился"));
        create(new CardNote("Дом","06.03.22","Забрать смеситель"));
        create(new CardNote("Подарки","05.03.22","Подумать о подарках"));
        create(new CardNote("Отпуск","04.03.22","Подписать заявление на отпуск"));
        create(new CardNote("8 Марта","07.03.22","Забрать подарки"));
        create(new CardNote("Врач","11.03.22","Запись к офтальмологу"));
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
                notes.remove(i);
                break;
            }
        }
    }
    @Override
    public List<CardNote> getAll() {
        return notes;
    }

    @Override
    public int getSize() {
        return notes.size();
    }
}
