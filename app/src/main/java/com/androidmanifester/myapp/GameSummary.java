package com.androidmanifester.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class GameSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_summary);
        ((TextView) findViewById(R.id.textView15)).setText(getIntent().getStringExtra("scr"));
    }

    public void gotohome(View view) {
        startActivity(new Intent(GameSummary.this, SelectModeActivity.class));

    }
    @Override
    public void onBackPressed() {

        new MaterialDialog.Builder(this)
                .title("Confirm Exit")
                .content("Are You Sure?")
                .positiveText("Exit")
                .negativeText("Home")
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
                        startActivity(new Intent(GameSummary.this, SelectModeActivity.class)); finish();
                 }
                })
                .show();

    }
}
