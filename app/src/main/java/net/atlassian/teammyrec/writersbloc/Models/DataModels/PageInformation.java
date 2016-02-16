package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jay on 2/10/16.
 */
public class PageInformation {

    private static final String TITLE_KEY = "Title";
    private static final String TEXT_KEY = "Text";

    private String title;
    private String text;

    PageInformation(){
        title = null;
        text = null;
    }

    PageInformation(String title, String text){
        this.title = title;
        this.text = text;
    }

    PageInformation(JSONObject object) throws JSONException{
        if(object.length() > 0) {
            title = (String) object.get(TITLE_KEY);
            text = (String) object.get(TEXT_KEY);
        }
    }

    public String toJSONToken() throws JSONException{
        JSONObject object = new JSONObject();
        object.put(TITLE_KEY, title);
        object.put(TEXT_KEY, text);
        return object.toString();
    }


}
