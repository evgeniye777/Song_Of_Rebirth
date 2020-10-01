package com.example.songofrebirth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;

import static com.example.songofrebirth.Active_Two.mSong2;

public class Fragment_Two extends Fragment {
    //фрагмент для отображения содержимого одной песни
    private static final String Key = "index";
    private Class_Song_One mSong;
    private TextView Slova;
    private static final String VT = "vton";
    private static final int vt = 0;
    private static final String PO = "pobnov";
    private static final int po = 1;
    private static final String VA = "value";
    private static final int va = 2;
    //private DATABasestwo basa2;
   static private SQLiteDatabase mdb2;

    String Tonorg, Tonnew;
    String namepo,slovapo;
    Sort_Kyplets sort_kyplets;
    static Class_Song_One sInfoOneSong = new Class_Song_One();
    MenuItem Mitem2;
    MenuItem Mitem3;
    int izbran=0;
    static int color;
    boolean vievA;
    int str = 0;
    static int[] sit = new int[20];
    String masT[] = {"A", "A#", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "Am", "Am#", "Hm", "Cm", "Cm#", "Dm", "Dm#", "Em", "Fm", "Fm#", "Gm", "Gm#"};

    public static Fragment_Two newInstance(Class_Song_One song, int sit0[],SQLiteDatabase mdb20) {
        sit = sit0;
        mdb2 =mdb20;
        Bundle args = new Bundle();
        //color = color0;
        sInfoOneSong = song;
        Fragment_Two fragment = new Fragment_Two();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSong = sInfoOneSong;
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            String[] mas = new String[6];
            int i0=0,n=0;
            for (int i=0;i<vs.length();i++) {if (vs.charAt(i)=='¶') {mas[n]=vs.substring(i0,i);i0=i+1;n++;}}
            mas[5] =vs.substring(i0);
            mSong.setName(mas[0]);
            mSong.setText(mas[1]);
            mSong.setTon(mas[2]);
            mSong.setAkords(mas[3]);
            mSong.setnsb(mas[4]);
            mSong.setidn(mas[5]);

        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        /*basa2 = new DATABasestwo(getActivity());
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
        info();*/
        // 8ой параметр настроек отвечает за оценку приложения, как только поьзователь попытался оценить его, этот параметр меняется на еденицу, и тогда больше не будет всплывать уведомление с просьбой оценить
        /*if (sit[8]==0) {
        FragmentManager manager = getFragmentManager();
        ViewNewSB dialog = ViewNewSB.newInstance(color,mdb2);
        dialog.setTargetFragment(Fragment3.this, va);
        dialog.show(manager, VA);}*/



    }
    //получение данных с таблицы где хранятся параметры настроек
    /*public void info() {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            sit[i] = Integer.parseInt(cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        Cursor cursor2 = mdb2.rawQuery("SELECT * FROM " + "t6", null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            if (cursor2.getString(1).equals(mSong.getnsb()+";"+mSong.getidn())) {izbran=1;break;}
            cursor2.moveToNext();
        }
        cursor2.close();
    }*/
    //описание пунктов меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.panel3,menu);
        Mitem2 = menu.findItem(R.id.izbrannoe);
        Mitem3 = menu.findItem(R.id.noizbrannoe);
        if (izbran==0) {Mitem2.setVisible(false);}
        else {Mitem3.setVisible(false);}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orientation:
                ContentValues values = new ContentValues();
                int orN = (sit[1]+1)%3;
                values.put("sitings", orN);
                mdb2.update("Sitings", values, "_id" + " = ?", new String[]{""+2});
                sit[1]=orN;
                orientation();
                return true;
            case R.id.Pripev:
                ContentValues values2 = new ContentValues();
                int orN2 = (sit[10]+1)%2;
                values2.put("sitings", orN2);
                mdb2.update("Sitings", values2, "_id" + " = ?", new String[]{""+2});
                sit[10]=orN2;
                povtPripev(sit[10]);

