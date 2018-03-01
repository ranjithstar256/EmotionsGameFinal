package com.androidmanifester.myapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class LearningMode extends AppCompatActivity {
    List<String> Happiness; List<String> Sadness; List<String> Good;    List<String> Bad;    List<String> Weird;    List<String> Disgust;    List<String> Anger;    List<String> Anxiety;    ArrayList<String> All;    ArrayList<Integer> BalloonColor;        ArrayList<TextView> tvlist;    int height, width, score;    TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tvscore, tvcount;
    Integer[] imageId = {            R.drawable.happy,            R.drawable.sad,            R.drawable.good,            R.drawable.bad,            R.drawable.anger,            R.drawable.anxiety,            R.drawable.disgust,            R.drawable.weird,    };
    private SoundHelper mSoundHelper;    SharedPreferences.Editor editor;
    Handler handler;    Runnable runnable;    LinkedHashMap<String,String> map;    SharedPreferences sharedPreferences;    List<String> Emotions;    String SelectedWord;    TextView selectedEmotion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main );
        if (((getIntent().getIntExtra("ori", 1)) == 1)) {      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);        } else {            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        }
        Toast.makeText(this, "Learning mode", Toast.LENGTH_SHORT).show();
        DisplayMetrics displayMetrics = new DisplayMetrics();        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);    height = displayMetrics.heightPixels;        width = displayMetrics.widthPixels;
        mSoundHelper = new SoundHelper(this);        mSoundHelper.prepareMusicPlayer(this);        mSoundHelper.playMusic();
        tv0 = (TextView) findViewById(R.id.textView0);        tv1 = (TextView) findViewById(R.id.textView1);        tv2 = (TextView) findViewById(R.id.textView2);        tv3 = (TextView) findViewById(R.id.textView3);        tv4 = (TextView) findViewById(R.id.textView4);        tv5 = (TextView) findViewById(R.id.textView5);        tv6 = (TextView) findViewById(R.id.textView6);        tv7 = (TextView) findViewById(R.id.textView7);        tv8 = (TextView) findViewById(R.id.textView8);        tvcount = (TextView) findViewById(R.id.tvcountid);        tvscore = (TextView) findViewById(R.id.tvscoreid);       selectedEmotion = (TextView) findViewById(R.id.selectedEmo);
        tvlist = new ArrayList<TextView>();        BalloonColor = new ArrayList<Integer>();        All = new ArrayList<String>();
        sharedPreferences = getSharedPreferences("sfname", MODE_PRIVATE);        editor = sharedPreferences.edit();        SelectedWord = sharedPreferences.getString("selectedword", "None");        selectedEmotion.setText(SelectedWord);
        Emotions = new ArrayList<String >();        Happiness = new ArrayList<String>();        tvlist = new ArrayList<TextView>();        BalloonColor = new ArrayList<Integer>();        All = new ArrayList<String>();        Weird = new ArrayList<String>();        Bad = new ArrayList<String>();        Sadness = new ArrayList<String>();        Good = new ArrayList<String>();        Anger = new ArrayList<String>();        Anxiety = new ArrayList<String>();        Disgust = new ArrayList<String>();
        Emotions= Arrays.asList(getResources().getStringArray(R.array.Emotions));        Happiness=Arrays.asList(getResources().getStringArray(R.array.Happiness));        Sadness=Arrays.asList(getResources().getStringArray(R.array.Sadness));        Anger=Arrays.asList(getResources().getStringArray(R.array.Anger));        Disgust=Arrays.asList(getResources().getStringArray(R.array.Disgust));        Anxiety=Arrays.asList(getResources().getStringArray(R.array.Anxiety));        Weird=Arrays.asList(getResources().getStringArray(R.array.Weird));        Bad=Arrays.asList(getResources().getStringArray(R.array.Bad));        Good=Arrays.asList(getResources().getStringArray(R.array.Good));
        tvlist.add(tv4);        tvlist.add(tv1);        tvlist.add(tv0);        tvlist.add(tv6);        tvlist.add(tv2);        tvlist.add(tv3);        tvlist.add(tv8);        tvlist.add(tv5);        tvlist.add(tv7);

        String[] keys = this.getResources().getStringArray(R.array.words);
        String[] values = this.getResources().getStringArray(R.array.meanings);

        map = new LinkedHashMap<String,String>();
        for (int i = 0; i < Math.min(keys.length, values.length); ++i) {
            map.put(keys[i], values[i]);
            Log.d("words",keys[i]+"=-="+ values[i]);
           // All.add(keys[i]);
        }

        switch (SelectedWord){
            case "Happiness":              All.clear(); All.addAll(Happiness);                  break;
            case "Sadness":                All.clear(); All.addAll(Sadness);                    break;
            case "Good":                   All.clear(); All.addAll(Good);                       break;
            case "Bad":                    All.clear(); All.addAll(Bad);                       break;
            case "Anger":                  All.clear(); All.addAll(Anger);                        break;
            case "Anxiety":                All.clear(); All.addAll(Anxiety);                    break;
            case "Weird":                  All.clear(); All.addAll(Weird);                      break;
            case "Disgust":                All.clear(); All.addAll(Disgust);                    break;
            default:                        Toast.makeText(this, "Please select a Emotion", Toast.LENGTH_SHORT).show();                break;
        }

        BalloonColor.add(R.drawable.balloon_blue); BalloonColor.add(R.drawable.balloon_green); BalloonColor.add(R.drawable.balloon_orange);        BalloonColor.add(R.drawable.balloon_red);        BalloonColor.add(R.drawable.balloon_yellow);        BalloonColor.add(R.drawable.balloon_blue); BalloonColor.add(R.drawable.balloon_green); BalloonColor.add(R.drawable.balloon_orange); BalloonColor.add(R.drawable.balloon_red); BalloonColor.add(R.drawable.balloon_yellow);
        handler = new Handler();          runnable = new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(0).setGravity(Gravity.CENTER);                        tvlist.get(0).setBackgroundResource(BalloonColor.get(0));                        animat(tvlist.get(0), All.get(0));                    }
                }, 1000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(1).setGravity(Gravity.CENTER);                        tvlist.get(1).setBackgroundResource(BalloonColor.get(1));                        animat(tvlist.get(1), All.get(1));                    }
                }, 2000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(2).setGravity(Gravity.CENTER);                        tvlist.get(2).setBackgroundResource(BalloonColor.get(2));                        animat(tvlist.get(2), All.get(2));                    }
                }, 3000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(3).setGravity(Gravity.CENTER);                        tvlist.get(3).setBackgroundResource(BalloonColor.get(3));                        animat(tvlist.get(3), All.get(3));                    }
                }, 4000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(4).setGravity(Gravity.CENTER);                        tvlist.get(4).setBackgroundResource(BalloonColor.get(4));                        animat(tvlist.get(4), All.get(4));                    }
                }, 5000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(5).setGravity(Gravity.CENTER);                        tvlist.get(5).setBackgroundResource(BalloonColor.get(5));                        animat(tvlist.get(5), All.get(5));                    }
                }, 6000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(6).setGravity(Gravity.CENTER);                        tvlist.get(6).setBackgroundResource(BalloonColor.get(6));                        animat(tvlist.get(6), All.get(6));                    }
                }, 7000);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {                        tvlist.get(7).setGravity(Gravity.CENTER);                        tvlist.get(7).setBackgroundResource(BalloonColor.get(7));                        animat(tvlist.get(7), All.get(7));                    }
                }, 8000);
                Collections.shuffle(All);                Collections.shuffle(tvlist);                Collections.shuffle(BalloonColor);                handler.postDelayed(this, 11000);            }
        };        handler.postDelayed(runnable, 0);
    }

    public void animat(final TextView t, final String v) {
        // if(z==7){

        // } else {
        final ObjectAnimator mover = ObjectAnimator.ofFloat(t, "translationY", 0, -height);
        mover.setDuration(10000);        t.setText(v);        mover.start();
        mover.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mover.pause();
                String buttonText = (t.getText().toString());
                String val = map.get(buttonText);
                String buttonTextup = (t.getText().toString().toUpperCase());
                new MaterialDialog.Builder(LearningMode.this)
                        .iconRes(BalloonColor.get(2))
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title(Html.fromHtml("<b>" + buttonTextup + "</b>"))
                        .content(Html.fromHtml("<h5> "+val+"</h5>"))
                        .positiveText("Continue")
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mover.resume();
                            }
                        })

                        .show();

                //Toast.makeText(LearningMode.this, ""+val, Toast.LENGTH_SHORT).show();
               // t.setClickable(false);
            }
        });
        //}
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.words, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        final Dialog dialog = new Dialog(LearningMode.this);
        dialog.setContentView(R.layout.dia);

        ListView listView = (ListView) dialog.findViewById(R.id.lv);

        CustomList arrayAdapter = new CustomList(LearningMode.this, Emotions, imageId);

        //    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GameMode.this, android.R.layout.simple_spinner_dropdown_item, Emotions);
        listView.setChoiceMode(1);
        listView.setAdapter(arrayAdapter);
        dialog.setTitle("Select Word");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedWord = Emotions.get(position);
                editor.putString("selectedword", SelectedWord).commit();

                selectedEmotion.setText(SelectedWord);


                switch (SelectedWord){
                    case "Happiness":              All.clear(); All.addAll(Happiness);                  break;
                    case "Sadness":                All.clear(); All.addAll(Sadness);                    break;
                    case "Good":                   All.clear(); All.addAll(Good);                       break;
                    case "Bad":                    All.clear(); All.addAll(Bad);                        break;
                    case "Anger":                  All.clear(); All.addAll(Anger);                      break;
                    case "Anxiety":                All.clear(); All.addAll(Anxiety);                    break;
                    case "Weird":                  All.clear(); All.addAll(Weird);                      break;
                    case "Disgust":                All.clear(); All.addAll(Disgust);                    break;
                    default:                       Toast.makeText(LearningMode.this, "Please select a Emotion", Toast.LENGTH_SHORT).show();                break;
                }
                Toast.makeText(LearningMode.this, SelectedWord + " selected", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSoundHelper.stopMusic();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public void onBackPressed() {
        mSoundHelper.stopMusic();
        new MaterialDialog.Builder(this)
                .title("Confirm Exit")                .content("Are You Sure?")                .positiveText("Exit")                .negativeText("Cancel")                .neutralText("Home")                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {                        Intent intent = new Intent(Intent.ACTION_MAIN);                        intent.addCategory(Intent.CATEGORY_HOME);                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                        startActivity(intent);                        mSoundHelper.stopMusic();                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {                        dialog.dismiss();                        mSoundHelper.playMusic();                    }

                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {                         startActivity(new Intent(LearningMode.this, SelectModeActivity.class));                    }
                })                .show();
    }
    //        All.addAll(Good);
