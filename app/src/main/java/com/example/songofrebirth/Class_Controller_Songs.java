package com.example.songofrebirth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Class_Controller_Songs {
    private static Class_Controller_Songs B;
    private static final String TAG = "MainActivity";
    private List<Class_Song_One> Arhive;
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    private String namTa;

    public static Class_Controller_Songs get(Context context) {
        if (B == null) {
            B = new Class_Controller_Songs(context);
        }
        return B;
    }

    private Class_Controller_Songs(Context context) {

    }

    public List<Class_Song_One> getInfoOne() {
        return Arhive;
    }
    //метод для хранения всех песен выбранного сборника
    public void Obnovlenie() {
        Arhive = new ArrayList<>();
        //в таблице 5 столбцов, поэтому нужно 5 переменных для храниня id, названия, слов, тональности и аккордов
        String product = "";
        String product2 = "";
        String product0;
            Cursor cursor;
            Log.i(TAG,namTa);
            cursor = db.rawQuery("SELECT * FROM " + namTa, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                product0 = cursor.getString(0);
                product = cursor.getString(1);
                product2 = cursor.getString(2);
                cursor.moveToNext();

                Class_Song_One Song = new Class_Song_One();
                ;
                Song.setText(product2);
                if (product.length() > 32) {
                    product = product.substring(0, 32) + "...";
                }
                Song.setName(product);
                Song.N1(0);
                Song.N2(0);
                Song.setidn(product0);
                Song.setnameintext("name");
                Arhive.add(Song);

            }
            cursor.close();
        }
    // метод для записи новой песни или внесения изменений
    public void updateCrime(Class_Song_One isong) {
        ContentValues values = getContentValues(isong);
        if (!isong.getnsb().equals("t5")&&!isong.getnsb().equals("t6")&&!isong.getnsb().equals("t7")) {
            db.update(isong.getnsb(), values, "_id" + " = ?", new String[]{isong.getidn()});}
        else {db2.update(isong.getnsb(), values, "_id" + " = ?", new String[]{isong.getidn()});}
    }

    private ContentValues getContentValues(Class_Song_One isong) {
        ContentValues values = new ContentValues();
        values.put("_id", isong.getidn());
        values.put("name", isong.getName());
        values.put("slova", isong.getText());
        values.put("ton", isong.getTon());
        values.put("akords", isong.getAkords());
        return values;
    }
    //  с помощью этого метода данному классу передаются две базы данных и название сборника
    public void plusbasa(SQLiteDatabase db0/*, SQLiteDatabase db1*/, String namT) {
        db = db0;
        //db2 = db1;
        namTa = namT;
    }
    //удаление песни
    public void delCrime(Class_Song_One c) {
        if (!c.getnsb().equals("t5")&&!c.getnsb().equals("t6")&&!c.getnsb().equals("t7")) {
            db.delete(c.getnsb(), "_id = ?", new String[]{c.getidn().toString()});}
        else {db2.delete(c.getnsb(), "_id = ?", new String[]{c.getidn().toString()});}
        Obnovlenie();
    }
}
