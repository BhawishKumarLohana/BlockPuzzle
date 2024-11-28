import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author:KUMAR Bhawish your name here (SID) 23205172
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 * 
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening
 *          you with your friendship. If they don't have the ability to work on
 *          something that can compile, they would not be able to change your
 *          code to a state that we can't detect the act of plagiarism. For the
 *          first commit of plagiarism, regardless you shared your code or
 *          copied code from others, you will receive 0 with an addition of 5
 *          mark penalty. If you commit plagiarism twice, your case will be
 *          presented in the exam board and you will receive a F directly.
 *
 *          Terms about generative AI:
 *          You are not allowed to use any generative AI in this assignment.
 *          The reason is straight forward. If you use generative AI, you are
 *          unable to practice your coding skills. We would like you to get
 *          familiar with the syntax and the logic of the Java programming.
 *          We will examine your code using detection software as well as 
 *          inspecting your code with our eyes. Using generative AI tool 
 *          may fail your assignment.
 * 
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Discord. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 */
public class BlockPuzzle {

    public static final 
    char[][][] PUZZLES = {{{'A'}},                       // A 

                          {{'B'},                        // B
                           {'B'}},                       // B                                                       

                          {{'C', 'C'},                   // CC
                           {'C', 'C'}},                  // CC
                          
                          {{'D', 'D', 'D'},              // DDD
                           {'D', '.', 'D'}},             // D D

                          {{'E', 'E', 'E'},              // EEE
                           {'.', 'E'}},                  //  E 

                          {{'F', 'F', 'F'},              // FFF
                           {'F'}},                       // F 

                          {{'G', 'G', 'G'},              // GGG
                           {'.', '.', 'G'}},             //   G

                          {{'H', 'H', 'H'},              // HHH
                           {'H', 'H', 'H'},              // HHH
                           {'H', 'H', 'H'}},             // HHH

                          {{'I', 'I', 'I', 'I', 'I'}}   // IIIII

                          
    };

    public static final int ROW = 8;
    public static final int COL = 9;
    public static final int ROUND = 3;
    public static final char EMPTY = '.';

    public static final int SCORES[] = {0, 10, 30, 50, 100, 200, 500};

    public static void main(String[] args) {
        BlockPuzzle bp = new BlockPuzzle();
        bp.run();
    }
    /**
     * The main game loop. Given.
     * 
     * This method has been finished for you. 
     * You are not allowed to change this method.
     */
    void run() {
        char[][] map = new char[ROW][COL];
        char[][][] puzzlesToPlace = new char[ROUND][][];
        init(map);
        int score = 0;
        while(true) {
            randomPuzzleIfNeeded(puzzlesToPlace); 
            printMap(map);
            System.out.println("Your score: " + score);
            printPuzzles(puzzlesToPlace);
            if (gameOver(map, puzzlesToPlace)) {
                System.out.println("Game Over..\nYour score is " + score);
                return;
            }
            getInputAndPlacePuzzle(map, puzzlesToPlace);
            score += SCORES[cancelPuzzles(map)];
        }
    }
    /**
     * Initialize the map so that all cells 
     * in the 2D array is assigned with the character '.'
     * 
     * The input map is a 2D array of characters.
     * You may assume it is a non-null, rectangular 2D array.
     */
    void init(char[][] map){
        for (int i = 0 ; i < map.length;i++){
            for (int j = 0 ; j < map[i].length;j++){
                map[i][j]=EMPTY;
            }
        }

    }
    /**
     * Print the puzzles to the console.
     *
     * The input puzzles is an array of 2D arrays.
     * Print each of the 2D arrays.
     * If the 2D array is null, print "used" instead.
     * 
     * Assume the input puzzles is as follows:
     *      puzzles[0] = {{'A'}}
     *      puzzles[1] = null
     *      puzzles[2] = {{'C', 'C'}, {'C', 'C'}}
     * The output should be:
     *     Puzzle 0
     *     A
     *     Puzzle 1
     *     used
     *     Puzzle 2
     *     CC
     *     CC
     */
    void printPuzzles(char[][][] puzzles) {
        for(int i = 0 ; i < puzzles.length;i++){
            System.out.printf("Puzzle %d\n",i);
            if(puzzles[i]!=null){
                for(int j = 0 ; j < puzzles[i].length;j++){
                    for(int k = 0; k < puzzles[i][j].length;k++){
                        System.out.print(puzzles[i][j][k]);
                    }
                    System.out.println();
                }
            }else{System.out.print("used\n");}

        }


    }

