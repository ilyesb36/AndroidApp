package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.myapplication.classes.Constante;
import com.example.myapplication.classes.Object;
import com.example.myapplication.classes.ObjetctsBDD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView name;
    TextView level;
    ObjetctsBDD objetctsBDD;
    TextView goldValue;
    RoundCornerProgressBar expBar;
    RoundCornerProgressBar healthBar;
    RoundCornerProgressBar foodBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Apple") == null){
            Object object = new Object("Apple", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Orange") == null){
            Object object = new Object("Orange", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Strawberry") == null){
            Object object = new Object("Strawberry", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Banana") == null){
            Object object = new Object("Banana", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Potion") == null){
            Object object = new Object("Potion", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Gold") == null){
            Object object = new Object("Gold", 200);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Health") == null) {
            Object object = new Object("Health", 200);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Food") == null) {
            Object object = new Object("Food", 200);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Experience") == null) {
            Object object = new Object("Experience", 0);
            objetctsBDD.insertProduit(object);
        }

        if (objetctsBDD.getProduitWithTitre("Level") == null) {
            Object object = new Object("Level", 1);
            objetctsBDD.insertProduit(object);
        }

        this.name = findViewById(R.id.name);
        this.level = findViewById(R.id.level);
        this.level.bringToFront();
        this.goldValue = findViewById(R.id.goldValue);
        this.expBar = findViewById(R.id.expBar);
        this.healthBar = findViewById(R.id.healthBar);
        this.foodBar = findViewById(R.id.foodBar);

        if (getName().equals("")) {
            giveNameToVimo("");
        } else {
            name.setText(getName());
        }

        this.level.setText(String.valueOf(objetctsBDD.getQuantityWithTitle("Level")));
        this.expBar.setProgress(objetctsBDD.getQuantityWithTitle("Experience"));
        this.foodBar.setProgress(objetctsBDD.getQuantityWithTitle("Food"));
        this.healthBar.setProgress(objetctsBDD.getQuantityWithTitle("Health"));

        // Load gold value from a file of internal storage
        int gold = objetctsBDD.getProduitWithTitre("Gold").getQuantity();
        goldValue.setText(Integer.toString(gold));

        MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.level.setText(String.valueOf(objetctsBDD.getQuantityWithTitle("Level")));
        this.expBar.setProgress(objetctsBDD.getQuantityWithTitle("Experience"));
    }

    public void goActShop(View view) {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goPlayGame(View view) {
        Intent intent = new Intent(MainActivity.this, TicTacToeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goActDinner(View view) {
        Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void changeName(View view) {
        TextView temp = (TextView) view;
        giveNameToVimo(temp.getText().toString());
    }

    private void giveNameToVimo(String current) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nommer votre Vimo");

        final EditText input = new EditText(this);
        input.setText(current);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setCancelable(false);
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name.setText(input.getText().toString());
                setName(input.getText().toString());
            }
        });

        builder.show();
    }

    private void setName(String name) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(Constante.NAME_FILE, MODE_PRIVATE);
            fos.write(name.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getName() {
        String res = "";

        FileInputStream fis = null;
        try {
            fis = openFileInput(Constante.NAME_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            res = sb.toString().trim();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }
}



