package maze;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class that represents a Maze in a game which has one or many paths from any point in the maze to
 * any other point in the maze. The placement of rooms, doors, and hallways in the grid is usually
 * randomly selected with some constraint that is specified at the time of creation.
 * The maze also has the following requirements:
 * 1. There is one start and one goal location cell
 * 2. 20% of locations (at random) have gold coins in them that the player can pick up
 * 3. 10% of locations (at random) have a thief that takes some of the player's gold coins
 */
public abstract class Maze implements InterfaceMaze {
  private static int PERCENT_ROOMS_WITH_GOLD = 20;
  private static int PERCENT_ROOMS_WITH_THIEF = 10;

  private Player player;

  //adjacency matrix represents wall relation between all the rooms in the maze
  //gridAdjMatrix[room1][room2] = 1 indicates there is a path and gridAdjMatrix[room1][room2] = 0
  //indicates there is a wall
  private final int[][] gridAdjMatrix;

  //hasGold[room_i] = true indicates presence of gold in room_i
  private final boolean[] hasGold;

  //hasThief[room_i] = true indicates presence of thief in room_i
  private final boolean[] hasThief;
  private final int countRooms;
  private final int row;
  private final int col;
  private final boolean wrappingFlag;
  private final int minWallsRemovalRequired;

  /**
   * Constructor for the maze that constructs a maze grid of dimensions row*column.
   * @param row number of rows for the maze
   * @param col number of columns for the maze
   * @param wrappingFlag wrapping allowance
   */
  public Maze(int row, int col, boolean wrappingFlag) {
    this.row = row;
    this.col = col;
    this.wrappingFlag = wrappingFlag;
    this.countRooms = row * col;
    this.gridAdjMatrix = new int[countRooms][countRooms];
    this.hasGold = new boolean[countRooms]; //will be initialized by default to false
    this.hasThief = new boolean[countRooms]; //will be initialized by default to false
    this.minWallsRemovalRequired = countRooms - 1;
  }

  //Methods to mutate the maze object
  @Override
  public void wallDemolish(int i, int j) {
    gridAdjMatrix[i][j] = 1;
    gridAdjMatrix[j][i] = 1;
  }

  @Override
  public Set<int[]> getAllWallsInMaze() {
    Set<int[]> walls = new HashSet<>();

    int[][] gridAdjMatrix = this.getGridAdjMatrix();
    for (int i = 0; i < gridAdjMatrix.length; i++) {
      int[] edge = new int[2];

      //for all rooms except belonging to last column in the grid
      if ((i + 1) % this.getCol() != 0 || i == 0) {
        if (gridAdjMatrix[i][i + 1] == 0) { //edge exists
          edge[0] = i;
          edge[1] = i + 1;
          walls.add(edge);
        }
      }

      //for all rooms except belonging to last row in the grid
      if (i < this.getCountRooms() - this.getCol()) {
        if (gridAdjMatrix[i][i + this.getCol()] == 0) { //edge exists
          edge[0] = i;
          edge[1] = i + this.getCol();
          walls.add(edge);
        }
      }
    }
    return walls;
  }

  @Override
  public void addGold(Random random) {
    int roomsWithGold = (int) Math.round(countRooms * (float) PERCENT_ROOMS_WITH_GOLD / 100);

    for (int i = 0; i < roomsWithGold; i++) {
      boolean foundRoom = false;
      int rand;

      while (!foundRoom) {
        rand = random.nextInt(countRooms);
        if (!this.hasThief(rand) && !this.hasGold(rand)) {
          this.hasGold[rand] = true;
          foundRoom = true;
        }
      }
    }
  }

  @Override
  public void addThieves(Random random) {
    int roomsWithThieves = (int) Math.round(countRooms * (float) PERCENT_ROOMS_WITH_THIEF / 100);

    for (int i = 0; i < roomsWithThieves; i++) {
      boolean foundRoom = false;
      int rand;

      while (!foundRoom) {
        rand = random.nextInt(countRooms);
        if (!this.hasThief(rand) && !this.hasGold(rand)) {
          this.hasThief[rand] = true;
          foundRoom = true;
        }
      }
    }
  }

  @Override
  public void removeInsideWalls(Random random) {
    int[] parent = applyPrims(random);
    for (int i = 0; i < countRooms; i++) {
      this.wallDemolish(parent[i], i);
    }
  }

