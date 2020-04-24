
/**
 * Write a description of class Piece here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Piece
{
    // instance variables - replace the example below with your own
    private int xLocation;
    private int yLocation;
    private char colour;
    private char type;
    private Board board;

    public Piece(char newColour,char newType, Board newBoard)
    {
        colour = newColour;
        type = newType;
        board = newBoard;
    }
    
    public char getColour()
    {
        return colour;
    }
    
    public char getType()
    {
        return type;
    }
    
    public void setType(char newType)
    {
        type = newType;
    }
    
    public void setBoard(Board newBoard)
    {
        board = newBoard;
    }
    
    public void setLocation(int x, int y)
    {
        xLocation = x;
        yLocation = y;
    }

    public void move(int x, int y)
    {
        Piece oldPiece = board.getPiece(x,y);
        
        if (oldPiece.getType() == '0')
        {
            board.setPiece(xLocation,yLocation,oldPiece);
            board.setPiece(x,y,this);
        }
        else if (oldPiece.getColour() != colour)
        {
            Piece newBlankPiece = new Piece('0','0',board);
            board.setPiece(xLocation,yLocation,newBlankPiece);
            board.setPiece(x,y,this);
        }
        else
        {
            //Error
        }
    }
}
