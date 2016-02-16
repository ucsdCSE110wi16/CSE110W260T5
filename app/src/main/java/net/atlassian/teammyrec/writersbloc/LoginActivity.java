package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.parse.*;
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Parse.initialize(this);
    }

    public void goToProjects(View v){
        // Grab data from view
        EditText userName = (EditText)findViewById(R.id.username_edit_activity_login);
        EditText password = (EditText)findViewById(R.id.password_edit_activity_login);

        // Create intent for ProjectActivity, and go to ProjectActivity
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(ProjectActivity.INTENT_EXTRA_USERNAME, userName.getText().toString());
        intent.putExtra(ProjectActivity.INTENT_EXTRA_PASSWORD, password.getText().toString());
        this.startActivity(intent);
    }


    public void createAccount(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }
}
