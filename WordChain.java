import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.*;

/**
 * Word chain takes a starting word, ending word, and dictionary. It finds a chain
 * of words from starting from the starting word to end word with only changing
 * one char and words staying the same length. User can specify minimum chain
 * length if possible.
 *
 * @author David Carey and Ethan Morgan
 * @args 0 will give instructions on running program
 * @args 2 or greater will give starting word and end word
 *
 * */
public class WordChain {
    static ArrayList<String> wordList;
    static int chainLength;
    static String endWord;
    static boolean found;
    static String startWord;

    /**
     *  Main method to get user input takes in User's arguments and
     *  will give instructions if no args. If args are given first arg is set to
     *  start word, while the second is endWord. If more than two args given then
     *  it will apply option of chain length. The word chain is solved
     *  by adding visited nodes and the method call of distance to each element in the
     *  priority queue.
     *
     * @param  args, which is not used at all.
     * */
    public static void main(String[] args) {
        if(args.length==0){
            System.out.println("Please, run it in following way");
            System.out.println("For desired step count:");
            System.out.println("java WordChain note book 10 < words.txt");
            System.out.println("For shortest step count:");
            System.out.println("java WordChain note book  < words.txt");
            return;
        }
       startWord = args[0];
       endWord = args[1];
       boolean shortest = false;
       int wordLength = startWord.length();

       if(args.length>2){
         chainLength = Integer.parseInt(args[2]);
       } else{
           shortest = true;
       }
       found = false;

       Scanner in = new Scanner(System.in);
       wordList = new ArrayList<>();
       //this part was used for checking the program
       //for checking make debug = true
       boolean debug = false;
       if(debug){
            try {
            Scanner inFile = new Scanner(new File("Three"));
            while(inFile.hasNextLine()){
                String word = inFile.nextLine().trim();
                if(word.length()==wordLength) {
                    wordList.add(word);
                }
            }
            inFile.close();
            } catch(FileNotFoundException ex){
            System.out.println("Error reading the file");
            }
       } else{
            while(in.hasNextLine()){
                String word = in.nextLine().trim();
                //if(word.equals("***")) break;
                if(word.length()==wordLength) {
                    wordList.add(word);
                }
            }
       }

       ArrayList<String> visited = new ArrayList<>();
       visited.add(startWord);
       PriorityQueue<Path> pQueue = new PriorityQueue<>();
       pQueue.add(new Path(visited, distance(startWord, endWord)));
       //System.err.println("adding path: " + startWord + " : " + endWord);

       int minimumDistance = distance(startWord, endWord);

       if (chainLength <= minimumDistance && !shortest) {
           System.out.println(startWord+" "+endWord+" "+chainLength+ " not possible");
           System.exit(0);
       }

       if(shortest){
           if(distance(startWord, endWord)==1){
             System.out.println(startWord+" "+endWord);
           } else{
               //solve(pQueue,shortest);

               //if(!found) {
            //	   System.out.println(startWord+" "+endWord+" not possible");
              // }

               String s = FindShortest(new ArrayList<String>(wordList), startWord, endWord);

               if (s.length() == 0) {
                System.out.println(startWord+" "+endWord+" not possible");
               } else {
                System.out.println(s + " ");
               }
           }
       }
        else{
           solve(pQueue,shortest);
           if(!found) {
                System.out.println(startWord+" "+endWord+" "+chainLength+ " not possible");
           }
       }
    }

    /**
     * Calculates the distance between s1 word and the s2 word.
     *
     * @param s1 as start word
     * @param s2 as targeted word
     * */
    private static int distance(String s1, String s2){
        int count=0;
        for(int i=0;i<s1.length();i++) {
            if(s1.charAt(i)!=s2.charAt(i)) {
            	count++;
            }
        }
        return count;
    }

    /**
     * Solve method works through the visited list and compares it with targetted word.
     * If it equals then prints out word chain words. If last word is not equal to targetted word
     * then current word is iterated through the dictionary to try find a path. It does this by
     * making a copy of the visited list and then adding current word to the queue.
     * */
    private static void solve(PriorityQueue<Path> pQueue, boolean shortest){
        while(!pQueue.isEmpty() && !found){
            Path path = pQueue.poll();
            ArrayList<String> visited = path.visited;
            String lastWord = visited.get(visited.size()-1);

            if(lastWord.equals(endWord)){
                if(shortest){
                    for(String s:visited) {
                    	System.out.print(s + " ");
                    }
                    System.out.println();
                    found = true;
                } else{
                    if(visited.size() == chainLength){
                        for(String s:visited) {
                        	System.out.print(s + " ");
                        }
                        System.out.println();
                        found = true;
                    }
                }
            } else{
                for(String currentWord : wordList){
                    if(distance(lastWord, currentWord) == 1
                    && visited.indexOf(currentWord) < 0) {
                        //ArrayList<String> c = copy(visited);
                        ArrayList<String> c = new ArrayList<String>(visited);
                        c.add(currentWord);
                        pQueue.add(new Path(c, distance(currentWord, endWord)));
                       // System.err.println("adding path: " + currentWord + " : " + endWord + " dist=" + distance(currentWord, endWord));
                    }
                }
            }
        }

    }

