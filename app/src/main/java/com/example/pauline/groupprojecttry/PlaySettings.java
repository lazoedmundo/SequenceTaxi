package com.example.pauline.groupprojecttry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *PlaySettings is an android interface class that initializes the Controller, Player and JSON fields, and shows the menus for
 *selecting the icon images of the game and gives an option to mute the sound of the buttons
 *
 */
public class PlaySettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Controller controller;
    Player player;
    JSON json = new JSON();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_settings);


        controller = new Controller(this);
        player = json.loadPlayer(this);

        //update remaining lives of the player from json
        TextView lives = findViewById(R.id.lives);
        lives.setText(String.valueOf(controller.getPlayerLives()));

        //update remaining coins of the player from json
        TextView coins = findViewById(R.id.coins);
        coins.setText(String.valueOf(controller.getPlayerCoins()));

        /* initiate a Switch */
        Switch simpleSwitch = (Switch) findViewById(R.id.sound);

        /* set the current state of a Switch */
        simpleSwitch.setChecked(true);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setSelection(player.getPreferedStyle());

        // Back Button
        Button button=(Button)findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PlaySettings.this, StartPageActivity.class); // Change the mainActivity to the game page
                intent.putExtra("data", String.valueOf(spinner.getSelectedItem()));
                startActivity(intent);
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("directions");
        categories.add("animals");
        categories.add("numbers");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();

        player.setPreferedStyle(i);

        //save json
        json.savePlayer(this, player);

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        //Cut and paste the following code to the class where you control the spinner
        /* Bundle bundle=getIntent().getExtras();
        String data=bundle.get("data").toString(); */
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}