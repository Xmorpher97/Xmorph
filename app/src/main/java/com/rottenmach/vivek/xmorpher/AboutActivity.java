package com.rottenmach.vivek.xmorpher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button button = (Button) findViewById(R.id.toast);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0)
            {

                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://www.instagram.com/vivek.97/"));
                startActivity(myWebLink);

            }

        });



    }
}