    /**
     * Print the map to the console.
     * 
     * The map should be printed as follows:
     * 
     *  012345678
     * a....CC...
     * b....CC...
     * c.........
     * d.........
     * e....IIIII
     * f.........
     * g.........
     * h.........
     *
     * The first row should be the column index 0 to 8.
     * The first column should be the row index a to h. 
     */
    void printMap(char[][] map) {
        char [] alpha ={'a','b','c','d','e','f','g','h'};
        System.out.println(" 012345678");
        for (int i = 0 ; i < map.length;i++){
            System.out.print(alpha[i]);
            for (int j = 0 ; j < map[i].length;j++){
                System.out.print(map[i][j]);
            }
            System.out.print("\n");
        }
    }


    /**
     * This method is used to get the input from the user and place the puzzle
     * 
     * The input map is a 2D array of characters, representing your game map.
     * The input puzzles is an array of 2D arrays, representing the puzzles that
     * you can place.
     * 
     * The method should get the input from the user, and place the puzzle to the
     * map. If the input is invalid, e.g. the index is out-of-bound, or the position
     * is invalid, the method should print some error message and ask the user to
     * to input again, until the user enter a valid input.
     * 
     * After the user has placed the puzzle, the method should update the map and
     * remove that puzzle from the puzzles array.
     */
    void getInputAndPlacePuzzle(char[][] map, char[][][] puzzles) {
        int choice;
        String Selection;
        int[]  RC = new int [2];

        do{
            Scanner in = new Scanner(System.in);
            System.out.print("Select the puzzles you want to place: ");
            choice = in.nextInt();
            while(choice < 0 || choice > 2 || puzzles[choice] == null){
                System.out.print("Invalid puzzle Index \n");
                System.out.print("Select the puzzles you want to place:  ");
                choice = in.nextInt();


            }
            System.out.print("Please enter the position to place the puzzle (e.g. a1):  ");
            Selection = in.next();
            if (validateInput(Selection)){
                RC =  convertInputToRC(Selection);
                if(canPlace(map,puzzles[choice],RC[0],RC[1])){
                    break;

                }else{
                    System.out.print("Invalid position to place the puzzle \n");

                }
            }else{
                System.out.print("Invalid position to place the puzzle \n");

            }




        }while(!validateInput(Selection) || !canPlace(map, puzzles[choice], RC[0], RC[1]));

        place(map,puzzles[choice],RC[0],RC[1]);
        puzzles[choice]= null;
    }

    /**
     * To randomize the puzzles if needed.
     * 
     * The input puzzles is an array of 2D arrays. When all the elements in the
     * array are null, the method should randomize the puzzles array.
     * 
     * After randomization, the method should assign the puzzles array with 
     * the randomized puzzles. You should use PUZZLES - a constant 3D array
     * to get the puzzles.
     */
    void randomPuzzleIfNeeded(char[][][] puzzles) {
        boolean Nullfound = true;
        for(int i = 0 ; i < puzzles.length;i++){
            if(puzzles[i]!=null){
                Nullfound=false;
                break;
            }

        }
        if(Nullfound){
            int randomIndexA = ThreadLocalRandom.current().nextInt(0,4);
            int randomIndexB = ThreadLocalRandom.current().nextInt(0,4);
            int randomIndexC = ThreadLocalRandom.current().nextInt(0,4);

            puzzles[0] = PUZZLES[randomIndexA];
            puzzles[1]= PUZZLES[randomIndexB];
            puzzles[2]=PUZZLES[randomIndexC];






        }




    }

    /**
     * To check if the game is over.
     * 
     * The gameOver condition is defined as when there is no more valid position
     * to place any puzzle from the arrays puzzles. 
     * 
     * Return true if it is game over, otherwise return false.
     */
    boolean gameOver(char [] [] map , char [] [] [] puzzles) {
        boolean NoneofThePuzzlescCanBePlaced = false;
        boolean [] Placement = new boolean[puzzles.length];
        int countPlacement = 0;

        for(int i = 0 ; i < puzzles.length ;i++){
            if(puzzles[i]!=null){
                boolean CanPPlaced = false;
                for(int a = 0 ; a < map.length;a++){
                    for(int b = 0 ; b < map[a].length;b++){
                        if(canPlace(map,puzzles[i],a,b)){
                            CanPPlaced=true;
                            break;
                        }
                    }
                    if(CanPPlaced){
                        break;
                    }
                }
                if (CanPPlaced){
                    Placement[countPlacement]= true;


                }else{
                    Placement[countPlacement]=false;
                }
                countPlacement++;


                }

            }
        for(int i = 0 ;i < Placement.length;i++){
            if(Placement[i]== true){
                return false;
            }

        }
        return true;
        // all the puzzles have valid pos so we know game must go on

    }

