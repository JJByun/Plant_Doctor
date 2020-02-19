package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class Search2Activity extends AppCompatActivity {
    ArrayList<String> flowers = new ArrayList<String>();
    ArrayList<String> descriptions = new ArrayList<String>();
    TextView txtDescription;
    ImageView imgZoom;

    Integer[] plantImg = {R.drawable.img_croton, R.drawable.img_syngonium2, R.drawable.img_moth_orchid, R.drawable.img_anthurium,
            R.drawable.img_golden_photos,R.drawable.img_lucky_bamboo,R.drawable.img_dracaena_marginata,R.drawable.img_snake_plant,R.drawable.img_peace_lily,R.drawable.img_ponytail_palm};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        flowers.add("Croton");
        descriptions.add(getText(R.string.croton_description).toString() );

        flowers.add("Syngonium");
        descriptions.add(getText(R.string.Syngonium_description).toString());

        flowers.add("Moth Orchid");
        descriptions.add(getText(R.string.moth_orchid_description).toString());

        flowers.add("Anthurium");
        descriptions.add(getText(R.string.anthurium_description).toString());

        flowers.add("Golden Pothos");
        descriptions.add(getText(R.string.golden_photos_description).toString());

        flowers.add("Lucky Bamboo");
        descriptions.add(getText(R.string.lucky_bamboo_description).toString());

        flowers.add("Dracaena Marginata");
        descriptions.add(getText(R.string.dracaena_marginata_description).toString());

        flowers.add("Snake Plant");
        descriptions.add(getText(R.string.snake_plant_description).toString());

        flowers.add("Peace Lily");
        descriptions.add(getText(R.string.peace_lily_description).toString());

        flowers.add("Ponytail Palm");
        descriptions.add(getText(R.string.ponytail_palm_description).toString());

        txtDescription = (TextView)findViewById(R.id.txtSearch2Description);
        imgZoom = (ImageView)findViewById(R.id.imgSearch2Zoom);

        txtDescription.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        String flowerName = intent.getStringExtra("flowerName");

        Log.d("androidTest",flowerName.toString());

        //넘겨받은 꽃 이름 찾기
        int i =0;

        for (String s :flowers)
        {
            if(flowerName.equals(s)){
                Log.d("androidTest","flower: "+flowerName);
                Log.d("androidTest","number: "+i);
                imgZoom.setImageResource((plantImg[i]));
                txtDescription.setText(descriptions.get(i));

                /*if(strName.equals(flowers.get(0))){
                    imgZoom.setImageResource(R.drawable.img_croton);
                    txtDescription.setText(descriptions.get(i));
                    break;
                }
                else if(strName.equals(flowers.get(1))){
                    imgZoom.setImageResource(R.drawable.img_anthurium);
                    txtDescription.setText(descriptions.get(i));
                    break;
                }
                else if(strName.equals(flowers.get(2))){
                    imgZoom.setImageResource(R.drawable.img_dracaena_marginata);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(3))){
                    imgZoom.setImageResource(R.drawable.img_golden_photos);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(4))){
                    imgZoom.setImageResource(R.drawable.img_lucky_bamboo);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(5))){
                    imgZoom.setImageResource(R.drawable.img_moth_orchid);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(6))){
                    imgZoom.setImageResource(R.drawable.img_peace_lily);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(7))){
                    imgZoom.setImageResource(R.drawable.img_snake_plant);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(8))){
                    imgZoom.setImageResource(R.drawable.img_syngonium2);
                    txtDescription.setText(descriptions.get(i));
                }
                else if(strName.equals(flowers.get(9))){
                    imgZoom.setImageResource(R.drawable.img_ponytail_palm);
                    txtDescription.setText(descriptions.get(i));
                }*/

            }
            i++;
        }
    }
}
