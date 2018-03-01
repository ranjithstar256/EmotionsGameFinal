package com.androidmanifester.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SelectModeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //RatingBar ratingBar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        sharedPreferences = getSharedPreferences("sfname", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        /*ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setProgress(1);*/

    }

    public void gotogame(View view) {
       // float t = ratingBar.getRating();
       // int r = (int) t;
     //   Toast.makeText(this, t + "   " + r, Toast.LENGTH_SHORT).show();
        editor.putString("mode", "Game").commit();
        startActivity(new Intent(SelectModeActivity.this, AboutGameMode.class));
        finish();
    }

    public void gotolearn(View view) {
       // float t = ratingBar.getRating();
       // int r = (int) t;

        editor.putString("mode", "Learning").commit();
        //Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SelectModeActivity.this, AboutLearningMode.class));
        //finish();

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
