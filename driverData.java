import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.Comparator;
import java.util.Collections;

// import java.util.HashSet;
// import java.util.Set;

public class driverData {
    public static void main(String[] args) throws Exception {
        // process the data
        // first line will contain the size of the universal set
        // second line will contain the number of subsets
        // each line after that will contain the associated subset

        File file1 = new File("./s-k-30-50.txt");
        
        // File file1 = new File("./s-k-20-30.txt");
        // File file1 = new File("./test.txt");
        
        ArrayList<ArrayList<Integer>> subsets = new ArrayList<>(); // arraylist of all the subests
        int universal;
        int numSubsets;

        try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
            String line;
            universal = Integer.parseInt(br.readLine()); // first line
            numSubsets = Integer.parseInt(br.readLine()); // second line
            while ((line = br.readLine()) != null) {
                // process the line for each individual subset
                ArrayList<Integer> subset = new ArrayList<>();
                for (String s : line.split("\\s+")) { // String[] subsetArray = line.split("\\s+");
                    if (s.isEmpty()) {

                    } else {
                        subset.add((Integer.parseInt(s)));
                    }
                }
                subsets.add(subset);
            }
        }

        System.out.println(file1.getName());
        // System.out.println();

        long startTime = System.nanoTime();

        // ArrayList<ArrayList<Integer>> result = new ArrayList<>(); // result
        // boolean[] a = new boolean[numSubsets]; // current combination
        // subsets = data... the subsets given (ints given)
        // universal = numbers needed to be covered
        
        // ArrayList<ArrayList<Integer>> start = new ArrayList<>(); // result
        minSetCover f1 = new minSetCover(universal, subsets);
        // f1.backtrack(start);
        f1.backtrack(f1.a);
        System.out.println("Minimum set cover is:");
        System.out.println(Arrays.toString(f1.currSol.toArray()));
        System.out.println("Size of MSC is: " + f1.currSol.size());
        
        System.out.println("Program running time: " + (System.nanoTime() - startTime)/1000000000.0 + "s");
        System.out.println("Number of comparisons: " + f1.test);
    }
}

class minSetCover {
    ArrayList<ArrayList<Integer>> currSol = new ArrayList<>();
    ArrayList<ArrayList<Integer>> a = new ArrayList<>();
    boolean[] check;
    int universal;
    ArrayList<ArrayList<Integer>> data = new ArrayList<>();

    public minSetCover(int universal, ArrayList<ArrayList<Integer>> data) {
        this.check = new boolean[universal];
        this.universal = universal;
        this.data = new ArrayList<ArrayList<Integer>>(data); // clone;

        // sort data subsets(array lists) from longest to smallest
        Collections.sort(this.data, new Comparator<ArrayList<Integer>>() {
            public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) {
                return a2.size() - a1.size();
            }
        });

        // early on
        // System.out.println("data " + Arrays.toString(this.data.toArray()));

        ArrayList<ArrayList<Integer>> contained = new ArrayList<>();
        for (int k = 0; k < universal; k++) {
            ArrayList<Integer> empty = new ArrayList<>();
            contained.add(empty);
        }
        // universal counting of data index
        for (int w = 0; w < this.data.size(); w++) { // each subset
            ArrayList<Integer> currSubset = this.data.get(w);
            for (int j = 0; j < currSubset.size(); j++) { // inside the subset
                int num = currSubset.get(j); // each number in the subset
                contained.get(num-1).add(w); // add subset's index in data                
            }
        }
        
        // System.out.println("contained " + Arrays.toString(contained.toArray()));
        // System.out.println();

        for (int w = 0; w < contained.size(); w++) { // contained
            if (contained.get(w).size() == 1) { 
                // must be added to the solution
                int index = contained.get(w).get(0);
                // System.out.println(index);
                this.a.add(this.data.get(index));
                // System.out.println("d arr " + Arrays.toString(this.data.toArray()));
                // System.out.println("this arr " + Arrays.toString(this.a.toArray()));
                this.data.remove(this.data.get(index));
                // System.out.println("data arr " + Arrays.toString(this.data.toArray()));
            }
        }
    }

    // BACKTRACK
    int test;

    public void backtrack(ArrayList<ArrayList<Integer>> a) {
        // a = current combination
        // universal, need numbers 1 - universal
        // check is the universal cover check

        ArrayList<ArrayList<Integer>> candidates = new ArrayList<>(); // candidates of subsets
        int i; // counter

        this.check = new boolean[this.universal]; // reset the check
        // update the check array
        for (int w = 0; w < a.size(); w++) { // each subset
            ArrayList<Integer> currSubset = a.get(w);
            for (int j = 0; j < currSubset.size(); j++) { // inside the subset
                int num = currSubset.get(j); // each number in the subset
                // update the boolean[] check array
                this.check[num - 1] = true;
            }
        }

        // System.out.println(Arrays.toString(this.check));
        // System.out.println("...testing " + Arrays.toString(a.toArray()));
        this.test++;
        if (isASolution(this.check)) {
            processSolution(a);
        } else {
            // CONSTRUCT CANDIDTATES

            if (!currSol.isEmpty() && (a.size() >= this.currSol.size())) { 
                // if current perm is longer than sol found
                return; // candidates is an empty array
            }

            // get rid of last seen
            int number = 0;
            if (!a.isEmpty()) {
                if (this.data.indexOf(a.get(a.size() - 1)) == -1) {
                    number = 0;
                } else { 
                    number = this.data.indexOf(a.get(a.size() - 1));
                }
            }

            for (int w = number; w < this.data.size(); w++) { // each subset
                ArrayList<Integer> currSubset = this.data.get(w);

                if (!a.contains(this.data.get(w))) { // if it is not in the subset already
                    for (int j = 0; j < currSubset.size(); j++) { // inside the subset
                        int num = currSubset.get(j); // each number in the subset
                        // amount of uncovered elements
                        if (this.check[num - 1] == false) {
                            // uncovered += 1;
                            candidates.add(currSubset);
                            break;
                        } 
                    } // done with the subset
                }
            }
            // end of construct candidates

            // System.out.println("a is " + Arrays.toString(a.toArray()));
            // System.out.println("candidates are " + Arrays.toString(candidates.toArray()));

            for (i = 0; i < candidates.size(); i++) {
                
                // make move
                a.add(candidates.get(i)); // add subset to current combination

                // System.out.println("a is " + Arrays.toString(a.toArray()));
                // System.out.println("check is " + Arrays.toString(this.check));
                // System.out.println("");

                // backtrack
                backtrack(a);

                // unmake move
                a.remove(a.size() - 1);
            }
        }
    }

    public boolean isASolution(boolean[] b) { // is a set cover
        for (int i = 0; i < b.length; i++) {
            if (b[i] == false) {
                return false;
            }
        }
        return true;
    }

    public void processSolution(ArrayList<ArrayList<Integer>> a) { // print out

        if (this.currSol.isEmpty() || (a.size() < this.currSol.size())) {
            this.currSol = new ArrayList(a); // clone
        }
    }

}