package net.atlassian.teammyrec.writersblock;
import java.util.*;
import android.app.Activity;
import android.content.*;

/**
 * Created by matt on 2/1/16.
 */
public class RecoveryController {

    /*
     * saveState()
     * Description: Saves the state of the current activity to be restored later.
     * @params act - Current activity of app
     *         keyVals - Key-value pairs representing current state information.
     * @return void
     */
    public void saveState(Activity act, Map<String, String> keyVals) {
        SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (Map.Entry<String, String> entry : keyVals.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    /*
     * getLastState()
     * Description: Fetches all of the most recent key-value pairs representing the last
     * state of the app before closing.
     * @params act - Current activity of the app
     *         keys - Keys to use when fetching values
     * @return Map<String, String>: HashMap containing the retrieved key-value pairs
     */
    public Map<String, String> getLastState(Activity act, String[] keys) {
        SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
        Map<String, String> keyVals = new HashMap<String, String>();
        for(String key : keys) {
            String val = prefs.getString(key, ""); // If no value found, return empty string
            keyVals.put(key, val);
        }
        return keyVals;

    }
}


