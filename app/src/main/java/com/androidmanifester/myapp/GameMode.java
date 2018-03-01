package com.androidmanifester.myapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

public class GameMode extends AppCompatActivity {

    TextView selectedEmotion;
    List<String> Emotions;
    List<String> Happiness;
    List<String> Sadness;
    List<String> Good;
    List<String> Bad;
    List<String> Weird;
    List<String> Disgust;
    List<String> Anger;
    List<String> Anxiety;
    ArrayList<String> All;
    ArrayList<Integer> BalloonColor;
    ArrayList<TextView> tvlist;
    int height, width, score;
    TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tvscore, tvcount;
    String SelectedWord;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SoundHelper mSoundHelper;
    //Declare a variable to hold count down timer's paused status
    private boolean isPaused = false;
    private boolean firsttime = true;
    //Declare a variable to hold count down timer's paused status
    private boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private long timeRemaining = 0;
    Handler handler;
    Runnable runnable;
    CountDownTimer timer;
    long millisInFuture = 120000; //30 seconds
    long countDownInterval = 1000; //1 second

            Integer[] imageId = {
                    R.drawable.happy,
                    R.drawable.sad,
                    R.drawable.good,
                    R.drawable.bad,
                    R.drawable.anger,
                    R.drawable.anxiety,
                    R.drawable.disgust,
                    R.drawable.weird,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main );
        if (((getIntent().getIntExtra("ori", 1)) == 1)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        firsttime=true;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        mSoundHelper = new SoundHelper(this);
        mSoundHelper.prepareMusicPlayer(this);
        mSoundHelper.playMusic();
        tv0 = (TextView) findViewById(R.id.textView0);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        tv7 = (TextView) findViewById(R.id.textView7);
        tv8 = (TextView) findViewById(R.id.textView8);
        selectedEmotion = (TextView) findViewById(R.id.selectedEmo);
        tvcount = (TextView) findViewById(R.id.tvcountid);
        tvscore = (TextView) findViewById(R.id.tvscoreid);

        sharedPreferences = getSharedPreferences("sfname", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        SelectedWord = sharedPreferences.getString("selectedword", "None");
        selectedEmotion.setText(SelectedWord);

        Emotions = new ArrayList<String >();
        Happiness = new ArrayList<String>();
        tvlist = new ArrayList<TextView>();
        BalloonColor = new ArrayList<Integer>();
        All = new ArrayList<String>();
        Weird = new ArrayList<String>();
        Bad = new ArrayList<String>();
        Sadness = new ArrayList<String>();
        Good = new ArrayList<String>();
        Anger = new ArrayList<String>();
        Anxiety = new ArrayList<String>();
        Disgust = new ArrayList<String>();

        Emotions= Arrays.asList(getResources().getStringArray(R.array.Emotions));
        Happiness=Arrays.asList(getResources().getStringArray(R.array.Happiness));
        Sadness=Arrays.asList(getResources().getStringArray(R.array.Sadness));
        Anger=Arrays.asList(getResources().getStringArray(R.array.Anger));
        Disgust=Arrays.asList(getResources().getStringArray(R.array.Disgust));
        Anxiety=Arrays.asList(getResources().getStringArray(R.array.Anxiety));
        Weird=Arrays.asList(getResources().getStringArray(R.array.Weird));
        Bad=Arrays.asList(getResources().getStringArray(R.array.Bad));
        Good=Arrays.asList(getResources().getStringArray(R.array.Good));

        tvlist.add(tv4);
        tvlist.add(tv1);
        tvlist.add(tv0);
        tvlist.add(tv6);
        tvlist.add(tv2);
        tvlist.add(tv3);
        tvlist.add(tv8);
        tvlist.add(tv5);
        tvlist.add(tv7);

        All.addAll(Good);
        All.addAll(Bad);
        All.addAll(Anger);
        All.addAll(Anxiety);
        All.addAll(Weird);
        All.addAll(Disgust);
        All.addAll(Sadness);
        All.addAll(Happiness);

        BalloonColor.add(R.drawable.balloon_blue);
        BalloonColor.add(R.drawable.balloon_green);
        BalloonColor.add(R.drawable.balloon_orange);
        BalloonColor.add(R.drawable.balloon_red);
        BalloonColor.add(R.drawable.balloon_yellow);
        BalloonColor.add(R.drawable.balloon_blue);
        BalloonColor.add(R.drawable.balloon_green);
        BalloonColor.add(R.drawable.balloon_orange);
        BalloonColor.add(R.drawable.balloon_red);
        BalloonColor.add(R.drawable.balloon_yellow);

          handler = new Handler();

          runnable = new Runnable() {
            @Override
            public void run() {

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(0).setGravity(Gravity.CENTER);
                        tvlist.get(0).setBackgroundResource(BalloonColor.get(0));
                        animat(tvlist.get(0), All.get(0));
                    }
                }, 1000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(1).setGravity(Gravity.CENTER);
                        tvlist.get(1).setBackgroundResource(BalloonColor.get(1));
                        animat(tvlist.get(1), All.get(1));
                    }
                }, 2000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(2).setGravity(Gravity.CENTER);
                        tvlist.get(2).setBackgroundResource(BalloonColor.get(2));
                        animat(tvlist.get(2), All.get(2));
                    }
                }, 3000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(3).setGravity(Gravity.CENTER);
                        tvlist.get(3).setBackgroundResource(BalloonColor.get(3));
                        animat(tvlist.get(3), All.get(3));
                    }
                }, 4000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(4).setGravity(Gravity.CENTER);
                        tvlist.get(4).setBackgroundResource(BalloonColor.get(4));
                        animat(tvlist.get(4), All.get(4));
                    }
                }, 5000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(5).setGravity(Gravity.CENTER);
                        tvlist.get(5).setBackgroundResource(BalloonColor.get(5));
                        animat(tvlist.get(5), All.get(5));
                    }
                }, 6000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(6).setGravity(Gravity.CENTER);
                        tvlist.get(6).setBackgroundResource(BalloonColor.get(6));
                        animat(tvlist.get(6), All.get(6));
                    }
                }, 7000);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvlist.get(7).setGravity(Gravity.CENTER);
                        tvlist.get(7).setBackgroundResource(BalloonColor.get(7));
                        animat(tvlist.get(7), All.get(7));
                    }
                }, 8000);
                Collections.shuffle(All);
                Collections.shuffle(tvlist);
                Collections.shuffle(BalloonColor);
                handler.postDelayed(this, 11000);
            }
        };

        handler.postDelayed(runnable, 0);

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
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //do something in every tick
                if (isPaused || isCanceled) {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                } else {
                    //Display the remaining seconds to app interface
                    //1 second = 1000 milliseconds
                    tvcount.setText("" + millisUntilFinished / 1000);
                    //Put count down timer remaining time in a variable
                    timeRemaining = millisUntilFinished;
                }
            }

            public void onFinish() {
                tvcount.setText("Time's Up!");
                handler.removeCallbacks(runnable);
                //  handler2.removeCallbacks(runnable2);

                AlertDialog.Builder dialog = new AlertDialog.Builder(GameMode.this);
                dialog.setMessage("Time's Up!" + "" +
                        "\n" + "Your Score " + tvscore.getText().toString());
                dialog.setIcon(R.drawable.happy);
                dialog.setTitle("Good Job!");
                mSoundHelper.stopMusic();

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(GameMode.this, GameSummary.class).putExtra("scr", "Your Score " + tvscore.getText().toString()));
                    }
                });
                dialog.setCancelable(false);
                dialog.show();

            }
        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundHelper.stopMusic();
        isPaused = true;
        editor.putInt("asdfaf", 0).commit();
        Log.d("abcdeonpause", "" + sharedPreferences.getInt("COUNT", 0));
        firsttime=false;

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (firsttime) {

        } else {
            mSoundHelper.playMusic();
            isPaused = false;
            isCanceled = false;

            //Initialize a new CountDownTimer instance
            long millisInFuture = timeRemaining;
            long countDownInterval = 1000;
            new CountDownTimer(millisInFuture, countDownInterval) {
                public void onTick(long millisUntilFinished) {
                    //Do something in every tick
                    if (isPaused || isCanceled) {
                        //If user requested to pause or cancel the count down timer
                        cancel();
                    } else {
                        tvcount.setText("" + millisUntilFinished / 1000);
                        //Put count down timer remaining time in a variable
                        timeRemaining = millisUntilFinished;
                    }
                }

                public void onFinish() {
                    //Do something when count down finished
                    tvcount.setText("Time's Up!");
                    //handler.removeCallbacks(runnable);
                    //Disable the pause, resume and cancel button

                    handler.removeCallbacks(runnable);
                    //  handler2.removeCallbacks(runnable2);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(GameMode.this);
                    dialog.setMessage("Time's Up!" + "" +
                            "\n" + "Your Score " + tvscore.getText().toString());
                    dialog.setIcon(R.drawable.happy);
                    dialog.setTitle("Good Job!");
                    mSoundHelper.stopMusic();

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(GameMode.this, GameSummary.class).putExtra("scr", "Your Score " + tvscore.getText().toString()));
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }.start();
        }
    }
    @Override
    public void onBackPressed() {
        mSoundHelper.stopMusic();
        isPaused = true;

        new MaterialDialog.Builder(this)
                .title("Confirm Exit")
                .content("Are You Sure?")
                .positiveText("Exit")
                .neutralText("Home")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                        mSoundHelper.stopMusic();
                        isCanceled = true;

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
                        mSoundHelper.playMusic();
                        isPaused = false;
                        isCanceled = false;

                        //Initialize a new CountDownTimer instance
                        long millisInFuture = timeRemaining;
                        long countDownInterval = 1000;
                        new CountDownTimer(millisInFuture, countDownInterval) {
                            public void onTick(long millisUntilFinished) {
                                //Do something in every tick
                                if (isPaused || isCanceled) {
                                    //If user requested to pause or cancel the count down timer
                                    cancel();
                                } else {
                                    tvcount.setText("" + millisUntilFinished / 1000);
                                    //Put count down timer remaining time in a variable
                                    timeRemaining = millisUntilFinished;
                                }
                            }

                            public void onFinish() {
                                //Do something when count down finished
                                tvcount.setText("Time's Up!");
                                //handler.removeCallbacks(runnable);
                                //Disable the pause, resume and cancel button

                                handler.removeCallbacks(runnable);
                                //  handler2.removeCallbacks(runnable2);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(GameMode.this);
                                dialog.setMessage("Time's Up!" + "" +
                                        "\n" + "Your Score " + tvscore.getText().toString());
                                dialog.setIcon(R.drawable.happy);
                                dialog.setTitle("Good Job!");
                                mSoundHelper.stopMusic();

                                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(GameMode.this, GameSummary.class).putExtra("scr", "Your Score " + tvscore.getText().toString()));
                                    }
                                });
                                dialog.setCancelable(false);
                                dialog.show();
                            }
                        }.start();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(GameMode.this, SelectModeActivity.class));
                    }
                })
                .show();
    }

    public void animat(final TextView t, final String v) {
        // if(z==7){

        // } else {
        final ObjectAnimator mover = ObjectAnimator.ofFloat(t, "translationY", 0, -height);

        switch (sharedPreferences.getInt("skilevel", 1)) {

            case 0:
                mover.setDuration(10000);
                break;
            case 1:
                mover.setDuration(10000);
                break;
            case 2:
                mover.setDuration(10000);
                break;
            case 3:
                mover.setDuration(10000);
                break;
            case 4:
                mover.setDuration(10000);
                break;
            case 5:
                mover.setDuration(10000);
                break;
            default:
                mover.setDuration(10000);
                break;
        }
        mover.setDuration(10000);

        t.setText(v);
        mover.start();
        mover.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                editor.putInt("ge", 7).commit();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                editor.putInt("ge", 6).commit();
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
                String buttonText = t.getText().toString();
                //Toast.makeText(GameMode.this, ""+buttonText, Toast.LENGTH_SHORT).show();
                boolean state;
                switch (SelectedWord) {
                    case "Happiness":
                        state = Happiness.contains(buttonText);
                        break;
                    case "Sadness":
                        state = Sadness.contains(buttonText);
                        break;
                    case "Good":
                        state = Good.contains(buttonText);
                        break;
                    case "Bad":
                        state = Bad.contains(buttonText);
                        break;
                    case "Weird":
                        state = Weird.contains(buttonText);
                        break;
                    case "Disgust":
                        state = Disgust.contains(buttonText);
                        break;
                    case "Anger":
                        state = Anger.contains(buttonText);
                        break;
                    case "Anxiety":
                        state = Anxiety.contains(buttonText);
                        break;
                    default:
                        state = false;
                        break;
                }
                if (state) {
                    score = score + 10;
                    tvscore.setText(score + "");
                    mSoundHelper.playSound(t, 0);
                    mover.end();
                } else {
                    score = score - 10;
                    tvscore.setText(score + "");
                    mSoundHelper.playSound(t, 1);
                    mover.end();
                }
                t.setClickable(false);
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
        final Dialog dialog = new Dialog(GameMode.this);
        dialog.setContentView(R.layout.dia);

        ListView listView = (ListView) dialog.findViewById(R.id.lv);

        CustomList arrayAdapter = new CustomList(GameMode.this, Emotions, imageId);

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

                Toast.makeText(GameMode.this, SelectedWord + " selected", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        return super.onOptionsItemSelected(item);
    }
        /*new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvcount.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvcount.setText("Time's Up!");
                handler.removeCallbacks(runnable);
              //  handler2.removeCallbacks(runnable2);

                AlertDialog.Builder dialog = new AlertDialog.Builder(GameMode.this);
                dialog.setMessage("Time's Up!" + "" +
                        "\n" + "Your Score " + tvscore.getText().toString());
                dialog.setIcon(R.drawable.happy);
                dialog.setTitle("Good Job!");
                mSoundHelper.stopMusic();

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(GameMode.this, GameSummary.class).putExtra("scr", "Your Score " + tvscore.getText().toString()));
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        }.start();*/
//
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.context_menu, menu);
//    }
//
//    public boolean onContextItemSelected(MenuItem item) {
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        String[] names = getResources().getStringArray(R.array.states);
//        switch (item.getItemId()) {
//            case R.id.edit:
//                Toast.makeText(this, "You have chosen the " + "iki" +
//                                " context menu option for " + names[(int) info.id],
//                        Toast.LENGTH_SHORT).show();
//                return true;
//
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }


    // int f = sharedPreferences.getInt("it",0);
    //    Log.d("abcdet1enternce",""+sharedPreferences.getInt("COUNT",0));
    //  if(sharedPreferences.getInt("COUNT",0)<tvs.size()){
    // tvs.get(sharedPreferences.getInt("COUNT",0)).setGravity(Gravity.CENTER);
    // tvs.get(sharedPreferences.getInt("COUNT",0)).setBackgroundResource(BalloonColor.get(0));
    //Collections.shuffle(BalloonColor, new Random(System.nanoTime()));
    // tvs.get(now).setGravity(Gravity.CENTER);
    // tvs.get(now).setBackgroundResource(BalloonColor.get(0));
    // Collections.shuffle(All, new Random(System.nanoTime()));
    ///og.d("abcdeanimating numer",""+sharedPreferences.getInt("COUNT",0));

    //animat(tvs.get(sharedPreferences.getInt("COUNT",0)), All.get(0));
    // int nr=sharedPreferences.getInt("COUNT",0)+1;
    //    Log.d("abcdeadded+1?",""+nr);

    //   editor.putInt("COUNT",nr).commit();
    //   int cv=tvs.size()-1;
    //  Log.d("abcdecv size",""+cv);

    // if(nr==cv){

    //      Collections.shuffle(tvs, new Random(System.nanoTime()));
    //   editor.putInt("COUNT",0).commit();
    //       Log.d("abcdechanged?",""+sharedPreferences.getInt("COUNT",0));

    //  }
    //  }
    //  else {
    //      Collections.shuffle(tvs, new Random(System.nanoTime()));
    ////      editor.putInt("COUNT",0).commit();
    //      Log.d("abcderesetted?",""+sharedPreferences.getInt("COUNT",0));

    //animat(tvs.get(sharedPreferences.getInt("chnAis",0)), All.get(0));
    //  }
    ///if (!mover.isRunning()){
    ///  Toast.makeText(this, "runing", Toast.LENGTH_SHORT).show();
    ///}else {}
    /// //                    case 0:
