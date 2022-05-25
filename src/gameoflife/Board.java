
package gameoflife;

/**
TODO: JAVADOC
*/
public class Board {

  // CLASS ATTRIBUTES /////
  private static final int SIDE = 140;
  private static final char EMPTY = '_';
  private static final char ENTITY = '#';

  // ATTRIBUTES /////
  private char[][] board;
  private int currentGeneration = 0;

  // CONSTRUCTORS /////
  // TODO: JAVADOC
  public Board() {
    this.board = new char[SIZE][SIZE];
  }

  // TODO: JAVADOC
  public Board(char[][] board) {
    // Parameter checking
    {
      // Check for null value
      Objects.requireNonNull(board);

      // Check board size
      int sideOne = board.length;                                     // Check for 140 rows
      if(sideOne != 140)
        throw new Exception("Invalid array size: " + sideOne);

      for(int index = 0; index < sideOne; ++index) {                              // Check for 140 columns (in all rows)
        int sideTwo = board[index].length;

        if(sideTwo != 140)
          throw new Exception("Invalid array size for row at index " + index + ": " + sideTwo);
      }

      // Check for valid game characters on board
      for(int rowIndex = 0; rowIndex < 140; ++rowIndex) {
        for(int columnIndex  = 0; columnIndex < 140; ++columnIndex) {
          char currentChar = board[rowIndex][columnIndex];
          boolean validCharacter = (currentChar == EMPTY) || (currentChar == ENTITY);

          if(!validCharacter)
            throw new Exception("Invalid character '" + currentChar + "' in row " (rowIndex + 1) + ", column " + (columnIndex + 1));
        }
      }


    }// end parameter checking

    this.board = board;
  }

  // ACCESSORS /////
  public char[][] getBoard() {
    return this.board;
  }

  public int getCurrentGeneration() {
    return this.currentGeneration;
  }

  // MUTATORS /////
  // no mutators - class user must create new board

  
}
