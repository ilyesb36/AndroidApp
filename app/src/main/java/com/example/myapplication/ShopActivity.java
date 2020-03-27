package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.classes.Constante;
import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    public void goMainActivity(View view) {
        Intent intent = new Intent(ShopActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void addApple(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);

        produitsBDD.open();

        if (produitsBDD.getProduitWithTitre("Gold").getQuantity()>15){
            produitsBDD.ajouterProduit("Apple");
            Produit produit = new Produit("Gold", produitsBDD.getProduitWithTitre("Gold").getQuantity() - 15);
            produitsBDD.updateProduit("Gold", produit);Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Vous avez maintenant " + Integer.toString(produitsBDD.getProduitWithTitre("Apple").getQuantity()) + " pommes", Toast.LENGTH_LONG).show();
        } else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }

    public void addBanana(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);

        produitsBDD.open();

        if (produitsBDD.getProduitWithTitre("Gold").getQuantity()>8){
            produitsBDD.ajouterProduit("Banana");
            Produit produit = new Produit("Gold", produitsBDD.getProduitWithTitre("Gold").getQuantity() - 8);
            produitsBDD.updateProduit("Gold", produit);
            Toast.makeText(this, "Vous avez maintenant " + Integer.toString(produitsBDD.getProduitWithTitre("Banana").getQuantity()) + " bananes", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }

    public void addPotion(View view) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);

        produitsBDD.open();

        if (produitsBDD.getProduitWithTitre("Gold").getQuantity()>50){
            produitsBDD.ajouterProduit("Potion");
            Produit produit = new Produit("Gold", produitsBDD.getProduitWithTitre("Gold").getQuantity() - 50);
            produitsBDD.updateProduit("Gold", produit);
            Toast.makeText(this, "Vous avez maintenant "  + Integer.toString(produitsBDD.getProduitWithTitre("Potion").getQuantity()) + " potions", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }
}
