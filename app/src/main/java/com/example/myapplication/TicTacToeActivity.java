package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.classes.Constante;
import com.example.myapplication.classes.Produit;
import com.example.myapplication.classes.ProduitsBDD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TicTacToeActivity extends Activity implements View.OnClickListener {

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

        if (player1Turn) {
            imgBtn.setImageResource(R.drawable.cross);
            imgBtn.setTag("X");
            player1Turn = false;
        } else {
            imgBtn.setImageResource(R.drawable.round);
            imgBtn.setTag("O");
            player1Turn = true;
        }

        this.roundCount++;

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        if (this.checkWins()) {
            if (!player1Turn) {
                builder1.setTitle("Bravo ! Vous avez gagné");
                builder1.setMessage("Vous pouvez récupérer 20 pièces !");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Récupéré", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Augmenter le nombre de gold de 20
                                updateGold(20);
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
                builder1.setMessage("Vous avez perdu 5 pièces...");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Sortir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Diminuer le nombre de gold de 5
                                updateGold(-5);
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
            builder1.setTitle("Égalité !");
            builder1.setMessage("Vous voulez faire un autre essai ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton("Sortir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Aller vers page d'accueil
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton("Rejouer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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

    private void updateGold(int value) {
        ProduitsBDD produitsBDD = new ProduitsBDD(this);

        produitsBDD.open();

        Produit produit = new Produit("Player", produitsBDD.getProduitWithTitre("Player").getQuantity() + value);
        produitsBDD.updateProduit("Player", produit);
        /*String gold = "";

        FileInputStream fis = null;

        try {
            fis = openFileInput(Constante.GOLD_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            gold = sb.toString().trim();

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

        if (Integer.parseInt(gold) + value < 0) {
            gold = "0";
        } else {
            gold = String.valueOf(Integer.parseInt(gold) + value);
        }

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(Constante.GOLD_FILE, MODE_PRIVATE);
            fos.write(gold.getBytes());
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
        }*/
    }

}
