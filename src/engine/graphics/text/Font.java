package engine.graphics.text;

import engine.graphics.image.Image;

import java.awt.*;

public class Font
{
    private Image fontImage;
    private int[] characterOffsets;
    private int[] characterWidths;

    private Font( String name )
    {
        String path = FontBitmap.getBitmapsDirectoryPath() + " / " + name + ".png";
        fontImage = new Image( path );
        characterOffsets = new int[ 256 ];
        characterWidths = new int[ 256 ];

        int currentCharacter = 0;

        for( int i = 0; i < fontImage.getWidth(); i++ )
        {
            if( fontImage.getPixels()[ i ] == Color.RED.hashCode() )
            {
                characterOffsets[ currentCharacter ] = i;
            }

            if( fontImage.getPixels()[ i ] == Color.BLUE.hashCode() )
            {
                characterWidths[ currentCharacter ] = i - characterOffsets[ currentCharacter ];
                currentCharacter++;
            }
        }
    }

    public Image getFontImage()
    {
        return fontImage;
    }

    public int[] getCharacterOffsets()
    {
        return characterOffsets;
    }

    public int[] getCharacterWidths()
    {
        return characterWidths;
    }
}

