package engine.graphics.text;

import engine.graphics.Renderer;
import engine.twinUtils.StringUtils;

public class Text
{
    private Font font;

    private String string;
    private String[] words;

    private int offsetX;
    private int offsetY;

    private int color;

    private int spaceBetweenLetters;
    private int spaceBetweenWords;

    public Text( String string, Font font, int color )
    {
        setString( string );
        this.font = font;
        this.color = color;

        spaceBetweenLetters = 0;
        spaceBetweenWords = font.getCharacterWidths()[ ' ' ];
    }

    public void draw( Renderer renderer )
    {
        int characterOffsetX = offsetX;
        int characterOffsetY = offsetY;
        for( String word : words )
        {
            for( int i = 0; i < word.length(); i++ )
            {
                int unicode = word.codePointAt( i );

                drawLetter( renderer, unicode, characterOffsetX, characterOffsetY );
                characterOffsetX += font.getCharacterWidths()[ unicode ] + spaceBetweenLetters;
            }

            characterOffsetX += spaceBetweenWords;
        }
    }

    private void drawLetter( Renderer renderer, int unicode, int offsetX, int offsetY )
    {
        for( int x = 0; x < font.getCharacterWidths()[ unicode ]; x++ )
        {
            for( int y = 1; y < font.getHeight(); y++ )
            {
                int alpha = ( font.getBitmap().getPixel( x + font.getCharacterOffsets()[ unicode ], y ) >> 24 ) & 0xff;
                if( alpha > 0 )
                {
                    int alphaPercentage = (int)( (float)alpha / 255 * 100 );
                    renderer.setPixel( offsetX + x, offsetY + y, color, alphaPercentage );
                }
            }
        }
    }

    private int getWordWidth( String string )
    {
        int wordWidth = 0;

        for( int i = 0; i < string.length(); i++ )
        {
            wordWidth += font.getCharacterWidths()[ string.codePointAt( i ) ];
            wordWidth += spaceBetweenLetters;
        }

        // return with "- space between letters" because the last letter does not have space behind it
        return wordWidth - spaceBetweenLetters;
    }

    public void setString( String string )
    {
        this.string = string;
        words = StringUtils.getWords( string );
    }


    public void setOffsets( int offsetX, int offsetY )
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getSpaceBetweenLetters()
    {
        return spaceBetweenLetters;
    }

    public void setSpaceBetweenLetters( int spaceBetweenLetters )
    {
        this.spaceBetweenLetters = spaceBetweenLetters;
    }

    public int getSpaceBetweenWords()
    {
        return spaceBetweenWords;
    }

    public void setSpaceBetweenWords( int spaceBetweenWords )
    {
        this.spaceBetweenWords = spaceBetweenWords;
    }
}
