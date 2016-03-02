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
    public PriorityQueue<String> getAllPages() {
        Comparator<String> comparator = new StringLengthComparator();
        PriorityQueue<String> queue = new PriorityQueue<String>(10, comparator);
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            for (File f : projectFile.listFiles())
                categories.add(new Category(f.getAbsolutePath()));
        } catch (Exception e){
            Logger.getLogger("Project.DataModel").log(Level.WARNING, "Error detected when creating "
                    +"project from project folder: " + e);

        }

        for(Category category : categories) {
            System.out.println(category.getAbsolutePath());
            ArrayList<Page> currPages = category.getPages();
            for(Page page : currPages) {
                try {
                    System.out.println(page.getPageInformation().getTitle());
                    String[] path = page.getAbsolutePath().split("/");
                    queue.add(path[path.length - 1]);
                } catch (Exception e) {
                }
            }

        }
        return queue;
    }

    // Bottom of Project.java
    class StringLengthComparator implements Comparator<String>
    {
        @Override
        public int compare(String x, String y)
        {
            if(x.length() == y.length()) return 0;
            return (x.length() > y.length()? -1 : 1);
        }
    }

}
