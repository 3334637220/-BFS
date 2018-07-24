/* Ladder.java
   CSC 225 - Summer 2018
   
   Starter code for Programming Assignment 3

   B. Bird - 06/30/2018
*/


import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Ladder {


    public static void showUsage() {
        System.err.printf("Usage: java Ladder <word list file> <start word> <end word>\n");
    }


    public static void main(String[] args) {

        //At least four arguments are needed
        if (args.length < 3) {
            showUsage();
            return;
        }
        String wordListFile = args[0];
        String startWord = args[1].trim();
        String endWord = args[2].trim();


        //Read the contents of the word list file into a LinkedList (requires O(nk) time for
        //a list of n words whose maximum length is k).
        //(Feel free to use a different data structure)
        BufferedReader br = null;
        LinkedList<String> words = new LinkedList<String>();

        try {
            br = new BufferedReader(new FileReader(wordListFile));
        } catch (IOException e) {
            System.err.printf("Error: Unable to open file %s\n", wordListFile);
            return;
        }

        try {
            for (String nextLine = br.readLine(); nextLine != null; nextLine = br.readLine()) {
                nextLine = nextLine.trim();
                if (nextLine.equals(""))
                    continue; //Ignore blank lines
                //Verify that the line contains only lowercase letters
                for (int ci = 0; ci < nextLine.length(); ci++) {
                    //The test for lowercase values below is not really a good idea, but
                    //unfortunately the provided Character.isLowerCase() method is not
                    //strict enough about what is considered a lowercase letter.
                    if (nextLine.charAt(ci) < 'a' || nextLine.charAt(ci) > 'z') {
                        System.err.printf("Error: Word \"%s\" is invalid.\n", nextLine);
                        return;
                    }
                }
                words.add(nextLine);
            }
        } catch (IOException e) {
            System.err.printf("Error reading file\n");
            return;
        }

        /* Find a word ladder between the two specified words. Ensure that the output format matches the assignment exactly. */


        /* Your code here */
        HashMap<String, Set<String>> map = new HashMap<>();

        for (String w1 : words) {
            if (!map.containsKey(w1)) {
                map.put(w1, new HashSet<>());
            }
            for (String w2 : words) {
                if (diff(w1, w2) == 1) {
                    map.get(w1).add(w2);
                }
            }
        }

        if(!map.containsKey(startWord)||!map.containsKey(endWord)){
            System.out.println("No word ladder found.");
            return;
        }

        Map<String, String> pathMap = new LinkedHashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(startWord);
        boolean finish = false;
        while (!queue.isEmpty()&&!finish) {
            String curWord = queue.poll();
            for (String w : map.get(curWord)) {
                if (!visited.contains(w)) {
                    if(!pathMap.containsKey(w))
                        pathMap.put(w, curWord);
                    if(w.equals(endWord)){
                        finish = true;
                        break;
                    }
                    if(!queue.contains(w)) {
                        queue.offer(w);
                    }
                }
            }
            visited.add(curWord);
        }
        if(!pathMap.containsKey(endWord)){
            System.out.println("No word ladder found.");
        }
        List<String> path = new ArrayList<>();
        String curWord = endWord;
        path.add(curWord);
        while (!curWord.equals(startWord)) {
            curWord = pathMap.get(curWord);
            path.add(curWord);
        }
        Collections.reverse(path);
        for(String p: path){
            System.out.println(p);
        }

    }

    private static int diff(String w1, String w2) {
        int s = 0;
        for (int i = 0; i < w1.length(); i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                s++;
            }
        }
        return s;
    }

}