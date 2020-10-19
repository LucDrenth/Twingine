package game;

import engine.Engine;
import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.shapes.Line;
import engine.input.Input;
import engine.twinUtils.Coordinate;

public class PlayingState implements GameStateManager
{
    private Engine engine;
    private Input input;
    private Renderer renderer;

    private int angle;

    public PlayingState( Engine engine )
    {
        this.engine = engine;
        renderer = engine.getRenderer();
        input = engine.getInput();

    }

    @Override
    public void update()
    {
        angle += 2;
    }

    @Override
    public void draw()
    {
        Line.draw( new Coordinate( engine.getWindow().getWidth() / 2, engine.getWindow().getHeight() / 2 ), 100, angle, 0xff_f3c802, renderer );
    }

}
