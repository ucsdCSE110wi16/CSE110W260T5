package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.util.JsonToken;

import net.atlassian.teammyrec.writersbloc.Interfaces.Deletable;
import net.atlassian.teammyrec.writersbloc.ParseController;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import android.content.*;

/**
 * Created by jay on 2/6/16.
 */


public class Page implements Deletable {
    private String categoryName;
    private String projectName;
    private String owner;
    private String pageName;


    public Page(String pageName, String categoryName, String projectName, String owner) {
        this.pageName = pageName;
        this.categoryName = categoryName;
        this.projectName = projectName;
        this.owner = owner;
    }

    public Category getCategory(){
        return new Category(this.categoryName, this.owner, this.projectName);
    }

    public String toString(){
        return this.pageName;
    }


    @Override
    public void delete(){
        ParseController.deletePage(pageName, categoryName, projectName, owner);
    }


}
