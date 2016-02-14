package net.atlassian.teammyrec.writersbloc;

/**
 * Created by dheerajgottipalli on 2/10/16.
 */

import android.util.Log;

import com.parse.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by matt on 2/10/16.
 */
public class ParseController {

    public static void addUser(String username, String password) {

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    //
                } else {
                    // error
                    System.out.println(e.toString());
                    System.out.println("error signing up");
                    Logger.getLogger("ok").log(Level.INFO, "Error signing up");
                }
            }
        });

    }

    public static boolean checkLogin(String username, String password) {
        try {
            ParseUser user = ParseUser.logIn(username, password);
            if(user != null) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}