  @Override
  public void removeAdditionalInsideWalls(int numWallsToRemove) {
    if (this.equalsPerfect()) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void removeWrappingWalls(Random random, int numWrappingWallsToRemove) {
    if (this.equalsPerfect()) {
      throw new IllegalStateException();
    }
  }

  //Assigns player to a maze
  @Override
  public void assignPlayer(Player player, int start) {
    this.player = player;
    this.player.setPosition(start);

    if (hasGold(start)) {
      this.player.collectGold();
    }
  }

  @Override
  public void removeGold(Integer nextPosition) {
    this.hasGold[nextPosition] = false;
  }

  @Override
  public void finishMazeCreation() {
    int maxEdges = row * (col - 1) + col * (row - 1); //might change if wrapping is applied

    if (!this.wrappingFlag && this.getAllWallsInMaze().size() > maxEdges - (row * col - 1)) {
      System.out.println("Path does not exist as walls are too many");
      throw new IllegalStateException();
    }

    int countThieves = 0;
    int expectedRoomsWithThieves = (int) Math.round(countRooms * (float) PERCENT_ROOMS_WITH_THIEF
                                                            / 100);
    for (int i = 0; i < countRooms; i++) {
      if (this.hasThief(i)) {
        countThieves++;
      }
    }
    if (countThieves != expectedRoomsWithThieves) {
      System.out.println("Thieves not added correctly");
      throw new IllegalStateException();
    }

    int countGold = 0;
    int expectedRoomsWithGold = (int) Math.round(countRooms * (float) PERCENT_ROOMS_WITH_GOLD
                                                         / 100);
    for (int i = 0; i < countRooms; i++) {
      if (this.hasGold(i)) {
        countGold++;
      }
    }
    if (countGold != expectedRoomsWithGold) {
      System.out.println("Gold not added correctly");
      throw new IllegalStateException();
    }
    printMaze();
  }

  //Helper methods and getters
  @Override
  public boolean hasGold(int roomNumber) {
    return hasGold[roomNumber];
  }

  @Override
  public boolean hasThief(int roomNumber) {
    return hasThief[roomNumber];
  }

  @Override
  public boolean equalsPerfect() {
    return false;
  }

  @Override
  public boolean equalsImperfect() {
    return false;
  }

  @Override
  public int[][] getGridAdjMatrix() {
    return gridAdjMatrix;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }

  @Override
  public boolean getWrappingFlag() {
    return wrappingFlag;
  }

  @Override
  public int getCountRooms() {
    return countRooms;
  }

  @Override
  public boolean[] getHasGold() {
    return hasGold;
  }

  @Override
  public boolean[] getHasThief() {
    return hasThief;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  // Methods to randomly assign weight to edges and apply Prims to find a MST.
  private int[][] assignRandomWeightToEdges(Random random) {

    int row = this.getRow();
    int column = this.getCol();
    int countRooms = this.getCountRooms();
    int[][] randomGridWeight = new int[countRooms][countRooms];

    if (this.getWrappingFlag() && this.equalsPerfect()) {
      int room1;
      int room2;

      for (int rowNumber = 0; rowNumber < row; rowNumber++) {
        room1 = rowNumber * column;
        room2 = room1 + column - 1;
        randomGridWeight[room1][room2] = random.nextInt(countRooms * 3) + 1;
      }

      for (int colNumber = 0; colNumber < column; colNumber++) {
        room1 = colNumber;
        room2 = room1 + (row - 1) * column;
        randomGridWeight[room1][room2] = random.nextInt(countRooms * 3) + 1;
      }
    }

    for (int i = 0; i < countRooms; i++) {
      if ((i + 1) % column != 0 || i == 0) { //for all rooms except ones in last column in the grid
        randomGridWeight[i][i + 1] = random.nextInt(countRooms * 3) + 1;
      }

      if (i < countRooms - column) { //for all rooms except belonging to last row in the grid
        randomGridWeight[i][i + column] = random.nextInt(countRooms * 3) + 1;
      }
    }
    return randomGridWeight;
  }

  private void printRandomGridWeight(int[][] randomGridWeight) {
    for (int[] ints : randomGridWeight) {
      for (int j = 0; j < randomGridWeight[0].length; j++) {
        System.out.print(ints[j] + " ");
      }
      System.out.println();
    }
  }

  private int[] applyPrims(Random random) {
    int[][] randomGridWeight = assignRandomWeightToEdges(random);
    //printRandomGridWeight(randomGridWeight);
    int[] parent = new int[countRooms];
    int[] weight = new int[countRooms];

    boolean[] visited = new boolean[countRooms];

    for (int i = 0; i < countRooms; i++) {
      weight[i] = Integer.MAX_VALUE;
      parent[i] = Integer.MAX_VALUE;
    }
    weight[0] = 0;
    parent[0] = 0;

    int min;
    int nextNode;
    for (int i = 0; i < minWallsRemovalRequired; i++) {
      min = Integer.MAX_VALUE;
      nextNode = -1;

      for (int j = 0; j < countRooms; j++) {
        if (!visited[j] && weight[j] < min) {
          min = weight[j];
          nextNode = j;
        }
      }

      visited[nextNode] = true;
      for (int j = 0; j < countRooms; j++) {
        if (!visited[j] && randomGridWeight[nextNode][j] != 0
                    && randomGridWeight[nextNode][j] < weight[j]) {
          weight[j] = randomGridWeight[nextNode][j];
          parent[j] = nextNode;
        } else if (!visited[j] && randomGridWeight[j][nextNode] != 0
                           && randomGridWeight[j][nextNode] < weight[j]) {
          weight[j] = randomGridWeight[j][nextNode];
          parent[j] = nextNode;
        }
      }
    }

    return parent;
  }

  // Methods to print the maze on screen
  protected void printMaze() {
    System.out.println("Final connections between all rooms are as follows:");
    //    for (int[] adjMatrix : gridAdjMatrix) {
    //      for (int n = 0; n < gridAdjMatrix[0].length; n++) {
    //        System.out.print(adjMatrix[n] + "\t");
    //      }
    //      System.out.println();
    //    }
    //    System.out.println("\n");

    System.out.println("Maze is wrapping " + this.getWrappingFlag());
    System.out.println("Rooms look like:");
    printHorizontalBoundary();
    for (int i = 0; i < countRooms; i = i + col) {
      printVerticalWalls(i);
      printHorizontalWalls(i);
    }

    System.out.println("Rooms with gold:");
    for (int i = 0; i < hasGold.length; i++) {
      if (hasGold[i]) {
        System.out.print(i + " ");
      }
    }

    System.out.println();
    System.out.println("Rooms with thieves:");
    for (int i = 0; i < hasThief.length; i++) {
      if (hasThief[i]) {
        System.out.print(i + " ");
      }
    }
    System.out.println();

  }

  private void printHorizontalWalls(int index) {
    if (index >= (row - 1) * col) { //the last row
      printHorizontalBoundary();
    } else {
      for (int count = index; count <= index + col - 1; count++) {
        if (gridAdjMatrix[count][count + col] == 0) {
          System.out.print("__\t");
        } else {
          System.out.print("\t");
        }
        if ((count + 1) % col == 0) { //move to next row
          System.out.print("\n");
        }
      }
    }
  }

  private void printHorizontalBoundary() {
    int countRoomsOtherThanLastRow = (row - 1) * col;
    for (int count = 0; count <= col - 1; count++) {
      if (gridAdjMatrix[count][count + countRoomsOtherThanLastRow] == 0) {
        System.out.print("__\t");
      } else {
        System.out.print("\t");
      }
    }
    System.out.println();
  }

  private void printVerticalBoundary(int index) {
    if (index % col == 0) { //first element in the row
      if (gridAdjMatrix[index][index + col - 1] == 0) { //indicates a wall
        System.out.print("|");
      } else {
        System.out.print(" ");
      }
    } else if ((index + 1) % col == 0) { //last element in the row
      if (gridAdjMatrix[index - (col - 1)][index] == 0) { //indicates a wall
        System.out.print("|");
      } else {
        System.out.print(" ");
      }
    }

  }

  private void printVerticalWalls(int index) {
    for (int count = index; count <= index + col - 1; count++) {
      if (count % col == 0) { //first room in the row
        printVerticalBoundary(count);
      }

      System.out.print(count + "\t"); //print room number
      if ((count + 1) % col == 0) { //last room in the row move to next row
        printVerticalBoundary(count);
        System.out.print("\n");
      } else {
        if (gridAdjMatrix[count][count + 1] == 0) { //indicates a wall
          System.out.print("|");
        } else {
          System.out.print(" ");
        }
      }

    }
  }

}
