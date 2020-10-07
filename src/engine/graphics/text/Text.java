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

    private boolean isParagraph;
    private int paragraphWidth;
    private int spaceBetweenLines;

    private boolean showLetterForLetter;
    private int showingSpeed;
    private int lettersToShow;

    public Text( String string, Font font, int color )
    {
        setString( string );
        this.font = font;
        this.color = color;

        showLetterForLetter = true;
        spaceBetweenLetters = 0;
        spaceBetweenWords = font.getCharacterWidths()[ ' ' ];

        showingSpeed = 1;
    }

    public void draw( Renderer renderer )
    {
        int characterOffsetX = offsetX;
        int characterOffsetY = offsetY;

        int lettersShown = 0; // keeps track of how many letters are already shown

        for( int wordIndex = 0; wordIndex < words.length; wordIndex++ )
        {

            for( int characterIndex = 0; characterIndex < words[ wordIndex ].length(); characterIndex++ )
            {
                int unicode = words[ wordIndex ].codePointAt( characterIndex );

                if( showLetterForLetter )
                {
                    if( lettersShown < lettersToShow )
                    {
                        drawLetter( renderer, unicode, characterOffsetX, characterOffsetY );
                        characterOffsetX += font.getCharacterWidths()[ unicode ] + spaceBetweenLetters;
                        lettersShown++;
                    }
                }
                else
                {
                    drawLetter( renderer, unicode, characterOffsetX, characterOffsetY );
                    characterOffsetX += font.getCharacterWidths()[ unicode ] + spaceBetweenLetters;

                }
            }

            characterOffsetX += spaceBetweenWords;

            if( isParagraph &&
                wordIndex < words.length - 1 &&
                characterOffsetX + getWordWidth( words[ wordIndex + 1 ] ) > paragraphWidth + offsetX )
            {
                characterOffsetX = offsetX;
                characterOffsetY += font.getHeight() + spaceBetweenLines;
            }
        }

        updateLetterShowing();
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

    private void updateLetterShowing()
    {
        if( showLetterForLetter )
        {
            if( lettersToShow < string.length() )
            {
                lettersToShow += showingSpeed;
                if( lettersToShow > string.length() )
                    lettersToShow = string.length();
            }
        }
    }

    public String getString()
    {
        return string;
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

    public void setFont( Font font )
    {
        this.font = font;
    }

    public void setParagraph( boolean paragraph )
    {
        isParagraph = paragraph;
    }

    public void setParagraphWidth( int paragraphWidth )
    {
        this.paragraphWidth = paragraphWidth;
    }

    public void setSpaceBetweenLines( int spaceBetweenLines )
    {
        this.spaceBetweenLines = spaceBetweenLines;
    }

    public boolean isShowLetterForLetter()
    {
        return showLetterForLetter;
    }

    public void setShowLetterForLetter( boolean showLetterForLetter )
    {
        this.showLetterForLetter = showLetterForLetter;
    }

    public int getShowingSpeed()
    {
        return showingSpeed;
    }

    public void setShowingSpeed( int showingSpeed )
    {
        this.showingSpeed = showingSpeed;
    }

    public int getLettersToShow()
    {
        return lettersToShow;
    }

    public void setLettersToShow( int lettersToShow )
    {
        this.lettersToShow = lettersToShow;
    }
}
