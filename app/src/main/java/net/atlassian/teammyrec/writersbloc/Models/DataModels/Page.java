package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.util.JsonToken;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by jay on 2/6/16.
 */
public class Page {
    private File baseFile;
    public Page(String absolutePageName) throws Exception{
        baseFile = new File(absolutePageName);
        if(!baseFile.exists()){
            if(!baseFile.createNewFile())
                throw new Exception("Invalid IO error");
        }
    }

    public Page(String parent, String pageName) throws Exception{
        this(parent + "/" + pageName);
    }

    public Category getCategory(){
        return null;
    }

    public String toString(){
        return baseFile.getName();
    }

    public PageInformation getPageInformation() throws Exception{
        FileReader reader = new FileReader(baseFile);
        char[] buffer = new char[(int)baseFile.length()];
        reader.read(buffer);
        String jsonToken = new String(buffer);
        JSONObject object = new JSONObject(jsonToken.length()> 0 ? jsonToken : "{}");
        reader.close();
        return new PageInformation(object);
    }

    public void writePageInformation(PageInformation info) throws Exception{
        FileWriter writer = new FileWriter(baseFile);
        writer.write(info.toJSONToken());
        writer.close();
    }

    public String getAbsolutePath(){
        return baseFile.getAbsolutePath();
    }
}
