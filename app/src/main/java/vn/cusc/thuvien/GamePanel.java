package vn.cusc.thuvien;

import java.util.ArrayList;

import vn.cusc.cacdoituong.CaiRo;
import vn.cusc.cacdoituong.ConGa;
import vn.cusc.cacdoituong.Nen;
import vn.cusc.cacdoituong.TrungGa;
import vn.cusc.game2d.GameOver;
import vn.cusc.game2d.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public int touchX, touchY;

    public static int maxWidth;
    public static int maxHeight;

    public static int soTrungGa;
    public static int soTrungVang;
    public static int soConGa;
    public static int soPhanGa;

    public static int soDiem = 0;
    public static int capDo = 1;

    Game game;
    ArrayList<ConGa> conga = new ArrayList<ConGa>();
    CaiRo cairo;
    Nen nen;

    Paint p = new Paint();
    MediaPlayer player;

    public GamePanel(Context context) {
        super(context);
        p.setColor(Color.RED);
        p.setTextSize(40);

        player = MediaPlayer.create(context, R.raw.coin);

        // dang ky su kien tren surfaceview
        getHolder().addCallback(this);
        setFocusable(true);

        game = new Game(getHolder(), this);

        khoitaocacdoituong(context);
    }

    private void khoitaocacdoituong(Context context) {
        conga.add(new ConGa(getResources(), new Point(5, 0), new Point(80, 150), context));
        cairo = new CaiRo(getResources(), new Point(0, 0), new Point(GamePanel.maxWidth / 2, GamePanel.maxHeight - 240));
        nen = new Nen(getResources(), new Point(1, 1), new Point(1, 1));

        reset();
    }

    public void stop() {
        game.setRunning(false);
    }

    void reset() {
        soTrungGa = 0;
        soConGa = 0;
        soTrungVang = 0;
        soPhanGa = 0;
        soDiem = 0;
        capDo = 1;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nen.ve(canvas);

        for (int i = 0; i < conga.size(); i++) {
            conga.get(i).ve(canvas);
            for (int j = 0; j < conga.get(i).trung.size(); j++) {
                conga.get(i).trung.get(j).ve(canvas);
            }
        }

        cairo.ve(canvas);

        canvas.drawText("Level: " + capDo + " - " +
                (soTrungGa + soConGa + soTrungVang) + " trứng - " + soDiem + " điểm !", 20, 50, p);
    }

    public void capnhat() {
        for (int i = 0; i < conga.size(); i++) {
            conga.get(i).capnhat();
            if (conga.get(i).vantoc.x == 0) {
                stop();

                // kết thúc game
                Intent intent = new Intent(getContext(), GameOver.class);
                ((Activity) getContext()).startActivity(intent);
                ((Activity) getContext()).finish();

                break;
            }
        }

        cairo.capnhat();

        nen.capnhat();

        xetVaCham();
    }

    void xetVaCham() {
        for (ConGa ga : conga) {
            for (TrungGa tga : ga.trung) {
                // nếu dã vỡ kg xét tiếp
                if (tga.count < 30) continue;

                if (tga.hinh.getWidth() / 2 + cairo.hinh.getHeight() / 2 >=
                        Math.sqrt(Math.pow((tga.vitri.x + tga.hinh.getWidth() / 2) - (cairo.vitri.x + cairo.hinh.getWidth() / 2), 2) +
                                Math.pow((tga.vitri.y + tga.hinh.getWidth() / 2) - (cairo.vitri.y + cairo.hinh.getWidth() / 2), 2))) {
                    GamePanel.soDiem += tga.diem;
                    if (GamePanel.soDiem < 0) GamePanel.soDiem = 0;

                    // hứng phân gà bị rơi
                    if (tga.diem < 0)
                        ga.vitri.y += 50 * GamePanel.capDo;

                    // 10 điểm lên một cấp độ
                    GamePanel.capDo = (GamePanel.soDiem / 10) + 1;

                    player.start();

                    tga.vantoc.y = 0;
                    tga.trongro = true;
                    // điểm chỉ trừ đến 0
                    if (soDiem < 0) soDiem = 0;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int) event.getX();
        touchY = (int) event.getY();
        cairo.vitri.x = touchX - cairo.hinh.getWidth() / 2;

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        game.setRunning(true);
        game.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        game.setRunning(false);
    }

}
