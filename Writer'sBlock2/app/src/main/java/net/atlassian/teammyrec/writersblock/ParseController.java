package net.atlassian.teammyrec.writersblock;

import com.parse.*;

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
