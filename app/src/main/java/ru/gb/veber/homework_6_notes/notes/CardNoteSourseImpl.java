package ru.gb.veber.homework_6_notes.notes;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;

public class CardNoteSourseImpl implements CardNoteSourse {

    private int counter = 0;
    static CardNoteSourseImpl sourse;
    private ArrayList<CardNote> notes = new ArrayList<>();
    Calendar calendar = new GregorianCalendar();

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
        calendar.set(2022, Calendar.MARCH, 23);
        create(new CardNote("Дом","Убратся дома хотябы раз за неделю", calendar.getTime()));
        create(new CardNote("Стоматолог","Записан на 12:00",calendar.getTime()));
        calendar.set(2022, Calendar.MAY, 21);
        create(new CardNote("Кино","Сходить на что нибудь",calendar.getTime()));
        calendar.set(2022, Calendar.FEBRUARY, 22);
        create(new CardNote("Спортзал","Мб когда нибудь",calendar.getTime()));
        calendar.set(2022, Calendar.JANUARY, 23);
        create(new CardNote("Android","Заниматся разработкой, по практиковатся с Intent и приступать к фрагментам + почитать и сделать примеи TextInputLayout ",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 31);
        create(new CardNote("Вебинар","Успеть к  20:00",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 21);
        create(new CardNote("Работа","Не забыть что отпуск закончился",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 12);
        create(new CardNote("Дом","Забрать смеситель",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 11);
        create(new CardNote("Подарки","Подумать о подарках",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 9);
        create(new CardNote("Отпуск","Подписать заявление на отпуск",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 2);
        create(new CardNote("8 Марта","Забрать подарки",calendar.getTime()));
        calendar.set(2022, Calendar.MARCH, 1);
        create(new CardNote("Врач","Запись к офтальмологу",calendar.getTime()));
    }
    @Override
    public int create(CardNote note) {
        int id= counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }
    @Override
    public int res_create(CardNote note,int pos) {
        int id= counter++;
        note.setId(id);
        notes.add(pos,note);
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

    @Override
    public void sortReverse() {
        Collections.reverse(notes);
    }
    @Override
    public void sortName() {
        Collections.sort(notes);
    }
    @Override
    public void sortId() {
        Collections.sort(notes,new ComparatorId());
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
