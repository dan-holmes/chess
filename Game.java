import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game
{

    private Board board;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private boolean secondClick;
    private Piece selectedPiece;
    private JFrame window;
    
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    public Game()
    {

        window = new JFrame("Chess");
        window.setSize(800,800);
        window.setLocation(100,100);
        
        player1 = new Player(this,'W');
        player2 = new Player(this,'B');        
        activePlayer = player1;
        
        board = new Board(this);
        
        player1.setKing();
        player2.setKing();
        
        board.paintBoard(window);
        window.setVisible(true);
        
        
        secondClick = false;
        selectedPiece = null;
        
        board.display();
        
    }
    
    public Player getActivePlayer()
    {
        return activePlayer;
    }
    
    public Player getPlayer1()
    {
        return player1;
    }
    
    public Player getPlayer2()
    {
        return player2;
    }
    
    public Board getBoard()
    {
        return board;
    }
    
    public void handleSelection(Piece piece)
    {
        if(secondClick)
        {
            selectedPiece.getPieceDisplay().setSelected(false);
            selectedPiece.getPieceDisplay().revalidate();
            selectedPiece.getPieceDisplay().repaint();
        }
        if (piece.getColour() == activePlayer.getColour())
        {
            secondClick = false;
        }
        if (secondClick == false)
        {
            selectedPiece = piece;
            piece.updateValidMoves();
            
            piece.getPieceDisplay().setSelected(true);
            piece.getPieceDisplay().revalidate();
            piece.getPieceDisplay().repaint();
            
            secondClick = true;
        } else {
            makeMove(activePlayer, selectedPiece, piece);
            selectedPiece = null;
            secondClick = false;
        }
    }   

    public void makeMove(Player player,Piece selectedPiece, Piece destinationPiece)
    {
        Coords selectedPieceCoords = selectedPiece.getCoords();
        Piece destinationPieceBackup = new Piece(destinationPiece);
        Player oppPlayer;
        
        if (selectedPiece.getColour() != player.getColour())
        {
            return;
        }
        
        if (!selectedPiece.move(destinationPiece))
        {
            return;
        }
        
        if (player.inCheck())
        {
            board.display();
            board.setPiece(selectedPieceCoords,selectedPiece);
            board.setPiece(destinationPieceBackup.getCoords(),destinationPieceBackup);
            board.paintBoard(window);
            return;
        }
        
        if (selectedPiece.getType() == 'K')
        {
            player.moveKing();
        }
        
        if (selectedPiece.getType() == 'P')
        {
            if (selectedPiece.getCoords().x == 0 || selectedPiece.getCoords().x == 7)
            {
                selectedPiece.setType('Q');
            }
        }

        if (selectedPiece.getType() == 'R')
        {
            if (selectedPiece.getCoords().y == 0)
            {
                player.moveQSCastle();
            }
            if (selectedPiece.getCoords().y == 7)
            {
                player.moveKSCastle();
            }
        }
        
        if (player == player1)
        {
            oppPlayer = player2;
        } else {
            oppPlayer = player1;
        }
        
        
        
        if (oppPlayer.inCheckmate())
        {
            board.paintBoard(window);
            String strColour;
            if (player.getColour() == 'W') {strColour = "White"; } else { strColour = "Black"; }
            JOptionPane.showMessageDialog(window,strColour+" wins by checkmate!");
            /*board.resetBoard();
            player1.setKing();
            player2.setKing();
            activePlayer = player2;*/
        }
        
        
        activePlayer = oppPlayer;
        
        board.paintBoard(window);
    } 
    
    public void debug(String message)
    {
        board.paintBoard(window);
        JOptionPane.showMessageDialog(window,message);
    }
}
