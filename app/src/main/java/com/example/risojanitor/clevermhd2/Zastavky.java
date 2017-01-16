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

import java.util.ArrayList;
import java.util.List;

public class Zastavky extends ListActivity {
  private TextView nadpis, zoznamPre;
  LinearLayout popis, layoutbuttons;
  List<String> zastavky = new ArrayList<String>();
  String speclinka, smer;
  DatabaseHelper asset;
  Cursor crZastavky;
  Context packageName;
  ImageButton home;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.zoznam);
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);

    try{

      nadpis=(TextView)findViewById(R.id.nadpisZoznam);
      nadpis.setText("Zastávky");
      popis=(LinearLayout)findViewById(R.id.linkyPopis);
      popis.setVisibility(View.INVISIBLE);
      zoznamPre=(TextView)findViewById(R.id.nadpisPre);
      zoznamPre.setVisibility(View.GONE);
      Intent mIntent = getIntent();
      layoutbuttons = (LinearLayout) findViewById(R.id.layoutbuttons);
      layoutbuttons.setVisibility(View.GONE);

//      speclinka=mIntent.getStringExtra("specZast");
//      if (speclinka!=null){
//        packageName=getApplicationContext();
//        asset=new DatabaseHandler(packageName,mesto+".db");
//        //asset.close();
//        asset.createDatabase();
//        asset.open();
//        crZastavky=asset.getSpecificZastavka(getNazovLinky(speclinka),smer);
//        asset.close();
//        zastavky=getZastavky(crZastavky);
//      } else {
        zastavky= mIntent.getStringArrayListExtra("zastavky");
//      }
      setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        zastavky));


      nadpis.setTextColor(Color.parseColor("#0000FF"));
      home= (ImageButton)findViewById(R.id.buttonhome);
      home.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

          Intent inn1=new Intent(view.getContext(), MainScreen.class);

          startActivity(inn1);
        }
      });


    } catch (Exception ex){};


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

    Intent inn1=new Intent(v.getContext(), Linky.class);
    inn1.putExtra("zastavka", zastavky.get(position));
    startActivity(inn1);
  }

  /**
   * Metóda na získanie názvu linky zo stringu
   * @param nazov
   * @return
   */
  public String getNazovLinky(String nazov){
    String[] help = nazov.split("\\|\\|");
    smer=help[1].substring(2);
    return help[0].substring(0, help[0].length()-2);
  }

  /**
   * Metóda na získanie zastávok z databázy
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
