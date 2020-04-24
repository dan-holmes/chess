import java.util.Scanner;

public class Game
{

    private Board board;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    
    public static void main()
    {
        Game game = new Game();
    }

    public Game()
    {
        board = new Board();
        player1 = new Player('W');
        player2 = new Player('B');
                      
        activePlayer = player1;
        
        board.display();
        
        while(1==1)
        {
            makeMove(player1);
        }
    }
    
    public int[] getMove()
    {
        Scanner user_input = new Scanner( System.in );
        String instruction = user_input.nextLine();
        int[] instructions = new int[4];
        for (int i = 0; i<=3; i++)
        {
            char character = instruction.charAt(i);
            instructions[i] = Character.getNumericValue(character);
        }
        return instructions;
    }

    public void makeMove(Player player)
    {
        int[] instructions = getMove();
        
        Piece piece = board.getPiece(instructions[0],instructions[1]);
        
        piece.move(instructions[2],instructions[3]);
        
        if (player == player1)
        {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
        
        board.display();
    }
}
