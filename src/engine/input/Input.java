package engine.input;

import engine.Engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
    private Engine engine;

    private final int numberOfKeys = 1024;
    private boolean[] keys = new boolean[numberOfKeys];
    private boolean[] keysLast = new boolean[numberOfKeys];

    private final int numberOfButtons = 5;
    private boolean[] buttons = new boolean[numberOfButtons];
    private boolean[] buttonsLast = new boolean[numberOfButtons];

    private int mouseX;
    private int mouseY;
    private int previousMouseX;
    private int previousMouseY;

    private int scroll;

    public Input( Engine engine )
    {
        this.engine = engine;

        mouseX = 0;
        mouseY = 0;
        previousMouseX = 0;
        previousMouseY = 0;
        scroll = 0;

        engine.getWindow().getCanvas().addKeyListener( this );
        engine.getWindow().getCanvas().addMouseListener( this );
        engine.getWindow().getCanvas().addMouseMotionListener( this );
        engine.getWindow().getCanvas().addMouseWheelListener( this );
    }

    public void update()
    {
        for( int i = 0; i < numberOfKeys; i++ )
        {
            keysLast[i] = keys[i];
        }

        for( int i = 0; i < numberOfButtons; i++ )
        {
            buttonsLast[i] = buttons[i];
        }

        previousMouseX = mouseX;
        previousMouseY = mouseY;

        scroll = 0;
    }

    public boolean isKey( int keyCode )
    {
        return keys[ keyCode ];
    }

    public boolean isKeyUp( int keyCode )
    {
        return !keys[ keyCode ] && keysLast[ keyCode ];
    }

    public boolean isKeyDown( int keyCode )
    {
        return keys[ keyCode ] && !keysLast[ keyCode ];
    }

    public boolean isButton( int buttonCode )
    {
        return buttons[ buttonCode ];
    }

    public boolean isButtonUp( int buttonCode )
    {
        return !buttons[ buttonCode ] && buttonsLast    [ buttonCode ];
    }

    public boolean isButtonDown( int buttonCode )
    {
        return buttons[ buttonCode ] && !buttonsLast[ buttonCode ];
    }

    @Override
    public void keyTyped( KeyEvent e )
    {

    }

    @Override
    public void keyPressed( KeyEvent e )
    {
        keys[ e.getKeyCode() ] = true;
    }

    @Override
    public void keyReleased( KeyEvent e )
    {
        keys[ e.getKeyCode() ] = false;
    }

    @Override
    public void mouseClicked( MouseEvent e )
    {

    }

    @Override
    public void mousePressed( MouseEvent e )
    {
        buttons[ e.getButton() ] = true;
    }

    @Override
    public void mouseReleased( MouseEvent e )
    {
        buttons[ e.getButton() ] = false;
    }

    @Override
    public void mouseEntered( MouseEvent e )
    {

    }

    @Override
    public void mouseExited( MouseEvent e )
    {

    }

    @Override
    public void mouseDragged( MouseEvent e )
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved( MouseEvent e )
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved( MouseWheelEvent e )
    {
        scroll = e.getWheelRotation();
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public int getMouseMovedX()
    {
        return mouseX - previousMouseX;
    }

    public int getMouseMovedY()
    {
        return mouseY - previousMouseY;
    }

    /**
     * returns positive if you scroll van top to bottom
     * return negative if you scroll van bottom to top
     */
    public int getScroll()
    {
        return scroll;
    }
}
