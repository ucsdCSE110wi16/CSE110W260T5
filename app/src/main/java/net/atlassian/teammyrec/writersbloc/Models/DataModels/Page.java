package net.atlassian.teammyrec.writersbloc.Models.DataModels;

/**
 * Created by jay on 2/6/16.
 */
public class Page {
    public Page(String absolutePageName) throws Exception{

    }

    public Page(String parent, String pageName) throws Exception{
        this(parent + "/" + pageName);
    }

}
