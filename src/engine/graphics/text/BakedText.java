package engine.graphics.text;

import engine.graphics.pixeldata.PixelData;
import engine.graphics.Renderer;
import engine.twinUtils.Point;

import java.awt.*;

public class BakedText
{
    private PixelData pixelData;
    private Point offset;

    public BakedText( Text text )
    {
        pixelData = bake( text );
        offset = new Point( 0, 0 );
    }

    public void draw( Renderer renderer )
    {
        renderer.draw( pixelData, offset );
    }

    private static PixelData bake( Text text )
    {
        int width = calculateBakedWidth( text );
        int height = calculateBakedHeight( text );
        PixelData pixelData = new PixelData( width, height );

        Point characterOffset = new Point( 0, 0 );

        // loop trough all word
        for( int wordIndex = 0; wordIndex < text.getWords().length; wordIndex++ )
        {
            bakeWord( text, wordIndex, characterOffset, pixelData );
        }

        return pixelData;
    }

    private static void bakeWord( Text text, int wordIndex, Point characterOffset, PixelData pixelData )
    {
        for( int characterIndex = 0; characterIndex < text.getWords()[ wordIndex ].length(); characterIndex++ )
        {
            int unicode = text.getWords()[ wordIndex ].codePointAt( characterIndex );
            bakeLetter( text, unicode, pixelData, characterOffset );
            characterOffset.addX( text.getFont().getCharacterWidths()[ unicode ] + text.getSpaceBetweenLetters() );
        }

        characterOffset.addX( text.getSpaceBetweenWords() );

        if( wordShouldWrapAround( text, wordIndex, characterOffset ) ) // adding the next word exceeds the paragraph width
        {
            characterOffset.setX( 0 );
            characterOffset.addY( text.getFont().getHeight() + text.getSpaceBetweenLines() );
        }
    }

    private static void bakeLetter( Text text, int unicode, PixelData pixelData, Point characterOffset )
    {
        for( int x = 0; x < text.getFont().getCharacterWidths()[ unicode ]; x++ )
        {
            for( int y = 1; y < text.getFont().getHeight(); y++ )
            {
                int alpha = ( text.getFont().getBitmap().getPixel( x + text.getFont().getCharacterOffsets()[ unicode ], y ) >> 24 ) & 0xff;
                if( alpha > 0 )
                {
                    int alphaPercentageInBitmap = (int)( (float)alpha / 255 * 100 );
                    int alphaPercentageToSet = (int)( (float)alphaPercentageInBitmap / 100 * text.getAlphaPercentage() );
                    Color textColor = new Color( text.getColor() );
                    Color colorAfterAlpha = new Color( textColor.getRed(), textColor.getGreen(), textColor.getBlue(), (int)( (float)alphaPercentageToSet * 2.55f ) );

                    pixelData.setPixel( x + characterOffset.getX(), y + characterOffset.getY(), colorAfterAlpha.hashCode() );
                }
            }
        }
    }

    private static boolean wordShouldWrapAround( Text text, int wordIndex, Point characterOffset )
    {
        return text.isParagraph() &&
               wordIndex < text.getWords().length - 1 && // is not the last word
               characterOffset.getX() + text.getWordWidth( text.getWords()[ wordIndex + 1 ] ) > text.getParagraphWidth(); // check if adding the word exceeds the paragraph width
    }

    private static int calculateBakedWidth( Text text )
    {
        int width = 0;

        for( String word : text.getWords() )
        {
            if( text.isParagraph() )
            {
                if( width + text.getWordWidth( word ) >= text.getParagraphWidth() )
                    return text.getParagraphWidth();
                else
                {
                    width += text.getWordWidth( word );
                    width += text.getSpaceBetweenWords();
                }
            }
            else
            {
                width += text.getWordWidth( word );
                width += text.getSpaceBetweenWords();
            }
        }

        return width - text.getSpaceBetweenWords();
    }

    private static int calculateBakedHeight( Text text )
    {
        if( !text.isParagraph() )
        {
            return text.getFont().getHeight();
        }
        else
        {
            int height = text.getFont().getHeight();
            int width = 0;

            for( String word : text.getWords() )
            {
                if( width + text.getWordWidth( word ) > text.getParagraphWidth() )
                {
                    width = text.getWordWidth( word );
                    height += text.getSpaceBetweenLines();
                    height += text.getFont().getHeight();
                }
                else
                {
                    width += text.getWordWidth( word );
                }

                width += text.getSpaceBetweenWords();
            }

            return height;
        }
    }

    public PixelData getPixelData()
    {
        return pixelData;
    }

    public Point getOffset()
    {
        return offset;
    }

    public void setOffset( int x, int y )
    {
        offset.setX( x );
        offset.setY( y );
    }
}
