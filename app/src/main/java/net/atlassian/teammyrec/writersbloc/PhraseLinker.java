import java.util.*; 

public class PhraseLinker { 

	/* 
	 * findPhrases()
	 * Description: Finds all of the keyphrases within a body of text. 
	 */ 
	public static HashMap<Integer, String> findPhrases(String body, PriorityQueue<String> keyPhrases) { 

		HashMap<Integer, String> links = new HashMap<Integer, String>(); 
		boolean[] hasBeenLinked = new boolean[body.length()];

		// Valid delimiters that can be to the left or right of the keyphrase
		ArrayList<String> delimiters = new ArrayList<String>(
			Arrays.asList(new String[] {" ", "\n", "'", "\"", ":", "-", ".", "!", ";", "?", ")", "("}));
		
		
		// Check each keyphrase to see if it's part of the body
		for(String phrase : keyPhrases) { 
			boolean addToLinks = true; 
			int localIndex = body.toLowerCase().indexOf(phrase.toLowerCase());
			int globalIndex = localIndex; 
			if(localIndex != -1) {
				String newBody = body; 
				
				do { 
					String beforeDelim;
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
							addToLinks = false; 
							break; 
						}
					}type

					// Check that they're valid delimiters - this indicates the token is an intended link 
					if(delimiters.contains(beforeDelim) && delimiters.contains(afterDelim)) { 
						if(addToLinks) { 
							links.put(new Integer(globalIndex), phrase);

							// Mark entire phrase in body as part of a link
							for(int i = globalIndex; i < globalIndex + phrase.length(); i++) { 
								hasBeenLinked[i] = true; 
							}
						}
					}
					globalIndex = localIndex + phrase.length(); 
					newBody = newBody.substring(localIndex + phrase.length());
					localIndex = newBody.toLowerCase().indexOf(phrase.toLowerCase());
					globalIndex += localIndex; 

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

