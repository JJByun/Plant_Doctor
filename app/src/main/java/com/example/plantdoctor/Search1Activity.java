package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Search1Activity extends AppCompatActivity {

    Intent intent;
    ListView mListView = null;
    ArrayList<Plant> mData = null;
    BaseAdapterEx mAdapter = null;
    String[] plantName = {"Croton","Syngonium","Moth Orchid", "Anthurium","Golden Pothos",
            "Lucky Bamboo","Dracaena Marginata", "Snake Plant","Peace Lily","Ponytail Palm"};
    String[] plantDescription = {
            "Croton variegatum is a species of plant in the genus Codiaeum, which is a member of the family Euphorbiaceae. It is native to Indonesia, Malaysia, Australia, and the western Pacific Ocean islands, growing in open forests and scrub.",
            "Syngonium is a species of aroid, and commonly cultivated as a houseplant. Common names\n" +
                    "include: arrowhead plant, arrowhead vine, arrowhead philodendron, goosefoot, African\n" +
                    "evergreen, and American evergreen. The species is native to a wide region of Latin\n" +
                    "America from Mexico to Bolivia, and naturalized in the West Indies, Florida, Texas, Hawaii , and\n" +
                    "other places.",
            ": Phalaenopsis commonly known as moth orchids, [2]  is a genus of about seventy species of orchids\n" +
                    "in the family orchid. Orchids in this genus are monopodial epiphytes or lithophytes with long, coarse roots, short,\n" +
                    "leafy stems and long-lasting, flat flowers arranged in a flowering stem that often branches near the end. Orchids\n" +
                    "in this genus are native to India, China, Southeast Asia, New Guinea and Australia with the majority\n" +
                    "in Indonesia and the Philippines.",
            "[Anthurium]\n" +
                    "Commonly known as flamingo flower, Anthurium has nearly 1,000 species, making it the largest genus in the plant family Araceae. Anthruium is native to tropical America, Mexico, Costa Rica, Cuba, and Brazil. Growth habits vary depending on species.",
            "The pothos plant is considered by many to be a great way to get started caring for houseplants. Because pothos care in easy and undemanding, this lovely plant is an easy way to add some green in your home.\n" +
                    "\n" +
                    "Read more at Gardening Know How: Information On Caring For Pothos Plants",
            "Lucky bamboo is an indoor plant that is attractive, popular, and easy to care for. It&#39;s also considered to\n" +
                    "be an auspicious plant that brings good luck according to the principles of feng shui and vastu\n" +
                    "shastra. Lucky bamboo is a great indoor plant that is very easy to grow. Not only does it do well in\n" +
                    "soil, but it does well in plain water, as well. Properly cared for, it can grow to about 2 to 3 feet in\n" +
                    "height.\n",
            "Dracaena Marginata\n" +
                    "Dracaena marginata is an evergreen tree that can grow eight to 15 feet high and three to eight feet\n" +
                    "wide with proper care. It has stiff purplish-red leaves and slim, curving stalks for trunks. Since it\n" +
                    "cannot tolerate low light but is not frost hardy, it is often grown indoors. They make excellent\n" +
                    "houseplants because they are drought tolerant and among the more forgiving dracaena plants.",
            "Sansevieria trifasciata is a species of flowering plant in the family Asparagaceae, native to tropical West Africa from\n" +
                    "Nigeria east to the Congo. It is most commonly known as the snake plant, Saint George&#39;s sword, mother-in-law&#39;s\n" +
                    "tongue, and viper&#39;s bowstring hemp, among other names.",
            "Peace lilies are tropical, evergreen plants that thrive on the forest floor, where they receive dappled sunlight and\n" +
                    "consistent moisture. Replicating these conditions in the home is the key to getting your peace lily to be happy\n" +
                    "and healthy.\n" +
                    "With enough light, peace lilies may produce white to off-white flowers in the early summer and continue to bloom\n" +
                    "throughout the year. \n" +
                    "Most household varieties of peace lily grow up to 16 inches tall, but larger outdoor cultivars can reach 6 feet in height.\n" +
                    "Peace lilies are not cold-hardy plants, so they may only be grown outdoors in warm, humid climates",
            "Beaucarnea recurvata (elephant&#39;s foot, ponytail palm) is a species of plant in the family Asparagaceae, native to the\n" +
                    "states of Tamaulipas, Veracruz and San Luis Potosí in eastern Mexico. Despite its common name, it is not closely\n" +
                    "related to the true palms (Arecaceae). It has become popular in Europe and worldwide as an ornamental plant. There\n" +
                    "are 350-year-old Beaucarneas registered in Mexico."};
    Integer[] plantImg = {R.drawable.img_croton, R.drawable.img_syngonium2, R.drawable.img_moth_orchid, R.drawable.img_anthurium,R.drawable.img_golden_photos,
    R.drawable.img_lucky_bamboo,R.drawable.img_dracaena_marginata,R.drawable.img_snake_plant,R.drawable.img_peace_lily,R.drawable.img_ponytail_palm};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);

        intent = new Intent(this, Search2Activity.class);
        //어댑터 데이터 세팅
        mData = new ArrayList<Plant>();

        //data Set
        for(int i =0; i<10; i++){
            Plant plant = new Plant();
            plant.plantName=plantName[i];
            plant.imageNum=plantImg[i];
            plant.plantDescription=plantDescription[i];
            mData.add(plant);

        }

        //어뎁터를 생성하고 데이터 설정
        mAdapter = new BaseAdapterEx(this,mData);

        //리스브튜에 어댑터 설정
        mListView = (ListView)findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("androidTest","Num: "+i);
                intent.putExtra("flowerName", plantName[i].toString());
                startActivity(intent);
            }
        });
    }



}
