package com.example.songofrebirth;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Active_Sitings extends AppCompatActivity {
    SeekBar textSize;
    TextView text;
    Switch  swi2;
   private DataBasesSit basa2;
    private SQLiteDatabase mdb2;
    float r0=20;
    int[] sit = new int[20];
    int i=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Настройки");
        String nameT;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);


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
        if (sit[2]==0) {theme = sp.getInt("THEME", R.style.AppTheme);}
        else if (sit[2]==1) { theme = sp.getInt("THEME", R.style.AppTheme2);}
        else if (sit[2]==2) { theme = sp.getInt("THEME", R.style.AppTheme3);}
        else if (sit[2]==3) { theme = sp.getInt("THEME", R.style.AppTheme4);}
        else if (sit[2]==4) { theme = sp.getInt("THEME", R.style.AppTheme5);}
        else if (sit[2]==5) { theme = sp.getInt("THEME", R.style.AppTheme6);}
        else if (sit[2]==6) { theme = sp.getInt("THEME", R.style.AppTheme7);}
        else if (sit[2]==7) { theme = sp.getInt("THEME", R.style.AppTheme8);}
        else if (sit[2]==8) { theme = sp.getInt("THEME", R.style.AppTheme9);}
        else { theme = sp.getInt("THEME", R.style.AppTheme10);}
        super.setTheme(theme);

        setContentView(R.layout.for_active_sitings);
        textSize = (SeekBar) findViewById(R.id.seekBar2);
        text = (TextView) findViewById(R.id.text);
        Boltactiv(); Italikactiv();Allactiv();
        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                i = progressValue;
                float r;
                if (i>50) {r = r0+(i-50)/4;}
                else {r = r0+(i-50)/5;}
                text.setTextSize(r);
                ContentValues values = new ContentValues();
                values.put("sitings", (int)r);
                mdb2.update("Sitings", values, "_id" + " = ?", new String[]{""+1});
            }

            // никак не реагируем на начало движения индикатора прогресса
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            // когда пользователь отпустил индикатор изменения прогресса
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int i=0;
        if (sit[0]>20) {i=(sit[0]-20)*4+50;}
        else {i = (sit[0]-20)*5+50;}
        textSize.setProgress(i);
        swi2 = (Switch) findViewById(R.id.switch2);
        if (sit[9]==0) {
            swi2.setChecked(true);}
        else {
            swi2.setChecked(false);}

        ///Обработчик чекБокса "Отображать аккорды"


        ///Обработчик чекБокса "Встроенная клаватура
        swi2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                ContentValues values2 = new ContentValues();
                if (isChecked) {
                    values2.put("sitings", 0);
                }
                else {values2.put("sitings", 1);}
                mdb2.update("Sitings", values2, "_id" + " = ?", new String[]{""+10});
            }
        });
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
        cursor.close();
    }


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Active_Sitings.class);
        return intent;
    }

    public void vivod(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    public void beginWhite(View view) {butresult(0);}
    public void beginBlack(View view) {butresult(1);}
    public void beginGreen(View view) {butresult(2);}
    public void beginYellow(View view) {butresult(3);}
    public void beginPink(View view) {butresult(4);}
    public void beginbly(View view) {butresult(5);}
    public void beginParple(View view) {butresult(6);}
    public void beginblydark(View view) {butresult(7);}
    public void beginbrown(View view) {butresult(8);}
    public void beginorange(View view) {butresult(9);}
    public void beginBolt(View view) { click(4);Boltactiv();}
    public void beginItalik(View view) {click(5);Italikactiv();}
    public void beginAll(View view) {click(6);Allactiv();}
    public void butresult(int i) {
        ContentValues values = new ContentValues();
        values.put("sitings", i);
        mdb2.update("Sitings", values, "_id" + " = ?", new String[]{""+3});
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void Boltactiv() {
        if (sit[5]==0&&sit[4]==0) {text.setTypeface(null, Typeface.NORMAL);}
        else if (sit[5]==0&&sit[4]==1){text.setTypeface(null, Typeface.BOLD);}
        else if (sit[5]==1&&sit[4]==0){text.setTypeface(null, Typeface.ITALIC);}
        else if (sit[5]==1&&sit[4]==1){text.setTypeface(null, Typeface.BOLD_ITALIC);}
    }
    public void Italikactiv() {
        if (sit[4]==0&&sit[5]==0) {text.setTypeface(null, Typeface.NORMAL);}
        else if (sit[4]==0&&sit[5]==1){text.setTypeface(null, Typeface.ITALIC);}
        else if (sit[4]==1&&sit[5]==0){text.setTypeface(null, Typeface.BOLD);}
        else if (sit[4]==1&&sit[5]==1){text.setTypeface(null, Typeface.BOLD_ITALIC);
        }}
    public void Allactiv() {
        if (sit[6]==0) {text.setAllCaps(false);}
        else{text.setAllCaps(true);}
    }
    public void click(int num) {
        ContentValues values = new ContentValues();
        sit[num] = (sit[num]+1)%2;
        values.put("sitings", sit[num]);
        mdb2.update("Sitings", values, "_id" + " = ?", new String[]{""+(num+1)});
    }

}
