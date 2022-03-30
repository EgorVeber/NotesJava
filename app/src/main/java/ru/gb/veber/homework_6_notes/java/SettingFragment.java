package ru.gb.veber.homework_6_notes.java;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import ru.gb.veber.homework_6_notes.R;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SettingFragment";
    private Pattern checkLogin = Pattern.compile("^[A-Z][a-z]{2,}$");

    private SharedPreferences prefs;
    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String KEY_PROFILE = "PROFILE_NAME";
    public static final String KEY_THEME = "KeyTheme";
    public static final String KEY_CHECKBOX = "KeyCheckbox";

    private String getProfileName = "Profile_name";
    private boolean getCheckBox = false;
    public static int getNumTheme = 0;

    private EditText profile_name;
    private Button save_button_profile_name;
    private TextInputLayout textInputLayout;

    private CheckBox checkBox;
    private RadioGroup radioGroup;
    private RadioButton radioButtonStandart;
    private RadioButton radioButtonTest;
    private TextView headerProfileName;
    private ActionBar actionBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO isLandscape что не вкидывалдо на главную после применения темы.
        // Надо подуамть что можно с этим сделать
        // Скорее всего просто очиститьв все и выдрать сеттингс фрагмент
        Log.d(TAG, String.valueOf(requireActivity().getSupportFragmentManager().getBackStackEntryCount()));
        if (isLandscape()) // Возможно дело не в этом
            requireActivity().getSupportFragmentManager().popBackStack();//При перевороте показваем списки
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle("Settings");
        init(view);
    }
    public void init(View view)
    {
        prefs = requireActivity().getSharedPreferences(FILE_PROFILE, MODE_PRIVATE);

        textInputLayout = view.findViewById(R.id.textInputLayout2);
        profile_name = view.findViewById(R.id.profile_name_edit_text);
        save_button_profile_name = view.findViewById(R.id.save_profile_name);
        checkBox = view.findViewById(R.id.checkbox_settings);
        radioGroup = view.findViewById(R.id.radioButtons);
        radioButtonStandart = view.findViewById(R.id.standart_button);
        radioButtonTest = view.findViewById(R.id.test_button);

        getProfileName = prefs.getString(KEY_PROFILE, "");
        getCheckBox =prefs.getBoolean(KEY_CHECKBOX,false);
        getNumTheme = prefs.getInt(KEY_THEME, 0);

        profile_name.setText(getProfileName);
        checkBox.setChecked(getCheckBox);
        ((MaterialRadioButton) radioGroup.getChildAt(getNumTheme)).setChecked(true);


        checkBox.setOnClickListener(this);
        radioButtonStandart.setOnClickListener(this);
        radioButtonTest.setOnClickListener(this);
        save_button_profile_name.setOnClickListener(view1 -> save_button_profile_name_click());
    }
    public void save_button_profile_name_click()
    {
        getProfileName = profile_name.getText().toString();


        if (checkLogin.matcher(getProfileName).matches())
        {
            textInputLayout.setError(null);
            prefs.edit().putString(KEY_PROFILE, getProfileName).commit();

            NavigationView navigationView = requireActivity().findViewById(R.id.navigation_view);
            View header = navigationView.getHeaderView(0);
            headerProfileName  = header.findViewById(R.id.profile_name);
            headerProfileName.setText(getProfileName);

            popBackStack();
        }
        else
            textInputLayout.setError("Английский 6 букв первая заглавная");
    }

    private void popBackStack() {
        for (int i=0;i<requireActivity().getSupportFragmentManager().getBackStackEntryCount();i++)
            requireActivity().getSupportFragmentManager().popBackStack();//Очищаем все в стеке
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_toolbar_edit_fragment, menu);//добавляем назад

        MenuItem item = menu.findItem(R.id.add_item_toolbar_main);
        MenuItem item2 = menu.findItem(R.id.sort_reverse_toolbar_main);
        MenuItem item3 = menu.findItem(R.id.sort_name_toolbar_main);
        MenuItem item4 = menu.findItem(R.id.sort_id_toolbar_main);
        MenuItem item5 = menu.findItem(R.id.repost_memu_item_edit_note_fargment);
        MenuItem item6 = menu.findItem(R.id.search_toolbar_main);
        if (item == null) {}
        else
        {
            item.setVisible(false);
            item2.setVisible(false);
            item3.setVisible(false);
            item4.setVisible(false);
            item6.setVisible(false);
            item5.setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back_memu_item_edit_note_fargment)
            popBackStack();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.standart_button:
                setThemePref(0);
                requireActivity().recreate();
                break;
            case R.id.test_button:
                setThemePref(1);
                requireActivity().recreate();
                break;
            case R.id.checkbox_settings:
                if (!checkBox.isChecked())
                {
                    prefs.edit().putBoolean(KEY_CHECKBOX, false).commit();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                }
                else
                {
                    prefs.edit().putBoolean(KEY_CHECKBOX, true).commit();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                    Toast.makeText(requireActivity(), "При перевороте настройки не сохранятся", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private void setThemePref(int codeStyle) {
        prefs.edit().putInt(KEY_THEME, codeStyle).commit();
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    @Override
    public void onDetach() {
        if (actionBar != null) {
            actionBar.setSubtitle("ListNotes");
        }
        super.onDetach();
    }
}
