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
    private int alphaPercentage;

    private int spaceBetweenLetters;
    private int spaceBetweenWords;

    private boolean isParagraph;
    private int paragraphWidth;
    private int spaceBetweenLines;

    private boolean showLetterForLetter;
    private int showingSpeed;
    private int lettersToShow;

    private boolean drawBetweenX;
    private int drawFromX;
    private int drawUntilX;

    private boolean drawBetweenY;
    private int drawFromY;
    private int drawUntilY;

    public Text( String string, Font font, int color )
    {
        setString( string );
        this.font = font;
        this.color = color;
        alphaPercentage = 100;

        spaceBetweenLetters = 0;
        spaceBetweenWords = font.getCharacterWidths()[ ' ' ];

        showingSpeed = 1;
        alphaPercentage = 100;
    }

    public void update()
    {
        updateLetterShowing();
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
                wordIndex < words.length - 1 && // is not the last word
                characterOffsetX + getWordWidth( words[ wordIndex + 1 ] ) > paragraphWidth + offsetX ) // adding the next word exceeds the paragraph width
            {
                characterOffsetX = offsetX;
                characterOffsetY += font.getHeight() + spaceBetweenLines;
            }
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
                    int alphaPercentageInBitmap = (int)( (float)alpha / 255 * 100 );
                    int alphaPercentageToDraw = (int)( (float)alphaPercentageInBitmap / 100 * this.alphaPercentage );

                    if( !drawBetweenX || ( offsetX + x >= drawFromX && offsetX + x <= drawUntilX ) )
                    {
                        if( !drawBetweenY || ( offsetY + y >= drawFromY && offsetY + y <= drawUntilY ) )
                        {
                            renderer.setPixel( offsetX + x, offsetY + y, color, alphaPercentageToDraw );
                        }
                    }
                }
            }
        }
    }

    public int getWordWidth( String string )
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

    public int getAlphaPercentage()
    {
        return alphaPercentage;
    }

    public void setAlphaPercentage( int alphaPercentage )
    {
        this.alphaPercentage = alphaPercentage;
        if( alphaPercentage < 0 ) this.alphaPercentage = 0;
        if( alphaPercentage > 100 ) this.alphaPercentage = 100;
    }

    public void setDrawBetweenX( boolean drawBetweenX )
    {
        this.drawBetweenX = drawBetweenX;
    }

    public void setDrawBetweenXPoints( int drawFromX, int drawUntilX )
    {
        this.drawFromX = drawFromX;
        this.drawUntilX = drawUntilX;
    }

    public void setDrawBetweenY( boolean drawBetweenY )
    {
        this.drawBetweenY = drawBetweenY;
    }

    public void setDrawBetweenYPoints( int drawFromY, int drawUntilY )
    {
        this.drawFromY = drawFromY;
        this.drawUntilY = drawUntilY;
    }

    public String[] getWords()
    {
        return words;
    }

    public boolean isParagraph()
    {
        return isParagraph;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor( int color )
    {
        this.color = color;
    }

    public int getParagraphWidth()
    {
        return paragraphWidth;
    }

    public int getSpaceBetweenLines()
    {
        return spaceBetweenLines;
    }

    public Font getFont()
    {
        return font;
    }
}
