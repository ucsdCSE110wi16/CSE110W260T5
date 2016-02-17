package net.atlassian.teammyrec.writersbloc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import android.content.*;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


    }

    public void signup(View v) {
        EditText username = (EditText)findViewById(R.id.username_edit_activity_signup);
        EditText password = (EditText)findViewById(R.id.password_edit_activity_signup);
        EditText passConfirm = (EditText)findViewById(R.id.password_edit_activity_signup2);

        if(!password.getText().toString().equals(passConfirm.getText().toString())) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Incorrect Login Credentials");
            dlgAlert.setTitle("Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }

        ParseController.addUser(username.getText().toString(), password.getText().toString());
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(ProjectActivity.INTENT_EXTRA_USERNAME, username.getText().toString());
        intent.putExtra(ProjectActivity.INTENT_EXTRA_PASSWORD, password.getText().toString());
        this.startActivity(intent);

    }

}
