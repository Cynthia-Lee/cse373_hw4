# min-set-cover

Homework 4 Minimum Set Cover Problem 
 
For finding the minimum set cover, I used backtracking.  

isASolution​: Checks if the current list of subsets covers all the elements in the universal subset. I do this by using a boolean array, each index represents a number in the universal. Everytime a candidate is added to the current list of subsets, I update the boolean array by going through each element in the current list of subsets. 

processSolution​: I store the current list of subsets that is a solution into an ArrayList - called currSol. Everytime there is a solution found that has less subsets than the current solution, it will be replaced. currSol will now store that solution. 

findCandidates​: I look through the remaining subsets that are not in the current list, I check this by checking subsets in the input/data subsets. Those will be added to candidates. I further pruned findCandidates by doing the following: 

● The way I went about it was to use code similar to the backtracking code for finding permutations. However, my code does not have elements repeated in the permutation, ex. There will be S1,S2,S3 but not S1,S3,S2 or S2,S1,S3. I prevented elements from repeating when constructing candidates, I would limit the subsets that were available to choose from. If S1,S2,S3 was seen already, the next backtrack for S1,S3 would not have S2 as a possible candidate. I did this by getting the last added subset in the current list of subset’s subset index in data. Then I would have findCandidates only being able to choose from subsets after that index in the data. 

○ For example, data: S1,S2,S3. Current list of subset being built: S1, S3. S3’s index is 2 in data. The current subset being built only has the subsets in data after index 2, so only nothing will be available, excluding S2. We don’t have to worry about S2, because the previous subset that was built was S1,S2,S3. 

● If the current length of the current solution being built is >= the currSol already previously found, candidates will be empty to terminate early. 


● If there is a subset in the data/input that has all it’s elements that are already covered in the current subset being built, it will not add it to the candidates. Other pruning: 

● Sorting the input/data subsets from greatest to smallest before calling backtrack. 

● Go through the input/data subsets, if there is only one subset that contains an element in the universal, that subset must be used in the solution. I add this subset to the solution, and remove it from the input/data. 
 
Largest files it can handle under 1 minute: s-rg-63-25.txt, s-k-30-50.txt 
 
s-rg-63-25.txt Minimum set cover is: [[2, 7, 24, 37, 38, 39, 40, 41, 42], [6, 7, 8, 9, 10, 11, 12, 13], [13, 18, 26, 36, 49, 53, 59, 63], [22, 41, 55, 57, 60, 61, 62], [1, 14, 15, 16, 17, 18], [28, 39, 50, 54, 55, 56], [11, 25, 32, 51, 60, 63], [1, 2, 3, 4, 5], [6, 19, 20, 21, 22], [8, 14, 20, 43, 44], [3, 33, 37, 45, 46], [5, 16, 34, 38, 48], [30, 50, 51, 52, 53], [40, 54, 57, 58, 59], [23, 27, 28, 29], [31, 35, 44, 47]] Size of MSC is: 16 Program running time: 6.4515427s 
 
s-k-30-50.txt Minimum set cover is: [[5], [8, 12, 15, 17, 21], [8, 10, 14, 22, 30], [7, 10, 16, 17, 29], [1, 2, 9, 12, 23], [3, 6, 11, 12, 20], [4, 22, 25, 26, 27], [18, 19, 24, 28, 29], [2, 13, 18, 19, 29]] Size of MSC is: 9 Program running time: 16.1498614s 
 
 
