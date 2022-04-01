package ru.gb.veber.homework_6_notes.DIalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import ru.gb.veber.homework_6_notes.notes.CardNote;

public class ViewModelDialog extends ViewModel {

    //Чтобы вернуть данные
    private MutableLiveData<Date> secondInput = new MutableLiveData<>();
    public void secondSay(Date text)
    {
        secondInput.postValue(text);
    }
    public LiveData<Date> getSecondInput()
    {
        return secondInput;
    }

//    private MutableLiveData<Date> dateInput = new MutableLiveData<>();
//    public void DateSay(Date text)
//    {
//        dateInput.postValue(text);
//    }
//    public LiveData<Date> getDate()
//    {
//        return dateInput;
//    }
}
