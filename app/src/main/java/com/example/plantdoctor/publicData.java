package com.example.plantdoctor;

import java.util.ArrayList;

public class publicData {

    public static int monitoringCount;
    //저장 가능 개수
    public static ArrayList<String> HemsID=new ArrayList<String> ( 100 );
    //prefernce를 사용하기위한 명칭
    public static String PREFERENCE="PLANTDATA";
    //등록 HEMS 개수
    public static String SAVECOUNT="DATACOUNT";

    //HEMS 등록 개수
    public static int getData(){
        return monitoringCount;
    }
    public static void setData(int i)
    {
        monitoringCount=i;
    }

    //HEMS ID 넣기
    //HEMS 등록한 총 개수 가져오기
    public static int getHemsCount(){return HemsID.size ();}
    //해당 index의 HEMS 아이디 가져오기
    public static String getHemsID(int i){return HemsID.get (i );}
    //HEMS ID 넣기
    public static void setHemsID(String hemsID){HemsID.add ( hemsID );}

}
