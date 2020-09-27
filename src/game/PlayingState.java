package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.text.Font;
import engine.graphics.text.Text;
import engine.input.Input;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    public PlayingState( Engine engine )
    {
        this.engine = engine;

        input = engine.getInput();
        renderer = engine.getRenderer();

        Font roboto = new Font( "Roboto-Regular", 12f );
        Text text = new Text( "Welcome to my application!", roboto, 0xff_f3c802 );
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw()
    {

    }

}
