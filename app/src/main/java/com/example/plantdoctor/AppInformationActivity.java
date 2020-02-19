package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AppInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imgBtnRefresh:{
                Toast.makeText(this.getApplicationContext(),"Latest Version",Toast.LENGTH_SHORT).show();
            }
        }

    }


}
