package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jay on 2/10/16.
 */
public class PageInformation {

    private static final String TITLE_KEY = "Title";
    private static final String TEXT_KEY = "Text";
    private static final String LINK_KEY = "Links";

    private String title;
    private String text;
    private String[] links;

    PageInformation(){
        title = null;
        text = "";
        links = null;
    }

    PageInformation(String title, String text, String[] links){
        this.title = title;
        this.text = text;
        this.links = links;
    }

    PageInformation(String title, String text){
        this(title, text, null);
    }

    PageInformation(JSONObject object) throws JSONException{
        if(object.length() > 0) {
            if(object.has(TITLE_KEY))
                title = (String) object.get(TITLE_KEY);
            if(object.has(TEXT_KEY))
                text = (String) object.get(TEXT_KEY);
            if(object.has(LINK_KEY))
                links = (String[]) object.get(LINK_KEY);
        }
    }

    public String toJSONToken() throws JSONException{
        JSONObject object = new JSONObject();
        object.put(TITLE_KEY, title);
        object.put(TEXT_KEY, text);
        object.put(LINK_KEY, links);
        return object.toString();
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public String[] getLinks() {
        return  links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }


}
