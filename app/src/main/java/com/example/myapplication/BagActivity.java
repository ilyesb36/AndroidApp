package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitListAdapter;
import com.example.myapplication.classes.ProduitsBDD;

import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    ProduitsBDD produitsBDD;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bag);

        listView = findViewById(R.id.listView);

        // Obtenir les objets que l'utilisateur possède
        ArrayList<Produit> produitList = new ArrayList<>();
        produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        if (produitsBDD.getProduitWithTitre("Apple").getQuantity() > 0) {
            produitList.add(new Produit("Apple", produitsBDD.getQuantityWithTitle("Apple")));
        }
        if (produitsBDD.getProduitWithTitre("Banana").getQuantity() > 0) {
            produitList.add(new Produit("Banana", produitsBDD.getQuantityWithTitle("Banana")));
        }
        if (produitsBDD.getProduitWithTitre("Potion").getQuantity() > 0) {
            produitList.add(new Produit("Potion", produitsBDD.getQuantityWithTitle("Potion")));
        }

        final ProduitListAdapter adapter = new ProduitListAdapter(this, R.layout.adapter_view_layout, produitList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                final Produit selectedItem = (Produit) parent.getItemAtPosition(position);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(BagActivity.this);
                builder1.setTitle("Utiliser un objet");
                builder1.setMessage("Vous voulez utiliser " + selectedItem.getTitre() + " ?");
                builder1.setCancelable(false);

                builder1.setPositiveButton("Utiliser", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Aller vers page d'accueil
                        useItem(selectedItem.getTitre());
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder1.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder1.create();
                alert.show();
            }
        });

    }

    private void useItem(String name) {

    }

}
