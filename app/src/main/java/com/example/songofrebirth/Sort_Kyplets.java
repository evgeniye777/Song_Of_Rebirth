package com.example.songofrebirth;

import android.util.Log;

public class Sort_Kyplets {
    private static final String TAG2 = "Main2Activity";

    String[] mas = new String[100];
    String[][] mas_SOng = new String[100][2];

    String song="",num="",Song_G;
    int dm = 0,dm_s=0,n=0;
    public void setSong(String song0) {song = song0;
    n=indexOfNorm(0,song);
    num = indexOfNum(0,song);
    }


    ///разбитие на припевы и куплеты без Форматирования
    public void Komponovka() {
        String kup="";
        int j=0;
        for (int i=n; i<song.length()-6; i++) {

            if (song.substring(i,i+6).equals("Куплет")||song.substring(i,i+6).equals("Припев")) {
              mas[j] = kup;
              kup = ""+song.charAt(i);
              j++;
            }
            else {kup+=song.charAt(i);}
        }
        kup+=song.substring(song.length()-6);
        mas[j] = kup;
        dm=j;
        j=0;
        for (int i=0; i<=dm;i++) {
            if (mas[i].length()>0&& mas[i] !=null) {
                String kp = mas[i].substring(0, 6);
                if (kp.equals("Куплет")) {
                    String num = indexOfNum(0, ""+mas[i]);
                    mas_SOng[j][0] = kp + " " + num + ":";
                    mas_SOng[j][1] = mas[i].substring(indexOfNorm(6, mas[i]));
                    j++;
                }
                else if (kp.equals("Припев")) {
                    mas_SOng[j][0] = kp + ":";
                    mas_SOng[j][1] = mas[i].substring(indexOfNorm(6, mas[i]));
                    j++;
                }
            }

        }
        dm_s=j;
    }

    ///ВЫВОД ОТФОРМАТИРОВАННЫХ ДАННЫХ
    public String vivodd() {
return ""+num+"\n\n"+Song_G;}

    ///СБОРКА ПЕСНИ
    public void Sborka(int p) {
        Song_G = "";
        String pripev="";
        for (int i=0; i<dm_s;i++) {
            Song_G += mas_SOng[i][0] + "\n" + mas_SOng[i][1];
            if (p==1) {

            if (mas_SOng[i][0].contains("Припев:")) {
                pripev = mas_SOng[i][0] + "\n" + mas_SOng[i][1];
            }
            if (!mas_SOng[i][0].contains("Припев:") && pripev.length() > 0 && i < dm_s - 1 && !mas_SOng[i + 1][0].contains("Припев:")) {
                Song_G += pripev;
            } else if (!mas_SOng[i][0].contains("Припев:") && pripev.length() > 0 && i == dm_s - 1) {
                Song_G += pripev;
            }
        }
        }
    }
    //ПРОВЕРЯЕТ ОТНОСИТСЯ ЛИ СИМВОЛ К РУССКОМУ АЛФАВИТУ ИЛИ К ЗНАКУ ПРЕПИНАНИЯ
    private static boolean NorB(char s) {
        if (s>='а'&&s<='я' ||s=='!'||s=='?'||s=='.'||s==','/*||s>='0'&&s<='9'*/) {
            return true;
        }else {return false;}
    }
    //ПРОВЕРЯЕТ ОТНОСИТСЯ ЛИ СИМВОЛ К ЦИФРАМ
    private static boolean NorNum(char s) {
        if (s>='0'&&s<='9') {
            return true;
        }else {return false;}
    }

    //НАХОДИТ НОМЕР КУПЛЕТА (ПЕРВОЕ ЧИСЛО)
    static String indexOfNum(int i0, String str) {
        String n="";
        int Num=-1;
        boolean r=false;
        for (int i=i0; i<str.length(); i++) {
            if (NorNum(str.toLowerCase().charAt(i))) {n+=str.charAt(i); r=true;}
            else if (r==true) {break;}
        }
        //if (n.length()>0) {Num = Integer.getInteger(n);}
        return n;
    }
    //НАХОДИТ ПЕРВЫЙ СИМВОЛ УДОВЛЕТВОРЯЮЩИЙ ФУНКЦИИ "NorB"
    static int indexOfNorm(int i0, String str) {
        int n=-1;
        for (int i=i0; i<str.length(); i++) {
            if (NorB(str.toLowerCase().charAt(i))) {n=i; break;}
        }
        return n;
    }
}
