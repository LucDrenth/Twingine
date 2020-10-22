package engine.graphics.shapes;

import engine.graphics.Renderer;
import engine.twinUtils.Coordinate;

public class Star
{
    public static void draw( int points, int highPoints, int lowPoints, Coordinate offset, int color, Renderer renderer )
    {
        for( int i = 0; i < points; i++ )
        {
            Coordinate coordinate1 = new Coordinate( offset.getX(), offset.getY() );
            Coordinate coordinate2 = new Coordinate( offset.getX(), offset.getY() );
            Coordinate coordinate3 = new Coordinate( offset.getX(), offset.getY() );
            coordinate1.moveByAngle( (int)( 360 / (float)points * i ), highPoints );
            coordinate2.moveByAngle( (int)( 360 / (float)points * (i + 0.5f) ), lowPoints );
            coordinate3.moveByAngle( (int)( 360 / (float)points * ( i + 1 ) ), highPoints );

            Line.draw( coordinate1, coordinate2, color, renderer );
            Line.draw( coordinate2, coordinate3, color, renderer );
        }
    }
}
