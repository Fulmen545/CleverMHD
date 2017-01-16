package com.example.risojanitor.clevermhd2;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;

import com.example.risojanitor.clevermhd2.db.DatabaseHelper;
import com.example.risojanitor.clevermhd2.db.DatabaseSelects;
import com.example.risojanitor.clevermhd2.tabs.CasyPraca;
import com.example.risojanitor.clevermhd2.tabs.ZoznamZast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CasyPreZast extends TabActivity {
  private static final String ZASTAVKY = "Zastávky";
  private static final String WORK = "Pracovný deň";
  private static final String HOLIDAY = "Prázdniny";
  private static final String WEEKEND = "Víkend";

  private String linka, smer, odkial;
  private List<String> typy = new ArrayList<String>();
  private Cursor typDna;
  private DatabaseHelper databaseHelper;
  private DatabaseSelects databaseSelects;

  public void init() {
    // Here should go all initializations
    try {
      databaseHelper = new DatabaseHelper(getApplication().getApplicationContext());
    } catch (IOException e) {
      throw new RuntimeException("Unable to initialize database helper!", e);
    }
    databaseSelects = new DatabaseSelects(databaseHelper);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_casy_pre_zast);
    init();
    Intent mIntent = getIntent();
    linka=mIntent.getStringExtra("linka");
    smer = mIntent.getStringExtra("smer");
    odkial=mIntent.getStringExtra("odkial");
    typDna= databaseSelects.getCountDen(linka);
    typy=getTypDna(typDna);

    TabHost tabHost = getTabHost();

    TabHost.TabSpec zast = tabHost.newTabSpec(ZASTAVKY);
    zast.setIndicator(ZASTAVKY);
    Intent zastIntent = new Intent(this, ZoznamZast.class);
    zastIntent.putExtra("linka",linka);
    zastIntent.putExtra("smer",smer);
    zastIntent.putExtra("odkial", odkial);

    zast.setContent(zastIntent);
    tabHost.addTab(zast);

    TabHost.TabSpec praca = tabHost.newTabSpec(WORK);
    praca.setIndicator(WORK);
    Intent pracaIntent = new Intent(this, CasyPraca.class);
    pracaIntent.putExtra("linka",linka);
    pracaIntent.putExtra("smer",smer);
    pracaIntent.putExtra("zastavka", odkial);
    pracaIntent.putExtra("den", "1");

    praca.setContent(pracaIntent);
    tabHost.addTab(praca);

    if (typy.size()==2){
      TabHost.TabSpec vikend = tabHost.newTabSpec(WEEKEND);
      vikend.setIndicator(WEEKEND);
      Intent vikendIntent = new Intent(this, CasyPraca.class);
      vikendIntent.putExtra("linka",linka);
      vikendIntent.putExtra("smer",smer);
      vikendIntent.putExtra("zastavka", odkial);
      vikendIntent.putExtra("den", "3");

      vikend.setContent(vikendIntent);
      tabHost.addTab(vikend);

    } else if (typy.size()==3){

      TabHost.TabSpec prazdn = tabHost.newTabSpec(HOLIDAY);
      prazdn.setIndicator(HOLIDAY);
      Intent prazdnIntent = new Intent(this, CasyPraca.class);
      prazdnIntent.putExtra("linka",linka);
      prazdnIntent.putExtra("smer",smer);
      prazdnIntent.putExtra("zastavka", odkial);
      prazdnIntent.putExtra("den", "2");

      prazdn.setContent(prazdnIntent);
      tabHost.addTab(prazdn);

      TabHost.TabSpec vikend = tabHost.newTabSpec(WEEKEND);
      vikend.setIndicator(WEEKEND);
      Intent vikendIntent = new Intent(this, CasyPraca.class);
      vikendIntent.putExtra("linka",linka);
      vikendIntent.putExtra("smer",smer);
      vikendIntent.putExtra("zastavka", odkial);
      vikendIntent.putExtra("den", "3");

      vikend.setContent(vikendIntent);
      tabHost.addTab(vikend);


    }


  }

  /**
   * Metóda, ktorá získa názvy stlpcov pre typy dní
   * @param c
   * @return Zoznam názvov stĺpcov
   */
  public List<String> getTypDna(Cursor c){
    List<String> typdna = new ArrayList<String>();
    if (c != null ) {
      if  (c.moveToFirst()) {
        do {
          String dir = c.getString(c.getColumnIndex("typ_dna"));
          typdna.add(dir);
        }while (c.moveToNext());
      }
    } else {
      typdna.add("Hello");
    }
    return typdna;
  }

}
