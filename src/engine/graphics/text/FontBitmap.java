package engine.graphics.text;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Font;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Hashtable;

public class FontBitmap
{
    private static final String ttfDirectoryPath = "res/fonts/ttf";
    private static final String bitmapsDirectoryPath = "res/fonts/bitmaps";
    private static final int characters = 256;

    public static BufferedImage create( String fontName, float fontSize )
    {
        Font font = createFontFromTTF( fontName, fontSize );
        RenderedImage renderedImage = fontToBitmap( font, fontSize );
        return convertRenderedImage( renderedImage );
    }

    /*
     * - fontName should be the name of an existing .ttf file in fonts/bitmaps/ttf and should include .ttf
     * - outputFileName should be the name of the file of the bitmap that wil be created in fonts/bitmaps and should not
     *   include .png
     */
    public static void createAndSaveAsPng( String fontName, float fontSize, String outputFileName )
    {
        Font font = createFontFromTTF( fontName, fontSize );
        RenderedImage renderedImage = fontToBitmap( font, fontSize );
        saveAsPng( renderedImage, fontName, fontSize, outputFileName );
    }

    private static Font createFontFromTTF( String name, float size )
    {
        String path = ttfDirectoryPath + "/" + name;
        try
        {
            InputStream inputStream = new BufferedInputStream( new FileInputStream( path ) );
            Font font = Font.createFont( Font.TRUETYPE_FONT, inputStream );
            font = font.deriveFont( size );
            return font;
        }
        catch( FontFormatException | IOException e )
        {
            e.printStackTrace();
            return null;
        }
    }

    private static RenderedImage fontToBitmap( Font font, float fontSize )
    {
        // Create an instance of Graphics2D
        BufferedImage img = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
        Graphics2D graphics2D = img.createGraphics();

        // calculate bitmap size
        FontMetrics fontMetrics = new JPanel().getFontMetrics( font );
        FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();

        int width = calculateBitmapWidth( fontMetrics );
        int height = calculateBitmapHeight( font, fontRenderContext );

        // Calculate offset of font due to wrong height of chars like '(', ')' or 'Q'
        fontSize = calculateFontSize( fontSize, font, fontRenderContext );

        // Create image and get the Graphics to draw
        BufferedImage sprite = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D graphics = sprite.createGraphics();
        graphics.setColor( new Color( 0, 0, 0, 0 ) );
        graphics.fillRect( 0, 0, width, height );
        graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        graphics.setFont( font );

        // draw the characters in the image
        drawCharactersToImage( graphics, fontMetrics, (int) fontSize );

        return sprite;
    }

    private static int calculateBitmapWidth( FontMetrics fontMetrics )
    {
        int width = 0;

        for( int c = 0; c < characters; c++ )
        {
            width += fontMetrics.charWidth( (char) c ) + 2;
        }

        return width;
    }

    private static int calculateBitmapHeight( Font font, FontRenderContext fontRenderContext )
    {
        StringBuilder buffer = new StringBuilder( characters );
        for( int c = 0; c < characters; c++ )
        {
            buffer.append( (char) c );
        }

        GlyphVector vec = font.createGlyphVector( fontRenderContext, buffer.toString() );
        Rectangle bounds = vec.getPixelBounds( null, 0, 0 );
        return bounds.height + 1;
    }

    private static float calculateFontSize( float fontSize, Font font, FontRenderContext fontRenderContext )
    {
        int[] sizes = new int[ characters ];

        for( int c = 0; c < characters; c++ )
        {
            GlyphVector glyphVector = font.createGlyphVector( fontRenderContext, String.valueOf( (char) c ) );
            sizes[ c ] = glyphVector.getPixelBounds( null, 0, 0 ).height;
        }

        int total = 0;
        for( int size : sizes )
            total += size;

        int ratio = ( total / sizes.length );
        fontSize = ( fontSize + ratio ) / 2;

        return fontSize;
    }

    private static void drawCharactersToImage( Graphics2D graphics, FontMetrics fontMetrics, int fontSize )
    {
        int offsetX = 0;
        int offsetY;

        for( int c = 0; c < characters; c++ )
        {
            // draw a red dot to mark the beginning of the character
            offsetY = 0;
            graphics.setColor( new Color( 255, 0, 0 ) );
            graphics.drawLine( offsetX, offsetY, offsetX, offsetY );
            offsetX++;

            // draw the character
            offsetY = 1;
            graphics.setColor( Color.WHITE );
            graphics.drawString( String.valueOf( (char) c ), offsetX, offsetY + fontSize );
            offsetX += fontMetrics.charWidth( (char) c );

            // draw a blue dot to mark the end of a character
            offsetY = 0;
            graphics.setColor( new Color( 0, 0, 255 ) );
            graphics.drawLine( offsetX, offsetY, offsetX, offsetY );
            offsetX++;
        }
    }

    private static void saveAsPng( RenderedImage renderedImage, String fontName, float fontSize, String outputFileName )
    {
        String fileName = bitmapsDirectoryPath + "/" + outputFileName + ".png";

        File file = new File( fileName );
        try
        {
            ImageIO.write( renderedImage, "png", file );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private static BufferedImage convertRenderedImage( RenderedImage renderedImage )
    {
        if( renderedImage instanceof BufferedImage )
            return (BufferedImage) renderedImage;

        ColorModel cm = renderedImage.getColorModel();
        int width = renderedImage.getWidth();
        int height = renderedImage.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster( width, height );
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable<String, Object> properties = new Hashtable<>();
        String[] keys = renderedImage.getPropertyNames();

        if( keys != null )
        {
            for( String key : keys )
                properties.put( key, renderedImage.getProperty( key ) );
        }

        BufferedImage result = new BufferedImage( cm, raster, isAlphaPremultiplied, properties );
        renderedImage.copyData( raster );
        return result;
    }

    public static int getCharacters()
    {
        return characters;
    }
}
