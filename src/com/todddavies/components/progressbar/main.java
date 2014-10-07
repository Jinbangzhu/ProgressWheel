package com.todddavies.components.progressbar;

import android.app.Activity;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * A sample activity showing some of the functions of the progress bar
 */
public class main extends Activity {
    boolean running;
    ProgressWheel pw_two;
    //ProgressWheel pw_five;
    float progress = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_wheel_activity);
        pw_two = (ProgressWheel) findViewById(R.id.progressBarTwo);
        final TextView tvProgress = (TextView) findViewById(R.id.tv_progress);
        //pw_five = (ProgressWheel) findViewById(R.id.progressBarFive);

        final Runnable r = new Runnable() {
            public void run() {
                running = true;
                while (progress < 75*3.6) {
                    pw_two.incrementProgress();
                    progress+=0.3f;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvProgress.setText((int)(progress/360f*100)+"");
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                running = false;
            }
        };

        Button spin = (Button) findViewById(R.id.btn_spin);
        spin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!running) {
                    if (pw_two.isSpinning) {
                        pw_two.stopSpinning();
                    }
                    pw_two.resetCount();
                    pw_two.setText("Loading...");
                    pw_two.spin();
                }
            }
        });

        Button increment = (Button) findViewById(R.id.btn_increment);
        increment.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!running) {
                    progress = 0;
                    pw_two.resetCount();
                    Thread s = new Thread(r);
                    s.start();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        progress = 361;
        pw_two.stopSpinning();
        pw_two.resetCount();
        pw_two.setText("Click\none of the\nbuttons");
    }
}
