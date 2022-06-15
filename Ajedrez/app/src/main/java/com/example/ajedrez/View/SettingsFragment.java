package com.example.ajedrez.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.ajedrez.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private final String SCHOOL_PREF = "schoolPref";
    private final String CAT_NAC_PREF = "catNacPref";
    private final String CAT_EST_PREF = "catEstPref";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_screen, rootKey);
        bindPreferenceSummaryToValue(findPreference(SCHOOL_PREF));

        String ACTIVITY_PREF = "activityFilterPref";
        Preference preference = findPreference(ACTIVITY_PREF);
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getBoolean(preference.getKey(), false));

        bindPreferenceSummaryToValue(findPreference(CAT_NAC_PREF));
        bindPreferenceSummaryToValue(findPreference(CAT_EST_PREF));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                Objects.requireNonNull(PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), "")));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        if (preference.getKey().equals(SCHOOL_PREF)) {
            String stringValue = value.toString();
            if (!stringValue.equals("")) {
                preference.setSummary(stringValue);
            } else {
                preference.setSummary("Ingresa el nombre de una escuela o colegio");
            }
        }

        if (preference.getKey().equals(CAT_NAC_PREF)) {
            String[] nationalCategories = getResources().getStringArray(R.array.cat_nac);
            int index = Integer.parseInt(value.toString());
            if (index != 0) {
                preference.setSummary(nationalCategories[index-1]);
            } else {
                preference.setSummary("Elegir para mostrar solamente una categoria de las usadas en juegos nacionales");
            }
        }

        if (preference.getKey().equals(CAT_EST_PREF)) {
            String[] nationalCategories = getResources().getStringArray(R.array.cat_est);
            int index = Integer.parseInt(value.toString());
            if (index != 0) {
                preference.setSummary(nationalCategories[index-1]);
            } else {
                preference.setSummary("Elegir para mostrar solamente una categoria de las usadas en los juegos estudiantiles");
            }
        }

        return true;
    }
}