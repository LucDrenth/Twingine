package engine.graphics.image;

import engine.Engine;
import engine.graphics.Renderer;

import java.util.Arrays;

public class Sprite
{
    private Image image;
    private int width;
    private int height;

    // animation variables
    private AnimationMode animationMode;
    private float xTile;  // starts at 0
    private int yTile;  // starts at 0
    private int[] xTilesPerRow;
    private float animationSpeed;
    private boolean playing;
    private boolean playingBackwards;

    public Sprite( String path, int width, int height )
    {
        this.image = new Image( path );
        this.width = width;
        this.height = height;

        setAnimationMode( AnimationMode.ONCE_ENDING_ON_FIRST );
        xTile = 0f;
        yTile = 0;
        xTilesPerRow = getXTilesPerRow();
        animationSpeed = 10;
        playingBackwards = false;
    }

    /**
     * gets the amount of tiles per row.
     *
     * for every row, starts checking at from right to left if that tile contains a colored pixel and if so, put that
     * tile its index in the tilesPerRow array.
     */
    private int[] getXTilesPerRow()
    {
        xTilesPerRow = new int[image.getHeight() / height];

        int totalTilesX = image.getWidth() / width;
        int totalTilesY = image.getHeight() / height;

        // loop trough every row (yTile) of the image
        for( int row = 0; row < totalTilesY; row++ )
        {
            int currentTileX = totalTilesX - 1;
            boolean containsPixel = false;

            // in every tile of the row, check if there exists a colored pixel in that tile
            while( !containsPixel )
            {
                containsPixel = tileContainsColoredPixel( currentTileX, row );

                if( containsPixel )
                    xTilesPerRow[ row ] = currentTileX + 1;
                else
                {
                    currentTileX --;

                    // if there exists no colored pixel in this row, put 0 in the array
                    if( currentTileX < 0 )
                    {
                        xTilesPerRow[ row ] = 0;
                        break;
                    }
                }
            }
        }

        return xTilesPerRow;
    }

    private boolean tileContainsColoredPixel( int xTile, int yTile )
    {
        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                int pixelPlace = xTile * width + x + ( yTile * height + y ) * image.getWidth();
                int pixelValue = image.getPixels()[ pixelPlace ];
                if( pixelValue != 0 )
                    return true;
            }
        }
        return false;
    }

    public void update()
    {
        if( playing )
        {
            if( !playingBackwards )
                updatePlayingForwards();
            else
                updatePlayingBackwards();
        }
    }

    private void updatePlayingForwards()
    {
        xTile += Engine.getDeltaTime() * animationSpeed;
        if( (int) xTile >= xTilesPerRow[ yTile ] )
        {
            switch( animationMode )
            {
                case ONCE_ENDING_ON_FIRST:
                    playing = false;
                    xTile = 0;
                    break;
                case ONCE_ENDING_ON_LAST:
                    playing = false;
                    xTile--;
                    break;
                case ONCE_BACK_AND_FORTH:
                    playBackwards();
                    break;
                case LOOPING:
                    xTile = 0;
                    break;
                case LOOPING_BACK_AND_FORTH:
                    playBackwards();
                    break;
            }
        }
    }

    private void updatePlayingBackwards()
    {
        xTile -= Engine.getDeltaTime() * animationSpeed;
        if( xTile < 0 )
        {
            playingBackwards = false;

            switch( animationMode )
            {
                case ONCE_BACK_AND_FORTH:
                    playing = false;
                    xTile = 0f;
                    break;
                case LOOPING_BACK_AND_FORTH:
                    if( xTilesPerRow[ yTile ] > 1 )
                        xTile = 1f;
                    break;

            }
        }
    }

    private void playBackwards()
    {
        playingBackwards = true;
        if( (int)xTile > 0 )
            xTile--;
    }

    public void draw( Renderer renderer )
    {
        int startX = (int)xTile * width;
        int startY = yTile * height;

        for( int x = 0; x < width; x++ )
        {
            for( int y = 0; y < height; y++ )
            {
                renderer.setPixel( x + image.getOffsetX(), y + image.getOffsetY(), image.getPixels()[ startX + x + (startY + y) * image.getWidth() ] );
            }
        }
    }

    public void play()
    {
        playing = true;
        reset();
    }

    public void stop()
    {
        playing = false;
    }

    public void reset()
    {
        xTile = 0f;
    }

    public void countXTilesPerRowWithEmptySpaces()
    {
        Arrays.fill( xTilesPerRow, image.getWidth() / width );
    }

    public void setOffsets( int x, int y )
    {
        image.setOffsets( x, y );
    }

    public void setAnimationMode( AnimationMode animationMode )
    {
        this.animationMode = animationMode;
        switch( animationMode )
        {
            case ONCE_ENDING_ON_FIRST:
                playing = false;
                break;
            case ONCE_ENDING_ON_LAST:
                playing = false;
                break;
            case ONCE_BACK_AND_FORTH:
                playing = false;
                break;
            case LOOPING:
                playing = true;
                break;
            case LOOPING_BACK_AND_FORTH:
                playing = true;
                break;
        }
    }

    public void setYTile( int yTile )
    {
        this.yTile = yTile;
    }

    public void blur( int blurPercentage )
    {
        image.blur( blurPercentage );
    }
}
