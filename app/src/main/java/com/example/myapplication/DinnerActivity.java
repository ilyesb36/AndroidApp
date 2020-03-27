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

import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

import java.util.HashMap;

public class DinnerActivity extends AppCompatActivity {

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

        list = new HashMap<>();

        list.put("Apple","apple");
        list.put("Banana", "banana");
        list.put("Potion", "potion");
        list.put("Orange", "orange");
        list.put("Strawberry", "strawberry");

        iFirst = (ImageView) findViewById(R.id.first);
        iSecond = (ImageView) findViewById(R.id.second);
        iThird = (ImageView) findViewById(R.id.third);



        updateList();


        quantityFirst = (TextView) findViewById(R.id.quantityFirst);
        quantitySecond = (TextView) findViewById(R.id.quantitySecond);
        quantityThird = (TextView) findViewById(R.id.quantityThird);

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
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        String first = (String) list.keySet().toArray()[position];
        if (list.size()>position+1){
            String second = (String) list.keySet().toArray()[position + 1];
            quantitySecond.setText(Integer.toString(produitsBDD.getQuantityWithTitle(second)));
            if(list.size()>position+2){
                String third = (String) list.keySet().toArray()[position + 2];
                quantityThird.setText(Integer.toString(produitsBDD.getQuantityWithTitle(third)));
            }
        }

        quantityFirst.setText(Integer.toString(produitsBDD.getQuantityWithTitle(first)));

    }

    public void goMainActivity(View view) {
        Intent intent = new Intent(DinnerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void eatApple(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        String first = (String) list.keySet().toArray()[position];
        if(produitsBDD.getQuantityWithTitle(first)>0){
            produitsBDD.decrementerProduit(first,1);
        }else {
            Toast.makeText(this, "Vous n'avez pas assez de " + first + " ", Toast.LENGTH_SHORT).show();
        }

        updateQuatity();

    }

    public void eatBanana(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        String second = "Banana";
        if (list.size()>position+1){
             second = (String) list.keySet().toArray()[position + 1];
        }else {
             second = (String) list.keySet().toArray()[position - 3 + 1];
        }
        if(produitsBDD.getQuantityWithTitle(second)>0){
            produitsBDD.decrementerProduit(second,1);
        }else {
            Toast.makeText(this, "Vous n'avez pas assez de " + second+ " ", Toast.LENGTH_SHORT).show();
        }
        updateQuatity();
    }

    public void drinkPotion(View view) {
        String third = "potion";
        if (list.size()>position+2){
            third = (String) list.keySet().toArray()[position + 2];
        }else {
            third = (String) list.keySet().toArray()[position - 3 + 2];
        }
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        if(produitsBDD.getQuantityWithTitle(third)>0){
            produitsBDD.decrementerProduit(third,1);
        }else {
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
}
