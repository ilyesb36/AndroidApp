package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.myapplication.classes.Object;
import com.example.myapplication.classes.ObjetctsBDD;

import java.util.HashMap;

public class DinnerActivity extends AppCompatActivity {

    ObjetctsBDD objetctsBDD;

    TextView level;
    RoundCornerProgressBar expBar;
    RoundCornerProgressBar healthBar;
    RoundCornerProgressBar foodBar;

    TextView quantityFirst;
    TextView quantitySecond;
    TextView quantityThird;

    int position = 0;

    ImageView iFirst;
    ImageView iSecond;
    ImageView iThird;

    HashMap<String,String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dinner);

        objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();

        level = findViewById(R.id.level);
        level.bringToFront();
        expBar = findViewById(R.id.expBar);
        healthBar = findViewById(R.id.healthBar);
        foodBar = findViewById(R.id.foodBar);

        level.setText(String.valueOf(objetctsBDD.getQuantityWithTitle("Level")));
        expBar.setProgress(objetctsBDD.getQuantityWithTitle("Experience"));
        healthBar.setProgress(objetctsBDD.getQuantityWithTitle("Health"));
        foodBar.setProgress(objetctsBDD.getQuantityWithTitle("Food"));

        list = new HashMap<>();

        list.put("Apple","apple");
        list.put("Banana", "banana");
        list.put("Potion", "potion");
        list.put("Orange", "orange");
        list.put("Strawberry", "strawberry");

        iFirst = findViewById(R.id.first);
        iSecond = findViewById(R.id.second);
        iThird = findViewById(R.id.third);

        updateList();

        quantityFirst = findViewById(R.id.quantityFirst);
        quantitySecond = findViewById(R.id.quantitySecond);
        quantityThird = findViewById(R.id.quantityThird);

        updateQuatity();
    }

    private void updateList() {
        String first = (String) list.keySet().toArray()[position];
        if (list.size()>position+1){
            String second = (String) list.keySet().toArray()[position + 1];
            fixImage(second, iSecond);
            if(list.size()>position+2){
                String third = (String) list.keySet().toArray()[position + 2];
                fixImage(third, iThird);
            }
        }
        fixImage(first, iFirst);


    }

    private void fixImage(String first, ImageView imageView) {
        if(list.get(first).equals("apple")){
            imageView.setImageResource(R.drawable.apple);
        }else if(list.get(first).equals("banana")){
            imageView.setImageResource(R.drawable.banana);
        }else if(list.get(first).equals("potion")){
            imageView.setImageResource(R.drawable.potion);
        }else if (list.get(first).equals("orange")){
            imageView.setImageResource(R.drawable.orange);
        }else if (list.get(first).equals("strawberry")){
            imageView.setImageResource(R.drawable.strawberry);
        }
    }

    public void updateQuatity(){
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();
        String first = (String) list.keySet().toArray()[position];
        if (list.size()>position+1){
            String second = (String) list.keySet().toArray()[position + 1];
            quantitySecond.setText(Integer.toString(objetctsBDD.getQuantityWithTitle(second)));
            if(list.size()>position+2){
                String third = (String) list.keySet().toArray()[position + 2];
                quantityThird.setText(Integer.toString(objetctsBDD.getQuantityWithTitle(third)));
            }
        }
        quantityFirst.setText(Integer.toString(objetctsBDD.getQuantityWithTitle(first)));
    }

    public void goMainActivity(View view) {
        Intent intent = new Intent(DinnerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void eatFirst(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();

        String first = (String) list.keySet().toArray()[position];
        if (objetctsBDD.getQuantityWithTitle(first)>0){
            objetctsBDD.decrementerProduit(first,1);
            increaseSatiety(10);
            Toast.makeText(this, "La satiété a augmenté de 10", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vous n'avez pas assez de " + first + " ", Toast.LENGTH_SHORT).show();
        }

        updateQuatity();

    }

    public void eatSecond(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();

        String second = "Banana";
        if (list.size()>position+1){
             second = (String) list.keySet().toArray()[position + 1];
        } else {
             second = (String) list.keySet().toArray()[position - 3 + 1];
        }
        if (objetctsBDD.getQuantityWithTitle(second)>0){
            objetctsBDD.decrementerProduit(second,1);
            increaseSatiety(10);
            Toast.makeText(this, "La satiété a augmenté de 10", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vous n'avez pas assez de " + second+ " ", Toast.LENGTH_SHORT).show();
        }
        updateQuatity();
    }

    public void eatThird(View view) {
        String third = "potion";
        if (list.size()>position+2){
            third = (String) list.keySet().toArray()[position + 2];
        }else {
            third = (String) list.keySet().toArray()[position - 3 + 2];
        }
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();

        if (objetctsBDD.getQuantityWithTitle(third)>0){
            objetctsBDD.decrementerProduit(third,1);
            increaseSatiety(20);
            Toast.makeText(this, "La satiété a augmenté de 20", Toast.LENGTH_SHORT).show();
            increaseExp(20);
        } else {
            Toast.makeText(this, "Vous n'avez pas assez de " + third+ " ", Toast.LENGTH_SHORT).show();
        }
        updateQuatity();
    }

    public void right(View view) {
        if(list.size()>position + 3){
            position = position + 3;
            updateList();
            updateQuatity();
        }

    }

    public void left(View view) {
        if(position != 0){
            position = position - 3;
            updateList();
            updateQuatity();
        }
    }

    private void increaseSatiety(int value) {
        ObjetctsBDD objetctsBDD  = new ObjetctsBDD(this);
        objetctsBDD.open();
        int satiety = objetctsBDD.getProduitWithTitre("Food").getQuantity() + value;
        if (satiety > 200) {
            satiety = 200;
        }
        foodBar.setProgress(satiety);
        Object object = new Object("Food", satiety);
        objetctsBDD.updateProduit("Food", object);
    }

    private void increaseExp(int value) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);
        objetctsBDD.open();
        int exp = objetctsBDD.getQuantityWithTitle("Experience") + value;
        if (exp > 500) {
            Object object = new Object("Experience", exp - 500);
            objetctsBDD.updateProduit("Experience", object);
            objetctsBDD.updateProduit("Level", new Object("Level", objetctsBDD.getQuantityWithTitle("Level") + 1));
            expBar.setProgress(objetctsBDD.getQuantityWithTitle("Experience"));
            level.setText(String.valueOf(objetctsBDD.getQuantityWithTitle("Level")));
            Toast.makeText(this, "Bravo ! Votre Vimo a augmenté de niveau", Toast.LENGTH_LONG).show();
        } else {
            Object object = new Object("Experience", exp);
            objetctsBDD.updateProduit("Experience", object);
            expBar.setProgress(objetctsBDD.getQuantityWithTitle("Experience"));
        }

    }
}
