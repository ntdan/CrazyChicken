package vn.cusc.game2d;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import vn.cusc.thuvien.GamePanel;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GiaoDienChinh extends ActionBarActivity {

    GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        GamePanel.maxWidth = size.x;
        GamePanel.maxHeight = size.y;

        setContentView(R.layout.activity_giao_dien_chinh);

        gamePanel = new GamePanel(this);

        LinearLayout ln = (LinearLayout) findViewById(R.id.LinearLayout1);
        gamePanel = new GamePanel(this);

        AdView adView = (AdView) findViewById(R.id.adView);
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));

        // AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
        adView.loadAd(adRequest);

        // do phan gian man hinh 50dp -> px
        /*int pxSize =200;
        DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			pxSize = 38;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			pxSize = 50;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			pxSize = 75;
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			pxSize = 100;
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			pxSize = 150;
			break;
		case DisplayMetrics.DENSITY_XXXHIGH:
			pxSize = 200;
			break;
		}

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, pxSize); // You might want to tweak these
												// to WRAP_CONTENT
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		ln.addView(adView, lp);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		lp1.addRule(RelativeLayout.ABOVE, adView.getId());*/

        ln.addView(gamePanel, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giao_dien_chinh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gamePanel.stop();
        Log.d("Thread", "Stop");
    }
}
