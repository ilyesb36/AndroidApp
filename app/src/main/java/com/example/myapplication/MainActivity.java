package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.ring);
        ring.start();
    }

    public void goActShop(View view) {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    public void goPlayGame(View view) {
        Intent intent = new Intent(MainActivity.this, TicTacToeActivity.class);
        startActivity(intent);
    }


}



