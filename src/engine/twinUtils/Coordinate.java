package engine.twinUtils;

public class Coordinate
{
    private int x;
    private int y;

    public Coordinate( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public Coordinate( Coordinate coordinate )
    {
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }

    public int getX()
    {
        return x;
    }

    public void setX( int x )
    {
        this.x = x;
    }

    public void addX( int additionalX )
    {
        x += additionalX;
    }

    public void subtractX( int subtractAmount )
    {
        x -= subtractAmount;
    }

    public int getY()
    {
        return y;
    }

    public void setY( int y )
    {
        this.y = y;
    }

    public void addY( int additionalY )
    {
        y += additionalY;
    }

    public void subtractY( int subtractAmount )
    {
        y -= subtractAmount;
    }

    public void add( Coordinate coordinate )
    {
        x += coordinate.getX();
        y += coordinate.getY();
    }

    public void subtract( Coordinate coordinate )
    {
        x -= coordinate.getX();
        y -= coordinate.getY();
    }

    public void moveByAngle( int angle, int amountToMove )
    {
        int newX = x + (int)( amountToMove * Math.cos( Math.toRadians( angle - 90 ) ) );
        int newY = y + (int)( amountToMove * Math.sin( Math.toRadians( angle - 90 ) ) );
        Coordinate end = new Coordinate( newX, newY );
        set( end );
    }

    public void set( Coordinate coordinate )
    {
        x = coordinate.getX();
        y = coordinate.getY();
    }

    public void set( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
}
