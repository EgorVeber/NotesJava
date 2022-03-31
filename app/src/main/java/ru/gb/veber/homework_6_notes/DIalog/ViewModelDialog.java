package ru.gb.veber.homework_6_notes.DIalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import ru.gb.veber.homework_6_notes.notes.CardNote;

public class ViewModelDialog extends ViewModel {

    //Чтобы вернуть данные
    private MutableLiveData<CardNote> secondInput = new MutableLiveData<>();
    public void secondSay(CardNote text)
    {
        secondInput.postValue(text);
    }
    public LiveData<CardNote> getSecondInput()
    {
        return secondInput;
    }

    private MutableLiveData<CardNote> dateInput = new MutableLiveData<>();
    public void DateSay(CardNote text)
    {
        dateInput.postValue(text);
    }
    public LiveData<CardNote> getDate()
    {
        return dateInput;
    }
}
