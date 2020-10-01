package com.example.songofrebirth;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

public class Active_One extends AppCompatActivity implements Fragment_One.onSomeEventListener{
    //активность для работы с песнями
    private static final String TAG = "Active2";
    private static final String Key = "index";
    private EditText poisk;
    private DataBasesSit basa2;
    private SQLiteDatabase mdb2;
    int sit,sitc,SB,SBc,SI,SIc,SA,SAc,sitK,sitKc;
    StileTheme mStileTheme = new StileTheme();
    FragmentManager fm;
    Fragment fragment, fragment2;
    String sQ;
    int color, colorKey;
   // static int sitc, sitK;
    boolean ra=true,A23 = false, frkView = false;
    private static String namTa;
    Fragment_One fr = new Fragment_One();
    KeyFragment frk = new KeyFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {/*String vs=savedInstanceState.getString(Key,"");
            int index1 = vs.indexOf("/");
            namTa = vs.substring(0,index1);
            int index2 = vs.indexOf("/",index1+1);
            sitc=Integer.parseInt(vs.substring(index1+1,index2));
            sitK=Integer.parseInt(vs.substring(index2+1));
            Log.d(TAG,namTa+";"+sitc);
            ra=false;
            Intent intent = Active_One.newIntent(this,namTa,sitc,sitK);
            finish();
            startActivity(intent);*/
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
        sitc=sit;
        SBc = SB;
        SIc = SI;
        SAc = SA;
        sitKc = sitK;
        //Установка темы
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = mStileTheme.GetTheme(sitc);
        super.setTheme(theme);
        setTheme(theme);
        colorKey = Color.parseColor(mStileTheme.GetColorKey(sitc));
        ////
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractiveone);
        poisk = (EditText) findViewById(R.id.poisk);
        //////idFragment1 = (android.support.v7.widget.RecyclerView) findViewById(R.id.forfragment2);


        //poisk.setTextIsSelectable(false);
        //poisk.setInputType(InputType.TYPE_NULL);

        ////РЕАЛИЗАЦИЯ ФРАГМЕНТОВ
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.forActiv1);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.forActiv1, fragment).commit();
        }

        fragment2 = fm.findFragmentById(R.id.forActiv1);
        ///блокировка клавиатуры
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&sitK==0) {
            poisk.setShowSoftInputOnFocus(false);

            if (fragment2 == null) { ///работает, только осталось привести в нормальный вид
                fragment2 = createFragment2();
                fm.beginTransaction().add(R.id.forActiv1, fragment2).commit();
                fm.beginTransaction().hide(fragment2).commit();
            }

        }
        ///


        poisk.addTextChangedListener(


                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        sQ=""+s;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sQ=""+s;

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        fr.Dpoisk(sQ);

                    }

                });


        poisk.setOnTouchListener(new View.OnTouchListener(){

                                     @Override
                                     public boolean onTouch(View v, MotionEvent event) {
                                         /// условие отпускания а не нажатия
                                         /*if (event.getAction() == MotionEvent.ACTION_UP) {
                                         if (frkView==false) {*/
                                         if (sitK==0) {
                                             fm.beginTransaction().show(fragment2).commit();
                                             frkView = true;}
                                     /*} else {fm.beginTransaction().hide(fragment2).commit();
                                             frkView = false;}}*/
                                         return false;
                                     }
                                 }
        );



}

    @Override
    public void someEvent(String s) {
        if (s.equals("hindkeyFr")&&frkView==true) {
            fm.beginTransaction().hide(fragment2).commit();
            frkView=false;
        }
    }
    public void info() {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "Sitings", null);
        cursor.moveToFirst();
        cursor.moveToPosition(2);
        sit = cursor.getInt(1);
        cursor.moveToPosition(4);
        SB = cursor.getInt(1);
        cursor.moveToPosition(5);
        SI = cursor.getInt(1);
        cursor.moveToPosition(6);
        SA = cursor.getInt(1);
        cursor.moveToPosition(9);
        sitK = cursor.getInt(1);
        cursor.close();
    }
    protected Fragment createFragment() {
        return fr;
    }
    protected Fragment createFragment2() {
        frk.setColorAndN(colorKey,sitc);
        frk.GetPoisk(poisk);
        return frk;
    }
    public  void onStart() {super.onStart();
        info();
        if (sitc!=sit||SAc!=SA||SBc!=SB||SIc!=SI||sitKc!=sitK) {
            Intent i = new Intent( this , this.getClass() );
            finish();
            this.startActivity(i);
        }
    }

}
