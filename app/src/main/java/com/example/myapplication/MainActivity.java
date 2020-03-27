package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;


public class MainActivity extends AppCompatActivity {

    TextView goldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ProduitsBDD produitsBDD = new ProduitsBDD(this);

        produitsBDD.open();

        if (produitsBDD.getProduitWithTitre("Apple") == null){
            Produit produit = new Produit("Apple", 0);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Banana") == null){
            Produit produit = new Produit("Banana", 0);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Potion") == null){
            Produit produit = new Produit("Potion", 0);
            produitsBDD.insertProduit(produit);
        }
        if (produitsBDD.getProduitWithTitre("Gold") == null){
            Produit produit = new Produit("Gold", 200);
            produitsBDD.insertProduit(produit);
        }
        if (produitsBDD.getProduitWithTitre("Health") == null) {
            Produit produit = new Produit("Health", 200);
            produitsBDD.insertProduit(produit);
        }

        this.goldValue = findViewById(R.id.goldValue);

        // Load gold value from a file of internal storage
        int gold = produitsBDD.getProduitWithTitre("Gold").getQuantity();
        goldValue.setText(Integer.toString(gold));

        MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public void goActBag(View view) {
        Intent intent = new Intent(MainActivity.this, BagActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}



