package com.example.petdatabase.ui.preference

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.petdatabase.R

class BaseSortPreference : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }


}