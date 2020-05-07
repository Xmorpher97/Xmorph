package com.rottenmach.vivek.xmorpher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import androidx.appcompat.app.AppCompatActivity;

public class ToolsActivity extends AppCompatActivity {

    SwitchButton swithButton;

    SharedPreferences.Editor prefEditor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        swithButton = findViewById(R.id.swithButton);
        prefEditor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        swithButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if (isChecked){

                    // DO what ever you want here when button is enabled
                    Toast.makeText(ToolsActivity.this, "Enabled", Toast.LENGTH_SHORT).show();
                    prefEditor.putString("checked","yes");
                    prefEditor.apply();

                }else {

                    // DO what ever you want here when button is disabled
                    Toast.makeText(ToolsActivity.this, "Disabled", Toast.LENGTH_SHORT).show();
                    prefEditor.putString("checked","false");
                    prefEditor.apply();
                }


            }
        });


        if (prefs.getString("checked","no").equals("yes")){

            swithButton.setChecked(true);

        }else {

            swithButton.setChecked(false);
        }



    }
}
