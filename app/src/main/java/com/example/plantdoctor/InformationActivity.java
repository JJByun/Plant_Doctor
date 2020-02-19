package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLicense:{
                Intent intent  = new Intent(this, LicenseInformationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnInfo:{
                Intent intent = new Intent(this, AppInformationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnHowToUse:{
                break;
            }
        }

    }
}
