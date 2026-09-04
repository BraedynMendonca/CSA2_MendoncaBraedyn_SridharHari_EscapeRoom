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

  private static final int INVALID_COMMAND_PENALTY = 1;

  /** Returns whether the player's entry matches one of the available commands. */
  public static boolean isValidCommand(String command, String[] validCommands)
  {
    for (String validCommand : validCommands)
    {
      if (command.equals(validCommand))
      {
        return true;
      }
    }
    return false;
  }

  /** Prints every command so the player can check the controls at any time. */
  public static void showCommands()
  {
    System.out.println("\nValid commands:");
    System.out.println("  W/A/S/D - move up, left, down, or right");
    System.out.println("  right/r, left/l, up/u, down - typed movement commands");
    System.out.println("  jump/jr, jumpleft/jl, jumpup/ju, jumpdown/jd - move two spaces");
    System.out.println("  pickup/p - pick up a prize on your space");
    System.out.println("  spring/t - spring a trap on your space");
    System.out.println("  check/c - check the spaces around you for traps");
    System.out.println("  replay - reset the board");
    System.out.println("  help/? - show the commands again");
    System.out.println("  quit/q - end the game\n");
  }

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

    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "w", "a", "s", "d",
    "jump", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "spring", "t", "check", "c", "quit", "q", "replay", "help", "?"};

    Scanner in = new Scanner(System.in);
    showCommands();
  
    // set up game
    boolean play = true;
    while (play)
    {
      System.out.print("What would you like to do?\n>");
      String command = in.nextLine().trim().toLowerCase();

      if (!isValidCommand(command, validCommands))
      {
        score -= INVALID_COMMAND_PENALTY;
        System.out.println("Invalid command. You lose " + INVALID_COMMAND_PENALTY + " point.");
        System.out.println("score=" + score);
        continue;
      }

      // Start each turn without movement. Direction commands change these values.
      px = 0;
      py = 0;
      boolean shouldMove = false;

      if (command.equals("right") || command.equals("r") || command.equals("d"))
      {
        px = m;
        shouldMove = true;
      }
      else if (command.equals("left") || command.equals("l") || command.equals("a"))
      {
        px = -m;
        shouldMove = true;
      }
      else if (command.equals("up") || command.equals("u") || command.equals("w"))
      {
        py = -m;
        shouldMove = true;
      }
      else if (command.equals("down") || command.equals("s"))
      {
        py = m;
        shouldMove = true;
      }
      else if (command.equals("jump") || command.equals("jr"))
      {
        px = 2 * m;
        shouldMove = true;
      }
      else if (command.equals("jumpleft") || command.equals("jl"))
      {
        px = -2 * m;
        shouldMove = true;
      }
      else if (command.equals("jumpup") || command.equals("ju"))
      {
        py = -2 * m;
        shouldMove = true;
      }
      else if (command.equals("jumpdown") || command.equals("jd"))
      {
        py = 2 * m;
        shouldMove = true;
      }
      else if (command.equals("pickup") || command.equals("p"))
      {
        // Picking up an empty space costs the same points that a prize is worth.
        score += game.pickupPrize();
      }
      else if (command.equals("spring") || command.equals("t"))
      {
        // A successful spring earns points, but guessing costs points.
        score += game.springTrap(0, 0);
      }
      else if (command.equals("check") || command.equals("c"))
      {
        // The four checks are combined because a trap in any direction matters.
        boolean trapNearby = game.isTrap(m, 0) || game.isTrap(-m, 0)
            || game.isTrap(0, m) || game.isTrap(0, -m);

        if (trapNearby)
        {
          System.out.println("Be careful. There is a trap one space away.");
        }
        else
        {
          System.out.println("No traps were found in the spaces next to you.");
        }
      }
      else if (command.equals("replay"))
      {
        System.out.println("steps=" + game.getSteps());
        score += game.replay();
        System.out.println("The board has been reset.");
      }
      else if (command.equals("help") || command.equals("?"))
      {
        showCommands();
      }
      else if (command.equals("quit") || command.equals("q"))
      {
        play = false;
      }

      if (shouldMove)
      {
        // A wall or board-edge penalty is returned and added to the running score.
        score += game.movePlayer(px, py);

        // Coins are collected as soon as the player lands on their space.
        if (game.hasPrize())
        {
          score += game.pickupPrize();
        }

        if (game.isTrap(0, 0))
        {
          System.out.println("You landed on a trap. Use spring to disarm it.");
        }

        // Reaching the right side ends the game without needing to type quit.
        if (game.hasReachedExit())
        {
          play = false;
        }
      }

      if (play)
      {
        System.out.println("score=" + score);
      }
    }

  

    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }
}
