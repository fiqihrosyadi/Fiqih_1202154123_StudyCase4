package com.vkeyws.studycase4async;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //membuat variable
    Button list,sImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menginisialisasi variable
        list = findViewById(R.id.namelist);
        sImage = findViewById(R.id.carigambar);

        //membuat action pada button list
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat perpindahan layout
                Intent a = new Intent(MainActivity.this,NameList.class);
                //mulai menjalankan perpindahan
                startActivity(a);
            }
        });

        //membaut actionpada button pada sImage
        sImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat perpindahan layout
                Intent b = new Intent(MainActivity.this,Search.class);
                //mulai menjalankan perpindahan
                startActivity(b);
            }
        });
    }
}
