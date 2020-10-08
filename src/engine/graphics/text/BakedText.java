package engine.graphics.text;

import engine.graphics.PixelData;
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

    private PixelData bake( Text text )
    {
        int width = calculateBakedWidth( text );
        int height = calculateBakedHeight( text );
        PixelData pixelData = new PixelData( width, height );

        int characterOffsetX = 0;
        int characterOffsetY = 0;

        // loop trough all word
        for( int wordIndex = 0; wordIndex < text.getWords().length; wordIndex++ )
        {
            // loop trough all letters of the word
            for( int characterIndex = 0; characterIndex < text.getWords()[ wordIndex ].length(); characterIndex++ )
            {
                int unicode = text.getWords()[ wordIndex ].codePointAt( characterIndex );

                // loop trough bitmap at letter its position
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

                            pixelData.setPixel( x + characterOffsetX, y + characterOffsetY, colorAfterAlpha.hashCode() );
                        }
                    }
                }

                characterOffsetX += text.getFont().getCharacterWidths()[ unicode ] + text.getSpaceBetweenLetters();
            }

            characterOffsetX += text.getSpaceBetweenWords();

            if( text.isParagraph() &&
                wordIndex < text.getWords().length - 1 && // is not the last word
                characterOffsetX + text.getWordWidth( text.getWords()[ wordIndex + 1 ] ) > text.getParagraphWidth() ) // adding the next word exceeds the paragraph width
            {
                characterOffsetX = 0;
                characterOffsetY += text.getFont().getHeight() + text.getSpaceBetweenLines();
            }
        }

        return pixelData;
    }

    private int calculateBakedWidth( Text text )
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

    private int calculateBakedHeight( Text text )
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
