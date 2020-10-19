package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.color.ColorPalette;
import engine.graphics.shapes.Circle;
import engine.graphics.shapes.Line;
import engine.input.Input;
import engine.twinUtils.Coordinate;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private Coordinate start;
    private Coordinate end;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();

        start = new Coordinate( engine.getWindow().getWidth() / 2, engine.getWindow().getHeight() / 2 );
        end = new Coordinate( 0, 0 );
    }

    @Override
    public void update()
    {
        end = new Coordinate( input.getMouseX(), input.getMouseY() );
    }

    @Override
    public void draw()
    {
        Line.draw( start, end, ColorPalette.getWhite(), renderer );
        if( input.getMouseX() > 0 )
            Circle.draw( input.getMouseX(), 0xff_f3c802, new Coordinate( 10, 10 ), renderer );
    }

}
