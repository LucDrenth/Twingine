package engine.graphics.shapes;

import engine.graphics.Renderer;
import engine.graphics.pixeldata.PixelData;
import engine.twinUtils.Coordinate;

public class Line
{
    private PixelData pixelData;
    private Coordinate offset;

    public Line( Coordinate start, Coordinate end, int color )
    {
        instantiateLine( start, end, color );
    }

    public Line( Coordinate start, int lineLength, int angle, int color )
    {
        Coordinate end = new Coordinate( start );
        end.moveByAngle( angle, lineLength );

        instantiateLine( start, end, color );
    }

    private void instantiateLine( Coordinate start, Coordinate end, int color )
    {
        int width = start.getX() - end.getX();
        if( width < 0 ) width *= -1;
        int height = start.getY() - end.getY();
        if( height < 0 ) height *= -1;
        width++;
        height++;

        this.pixelData = new PixelData( width, height );
        fillPixelData( pixelData, start, end, color );

        this.offset = new Coordinate( 0, 0 );
    }

    private void fillPixelData( PixelData pixelData, Coordinate start, Coordinate end, int color )
    {
        int decider = 0;

        int dx = Math.abs( end.getX() - start.getX() );
        int dy = Math.abs( end.getY() - start.getY() );

        // slope scaling factors to avoid floating point
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        int incrementDirectionX = start.getX() < end.getX() ? 1 : -1;
        int incrementDirectionY = start.getY() < end.getY() ? 1 : -1;

        int lowestX = start.getX();
        if( end.getX() < start.getX() ) lowestX = end.getX();
        int lowestY = start.getY();
        if( end.getY() < start.getY() ) lowestY = end.getY();

        int x = start.getX() - lowestX;
        int y = start.getY() - lowestY;

        if( dx >= dy )
        {
            while( true )
            {
                pixelData.setPixel( x, y, color );

                if( x == end.getX() - lowestX ) // if done drawing the line
                    break;

                x += incrementDirectionX;
                decider += dy2;

                if( decider > dx )
                {
                    y += incrementDirectionY;
                    decider -= dx2;
                }
            }
        }
        else
        {
            while( true )
            {
                pixelData.setPixel( x, y, color );

                if( y == end.getY() - lowestY ) // if done drawing the line
                    break;

                y += incrementDirectionY;
                decider += dx2;

                if( decider > dy )
                {
                    x += incrementDirectionX;
                    decider -= dy2;
                }
            }
        }
    }

    public void draw( Renderer renderer )
    {
        renderer.draw( pixelData, offset );
    }

    public void setOffsets( int x, int y )
    {
        offset.set( x, y );
    }

    public PixelData getPixelData()
    {
        return pixelData;
    }

    public void setPixelData( PixelData pixelData )
    {
        this.pixelData = pixelData;
    }

    public Coordinate getOffset()
    {
        return offset;
    }

    public static void draw( Coordinate start, Coordinate end, int color, Renderer renderer )
    {
        int decider = 0;

        int dx = Math.abs( end.getX() - start.getX() );
        int dy = Math.abs( end.getY() - start.getY() );

        // slope scaling factors to avoid floating point
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        int incrementDirectionX = start.getX() < end.getX() ? 1 : -1;
        int incrementDirectionY = start.getY() < end.getY() ? 1 : -1;

        int x = start.getX();
        int y = start.getY();

        if( dx >= dy )
        {
            while( true )
            {
                renderer.setPixel( x, y, color );

                if( x == end.getX() ) // if done drawing the line
                    break;

                x += incrementDirectionX;
                decider += dy2;

                if( decider > dx )
                {
                    y += incrementDirectionY;
                    decider -= dx2;
                }
            }
        }
        else
        {
            while( true )
            {
                renderer.setPixel( x, y, color );

                if( y == end.getY() ) // if done drawing the line
                    break;

                y += incrementDirectionY;
                decider += dx2;

                if( decider > dy )
                {
                    x += incrementDirectionX;
                    decider -= dy2;
                }
            }
        }
    }

    public static void draw( Coordinate start, int lineLength, int angle, int color, Renderer renderer )
    {
        Coordinate end = new Coordinate( start );
        end.moveByAngle( angle, lineLength );

        draw( start, end, color, renderer );
    }
}
