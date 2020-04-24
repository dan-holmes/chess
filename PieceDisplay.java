import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PieceDisplay extends JPanel
{

    private Piece piece;
    private boolean selected;
    
    public PieceDisplay(Piece newPiece)
    {
        piece = newPiece;
        selected = false;
    }
    
    public void setSelected(boolean newSelected)
    {
        selected = newSelected;
    }
    
    public boolean getSelected()
    {
        return selected;
    }
    
  
    public void paintComponent(Graphics g) 
    {
      char pieceType = piece.getType();
      char pieceColour = piece.getColour();
      Color colour = Color.BLACK;
      Color lightBrown = new Color(222,184,135);
      Color darkBrown = new Color(139,69,19);
      Color boardColour;
      
      if (pieceType == '0')
      {
          pieceType = ' ';
      }
      
      if (pieceColour == 'W')
      {
          colour = Color.WHITE;
      }
      
      if (selected == true)
      {
          colour = Color.GRAY;
        }
      
      if ((piece.getCoords().x + piece.getCoords().y) % 2 == 0)
      {
          boardColour = lightBrown;
      } else {
          boardColour = darkBrown;
        }
      
      super.paintComponent(g);
      this.setBackground(boardColour);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 70));
      g.setColor(colour);
      g.drawString( Character.toString(pieceType), 10, 70 );
   }
}
