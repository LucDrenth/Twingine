package engine.twinUtils;

public class Point
{
    private int x;
    private int y;

    public Point( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public Point( Point point )
    {
        this.x = point.getX();
        this.y = point.getY();
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

    public void add( Point point )
    {
        x += point.getX();
        y += point.getY();
    }

    public void subtract( Point point )
    {
        x -= point.getX();
        y -= point.getY();
    }

    public void set( Point point )
    {
        x = point.getX();
        y = point.getY();
    }

    public void set( int x, int y )
    {
        this.x = x;
        this.y = y;
    }
}
