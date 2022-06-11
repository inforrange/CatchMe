package com.example.sayfa_gecisi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterScreen extends AppCompatActivity {

    private SQLiteDatabase database;
    private ImageView c0,c1,c2,c3,c4,c5;
    private int cn0,cn1,cn2,cn3,cn4,cn5,coin,money,coinD,moneyD;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_screen);

        alertDialog = new AlertDialog.Builder(CharacterScreen.this).create();
        alertDialog.setTitle("KARAKTER SATIN AL");
        alertDialog.setCancelable(false);

        c0 = (ImageView)findViewById(R.id.c0);
        c1 = (ImageView)findViewById(R.id.c1);
        c2 = (ImageView)findViewById(R.id.c2);
        c3 = (ImageView)findViewById(R.id.c3);
        c4 = (ImageView)findViewById(R.id.c4);
        c5 = (ImageView)findViewById(R.id.c5);




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
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            getData();
            System.out.println("GETDATA OK");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void DBupdateC(String ch,int cn,String mc){
        try {

            database.execSQL("UPDATE Player SET '"+ch+"' =1 , '"+mc+"' = '"+cn+"' WHERE ID=1");
            getData();
           // Toast.makeText(getApplicationContext(), "VERİ GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
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
        int c0Index = cursor.getColumnIndex("c0");
        int c1Index = cursor.getColumnIndex("c1");
        int c2Index = cursor.getColumnIndex("c2");
        int c3Index = cursor.getColumnIndex("c3");
        int c4Index = cursor.getColumnIndex("c4");
        int c5Index = cursor.getColumnIndex("c5");

        while (cursor.moveToNext()){
            System.out.println(
                    "ID: "+cursor.getInt(ıdIndex)+
                    " Name: "+cursor.getString(nameIndex)+
                    " Score: "+cursor.getInt(scoreIndex)+
                    " HS: "+cursor.getInt(hsIndex)+
                    " Coin: "+cursor.getInt(coinIndex)+
                    " Money: "+cursor.getInt(moneyIndex)+
                    "c0: "+cursor.getInt(c0Index)+
                    "c1: "+cursor.getInt(c1Index)+
                    "c2: "+cursor.getInt(c2Index)+
                    "c3: "+cursor.getInt(c3Index)+
                    "c4: "+cursor.getInt(c4Index)+
                    "c5: "+cursor.getInt(c5Index));

            cn0 =cursor.getInt(c0Index);
            cn1 =cursor.getInt(c1Index);
            cn2 =cursor.getInt(c2Index);
            cn3 =cursor.getInt(c3Index);
            cn4 =cursor.getInt(c4Index);
            cn5 =cursor.getInt(c5Index);
            coin =cursor.getInt(coinIndex);
            money =cursor.getInt(moneyIndex);


            TextView viptlScore = (TextView)findViewById(R.id.viptlscore);
            viptlScore.setText(""+cursor.getInt(moneyIndex));

            TextView liraScore = (TextView)findViewById(R.id.lirascore);
            liraScore.setText(""+cursor.getInt(coinIndex));

        }
        cursor.close();
    }

    public void DBupdate(String name){
        try {
            database.execSQL("UPDATE Player SET name ='"+name+"'WHERE ID=1");
            getData();
            //Toast.makeText(getApplicationContext(), "VERİ GÜNCELLENDİ",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Karakter sayfasından çıkıp ana menüye döner
    public void geri(View view) {
        Intent MainScreen = new Intent(CharacterScreen.this,MainActivity.class);
        MainScreen.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(MainScreen);
        this.finish();
    }

    //Reklam izleyerek para kazandırmayı sağlar
    public void reklam(View view) {
    }

    public void Animal(View view) {
            switch (view.getId()){
                case R.id.c0:
                    c0.setBackgroundColor(Color.parseColor("#C2FFC2"));
                    c1.setBackgroundColor(Color.TRANSPARENT);
                    c2.setBackgroundColor(Color.TRANSPARENT);
                    c3.setBackgroundColor(Color.TRANSPARENT);
                    c4.setBackgroundColor(Color.TRANSPARENT);
                    c5.setBackgroundColor(Color.TRANSPARENT);
                    DBupdate("dog");
                    break;
                case R.id.c1:
                    if (cn1==1) {
                        c1.setBackgroundColor(Color.parseColor("#C2FFC2"));
                        c0.setBackgroundColor(Color.TRANSPARENT);
                        c2.setBackgroundColor(Color.TRANSPARENT);
                        c3.setBackgroundColor(Color.TRANSPARENT);
                        c4.setBackgroundColor(Color.TRANSPARENT);
                        c5.setBackgroundColor(Color.TRANSPARENT);
                        DBupdate("cat");
                    }else {
                        alertDialog.setMessage("Kedi 15TL Coin");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SATIN AL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(coin>=15) {
                                            coinD =coin -15;
                                            DBupdateC("c1",coinD,"coin");
                                        }else {
                                            Toast.makeText(getApplicationContext(), "YETERSİZ TL COİN", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"İPTAL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "İPTAL EDİLDİ",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    break;
                case R.id.c2:
                    if(cn2 ==1) {
                        c2.setBackgroundColor(Color.parseColor("#C2FFC2"));
                        c0.setBackgroundColor(Color.TRANSPARENT);
                        c1.setBackgroundColor(Color.TRANSPARENT);
                        c3.setBackgroundColor(Color.TRANSPARENT);
                        c4.setBackgroundColor(Color.TRANSPARENT);
                        c5.setBackgroundColor(Color.TRANSPARENT);
                        DBupdate("monkey");
                    }else{
                        alertDialog.setMessage("Goril 25TL Coin");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SATIN AL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(coin>=25) {
                                            coinD =coin -25;
                                            DBupdateC("c2",coinD,"coin");
                                        }else {
                                            Toast.makeText(getApplicationContext(), "YETERSİZ TL COİN", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"İPTAL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "İPTAL EDİLDİ",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    break;
                case R.id.c3:
                    if(cn3==1) {
                        c3.setBackgroundColor(Color.parseColor("#C2FFC2"));
                        c0.setBackgroundColor(Color.TRANSPARENT);
                        c1.setBackgroundColor(Color.TRANSPARENT);
                        c2.setBackgroundColor(Color.TRANSPARENT);
                        c4.setBackgroundColor(Color.TRANSPARENT);
                        c5.setBackgroundColor(Color.TRANSPARENT);
                        DBupdate("fox");
                    }else {
                        alertDialog.setMessage("Tilki 5TL");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SATIN AL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(money>=5) {
                                            moneyD =money -5;
                                            DBupdateC("c3",moneyD,"money");
                                        }else {
                                            Toast.makeText(getApplicationContext(), "YETERSİZ TL", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"İPTAL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "İPTAL EDİLDİ",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    break;
                case R.id.c4:
                    if(cn4==1) {
                        c4.setBackgroundColor(Color.parseColor("#C2FFC2"));
                        c0.setBackgroundColor(Color.TRANSPARENT);
                        c1.setBackgroundColor(Color.TRANSPARENT);
                        c2.setBackgroundColor(Color.TRANSPARENT);
                        c3.setBackgroundColor(Color.TRANSPARENT);
                        c5.setBackgroundColor(Color.TRANSPARENT);
                        DBupdate("wolf");
                    }else {
                         alertDialog.setMessage("Kurt 15TL");
                         alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SATIN AL",
                         new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(money>=15) {
                                moneyD =money -15;
                                DBupdateC("c4",moneyD,"money");
                            }else {
                                Toast.makeText(getApplicationContext(), "YETERSİZ TL", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"İPTAL",
                        new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "İPTAL EDİLDİ",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                        alertDialog.show();
                }
                    break;
                case R.id.c5:
                    if (cn5==1) {
                        c5.setBackgroundColor(Color.parseColor("#C2FFC2"));
                        c0.setBackgroundColor(Color.TRANSPARENT);
                        c1.setBackgroundColor(Color.TRANSPARENT);
                        c2.setBackgroundColor(Color.TRANSPARENT);
                        c3.setBackgroundColor(Color.TRANSPARENT);
                        c4.setBackgroundColor(Color.TRANSPARENT);
                        DBupdate("lion");
                    }else {
                        alertDialog.setMessage("Aslan 20TL");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SATIN AL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (money >= 20) {
                                            moneyD = money - 20;
                                            DBupdateC("c5",moneyD,"money");
                                        } else {
                                            Toast.makeText(getApplicationContext(), "YETERSİZ TL", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "İPTAL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "İPTAL EDİLDİ", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    break;
            }

    }

}