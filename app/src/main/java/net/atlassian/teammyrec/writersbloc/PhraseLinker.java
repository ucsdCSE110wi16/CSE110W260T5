package net.atlassian.teammyrec.writersbloc;
import java.util.*;
import android.util.*;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

public class PhraseLinker {

    /*
     * findPhrases()
     * Description: Finds all of the keyphrases within a body of text.
     */
    public static PriorityQueue< Pair<Integer, Page> > findPhrases(String body, PriorityQueue<Page> keyPhrases, String currPage) {
        PairComparator pc = new PairComparator();
        PriorityQueue< Pair<Integer, Page> > links = new PriorityQueue< Pair<Integer, Page> >(10, pc);
        if(body == null){
            return links;
        }
        boolean[] hasBeenLinked = new boolean[body.length()];

        // Valid delimiters that can be to the left or right of the keyphrase
        ArrayList<String> delimiters = new ArrayList<String>(
                Arrays.asList(new String[] {" ", "\n", "'", "\"", ":", "-", ".", "!", ";", "?", ")", "("}));


        // Check each keyphrase to see if it's part of the body
        for(Page page : keyPhrases) {
            String phrase = page.toString();
            if(phrase.equals(currPage)){continue;}
            boolean addToLinks = true;
            int localIndex = body.toLowerCase().indexOf(phrase.toLowerCase());
            int globalIndex = localIndex;
            if(localIndex != -1) {
                String newBody = body;

                do {
                    addToLinks = true;
                    System.out.println("BEG OF LOOP: globalIndex = " + globalIndex);
                    System.out.println(phrase + " has been found at index " + globalIndex);
                    String beforeDelim ;
                    String afterDelim;

                    // If the phrase is at the beginning of the string, just set delim to whitespace
                    if(globalIndex - 1 >= 0) {
                        beforeDelim = new String(new char[] {body.charAt(globalIndex- 1)});
                    } else {
                        beforeDelim = " ";
                    }

                    // Same with end of string
                    if(globalIndex + phrase.length() < body.length()) {
                        afterDelim = new String(new char[] {body.charAt(globalIndex+phrase.length() )});
                    } else {
                        afterDelim = " ";
                    }

                    // If any part of the phrase is part of another link, don't add
                    for(int i = globalIndex; i < globalIndex + phrase.length(); i++) {
                        if(hasBeenLinked[i]) {
                            System.out.println("Index " + i + " has already been linked.");
                            addToLinks = false;
                            break;
                        }
                    }

                    if(!addToLinks) System.out.println("We're not adding " + phrase + " to links this time.");

                    // Check that they're valid delimiters - this indicates the token is an intended link
                    if(delimiters.contains(beforeDelim) && delimiters.contains(afterDelim)) {
                        if(addToLinks) {
                            links.add(new Pair(new Integer(globalIndex), page));

                            // Mark entire phrase in body as part of a link
                            for(int i = globalIndex; i < globalIndex + phrase.length(); i++) {
                                hasBeenLinked[i] = true;
                            }
                        }
                    }

                    globalIndex += phrase.length();
                    newBody = newBody.substring(localIndex + phrase.length());
                    localIndex = newBody.toLowerCase().indexOf(phrase.toLowerCase());
                    globalIndex += localIndex;
                    System.out.println("Local index is : " + localIndex);
                } while(localIndex != -1);
            }
        }
        return links;
    }

}


/* Custom comparator that prioritizes longest strings */
class StringLengthComparator implements Comparator<String>
{
    @Override
    public int compare(String x, String y)
    {
        if(x.length() == y.length()) return 0;
        return (x.length() > y.length()? -1 : 1);
    }
}

class PairComparator implements Comparator< Pair<Integer, Page> >
{
    @Override
    public int compare(Pair<Integer, Page> x, Pair<Integer, Page> y)
    {

        if(x.first < y.first) {
            return 1;
        } else if(x.first > y.first) {
            return -1;
        } else {
            return 0;
        }
    }
}


