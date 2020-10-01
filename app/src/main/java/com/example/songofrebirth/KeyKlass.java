package com.example.songofrebirth;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class KeyKlass {
    protected EditText poisk;
    int sitc;
    protected View view;
    protected byte ZnachRegKey, ZnachLangyageKey, ZnachFSKey;
    protected int masIDbut[] =  {R.id.й,R.id.ц,R.id.у,R.id.к,R.id.е,R.id.н,R.id.г,R.id.ш,R.id.щ,R.id.з,R.id.х,
            R.id.ф,R.id.ы,R.id.в,R.id.а,R.id.п,R.id.р,R.id.о,R.id.л,R.id.д,R.id.ж,R.id.э,
            R.id.я,R.id.ч,R.id.с,R.id.м,R.id.и,R.id.т,R.id.ь,R.id.б,R.id.ю,
            R.id.ә,R.id.ө,R.id.ү,R.id.җ,R.id.ң,R.id.һ,
            R.id.EN,R.id.FS,R.id.pro,R.id.del,R.id.Reg};
    protected char masRU[] = {'й','ц','у','к','е','н','г','ш','щ','з','х','ф','ы','в','а','п','р','о','л','д','ж','э','я','ч','с','м','и','т','ь','б','ю','ә','ө','ү','җ','ң','һ'};
    protected char masEN[] = {'1','2','3','4','5','6','7','8','9','0','q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'/*,',','.','!','?','(',')','-'*/};
    protected char masFS[] = {',','.','!','?',')','-'};
    public void KeyListener(EditText poisk0, MotionEvent event, View v,View view0, HoldTimer timer,byte ZnachRegKey0,byte ZnachLangyageKey0,byte ZnachFSKey0) {
        ////////
        poisk=poisk0;
        view=view0;
        ZnachRegKey=ZnachRegKey0;
        ZnachLangyageKey=ZnachLangyageKey0;
        ZnachFSKey=ZnachFSKey0;
        String textPo = poisk.getText().toString();
        int position = poisk.getSelectionStart();
        int selektStart = poisk.getSelectionStart(), selektEnd = poisk.getSelectionEnd();
        //final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        //v.startAnimation(animAlpha);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //if (v.getId()!=R.id.Reg) {ZnachRegKey=0;}

            if ((v.getId() == R.id.del) || (v.getId() == R.id.е) || (v.getId() == R.id.ь)) {
                timer.start();
                if ((v.getId() == R.id.е) || (v.getId() == R.id.ь)) {
                    ((Button) v).setBackgroundResource(R.drawable.butclick);
                }
                else {((LinearLayout)view.findViewById(R.id.delL)).setBackgroundResource(R.drawable.butclick);}
                timer.SentFromVvod(v.getId(), poisk, position);
                //mSentFromKeyFragment.someEventKey("del");
                if (ZnachRegKey==1) {
                    RegUpDown(ZnachRegKey);}
                DopSimvol(1);
            } else if (v.getId() == R.id.Reg) {
                ((LinearLayout) view.findViewById(R.id.RegL)).setBackgroundResource(R.drawable.butclick);
                //mSentFromKeyFragment.someEventKey("Reg");
                /*if (ZnachRegKey == 0) {
                    for (int id : masIDbut) {
                        String TextBut = ((Button) view.findViewById(id)).getText().toString();
                        ((Button) view.findViewById(id)).setText(TextBut.toUpperCase());
                        if (id ==R.id.һ) {break;}
                    }
                    ZnachRegKey = 1;
                } else {
                    for (int id : masIDbut) {
                        String TextBut = ((Button) view.findViewById(id)).getText().toString();
                        ((Button) view.findViewById(id)).setText(TextBut.toLowerCase());
                        if (id ==R.id.һ) {break;}
                    }
                    ZnachRegKey = 0;
                }*/
                RegUpDown(ZnachRegKey);
            } else if (v.getId() == R.id.EN) {
                ((Button) v).setBackgroundResource(R.drawable.butclick);
                //mSentFromKeyFragment.someEventKey("EN");
                ZnachLangyageKey = (byte) ((ZnachLangyageKey + 1) % 2);
                funcshionForEnRu(ZnachLangyageKey);
            }
            else if (v.getId() == R.id.FS) {
                if (((Button)view.findViewById(R.id.ә)).getText().equals(",")) {DopSimvol(1);}
                else {
                    DopSimvol(0);}
            } else {
                //mSentFromKeyFragment.someEventKey(((Button)v).getText().toString());
                functionForLetters(selektStart, selektEnd, position, textPo, ((Button) v).getText().toString());
                ((Button) v).setBackgroundResource(R.drawable.butclick);
                DopSimvol(1);
            }
            if (v.getId() != R.id.FS) {
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getId() == R.id.Reg) {
                ((LinearLayout) view.findViewById(R.id.RegL)).setBackgroundResource(R.drawable.mybutton);}
            else if(v.getId() == R.id.del) {
                ((LinearLayout) view.findViewById(R.id.delL)).setBackgroundResource(R.drawable.mybutton);
            }
            else {
                ((Button) v).setBackgroundResource(R.drawable.mybutton);
            }
            if ((v.getId() == R.id.del) || (v.getId() == R.id.е) || (v.getId() == R.id.ь)) {
                timer.cancel();
                byte state0 = timer.StateTimer();
                if (state0 == 0) {
                    if (v.getId() == R.id.del && position > 0) {
                        //mSentFromKeyFragment.someEventKey("del");
                        if (selektEnd - selektStart == 0) {
                            poisk.setText(textPo.substring(0, position - 1) + textPo.substring(position));
                            poisk.setSelection(position - 1);
                        } else {
                            poisk.setText(textPo.substring(0, selektStart) + textPo.substring(selektEnd));
                            poisk.setSelection(selektStart);
                        }
                    } else if (v.getId() == R.id.е) {
                        //mSentFromKeyFragment.someEventKey(((Button)v).getText().toString());
                        functionForLetters(selektStart, selektEnd, position, textPo, ((Button) v).getText().toString());
                    } else if (v.getId() == R.id.ь) {
                        //mSentFromKeyFragment.someEventKey(((Button)v).getText().toString());
                        functionForLetters(selektStart, selektEnd, position, textPo, ((Button) v).getText().toString());
                    }
                } else {//mSentFromKeyFragment.someEventKey("delEvery");
                    //теперь этот метод вызывается внутри HolTimer
                    timer.StateNew((byte) 0);
                }
            }

        }
///////
    }

    void funcshionForEnRu(int forma) {
        if (forma==0) {
            int sch=0;
            for (int i=0;i<37;i++) {
                if (masIDbut[i]!=R.id.х) {
                    ((Button)view.findViewById(masIDbut[i])).setVisibility(View.VISIBLE);
                    ((Button)view.findViewById(masIDbut[i])).setText(""+masEN[sch]);sch++;}
                else {((Button)view.findViewById(masIDbut[i])).setVisibility(View.GONE);} }
            ((Button)view.findViewById(R.id.EN)).setText("RU");
        }
        else {
            for (int i=0;i<37;i++) {
                ((Button)view.findViewById(masIDbut[i])).setVisibility(View.VISIBLE);
                ((Button)view.findViewById(masIDbut[i])).setText(""+masRU[i]);}
            ((Button)view.findViewById(R.id.EN)).setText("EN&123");
            for (int i=31;i<=36;i++) {
                ((Button)view.findViewById(masIDbut[i])).setVisibility(View.VISIBLE);}
        }
        ZnachRegKey=0;
    }
    /*void functionForFS() {
        if (ZnachFSKey==0) {
            int sch=0;
            for (int i=0;i<31;i++) {
                if (masIDbut[i]!=R.id.е && masIDbut[i]!=R.id.ж && masIDbut[i]!=R.id.э && masIDbut[i]!=R.id.ь && masIDbut[i]!=R.id.б&&i<=masFS.length) {
                    ((Button)view.findViewById(masIDbut[i])).setText(""+masFS[sch]);sch++;}
                else {((Button)view.findViewById(masIDbut[i])).setVisibility(View.GONE);} }
            ((Button)view.findViewById(R.id.FS)).setText("AБ");
            ZnachFSKey=1;
            for (int i=31;i<=36;i++) {
            ((Button)view.findViewById(masIDbut[i])).setVisibility(View.GONE);}
            ((Button)view.findViewById(R.id.EN)).setEnabled(false);
            ((Button)view.findViewById(R.id.Reg)).setEnabled(false);
        }
        else {((Button)view.findViewById(R.id.EN)).setEnabled(true);
            ((Button)view.findViewById(R.id.Reg)).setEnabled(true);
            funcshionForEnRu(ZnachLangyageKey);((Button)view.findViewById(R.id.FS)).setText("123.,");ZnachFSKey=0;}

    }*/
    void functionForLetters(int selektStart, int selektEnd, int position, String textPo, String s) {
        if (selektEnd-selektStart==0) {
            poisk.setText(textPo.substring(0,position)+s+textPo.substring(position));
            poisk.setSelection(position+1);}
        else {poisk.setText(textPo.substring(0,selektStart)+s+textPo.substring(selektEnd));
            poisk.setSelection(selektStart+1);}
        if (ZnachRegKey==1) {
            RegUpDown(ZnachRegKey);}
    }

    void RegUpDown(byte b) {
        DopSimvol(1);
        if (b==0) {for (int id:masIDbut) {
            if (id==R.id.EN) {break;}
            String TextBut = ((Button)view.findViewById(id)).getText().toString();
            ((Button)view.findViewById(id)).setText(TextBut.toUpperCase());
            ZnachRegKey=1;
        }}
        else  {
            for (int id:masIDbut) {
                if (id==R.id.EN) {break;}
                String TextBut = ((Button)view.findViewById(id)).getText().toString();
                ((Button)view.findViewById(id)).setText(TextBut.toLowerCase());
                ZnachRegKey=0;
            }
        }
    }
    void DopSimvol(int b) {

        for (int i=31; i<37; i++) {
            if (b==0) {((Button)view.findViewById(masIDbut[i])).setText(""+masFS[i-31]);}

            else {
                if (ZnachLangyageKey==1) {((Button)view.findViewById(masIDbut[i])).setText(""+masRU[i]);}
                else {((Button)view.findViewById(masIDbut[i])).setText(""+masEN[i-1]);}
            }
        }
    }
    public byte GetZnachRegKey() {return  ZnachRegKey;}
    public byte GetZnachLangyageKey() {return  ZnachLangyageKey;}
    public byte GetZnachFSKey() {return  ZnachFSKey;}
}

