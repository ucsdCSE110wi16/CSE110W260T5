package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.util.Log;

import java.io.File;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jay on 2/6/16.
 */
public class Category {

    private static final String DATAMODEL_PROJECT_LOG = "net.atlassian.teammyrec.writersbloc.models.datamodels.CATEGORY";
    private static final Logger PROJECT_LOGGER = Logger.getLogger(DATAMODEL_PROJECT_LOG);
    private static final String LOG_NEW_CATEGORY = "New category created: ";
    private static final String PAGE_EXCEPTION = "Encountered an exception while creating a page:";
    private static final String CREATE_EXCEPTION = "Encountered an exception while creating category: ";

    private final File categoryFile;

    public Category(String parentFolder, String categoryName) throws Exception{
        this(parentFolder + "/" + categoryName);
        PROJECT_LOGGER.log(Level.INFO, LOG_NEW_CATEGORY + categoryName );
    }


    public Category(String absoluteName) throws Exception{
        // Create file
        categoryFile = new File(absoluteName);

        if(!categoryFile.exists())
        {
            if(!categoryFile.mkdir()) {
                Exception e = new Exception("Unhandled IO Exception");
                PROJECT_LOGGER.log(Level.SEVERE, CREATE_EXCEPTION + e);
                throw e;
            }
        }
    }

    public Project getProject(){
        try{
            return new Project(categoryFile.getParent());
        } catch (Exception e){
            return null;
        }
    }

    public File[] getFiles(){
        return categoryFile.listFiles();
    }

    public String getAbsolutePath(){
        return categoryFile.getAbsolutePath();
    }

    public Page addPage(String pageName){
        try {
            return new Page(categoryFile.getAbsolutePath(), pageName);
        } catch (Exception e){
            PROJECT_LOGGER.log(Level.SEVERE, PAGE_EXCEPTION + e);
            return null;
        }
    }

    public void removePage(String page){
        File file = new File(categoryFile.getAbsolutePath() + "/" + page);
        if(file.exists()){
            file.delete();
        }
    }

    public ArrayList<Page> getPages(){
        ArrayList<Page> pages = new ArrayList<Page>();
        try {
            for (File f : categoryFile.listFiles()) {
                pages.add(new Page(f.getAbsolutePath()));
            }
        } catch (Exception e){
            Logger.getAnonymousLogger().log(Level.WARNING, "Error handling page creation");
        }
        return pages;
    }

    public String toString(){
        return categoryFile.getName();
    }

}
