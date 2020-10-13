package engine.graphics.pixeldata;

import engine.graphics.shapes.Circle;

import java.util.Arrays;

public class BooleanMask
{
    boolean[] mask;
    int width;
    int height;

    public BooleanMask( int width, int height, boolean defaultValue )
    {
        this.width = width;
        this.height = height;

        mask = new boolean[ width * height ];
        Arrays.fill( mask, defaultValue );
    }

    public void roundCorners( int radius )
    {
        roundCornerTopLeft( radius );
        roundCornerTopRight( radius );
        roundCornerBottomLeft( radius );
        roundCornerBottomRight( radius );
    }

    public void roundCorners(
            int radiusTopLeft,
            int radiusTopRight,
            int radiusBottomLeft,
            int radiusBottomRight )
    {
        roundCornerTopLeft( radiusTopLeft );
        roundCornerTopRight( radiusTopRight );
        roundCornerBottomLeft( radiusBottomLeft );
        roundCornerBottomRight( radiusBottomRight );
    }

    private void roundCornerTopLeft( int radius )
    {
        Circle circle = new Circle( radius );
        circle.fill();

        for( int x = 0; x < radius; x++ )
        {
            for( int y = 0; y < radius; y++ )
            {
                if( circle.getPixels().getPixel( x, y ) == 0 )
                {
                    set( x, y, false );
                }
            }
        }
    }

    private void roundCornerTopRight( int radius )
    {
        Circle circle = new Circle( radius );
        circle.fill();

        for( int x = 0; x < radius; x++ )
        {
            for( int y = 0; y < radius; y++ )
            {
                if( circle.getPixels().getPixel( x, y ) == 0 )
                {
                    set( width - 1 - x, y, false );
                }
            }
        }
    }

    private void roundCornerBottomLeft( int radius )
    {
        Circle circle = new Circle( radius );
        circle.fill();

        for( int x = 0; x < radius; x++ )
        {
            for( int y = 0; y < radius; y++ )
            {
                if( circle.getPixels().getPixel( x, y ) == 0 )
                {
                    set( x, height - 1 - y, false );
                }
            }
        }
    }

    private void roundCornerBottomRight( int radius )
    {
        Circle circle = new Circle( radius );
        circle.fill();

        for( int x = 0; x < radius; x++ )
        {
            for( int y = 0; y < radius; y++ )
            {
                if( circle.getPixels().getPixel( x, y ) == 0 )
                {
                    set( width - 1 - x, height - 1 - y, false );
                }
            }
        }
    }

    public boolean get( int x, int y )
    {
        return mask[ x + y * width ];
    }

    public void set( int x, int y, boolean value )
    {
        mask[ x + y * width ] = value;
    }
}
