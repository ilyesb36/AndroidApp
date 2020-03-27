package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TicTacToeActivity extends Activity implements View.OnClickListener {

    ProduitsBDD produitsBDD;
    private ImageButton[][] btns;
    private boolean player1Turn;
    private int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set no title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_tictactoe);

        this.player1Turn = true;
        btns = new ImageButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String id = "btn_" + i + j;
                int resId = getResources().getIdentifier(id, "id", getPackageName());
                btns[i][j] = findViewById(resId);
                btns[i][j].setTag("");
                btns[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        ImageButton imgBtn = ((ImageButton) view);

        if ( !imgBtn.getTag().equals("") ) {
            return;
        }

        imgBtn.setImageResource(R.drawable.cross);
        imgBtn.setTag("X");
        player1Turn = false;

        if (!checkWins()) {
            // Obtenir les cases libres
            ArrayList<ArrayList<Integer>> freeCases = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (btns[i][j].getTag().toString().equals("")) {
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.add(i);
                        temp.add(j);
                        freeCases.add(temp);
                    }
                }
            }

            // Au tour de bot
            Random r = new Random();
            int pos = r.nextInt(freeCases.size());
            btns[freeCases.get(pos).get(0)][freeCases.get(pos).get(1)].setImageResource(R.drawable.round);
            btns[freeCases.get(pos).get(0)][freeCases.get(pos).get(1)].setTag("0");
            player1Turn = true;

            this.roundCount++;
        }

        if (this.checkWins()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            if (!player1Turn) {
                builder1.setTitle("Bravo ! Vous avez gagné");
                builder1.setMessage("Vous pouvez récupérer 20 pièces !");
                builder1.setCancelable(false);

                builder1.setPositiveButton("Récupéré", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Baisser la satiété
                                decreseStiety(-20);
                                // Augmenter le nombre de gold de 20
                                updateGold(20);
                                // Augmenter l'expérience
                                increaseExp(10);
                                // Aller vers l'accueil
                                dialog.cancel();
                                Intent intent = new Intent(TicTacToeActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();
            } else {
                builder1.setTitle("Dommage, vous avez perdu...");
                builder1.setMessage("Vous avez perdu 10 pièces...");
                builder1.setCancelable(false);

                builder1.setPositiveButton("Sortir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Baisser la satiété
                                decreseStiety(-20);
                                // Diminuer le nombre de gold de 10
                                updateGold(-10);
                                // Aller vers page d'accueil
                                dialog.cancel();
                                Intent intent = new Intent(TicTacToeActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();
            }
        } else if (this.roundCount == 9) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Égalité !");
            builder1.setMessage("Vous voulez faire un autre essai ?");
            builder1.setCancelable(false);

            builder1.setPositiveButton("Sortir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Baisser la satiété
                            decreseStiety(-20);
                            // Aller vers page d'accueil
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton("Rejouer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Baisser la satiété
                            decreseStiety(-20);
                            // Recommencer le jeu
                            resetGame();
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder1.create();
            alert.show();
        }
    }

    private boolean checkWins() {
        boolean wins = false;
        String[][] cases = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cases[i][j] = btns[i][j].getTag().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (cases[i][0].equals(cases[i][1])
                    && cases[i][0].equals(cases[i][2])
                    && !cases[i][0].equals("")) {
                wins = true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (cases[0][i].equals(cases[1][i])
                    && cases[0][i].equals(cases[2][i])
                    && !cases[0][i].equals("")) {
                wins = true;
            }
        }

        if (cases[0][0].equals(cases[1][1])
                && cases[0][0].equals(cases[2][2])
                && !cases[0][0].equals("")) {
            wins = true;
        }

        if (cases[0][2].equals(cases[1][1])
                && cases[0][2].equals(cases[2][0])
                && !cases[0][2].equals("")) {
            wins = true;
        }

        return wins;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btns[i][j].setImageResource(android.R.color.transparent);
                btns[i][j].setTag("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void decreseStiety(int value) {
        produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        int satiety = produitsBDD.getProduitWithTitre("Food").getQuantity() + value;
        if (satiety < 0) {
            satiety = 0;
        }
        Produit produit = new Produit("Food", satiety);
        produitsBDD.updateProduit("Food", produit);
    }

    private void updateGold(int value) {
        produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        Produit produit = new Produit("Gold", produitsBDD.getProduitWithTitre("Gold").getQuantity() + value);
        produitsBDD.updateProduit("Gold", produit);
    }

    private void increaseExp(int value) {
        produitsBDD = new ProduitsBDD(this);
        produitsBDD.open();
        int exp = produitsBDD.getQuantityWithTitle("Experience") + value;
        if (exp > 500) {
            Produit produit = new Produit("Experience", exp - 500);
            produitsBDD.updateProduit("Experience", produit);
            produitsBDD.updateProduit("Level", new Produit("Level", produitsBDD.getQuantityWithTitle("Level") + 1));
            Toast.makeText(this, "Bravo ! Votre Vimo a augmenté de niveau", Toast.LENGTH_LONG).show();
        } else {
            Produit produit = new Produit("Experience", exp);
            produitsBDD.updateProduit("Experience", produit);
        }

    }

}
