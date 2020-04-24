import java.util.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Player
{
    private Game game;
    private char colour;
    private Piece king;
    private boolean hasMovedKing;
    private boolean hasMovedKSCastle;
    private boolean hasMovedQSCastle;

    /**
     * Constructor for objects of class Player
     */
    public Player(Game newGame, char newColour)
    {
       game = newGame;
       colour = newColour;
       hasMovedKing = false;
    }
    
    public char getColour()
    {
        return colour;
    }
    
    public void moveKing()
    {
        hasMovedKing = true;
    }
    
    public void moveKSCastle()
    {
        hasMovedKSCastle = true;
    }
    
    public void moveQSCastle()
    {
        hasMovedQSCastle = true;
    }
    
    public boolean hasMovedKing()
    {
        return hasMovedKing;
    }
    
    public boolean hasMovedKSCastle()
    {
        return hasMovedKSCastle;
    }
    
    public boolean hasMovedQSCastle()
    {
        return hasMovedQSCastle;
    }
    
    public void setKing()
    {
       if (colour == 'W')
       {
           king = game.getBoard().getPiece(new Coords(7,4));
        } else {
            king = game.getBoard().getPiece(new Coords(0,4));
        }
    }
    
    public boolean inCheck()
    {
        System.out.println("King location:" + String.valueOf(king.getCoords().x) + String.valueOf(king.getCoords().y));
        
        return king.isThreatened();
    }
    
    public boolean inCheckmate()
    {
        Board board = game.getBoard();
        Piece[][] pieces = board.getPieces();
        if(inCheck())
        {
            for (Piece[] list : pieces)
            {
                for (Piece piece : list)
                {
                    if (piece.getColour() == colour)
                    {
                        piece.updateValidMoves();
                        ArrayList<Coords> validMoves = piece.getValidMoves();
                        for (Coords move : validMoves)
                        {
                            Piece destinationPiece = board.getPiece(move);
                            boolean inCheck;
 
                            Coords selectedPieceCoords = piece.getCoords();
                            Piece destinationPieceBackup = new Piece(destinationPiece);
                            
                            piece.move(destinationPiece,true);
                            
                            if(piece.getType() == '\u2654')
                            {
                                inCheck = piece.isThreatened();
                            } else {
                                inCheck = inCheck();
                            }
                            
                            //game.debug(String.valueOf(inCheck));
                            
                            board.setPiece(selectedPieceCoords,piece);
                            board.setPiece(destinationPieceBackup.getCoords(),destinationPieceBackup);
                            
                            if(!inCheck)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
