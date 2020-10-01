package com.example.songofrebirth;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class KeyFragment extends Fragment implements View.OnTouchListener{
    EditText poisk;
    int color, sitc;
    StileTheme mStileTheme;
    View view;
    KeyKlass mKeyKlass;
    byte ZnachRegKey,ZnachLangyageKey=1,ZnachFSKey;
    LinearLayout KeyLayut;
    HoldTimer timer= new HoldTimer(777, 1000000);
    /*public interface SentFromKeyFragment {
        public void someEventKey(String s);
    }
    SentFromKeyFragment mSentFromKeyFragment;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSentFromKeyFragment = (SentFromKeyFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }*/
    private LinearLayout mSongRec;
    protected int masIDbut[] =  {R.id.й,R.id.ц,R.id.у,R.id.к,R.id.е,R.id.н,R.id.г,R.id.ш,R.id.щ,R.id.з,R.id.х,
            R.id.ф,R.id.ы,R.id.в,R.id.а,R.id.п,R.id.р,R.id.о,R.id.л,R.id.д,R.id.ж,R.id.э,
            R.id.я,R.id.ч,R.id.с,R.id.м,R.id.и,R.id.т,R.id.ь,R.id.б,R.id.ю,
            R.id.ә,R.id.ө,R.id.ү,R.id.җ,R.id.ң,R.id.һ,
            R.id.EN,R.id.FS,R.id.pro,R.id.del,R.id.Reg};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.for_key_fragment,container,false);
        mKeyKlass = new KeyKlass();
        KeyLayut = view.findViewById(R.id.forkeyfragment);
        KeyLayut.setBackgroundColor(color);
        mStileTheme = new StileTheme();
        ((Button)view.findViewById(R.id.Reg)).setBackgroundResource(mStileTheme.GetThemeRegDarkLite(sitc));
        ((Button)view.findViewById(R.id.del)).setBackgroundResource(mStileTheme.GetThemeDelDarkLite(sitc));
        for (int i:masIDbut) {
            Button butEvery = view.findViewById(i);
            //управление регистром текста кнопок (0 ижний)(1 верхний)
            /*if (ZnachReg==1) {
            butEvery.setText(butEvery.getText().toString().toUpperCase());}*/
            butEvery.setOnTouchListener(this);
            ///
        }
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mKeyKlass.KeyListener(poisk,event,v,view,timer,ZnachRegKey,ZnachLangyageKey,ZnachFSKey);
        ZnachRegKey= mKeyKlass.GetZnachRegKey();
        ZnachLangyageKey= mKeyKlass.GetZnachLangyageKey();
        ZnachFSKey= mKeyKlass.GetZnachFSKey();
        return false;
    }



    public void GetPoisk(EditText poisk0) {poisk=poisk0;}

    public void vivod(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
    void setColorAndN(int color0, int sit) {color = color0;sitc = sit;}

    /*void RegStart(byte ZnachReg0) {
        ZnachReg = ZnachReg0;
    }*/
}

