package vn.cusc.game2d;

import vn.cusc.thuvien.GamePanel;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class GameOver extends ActionBarActivity {

    TextView tv;
    MediaPlayer play;
    Typeface font;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        play = MediaPlayer.create(this, R.raw.dance);
        play.setLooping(true);
        play.start();

        tv = (TextView) findViewById(R.id.tvPlay);
        font = Typeface.createFromAsset(
                this.getAssets(), "starcraft.ttf");
        tv.setTypeface(font);

        TextView tvlevel = (TextView) findViewById(R.id.tvLevel);
        tvlevel.setTypeface(font);

        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                play.stop();
                Intent i = new Intent(getApplicationContext(), GiaoDienChinh.class);
                startActivity(i);
                finish();
            }
        });

        loadAnimation();

        loadResult();
    }

    private void loadResult() {
        TextView tvTrungGa = (TextView) findViewById(R.id.tvTrungGa);
        tvTrungGa.setText(GamePanel.soTrungGa + "");
        tvTrungGa.setTypeface(font);
        TextView tvTrungVang = (TextView) findViewById(R.id.tvTrungVang);
        tvTrungVang.setText(GamePanel.soTrungVang + "");
        tvTrungVang.setTypeface(font);
        TextView tvConGa = (TextView) findViewById(R.id.tvConGa);
        tvConGa.setText(GamePanel.soConGa + "");
        tvConGa.setTypeface(font);
        TextView tvPhanGa = (TextView) findViewById(R.id.tvPhanGa);
        tvPhanGa.setText(GamePanel.soPhanGa + "");
        tvPhanGa.setTypeface(font);
        TextView tvResult = (TextView) findViewById(R.id.tvLevel);
        tvResult.setText("Level " + GamePanel.capDo + " - " + GamePanel.soDiem + " points!");
        tvResult.setTypeface(font);

        SharedPreferences spr = getSharedPreferences("HighScore", MODE_PRIVATE);
        final Editor edt = spr.edit();
        int level = spr.getInt("Level", 1);
        int point = spr.getInt("Point", 0);

        if (GamePanel.soDiem > point) {
            AlertDialog.Builder dialog = new Builder(GameOver.this);
            dialog.setTitle("High Score !");
            dialog.setIcon(R.drawable.conga);
            dialog.setMessage("You are high score !\n" + GamePanel.soDiem + " points!")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            // cap nhat
                            edt.putInt("Level", GamePanel.capDo);
                            edt.putInt("Point", GamePanel.soDiem);
                            edt.commit();
                        }
                    });
            dialog.create().show();
        }

        TextView tvHighScore = (TextView) findViewById(R.id.tvHighScore);
        tvHighScore.setText("Level " + level + " - " + point + " points!");
    }

    private void loadAnimation() {
        final ScaleAnimation al1 = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        al1.setFillAfter(true);
        al1.setFillEnabled(true);
        al1.setDuration(200);
        al1.setInterpolator(new OvershootInterpolator(6f));

        final ScaleAnimation al2 = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        al2.setFillAfter(true);
        al2.setFillEnabled(true);
        al2.setDuration(200);
        al2.setInterpolator(new OvershootInterpolator(6f));

        al2.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {            // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv.startAnimation(al1);
            }
        });

        al1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv.startAnimation(al2);
            }
        });

        tv.startAnimation(al1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        play.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        play.pause();
        super.onPause();
    }
}
