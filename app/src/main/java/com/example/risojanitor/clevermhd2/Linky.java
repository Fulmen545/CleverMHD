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
 * Created by Riso Janitor on 15. 1. 2017.
 */

public class Linky extends ListActivity {
  private TextView nadpis, textLinky, textSmer, nadpisPre;
  LinearLayout popis, layoutbuttons;
  List<String> linklist = new ArrayList<String>();
  String zastavka;
  Cursor crLinky;
  Context packageName;
  ImageButton home;
  private DatabaseSelects databaseSelects;
  private DatabaseHelper databaseHelper;

  private void init() {
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
   * @param savedInstanceState
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
    try {
      setContentView(R.layout.zoznam);

      nadpis = (TextView) findViewById(R.id.nadpisZoznam);
      nadpis.setText("Linky");
      popis = (LinearLayout) findViewById(R.id.linkyPopis);
      popis.setVisibility(View.VISIBLE);
      layoutbuttons = (LinearLayout) findViewById(R.id.layoutbuttons);
      layoutbuttons.setVisibility(View.GONE);
      nadpisPre = (TextView) findViewById(R.id.nadpisPre);
      Intent mIntent = getIntent();
      zastavka=mIntent.getStringExtra("zastavka");
      packageName=getApplicationContext();
      if (zastavka==null){
        crLinky= databaseSelects.getLinkySql();
        nadpisPre.setVisibility(View.INVISIBLE);
      } else {
        crLinky= databaseSelects.getSpecificLinka(zastavka);
        nadpisPre.setVisibility(View.VISIBLE);
        nadpisPre.setText("Pre zastávku: " +zastavka);
      }

      linklist=getLinky(crLinky);
      setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        linklist));
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
//  public void onListItemClick(ListView parent, View v, int position,
//                              long id) {
//
//    Intent inn1=new Intent(v.getContext(), ZastLinku.class);
//    inn1.putExtra("mesto", mesto);
//    inn1.putExtra("specZast", linklist.get(position));
//
//    startActivity(inn1);
//  }

  /**
   * Metóda na získanie liniek z databázy
   * @param c
   * @return Zoznam liniek
   */
  public List<String> getLinky(Cursor c){
    List<String> linky = new ArrayList<String>();
    if (c != null ) {
      if  (c.moveToFirst()) {
        do {
          String dir = c.getString(c.getColumnIndex("linka"))+ "  ||  " + c.getString(c.getColumnIndex("smer"));
          linky.add(dir);
        }while (c.moveToNext());
      }
    } else {
      linky.add("Hello");
    }
    return linky;
  }

}
