package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

public class DinnerActivity extends AppCompatActivity {

    TextView quantityApple;
    TextView quantityBanana;
    TextView quantityPotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dinner);

        quantityApple = (TextView) findViewById(R.id.quantityApple);
        quantityBanana = (TextView) findViewById(R.id.quantityBanana);
        quantityPotion = (TextView) findViewById(R.id.quantityPotion);

        updateQuatity();
    }

    public void updateQuatity(){
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        quantityApple.setText(Integer.toString(produitsBDD.getQuantityWithTitle("Apple")));
        quantityBanana.setText(Integer.toString(produitsBDD.getQuantityWithTitle("Banana")));
        quantityPotion.setText(Integer.toString(produitsBDD.getQuantityWithTitle("Potion")));

        produitsBDD.updateProduit("Player", new Produit("Player", 2000));
    }

    public void goMainActivity(View view) {
        Intent intent = new Intent(DinnerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void eatApple(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        if(produitsBDD.getQuantityWithTitle("Apple")>0){
            produitsBDD.decrementerProduit("Apple",1);
        }else {
            Toast.makeText(this, "Vous n'avez pas assez de pommes", Toast.LENGTH_SHORT).show();
        }

        updateQuatity();

    }

    public void eatBanana(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        if(produitsBDD.getQuantityWithTitle("Banana")>0){
            produitsBDD.decrementerProduit("Banana",1);
        }else {
            Toast.makeText(this, "Vous n'avez pas assez de bananes", Toast.LENGTH_SHORT).show();
        }
        updateQuatity();
    }

    public void drinkPotion(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();

        if(produitsBDD.getQuantityWithTitle("Potion")>0){
            produitsBDD.decrementerProduit("Potion",1);
        }else {
            Toast.makeText(this, "Vous n'avez pas assez de potions", Toast.LENGTH_SHORT).show();
        }
        updateQuatity();
    }
}
