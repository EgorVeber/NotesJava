package ru.gb.veber.homework_6_notes.java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;

public class MainActivity extends AppCompatActivity implements AdapterNote.OnNoteClickListner{
    private static final String TAG = "CardNotes";
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 1;

    private RecyclerView list;

    private CardNoteSourse sourse  ;
    private AdapterNote adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("RecyclerView");

        sourse = new CardNoteSourseImpl().init(getResources());

        list= findViewById(R.id.list);

        adapters = new AdapterNote();
        adapters.SetNote(sourse.getAll());
        adapters.setOnNoteCliclListner(this);

        list.setHasFixedSize(true);
        list.setAdapter(adapters);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        list.addItemDecoration(itemDecoration);
    }
    @Override
    public void onNoteClick(CardNote note) {
        Log.d(TAG, "onNoteClick() called with: note = [" + note + "]");
        Intent intent = new Intent(this,EditNoteActivity.class);
        intent.putExtra(CardNote.NOTE,note);
        startActivityForResult(intent,REQUEST_CODE_SETTING_ACTIVITY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE_SETTING_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if(resultCode==RESULT_OK)
        {
            CardNote note = (CardNote) data.getSerializableExtra(CardNote.NOTE);
            sourse.update(note);
            adapters.SetNote(sourse.getAll());
        }
    }
}