    /**
     * Check if the puzzlecan be placed at the position (r, c) of the map.
     * 
     * The input map is a 2D array of characters, representing the game map.
     * The input puzzle is a 2D array of characters, representing the puzzle.
     * The input r is an integer, representing the row index of the map.
     * The input c is an integer, representing the column index of the map.
     * 
     * A puzzle can be placed at the position (r, c) if the non-empty cells of the
     * puzzle do not cover any non-empty cells of the map. Also, the puzzle should
     * be placed within the boundary of the map.
     * 
     * 
     * The method should return true if the puzzle can be placed at the position (r, c)
     * of the map. Otherwise, return false.
     */
    boolean canPlace(char[][] map, char[][] puzzle, int r, int c) {
        if(r >= 0 && r <= 7 && c >=0 && c <=8){
            for(int i = 0; i < puzzle.length;i++){
                for(int k = 0; k < puzzle[i].length;k++){
                    try {
                        if (map[r + i][c + k] != EMPTY) {
                            return false;


                        }
                    }catch (Exception e){
                        return false;
                    }

                }
            }
            return true;




        }
        return false;



    }
    /**
     * This method is to place the puzzle at the position (r, c) of the map.
     * You can assume that the puzzle can be placed at the position (r, c) of the map when this 
     * method is called.
     * 
     * The map will be updated after placing the puzzle.
     * The puzzle will not be updated after the puzzle is placed.
     */
    void place(char[][] map, char[][] puzzle, int r, int c) {
        for(int i = 0; i < puzzle.length;i++){
            for(int k = 0; k < puzzle[i].length;k++) {
                map[r+i][c+k] = puzzle[i][k];
            }

        }
    }
 
    /**
     * Validate the input string.
     * 
     * Check if the input string is a valid input.
     * The input string should be a string of length 2.
     * The first character should be a lowercase letter from 'a' to 'z'.
     * The second character should be a digit from '0' to '9'.
     * 
     * Return true if the input string is valid, otherwise return false.
     * 
     * You also need to figure out how to use this method in your code
     * properly, i.e., which method should call this method.
     */
    boolean validateInput(String input) {
        if(input.length()==2){
            if(input.charAt(0) >= 'a' && input.charAt(0)<='z' && input.charAt(1) >= '0' && input.charAt(1)<= '9'){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * Convert the input string to row and column index.
     * 
     * The input string represents the position of the map, e.g. "a1", "b7", "d4", etc..
     * 
     * The method should convert the input string to the row and column index.
     * row index is between 0 to 8
     * column index is between 0 to 8
     * 
     * return an array of two integers, where the first element is the row index, 
     * and the second element is the column index.
     * 
     * Therefore, if the input string is "a1", the method should return an array of {0, 1}.
     * If the input string is "b7", the method should return an array of {1, 7}.
     */
    int[] convertInputToRC(String input) {
        int R = input.charAt(0) - 97;
        int C = input.charAt(1) - 48;
        //System.out.print(R+" "+C);
        return new int [] {R,C};

    }

    /**
     * Cancel the puzzles in the map.
     * 
     * If a cell is cancelled, it will become the EMPTY symbol '.'
     * When a row or a column is all filled with non-empty cells, the row or column
     * will be cancelled.
     * 
     * It is possible to cancel more than a row or a column at the same time.
     * 
     * The method should return the number of rows and columns that are cancelled.
     */
    int cancelPuzzles(char[][] map) {
        int RowC = 0;
        int ColC = 0;
        boolean [] RowCIndexes = new boolean[ROW];
        boolean [] ColCIndexes = new boolean[COL];
        int i = 0 ;
        int j = 0;
        // First we need to check the Rows
        for (i = 0; i < map.length; i++) {
            boolean RowFilled = true;
            for (j = 0; j < map[i].length; j++) {
                if (map[i][j] == EMPTY) {
                    RowFilled = false;
                    break;
                }
            }
            if (RowFilled) {
                RowC++;
                RowCIndexes [i] = true ;
                //System.out.println(RowCIndex);

            }
        }

        // Then we need to check the Columns
        int m = 0;
        int n = 0;
        for ( m = 0; m < map[0].length; m++) {
            boolean ColFilled = true;
            for ( n = 0; n < map.length; n++) {
                if (map[n][m] == EMPTY) {
                    ColFilled = false;
                    break;
                }
            }
            if (ColFilled) {
                ColC++;
                ColCIndexes [m] = true;
                //System.out.println(ColCIndex);

            }
        }



        if(RowC != 0) {
            for(int k = 0 ; k < RowCIndexes.length;  k++){
                //System.out.println("RowCIndexes: "+RowCIndexes);
                if(RowCIndexes[k]){
                    for (int p = 0; p < map[k].length; p++) {
                        map[k][p] = EMPTY;
                        //System.out.print("I am cancelling the row");
                    }

                }

            }

        }
        if(ColC != 0) {
            for(int k = 0 ; k < ColCIndexes.length;  k++){
                if(ColCIndexes[k]){
                    for (int p = 0; p < map.length; p++) {
                        map[p][k] = EMPTY;
                        //System.out.print("I am cancelling the row");
                    }

                }

            }

        }



        return RowC + ColC;
    }

}