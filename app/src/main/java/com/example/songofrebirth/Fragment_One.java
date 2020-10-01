package com.example.songofrebirth;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Fragment_One extends Fragment {
    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
    private RecyclerView mSongRec;
    private SongAdapter mAdapter;
    private String dpoisk = "";
    int l = 0;
    boolean ra = false; //разрешение на расширенный поиск
    private DataBases basa;
    private SQLiteDatabase mdb;
    private String namTa = "Table2600";
    Class_Controller_Songs arhiveSong = Class_Controller_Songs.get(getActivity());
    List<Class_Song_One> songi;//эта переменная хранит данные песен выбранного сборника
    List<Class_Song_One> Arhivetwo = new ArrayList<>(); //та же самая переменная, но в ней хранятся выбранные песни, при введенном поисковом запросе
    //int sitc;
    //int[] sit = new int[20];
    int idI;
    //private static final String PO = "pobnov";
    //private static final int po = 0;
    //String ton, akordspo, namepo, slovapo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        basa = new DataBases(getActivity());//в первой БД хранятся данные которые я буду впоследствии обновлять, меняя версию БД
        try {
            basa.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb = basa.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        //info();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forfragmentone, container, false);
        mSongRec = (RecyclerView) view.findViewById(R.id.FragmentOne);
        mSongRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someEventListener.someEvent("hindkeyFr");
            }
        });*/
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    someEventListener.someEvent("hindkeyFr");
                }
                return false;
            }
        });
        return view;
    }

    private void updateUI() {
        arhiveSong.plusbasa(mdb, namTa);
        arhiveSong.Obnovlenie();
        songi = arhiveSong.getInfoOne();//arhiveSong.getInfoOne();
        if (mAdapter == null) {
            mAdapter = new SongAdapter(songi);
            mSongRec.setAdapter(mAdapter);
        } else {
            mAdapter.setSongs(songi);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void update2() {Arhivetwo.clear();
        for (Class_Song_One Song: songi) {
            if (Song.getName().toLowerCase().contains(dpoisk.toLowerCase())) {
                int x1 = Song.getName().toLowerCase().indexOf(dpoisk.toLowerCase());
                int x2 = x1 + dpoisk.length();
                Song.N1(x1);
                Song.N2(x2);
                Song.setnameintext("name");
                Arhivetwo.add(Song);
            }
            else if (ra&&Song.getText().replace("\n", " ").toLowerCase().replace(",","").contains(dpoisk.toLowerCase().replace(",",""))) {
                int x1 = Song.getText().replace("\n", " ").toLowerCase().replace(",","").indexOf(dpoisk.toLowerCase().replace(",",""));
                Song.setnameintext("text");
                Song.N1(x1);
                Arhivetwo.add(Song);
            }
            else if (Song.getidn().contains(dpoisk)) {
                int x1 = Song.getidn().indexOf(dpoisk);
                int x2 = x1 + dpoisk.length();
                Song.N1(x1);
                Song.N2(x2);
                Song.setnameintext("numbe");
                Arhivetwo.add(Song);
            }
        }
        if (/*songi.size()>7&&*/ra==false&&!dpoisk.equals("")) {
            Class_Song_One songpos = new Class_Song_One();
            songpos.setnameintext("pos");
            songpos.setName("Расширенный поиск");
            Arhivetwo.add(songpos);}
        if (mAdapter == null) {mAdapter = new SongAdapter(Arhivetwo);
            mSongRec.setAdapter(mAdapter);}
        else {mAdapter.setSongs(Arhivetwo);
            mAdapter.notifyDataSetChanged();}
    }




    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Class_Song_One mSongg;
        private TextView mNumber; //номер одной песни в общем списке
        private TextView mTitle; //название одной песни в общем списке
        private TextView mText;//отрывок из текста песни при использовании расширенного поиска

        public SongHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.spisocksong, parent, false));
            itemView.setOnClickListener(this);
            mNumber = (TextView) itemView.findViewById(R.id.number);
            mTitle = (TextView) itemView.findViewById(R.id.song_Title);
            mText = (TextView) itemView.findViewById(R.id.song_Text);
        }

        public void bind(Class_Song_One songg) {
            mSongg = songg;
            //для создания в конце кнопки "расширенный поиск" я использовал тот же элемент что и для песен, и чтобы при вызове процыдуры onClick() отличить ее от всех песен, в обьектной переменной хранится слово "pos"
            if (!songg.getnameintext().equals("pos")) {
                mText.setVisibility(View.GONE);
                if (mSongg.getnameintext().equals("name")) {
                    String SO = mSongg.getName();
                    int x1 = mSongg.getn1(),
                            x2 = mSongg.getn2();
                    final SpannableStringBuilder text = new SpannableStringBuilder(SO);
                    final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(245, 78, 126));
                    text.setSpan(style, x1, x2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mTitle.setText(text);
                    mNumber.setText(mSongg.getidn());
                    mTitle.setTextSize(18);
                    mNumber.setTextSize(18);
                    mNumber.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.VISIBLE);
                } else if (mSongg.getnameintext().equals("text")) {
                    mText.setVisibility(View.VISIBLE);
                    int x1 = mSongg.getn1();
                    String SO = mSongg.getText().replaceAll("\n", " ");
                    if (x1 >= 18) {
                        SO = "..." + SO.substring(x1 - 18);
                    }
                    if (SO.length() > 50) {
                        SO = SO.substring(0, 50) + "...";
                    }
                    mTitle.setText(mSongg.getName());
                    mTitle.setTextSize(18);
                    mTitle.setVisibility(View.VISIBLE);
                    mNumber.setText(mSongg.getidn());
                    mNumber.setTextSize(18);
                    mNumber.setVisibility(View.VISIBLE);
                    mText.setText(SO);
                }

else if (mSongg.getnameintext().equals("numbe")) {
                    String SO = mSongg.getidn();
                    int x1 = mSongg.getn1(),
                            x2 = mSongg.getn2();
                    final SpannableStringBuilder text = new SpannableStringBuilder(SO);
                    final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(245, 78, 126));
                    text.setSpan(style, x1, x2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mTitle.setText(mSongg.getName());
                    mNumber.setText(text);
                    mTitle.setTextSize(18);
                    mNumber.setTextSize(18);
                    mNumber.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.VISIBLE);
                }

            } else {
                mTitle.setText(mSongg.getName());
                mTitle.setTextSize(30);
                mTitle.setGravity(Gravity.CENTER);
                mNumber.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);
            }
            mSongg.N1(0);
            mSongg.N2(0);
        }

        @Override
        public void onClick(View view) {
            if (!mSongg.getnameintext().equals("pos")) {
                Intent intent = Active_Two.newIntent(getActivity(),mSongg/*sitc0*/);
                //obnovTable("t7",mSongg.getnsb()+";"+mSongg.getidn());
                 startActivity(intent);}
                else {ra=true;update2();}
        }
    }

        private class SongAdapter extends RecyclerView.Adapter<SongHolder> {
            private List<Class_Song_One> mSong;

            public SongAdapter(List<Class_Song_One> songg) {
                mSong = songg;
            }

            @NonNull
            @Override
            public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new SongHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(SongHolder holder, int position) {
                Class_Song_One infoOneSongg = mSong.get(position);
                holder.bind(infoOneSongg);
            }

            @Override
            public int getItemCount() {
                return mSong.size();
            }

            public void setSongs(List<Class_Song_One> song) {
                mSong = song;
            }
        }

    @Override
    public  void onStart() {super.onStart();
        update2(); }

        public void Dpoisk(String dpois) {
            dpoisk = dpois;
            l = dpoisk.length();
            ra = false;
            update2();
        }



        public void vivod(String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }

        public void info() {
        /*Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            sit[i] = Integer.parseInt(cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
        cursor.close();*/
        }

        String ConvertName(String name0) {
        String name="";

        return name;
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.panel_one,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sitings:
                Intent intent2 = Active_Sitings.newIntent(getActivity());
                startActivity(intent2);
                return true;
            default: return super.onOptionsItemSelected(item) ;
        }
    }
}
