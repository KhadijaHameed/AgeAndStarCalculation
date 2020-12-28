package com.abdulwaheed.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplasScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 700;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplasScreen.this,MainActivity.class);
                SplasScreen.this.startActivity(mainIntent);
                SplasScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