                ///УСТАНОВКА ПОЗИЦИИ TextView
                Slova.post(new Runnable() {
                    public void run() {
                        Slova.scrollTo(0, Slova.getTop());
                    }
                });
                return true;
            /*case R.id.izbrannoe:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),1);
                return true;
            case R.id.noizbrannoe:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),0);
                return true;
            case R.id.export:
                FragmentManager manager2 = getFragmentManager();
                goinclass dialog2 = goinclass.newInstance(color,""+Slova.getText(),""+Akords.getText(),namepo);
                dialog2.setTargetFragment(Fragment3.this, va);
                dialog2.show(manager2, VA);
                return true;*/
            default: return super.onOptionsItemSelected(item) ;
        }
    }


    void orientation() {if (sit[1]==2) {Slova.setGravity(Gravity.RIGHT);}
        if (sit[1]==1) {Slova.setGravity(Gravity.LEFT);}
        if (sit[1]==0) {Slova.setGravity(Gravity.CENTER);}}

    void povtPripev(int p) {sort_kyplets.Sborka(p);
    Slova.setText(sort_kyplets.vivodd());}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forfragmenttwo, container, false);
        //текстовые метки слов и аккордов
        Slova = (TextView) v.findViewById(R.id.slova);
        Slova.setTextSize((float)sit[0]);
        Boltactiv();Italikactiv();Allactiv();

        sort_kyplets = new Sort_Kyplets();
        sort_kyplets.setSong(mSong.getText());
        sort_kyplets.Komponovka();
        povtPripev(sit[10]);

        orientation();
        Slova.setMovementMethod(new ScrollingMovementMethod());
        slovapo = mSong.getText();
        namepo = mSong.getName();
        //кнопка сменить тональность


        //кнопка сохранить


        return v;
    }

    @Override
    public void onActivityResult(int requesCode, int resultCode, Intent data) {





    }

    public void vivod(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
    //этот метод я больше всего продумывал. Он считывает строку с аккордами и меняет каждый аккорд в зависимости от того на сколько изменили тональность


    //этот метод используется если создавать новую песню: пользователь вводит строку с аккордами и программа считывает первый аккорд


    /*void obnovTable(String tab,String adreses,int i) {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + tab, null);
        cursor.moveToFirst();
        String product0="",product="";
        while (!cursor.isAfterLast()) {
            product0 = cursor.getString(0);
            product = cursor.getString(1);
            if (adreses.equals(product)) {
                mdb2.delete(tab, "_id = ?", new String[]{product0});
            }
            cursor.moveToNext();
        }
        Mitem2.setVisible(false);
        Mitem3.setVisible(true);
        if (i==0) {
            ContentValues values = new ContentValues();
            values.put("adreses", adreses);
            mdb2.insert(tab,null,values);
            Mitem2.setVisible(true);
            Mitem3.setVisible(false);}
        cursor.close();
    }*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,mSong.getName()+"¶"+mSong.getText()+"¶"+mSong.getTon()+"¶"+
                mSong.getAkords()+"¶"+mSong.getnsb() +"¶"+
                mSong.getidn());
    };

    //эти три следующих метода задают параметры текста (жирный курсив заглавный)
    public void Boltactiv() {
        if (sit[5]==0&&sit[4]==0) {Slova.setTypeface(null, Typeface.NORMAL);
        }
        else if (sit[5]==0&&sit[4]==1){Slova.setTypeface(null, Typeface.BOLD);}
        else if (sit[5]==1&&sit[4]==0){Slova.setTypeface(null, Typeface.ITALIC);}
        else if (sit[5]==1&&sit[4]==1){Slova.setTypeface(null, Typeface.BOLD_ITALIC);
            }
    }
    public void Italikactiv() {
        if (sit[4]==0&&sit[5]==0) {Slova.setTypeface(null, Typeface.NORMAL);}
        else if (sit[4]==0&&sit[5]==1){Slova.setTypeface(null, Typeface.ITALIC);}
        else if (sit[4]==1&&sit[5]==0){Slova.setTypeface(null, Typeface.BOLD);}
        else if (sit[4]==1&&sit[5]==1){Slova.setTypeface(null, Typeface.BOLD_ITALIC);
        }}
    public void Allactiv() {
        if (sit[6]==0) {Slova.setAllCaps(false);}
        else{Slova.setAllCaps(true);}
    }

    ///СОРТИРОВКА КУПЛЕТОВ И ПРИПЕВОВ
}
