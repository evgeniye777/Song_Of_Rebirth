package com.example.songofrebirth;

import java.util.UUID;

public class Class_Song_One {
    //класс для хранения параметров одной песни
    UUID mid;
    String Name;
    String Ton;
    String text;
    String akords;
    String nsb;
    String id;
    String intext;
    String izbran;
    int n1=0,n2=0;

    public Class_Song_One() {
        this(UUID.randomUUID());
    }
    public Class_Song_One(UUID id) {
        mid = id;
    }
    public UUID getId() {return mid;}

    public void setName(String name) {Name = name;}
    public String getName() {return Name;}

    public void setText(String text1) {text = text1;}
    public String getText() {return text;}

    public void setTon(String ton) {Ton = ton;}
    public String getTon() {return Ton; }

    public void setAkords(String akord) {akords = akord;}
    public String getAkords() {return akords; }

    public void setnsb(String Nsb) {nsb = Nsb;}
    public String getnsb() {return nsb; }

    public void N1(int N1) {n1=N1;}
    public int getn1() {return n1; }

    public void N2(int N2) {n2=N2;}
    public int getn2() {return n2; }

    public void izbran(String  izb) {izbran=izb;}
    public String getizbran() {return izbran; }

    public void setidn(String idn) {id = idn;}
    public String getidn() {return id; }

    public void setnameintext(String intex0) {intext = intex0;}
    public String getnameintext() {return  intext;}
}
