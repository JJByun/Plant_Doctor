package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DiagnosisActivity extends AppCompatActivity {

    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        initChart();
    }

    public void initChart(){
        //enable value hilighting
        pieChart.setDefaultFocusHighlightEnabled(true);
        //touch enable
        pieChart.setTouchEnabled(true);

        //alternative background color
        pieChart.setBackgroundColor(Color.TRANSPARENT);


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(800f,"Accuracy"));
        entries.add(new PieEntry(200f,"error"));
        PieDataSet pieDataSet = new PieDataSet(entries,"Accuracy");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(1000,1000);
        pieChart.invalidate();
    }
}
