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
import android.widget.TextView;

import com.example.risojanitor.clevermhd2.MainScreen;
import com.example.risojanitor.clevermhd2.R;
import com.example.risojanitor.clevermhd2.db.DatabaseSelects;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda pre zobrazenie časov pre linku
 */
public class CasyPraca extends ListActivity {

    String mesto, smer, linka, zastavka, den;
    Cursor crCasy;
    Context packageName;
    List<String> listCasy = new ArrayList<String>();
    private TextView nadpis, textLinky, textSmer, nadpisPre;
    ImageButton home;
    LinearLayout popis, layoutbuttons;

    /**
     * Metóda na vykreslenia obrazovky
     * @param icicle
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        try{
            setContentView(R.layout.zoznam);


            Intent mIntent = getIntent();
            linka=mIntent.getStringExtra("linka");
            smer = mIntent.getStringExtra("smer");
            zastavka=mIntent.getStringExtra("zastavka");
            den=mIntent.getStringExtra("den");

            ;
            packageName=getApplicationContext();
            crCasy=DatabaseSelects.getInstance().getCasyPreZast(linka, smer, zastavka, den);
            listCasy=getCasPreZast(crCasy);

            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listCasy));

            nadpis=(TextView)findViewById(R.id.nadpisZoznam);
            nadpis.setText("Časy");
            popis = (LinearLayout) findViewById(R.id.linkyPopis);
            popis.setVisibility(View.VISIBLE);
            layoutbuttons = (LinearLayout) findViewById(R.id.layoutbuttons);
            layoutbuttons.setVisibility(View.GONE);
            nadpisPre = (TextView) findViewById(R.id.nadpisPre);
            nadpisPre.setVisibility(View.VISIBLE);
            nadpisPre.setText("Pre: " +linka +" - " + zastavka);
            textLinky=(TextView)findViewById(R.id.textLiky);
            textLinky.setText("Hodina");
            textSmer=(TextView)findViewById(R.id.textSmer);
            textSmer.setText("Minúty");

                nadpis.setTextColor(Color.parseColor("#0000FF"));
                textLinky.setTextColor(Color.parseColor("#0000FF"));
                textSmer.setTextColor(Color.parseColor("#0000FF"));
                nadpisPre.setTextColor(Color.parseColor("#0000FF"));

            home= (ImageButton)findViewById(R.id.buttonhome);
            home.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent inn1=new Intent(view.getContext(), MainScreen.class);
                    inn1.putExtra("mesto", mesto);

                    startActivity(inn1);
                }
            });

        } catch (Exception ex){};
    }


    /**
     * Metóda pre získanie časov z databázy
     * @param c
     * @return Zoznam časov
     */
    public List<String> getCasPreZast(Cursor c){
        List<String> linky = new ArrayList<String>();
        List<String> casy = new ArrayList<String>();
        String dir, first, hour;
        String[] help;
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    dir = c.getString(c.getColumnIndex("cas"));

                    linky.add(dir);
                }while (c.moveToNext());
            }
        } else {
            linky.add("Hello");
        }
        first=linky.get(0);
        help=linky.get(0).split(":");
        hour=help[0];
        dir=help[0]+" || "+help[1];
//        casy.add(dir);
        for (int i = 1; i<linky.size(); i++){
            help=linky.get(i).split(":");
            if (hour.equalsIgnoreCase(help[0])){
                dir=dir+"  "+help[1];
            } else {
                casy.add(dir);
                dir=help[0]+" || "+help[1];
                hour=help[0];
            }
        }
        casy.add(dir);

        return casy;
    }
}