//        All.addAll(Bad);
//        All.addAll(Anger);
//        All.addAll(Anxiety);
//        All.addAll(Weird);
//        All.addAll(Disgust);
//        All.addAll(Sadness);
//        All.addAll(Happiness);

//
//        final Handler handler2 = new Handler();
//
//        final Runnable runnable2 = new Runnable() {
//            @Override
//            public void run() {
//                //Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                Collections.shuffle(BalloonColor, new Random(System.nanoTime()));
//                Collections.shuffle(All, new Random(System.nanoTime()));
//
//                int u = sharedPreferences.getInt("asdfaf", 0);//0
//                if (u < tvlist.size()) {
//                    // Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                    tvlist.get(u).setGravity(Gravity.CENTER);//0
//                    tvlist.get(u).setBackgroundResource(BalloonColor.get(0));//0
//                    animat(tvlist.get(u), All.get(0), sharedPreferences.getInt("ge", 7));//0
//
//                    u = u + 1;
//                    editor.putInt("asdfaf", u).commit();//1
//
//
//                } else {
//                    editor.putInt("asdfaf", 0).commit();
//                    Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                    tvlist.get(2).setBackgroundResource(BalloonColor.get(0));
//                    tvlist.get(2).setGravity(Gravity.CENTER);
//                    animat(tvlist.get(2), All.get(0), sharedPreferences.getInt("ge", 6));
//                }
//
//      /* and here comes the "trick" */
//                handler2.postDelayed(this, 3000);
//            }
//        };
//
//        handler2.postDelayed(runnable2, 1600);
//
//
//
//
//        final Handler handler3 = new Handler();
//
//        final Runnable runnable3 = new Runnable() {
//            @Override
//            public void run() {
//                //Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                Collections.shuffle(BalloonColor, new Random(System.nanoTime()));
//                Collections.shuffle(All, new Random(System.nanoTime()));
//
//                int u = sharedPreferences.getInt("asdfaf", 0);//0
//                if (u < tvlist.size()) {
//                    // Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                    tvlist.get(u).setGravity(Gravity.CENTER);//0
//                    tvlist.get(u).setBackgroundResource(BalloonColor.get(0));//0
//                    animat(tvlist.get(u), All.get(0), sharedPreferences.getInt("ge", 7));//0
//
//                    u = u + 1;
//                    editor.putInt("asdfaf", u).commit();//1
//
//
//                } else {
//                    editor.putInt("asdfaf", 0).commit();
//                    Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                    tvlist.get(2).setBackgroundResource(BalloonColor.get(0));
//                    tvlist.get(2).setGravity(Gravity.CENTER);
//                    animat(tvlist.get(2), All.get(0), sharedPreferences.getInt("ge", 6));
//                }
//
//      /* and here comes the "trick" */
//                handler3.postDelayed(this, 3000);
//            }
//        };
//
//        handler3.postDelayed(runnable3, 750);
//        final Timer t2 = new Timer();
//        t2.scheduleAtFixedRate(new TimerTask() {
//                                   @Override
//                                   public void run() {
//                                       runOnUiThread(new Runnable() {
//                                           @Override
//                                           public void run() {
//
//                                           }
//                                       });
//                                   }
//                               },//Set how long before to start calling the TimerTask (in milliseconds)
//                0,//Set the amount of time between each execution (in milliseconds)
//                2000);

    //Initialize a new CountDownTimer instance
    //     Weird = new ArrayList<String>();
    //     Bad = new ArrayList<String>();
    //     Sadness = new ArrayList<String>();
    //     Good = new ArrayList<String>();
    //     Anger = new ArrayList<String>();
    //    Anxiety = new ArrayList<String>();
    //    Disgust = new ArrayList<String>();

//        Emotions= Arrays.asList(getResources().getStringArray(R.array.Emotions));
//        Happiness=Arrays.asList(getResources().getStringArray(R.array.Happiness));
//        Sadness=Arrays.asList(getResources().getStringArray(R.array.Sadness));
//        Anger=Arrays.asList(getResources().getStringArray(R.array.Anger));
//        Disgust=Arrays.asList(getResources().getStringArray(R.array.Disgust));
//        Anxiety=Arrays.asList(getResources().getStringArray(R.array.Anxiety));
//        Weird=Arrays.asList(getResources().getStringArray(R.array.Weird));
//        Bad=Arrays.asList(getResources().getStringArray(R.array.Bad));
//        Good=Arrays.asList(getResources().getStringArray(R.array.Good));
//     Emotions = new ArrayList<String >();
    //    Happiness = new ArrayList<String>();
}