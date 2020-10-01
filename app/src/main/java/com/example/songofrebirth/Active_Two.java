package com.example.songofrebirth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

public class Active_Two extends AppCompatActivity {
    //активность для работы с одной песней
    private static final String Key = "index";
    static Class_Song_One mSong2 = new Class_Song_One();
    private static int sitc;
    private static String nam;
    private DataBasesSit basa2;
    private SQLiteDatabase mdb2;
    int[] sit = new int[20];
    StileTheme mStileTheme = new StileTheme();
    private int color; // Диалоговые окна не окрашиваются той темой которая выбрана в настройках и поэтому приходится этим окнам передавать значение этого цвета, чтобы вручную подстроить его под данную тему
    public  static Intent newIntent(Context packageContext, Class_Song_One song){
        mSong2 = song;
        nam = song.getName();
        //sitc = sit;
        Intent intent = new Intent(packageContext,Active_Two.class);
        return intent;
    };

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        //в случае если приложение было свернуто и при последующем его открытии программа не выдала ошибку, необходимо сохранить параметры слов аккордов и другого
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            String[] mas = new String[9];
            int i0=0,n=0;
            for (int i=0;i<vs.length();i++) {if (vs.charAt(i)=='¶') {mas[n]=vs.substring(i0,i);i0=i+1;n++;}}
            mas[8] =vs.substring(i0);
            mSong2.setName(mas[0]);
            mSong2.setText(mas[1]);
            mSong2.setTon(mas[2]);
            mSong2.setAkords(mas[3]);
            mSong2.izbran(mas[4]);
            mSong2.setnsb(mas[5]);
            mSong2.setidn(mas[6]);
            nam = mas[7];
            sitc = Integer.parseInt(mas[8]);

        }

        basa2 = new DataBasesSit(this);
        try {
            basa2.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb2 = basa2.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        info();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //передача параметров стиля

        ////
        int theme = mStileTheme.GetTheme(sitc);
        color = mStileTheme.GetColorWin(sitc);
        setTheme(theme);
        setTitle(nam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractivetwo);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.forActiveTwo);
        if (fragment == null) {
            fragment = Fragment_Two.newInstance(mSong2,sit,mdb2);
            fm.beginTransaction().add(R.id.forActiveTwo, fragment).commit();
        }
    }


    public void info() {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "Sitings", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            sit[i] = Integer.parseInt(cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
        sitc = sit[2];
        cursor.close();

        /*Cursor cursor2 = mdb2.rawQuery("SELECT * FROM " + "t6", null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            if (cursor2.getString(1).equals(mSong.getnsb()+";"+mSong.getidn())) {izbran=1;break;}
            cursor2.moveToNext();
        }
        cursor2.close();*/
    }
    //этот метод вызывается при сворачивании приложения, как раз здесь происходит сохранение параметров открытого контента, а в onCreat() возвращение этих данных
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,mSong2.getName()+"¶"+mSong2.getText()+"¶"+mSong2.getTon()+"¶"+
                mSong2.getAkords()+"¶"+mSong2.getizbran()+"¶"+mSong2.getnsb() +"¶"+
                mSong2.getidn()+"¶"+nam+"¶"+sitc);
    };
}
