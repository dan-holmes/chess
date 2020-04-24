import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board
{
    private Piece[][] pieces = new Piece[8][8];
    private Game game;
    
    private Piece[][] savedState = new Piece[8][8];

    /**
     * Constructor for objects of class Board
     */
    public Board(Game currentGame)
    {
        game = currentGame;
        resetBoard();
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public void resetBoard()
    {
        char[] startRow = {'R','N','B','Q','K','B','N','R'};
        
        for (int i = 0; i <=7; i++)
        {
            pieces[0][i] = new Piece('B',startRow[i],this);
            pieces[1][i] = new Piece('B','P',this);
            for (int j = 2; j <= 5; j++)
            {
                pieces[j][i] = new Piece('0','0',this);
            }
            pieces[6][i] = new Piece('W','P',this);
            pieces[7][i] = new Piece('W',startRow[i],this);
        }
        
        
        for (int i = 0;  i <= 7; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                Piece piece = pieces[i][j];
                piece.setLocation(new Coords(i,j));
            }
        }
    }
    
    public Piece getPiece(Coords coords)
    {
        if (coords.outOfRange())
        {
            return new Piece('0','X',this);
        }
        return pieces[coords.x][coords.y];
    }
    
    public void setPiece(Coords coords, Piece piece)
    {
        pieces[coords.x][coords.y] = piece;
        piece.setLocation(coords);
    }
    
    public Piece[][] getPieces()
    {
        return pieces;
    }
    
    public void paintBoard(JFrame window)
    {
        BoardDisplay boardDisplay = new BoardDisplay();
        
        boardDisplay.setLayout(new GridLayout(8,8));
        
        char activeColour = game.getActivePlayer().getColour();
        
        for (int i = 0; i <= 7; i++)
        {
            for (int j = 0; j <= 7; j++)
            {
                Piece piece;
                
                if (activeColour == 'B')
                {
                    piece = pieces[7-i][7-j];
                } else {
                    piece = pieces[i][j];
                }
                
                PieceDisplay pieceDisplay = new PieceDisplay(piece);
                MoveListener moveListener = new MoveListener(game, piece);
                pieceDisplay.addMouseListener(moveListener);
                boardDisplay.add(pieceDisplay);
                piece.setPieceDisplay(pieceDisplay);
            }
        }
        
        window.setContentPane(boardDisplay);
        window.revalidate();
        window.repaint();
        
    }
    
    public void display()
    {
        for (Piece[] line : pieces)
        {
            String output = "";
            for (Piece piece : line)
            {
                output = output+Character.toString(piece.getType());
            }
            System.out.println(output);
        }
    }
}