    /**
     * ArrayList is the mthod to copy the original dictionary so we can compare.
     *
     * @return c coppy of orginal dictionary.
     * */
    private static ArrayList<String> copy(ArrayList<String> original){
       ArrayList<String> c = new ArrayList<String>(original.size());
       for(String s:original) {
    	   c.add(s);
       }
       return c;
    }

    private static String FindShortest(ArrayList<String> words, String startWord, String endWord) {
        Graph g = new Graph();
        
        for (String word : wordList) {
            g.addVertex(word);
        }

        for (String word : wordList) {
            for (String nextWord : wordList) {
                if (distance(word, nextWord) == 1) {
                    g.addEdge(word, nextWord);
                }
            }
        }        

        Path p = g.shortest(startWord, endWord);
        
        if (p.visited.size() == 0) {
            return "";
        }
        
        String r = "";

        for (String w : p.visited) {
            r += w + " ";
        }

        return r;
    }
}

class Graph {
    HashMap<String, Vertex> vertices;

    public Graph() {
        vertices = new HashMap<String, Vertex>();
    }

    public void addVertex(String vertexLabel) {
        vertices.put(vertexLabel, new Vertex(vertexLabel));
    }

    public void addEdge(String vertexLabelA, String vertexLabelB) {
        Vertex vertexA = vertices.get(vertexLabelA);
        Vertex vertexB = vertices.get(vertexLabelB);
        vertexA.addEdge(vertexB);
        vertexB.addEdge(vertexA);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (Map.Entry<String, Vertex> v : vertices.entrySet()) {
            s.append(v.getValue() + "\n");
        }

        return s.toString();
    }

    public Path shortest(String startWord, String endWord) {
        Vertex startVertex = vertices.get(startWord);
        Queue<Vertex> bfsQueue = new ArrayDeque<Vertex>();
        bfsQueue.add(startVertex);   
        startVertex.visited = true;     
        Queue<Path> pathQueue = new ArrayDeque<Path>();        
        Path p = new Path();
        p.add(startVertex.vertexLabel);
        pathQueue.add(p);
        
        while (!bfsQueue.isEmpty()) {
            Vertex current = bfsQueue.poll();
            Path currentPath = pathQueue.poll();

            ArrayList<Vertex> adjacentVertices = current.adjacentVertices;
            
            for (int i = 0; i < adjacentVertices.size(); i++) {
                Path newPath = new Path();

                for (String c : currentPath.visited) {
                    newPath.add(c);
                }

                newPath.add(adjacentVertices.get(i).vertexLabel);

                if (!adjacentVertices.get(i).visited) {

                    if (adjacentVertices.get(i).vertexLabel.equals(endWord)) {
                        //System.err.println(newPath.toString());
                        return newPath;
                    } else {
                        //System.out.println(adjacentVertices.get(i).vertexLabel);
                        adjacentVertices.get(i).visited = true;
                        bfsQueue.add(adjacentVertices.get(i));                        
                        pathQueue.add(newPath);                                        
                    }
                }
            }
        }

        return new Path();
    }

    private class Vertex {
        ArrayList<Vertex> adjacentVertices = new ArrayList<Vertex>();
        String vertexLabel;
        boolean visited;

        public Vertex(String vertexLabel) {
            this.vertexLabel = vertexLabel;
            this.visited = false;
        }

        public void addEdge(Vertex v) {
            adjacentVertices.add(v);
        }

        public String getLabel() {
            return vertexLabel;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(vertexLabel + " => ");
            
            for (Vertex v : adjacentVertices) {
                s.append(v.getLabel() + " ");
            }

            return s.toString();
        }
    }
}

/**
 * This class is created to compare all paths within the Word Chain and
 * makes it possible to direct a path of the word chain.
 *
 * */
class Path implements Comparable<Path>{
   ArrayList<String> visited;
   int distance;

    public Path() {
        this.visited = new ArrayList<String>();
        this.distance = 0;
    }

   /**
    * Creates Path with visited in the ArrayList and distance between words.
    * */
   public Path(ArrayList<String> visited, int distance){
       this.visited = visited;
       this.distance = distance;
   }

   public void add(String s)
   {
       this.visited.add(s);
       this.distance++;
   }

   /**
    * Compares one path to another to get the shortest path.
    *
    * @return distance of compared path
    * */
   @Override
   public int compareTo(Path other){
       return distance - other.distance;
   }

   public String getLast() {
       String s = visited.get(visited.size() - 1);
       return s;
   }

   public String toString() {
       return this.visited.toString();
   }
}
