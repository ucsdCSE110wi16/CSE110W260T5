package net.atlassian.teammyrec.writersbloc.Models.DataModels;

import android.app.AlertDialog;
import android.content.Context;

import com.parse.Parse;

import net.atlassian.teammyrec.writersbloc.Interfaces.Deletable;
import net.atlassian.teammyrec.writersbloc.ParseController;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import android.util.*;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.*;
/**
 * Created by jay on 2/10/16.
 */
<<<<<<< HEAD
public class Project {
    private Context context;
=======
public class Project implements Deletable {

>>>>>>> f33c081809521102a693f9c09c3ca92774583a42
    private String projectName;
    private String owner;

    public Project(Context context, String projectName, String owner) {
        this.projectName = projectName;
        this.owner = owner;
        this.context = context;

    }


    public Category createCategory(String categoryName) {
        ArrayList<Category> categories = ParseController.getAllCategoriesForProject(this.toString());
        ArrayList<String> categoryNames = new ArrayList<String>();
        for(Category c: categories) {
            System.out.println("Adding " + c.toString() + " to existing projects.");
            categoryNames.add(c.toString());
        }
        Category c = new Category(this.context, categoryName, this.owner, this.projectName);
        ParseController.createCategory(categoryName, projectName, owner);
        return c;

    }

    public String toString() { return this.projectName; }

    public void deleteCategory(String categoryName){

    }

    public ArrayList<Category> getCategories() {
        return ParseController.getAllCategoriesForProject(this.projectName);
    }


    public PriorityQueue< Pair<Category, Page> > getAllPages() {
        Comparator< Pair<Category,Page> > comparator = new PageComparator();
        PriorityQueue< Pair<Category, Page> > queue =
                new PriorityQueue< Pair<Category, Page> >(10, comparator);

        PageComparator pc = new PageComparator();
        PriorityQueue< Pair<Category, Page> > allPages = new PriorityQueue< Pair<Category, Page> >(10, pc);
        ArrayList<Category> categories = ParseController.getAllCategoriesForProject(this.projectName);
        for(Category c : categories) {
            ArrayList<Page> localPages = ParseController.getAllPagesForCategory(
                    c.toString(), this.toString());
            for(Page p : localPages) {
                allPages.add(new Pair(c, p));
            }
        }
        return allPages;
    }


    @Override
    public void delete(){

    }

    // Bottom of Project.java
    class PageComparator implements Comparator<Pair<Category, Page>>
    {
        @Override
        public int compare(Pair<Category, Page> x, Pair<Category, Page> y)
        {

            if(x.second.toString().length() == y.second.toString().length()) return 0;
            return (x.second.toString().length() > y.second.toString().length()? -1 : 1);
        }
    }
    /*
    public String toString(){
        return projectFile.getName();
    }*/

}