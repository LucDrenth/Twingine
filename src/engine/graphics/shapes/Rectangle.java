package engine.graphics.shapes;

import engine.graphics.Renderer;
import engine.graphics.pixeldata.BooleanMask;
import engine.graphics.pixeldata.PixelData;
import engine.twinUtils.Coordinate;

public class Rectangle
{
    private PixelData pixelData;
    private Coordinate offset;
    private BooleanMask roundedCornersMask;

    private int color;
    private int alphaPercentage;

    public Rectangle( int width, int height, int color )
    {
        pixelData = new PixelData( width, height );
        this.color = color;
        pixelData.fill( color );

        offset = new Coordinate( 0, 0 );
        alphaPercentage = 100;
    }

    public void draw( Renderer renderer )
    {

        for( int x = 0; x < pixelData.getWidth(); x++ )
        {
            for( int y = 0; y < pixelData.getHeight(); y++ )
            {
                if( roundedCornersMask == null )
                    renderer.setPixel( x + offset.getX(), y + offset.getY(), color, alphaPercentage );
                else
                {
                    if( roundedCornersMask.get( x, y ) )
                        renderer.setPixel( x + offset.getX(), y + offset.getY(), color, alphaPercentage );
                }
            }
        }
    }

    public void setOffsets( int x, int y )
    {
        offset.set( x, y );
    }

    public void roundCorners( int radius )
    {
        if( roundedCornersMask == null )
            roundedCornersMask = new BooleanMask( getWidth(), getHeight(), true );

        roundedCornersMask.roundCorners( radius );
    }

    public void roundCorners( int radiusTopLeft, int radiusTopRight, int radiusBottomLeft, int radiusBottomRight )
    {
        if( roundedCornersMask == null )
            roundedCornersMask = new BooleanMask( getWidth(), getHeight(), true );

        roundedCornersMask.roundCorners( radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight );
    }

    public int getColor()
    {
        return color;
    }

    public void setColor( int color )
    {
        this.color = color;
    }

    public int getOffsetX()
    {
        return offset.getX();
    }

    public int getOffsetY()
    {
        return offset.getY();
    }

    public int getWidth()
    {
        return pixelData.getWidth();
    }

    public int getHeight()
    {
        return pixelData.getHeight();
    }

    public int getAlphaPercentage()
    {
        return alphaPercentage;
    }

    public void setAlphaPercentage( int alphaPercentage )
    {
        this.alphaPercentage = alphaPercentage;
    }
}
