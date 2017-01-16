package com.example.risojanitor.clevermhd2.tabs;

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

import com.example.risojanitor.clevermhd2.CasyPreZast;
import com.example.risojanitor.clevermhd2.MainScreen;
import com.example.risojanitor.clevermhd2.R;
import com.example.risojanitor.clevermhd2.db.DatabaseHelper;
import com.example.risojanitor.clevermhd2.db.DatabaseSelects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Trieda pre výpis zastávok a trvaní cesty medzi nimi pre konkrétnu linku
 */
public class ZoznamZast extends ListActivity {

    String mesto, speclinka, smer, linka, odkial;
    Cursor crZastavky;
    Context packageName;
    List<String> zastavky = new ArrayList<String>();
    private TextView nadpis, textLinky, textSmer, nadpisPre;
    ImageButton home;
    LinearLayout popis, layoutbuttons;
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
      init();
        setContentView(R.layout.zoznam);
        try{



            Intent mIntent = getIntent();
            linka=mIntent.getStringExtra("linka");
            smer = mIntent.getStringExtra("smer");
            odkial=mIntent.getStringExtra("odkial");
            
                packageName=getApplicationContext();
                crZastavky=databaseSelects.getTrvanie(linka,smer,odkial);
                zastavky=getZastTrv(crZastavky);

            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    zastavky));

            nadpis=(TextView)findViewById(R.id.nadpisZoznam);
            nadpis.setText("Zastávky");
            popis = (LinearLayout) findViewById(R.id.linkyPopis);
            popis.setVisibility(View.VISIBLE);
            layoutbuttons = (LinearLayout) findViewById(R.id.layoutbuttons);
            layoutbuttons.setVisibility(View.GONE);
            nadpisPre = (TextView) findViewById(R.id.nadpisPre);
            nadpisPre.setVisibility(View.VISIBLE);
            nadpisPre.setText("Pre: " +linka +" - " + odkial);
            textLinky=(TextView)findViewById(R.id.textLiky);
            textLinky.setText("Trvanie");
            textSmer=(TextView)findViewById(R.id.textSmer);
            textSmer.setText("Zastávka");


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
        } catch (Exception ex){};
    }


    /**
     * Metoóda na získanie zastávok a trvania cesty
     * @param c
     * @return Zoznam zastávok a trvaní
     */
    public List<String> getZastTrv(Cursor c){
        List<String> linky = new ArrayList<String>();
        String dir;
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    if (c.getString(c.getColumnIndex("trvanie"))==null){
                        dir = "--  ||  " + c.getString(c.getColumnIndex("kam"));
                    } else {
                        dir = c.getString(c.getColumnIndex("trvanie")) + "  ||  " + c.getString(c.getColumnIndex("kam"));
                    }
                    linky.add(dir);
                }while (c.moveToNext());
            }
        } else {
            linky.add("Hello");
        }
        return linky;
    }

    /**
     * Metóda pre funkcionalitu stringu
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
        inn1.putExtra("odkial", getNazovZastavky(zastavky.get(position)));
        startActivity(inn1);
    }

    /**
     * Metóda pre získanie názvu zastávky zo stringu
     * @param nazov
     * @return
     */
    public String getNazovZastavky(String nazov){
        String[] help = nazov.split("\\|\\|");

        return help[1].substring(2);
    }


}