//                        mover.setDuration(10000);
//                        break;
//                    case 1:
//                        mover.setDuration(9000);
//                        break;
//                    case 2:
//                        mover.setDuration(8000);
//                        break;
//                    case 3:
//                        mover.setDuration(7000);
//                        break;
//                    case 4:
//                        mover.setDuration(6000);
//                        break;
//                    case 5:
//                        mover.setDuration(5000);
//                        break;
//                    default:
//                        mover.setDuration(5000);
//                        break;
    //	unhappy, sorrowful,
    // dejected, regretful, depressed, downcast,
    //contented, content, cheerful,
    // cheery, merry, joyful
    //	fine, of high quality, of a high standard, quality, superior
    //	substandard, poor, inferior, second-rate, second-class, unsatisfactory, inadequate, unacceptable,
    // not up to scratch, not up to par,
    // deficient, imperfect, defective, faulty, shoddy, amateurish, careless, negligent
    //	uncanny, eerie, unnatural, preternatural, supernatural, unearthly,
    // other-worldly, unreal, ghostly, mysterious



    //Collections.shuffle(tvlist, new Random(System.nanoTime()));
//                Collections.shuffle(BalloonColor, new Random(System.nanoTime()));
//                Collections.shuffle(All, new Random(System.nanoTime()));
//
//    int u = sharedPreferences.getInt("asdfaf", 0);//0
//                if (u < tvlist.size()) {
//        // Collections.shuffle(tvlist, new Random(System.nanoTime()));
//        tvlist.get(u).setGravity(Gravity.CENTER);//0
//        tvlist.get(u).setBackgroundResource(BalloonColor.get(0));//0
//        animat(tvlist.get(u), All.get(0), sharedPreferences.getInt("ge", 7));//0
//
//        //u = ++u;
//        editor.putInt("asdfaf", u).commit();//1
//
//
//    } else {
//        editor.putInt("asdfaf", 0).commit();
//        Collections.shuffle(tvlist, new Random(System.nanoTime()));
//        tvlist.get(2).setBackgroundResource(BalloonColor.get(0));
//        tvlist.get(2).setGravity(Gravity.CENTER);
//        animat(tvlist.get(2), All.get(0), sharedPreferences.getInt("ge", 6));
//    }

}