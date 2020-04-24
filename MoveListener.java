import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MoveListener implements MouseListener
{
    private Game game;
    private Piece piece;
    
    public MoveListener(Game currentGame, Piece currentPiece)
    {
        game = currentGame;
        piece = currentPiece;
    }
  
    public void mouseClicked(MouseEvent e) {
         game.handleSelection(piece);
      }
    public void mousePressed(MouseEvent e) {
    }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
}