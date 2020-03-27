package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.classes.Object;
import com.example.myapplication.classes.ObjetctsBDD;

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
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);

        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Gold").getQuantity()>15){
            objetctsBDD.ajouterProduit("Apple");
            Object object = new Object("Gold", objetctsBDD.getProduitWithTitre("Gold").getQuantity() - 15);
            objetctsBDD.updateProduit("Gold", object);
            Toast.makeText(this, "Vous avez maintenant " + Integer.toString(objetctsBDD.getProduitWithTitre("Apple").getQuantity()) + " pommes", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBanana(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);

        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Gold").getQuantity()>12){
            objetctsBDD.ajouterProduit("Banana");
            Object object = new Object("Gold", objetctsBDD.getProduitWithTitre("Gold").getQuantity() - 12);
            objetctsBDD.updateProduit("Gold", object);
            Toast.makeText(this, "Vous avez maintenant " + Integer.toString(objetctsBDD.getProduitWithTitre("Banana").getQuantity()) + " bananes", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }

    public void addPotion(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);

        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Gold").getQuantity()>50){
            objetctsBDD.ajouterProduit("Potion");
            Object object = new Object("Gold", objetctsBDD.getProduitWithTitre("Gold").getQuantity() - 50);
            objetctsBDD.updateProduit("Gold", object);
            Toast.makeText(this, "Vous avez maintenant "  + Integer.toString(objetctsBDD.getProduitWithTitre("Potion").getQuantity()) + " potions", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }

    public void addStrawberry(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);

        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Gold").getQuantity()>28){
            objetctsBDD.ajouterProduit("Strawberry");
            Object object = new Object("Gold", objetctsBDD.getProduitWithTitre("Gold").getQuantity() - 28);
            objetctsBDD.updateProduit("Gold", object);
            Toast.makeText(this, "Vous avez maintenant "  + Integer.toString(objetctsBDD.getProduitWithTitre("Strawberry").getQuantity()) + " fraises", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }

    public void addOrange(View view) {
        ObjetctsBDD objetctsBDD = new ObjetctsBDD(this);

        objetctsBDD.open();

        if (objetctsBDD.getProduitWithTitre("Gold").getQuantity()>28){
            objetctsBDD.ajouterProduit("Orange");
            Object object = new Object("Gold", objetctsBDD.getProduitWithTitre("Gold").getQuantity() - 10);
            objetctsBDD.updateProduit("Gold", object);
            Toast.makeText(this, "Vous avez maintenant "  + Integer.toString(objetctsBDD.getProduitWithTitre("Orange").getQuantity()) + " oranges", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Vous n'avez pas assez de pièces", Toast.LENGTH_SHORT).show();
    }
}
