package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 * Created by jay on 2/10/16.
 */
public class Project {

    private File projectFile;
    public Project(String absoluteProjectName) throws Exception{
        projectFile = new File(absoluteProjectName);

        if(!projectFile.exists())
        {
            if(!projectFile.mkdir()){
                throw new Exception("Unexpected IO Error");
            }
        }
    }

    public Project(Context context, String projectName) throws Exception{
        this(context.getFilesDir().getAbsolutePath() + "/" + projectName);
    }

    public Category createCategory(String categoryName) throws Exception{
        return new Category(projectFile.getAbsolutePath(), categoryName);
    }

    public void deleteCategory(String categoryName){
        File category = new File(projectFile.getAbsolutePath() + "/" + categoryName);
        category.delete();
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            for (File f : projectFile.listFiles())
                categories.add(new Category(f.getAbsolutePath()));
        } catch (Exception e){
            Logger.getLogger("Project.DataModel").log(Level.WARNING, "Error detected when creating "
                    +"project from project folder: " + e);

        }
        return categories;
    }
    public PriorityQueue<Page> getAllPages() {
        Comparator<Page> comparator = new PageComparator();
        PriorityQueue<Page> queue = new PriorityQueue<Page>(10, comparator);
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            for(Category category: getCategories()){
                for(Page page: category.getPages()){
                    queue.add(page);
                }
            }
        } catch (Exception e){
            Logger.getLogger("Project.DataModel").log(Level.WARNING, "Error detected when creating "
                    +"project from project folder: " + e);

        }

        for(Category category : categories) {
            System.out.println(category.getAbsolutePath());
            ArrayList<Page> currPages = category.getPages();
            for(Page page : currPages) {
                try {

                } catch (Exception e) {
                }
            }

        }
        return queue;
    }

    public boolean delete(){
        for(Category f: getCategories()){
            f.delete();
        }
        return projectFile.delete();
    }

    // Bottom of Project.java
    class PageComparator implements Comparator<Page>
    {
        @Override
        public int compare(Page x, Page y)
        {

            if(x.toString().length() == y.toString().length()) return 0;
            return (x.toString().length() > y.toString().length()? -1 : 1);
        }
    }

}
