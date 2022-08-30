/**
 * created by Acer on August 17, 2022
 **/


import java.util.*;

public class Game {
    //Global Variables
    public static int rowNo = 10;
    public static int colNo = 10;
    public static int playerShips = 5;
    public static int computerShips = 5;
    public static int z = 1;
    public static int[][] computerLocation = new int[6][3];
    public static String[][] computerTurnLoc = new String [rowNo][colNo];
    public static String[][] oceanGrid = new String[rowNo][colNo];

    public void battleShip() {
        System.out.println("***Welcome to Battle Ships Game***");
        System.out.println("Right now the sea is empty.");
        //Algorithm No. 1 Create an Ocean Map
        oceanMap();
        //Algorithm No. 2 Deploy Player's Ships
        deployPlayerShips();
        //Algorithm No.3 Deploy Computer's Ships
        deployComputerShips();
        System.out.println("Let's Start the Battle Ships Game!");
        //Algorithm No.4 Battle
        battle();
    }

    public static void oceanMap() {
        //Ocean Map Header
        System.out.print("  ");
        for (int i = 0; i < colNo; i++) {
            System.out.print(i);
        }
        System.out.println();
        //Ocean Map Body
        for (int i = 0; i < oceanGrid.length; i++) {
            for (int j = 0; j < oceanGrid[i].length; j++) {
                oceanGrid[i][j] = " ";
                if (j == 0) {
                    System.out.print(i + "|" + oceanGrid[i][j]);
                } else if (j == oceanGrid[i].length - 1) {
                    System.out.println(oceanGrid[i][j] + "|" + i);
                } else {
                    System.out.print(oceanGrid[i][j]);
                }
            }
        }
        //Ocean Map Footer
        System.out.print("  ");
        for (int i = 0; i < colNo; i++) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void deployPlayerShips() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("\nDeploy your Ship");
            //Deploying Player Ship
            for (; z <= playerShips; z++) {
                System.out.print("Enter X coordinate for your " + z + ". ship: ");
                int x = input.nextInt();
                System.out.print("Enter Y coordinate for your " + z + ". ship: ");
                int y = input.nextInt();
                if ((x >= 0 && x < rowNo) && (y >= 0 && y < colNo) && (oceanGrid[x][y].equals(" "))) {
                    oceanGrid[x][y] = "@";
                } else if ((x >= 0 && x < rowNo) && (y >= 0 && y < colNo) && (oceanGrid[x][y].equals("@"))) {
                    System.out.println("You can't put two ships on the same area");
                    z--;
                } else {
                    System.out.println("You can't place your ships beyond " + rowNo + " by " + colNo + " Ocean Map");
                    z--;
                }
            }
            System.out.println();
            printOceanMap();
            System.out.println("-----------------------------");
        } catch (Exception e) {
            System.out.println("Must be integer!");
            deployPlayerShips();
        }
    }

    public static void deployComputerShips() {
        System.out.println("\nComputer is deploying ships");
        //Deploying Computer's Ship
        for (int i = 1; i <= computerShips; i++) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            if ((x >= 0 && x < rowNo) && (y >= 0 && y < colNo) && (oceanGrid[x][y].equals(" ")) &&
                    (((x != computerLocation[1][1]) && (y != computerLocation[1][2])) &&
                            ((x != computerLocation[3][1]) && (y != computerLocation[3][2])) &&
                            ((x != computerLocation[4][1]) && (y != computerLocation[4][2])) &&
                            ((x != computerLocation[5][1]) && (y != computerLocation[5][2])))) {
                computerLocation[i][1] = x;
                computerLocation[i][2] = y;
                System.out.println(i + ". ship DEPLOYED");
            } else if ((oceanGrid[x][y].equals("@")) ||
                    (((x == computerLocation[1][1]) && (y == computerLocation[1][2])) ||
                            ((x == computerLocation[2][1]) && (y == computerLocation[2][2])) ||
                            ((x == computerLocation[3][1]) && (y == computerLocation[3][2])) ||
                            ((x == computerLocation[4][1]) && (y == computerLocation[4][2])) ||
                            ((x == computerLocation[5][1]) && (y == computerLocation[5][2])))) {
                i--;
            } else {
                i--;
            }
        }
        System.out.println("-----------------------------");
    }

    public static void battlePlayersTurn() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Your Turn");
            //Player's Attack
            System.out.print("Enter x-coordinate:");
            int x = input.nextInt();
            System.out.print("Enter y-coordinate:");
            int y = input.nextInt();
            if (x < 0 || x > 9 || y < 0 || y > 9) {
                System.out.println("You can't attacked outside");
                battlePlayersTurn();
            } else if (x < rowNo && y < colNo && oceanGrid[x][y].equals("@")) {
                oceanGrid[x][y] = "x";
                System.out.println("Oh no,you sunk your own ship :(");
                playerShips--;
            } else if ((x < rowNo) && (y < colNo) && (((x == computerLocation[1][1]) &&
                    (y == computerLocation[1][2])) ||
                    ((x == computerLocation[2][1]) && (y == computerLocation[2][2])) ||
                    ((x == computerLocation[3][1]) && (y == computerLocation[3][2])) ||
                    ((x == computerLocation[4][1]) && (y == computerLocation[4][2])) ||
                    ((x == computerLocation[5][1]) && (y == computerLocation[5][2])))) {
                oceanGrid[x][y] = "!";
                System.out.println("Boom! You sunk the ship!");
                computerShips--;
            } else if (x< rowNo && y<colNo && oceanGrid[x][y].equals(" ")){
                oceanGrid[x][y] = "-";
                System.out.println("Sorry, you missed!");
            }
        } catch (Exception e) {
            System.out.println("Must be integer!");
            battlePlayersTurn();
        }
    }

    public static void computerOcean() {
        //Ocean Map Body
        for (int i = 0; i < computerTurnLoc.length; i++) {
            for (int j = 0; j < computerTurnLoc[i].length; j++) {
                computerTurnLoc[i][j] = " ";
            }
        }
    }
    public static void battleComputersTurn() {
        System.out.println("Computer's Turn");
        //Computer's Attack
        int x = (int) (Math.random() * 10);
        int y = (int) (Math.random() * 10);
        if (x < rowNo && y < colNo && oceanGrid[x][y].equals(" ") && computerTurnLoc[x][y].equals(" ")) {
            System.out.println("Computer Missed!");
            computerTurnLoc[x][y] = "$";
        } else if (x < rowNo && y < colNo && oceanGrid[x][y].equals("@") && computerTurnLoc[x][y].equals(" ")) {
            System.out.println("The Computer sunk one of your ships!");
            oceanGrid[x][y] = "x";
            computerTurnLoc[x][y] = "$";
            playerShips--;
        } else if (x < rowNo && y < colNo && oceanGrid[x][y].equals(" ") && computerTurnLoc[x][y].equals(" ") &&
                (((x == computerLocation[1][1]) && (y == computerLocation[1][2])) ||
                        ((x == computerLocation[2][1]) && (y == computerLocation[2][2])) ||
                        ((x == computerLocation[3][1]) && (y == computerLocation[3][2])) ||
                        ((x == computerLocation[4][1]) && (y == computerLocation[4][2])) ||
                        ((x == computerLocation[5][1]) && (y == computerLocation[5][2])))) {
            System.out.println("The Computer sunk one of its own ships!");
            oceanGrid[x][y] = "!";
            computerTurnLoc[x][y] = "$";
            computerShips--;
        } else if (x < rowNo && y < colNo && computerTurnLoc[x][y].equals("$")) {
            battleComputersTurn();
        }
    }
    public static void battle(){
        computerOcean();
       while ((computerShips>0) && (playerShips>0))  {
           battlePlayersTurn();
           battleComputersTurn();
           printOceanMap();
           System.out.println("Your Ships: " +playerShips+ " | Computer Ships: " +computerShips);
           System.out.println("---------------------------------");
       }
           gameOver();
    }
    public static void gameOver(){
        System.out.println("Game Over");
        if(playerShips>computerShips){
            System.out.println("Hooray! You win the battle :)");
        }else{
            System.out.println("Sorry! You lost the game :(");
        }
    }
    public static void printOceanMap() {
        //Ocean Map Header
        System.out.print("  ");
        for (int i = 0; i < colNo; i++) {
            System.out.print(i);
        }
        System.out.println();
        //Ocean Map Body
        for (int x = 0; x < oceanGrid.length; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < oceanGrid[x].length; y++) {
                System.out.print(oceanGrid[x][y]);
            }
            System.out.println("|" + x);
        }
        //Ocean Map Footer
        System.out.print("  ");
        for (int i = 0; i < colNo; i++) {
            System.out.print(i);
        }
        System.out.println();
    }
}