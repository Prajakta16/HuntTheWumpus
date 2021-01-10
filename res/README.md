# Homework 6 -- HTW Controller

###DESCRIPTION
In this version of Hunt the Wumpus, the part of the program that interfaces with the user, the view, has been replaced with an interactive graphical user interface (GUI). In a more modern implementation, the GUI would:
<br>
Expose all game settings through menus or other controls. These settings include maze size, number of walls, number of players (1 or 2), and difficulty (specified as number of superbats, pits, etc).
<br>
Provide an option for restarting the game as a new game or as the same game (with the same random seed resulting in the same maze).
<br>
Allow the maze to be bigger than the area allocated to it on the screen providing the ability to scroll the view.
<br>
Allow the player to move through the maze using a mouse click in addition to the keyboard arrow keys. A click on an invalid space, or pressing an invalid key would not advance the game.
<br>
Provide an option for two players where players take turns making moves or shooting arrows as they race to be the first to kill the Wumpus. In this mode, the rules of the game remain the same as before. The game ends when one player has killed the Wumpus, or both players have died. Arrows shot by a player miraculously miss the other player.
<br>
Provide a clear indication of the results of each action a player takes as well as whose turn it is.

####REQUIREMENT
Design and add an optional graphical user interface view for your implementation of Hunt the Wumpus (your implementation should still be able to operate in text-based mode). While the choices about layout and behavior are up to you, your graphical user interface should have all of the features of the modern implementation listed above and obey the following constraints:

You must use Java Swing to build your graphical user interface. You may find the GridLayout (Links to an external site.) useful.

The view should begin with a mostly blank screen and display only the pieces of the maze that have been revealed by the user's exploration of the caves and tunnels. In two-player mode, the areas explored by both players should be visible.

If the game is in two-player mode, the view must identify which player is currently taking their turn.

Each user interaction or user input must be reasonably user-friendly (e.g. making the user type the path to a file is poor UI design). We do not expect snazzy, sophisticated user-friendly programs. Our standard is: can a user unfamiliar with your code and technical documentation operate the program correctly without reading your code and technical documentation?

To help you with this assignment, we are are providing a set of images (Links to an external site.) to use, as well as additional images (Links to an external site.) to choose from.

#####View and Controller
Carefully design the interaction between a view and a controller, and formalize the interactions with view and controller interfaces. Different controllers for different views are also possible if the views are very different from each other. However be mindful of the MVC principles and separation between the model, view and controller. When designing, always ask: "can I change one part with no/minimal changes to the others?"

#####Extra Credit
Provide an alternative theme for the Hunt the Wumpus game as an alternative in your implementation. An alternative theme would have the same basic game play mechanisms but would have a different look-and-feel, a different goal, and different "obstacles." For example, in the mazes assignment we used thieves and gold as "obstacles." Replacing the images used in the game would give the game a different look and feel. Be creative here.

###SUBMISSION SUMMARY
In this homework I have designed and implemented a fully functional Hunt the wumpus game in text-based and GUI mode. The player needs to find and shoot the wumpus.
He may encounter other obstacles who may delay his play or kill the Player. 

###HOW TO USE
All the code is in src/.
All the tests are in test/.
Original, revised design document, the JAR file, example runs of program are in res/.

The program can be run using the JAR file within the res/ folder in the zipped file.
1. Unzip submission folder
2. Go to the to res/ folder using command prompt
3. Execute "java -jar HW6Maze.jar --config=themeConfig.txt" to run the themes
4. Execute "java -jar HW6Maze.jar --text" to run the GUI version
5. Execute "java -jar HW6Maze.jar --gui" to run the GUI version
6. Program should successfully execute

###PARTS COMPLETE
All the parts and requirements of the problem statement are completed and the tests have a good coverage of all program features.

###ASSUMPTIONS
New:
1. Game has 4 themes - WUMPUS, MARINE, SPACE and FOREST. Player can choose any one theme which will
change the look and feel of the game. 
2. Game can be played either in 1 player mode or 2 player mode
3. If wrapping maze is selected for imperfect maze, random number of walls will be removed to make the maze wrapping
4. Starting positions of all the players will be different. Pit/bat/wumpus will not be present in the starting location

Existing:
1. Only the following maze Types are being accounted in the Project
  Perfect, Imperfect
2. Maze may or may not be wrapping
3. Only the following direction types are supported for player to move:
	North, West, East, South
4. User can specify number of internal walls remaining
5. User can specify number of wrapping walls to be removed
6. Player can move through the maze in valid directions
7. Bats come back to their original Cave after transporting the Player
8. Player falls into a bottomless pit and looses the game
9. Bats may or may not transport the Player
10. Wumpus cannot exist at a location having pit or bats
11. Player can shoot as many arrows as initially assigned to him
12. Arrow will pass through the tunnel without decrementing the distance

###DESIGN AND JUSTIFICATION
1. Created different packages for models, views and controllers for ease of code reading
2. Added a IView interface for all 3 views - ThemeSelectorView, GameInputView and MazeView
3. Added a GUIGameController for controlling GUI based game by being the intermediary between Game models and views
4. Required features for different themes to dynamically generate the input form, so created a IThemeFeature interface.
 Implemented ITheme interface and classes ThemeWumpus, ThemeMarine, ThemeSpace and ThemeForest  to return features specific to individual games.
5. For view to convey all the Maze generation inputs to the model, rather than conveying it for every feature, wrapped all the data
in a object of GameInput. Created IGameInput interface and implemented the same in GameInput class.
6. Created different type of interfaces for different views because there were extremely few common methods. 
It was a fair tradeoff rather than having numerous methods throw UnSupported exception.
7. Creating an interface IMazeCreator and corresponding class MazeCreator which takes in all the 
input provided by the user and verifies for any errors. After error checking, it would create an
instance of a maze and return the maze to the controller which will be an input to the Game model.
8. In HW5, my player's status like alive/dead and won/lost was stores in my Game class. Moved that to the Player class
Also added getts and setters for the same.
9. Added a new constructor GameWumpus(InterfaceMaze maze, List<Player> players, int randomSeed) to tackle the feature of multi-players.
Correspondingly made a similar constructor in super() ie AbstractGame which assigns the multi-players to the maze. 
The star5ing position is selected randomly.
10. Implemented IViewModel with read-only methods about the model which the view can pull as desired.
11. Implemented Button and Action listeners to take care of all activity.
12. Implemented corresponding methods in controller across each listener.



