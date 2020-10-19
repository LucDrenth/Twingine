package engine.graphics.shapes;

import engine.graphics.Renderer;
import engine.twinUtils.Coordinate;

public class Line
{
    public static void draw( Coordinate start, Coordinate end, int color, Renderer renderer )
    {
        int decidor = 0;

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
                decidor += dy2;

                if( decidor > dx )
                {
                    y += incrementDirectionY;
                    decidor -= dx2;
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
                decidor += dx2;

                if( decidor > dy )
                {
                    x += incrementDirectionX;
                    decidor -= dy2;
                }
            }
        }
    }
}
