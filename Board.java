
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
    private Piece[][] pieces = new Piece[8][8];

    /**
     * Constructor for objects of class Board
     */
    public Board()
    {
        resetBoard();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    
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
                piece.setLocation(i,j);
            }
        }
    }
    
    public Piece getPiece(int x,int y)
    {
        return pieces[x][y];
    }
    
    public void setPiece(int x,int y, Piece piece)
    {
        pieces[x][y] = piece;
        piece.setLocation(x,y);
    }
    
    public Piece[][] getPieces()
    {
        return pieces;
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
