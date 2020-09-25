package engine;

import engine.gameState.GameStateManager;
import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.input.Input;

public class Engine implements Runnable
{
    private Thread thread;

    private boolean running = false;
    private final static int updatesPerSecond = 60;
    private final static double deltaTime = 1.0  / (double)updatesPerSecond;

    private Window window;
    private final int windowWidth = 1000;
    private final int windowHeight = 600;
    private final String windowTitle = "Twingine 0.1";

    private Renderer renderer;
    private Input input;
    private GameStateManager gameState;

    public Engine()
    {
        window = new Window( windowWidth, windowHeight, windowTitle );
        renderer = new Renderer( this );
        input = new Input( this );
    }

    public void setGameState( GameStateManager gameState )
    {
        this.gameState = gameState;
    }

    public void start()
    {
        thread = new Thread( this );
        thread.start();
    }

    public void run()
    {
        running = true;

        boolean render;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1_000_000_000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0.0;
        int frames = 0;
        int fps = 0;

        while( running )
        {
            render = false; // change to true to uncap frame rate

            firstTime = System.nanoTime() / 1_000_000_000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while( unprocessedTime >= deltaTime )
            {
                unprocessedTime -= deltaTime;
                render = true;

                gameState.update();

                input.update();

                if( frameTime >= 1.0 )
                {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    window.setTitle( windowTitle + " (fps: " + Integer.toString( fps ) + ")" );
                }

            }

            if( render )
            {
                renderer.clear();
                gameState.draw();
                window.update();
                frames++;
            }
            else
            {
                try
                {
                    Thread.sleep( 1 );
                }
                catch( InterruptedException e )
                {
                    e.printStackTrace( );
                }
            }
        }
    }

    public static double getDeltaTime()
    {
        return deltaTime;
    }

    public Window getWindow()
    {
        return window;
    }

    public Input getInput()
    {
        return input;
    }

    public Renderer getRenderer()
    {
        return renderer;
    }
}
