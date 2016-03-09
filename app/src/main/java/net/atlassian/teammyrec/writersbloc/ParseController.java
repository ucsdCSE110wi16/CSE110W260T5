package net.atlassian.teammyrec.writersbloc;

/**
 * Created by dheerajgottipalli on 2/10/16.
 */

import android.util.Log;

import com.parse.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import android.content.*;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.*;


/**
 * Created by matt on 2/10/16.
 */
public class ParseController {

    public static void addUser(String username, String password) {

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user.signUp();
        } catch(ParseException e) {

        }

    }

    public static boolean checkLogin(String username, String password) {
        try {
            ParseUser user = ParseUser.logIn(username, password);
            if(user != null) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void logoutCurrentUser() {
        ParseUser.logOut();
    }

    public static String getCurrentUser() {
        return ParseUser.getCurrentUser().getUsername().toString();
    }

    public static boolean userIsLoggedIn() {
        boolean val = (ParseUser.getCurrentUser() != null)? true : false;
        return val;
    }

    public static void createPage(String pageName, String category, String project, String userName) {
        System.out.println("updating page");
        ParseObject page = new ParseObject("Page");
        if(pageName != null) page.put("title", pageName);
        if(category != null) page.put("category", category);
        if(project != null) page.put("project", project);
        page.put("pageContent", "");
        page.put("owner", userName);
        try {
            page.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static String getPageBody(String pageName, String categoryName, String projectName, String owner) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Page");
        query.whereEqualTo("title", pageName);
        query.whereEqualTo("category", categoryName);
        query.whereEqualTo("project", projectName);
        query.whereEqualTo("owner", owner);
        query.setLimit(1);

        try {
            List<ParseObject> pages = query.find();
            ParseObject page = pages.get(0);
            return (String)page.get("pageContent");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }


    public static void deletePage(String pageName, String category, String project, String userName) {

        final String page = new String(pageName);
        final String categoryName = new String(category);
        final String projectName = new String(project);
        final String user = new String(userName);

        try {

            Thread th = new Thread(){
                @Override
                public void run(){
                    try {
                        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Page");
                        query.whereEqualTo("title", page);
                        query.whereEqualTo("category", categoryName);
                        query.whereEqualTo("project", projectName);
                        query.whereEqualTo("owner", user);
                        query.setLimit(1);
                        List<ParseObject> pages = query.find();
                        ParseObject page = pages.get(0);
                        page.delete();
                    }catch (ParseException e){

                    }
                }
            };

            th.start();


        } catch(Exception e) {

        }
    }

    public static void updatePage(String pageName, String category, String project, String owner, String body) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Page");
        query.whereEqualTo("title", pageName);
        query.whereEqualTo("category", category);
        query.whereEqualTo("project", project);
        query.whereEqualTo("owner", owner);
        query.setLimit(1);

        try {
            List<ParseObject> pages = query.find();
            ParseObject page = pages.get(0);
            page.put("pageContent", body);
            page.save();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void createProject(String projectName, String owner) {
        ParseObject project = new ParseObject("Project");
        project.put("projectName", projectName);
        project.put("owner", owner);
        try {
            project.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void deleteProject(String projectName, String owner) {


        final String name = new String(projectName);
        final String own = new String(owner);


        try {
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        ArrayList<Category> categories = ParseController.getAllCategoriesForProject(name);
                        for(Category c : categories) {
                            ParseController.deleteCategory(c.toString(), name, own);
                        }
                        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Project");
                        query.whereEqualTo("projectName", name);
                        query.whereEqualTo("owner", own);
                        query.setLimit(1);
                        List<ParseObject> projects = query.find();
                        ParseObject project = projects.get(0);
                        project.delete();
                    }catch (ParseException e){

                    }
                }
            };

            th.start();
        } catch (Exception e) {

        }

    }

    public static void createCategory(String categoryName, String project, String owner) {
        ParseObject category = new ParseObject("Category");
        category.put("categoryName", categoryName);
        category.put("project", project);
        category.put("owner", owner);
        try {
            category.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static void deleteCategory(String categoryName, final String project, String owner) {

        final String name = new String(categoryName);
        final String projectName = new String(project);
        final String own = new String(owner);

        try {
            Thread th = new Thread(){
                @Override
                public void run(){
                    try{
                        ArrayList<Page> pages = ParseController.getAllPagesForCategory(name, projectName);

                        for(Page page : pages) {
                            ParseController.deletePage(page.toString(), name, projectName, own);
                        }
                        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
                        query.whereEqualTo("categoryName", name);
                        query.whereEqualTo("project", projectName);
                        query.whereEqualTo("owner", own);
                        query.setLimit(1);
                        List<ParseObject> categories = query.find();
                        ParseObject category = categories.get(0);
                        category.delete();
                    }catch (ParseException e){}
                }
            };

            th.start();

        } catch (Exception e) {

        }
    }




    public static ArrayList<Project> getAllProjects() {
        ArrayList<Project> projects = new ArrayList<Project>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Project");
        query.whereEqualTo("owner", ParseController.getCurrentUser());
        try {
            List<ParseObject> results = query.find();
            for(ParseObject result : results) {
                projects.add(new Project((String)result.get("projectName"),
                        ParseController.getCurrentUser()));
            }
        } catch (ParseException e) {

        }
        return projects;

    }

    public static String getPageContent(String pageName, String categoryName, String projectName, String owner) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Page");
        query.whereEqualTo("owner", owner);
        query.whereEqualTo("title", pageName);
        query.whereEqualTo("category", categoryName);
        query.whereEqualTo("project", projectName);
        query.setLimit(1);
        List<ParseObject> results;
        try {
            results = query.find();
            if(results.size() == 0) return "";
            ParseObject result = results.get(0);
            return result.get("pageContent") != null? (String)result.get("pageContent") : "";
        } catch(ParseException e) {
            return "";
        }
    }

    public static ArrayList<Category> getAllCategoriesForProject(String project) {
        ArrayList<Category> categories = new ArrayList<Category>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
        query.whereEqualTo("owner", ParseController.getCurrentUser());
        query.whereEqualTo("project", project);
        try {
            List<ParseObject> results = query.find();
            for(ParseObject result : results) {
                categories.add(new Category((String)result.get("categoryName"),
                        ParseController.getCurrentUser(), project));
            }
        } catch (ParseException e) {
        }
        return categories;

    }

    public static ArrayList<Page> getAllPagesForCategory(String category, String project) {
        ArrayList<Page> pages = new ArrayList<Page>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Page");
        query.whereEqualTo("owner", ParseController.getCurrentUser());
        query.whereEqualTo("project", project);
        query.whereEqualTo("category", category);
        try {
            List<ParseObject> results = query.find();
            for(ParseObject result : results) {
                pages.add(new Page((String) result.get("title"),
                        category, project, ParseController.getCurrentUser()));
            }
        } catch (ParseException e) {

        }
        return pages;

    }




}