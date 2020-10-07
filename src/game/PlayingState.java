package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.color.ColorPalette;
import engine.graphics.text.Font;
import engine.graphics.text.Text;
import engine.input.Input;

import static java.awt.event.KeyEvent.*;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Font roboto;
    private Text text;

    public PlayingState( Engine engine )
    {
        this.engine = engine;

        input = engine.getInput();
        renderer = engine.getRenderer();

        roboto = new Font( "Roboto-Regular_15.png", 14f );
        Font robotoTTF = new Font( "Roboto-Regular.png", 18f );
        text = new Text( "Hallo, ik ben Luc Drenth. Ik ben 22 jaar oud en ik ben software developer.", robotoTTF, ColorPalette.getWhite() );
        text.setOffsets( 250, 250 );
        text.setString( "Welcome to Twingine. This is my software renderer which I am going to use to make a game. But first, I am going to make a photo editing program. I already programmed in a lot of, if not all, filters and effects for images (or actually for pixel[]'s)." );
        text.setParagraph( true );
        text.setParagraphWidth( 300 );
        text.setShowingSpeed( 3 );
        text.setAlphaPercentage( 50 );
    }

    @Override
    public void update()
    {
        text.setOffsets( input.getMouseX(), input.getMouseY() );

        if( input.isKeyDown( VK_SPACE ) )
        {
            text.setLettersToShow( 0 );
        }

        text.setAlphaPercentage( (int)( (float)input.getMouseX() / (float)engine.getWindow().getWidth() * 100 ) );
    }

    @Override
    public void draw()
    {
        text.draw( renderer );
    }

}
