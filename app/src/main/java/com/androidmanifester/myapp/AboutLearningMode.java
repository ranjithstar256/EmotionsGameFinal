package com.androidmanifester.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class AboutLearningMode extends AppCompatActivity {
    int orientatn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_learning_mode);
        sharedPreferences = getSharedPreferences("sfname", MODE_PRIVATE);
        orientatn = 1;
        editor = sharedPreferences.edit();
    }

    public void gotogame(View view) {
        startActivity(new Intent(AboutLearningMode.this, SelectWordActivity.class));
        finish();


       // startActivity(new Intent(AboutLearningMode.this, LearningMode.class));
    }
    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("Confirm Exit")
                .content("Are You Sure?")
                 .positiveText("Exit")
                .neutralText("Home")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();

                        //Initialize a new CountDownTimer instance

                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                        startActivity(new Intent(AboutLearningMode.this, SelectModeActivity.class));


                    }
                })
                .show();
        // startActivity(new Intent(AboutGameMode.this, SelectWordActivity.class));
    }
}