package com.example.sayfa_gecisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringBufferInputStream;
import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity {

    private int sc,hs;
    private int ses=1;
    private SQLiteDatabase database;
    private long pressedTime;
    ImageView soundon,soundoff,vibrationon,vibrationoff;
    MediaPlayer MainSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try {
            database = this.openOrCreateDatabase("Game",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Player (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR, " +
                    "score INT, " +
                    "hs INT, " +
                    "coin INT, " +
                    "money INT," +
                    "c0 INT," +
                    "c1 INT," +
                    "c2 INT," +
                    "c3 INT," +
                    "c4 INT," +
                    "c5 INT," +
                    "ses INT)");            System.out.println("DataBase Online");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            getData();
            System.out.println("GETDATA OK");
        }catch (Exception e){
            e.printStackTrace();
        }




       // soundson();
        soundon = (ImageView) findViewById(R.id.soundsicon0); //Sesi kapatma id
        soundoff = (ImageView) findViewById(R.id.soundsicon1); //Sesi açma id
        vibrationon = (ImageView) findViewById(R.id.vibrationon); //Titreşimi açma id
        vibrationoff = (ImageView) findViewById(R.id.vibrationoff); //Titreşimi kapatma id



    }


    public void DBadd(){

        try {
            database.execSQL("INSERT INTO Player (name,score,hs,coin,money,c0,c1,c2,c3,c4,c5,ses) VALUES('Test',0,0,0,0,0,0,0,0,0,0,0)");
            getData();
           // Toast.makeText(getApplicationContext(), "VERİ EKLENDİ",Toast.LENGTH_SHORT).show();
            System.out.println("DBADD");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DBupdate(int hs){
        try {
            database.execSQL("UPDATE Player SET hs ='"+hs+"'WHERE ID=1");
            getData();
            //Toast.makeText(getApplicationContext(), "VERİ GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getData(){
        Cursor cursor = database.rawQuery("SELECT * FROM Player WHERE ID=1",null);

        int ıdIndex = cursor.getColumnIndex("ID");
        int nameIndex = cursor.getColumnIndex("name");
        int scoreIndex = cursor.getColumnIndex("score");
        int hsIndex = cursor.getColumnIndex("hs");
        int coinIndex = cursor.getColumnIndex("coin");
        int moneyIndex = cursor.getColumnIndex("money");
        int sesIndex = cursor.getColumnIndex("ses");



        while (cursor.moveToNext()){
            System.out.println("ses: "+cursor.getInt(sesIndex)+"ID: "+cursor.getInt(ıdIndex)+" Name: "+cursor.getString(nameIndex)+" Score: "+cursor.getInt(scoreIndex)+ " HS: "+cursor.getInt(hsIndex)+ " Coin: "+cursor.getInt(coinIndex)+ " Money: "+cursor.getInt(moneyIndex));

            TextView score = (TextView)findViewById(R.id.Score);
            score.setText("Score : "+cursor.getInt(scoreIndex));
            sc=cursor.getInt(scoreIndex);

            TextView highscore = (TextView)findViewById(R.id.highscore);
            highscore.setText("High Score : "+cursor.getInt(hsIndex));
            hs=cursor.getInt(hsIndex);

            TextView liraScore = (TextView)findViewById(R.id.lirascore);
            liraScore.setText(""+cursor.getInt(coinIndex));
        }
        if (sc>hs){
            DBupdate(sc);
        }
        ses=cursor.getInt(sesIndex);



        cursor.close();
    }



    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            sounsoff();
            quit();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Çıkmak için tekrar geri basın.", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    public void ss(int ss){
        try {
            database.execSQL("UPDATE Player SET ses ='"+ss+"'WHERE ID=1");
            getData();
            //Toast.makeText(getApplicationContext(), "VERİ GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void soundson(){

        if(ses==1) {
            MainSound = MediaPlayer.create(MainActivity.this, R.raw.mainbackgroundsound); //Arka planda çalacak olan sesi belirledik
            MainSound.start(); //Arka plan sesini çalıştırdık
            MainSound.setLooping(true); //Arka plan sesini döngüye soktuk
            getData();
        }
    }
    public void sounsoff(){
        if(ses==0) {
            MainSound.stop();//Arka plan sesini kapattık
        }
    }

    //Oyunu başlatma sayfasına gönderir
    public void play(View view) {
        Intent GameScreen = new Intent(getApplicationContext(),GameScreen.class);
        GameScreen.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        GameScreen.putExtra("sound",ses);
        sounsoff();//Arka plan sesini kapattık
        DBadd();
        startActivity(GameScreen);
        this.finish();
    }

    //Uygulamayı kapatır
    public void quit(View view){
         quit();
    }
    public void quit() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sounsoff();//Arka plan sesini kapattık
        startActivity(setIntent);
    }


    //Oyunun sesini KAPATIR
    public void soundson(View view) {
        try {
            ss(0);
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        soundon.setVisibility(View.INVISIBLE);
        soundoff.setVisibility(View.VISIBLE);
        sounsoff();//Arka plan sesini kapattık
    }

    //Oyunun sesini AÇAR
    public void soundsoff(View view) {
        try {
            ss(1);
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        soundon.setVisibility(View.VISIBLE);
        soundoff.setVisibility(View.INVISIBLE);
        MainSound = MediaPlayer.create(MainActivity.this,R.raw.mainbackgroundsound);//Arka planda çalacak olan sesi belirledik
        MainSound.start();//Arka plan sesini çalıştırdık
    }

    //Oyunun telefon titreşimini KAPATIR
    public void vibrationon(View view) {
        vibrationon.setVisibility(View.INVISIBLE);
        vibrationoff.setVisibility(View.VISIBLE);
    }

    //Oyunun telefon titreşimini AÇAR
    public void vibrationoff(View view) {
        vibrationon.setVisibility(View.VISIBLE);
        vibrationoff.setVisibility(View.INVISIBLE);
    }

    //Oyundaki karakter seçme ekranına geçiş yapar
    public void animal(View view) {
        Intent CharacterScreen = new Intent(getApplicationContext(),CharacterScreen.class);
        CharacterScreen.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        sounsoff();//Arka plan sesini kapattık
        DBadd();
        startActivity(CharacterScreen);
        this.finish();
    }
}