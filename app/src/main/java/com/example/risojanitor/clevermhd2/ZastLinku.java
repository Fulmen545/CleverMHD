package com.example.risojanitor.clevermhd2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.risojanitor.clevermhd2.db.DatabaseHelper;
import com.example.risojanitor.clevermhd2.db.DatabaseSelects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riso Janitor on 16. 1. 2017.
 */

public class ZastLinku extends ListActivity {
  private TextView nadpis, textLinky, textSmer, nadpisPre;
  private LinearLayout popis, layoutbuttons;
  private List<String> zastlist = new ArrayList<String>();
  private String smer, speclinka, linka;
  private Cursor crZastavky;
  private ImageButton home;
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

  /**
   * Metóda na vykreslenia obrazovky
   * @param icicle
   */
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    try {
      setContentView(R.layout.zoznam);
      init();

      nadpis = (TextView) findViewById(R.id.nadpisZoznam);
      nadpis.setText("Zastávky");
      popis = (LinearLayout) findViewById(R.id.linkyPopis);
      popis.setVisibility(View.GONE);
      nadpisPre = (TextView) findViewById(R.id.nadpisPre);
      nadpisPre.setVisibility(View.VISIBLE);
      layoutbuttons = (LinearLayout) findViewById(R.id.layoutbuttons);
      layoutbuttons.setVisibility(View.GONE);

      Intent mIntent = getIntent();
      speclinka=mIntent.getStringExtra("specZast");
      linka=getNazovLinky(speclinka);
      nadpisPre.setText("Pre Linku: " +speclinka);

      crZastavky=databaseSelects.getZoznZast(linka, smer);

      zastlist=getZastavky(crZastavky);
      setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        zastlist));
      textLinky=(TextView)findViewById(R.id.textLiky);
      textLinky.setText("Linka");
      textSmer=(TextView)findViewById(R.id.textSmer);
      textSmer.setText("Smer");

        nadpis.setTextColor(Color.parseColor("#0000FF"));
        textLinky.setTextColor(Color.parseColor("#0000FF"));
        textSmer.setTextColor(Color.parseColor("#0000FF"));
        nadpisPre.setTextColor(Color.parseColor("#0000FF"));


      home= (ImageButton)findViewById(R.id.buttonhome);
      home.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

          Intent inn1=new Intent(view.getContext(), MainScreen.class);

          startActivity(inn1);
        }
      });


    } catch (Exception ex) {
    }

  }

  /**
   * Metóda pre funkcionalitu zoznamu
   * @param parent
   * @param v
   * @param position
   * @param id
   */
  public void onListItemClick(ListView parent, View v, int position,
                              long id) {

    Intent inn1=new Intent(v.getContext(), CasyPreZast.class);
    inn1.putExtra("linka",linka);
    inn1.putExtra("smer",smer);
    inn1.putExtra("odkial", zastlist.get(position));

    startActivity(inn1);
  }

  /**
   * Metóda na získanie názvu linky
   * @param nazov
   * @return
   */
  public String getNazovLinky(String nazov){
    String[] help = nazov.split("\\|\\|");
    smer=help[1].substring(2);
    return help[0].substring(0, help[0].length()-2);
  }

  /**
   * Metóda na získanie zastávok
   * @param c
   * @return
   */
  public List<String> getZastavky(Cursor c){
    List<String> linky = new ArrayList<String>();
    if (c != null ) {
      if  (c.moveToFirst()) {
        do {
          String dir = c.getString(c.getColumnIndex("kam"));
          linky.add(dir);
        }while (c.moveToNext());
      }
    } else {
      linky.add("Hello");
    }
    return linky;
  }
}
