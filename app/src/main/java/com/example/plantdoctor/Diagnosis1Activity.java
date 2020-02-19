package com.example.plantdoctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Diagnosis1Activity extends AppCompatActivity {

    // 상수
    private static final int MY_PERMISSION_REQUEST_EXTERNAL_READ = 1001;
    private static final int MY_PERMISSION_REQUEST_EXTERNAL_WRITE = 1002;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1003;
    private static final int MY_STORAGE_ACCESS = 101;
    private static final int CAMERA_CAPTURE = 102;
    private static final String SAVE_DIRECTORY_PATH = Environment.getExternalStorageDirectory()+"/Captures";
    private static final String ABSOLUTE_PATH = Environment.getExternalStorageDirectory().toString();
    //변수
    ImageView imgSendLoading;
    ImageView imgSelectedPicture;
    TextView txtSendLoading;
    Button btnCapture, btnStorage, btnSend;
    Uri imgUri;
    File selectedFile;
    String responseMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis1);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnStorage = (Button) findViewById(R.id.btnStorage);
        btnSend = (Button)findViewById(R.id.btnSendToServer);
        imgSelectedPicture = (ImageView) findViewById(R.id.imgSelectFromUser);
        imgSendLoading= (ImageView) findViewById(R.id.imgSendLoading);
        txtSendLoading = (TextView)findViewById(R.id.txtSendLoading);
        //camera Permission 체크
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //아직 부여 받지 않았으므로 요청
        if( permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            }
            //퍼미션 부여 받음
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        //storage read Permission 체크
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if( permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }
            //퍼미션 부여 받음
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_EXTERNAL_READ);
            }
        }

        //storage write Permission 체크
        int permissionCheck3 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck3 != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }
            //퍼미션 부뎌 받음
            else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_EXTERNAL_WRITE);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //camera permission result
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Camera Permission is approved", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"Camera Permission is disapproved ",Toast.LENGTH_LONG).show();
                }
                return;
            }
            //external storage read permission result
            case MY_PERMISSION_REQUEST_EXTERNAL_READ:{
                if(grantResults.length >0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"External Storage Read Permission is approved",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"External Storage Read Permission is disapproved",Toast.LENGTH_SHORT).show();
                }
                return;
            }
            //external storage write permission result
            case MY_PERMISSION_REQUEST_EXTERNAL_WRITE:{
                if(grantResults.length >0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"External Storage Write Permission is approved",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"External Storage Write Permission is disapproved",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check which request we're responding to
        if (requestCode == MY_STORAGE_ACCESS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    imgUri = data.getData();
                    String tmpPath  = getPath(this, imgUri);
                    Log.i("androidTest", "tmp Path : "+ tmpPath);

                    Log.i("androidTest","uri path: "+imgUri);
                    //String absPath = DocumentsContract.getDocumentId(imgUri);
                    //absPath = absPath.substring(4);
                    //Log.i("androidTest","abs Path: "+absPath);

                    Log.i("androidTest", "environment "+ABSOLUTE_PATH);
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    imgSelectedPicture.setImageBitmap(img);

                    selectedFile = new File(tmpPath);
                    Log.i("androidTest","파일 생성. \n 경로: "+ tmpPath);
                    if(selectedFile.isDirectory()){
                        Log.i("androidTest","directory 임");
                    }else{
                        Log.i("androidTest","directory 아님");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == CAMERA_CAPTURE){
            //찍은 사진 가져와서 붙여주기
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                try {

                    // 촬영한 이미지 가져오기
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    img = imgRotate(img);
                    // 이미지뷰에 세팅
                    imgSelectedPicture.setImageBitmap(img);
                    //임시 저장 날짜
                    String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
                    //이미지 저장시켜주기
                    File tmpFile = new File(Environment.getExternalStorageDirectory()+"/Captures",date+".jpg");

                    File saveDir = getSaveDirectory();

                    if(saveDir.isDirectory()){
                        Log.i("androidTest","saveDir 디렉토리임");
                    }else{
                        Log.i("androidTest","saveDir 디렉토리 아님");
                    }

                    if(!tmpFile.exists()){
                        try{
                            tmpFile.createNewFile();
                        }catch (IOException e) {e.printStackTrace();}

                    }else{
                        tmpFile.delete();
                    }
                    try{
                        tmpFile.createNewFile();
                    }catch (IOException e){e.printStackTrace();}

                    FileOutputStream out = new FileOutputStream(tmpFile);
                    //compress 함수를 사용해 스트림에 비트맵 저장
                    img.compress(Bitmap.CompressFormat.JPEG,100,out);
                    out.close();
                    if(tmpFile.isFile()){
                        Log.i("androidTest","tmpfile: 파일임");
                    }else{
                        Log.i("androidTest","tmpFile: 파일아님");
                    }


                    Uri capturedImgUri = Uri.fromFile(tmpFile);
                    Log.d("androidTest","capture img uri path: "+capturedImgUri.getPath());
                    imgUri = capturedImgUri;
                    selectedFile = tmpFile;
                    /*FileOutputStream fos;
                    try{
                        fos = new FileOutputStream(savePath+"tmp_"+date+".jpeg");
                        img.compress(Bitmap.CompressFormat.JPEG,100,fos);

                    }catch (Exception e){
                        Log.i("androidTest","압축 익셉션: "+e.getMessage());
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnStorage:{
                //스토리지에 접근하는 인텐트
                Uri uri = Uri.parse("content://media/external/images");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, MY_STORAGE_ACCESS);

                //기본 갤러리에 접근하는 코드
/*
                Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType
                                    (android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                            startActivityForResult(intent, MY_STORAGE_ACCESS);
                */
                break;
            }
            case R.id.btnCapture:{
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_CAPTURE);
                break;
            }
            case R.id.btnSendToServer:{
                sendServer();
                setWaitAnimation();
                break;
                //http socket 통신
            }
        }
    }

    // @캡처 이미지 회전
    private Bitmap imgRotate(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90); //회전할 각도 세팅

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        bmp.recycle();

        return resizedBitmap;
    }

    private void setWaitAnimation(){
        //back 화면 흐리게 하기
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        //70% 흐리게 하기
        lpWindow.dimAmount = 0.7f;
        //스플래시 이미지 보여주기
        initLoadingView();
        //3초 로딩 대기 후 빠져나오기
        Handler handler = new Handler();
        //4초간 대기
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                stopLoadingView();

                //결과 확인 다이얼로그 생성
                Dialog dialog = new Dialog(Diagnosis1Activity.this);
                //커스터마이징 화면 붙이기
                dialog.setContentView(R.layout.custom_dialog_layout);
                //다이얼로그 뒷 배경 선택 불가
                dialog.setCancelable(false);
                //제목 붙이기
                dialog.setTitle("Diagnosis Complete!");
                dialog.create();

                try
                {
                    //complete 버튼 핸들러
                    Button button=(Button)dialog.findViewById(R.id.btnPopUp);
                    button.setOnClickListener(
                            new Button.OnClickListener(){
                                public void onClick(View v){
                                    Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                                    intent.putExtra("result",responseMsg);
                                    intent.putExtra("imgPath",imgUri.toString());
                                    startActivity(intent);
                                }
                            }
                    );
                }catch (Exception e){
                    e.getStackTrace();
                }

                //실제 띄우는 함수, 없으면 화면에 출력x
                dialog.show();
            }
        };
        //4초간 로딩 이미지 보여주기
        handler.postDelayed(runnable,4000);
    }

    //전송 로딩 이미지 애니메이션 실행
    private void initLoadingView(){

        imgSendLoading.setVisibility(View.VISIBLE);
        txtSendLoading.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.VISIBLE);
        //애니메이션 뒤 버튼 비활성화
        btnCapture.setClickable(false);
        btnStorage.setClickable(false);
        btnSend.setClickable(false);
        //애니메이션 객체 생성
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_loading);
        //사진에 애니메이션 적용
        imgSendLoading.setAnimation(animation);


    }

    //전송 로딩 이미 숨기기
    private void stopLoadingView(){
        imgSendLoading.setVisibility(View.GONE);
        txtSendLoading.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        imgSendLoading.clearAnimation();
    }

    //socket 통신 매소드
    public void sendServer(){
        class sendData extends AsyncTask<Void, Void, String>{
            String serverUrl = "http://100.22.19.169:5000/classify";

            public sendData() {
                //empty
            }

            @Override
            protected void onPreExecute() {
                Log.i("androidTest","onPreExecute()");
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i("androidTest","onPostExecute()");
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            //실제 실행
            protected String doInBackground(Void... voids) {
                try{
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file",selectedFile.getName(),RequestBody.create(MultipartBody.FORM,selectedFile))
                            .build();

                    Request request = new Request.Builder()
                            .url(serverUrl)
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                            Log.i("androidTest",e.getMessage());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String result = response.body().string();
                            Log.i("androidTest","Server Response: "+result);
                            responseMsg = result;
                        }
                    });
                }catch (Exception e){
                    Log.i("androidTest","okhttp3 request exception: "+e.getMessage());
                }
                return null;
            }
        }
        Log.i("androidTest","send to server(Execute)");
        sendData sendData = new sendData();
        sendData.execute();
    }

    private File getSaveDirectory(){
        File dir = new File(SAVE_DIRECTORY_PATH);
        //디렉토리 만들어주기 없다면 만들기
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    //절대경로 가져오기
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

// DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) { // 주 저장소이면.
                    return Environment.getExternalStorageDirectory() + "/" + split[1]; // 내부저장소 ex) /storage/0/
                }else {
                    return "/storage/" + type + "/" + split[1]; //외부저장소 ex)/storage/1/, /storage/sdcard/
                }

// TODO handle non-primary volumes
            }
// DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                Log.i("androidTest","ID: "+id);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
// MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
// MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
// File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
