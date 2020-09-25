package engine.graphics.image;

import engine.graphics.Renderer;
import engine.graphics.effects.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image
{
    private String path;
    private int[] pixels;

    private int alphaPercentage;
    private int width;
    private int height;
    private int offsetX;
    private int offsetY;

    private boolean blurInbound; // false gives value to surrounding "0 value" pixels, true only sets original colored (non 0 value) pixels

    public Image( String path )
    {
        this.path = path;

        loadImageFromPath( path );

        offsetX = 0;
        offsetY = 0;
        alphaPercentage = 100;
        blurInbound = false;
    }

    private void loadImageFromPath( String path )
    {
        try
        {
            BufferedImage image = ImageIO.read( Image.class.getResourceAsStream( path ) );
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB( 0, 0, width, height, null, 0, width );
            image.flush();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void draw( Renderer renderer )
    {
        for( int i = 0; i < pixels.length; i++ )
        {
            renderer.setPixel( i % width + offsetX, i / width + offsetY, pixels[i], alphaPercentage );
        }
    }

    // draw between 2 points and not outside them. First pixel draws at beginPointX and last pixel draws at endPointX
    public void drawBetweenX( Renderer renderer, int beginPointX, int endPointX )
    {
        for( int i = 0; i < pixels.length; i++ )
        {
            int x = i % width + offsetX;
            int y = i / width + offsetY;
            if( x >= beginPointX && x <= endPointX )
                renderer.setPixel( x, y, pixels[ i ], alphaPercentage );
        }
    }

    // draw between 2 points and not outside them. First pixel draws at beginPointY and last pixel draws at endPointY
    public void drawBetweenY( Renderer renderer, int beginPointY, int endPointY)
    {
        for( int i = 0; i < pixels.length; i++ )
        {
            int x = i % width + offsetX;
            int y = i / width + offsetY;
            if( y >= beginPointY && y <= endPointY )
                renderer.setPixel( x, y, pixels[ i ], alphaPercentage );
        }
    }

    // draw between 4 points and not outside them. First pixel draws at beginPointX, beginPointY and last pixel draws at endPointX, endPointY
    public void drawBetween( Renderer renderer, int beginPointX, int endPointX, int beginPointY, int endPointY )
    {
        for( int i = 0; i < pixels.length; i++ )
        {
            int x = i % width + offsetX;
            int y = i / width + offsetY;
            if( x >= beginPointX && x <= endPointX && y >= beginPointY && y <= endPointY )
                renderer.setPixel( x, y, pixels[ i ], alphaPercentage );
        }
    }

    /*
     * blurs the image, 0 being no blur
     * blurPercentage should be at least 0
     */
    public void blur( int blurPercentage )
    {
        pixels = Blur.blur( pixels, width, height, blurPercentage, blurInbound );
    }

    /*
     * scales the image up or down. Up being a positive scaleFactor and down being a negative scaleFactor
     */
    public void scale( float scaleFactor )
    {
        pixels = Scale.scale( pixels, width, height, scaleFactor );
        width = (int)( width * scaleFactor );
        height = (int)( height * scaleFactor );
    }

    public void scale( float scaleFactorX, float scaleFactorY )
    {
        pixels = Scale.scale( pixels, width, height, scaleFactorX, scaleFactorY );
        width = (int)( width * scaleFactorX );
        height = (int)( height * scaleFactorY );
    }

    public void rotate( int degrees )
    {
        // set the rotated pixels
        pixels = Rotate.rotate( pixels, width, height, degrees );

        // set the new width and height. Calculate them both before assigning so it will not use the new values
        int newWidth = Rotate.getWidthAfterRotation( width, height, degrees );
        int newHeight = Rotate.getHeightAfterRotation( width, height, degrees );
        width = newWidth;
        height = newHeight;
    }

    // HSB SHIFTING
    // shiftAmount should be a float between -1 and 1
    public void shiftHue     ( float shiftAmount     ){ pixels = HSB.shiftHue     ( pixels, shiftAmount     ); }
    public void addSaturation( float extraSaturation ){ pixels = HSB.addSaturation( pixels, extraSaturation ); }
    public void addBrightness( float extraBrightness ){ pixels = HSB.addBrightness( pixels, extraBrightness ); }

    // HSB SETTING
    // should be a float between 0 and 1
    public void setHue       ( float hue        ){ pixels = HSB.setHue( pixels, hue               ); }
    public void setSaturation( float saturation ){ pixels = HSB.setSaturation( pixels, saturation ); }
    public void setBrightness( float brightness ){ pixels = HSB.setBrightness( pixels, brightness ); }

    // FLIPPING
    public void flipHorizontal(){ pixels = Flip.horizontal( pixels, width, height ); }
    public void flipVertical(){ pixels = Flip.vertical( pixels, width, height ); }

    // SWAPPING RGB COLORS
    public void swapRedAndGreen (){ pixels = RGB.swapRedAndGreen( pixels ); }
    public void swapRedAndBlue  (){ pixels = RGB.swapRedAndBlue( pixels ); }
    public void swapGreenAndBlue(){ pixels = RGB.swapGreenAndBlue( pixels ); }

    // INCREASING RGB COLORS
    // percentage can reach from -100 to 100 (lower than 0 to decrease)
    public void increaseRed  ( int percentage ){ pixels = RGB.increaseRed  ( pixels, percentage ); }
    public void increaseGreen( int percentage ){ pixels = RGB.increaseGreen( pixels, percentage ); }
    public void increaseBlue ( int percentage ){ pixels = RGB.increaseBlue ( pixels, percentage ); }

    // GREY SCALE
    public void toGrayScale()
    {
        pixels = GrayScale.convert( pixels );
    }

    // numberOfGreys should be a value of min 2 and max 256
    public void toGrayScale( int numberOfGreys )
    {
        pixels = GrayScale.convertUsingCustomNumberOfGrays( pixels, numberOfGreys );
    }

    // border should be a number between 0 and 255 and determines from which value the colors go black or white
    public void toBlackAndWhite( int border )
    {
        pixels = BlackAndWhite.convert( pixels, border );
    }

    // temperatureChange should be min -50 and max 50
    public void changeTemperature( int temperatureChange )
    {
        pixels = Temperature.change( pixels, temperatureChange );
    }

    // tintChange should be min -20 and max 20
    public void changeTint( int tintChange )
    {
        pixels = Tint.change( pixels, tintChange );
    }

    public void mix( int spreadWidth, int spreadHeight )
    {
        pixels = Mix.randomizeInsideBox( pixels, width, height, spreadWidth, spreadHeight );
    }

    public void engrave( int shiftX, int shiftY, int extraColor )
    {
        pixels = Engrave.engrave( pixels, width, height, shiftX, shiftY, extraColor );
    }

    public void sepia()
    {
        pixels = Sepia.sepia( pixels );
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
        loadImageFromPath( path );
        alphaPercentage = 100;
    }

    public void setOffsets( int x, int y )
    {
        this.offsetX = x;
        this.offsetY = y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setWidth( int width )
    {
        this.width = width;
    }

    public void setHeight( int height )
    {
        this.height = height;
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
        return pixels;
    }

    public int getPixel( int x, int y )
    {
        return pixels[ x + y * width ];
    }

    private void setPixel( int x, int y, int color )
    {
        pixels[ x + y * width ] = color;
    }

    public void setPixels( int[] pixels )
    {
        this.pixels = pixels;
    }

    public int getOffsetX()
    {
        return offsetX;
    }

    public int getOffsetY()
    {
        return offsetY;
    }

    public void setBlurInbound( boolean blurInbound )
    {
        this.blurInbound = blurInbound;
    }
}
