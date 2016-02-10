package net.atlassian.teammyrec.writersbloc;

/**
 * Created by dheerajgottipalli on 2/10/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(6000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splash.this,Splash.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}