/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{

      // describe the game with brief welcome message
      // determine the size (length and width) a player must move to stay within the grid markings
      // Allow game commands:
      //    right, left, up, down: if you try to go off grid or bump into wall, score decreases
      //    jump over 1 space: you cannot jump over walls
      //    if you land on a trap, spring a trap to increase score: you must first check if there is a trap, if none exists, penalty
      //    pick up prize: score increases, if there is no prize, penalty
      //    help: display all possible commands
      //    end: reach the far right wall, score increase, game ends, if game ended without reaching far right wall, penalty
      //    replay: shows number of player steps and resets the board, you or another player can play the same board
      // Note that you must adjust the score with any method that returns a score
      // Optional: create a custom image for your player use the file player.png on disk
    
      /**** provided code:
      // set up the game
      boolean play = true;
      while (play)
      {
        // get user input and call game methods to play 
        play = false;
      }
      */

  public static void main(String[] args) 
  {      
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // size of move
    int m = 60; 
    // individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;

    Scanner in = new Scanner(System.in);
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "quit", "q"};
  
    // set up game
    boolean play = true;
    while (play)
    {
      System.out.print("Enter right, left, up, down, or quit\n>");
      String command = UserInput.getValidInput(validCommands);

      // Reset the change in position before interpreting the next command.
      px = 0;
      py = 0;

      if (command.equals("right") || command.equals("r"))
      {
        px = m;
      }
      else if (command.equals("left") || command.equals("l"))
      {
        px = -m;
      }
      else if (command.equals("up") || command.equals("u"))
      {
        py = -m;
      }
      else if (command.equals("down") || command.equals("d"))
      {
        py = m;
      }
      else if (command.equals("quit") || command.equals("q"))
      {
        play = false;
      }

      if (play)
      {
        // movePlayer returns any earned penalty, which updates the total score.
        score += game.movePlayer(px, py);
        System.out.println("score=" + score);
      }
    }

  

    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }
}
