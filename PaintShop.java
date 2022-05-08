/*
Date: 4/6/2022
Course: CSCI 3005
Description: Programming Assignment 2: Color Palettes
Mason Moore 30097089

 * A program that uses backtracking in order to process a list of colors, followed by a list
 * of color conflicts, and place the colors in order so that no colors that conflict with
 * another conflict are contiguous, returning the first correct sequence of colors. The program 
 * will also calculate the total number of possible color sequences with no conflicts. 
 */
import java.util.*;
import java.io.*;

public class PaintShop {
    ArrayList<String> myColors = new ArrayList<>();
    ArrayList<String[]> result = new ArrayList<>();
    private ArrayList<String[]> conflicts = new ArrayList<>();
    private ArrayList<Boolean> visited = new ArrayList<>();
    private boolean completed = false;

    /**
     * A constructor that reads data from a text file and stores each line into variables. 
     * First line is the number of colors, second line is the list of colors in order of priority, 
     * third line is the number of conflicts, and any subsequent line is a pair of color conflicts. 
     *
     * @param filename The name of the file.
     */
    public PaintShop(String filename) {
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            int numColors = Integer.parseInt(sc.nextLine());

            // Adding all colors in the array. Lower index = the highest popularity
            String[] colors = sc.nextLine().split(" ");
            // Add colors to array list myColors and initialize visited as false for each color
            for (String color : colors) {
                myColors.add(color);
                visited.add(false);
            }

            int numConflicts = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < numConflicts; i++) {
                String[] conflictColors = sc.nextLine().split(" ");
                conflicts.add(conflictColors);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * A method that returns the first correct sequence of colors. Initiates the
     * backTrackString() method.
     * 
     * @return the first correct sequence of colors
     */

    public String getSolution() {
        ArrayList<String> permutation = new ArrayList<>();
        colorNode(myColors,permutation,visited);
        return Arrays.toString(result.get(0));
    }

    /**
     * Backtracks through each color at every node in the sequence. The number of
     * nodes will be equal to the size of myColors. While not complete, checks if
     * each color in the list has been visited and if there is a conflict between 
     * the last color placed in the sequence. If it is promising, adds the color to 
     * the sequence and sets its visited property to true. Calls the method recursively 
     * and resets visited til it reaches the goal. If the goal is reached, applies the 
     * color to each node in the sequence and adds that sequence of nodes to the array of results.
     * Sets completed to true once first correct solution is found.
     * 
     * @param myColors list of colors
     * @param sequence the current possible sequence of correct colors
     * @param visited list of colors visited
     */
    private void colorNode(ArrayList<String> myColors, ArrayList<String> sequence, List<Boolean> visited) {
        if (!completed) {
            for (int i = 0; i < myColors.size(); i++) {
                if (visited.get(i) == false && (promising(sequence, myColors.get(i)) == true)) {
                    // make the choice and add to list.
                    visited.set(i, true);
                    sequence.add(myColors.get(i));
                    //Execute recursion
                    colorNode(myColors, sequence, visited);
                    visited.set(i, false);
                    sequence.remove(sequence.size() - 1);
                }
            }
        }
        // Goal Reached when sequence is same length as myColors
        if (sequence.size() == myColors.size()) {
            String node[] = new String[myColors.size()];
            for (int k = 0; k < node.length; k++) {
                node[k] = sequence.get(k);
            }
            result.add(node);
            completed = true;
        }
    }

    /**
     * Finds all possible color sequences and adds them to the array of results.
     * Clears the visited nodes and repeats the backtracking.
     * 
     * @return the number of possible solutions
     */
    public int howManySolutions(){
        //reset result and visited nodes
        result.clear();
        Collections.fill(visited, Boolean.FALSE);
        ArrayList<String> permutation = new ArrayList<>();
        colorAllNodes(myColors,permutation,visited);
        return result.size();
    }

    /**
     * Similar method to colorNode(), but does not stop once first correct color sequence 
     * is found. Continues to find every possible of correct color sequence until 
     * the goal is reached. Once goal is reached, add the sequence to the array of results.
     * 
     * @param myColors the list of colors
     * @param sequence the current possible sequence of correct colors
     * @param visited list of colors visited 
     */
    private void colorAllNodes(ArrayList<String> myColors, ArrayList<String> sequence, List<Boolean> visited) {
        for (int i = 0; i < myColors.size(); i++) {
            if (visited.get(i) == false && (promising(sequence, myColors.get(i)) == true)) {
                // make the choice and add to list.
                visited.set(i, true);
                sequence.add(myColors.get(i));
                colorAllNodes(myColors, sequence, visited);
                visited.set(i, false);
                sequence.remove(sequence.size() - 1);
            }
        }
        // Goal Reached
        if (sequence.size() == myColors.size()) {
            String arr[] = new String[sequence.size()];
            for (int k = 0; k < arr.length; k++) {
                arr[k] = sequence.get(k);
            }
            result.add(arr);
            return;
        }
    }
    
    /**
     * Checks if the considered color is promising. Returns true if the sequence 
     * is empty. Returns true if there is no matching conflict pair with the considered color
     * and last color in the sequence. Returns false if the last color placed is the same 
     * as the incoming color.
     * 
     * @param sequence the current possible sequence of correct colors
     * @param inComingColor the considered color to be placed into the next node
     * @return the boolean value for if the color is promising
     */
    private boolean promising(ArrayList<String> sequence, String inComingColor) {
        if (sequence.size() < 1) {
            return true;
        }
        String lastColorInSequence = sequence.get(sequence.size() - 1);
        // Checks the reverse of the conflict array to ensure there is no conflict
        for (int i = 0; i < conflicts.size(); i++) {
            String[] conflictArray = conflicts.get(i);
            String[] conflictArrayReversed = new String[] { conflictArray[1], conflictArray[0] };
            String[] incomingArray = new String[] { lastColorInSequence, inComingColor };

            if (Arrays.equals(incomingArray, conflictArray) || Arrays.equals(incomingArray, conflictArrayReversed)) {
                return false;
            }
        }
        return true;
    }
}
