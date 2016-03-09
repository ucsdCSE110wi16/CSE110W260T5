package net.atlassian.teammyrec.writersbloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.*;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.parse.*;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;
import android.widget.*;
import android.app.*;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start thread to run the Tutorial
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                //Helps track number of times tutorial has run
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                if (isFirstStart) {
                    Intent i = new Intent(LoginActivity.this, OnboardTutorial.class);
                    startActivity(i);

                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();

        setContentView(R.layout.activity_login);
        try {
            Parse.initialize(this);
        } catch(Exception e) {
        }

        if(ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(this, ProjectActivity.class);
            intent.putExtra(ProjectActivity.INTENT_EXTRA_USERNAME,
                    ParseUser.getCurrentUser().getUsername());
            this.startActivity(intent);
        }


        this.setTheme(R.style.AppThemeLogin);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setUpVideo();
    }

    private void setUpVideo(){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.login_video);

        VideoView videoView = (VideoView)findViewById(R.id.backgroundVideo);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        // Adjust the size of the video
        // so it fits on the screen
        float videoProportion = getVideoProportion();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float screenProportion = (float) screenHeight / (float) screenWidth;
        android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();

        if (videoProportion < screenProportion) {
            lp.height= screenHeight;
            lp.width = (int) ((float) screenHeight / videoProportion);
        } else {
            lp.width = screenWidth;
            lp.height = (int) ((float) screenWidth * videoProportion);
        }

        videoView.setLayoutParams(lp);

        videoView.start();
    }

    private float getVideoProportion(){
        return 336/596f;
    }

    public void goToProjects(View v){
        // Grab data from view
        EditText userName = (EditText)findViewById(R.id.userNameEditLogin);
        EditText password = (EditText)findViewById(R.id.passwordEditLogin);

        // Create intent for ProjectActivity, and go to ProjectActivity
        try {
            ParseUser validUser = ParseUser.logIn(userName.getText().toString(),
                    password.getText().toString());
            if(validUser != null) {
                Intent intent = new Intent(this, ProjectActivity.class);

                intent.putExtra(ProjectActivity.INTENT_EXTRA_USERNAME,
                        userName.getText().toString());
                this.startActivity(intent);
            } else {
                // invalid password
                Toast.makeText(this, "Incorrect login credentials",
                        Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            e.printStackTrace();
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Incorrect Login Credentials");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }


    public void createAccount(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    public void logout(View v){
        ParseController.logoutCurrentUser();

    }
}
