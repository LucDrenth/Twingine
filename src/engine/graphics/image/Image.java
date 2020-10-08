package engine.graphics.image;

import engine.graphics.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image
{
    // one of path and bufferedImage should be null. One should be stored for the reset function
    private String path;
    private BufferedImage bufferedImage;

    private PixelData pixelData;
    private Point offset;

    private int alphaPercentage;

    private boolean blurInbound; // false gives value to surrounding "0 value" pixels, true only sets original colored (non 0 value) pixels

    public Image( String path )
    {
        this.path = path;
        loadImageFromPath( path );
        initVariables();
    }

    public Image( BufferedImage bufferedImage )
    {
        this.bufferedImage = bufferedImage;
        loadPixelDataFromBufferedImage( bufferedImage );
        initVariables();
    }

    private void loadImageFromPath( String path )
    {
        try
        {
            BufferedImage bufferedImage = ImageIO.read( Image.class.getResourceAsStream( path ) );
            loadPixelDataFromBufferedImage( bufferedImage );
            bufferedImage.flush();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void loadPixelDataFromBufferedImage( BufferedImage bufferedImage )
    {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixels = bufferedImage.getRGB( 0, 0, width, height, null, 0, width );
        pixelData = new PixelData( pixels, width, height );
        bufferedImage.flush();
    }

    private void initVariables()
    {
        offset = new Point( 0, 0 );
        alphaPercentage = 100;
        blurInbound = false;
    }

    public void draw( Renderer renderer )
    {
        renderer.draw( pixelData, offset );
    }

    // draw between 2 points and not outside them. First pixel draws at beginPointX and last pixel draws at endPointX
    public void drawBetweenX( Renderer renderer, int beginPointX, int endPointX )
    {
        for( int i = 0; i < pixelData.getPixels().length; i++ )
        {
            int x = i % getWidth() + offset.getX();
            int y = i / getWidth() + offset.getY();
            if( x >= beginPointX && x <= endPointX )
                renderer.setPixel( x, y, pixelData.getPixel( i ), alphaPercentage );
        }
    }

    // draw between 2 points and not outside them. First pixel draws at beginPointY and last pixel draws at endPointY
    public void drawBetweenY( Renderer renderer, int beginPointY, int endPointY)
    {
        for( int i = 0; i < pixelData.getPixels().length; i++ )
        {
            int x = i % getWidth() + offset.getX();
            int y = i / getWidth() + offset.getY();
            if( y >= beginPointY && y <= endPointY )
                renderer.setPixel( x, y, pixelData.getPixel( i ), alphaPercentage );
        }
    }

    // draw between 4 points and not outside them. First pixel draws at beginPointX, beginPointY and last pixel draws at endPointX, endPointY
    public void drawBetween( Renderer renderer, int beginPointX, int endPointX, int beginPointY, int endPointY )
    {
        for( int i = 0; i < pixelData.getPixels().length; i++ )
        {
            int x = i % getWidth() + offset.getX();
            int y = i / getWidth() + offset.getY();
            if( x >= beginPointX && x <= endPointX && y >= beginPointY && y <= endPointY )
                renderer.setPixel( x, y, pixelData.getPixel( i ), alphaPercentage );
        }
    }

    public int[] getPartOfImage( int startX, int endX, int startY, int endY )
    {
        int width = endX - startX;
        int height = endY - startY;
        int[] partOfImage = new int[ width * height ];
        System.out.println( partOfImage.length );

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                partOfImage[ x + y * width ] = getPixel( startX + x, startY + y );
            }
        }

        return partOfImage;
    }

    public void setPartOfImage( int startX, int endX, int startY, int endY, int[] pixels )
    {
        int width = endX - startX;
        int height = endY - startY;
        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                setPixel( startX + x, startY + y, pixels[ x + y * width ] );
            }
        }
    }

    // resets the image by loading it again
    public void reset()
    {
        if( bufferedImage == null )
            loadImageFromPath( path );
        else
            loadPixelDataFromBufferedImage( bufferedImage );

        alphaPercentage = 100;
    }

    public void setOffsets( int x, int y )
    {
        offset.set( x, y );
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

    public int[] getPixels()
    {
        return pixelData.getPixels();
    }

    public int getPixel( int x, int y )
    {
        return pixelData.getPixel( x, y );
    }

    private void setPixel( int x, int y, int color )
    {
        pixelData.setPixel( x, y, color );
    }

    public void setPixels( int[] pixels )
    {
        this.pixelData.setPixels( pixels );
    }

    public int getOffsetX()
    {
        return offset.getX();
    }

    public int getOffsetY()
    {
        return offset.getY();
    }

    public void setBlurInbound( boolean blurInbound )
    {
        this.blurInbound = blurInbound;
    }
}
