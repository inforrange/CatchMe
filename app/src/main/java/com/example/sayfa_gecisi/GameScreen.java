package com.example.sayfa_gecisi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Attributes;

public class GameScreen extends AppCompatActivity {

    private String sound;
    private Bundle bundle;
    private SQLiteDatabase database;
    public Timer myTimer,myTimer2;
    public TimerTask doAsynchronousTask,doAsynchronousTask2;
    private ImageView f0,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,
            h0,h1,h2,
            b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,
            c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,
            x0,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11;
    public int random,randomCoin,randomBomb,randomClose,Scor,health =6,Coin,cnH,cnT,CNpop=5,CNnext,speed=1000,speedPop=3,timerStop=0;
    MediaPlayer MainSound,CHonSound,BombSound,CoinSound,CloseSound;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);


        f0 = (ImageView)findViewById(R.id.f0);
        f1 = (ImageView)findViewById(R.id.f1);
        f2 = (ImageView)findViewById(R.id.f2);
        f3 = (ImageView)findViewById(R.id.f3);
        f4 = (ImageView)findViewById(R.id.f4);
        f5 = (ImageView)findViewById(R.id.f5);
        f6 = (ImageView)findViewById(R.id.f6);
        f7 = (ImageView)findViewById(R.id.f7);
        f8 = (ImageView)findViewById(R.id.f8);
        f9 = (ImageView)findViewById(R.id.f9);
        f10 = (ImageView)findViewById(R.id.f10);
        f11 = (ImageView)findViewById(R.id.f11);

        h0 = (ImageView)findViewById(R.id.heart0);
        h1 = (ImageView)findViewById(R.id.heart1);
        h2 = (ImageView)findViewById(R.id.heart2);

        b0 = (ImageView)findViewById(R.id.b0);
        b1 = (ImageView)findViewById(R.id.b1);
        b2 = (ImageView)findViewById(R.id.b2);
        b3 = (ImageView)findViewById(R.id.b3);
        b4 = (ImageView)findViewById(R.id.b4);
        b5 = (ImageView)findViewById(R.id.b5);
        b6 = (ImageView)findViewById(R.id.b6);
        b7 = (ImageView)findViewById(R.id.b7);
        b8 = (ImageView)findViewById(R.id.b8);
        b9 = (ImageView)findViewById(R.id.b9);
        b10 = (ImageView)findViewById(R.id.b10);
        b11 = (ImageView)findViewById(R.id.b11);

        c0 = (ImageView)findViewById(R.id.c0);
        c1 = (ImageView)findViewById(R.id.c1);
        c2 = (ImageView)findViewById(R.id.c2);
        c3 = (ImageView)findViewById(R.id.c3);
        c4 = (ImageView)findViewById(R.id.c4);
        c5 = (ImageView)findViewById(R.id.c5);
        c6 = (ImageView)findViewById(R.id.c6);
        c7 = (ImageView)findViewById(R.id.c7);
        c8 = (ImageView)findViewById(R.id.c8);
        c9 = (ImageView)findViewById(R.id.c9);
        c10 = (ImageView)findViewById(R.id.c10);
        c11 = (ImageView)findViewById(R.id.c11);

        x0 = (ImageView)findViewById(R.id.x0);
        x1 = (ImageView)findViewById(R.id.x1);
        x2 = (ImageView)findViewById(R.id.x2);
        x3 = (ImageView)findViewById(R.id.x3);
        x4 = (ImageView)findViewById(R.id.x4);
        x5 = (ImageView)findViewById(R.id.x5);
        x6 = (ImageView)findViewById(R.id.x6);
        x7 = (ImageView)findViewById(R.id.x7);
        x8 = (ImageView)findViewById(R.id.x8);
        x9 = (ImageView)findViewById(R.id.x9);
        x10 = (ImageView)findViewById(R.id.x10);
        x11 = (ImageView)findViewById(R.id.x11);

        CHonSound = MediaPlayer.create(GameScreen.this,R.raw.animalbuttonvoice);
        BombSound = MediaPlayer.create(GameScreen.this,R.raw.bombsounds);
        CoinSound = MediaPlayer.create(GameScreen.this,R.raw.coinwin);
        CloseSound = MediaPlayer.create(GameScreen.this,R.raw.closesounds);

        MainSound = MediaPlayer.create(GameScreen.this,R.raw.gamebackgroundsound); //Arka planda çalacak olan sesi belirledik
        bundle = getIntent().getExtras();
       // if(bundle != null){
            sound = bundle.getString("sound");


            if(sound =="1"){
                MainSound.stop(); //Arka plan sesini çalıştırdık
                Toast.makeText(getBaseContext(),sound, Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(),"KAPALI", Toast.LENGTH_SHORT).show();

            }else{
                MainSound.start(); //Arka plan sesini çalıştırdık
                Toast.makeText(getBaseContext(),sound, Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(),"AÇIK", Toast.LENGTH_SHORT).show();
            }
       // }

        System.out.println("SOUND: "+sound);







        timer();








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
                    "C2 INT," +
                    "C3 INT," +
                    "C4 INT," +
                    "C5 INT)");
            System.out.println("DataBase Online");
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void DBadd(){
        try {
            database.execSQL("INSERT INTO Player (name,score,hs,coin,money) VALUES('Test',0,0,0,0)");
            getData();
           // Toast.makeText(getApplicationContext(), "VERİ EKLENDİ",Toast.LENGTH_SHORT).show();
            System.out.println("DBADD");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DBupdate(){
        try {
            int scr = Scor-1;
            database.execSQL("UPDATE Player SET score ='"+Scor+"', coin = '"+cnT+"' WHERE ID=1");
            getData();
          //  Toast.makeText(getApplicationContext(), "VERİ GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void timer(){
        timerStop=0;
        final Handler handler = new Handler();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        CHon();
                        Bomb();
                        Close();
                        health();

                        if(Scor >=speedPop){
                            speed-=5;
                            speedPop +=2;
                            myTimer.cancel();
                           timerUp();
                        }
                        System.out.println("SPEED: "+speed);
                        System.out.println("SPEEDPOP: "+speedPop);
                        System.out.println("TİMER");
                    }
                });
            }
        };
        myTimer = new Timer();
        myTimer.schedule(doAsynchronousTask,0,speed);

    }

    public void timerUp(){
        timerStop=1;
        final Handler handler = new Handler();
        doAsynchronousTask2 = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        CHon();
                        Bomb();
                        Close();
                        health();

                        if(Scor >=speedPop){
                            speed-=5;
                            speedPop +=2;
                            myTimer2.cancel();
                            timer();
                        }
                        System.out.println("SPEED: "+speed);
                        System.out.println("SPEEDPOP: "+speedPop);
                        System.out.println("TİMERUP");
                    }
                });
            }
        };
        myTimer2 = new Timer();
        myTimer2.schedule(doAsynchronousTask2,0,speed);

    }



    private void getData(){
        Cursor cursor = database.rawQuery("SELECT * FROM Player WHERE ID=1",null);

        int ıdIndex = cursor.getColumnIndex("ID");
        int nameIndex = cursor.getColumnIndex("name");
        int scoreIndex = cursor.getColumnIndex("score");
        int hsIndex = cursor.getColumnIndex("hs");
        int coinIndex = cursor.getColumnIndex("coin");
        int moneyIndex = cursor.getColumnIndex("money");


        while (cursor.moveToNext()){
            System.out.println("ID: "+cursor.getInt(ıdIndex)+" Name: "+cursor.getString(nameIndex)+" Score: "+cursor.getInt(scoreIndex)+ " HS: "+cursor.getInt(hsIndex)+ " Coin: "+cursor.getInt(coinIndex)+ " Money: "+cursor.getInt(moneyIndex));
            cnH=cursor.getInt(coinIndex);

            switch (cursor.getString(nameIndex)){
                case "cat":
                    cat();
                    break;
                case "monkey":
                    monkey();
                    break;
                case "fox":
                    fox();
                    break;
                case "wolf":
                    wolf();
                    break;
                case "lion":
                    lion();
                    break;


            }

        }
        cnT=Coin+cnH;


        cursor.close();
    }



    public void home(View view) {
       home();
    }
    public void home(){
        if(timerStop==0){
            myTimer.cancel();
        }else {
            myTimer2.cancel();
        }
        getData();
        Intent MainScreen = new Intent(GameScreen.this,MainActivity.class);
        MainScreen.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        DBupdate();
        MainSound.stop();
        startActivity(MainScreen);
        this.finish();
    }



    public void cat(){
        f0.setImageResource(R.drawable.cat);
        f1.setImageResource(R.drawable.cat);
        f2.setImageResource(R.drawable.cat);
        f3.setImageResource(R.drawable.cat);
        f4.setImageResource(R.drawable.cat);
        f5.setImageResource(R.drawable.cat);
        f6.setImageResource(R.drawable.cat);
        f7.setImageResource(R.drawable.cat);
        f8.setImageResource(R.drawable.cat);
        f9.setImageResource(R.drawable.cat);
        f10.setImageResource(R.drawable.cat);
        f11.setImageResource(R.drawable.cat);
    }
    public void monkey(){
        f0.setImageResource(R.drawable.monkey);
        f1.setImageResource(R.drawable.monkey);
        f2.setImageResource(R.drawable.monkey);
        f3.setImageResource(R.drawable.monkey);
        f4.setImageResource(R.drawable.monkey);
        f5.setImageResource(R.drawable.monkey);
        f6.setImageResource(R.drawable.monkey);
        f7.setImageResource(R.drawable.monkey);
        f8.setImageResource(R.drawable.monkey);
        f9.setImageResource(R.drawable.monkey);
        f10.setImageResource(R.drawable.monkey);
        f11.setImageResource(R.drawable.monkey);
    }
    public void fox(){
        f0.setImageResource(R.drawable.fox);
        f1.setImageResource(R.drawable.fox);
        f2.setImageResource(R.drawable.fox);
        f3.setImageResource(R.drawable.fox);
        f4.setImageResource(R.drawable.fox);
        f5.setImageResource(R.drawable.fox);
        f6.setImageResource(R.drawable.fox);
        f7.setImageResource(R.drawable.fox);
        f8.setImageResource(R.drawable.fox);
        f9.setImageResource(R.drawable.fox);
        f10.setImageResource(R.drawable.fox);
        f11.setImageResource(R.drawable.fox);
    }
    public void wolf(){
        f0.setImageResource(R.drawable.wolf);
        f1.setImageResource(R.drawable.wolf);
        f2.setImageResource(R.drawable.wolf);
        f3.setImageResource(R.drawable.wolf);
        f4.setImageResource(R.drawable.wolf);
        f5.setImageResource(R.drawable.wolf);
        f6.setImageResource(R.drawable.wolf);
        f7.setImageResource(R.drawable.wolf);
        f8.setImageResource(R.drawable.wolf);
        f9.setImageResource(R.drawable.wolf);
        f10.setImageResource(R.drawable.wolf);
        f11.setImageResource(R.drawable.wolf);
    }
    public void lion(){
        f0.setImageResource(R.drawable.lion);
        f1.setImageResource(R.drawable.lion);
        f2.setImageResource(R.drawable.lion);
        f3.setImageResource(R.drawable.lion);
        f4.setImageResource(R.drawable.lion);
        f5.setImageResource(R.drawable.lion);
        f6.setImageResource(R.drawable.lion);
        f7.setImageResource(R.drawable.lion);
        f8.setImageResource(R.drawable.lion);
        f9.setImageResource(R.drawable.lion);
        f10.setImageResource(R.drawable.lion);
        f11.setImageResource(R.drawable.lion);
    }



    public void health(){
        if(health<=0){
            health = 0;
        }
        switch (health){
            case 6:
                h0.setImageResource(R.drawable.like);
                h1.setImageResource(R.drawable.like);
                h2.setImageResource(R.drawable.like);
                break;
            case 5:
                h0.setImageResource(R.drawable.like);
                h1.setImageResource(R.drawable.like);
                h2.setImageResource(R.drawable.like2);
                break;
            case 4:
                h0.setImageResource(R.drawable.like);
                h1.setImageResource(R.drawable.like);
                h2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                h0.setImageResource(R.drawable.like);
                h1.setImageResource(R.drawable.like2);
                h2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                h0.setImageResource(R.drawable.like);
                h1.setVisibility(View.INVISIBLE);
                h2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                h0.setImageResource(R.drawable.like2);
                h1.setVisibility(View.INVISIBLE);
                h2.setVisibility(View.INVISIBLE);
                break;
            case 0:
                h0.setVisibility(View.INVISIBLE);
                h1.setVisibility(View.INVISIBLE);
                h2.setVisibility(View.INVISIBLE);
                break;

        }

    }

    public void CHon(View view){
        CHonSound.start();
        Scor += 1;
        CNnext=Scor;
        TextView textView = (TextView)findViewById(R.id.scoreText);
        textView.setText("Score : "+Scor);
        System.out.println("CNpop: "+CNpop);
        if(CNnext >= CNpop){
            Coin();
        }
        System.out.println("CNnext: "+CNnext);


    }
    public void CHon() {

        random = new Random().nextInt(12);

        System.out.println("Karakter: "+random);


        if(random == randomCoin || random == randomBomb || random == randomClose ){
            randomCoin +=1;
        }else {
            switch (random) {
                case 0:
                    f0.setVisibility(View.VISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.VISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.VISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.VISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.VISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.VISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 6:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.VISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 7:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.VISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 8:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.VISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 9:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.VISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 10:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.VISIBLE);
                    f11.setVisibility(View.INVISIBLE);
                    break;
                case 11:
                    f0.setVisibility(View.INVISIBLE);
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.INVISIBLE);
                    f4.setVisibility(View.INVISIBLE);
                    f5.setVisibility(View.INVISIBLE);
                    f6.setVisibility(View.INVISIBLE);
                    f7.setVisibility(View.INVISIBLE);
                    f8.setVisibility(View.INVISIBLE);
                    f9.setVisibility(View.INVISIBLE);
                    f10.setVisibility(View.INVISIBLE);
                    f11.setVisibility(View.VISIBLE);
                    break;

            }
        }



    }

    public void Bomb(View view){
        BombSound.start();
        if(health==1){
            health =0;
        }else {
            health -=2;
        }
        health();
        System.out.println("CAN: "+health);
        if(health==0){
            GameOver();
        }



    }
    public void Bomb() {
        randomBomb = new Random().nextInt(12);
        System.out.println("Bomba: "+randomBomb);

        if(randomBomb == randomCoin || randomBomb == random || randomBomb == randomClose){
            randomBomb +=1;
        }else {
            switch (randomBomb) {
                case 0:
                    b0.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 6:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.VISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 7:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.VISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 8:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 9:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.VISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 10:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.VISIBLE);
                    b11.setVisibility(View.INVISIBLE);
                    break;
                case 11:
                    b0.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    b9.setVisibility(View.INVISIBLE);
                    b10.setVisibility(View.INVISIBLE);
                    b11.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    public void Coin(View view) {
        CoinSound.start();
        Coin+=1;
        TextView liraScore = (TextView)findViewById(R.id.lirascore1);
        liraScore.setText(""+Coin);
        Coin();

    }
    public void Coin(){

            randomCoin = new Random().nextInt(12);
            System.out.println("Coin: "+randomCoin);
/*
            if(randomCoin == random || randomCoin == randomBomb || randomCoin == randomClose || randomCoin == BChealth){
                randomCoin +=1;
            }else {*/
                if (CNnext >= CNpop) {
                    switch (randomCoin) {
                        case 0:
                            c0.setVisibility(View.VISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.VISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 5:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.VISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 6:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.VISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 7:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.VISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 8:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.VISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 9:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.VISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 10:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.VISIBLE);
                            c11.setVisibility(View.INVISIBLE);
                            break;
                        case 11:
                            c0.setVisibility(View.INVISIBLE);
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                            c5.setVisibility(View.INVISIBLE);
                            c6.setVisibility(View.INVISIBLE);
                            c7.setVisibility(View.INVISIBLE);
                            c8.setVisibility(View.INVISIBLE);
                            c9.setVisibility(View.INVISIBLE);
                            c10.setVisibility(View.INVISIBLE);
                            c11.setVisibility(View.VISIBLE);
                            break;

                    }
                    CNpop +=5;
                }else {
                    c0.setVisibility(View.INVISIBLE);
                    c1.setVisibility(View.INVISIBLE);
                    c2.setVisibility(View.INVISIBLE);
                    c3.setVisibility(View.INVISIBLE);
                    c4.setVisibility(View.INVISIBLE);
                    c5.setVisibility(View.INVISIBLE);
                    c6.setVisibility(View.INVISIBLE);
                    c7.setVisibility(View.INVISIBLE);
                    c8.setVisibility(View.INVISIBLE);
                    c9.setVisibility(View.INVISIBLE);
                    c10.setVisibility(View.INVISIBLE);
                    c11.setVisibility(View.INVISIBLE);

                }

            //}


    }

    public void Close(View view) {
        CloseSound.start();
        health -=1;
        health();
        System.out.println("CAN: "+health);
        if(health==0){
            GameOver();
        }
    }
    public void Close(){
        randomClose = new Random().nextInt(12);
        System.out.println("Close: "+randomClose);

        if(randomClose == randomCoin || randomClose == random || randomClose == randomBomb ){
            randomClose +=1;
        }else {
            switch (randomClose) {
                case 0:
                    x0.setVisibility(View.VISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.VISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.VISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.VISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.VISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.VISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 6:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.VISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 7:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.VISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 8:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.VISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 9:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.VISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 10:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.VISIBLE);
                    x11.setVisibility(View.INVISIBLE);
                    break;
                case 11:
                    x0.setVisibility(View.INVISIBLE);
                    x1.setVisibility(View.INVISIBLE);
                    x2.setVisibility(View.INVISIBLE);
                    x3.setVisibility(View.INVISIBLE);
                    x4.setVisibility(View.INVISIBLE);
                    x5.setVisibility(View.INVISIBLE);
                    x6.setVisibility(View.INVISIBLE);
                    x7.setVisibility(View.INVISIBLE);
                    x8.setVisibility(View.INVISIBLE);
                    x9.setVisibility(View.INVISIBLE);
                    x10.setVisibility(View.INVISIBLE);
                    x11.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    public void GameOver(){
        if(timerStop==0){
            myTimer.cancel();
        }else {
            myTimer2.cancel();
        }
        System.out.println("GAME OVER");
        alertDialog = new AlertDialog.Builder(GameScreen.this).create();
        alertDialog.setTitle("GAME OVER");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Oyuna Devam Et -5TL Coin");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Devam et",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(Coin>=5){
                            health = 6;
                            Coin -=5;
                            dialog.dismiss();
                            TextView liraScore = (TextView)findViewById(R.id.lirascore1);
                            liraScore.setText(""+Coin);

                            h0.setVisibility(View.VISIBLE);
                            h1.setVisibility(View.VISIBLE);
                            h2.setVisibility(View.VISIBLE);
                            h0.setImageResource(R.drawable.like);
                            h1.setImageResource(R.drawable.like);
                            h2.setImageResource(R.drawable.like);
                            CHon();
                            Bomb();
                            Close();
                            speed = speed+150;
                            System.out.println("DEVAM ET");
                            timer();
                        }else {
                            Toast.makeText(getBaseContext(), "Yetersiz TL Coin.", Toast.LENGTH_SHORT).show();
                            home();
                            dialog.dismiss();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"ÇIKIŞ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        home();
                        dialog.dismiss();
                    }
                });


        x0.setVisibility(View.INVISIBLE);
        x1.setVisibility(View.INVISIBLE);
        x2.setVisibility(View.INVISIBLE);
        x3.setVisibility(View.INVISIBLE);
        x4.setVisibility(View.INVISIBLE);
        x5.setVisibility(View.INVISIBLE);
        x6.setVisibility(View.INVISIBLE);
        x7.setVisibility(View.INVISIBLE);
        x8.setVisibility(View.INVISIBLE);
        x9.setVisibility(View.INVISIBLE);
        x10.setVisibility(View.INVISIBLE);
        x11.setVisibility(View.INVISIBLE);
        c0.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);
        c4.setVisibility(View.INVISIBLE);
        c5.setVisibility(View.INVISIBLE);
        c6.setVisibility(View.INVISIBLE);
        c7.setVisibility(View.INVISIBLE);
        c8.setVisibility(View.INVISIBLE);
        c9.setVisibility(View.INVISIBLE);
        c10.setVisibility(View.INVISIBLE);
        c11.setVisibility(View.INVISIBLE);
        b0.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        b5.setVisibility(View.INVISIBLE);
        b6.setVisibility(View.INVISIBLE);
        b7.setVisibility(View.INVISIBLE);
        b8.setVisibility(View.INVISIBLE);
        b9.setVisibility(View.INVISIBLE);
        b10.setVisibility(View.INVISIBLE);
        b11.setVisibility(View.INVISIBLE);
        f0.setVisibility(View.INVISIBLE);
        f1.setVisibility(View.INVISIBLE);
        f2.setVisibility(View.INVISIBLE);
        f3.setVisibility(View.INVISIBLE);
        f4.setVisibility(View.INVISIBLE);
        f5.setVisibility(View.INVISIBLE);
        f6.setVisibility(View.INVISIBLE);
        f7.setVisibility(View.INVISIBLE);
        f8.setVisibility(View.INVISIBLE);
        f9.setVisibility(View.INVISIBLE);
        f10.setVisibility(View.INVISIBLE);
        f11.setVisibility(View.INVISIBLE);



        alertDialog.show();


    }
}