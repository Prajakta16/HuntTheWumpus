# HOMEWORK 3 : BUILDING MAZE

###DESCRIPTION
A perfect maze is the simplest type of maze for a computer to generate and solve. It is defined as a maze which has one and only one path from any point in the maze to any other point in the maze. This means that the maze has no inaccessible sections, no circular paths, no open areas.
Each cell in the grid represents a location in the maze that has a potential exit to the north, south, east, and west. One way to look at the perfect maze is that each location is a hallway that twists and turns its way from one place to another. The challenge for this type of maze is to find the direct path from one place (the pink square) to another (the blue one).

<br>
In the non-perfect maze, each cell in the grid also represent a location in the maze, but there can be multiple paths between any two cells. This form is useful in several applications. Computer games, for instance, use this kind of maze to create a map of the world by giving locations in the maze different characteristics. For instance, a room maze categorizes locations in the maze as either rooms or hallways, where a hallway has exactly two doors while a room has 1, 3 or 4 doors. In this example of a room maze, rooms are pictured as circles, hallways are channels. Since locations at the top, bottom, left and right can "wrap" to the other side, we call this a wrapping room maze.

<br>
The placement of rooms, doors, and hallways in the grid is usually randomly selected with some constraint that is specified at the time of creation. In general, we start with a perfect maze (since it the simplest to build) and then additional pathways are added until the constraint is met.

<br>
There are a number of algorithms for building perfect mazes including Kruskal's algorithm (Links to an external site.) and Prim's algorithm (Links to an external site.). Each of these requires starting with an undirected graph whose nodes represents locations in the maze, and whose edges represent walls between locations. For this assignment, you will implement a modification of Kruskal's algorithm.

<br>
To build a maze, we will need to specify the number of rows, the number of columns, and the number of walls remaining in the maze (this is our constraint). Given the number of locations in the maze as n = numberOfRows x numberOfColumns, the remaining number of walls must be between 0 (a maze where all locations are rooms with 4 doors) and numberOfEdges - n + 1 (a perfect maze). In this video, we demonstrate how to modify Kruskal's algorithm to build these mazes:

####REQUIREMENT
Design and implement the interfaces/classes to generate perfect mazes, room mazes, and wrapping room mazes in a way that captures their similarities and accurately represents the relevant data. For the purposes of this assignment, the maze also has the following requirements:

1. There is one goal location cell

2. 20% of locations (at random) have gold coins in them that the player can pick up

3. 10% of locations (at random) have a thief that takes some of the player's gold coins

4. The maze can produce the player's location and gold count

5. The maze can produce the possible moves of the player (North, South, East or West) from their current location

6. The player can make a move by specifying a direction

7. The player should be able to collect gold such that
	1. a player "collects" gold by entering a room that has gold which is then removed from the room
	2. a player "loses" 10% of their gold by entering a room with a thief
8. Your maze should not provide a way to dump the maze to the screen for anything other debugging purposes.

9. In addition, develop a driver program that uses command line arguments to:

	1. specify the size of the maze

	2. specify whether the maze is perfect or a non-perfect maze, and whether it is wrapping or non-wrapping

	3. if it is non-perfect, specify the number of walls remaining 

	4. specify the starting point of the player

	5. specify the goal location

10. Your program should then be able to demonstrate a player navigating within the maze, collecting gold, being robbed, and reaching the goal location (see details below for specific scenarios)

###SUBMISSION SUMMARY
In this homework I have designed and implemented a solution that can be used to generate a random maze and let a player find a path through the maze.

###HOW TO USE
All the code is in src/.
All the tests are in be test/.
Original, revised design document, the JAR file, example runs of program are in res/.

The program can be run using the JAR file within the res/ folder in the zipped file.
1. Unzip submission folder
2. Go to the to res/ folder using command prompt
3. Execute "java -jar HW3Maze.jar"
4. Program should successfully execute

###PARTS COMPLETE
All the parts and requirements of the problem statement are completed and the tests have a good coverage of all program features.

###ASSUMPTIONS
1. Only the following maze Types are being accounted in the Project
  Perfect, Imperfect
2. Maze may or may not be wrapping
3. Only the following direction types are supported for player to move:
	North, West, East, South
4. User can specify number of internal walls remaining
5. User can specify number of boundary walls to break if wrapping maze
6. Player can move through the maze in valid directions
7. Player collects gold on entering room with gold
8. Player loses 10% gold on entering room with thief
9. Player wins on entering room defined as goal

###DESIGN AND JUSTIFICATION
A maze interface will make the code more reusable and efficient. All the features that required merging of Perfecta and Imperfect maze are merged in maze abstract class. Maze Builder has been used that will take all inputs of the expected maze that should be created and will efficiently create the required maze.

We have Game as well as Person class other than maze. Person will be playing the maze. Game class encapsulates the maze and the steps of the player to solve the maze and give additional methods to move and collect gold and reach the destination in the maze. 

Finally, many use cases have been handled in the Driver class which allows you to execute either hardcoded sample cases for perfect, imperfect, wrapping and non-wrapping mazes or give a custom input the run the program.






