package com.example.plantdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ResultActivity extends AppCompatActivity {

    ImageView imgResult;
    Map<String, String> disMap;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        disMap =new HashMap<String, String>();
        // map 값 세팅
        disMap = setHashMap(disMap);

        Intent intent = getIntent();

        String responseMsg = intent.getStringExtra("result");
        if(responseMsg.equals(null)){
            Toast.makeText(this, "Socket Communication is fail", Toast.LENGTH_SHORT).show();
        }else{
            responseMsg = responseMsg.substring(11);
            responseMsg = responseMsg.substring(0,responseMsg.length()-3);

            String [] strResult = responseMsg.split(",");
            Log.i("androidTest","split Msg: " +responseMsg);

            TextView txtPlantName = (TextView)findViewById(R.id.txtPlantName);
            TextView txtPlantState = (TextView)findViewById(R.id.txtPlantState);
            TextView txtPlantTreatment = (TextView)findViewById(R.id.txtTreatment);
            txtPlantTreatment.setMovementMethod(new ScrollingMovementMethod());
            txtPlantName.setText(strResult[0]);
            if(strResult[1].equals("Healthy")){
                //건강하면 초록색으로 결과 도출
                txtPlantState.setTextColor(getColor(R.color.colorResultGreen));
                txtPlantTreatment.setTextColor(getColor(R.color.colorResultGreen));
                txtPlantTreatment.setText("Your Plant is Healthy :)");
            }else{
                //질병일 경우 빨간색으로 결과 도출
                txtPlantState.setTextColor(getColor(R.color.colorResultRed));
                txtPlantTreatment.setTextColor(getColor(R.color.colorResultRed));

                String disManagement = disMap.get(strResult[1].substring(0,1).toUpperCase()+strResult[1].substring(1));
                Log.i("androidTest",disManagement);
                txtPlantTreatment.setText(disManagement);
            }
            txtPlantState.setText(strResult[1]);

            //그래프 그리기
            pieChart = (PieChart) findViewById(R.id.pieChart);
            initChart();
        }

        //그림 다시 붙여주기
        imgResult = (ImageView)findViewById(R.id.imgResult);
        String imgPath = intent.getStringExtra("imgPath");
        Uri imguri = Uri.parse(imgPath);
        try {
            InputStream in = getContentResolver().openInputStream(imguri);
            Bitmap img = BitmapFactory.decodeStream(in);
            in.close();
            imgResult.setImageBitmap(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        imgUri = data.getData();
                    Log.i("androidTest","uri path: "+imgUri);
                    String absPath = DocumentsContract.getDocumentId(imgUri);
                    absPath = absPath.substring(4);
                    Log.i("androidTest","abs Path: "+absPath);
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    imgSelectedPicture.setImageBitmap(img);
         */
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Press Home Button",Toast.LENGTH_SHORT).show();
        //super.onBackPressed();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnHome:{
                Intent intent  = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }

        }
    }

    // 데이터 저장 함수
    //bool type
    public void setPreference(String key, boolean value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    //string type
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    //int type
    public void setPreference(String key, int value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    //float type
    public void setPreference(String key, float value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    //long type
    public void setPreference(String key, long value){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    // 데이터 불러오기 함수
    public boolean getPreferenceBoolean(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }
    public String getPreferenceString(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getString(key, "");
    }
    public int getPreferenceInt(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getInt(key, 0);
    }
    public float getPreferenceFloat(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getFloat(key, 0f);
    }
    public long getPreferenceLong(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        return pref.getLong(key, 0l);
    }

    // 데이터 한개씩 삭제하는 함수
    public void setPreferenceRemove(String key){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
    // 모든 데이터 삭제
    public void setPreferenceClear(){
        SharedPreferences pref = getSharedPreferences(publicData.PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    private Map<String, String> setHashMap(Map<String, String> myMap){
        Map<String , String> map = myMap;
        //croton
        map.put("MealyBugs","Killing Small Infestations with Rubbing Alcohol\n" +
                "   Using Neem Oil for Potted or Shaded Plants\n" +
                "   Using Insecticides");
        map.put("ScaleInsects","Rub the scales off your plant with brush\n" +
                "   Try using insecticidal soap\n" +
                "   Horticultural Oil Precautions\n" +
                "   Prune away parts of the plant that are infested with scales");

        //synganium
        map.put("BacterialLeafBlight","Purchase plants free from the disease. Discard infected plants.\n" +
                "Avoid overhead watering. Apply a bactericide to protect plants.");
        map.put("BacterialStemRot","Purchase plants free of the disease. Discard infected plants and\n" +
                "water in a manner that keeps the surface of the plants dry at all times.");

        //MothOrchid
        map.put("BacterialBrownSpot","This is a disease that needs to be detected quite early because it can kill a plant very rapidly. Once you\n" +
                "notice the spot you should get a sterile instrument and cut out the infected area. This is a disease that needs to\n" +
                "be detected quite early because it can kill a plant very rapidly. Once you notice the spot you should get a sterile\n" +
                "instrument and cut out the infected area. Once the area is cut you need to spray the area with Physan 20 or\n" +
                "Phyton 27. If neither of these solutions are readily available, then you can use either cinnamon or Listerine as an\n" +
                "alternative. If this disease is not detected early, then it may have spread to the crown of the orchid and this\n" +
                "almost always leads to orchid death.");
        map.put("BlackRot","To treat black rot, remove the infected tissue with a sterile tool, and spray a fungicide on the area\n" +
                "you’ve cut.");
        map.put("Botrytis","To help prevent botrytis from spreading, always remove wilted blooms or those that have already fallen\n" +
                "off of the plant. You will want to remove any affected flowers using a sterile instrument and then spray the area\n" +
                "where you’ve removed the bloom with a fungicide");

        //anthurium
        map.put("BacterialBlight","Lower Greenhous humidity and temperature by increasing air circulation and venting the production facility\n" +
                "   Clean, tissue-cultured plantlets should be used when establishing new plantings\n" +
                "   Discard immediately");
        map.put("BacterialWilt","A strict sanitation program is most successful way to stop the spread of this pathogen\n" +
                "   Fungicides that contain phosphorous acid have also been shown to be effective in preventing infection");
        map.put("BlackNose","A strict sanitation program is crucial to control the spread of this pathogen in a production facility\n" +
                "   Fungicides containing mancozeb are effective");
        map.put("PhytophthoraPythium","Use well-drained, synthetic soil mixes\n" +
                "   Should be discarded and the rest of the production facility should be treated with a fungicide drench");
        map.put("Rhizoctonia_RootRot","Never incorporate native soils into media mixes without steam sterilizing\n" +
                "   Many fungicides are effective against outbreaks of Rhizoctonia");

        //golden pothos
        map.put("BacterialLeafSpot","Avoid overhead watering. Discard infected plants.");
        map.put("BacterialWilt","Symptoms are especially severe during warm periods of the year.\n" +
                "Bacterial multiplication is rapid, and an aggressive sanita- tion program will be needed. If an outbreak occurs, plants, soil, and pots should be bagged and removed from the nursery. Benches and tools should be disinfected before any replanting is done. There are no effective bactericides that can be used in a nursery setting.");
        map.put("PhytophthoraRootRot","In commercial greenhouse production, exclusion is the best method of controlling this disease. Pothos plants expressing symptoms should be discarded. Because Phytophthora zoo- spores are readily transported via water, reducing irrigation volumes will help control disease spread. Remaining plants should be treated with an appropriate fungicide, such as aluminum tris/Fosetyl-al (Aliette® WDG), dimethomorph (Stature®), dimethomorph + ametoctradin (OrvegoTM), fluopicolide (AdornTM), and phosphorous acid (AludeTM, K-Phite®, Vital®), to effectively control Phytophthora. For more information on fungicide selection, please visit the Homeowner’s Guide to Fungicides for Lawn and Landscape Disease Management or the Fungicide Resistance Action Committee’s (FRAC) Classification Scheme of Fungicides According to Mode of Action.");

        //lucky bamboo
        map.put("BambooMites","A small infestation of bamboo spider mites can be controlled with insecticidal soap, a pyrethrin-\n" +
                "based spray or a contact pesticide. However, sprays aren’t usually effective for severe\n" +
                "infestations because the plant’s height and clumping nature prevent the substances from\n" +
                "reaching the pests. Additionally, it is difficult to reach mites hiding under the dense webbing. A\n" +
                "systemic miticide approved for bamboo mites is often more effective for bamboo mite control\n" +
                "because it is absorbed throughout the plant and kills the pests as they feed.");
        map.put("PowderyMildew","It depends on what you are calling mildew.\n" +
                "If you have an algae build up in the water or around the roots of the lucky bamboo, you need to\n" +
                "clean the container and any pebbles with soapy water and wipe off the roots and the stalks with\n" +
                "a moist cloth and refill the container.\n" +
                "\n" +
                "If you have lesions on the stalk, you will need to remove the lesions. In this case you may need\n" +
                "to create new stalks from the top of the stalk. You might want to read this lucky bamboo post, it\n" +
                "details how to remove diseased parts of lucky bamboo.\n" +
                "If you have white cottony substance on the stalks, it could actually be an insect called scale.\n" +
                "You can wipe this insect off and clean the container as a method of control. If the scale\n" +
                "persists, use an insecticide that is safe for houseplants.\n" +
                "If you have a type of fungus that is rust or sooty in color, we have a whole other ballgame.\n" +
                "These would need to be treated with a fungicide. The hardest part is finding a fungicide safe\n" +
                "for Dracaena. You could try daconil. It is a general purpose fungicide.");

        //
        map.put("InsectPests","Dracaena plants are generally free from serious insect or disease problems. However, you\n" +
                "should watch out for mealybugs, spider mites, and scale. Mealybugs and scale can both be\n" +
                "treated with an insecticide that contains pyrethrin.");
        map.put("Soil","Make sure container is well-drained. Use a potting soil with a loamy soil (a mixture of silt, sand,\n" +
                "and clay), along with some peat. Like all Dracaenas, the marginata flourishes in a humid\n" +
                "atmosphere. Mist the leaves occasionally, and keep the plant away from dry rooms with\n" +
                "excessive central heating.");
        map.put("Temperature","Dracaena prefers temperatures ranging from 65 – 78℉ during the day. Night temperatures can\n" +
                "drop about ten degrees cooler, but cold drafts and temperatures below 55℉ will harm the plant.\n" +
                "Make sure that you display your dracaena away from any heating or cooling appliances.\n" +
                "Natural room humidity is fine seeing as the dracaena is such a hardy houseplant, but it does\n" +
                "prefer the higher humidity of its natural rainforest habitat.");

        map.put("InsectInfection","Mealybugs live in colonies, so you will never just have one at a time to deal with. You will\n" +
                "have a mass of them to handle. Clean the plant with alcohol to remove bugs or insects. Use a soft\n" +
                "tissue, wipe the infected area, and treat with a good all-purpose organic pesticide, such as Neem Oil.");
        map.put("LackOfWater","Water at least once a week and keep the soil moist. Throughout the summer growing\n" +
                "season, spritz the leaves with soft or distilled water. Water your plant less often in winter.");
        map.put("Overwatering","The best approach falls between waiting for the soil to dry completely before watering\n" +
                "and watering so often that constantly stands in water. If the pot&#39;s diameter is smaller than 6 inches, water\n" +
                "when the top 1 inch of medium feels dry when you insert your index finger into it. For plants in larger\n" +
                "containers, let the top 2 inches of medium dry to the touch before watering. If your finger has difficulty\n" +
                "penetrating to the correct depth, check to see if the plant is root-bound. Always plant your peace lilies in\n" +
                "pots with drainage holes, and always water them until they begin to drain. Unless water is draining, the\n" +
                "bulk of the peace lily&#39;s root ball isn&#39;t completely wet. The escaping water performs the additional service\n" +
                "of washing out fertilizer salt residues. Accumulated salts can burn the roots and brown the foliage.\n" +
                "Empty the pot&#39;s saucer as soon as the water stops draining.");
        map.put("Sunburn","Consider placing the plant six to eight feet away from a north- or west-facing window.");


        map.put("Bugs","As with soft scale insects, an easy method of control is to apply alcohol with cotton swabs\n" +
                "directly on the bug. Wiping down the foliage regularly and helping plants clean will help keep these white\n" +
                "fuzzy bugs in check. For a mealy bug insecticide, if a plant becomes severely infested consider using\n" +
                "safe natural organic neem oil sprays to control the pest or make your own homemade insecticidal\n" +
                "soap or possibly horticultural oils.");
        map.put("Cylindrocladium","Sanitation and water management are critical for leaf spot disease management. For\n" +
                "water management, elimination of overhead irrigation or protection from rainfall is highly recommended.\n" +
                "Leaf spots are minimal if leaves are kept dry. If this is not possible, time the irrigation so the leaves are\n" +
                "wet for a minimal number of hours. This usually means irrigating in the hours before dawn. Increase air\n" +
                "circulation to keep plants drier by increasing spacing between plants and making sure larger plants are\n" +
                "not blocking air movement to smaller plants.");

        map.put("BrownSpots","Avoid going into fields that are wet. Other cultural practices for management include avoiding the reuse of irrigation water, and incorporating infected debris after harvest and rotating out of beans with other crops for at least two years, and managing volunteer beans.");
        map.put("WaterOversupply","reduce water supply");

        return map;
    }

    public void initChart(){
        //enable value hilighting
        pieChart.setDefaultFocusHighlightEnabled(true);
        //touch enable
        pieChart.setTouchEnabled(true);

        //alternative background color
        pieChart.setBackgroundColor(Color.TRANSPARENT);


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(89f,"Acc"));
        entries.add(new PieEntry(11f,""));
        PieDataSet pieDataSet = new PieDataSet(entries,"Accuracy");
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        Description des = new Description();
        des.setText("");
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription(des);
        pieChart.animateXY(1000,1000);
        pieChart.invalidate();
    }


}
