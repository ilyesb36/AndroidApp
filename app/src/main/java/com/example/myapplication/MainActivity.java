package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.classes.Constante;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    TextView goldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        this.goldValue = findViewById(R.id.goldValue);

        // Load gold value from a file of internal storage
        this.getGold();

        MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getGold();
    }

    public void goActShop(View view) {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    public void goPlayGame(View view) {
        Intent intent = new Intent(MainActivity.this, TicTacToeActivity.class);
        startActivity(intent);
    }

    private void getGold() {
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
            goldValue.setText(sb.toString());
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
    }
}



