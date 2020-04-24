import java.util.*;

public class Piece
{
    // instance variables - replace the example below with your own
    private Coords location;
    private char colour;
    private Player player;
    private char oppColour;
    private char type;
    private Board board;
    ArrayList<Coords> pieceValidMoves = new ArrayList<Coords>();
    private PieceDisplay pieceDisplay;

    public Piece(char newColour,char newType, Board newBoard)
    {
        colour = newColour;
        if (colour == 'W')
        {
            oppColour = 'B';
        } else if (colour == 'B') {
            oppColour = 'W';
        } else {
            oppColour = '0';
        }
        type = newType;
        board = newBoard;
        Player player1 = board.getGame().getPlayer1();
        Player player2 = board.getGame().getPlayer2();
        if (player1.getColour() == colour)
        {
            player = player1;
        } else {
            player = player2;
        }
    }
    
    public Piece(Piece clone)
    {
        location = clone.getCoords();
        colour = clone.getColour();
        player = clone.getPlayer();
        if (colour == 'W')
        {
            oppColour = 'B';
        } else if (colour == 'B') {
            oppColour = 'W';
        } else {
            oppColour = '0';
        }
        type = clone.getType();
        board = clone.getBoard();
        pieceDisplay = clone.getPieceDisplay();
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public Board getBoard()
    {
        return board;
    }
    
    public char getColour()
    {
        return colour;
    }
    
    public char getType()
    {
        return type;
    }
    
    public PieceDisplay getPieceDisplay()
    {
        return pieceDisplay;
    }
    
    public void setType(char newType)
    {
        type = newType;
    }
    
    public void setBoard(Board newBoard)
    {
        board = newBoard;
    }
    
    public void setLocation(Coords coords)
    {
        location = coords;
    }
    
    public void setPieceDisplay(PieceDisplay newPieceDisplay)
    {
        pieceDisplay = newPieceDisplay;
    }
    
    public Coords getCoords()
    {
        return location;
    }
    
    public ArrayList<Coords> getValidMoves()
    {
        return pieceValidMoves;
    }
    
    public boolean move(Piece destPiece)
    {
        return move(destPiece, false);
    }

    public boolean move(Piece destPiece, boolean moveCheckDefault)
    {
        Coords destCoords = destPiece.getCoords();
        boolean moveCheck = moveCheckDefault;
        
        if(!moveCheckDefault)
        {
            for (Coords move : pieceValidMoves)
            {
                if (move.x == destCoords.x && move.y == destCoords.y)
                {
                    moveCheck = true;
                }
            }
        }
        
        if (!moveCheck)
        {
            System.out.println("Invalid move.");
            return false;
        }
        
        if (type == 'K')
        {          
            if(destCoords.y == 6)
            {
                Piece rook = board.getPiece(new Coords(location.x,7));
                board.setPiece(new Coords(location.x,7),board.getPiece(new Coords(location.x,5)));
                board.setPiece(new Coords(location.x,5),rook);
                 
            }
            if(destCoords.y == 2)
            {
                Piece rook = board.getPiece(new Coords(location.x,0));
                board.setPiece(new Coords(location.x,0),board.getPiece(new Coords(location.x,3)));
                board.setPiece(new Coords(location.x,3),rook);
            }
        }
        
        if (destPiece.getType() == '0')
        {
            board.setPiece(location,destPiece);
            board.setPiece(destCoords,this);
            return true;
        }
        else if (destPiece.getColour() != colour)
        {
            Piece newBlankPiece = new Piece('0','0',board);
            board.setPiece(location,newBlankPiece);
            board.setPiece(destCoords,this);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void updateValidMoves()
    {
        updateValidMoves(true);
    }
    
    public void updateValidMoves(boolean canCastle)
    {
        ArrayList<Coords> validMoves = new ArrayList<Coords>();
        
        if (type == 'P')
        {
            int direction;
            
            if (colour == 'W')
            {
                direction = -1;
            } else {
                direction = 1;
            }
            
            if (board.getPiece(new Coords(location.x+direction,location.y)).getColour() != oppColour)
            {
                validMoves.addAll(getValidMovesInLine(direction,0,location,1,0));
            }
            if (location.x == (7+direction)%7 && board.getPiece(new Coords(location.x+direction*2,location.y)).getColour() != oppColour)
            {
                validMoves.addAll(getValidMovesInLine(2*direction,0,location,1,0));
            }
            if (board.getPiece(new Coords(location.x+direction,location.y+1)).getColour() == oppColour)
            {
                validMoves.addAll(getValidMovesInLine(direction,1,location,1,0));
            }
            if (board.getPiece(new Coords(location.x+direction,location.y-1)).getColour() == oppColour)
            {
                validMoves.addAll(getValidMovesInLine(direction,-1,location,1,0));
            }
        }
        
        if (type == 'K')
        {
            validMoves.addAll(getValidMovesInLine(1,0,location,1,-1));
            validMoves.addAll(getValidMovesInLine(0,1,location,1,-1));
            validMoves.addAll(getValidMovesInLine(1,1,location,1,-1));
            validMoves.addAll(getValidMovesInLine(1,-1,location,1,-1));
            
            if (canCastle == true)
            {
                if (canCastle(true))
                {
                    validMoves.add(new Coords(location.x,6));
                }
                
                if (canCastle(false))
                {
                    validMoves.add(new Coords(location.x,2));
                }
            }
        }
        
        if (type == 'R')
        {
            validMoves.addAll(getValidMovesInLine(1,0,location));
            validMoves.addAll(getValidMovesInLine(0,1,location));
        }
        
        if (type == 'B')
        {
            validMoves.addAll(getValidMovesInLine(1,1,location));
            validMoves.addAll(getValidMovesInLine(1,-1,location));
        }
        
        if (type == 'Q')
        {
            validMoves.addAll(getValidMovesInLine(1,0,location));
            validMoves.addAll(getValidMovesInLine(0,1,location));
            validMoves.addAll(getValidMovesInLine(1,1,location));
            validMoves.addAll(getValidMovesInLine(1,-1,location));
        }
        
        if (type == 'N')
        {
            validMoves.addAll(getValidMovesInLine(1,2,location,1,-1));
            validMoves.addAll(getValidMovesInLine(2,1,location,1,-1));
            validMoves.addAll(getValidMovesInLine(1,-2,location,1,-1));
            validMoves.addAll(getValidMovesInLine(2,-1,location,1,-1));
        }
        
        pieceValidMoves = validMoves;
            
    }
    
    public ArrayList<Coords> getValidMovesInLine(int incX, int incY, Coords currentCoords, int pLimit, int nLimit)
    {
            Coords candidate = new Coords(currentCoords);
            ArrayList<Coords> validMoves = new ArrayList<Coords>();
            int incs = 0;
        
            while((board.getPiece(candidate).getType() == '0' || candidate.equals(location))  && incs < pLimit)
            {
                candidate.x = candidate.x + incX;
                candidate.y = candidate.y + incY;
                if(candidate.x >= 0 && candidate.x <= 7 && candidate.y >= 0 && candidate.y <= 7 && board.getPiece(candidate).getColour() != colour)
                {
                    validMoves.add(new Coords(candidate));
                }
                incs++;
            }
            
            candidate = new Coords(currentCoords);
            incs = 0;
            
            while((board.getPiece(candidate).getType() == '0' || candidate.equals(location)) && incs > nLimit)
            {
                candidate.x = candidate.x - incX;
                candidate.y = candidate.y - incY;
                if(candidate.x >= 0 && candidate.x <= 7 && candidate.y >= 0 && candidate.y <= 7  && board.getPiece(candidate).getColour() != colour)
                {
                    validMoves.add(new Coords(candidate));
                }
                incs--;
            }
            
            return validMoves;
    }
    
    public ArrayList<Coords> getValidMovesInLine(int incX, int incY, Coords currentCoords)
    {
        return getValidMovesInLine(incX, incY, currentCoords,7,-7);
    }
    
    public boolean isThreatened()
    {
        int direction;
        char[] pieceTypes = {'R','N','B','Q','K'};
            
        for (char pieceType : pieceTypes)
        {
            Piece dummyPiece = new Piece(colour,pieceType,board);
            dummyPiece.setLocation(location);
            dummyPiece.updateValidMoves(false);
            for (Coords coords : dummyPiece.getValidMoves())
            {
                if (board.getPiece(coords).getColour() == oppColour && board.getPiece(coords).getType() == pieceType)
                {
                    return true;
                }
            }
        }
        
        if (colour == 'W')
            {
                direction = -1;
            } else {
                direction = 1;
            }
        
        Piece possiblePawn1 = board.getPiece(new Coords(location.x+direction,location.y+1));
        Piece possiblePawn2 = board.getPiece(new Coords(location.x+direction,location.y-1));
        
        if ((possiblePawn1.getColour() == oppColour && possiblePawn1.getType() == 'P') || (possiblePawn2.getColour() == oppColour && possiblePawn2.getType() == 'P'))
        {
            return true;
        }
        
        return false;
    }
    
    public boolean canCastle(boolean kingSide)
    {
        if (type != 'K')
        {
            return false;
        }
        
        if (!((location.x == 0 || location.x == 7) && location.y == 4))
        {
            return false;
        }
        
        if (player.hasMovedKing())
        {
            return false;
        }
        
        if (player.hasMovedKSCastle())
        {
            return false;
        }
        
        if (player.hasMovedQSCastle())
        {
            return false;
        }
        
        if (player.inCheck())
        {
            return false;
        }
        
        if (kingSide)
        {
            for (int i = 5; i <= 7; i++)
            {
                Piece trialPiece = board.getPiece(new Coords(location.x,i));
                if (trialPiece.getType() != '0' && i != 7)
                {
                    return false;
                }
                if(board.getPiece(new Coords(location.x,i)).isThreatened())
                {
                    return false;
                }
            }
        } else {
            for (int i = 3; i >= 0; i--)
            {
                Piece trialPiece = board.getPiece(new Coords(location.x,i));
                if (trialPiece.getType() != '0' && i != 0)
                {
                    return false;
                }
                if(board.getPiece(new Coords(location.x,i)).isThreatened())
                {
                    return false;
                }
            }
        }
        
        return true;
    }
}
