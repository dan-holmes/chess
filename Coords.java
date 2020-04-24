public class Coords
{
    public int x;
    public int y;
    
    public Coords(int newX, int newY)
    {
        x = newX;
        y = newY;
    }
    
    public Coords(Coords clone)
    {
        this.x = clone.x;
        this.y = clone.y;
    }
    
    public void setX(int newX)
    {
        x = newX;
    }
    
    public void setY(int newY)
    {
        y = newY;
    }
    
    public boolean equals(Coords coord)
    {
        if (coord.x == x && coord.y == y)
        {
            return true;
        } else {
            return false;
        }
    }
            
    
    public boolean outOfRange()
    {
        if (x > 7 || y > 7 || x < 0 || y < 0)
        {
            return true;
        } else {
            return false;
        }
    }

}
