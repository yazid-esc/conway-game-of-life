
package gameoflife;

import java.util.Objects;

/**
TODO: JAVADOC
*/
public class Board {

  // CLASS ATTRIBUTES /////
  private static final int SIDE = 140;
  private static final char EMPTY = '_';
  private static final char FULL = '#';

  // ATTRIBUTES /////
  private char[][] board;
  private int currentGeneration = 0;

  // CONSTRUCTORS /////
  // TODO: JAVADOC
  public Board() {
    this.board = new char[SIDE][SIDE];
  }

  // TODO: JAVADOC
  public Board(char[][] board) throws Exception{
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
          boolean validCharacter = (currentChar == EMPTY) || (currentChar == FULL);

          if(!validCharacter)
            throw new Exception("Invalid character '" + currentChar + "' in row " + (rowIndex + 1) + ", column " + (columnIndex + 1));
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

  public void print() {
    for(int rowIndex = 0; rowIndex < 140; ++rowIndex) {
      // If we're printing the first column (a.k.a barely about to start printing the board)
      if(rowIndex == 0)
        System.out.printf("%10s", "Gen " + this.currentGeneration);
      else
        System.out.printf("%10s", "");

      for(int columnIndex = 0; columnIndex < 140; ++columnIndex) {
        System.out.print((this.board)[rowIndex][columnIndex] + "");
      }

      System.out.println();
    }

    System.out.println("\n\n\n\n");
  }

  public void evolveOneGeneration() {
    char[][] nextBoard = (char[][])((this.board).clone());

    for(int rowIndex = 0; rowIndex < 140; ++rowIndex) {
      for(int columnIndex = 0; columnIndex < 140; ++columnIndex) {
        int totalNeighbors = countNeighbors(rowIndex, columnIndex);

        switch(totalNeighbors) {
          // Check for too few neighbors --> DIES
          case 0:
          case 1:
            {
              nextBoard[rowIndex][columnIndex] = EMPTY;
              break;
            }

          // Check for a good amount of neighbors --> SURVIVE
          case 2:
          case 3:
           {
             nextBoard[rowIndex][columnIndex] = FULL;
             break;
           }

          // If none from above then: too many neighbors --> DIES
          default:
            nextBoard[rowIndex][columnIndex] = EMPTY;
        }
      }
    }

    ++(this.currentGeneration);
    this.board = nextBoard;
  }

  public void evolveMultipleGenerations(int numberOfGenerations) throws Exception{
    // Parameter checking
    if(numberOfGenerations < 0)
      throw new Exception("Parameter x cannot be a negative number");

    for(int i = 0; i < numberOfGenerations; ++i)
      this.evolveOneGeneration();
  }

  // TODO: JAVADOC
  private int countNeighbors(int r, int c) {
    int totalNeighbors = 0;

    // Check if cell is corner cell
    if((r == 0) && (c == 0)) {             // upper left corner cell
      totalNeighbors += checkRight(r, c) + checkBottomRight(r, c) + checkBottom(r, c);
      return totalNeighbors;
    } else if((r == 0) && (c == 139)) {    // upper right corner cell
      totalNeighbors += checkBottom(r, c) + checkBottomLeft(r, c) + checkLeft(r, c);
      return totalNeighbors;
    } else if((r == 139) && (c == 0)) {    // lower left corner cell
      totalNeighbors += checkTop(r, c) + checkTopRight(r, c) + checkRight(r, c);
      return totalNeighbors;
    } else if((r == 139) && (c == 139)){   // lower right corner cell
      totalNeighbors += checkLeft(r, c) + checkTopLeft(r, c) + checkTop(r, c);
      return totalNeighbors;

    }

    // Check if cell is aligned to any board edge
    if(r == 0) {                                      // cell along top of board
      totalNeighbors += checkRight(r, c) + checkBottomRight(r, c) + checkBottom(r, c) + checkBottomLeft(r, c) + checkLeft(r, c);
      return totalNeighbors;
    } else if(r == 139) {                             // cell along bottom of board
      totalNeighbors += checkLeft(r, c) + checkTopLeft(r, c) + checkTop(r, c) + checkTopRight(r, c) + checkRight(r, c);
      return totalNeighbors;
    } else if(c == 0) {                               // cell along left side of board
      totalNeighbors += checkTop(r, c) + checkTopRight(r, c) + checkRight(r, c) + checkBottomRight(r, c) + checkBottom(r, c);
      return totalNeighbors;
    } else if(c == 139) {                             // cell along right side of board
      totalNeighbors += checkBottom(r, c) + checkBottomLeft(r, c) + checkLeft(r, c) + checkTopLeft(r, c) + checkTop(r, c);
      return totalNeighbors;
    }

    // Check non-special case cells (cells that have neighbors all around them)
    {
      totalNeighbors += checkTop(r, c) + checkTopRight(r, c) + checkRight(r, c) + checkBottomRight(r, c) +
                        checkBottom(r, c) + checkBottomLeft(r, c) + checkLeft(r, c) + checkTopLeft(r, c);

      return totalNeighbors;
    }
  }

  // TODO: JAVADOC
  public int checkTop(int rowIndex, int columnIndex) {
    // Check cell on top
    if((this.board)[rowIndex - 1][columnIndex] == FULL)
      return 1;

    return 0;
  }

  //TODO: JAVADOC
  public int checkRight(int rowIndex, int columnIndex) {
    // Check cell to right
    if((this.board)[rowIndex][columnIndex + 1] == FULL)
      return 1;

    return 0;
  }

  // TODO: JAVADOC
  public int checkBottom(int rowIndex, int columnIndex) {
    //Check cell on bottom
    if((this.board)[rowIndex + 1][columnIndex] == FULL)
      return 1;

    return 0;
  }

  // TODO: JAVADOC
  public int checkLeft(int rowIndex, int columnIndex) {
    // Check cell to left
    if((this.board)[rowIndex][columnIndex - 1] == FULL)
      return 1;

    return 0;
  }

  // TODO: JAVADOC
  public int checkTopRight(int rowIndex, int columnIndex) {
    // Check cell top right
    if((this.board)[rowIndex - 1][columnIndex + 1] == FULL)
      return 1;

    return 0;
  }

  // TODO: JAVADOC
  public int checkBottomRight(int rowIndex, int columnIndex) {
    // Check cell bottom right
    if((this.board)[rowIndex + 1][columnIndex + 1] == FULL)
      return 1;

    return 0;
  }

  // TODO: JAVADOC
  public int checkBottomLeft(int rowIndex, int columnIndex) {
    try {
    // Check cell bottom left
    if((this.board)[rowIndex + 1][columnIndex - 1] == FULL)
      return 1;

    return 0;
  } catch(ArrayIndexOutOfBoundsException aioobe) {
    System.out.println(String.format("method checkBottomLeft(%d, %d)", rowIndex, columnIndex));
  }

  return -1;
  }

  // TODO: JAVADOC
  public int checkTopLeft(int rowIndex, int columnIndex) {
    // Check cell top left
    if((this.board)[rowIndex - 1][columnIndex - 1] == FULL)
      return 1;

    return 0;
  }
}
