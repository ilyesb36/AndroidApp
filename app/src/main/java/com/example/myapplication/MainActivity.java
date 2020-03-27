package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

public class MainActivity extends AppCompatActivity {

    TextView level;
    ProduitsBDD produitsBDD;
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

        produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        if (produitsBDD.getProduitWithTitre("Apple") == null){
            Produit produit = new Produit("Apple", 0);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Orange") == null){
            Produit produit = new Produit("Orange", 50);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Strawberry") == null){
            Produit produit = new Produit("Strawberry", 20);
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

        if (produitsBDD.getProduitWithTitre("Food") == null) {
            Produit produit = new Produit("Food", 200);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Experience") == null) {
            Produit produit = new Produit("Experience", 0);
            produitsBDD.insertProduit(produit);
        }

        if (produitsBDD.getProduitWithTitre("Level") == null) {
            Produit produit = new Produit("Level", 1);
            produitsBDD.insertProduit(produit);
        }

        this.level = findViewById(R.id.level);
        this.level.bringToFront();
        this.goldValue = findViewById(R.id.goldValue);
        this.expBar = findViewById(R.id.expBar);
        this.healthBar = findViewById(R.id.healthBar);
        this.foodBar = findViewById(R.id.foodBar);
        this.level.setText(String.valueOf(produitsBDD.getQuantityWithTitle("Level")));
        this.expBar.setProgress(produitsBDD.getQuantityWithTitle("Experience"));
        this.foodBar.setProgress(produitsBDD.getQuantityWithTitle("Food"));
        this.healthBar.setProgress(produitsBDD.getQuantityWithTitle("Health"));

        // Load gold value from a file of internal storage
        int gold = produitsBDD.getProduitWithTitre("Gold").getQuantity();
        goldValue.setText(Integer.toString(gold));

        MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.level.setText(String.valueOf(produitsBDD.getQuantityWithTitle("Level")));
        this.expBar.setProgress(produitsBDD.getQuantityWithTitle("Experience"));
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
}



