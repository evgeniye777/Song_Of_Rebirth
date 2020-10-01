package com.example.songofrebirth;

import android.graphics.drawable.Drawable;

public class StileTheme {
    protected int masTheme[] = {R.style.AppTheme,R.style.AppTheme2,R.style.AppTheme3,R.style.AppTheme4,R.style.AppTheme5,R.style.AppTheme6,R.style.AppTheme7,R.style.AppTheme8,R.style.AppTheme9,R.style.AppTheme10};
    protected int masColorWin[] = {R.color.white,R.color.blacckS,R.color.greenwin,R.color.Yellowwin,R.color.Pinkwin,R.color.blywin,R.color.Parplewin,R.color.blydarkwin,R.color.brownwin,R.color.orangewin};
    protected String masColorKey[] = {"#FFffffff","#FF000000","#FFCCFFDD","#FFFFFFDD","#FFffc9de","#FFC4FAF8","#FFFBD8FD","#FF361274","#FF470A05","#FFFCD2B1"};
    public int GetTheme(int n) {return masTheme[n];}
    public int GetColorWin(int n) {return masColorWin[n];}
    public String GetColorKey(int n) {return masColorKey[n];}
    public int GetThemeDelDarkLite(int i) {if (i==1 || i==7 || i==8) {
        return R.drawable.delbutlite;}
    else {return R.drawable.delbutdark;}}
    public int GetThemeRegDarkLite(int i) {if (i==1 || i==7 || i==8) {
        return R.drawable.regimagelite;}
    else {return R.drawable.regimagedark;}}
}
