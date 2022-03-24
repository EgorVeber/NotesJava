package ru.gb.veber.homework_6_notes.java;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.logging.LogManager;
import java.util.regex.Pattern;

import ru.gb.veber.homework_6_notes.R;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SettingFragment";


    Pattern checkLogin = Pattern.compile("^[A-Z][a-z]{2,}$");
    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String PROFILE_NAME = "PROFILE_NAME";

    public static final String ThemeFile ="ThemeFile";
    public static final String KeyTheme="KeyTheme";


    private SharedPreferences prefs;
    private String getProfileName="Profile_name";
    public  static int NumTheme=0;

    private EditText profile_name;
    private Button save_button_profile_name;
    private  TextInputLayout layout;


    RadioButton rbD;
    RadioButton rbV1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateOptionsMenu() ");
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        layout = view.findViewById(R.id.textInputLayout2);
        profile_name= view.findViewById(R.id.profile_name_edit_text);
        save_button_profile_name=view.findViewById(R.id.save_profile_name);
        prefs = requireActivity().getSharedPreferences(FILE_PROFILE, MODE_PRIVATE);
        getProfileName= prefs.getString(PROFILE_NAME,"");
        profile_name.setText(getProfileName);


        save_button_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String profileName= profile_name.getText().toString();

                TextView textView = requireActivity().findViewById(R.id.profile_name);

                if(checkLogin.matcher(profileName).matches())
                {
                    prefs.edit().putString(PROFILE_NAME,profileName).commit();
                    layout.setError(null);
                    textView.setText(profileName);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
                else
                {
                    layout.setError("Неправильно");
                }
            }
        });


        requireActivity().setTheme(getThemePref());
        RadioGroup rg = view.findViewById(R.id.radioButtons);
        ((MaterialRadioButton)rg.getChildAt(NumTheme)).setChecked(true);
        rbD= view.findViewById(R.id.standart_button);
        rbV1= view.findViewById(R.id.test_button);
        rbD.setOnClickListener(this);
        rbV1.setOnClickListener(this);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (!isLandscape())
        {
            inflater.inflate(R.menu.menu_toolbar_edit_fragment, menu);//добавляем назад
        }
        MenuItem item = menu.findItem(R.id.item_1_toolbar_main);//Скрываем новый
        MenuItem item2 = menu.findItem(R.id.sort_reverse_toolbar_main);//Скрываем новый
        MenuItem item3 = menu.findItem(R.id.sort_name_toolbar_main);//Скрываем новый
        MenuItem item4 = menu.findItem(R.id.sort_id_toolbar_main);//Скрываем новый
        MenuItem item5 = menu.findItem(R.id.repost_memu_item_edit_note_fargment);//Скрываем новый
            if (item != null ) {
                item.setVisible(false);
                item2.setVisible(false);
                item3.setVisible(false);
                item4.setVisible(false);

            }
            if(item5 != null )
            {
                item5.setVisible(false);
            }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.back_memu_item_edit_note_fargment)
        {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.standart_button:
                setThemePref(0);
                requireActivity().recreate();
                break;
            case R.id.test_button:
                 setThemePref(1);
                requireActivity().recreate();
                break;

            default:break;
        }
    }
    private void setThemePref(int codeStyle) {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(ThemeFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(KeyTheme, codeStyle);
        editor.apply();
    }

    private int getThemePref()
    {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(ThemeFile, MODE_PRIVATE);
        NumTheme = sharedPref.getInt(KeyTheme, 0);

        switch(NumTheme){
            case 0:
                return R.style.Theme_Homework_6_notes;
            case 1:
                return R.style.Theme_Homework_6_notes_Test;
            default:
                return R.style.Theme_Homework_6_notes;
        }
    }
}
