package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.app.AlertDialog;
import android.util.Log;

import com.parse.Parse;

import net.atlassian.teammyrec.writersbloc.Interfaces.Deletable;
import net.atlassian.teammyrec.writersbloc.ParseController;

import java.io.File;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.content.*;

/**
 * Created by jay on 2/6/16.
 */
public class Category implements Deletable {

    private static final String DATAMODEL_PROJECT_LOG = "net.atlassian.teammyrec.writersbloc.models.datamodels.CATEGORY";
    private static final Logger PROJECT_LOGGER = Logger.getLogger(DATAMODEL_PROJECT_LOG);
    private static final String LOG_NEW_CATEGORY = "New category created: ";
    private static final String PAGE_EXCEPTION = "Encountered an exception while creating a page:";
    private static final String CREATE_EXCEPTION = "Encountered an exception while creating category: ";

    private String categoryName;
    private String projectName;
    private String owner;



    public Category(String categoryName, String owner, String projectName) {
        this.categoryName = categoryName;
        this.owner = owner;
        this.projectName = projectName;
    }


    public Project getProject(){
         return new Project(this.projectName, this.owner);
    }


    public Page addPage(String pageName){
        Page p = new Page(pageName, this.categoryName, this.projectName, this.owner);
        ParseController.createPage(pageName, categoryName, projectName, owner);
        return p;

    }

    public void removePage(String page){

    }

    public ArrayList<Page> getPages(){
        return ParseController.getAllPagesForCategory(this.categoryName, this.projectName);
    }

    @Override
    public void delete(){
        ParseController.deleteCategory(categoryName, projectName, owner);
    }

    public String toString(){
        return this.categoryName;
    }

}
