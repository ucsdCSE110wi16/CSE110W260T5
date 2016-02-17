package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
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

    }

    public void goToProjects(View v){
        // Grab data from view
        EditText userName = (EditText)findViewById(R.id.username_edit_activity_login);
        EditText password = (EditText)findViewById(R.id.password_edit_activity_login);

        // Create intent for ProjectActivity, and go to ProjectActivity
        try {
            ParseUser validUser = ParseUser.logIn(userName.getText().toString(),
                    password.getText().toString());
            if(validUser != null) {
                Intent intent = new Intent(this, ProjectActivity.class);

                intent.putExtra(ProjectActivity.INTENT_EXTRA_USERNAME,
                        userName.getText().toString());
                intent.putExtra(ProjectActivity.INTENT_EXTRA_PASSWORD,
                        password.getText().toString());
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
