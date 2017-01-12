package com.example.risojanitor.clevermhd2;

import android.app.Activity;
import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;


import java.io.IOException;
import java.util.List;

import com.example.risojanitor.clevermhd2.db.DatabaseHelper;
import com.example.risojanitor.clevermhd2.db.DatabaseSelects;

public class MainScreen extends Activity implements TextWatcher {

  private static final String TAG = MainScreen.class.getSimpleName();

  private TextView nadpis, odkial, kam;
  private Button rychVyhlad, rozsir, linky, stops, update, vyhladbtn, uloz;
  private AutoCompleteTextView plOdkial, plKam;
  private boolean on = true;
  private RadioButton praca, prazd, vikend;
  private CheckBox priameSpoj;

  private DatabaseHelper databaseHelper;
  private DatabaseSelects databaseSelects;

  private void init() {
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
    init();
    setContentView(R.layout.activity_main_screen);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("Clever MHD");

    plOdkial = (AutoCompleteTextView) findViewById(R.id.odkialplocha);
    plOdkial.addTextChangedListener(this);


    plKam = (AutoCompleteTextView) findViewById(R.id.kamplocha);
    plKam.addTextChangedListener(this);

    odkial = (TextView) findViewById(R.id.textodkial);
    kam = (TextView) findViewById(R.id.textkam);
    praca = (RadioButton) findViewById(R.id.praca);
    prazd = (RadioButton) findViewById(R.id.prazdniny);
    vikend = (RadioButton) findViewById(R.id.vikend);

    priameSpoj = (CheckBox) findViewById(R.id.checkBox1);
    priameSpoj.setChecked(true);

    nadpis = (TextView) findViewById(R.id.nadpmesto);
    nadpis.setText(R.string.kosice);
    nadpis.setTextColor(Color.parseColor("#0000FF"));
    rychVyhlad = (Button) findViewById(R.id.rychvyhlbtn);
    rychVyhlad.setText(R.string.rychVyhlad1);
    rychVyhlad.setBackgroundResource(R.drawable.kosicebutton);
    rychVyhlad.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr2.hideSoftInputFromWindow(plKam.getWindowToken(), 0);
        InputMethodManager mgr1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr1.hideSoftInputFromWindow(plOdkial.getWindowToken(), 0);

        if (on) {
          odkial.setVisibility(View.GONE);
          kam.setVisibility(View.GONE);
          plOdkial.setVisibility(View.GONE);
          plKam.setVisibility(View.GONE);
          praca.setVisibility(View.GONE);
          prazd.setVisibility(View.GONE);
          vikend.setVisibility(View.GONE);
          vyhladbtn.setVisibility(View.GONE);
          priameSpoj.setVisibility(View.GONE);
          rychVyhlad.setText(R.string.rychVyhlad2);
          on = false;
        } else {
          odkial.setVisibility(View.VISIBLE);
          kam.setVisibility(View.VISIBLE);
          plOdkial.setVisibility(View.VISIBLE);
          plKam.setVisibility(View.VISIBLE);
          praca.setVisibility(View.VISIBLE);
          prazd.setVisibility(View.VISIBLE);
          vikend.setVisibility(View.VISIBLE);
          vyhladbtn.setVisibility(View.VISIBLE);
          priameSpoj.setVisibility(View.VISIBLE);
          rychVyhlad.setText(R.string.rychVyhlad1);
          on = true;
        }
      }
    });

    rozsir = (Button) findViewById(R.id.rozsir);
    rozsir.setBackgroundResource(R.drawable.kosicebutton);
    linky = (Button) findViewById(R.id.linky);
    linky.setBackgroundResource(R.drawable.kosicebutton);
    stops = (Button) findViewById(R.id.zastavky);
    stops.setBackgroundResource(R.drawable.kosicebutton);
    update = (Button) findViewById(R.id.update);
    update.setBackgroundResource(R.drawable.kosicebutton);
    uloz = (Button) findViewById(R.id.ulozene);
    uloz.setBackgroundResource(R.drawable.kosicebutton);
    vyhladbtn = (Button) findViewById(R.id.rychvyhl);
    vyhladbtn.setBackgroundResource(R.drawable.kosicebutton);


    List<String> zastavky = databaseSelects.findAllBusStops();

    // TODO samy stav, megavela globalnych premennych..to je fest nebezpecne..zamotas sa
    plOdkial = (AutoCompleteTextView) findViewById(R.id.odkialplocha);
    plOdkial.addTextChangedListener(this);
    plOdkial.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, zastavky));

    plKam = (AutoCompleteTextView) findViewById(R.id.kamplocha);
    plKam.addTextChangedListener(this);
    plKam.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, zastavky));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main_screen, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void afterTextChanged(Editable editable) {

  }

  public void search(View view) {
    // TODO vyhladaj spoje
  }
}
