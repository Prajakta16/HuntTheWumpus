package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Class that represents a Maze in a game which has one or many paths from any point in the maze to
 * any other point in the maze. The placement of rooms, doors, and hallways in the grid is usually
 * randomly selected with some constraint that is specified at the time of creation. The maze also
 * has the following requirements: 1. There is one start and one goal location cell 2. 20% of
 * locations (at random) have gold coins in them that the player can pick up 3. 10% of locations (at
 * random) have a thief that takes some of the player's gold coins
 */
public abstract class Maze implements InterfaceMaze {
  private Player player;

  //adjacency matrix represents wall relation between all the rooms in the maze
  //gridAdjMatrix[room1][room2] = 1 indicates there is a path and gridAdjMatrix[room1][room2] = 0
  //indicates there is a wall
  private final int[][] gridAdjMatrix;
  private final List<Room> rooms;

  private final int countRooms;
  private final int row;
  private final int col;
  private final boolean wrappingFlag;
  private final int minWallsRemovalRequired;
  private Map<FeatureType, Integer> applicableFeatures;

  /**
   * Constructor for the maze that constructs a maze grid of dimensions row*column.
   *
   * @param row          number of rows for the maze
   * @param col          number of columns for the maze
   * @param wrappingFlag wrapping allowance
   */
  public Maze(int row, int col, boolean wrappingFlag) {
    this.row = row;
    this.col = col;
    this.wrappingFlag = wrappingFlag;
    this.countRooms = row * col;
    this.gridAdjMatrix = new int[countRooms][countRooms];
    this.rooms = new ArrayList<>();
    this.minWallsRemovalRequired = countRooms - 1;
    this.applicableFeatures = new HashMap<>();
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

  private void assignCavesAndTunnels() {
    int[] countDoorsFromRoom = getCountDoorsFromRoom();
    List<Room> roomsUnderConstruction = new ArrayList<>();
    int caveNum = 0;
    for (int i = 0; i < countRooms; i++) { // add all rooms to the maze
      Room room;
      if (countDoorsFromRoom[i] == 2) {
        room = new Tunnel(i);
      } else {
        room = new Cave(i, "C" + caveNum);
        caveNum++;
      }
      roomsUnderConstruction.add(room);
    }

    Room room1;
    Room room2;
    int[][] gridAdjMatrix = this.getGridAdjMatrix();

    for (int i = 0; i < gridAdjMatrix.length; i++) {

      //for all rooms except belonging to last column in the grid
      if ((i + 1) % this.getCol() != 0 || i == 0) {
        if (gridAdjMatrix[i][i + 1] == 1) { //path exists
          room1 = getRoomById(i, roomsUnderConstruction);
          room2 = getRoomById(i + 1, roomsUnderConstruction);
          room1.addRoomInDirection(Direction.EAST, room2);
          room2.addRoomInDirection(Direction.WEST, room1);
        }
      } else { //last column
        if (gridAdjMatrix[i][i - (this.getCol() - 1)] == 1) {
          room1 = getRoomById(i, roomsUnderConstruction);
          room2 = getRoomById(i - (this.getCol() - 1), roomsUnderConstruction);
          room1.addRoomInDirection(Direction.EAST, room2);
          room2.addRoomInDirection(Direction.WEST, room1);
        }
      }

      //for all rooms except belonging to last row in the grid
      if (i < this.getCountRooms() - this.getCol()) {
        if (gridAdjMatrix[i][i + this.getCol()] == 1) { //path exists
          room1 = getRoomById(i, roomsUnderConstruction);
          room2 = getRoomById(i + this.getCol(), roomsUnderConstruction);
          room1.addRoomInDirection(Direction.SOUTH, room2);
          room2.addRoomInDirection(Direction.NORTH, room1);
        }
      } else { //last row
        if (gridAdjMatrix[i][i - ((this.getRow() - 1) * this.getCol())] == 1) {
          room1 = getRoomById(i, roomsUnderConstruction);
          room2 = getRoomById(i - ((this.getRow() - 1) * this.getCol()), roomsUnderConstruction);
          room1.addRoomInDirection(Direction.SOUTH, room2);
          room2.addRoomInDirection(Direction.NORTH, room1);
        }
      }
    }

    this.rooms.addAll(roomsUnderConstruction);
    getAdjacentCaves();
  }

  private int[] getCountDoorsFromRoom() {
    int[] countDoors = new int[countRooms];

    for (int i = 0; i < gridAdjMatrix.length; i++) {

      //for all rooms except belonging to last column in the grid
      if ((i + 1) % this.getCol() != 0 || i == 0) {
        if (gridAdjMatrix[i][i + 1] == 1) { //path exists
          countDoors[i]++;
          countDoors[i + 1]++;
        }
      } else { //last column
        if (gridAdjMatrix[i][i - (this.getCol() - 1)] == 1) {
          countDoors[i]++;
          countDoors[i - (this.getCol() - 1)]++;
        }
      }

      //for all rooms except belonging to last row in the grid
      if (i < this.getCountRooms() - this.getCol()) {
        if (gridAdjMatrix[i][i + this.getCol()] == 1) { //path exists
          countDoors[i]++;
          countDoors[i + this.getCol()]++;
        }
      } else { //last row
        if (gridAdjMatrix[i][i - ((this.getRow() - 1) * this.getCol())] == 1) {
          countDoors[i]++;
          countDoors[i - ((this.getRow() - 1) * this.getCol())]++;
        }
      }
    }
    return countDoors;

  }

  private void getAdjacentCaves() {
    Map<Direction, Room> nextMoves;
    Room adj;
    for (Room cave : getAllCaves()) { // for every cave
      nextMoves = cave.getPossibleMoves();
      for (Direction direction : nextMoves.keySet()) {
        // if it is a tunnel, move ahead until cave is found
        if (!nextMoves.get(direction).isCave()) { //if current room is tunnel
          Room tunnel = nextMoves.get(direction);

          List<Room> connectingTunnels = tunnel.findAllConnectingTunnels(direction);
          if (connectingTunnels != null && connectingTunnels.size() != 0) {
            for (Room connectingTunnel : connectingTunnels) {
              cave.addAdjacentTunnel(direction, connectingTunnel);
            }
          }

          adj = tunnel.findCaveAtDistance(direction, 1); //if next room is cave
          cave.addAdjacentCave(direction, adj);
        } else {
          cave.addAdjacentCave(direction, nextMoves.get(direction));
        }
      }
    }
  }

  @Override
  public Room getRoomById(int roomId, List<Room> roomsList) {
    for (Room room : roomsList) {
      if (room.getRoomId() == roomId) {
        return room;
      }
    }

    System.out.println("Room not found");
    throw new IllegalArgumentException();
  }


  @Override
  public void addFeatures(Map<FeatureType, Integer> features, Random random) {
    applicableFeatures = features;

    assignCavesAndTunnels();
    List<Room> caves = getAllCaves();

    if (features.containsKey(FeatureType.WUMPUS)) {
      addFeatureHelper(FeatureType.WUMPUS, 1, caves, random);
    }
    for (FeatureType featureType : features.keySet()) {
      if (featureType != FeatureType.WUMPUS) {
        addFeatureHelper(featureType, features.get(featureType), caves, random);
      }
    }
  }

  private void addFeatureHelper(FeatureType featureType, int percentage, List<Room> caves,
                                Random random) {
    if (featureType == FeatureType.WUMPUS) {
      caves.get(random.nextInt(caves.size())).addNewFeature(featureType);
    } else {
      int numCavesToAddFeature = (int) Math.round(caves.size() * (float) percentage / 100);

      for (int i = 0; i < numCavesToAddFeature; i++) {
        boolean foundRoom = false;
        int rand;

        while (!foundRoom) {
          rand = random.nextInt(caves.size());

          // if room does not already has the feature we are trying to assign
          if (!caves.get(rand).getFeatures().get(featureType)) {
            if (!caves.get(rand).getFeatures().get(FeatureType.WUMPUS)) {
              caves.get(rand).addNewFeature(featureType);
              foundRoom = true;
            }
          }
        }
      }
    }
  }

  @Override
  public List<Room> getAllCaves() {
    List<Room> caves = new ArrayList<>();
    for (Room room : this.rooms) {
      if (room.isCave()) {
        caves.add(room);
      }
    }
    return caves;
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
    Room room = getRoomById(start, rooms);
    if (!room.isCave()) {
      throw new IllegalArgumentException("Player cannot start in a tunnel");
    }
    this.player = player;
    this.player.setRoom(room);

    if (this.player.getRoom().getFeatures().get(FeatureType.GOLD)) {
      this.player.collectGold();
    }
  }

  @Override
  public void assignMultiplePlayersRandomStartPositions(List<InterfacePlayer> players,
                                                        int randomSeed) {
    Random random = new Random(randomSeed);
    int start;
    boolean foundStart;
    Set<Integer> uniqueStartPositions = new HashSet<>();

    for (InterfacePlayer player : players) {
      foundStart = false;
      while (!foundStart) { //repeat until we don't find a start location
        start = random.nextInt(getAllCaves().size());
        Map<FeatureType, Boolean> features = getAllCaves().get(start).getFeatures();
        if (!features.get(FeatureType.WUMPUS) && !features.get(FeatureType.BAT)
                    && !features.get(FeatureType.PIT)) {
          if (!uniqueStartPositions.contains(start)) {
            foundStart = true;
            player.setRoom(getAllCaves().get(start));
            getAllCaves().get(start).setVisited();
            uniqueStartPositions.add(start);
            player.setIsDead(false); // initialization
            player.setHasWon(false);
          }
        }
      }
    }
  }

  @Override
  public void transportPlayerByBats(Random random) {
    int caveNumber = random.nextInt(getAllCaves().size());
    int roomNumber = getAllCaves().get(caveNumber).getRoomId();
    player.setRoom(getRoomById(roomNumber, rooms));
  }

  @Override
  public void finishMazeCreation() {
    int maxEdges = row * (col - 1) + col * (row - 1); //might change if wrapping is applied

    if (!this.wrappingFlag && this.getAllWallsInMaze().size() > maxEdges - (row * col - 1)) {
      System.out.println("Path does not exist as walls are too many");
      throw new IllegalStateException();
    }

    int countCavesForFeature;
    int expectedCavesForFeature;
    for (FeatureType featureType : applicableFeatures.keySet()) {
      if (featureType.equals(FeatureType.WUMPUS)) {
        if (getCavesWithFeature(FeatureType.WUMPUS).size() != 1) {
          System.out.println("Wumpus is not present in one cave");
          throw new IllegalStateException();
        }
      } else {
        countCavesForFeature = getCavesWithFeature(featureType).size();
        expectedCavesForFeature = (int) Math.round(
                getAllCaves().size() * (float) applicableFeatures.get(featureType) / 100);

        if (countCavesForFeature != expectedCavesForFeature) {
          System.out.println("Feature not added correctly -- " + featureType.toString());
          throw new IllegalStateException();
        }
      }
    }

    printMaze();
  }

  //Helper methods and getters
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
    //    System.out.println("Final connections between all rooms are as follows:");
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

    for (FeatureType featureType : applicableFeatures.keySet()) {
      printCavesWithFeature(featureType);
    }

    //printAllRooms();
    //printAllCavesWithAdjCaves();

    System.out.println();
  }

  private void printAllRooms() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Room room : rooms) {
      stringBuilder.append(room.getRoomId()).append("--");
      if (room.isCave()) {
        stringBuilder.append("cave--");
      } else {
        stringBuilder.append("tunnel--");
      }
      for (Direction direction : room.getPossibleMoves().keySet()) {
        stringBuilder.append(direction).append(":").append(room.getPossibleMoves().get(direction)
                                                                   .getRoomId());
      }
      stringBuilder.append("\n");
    }
    System.out.println(stringBuilder.toString());
  }

  private List<Room> getCavesWithFeature(FeatureType featureType) {
    List<Room> caves = getAllCaves();
    List<Room> cavesWithFeature = new ArrayList<>();
    for (Room cave : caves) {
      if (cave.getFeatures().get(featureType)) {
        cavesWithFeature.add(cave);
      }
    }
    return cavesWithFeature;
  }

  @Override
  public List<Room> getRooms() {
    return rooms;
  }

  private void printCavesWithFeature(FeatureType featureType) {
    List<Room> cavesWithFeature = getCavesWithFeature(featureType);
    System.out.println("Rooms with " + featureType.toString());
    for (Room room : cavesWithFeature) {
      System.out.print(room.getRoomId() + " ");
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
