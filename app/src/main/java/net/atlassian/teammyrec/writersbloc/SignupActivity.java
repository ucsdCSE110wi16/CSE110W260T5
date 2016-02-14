package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
        setContentView(R.layout.activity_signup);


    }

    public void signup(View v) {
        EditText username = (EditText)findViewById(R.id.username_edit_activity_signup);
        EditText password = (EditText)findViewById(R.id.password_edit_activity_signup);
        EditText passConfirm = (EditText)findViewById(R.id.password_edit_activity_signup2);

        if(!password.getText().toString().equals(passConfirm.getText().toString())) {
            return;
        }

        // @todo: username validation (no weird characters, spaces..)
        ParseController.addUser(username.getText().toString(), password.getText().toString());

    }

}
