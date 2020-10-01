package com.example.songofrebirth;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.Toast;

public class HoldTimer extends CountDownTimer {
    byte state=0; int id;
    int position;
    EditText poisk;
    public HoldTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }

    @Override
    public void onFinish() {
        state = 1;
        if (id == R.id.del) {
            //mSentFromKeyFragment.someEventKey("delEvery");
            poisk.setText("");
            poisk.setSelection(0);
        }
        else if (id == R.id.е) {
            //mSentFromKeyFragment.someEventKey("Vvodё");
            poisk.setText(poisk.getText().toString().substring(0,position)+"ё"+poisk.getText().toString().substring(position));
            poisk.setSelection(position+1);
        }
        else if (id == R.id.ь) {
            //mSentFromKeyFragment.someEventKey("Vvodъ");
            poisk.setText(poisk.getText().toString().substring(0,position)+"ъ"+poisk.getText().toString().substring(position));
            poisk.setSelection(position+1);
        }
    }
    public byte StateTimer() {return state;}
    public void StateNew(byte state0) {state = state0;}
    public void SentFromVvod(int id0, EditText poisk0,int position0) {id=id0;poisk= poisk0; position = position0;}
}

