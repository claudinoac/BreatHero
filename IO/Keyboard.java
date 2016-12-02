package IO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener,Joystick
{

	private char acao = 'n';
	private boolean isPaused = true;
	
	public void setPaused(boolean value)
	{
		isPaused = value;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !isPaused)
			acao = 'p';
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE && isPaused)
			acao = 'c';
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
			acao = 's';
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			acao = 'd';
	}
	  
	public void keyReleased(KeyEvent e)
	{
		acao = 'n';
	}

	public void keyTyped(KeyEvent e) 
	{
	
	}

	public char acaoCtrl()
	{
		return acao;
	}
	
}
