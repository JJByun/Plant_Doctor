package com.example.plantdoctor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapterEx extends BaseAdapter {
    Context mContext = null;
    ArrayList<Plant> mData = null;
    LayoutInflater mLayoutInflater = null;

    public BaseAdapterEx (Context context, ArrayList<Plant> data){
        mContext = context;
        mData = data;
        //리소스를 전개하여 뷰 객체들을 만드는 역할
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //리스트뷰에서 자식 아이템 개수 획득
    public int getCount(){
        return mData.size();
    }

    //아이템의 데이터 전달
    public Plant getItem(int position){
        return mData.get(position);
    }

    //아이템 위치 전달
    @Override
    public long getItemId(int i) {
        return i;
    }

    //Data Set
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //View itemLayout=mLayoutInflater.inflate(R.layout.activity_list__view__search1_,null);

        //Scroll 최적화
        View itemLayout = view;
        //null 이면 재사용할 아이템이 없으므로 새로운 뷰 생성
        if(itemLayout == null){
            itemLayout=mLayoutInflater.inflate(R.layout.activity_list__view__search1_,null);
        }

        TextView txtLeft = (TextView)itemLayout.findViewById(R.id.txtLeft);
        ImageView imgLeft = (ImageView)itemLayout.findViewById(R.id.imgLeft);

        TextView txtFlowerName = (TextView)itemLayout.findViewById(R.id.txtFlowerName);
        TextView txtFlowerDescription = (TextView)itemLayout.findViewById(R.id.txtFlowerDescription);

        //Log.d("androidTest","PlantName: "+mData.get(i).plantName);
        //Log.d("androidTest","PlantImg: "+mData.get(i).imageNum);

        txtLeft.setText(mData.get(i).plantName);
        imgLeft.setImageResource(mData.get(i).imageNum);
        txtFlowerName.setText(mData.get(i).plantName);
        txtFlowerDescription.setText(mData.get(i).plantDescription);


        return itemLayout;
    }
}
