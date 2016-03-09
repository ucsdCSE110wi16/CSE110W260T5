package net.atlassian.teammyrec.writersbloc.Models.DataModels;

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
public class Project implements Deletable {

    private String projectName;
    private String owner;

    public Project(String projectName, String owner) {
        this.projectName = projectName;
        this.owner = owner;

    }


    public Category createCategory(String categoryName) {
        Category c = new Category(categoryName, this.owner, this.projectName);
        ParseController.createCategory(categoryName, projectName, owner);
        return c;

    }

    public String toString() { return this.projectName; }


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
        ParseController.deleteProject(projectName, owner);

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

}
