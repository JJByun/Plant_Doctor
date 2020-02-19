package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.layout_btnSearch:{
                //검색 화면 넘기기
                intent = new Intent(this, Search1Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.layout_btnDiagnosis:{
                //진단 화면 넘기기
                intent = new Intent(this, Diagnosis1Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.layout_btnStatistic:{
                //intent = new Intent(this,DiagnosisActivity.class);
                //startActivity(intent);
                break;
            }
            case R.id.layout_btnInformation:{
                intent = new Intent(this,InformationActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
