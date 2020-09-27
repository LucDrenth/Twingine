package engine.graphics.text;

import engine.graphics.image.Image;

import java.awt.*;

public class Font
{
    private Image bitmap;
    private int[] characterOffsets;
    private int[] characterWidths;

    public Font( String name, float fontSize )
    {
        bitmap = new Image( FontBitmap.create( name, fontSize ) );
        characterOffsets = new int[ 256 ];
        characterWidths = new int[ 256 ];

        int currentCharacter = 0;

        for( int i = 0; i < bitmap.getWidth(); i++ )
        {
            if( bitmap.getPixels()[ i ] == Color.RED.hashCode() )
            {
                characterOffsets[ currentCharacter ] = i;
            }

            if( bitmap.getPixels()[ i ] == Color.BLUE.hashCode() )
            {
                characterWidths[ currentCharacter ] = i - characterOffsets[ currentCharacter ];
                currentCharacter++;
            }
        }
    }

    public Image getBitmap()
    {
        return bitmap;
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

