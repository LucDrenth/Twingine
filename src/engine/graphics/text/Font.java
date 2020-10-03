/*
 * How to use:
 *
 * When creating a font, String name in the constructor should be one of:
 * - "Arial" when you want to use a font from an existing .ttf file in fonts/ttf
 * - "Arial.png" when you want to use a custom bitmap from a .png file from fonts/bitmaps. To create a bitmap as .png
 *   from a ttf file, call createAndSaveAsPng().
 */

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
        if( isPng( name ) )
            bitmap = new Image( "/fonts/bitmaps/" + name );
        else if( isTtf( name ) )
            bitmap = new Image( FontBitmap.create( name, fontSize ) );

        calculateCharacterData();
    }

    private boolean isPng( String name )
    {
        return name.length() > 4 && name.substring( name.length() - 4 ).equals( ".png" );
    }

    private boolean isTtf( String name )
    {
        return name.endsWith( ".ttf" );
    }

    private void calculateCharacterData()
    {
        characterOffsets = new int[ FontBitmap.getCharacters() ];
        characterWidths = new int[ FontBitmap.getCharacters() ];

        int currentCharacter = 0;

        for( int i = 0; i < bitmap.getWidth(); i++ )
        {
            if( bitmap.getPixels()[ i ] == Color.RED.hashCode() )
            {
                characterOffsets[ currentCharacter ] = i;
            }

            if( bitmap.getPixels()[ i ] == Color.BLUE.hashCode() )
            {
                characterWidths[ currentCharacter ] = i - characterOffsets[ currentCharacter ] - 1;
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

    public int getHeight()
    {
        return bitmap.getHeight() - 1;
    }
}
