package IO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;

public class Joystick implements KeyListener 
{
	HashMap<Integer,Boolean> keyPool;

	public Joystick(JFrame fr)
	{
		fr.addKeyListener(this);
		keyPool = new HashMap<Integer,Boolean>();
	}
	
	public void keyPressed(KeyEvent e) 
	{
		keyPool.put(e.getKeyCode(), true);
	}
	
	public String onUpdate()
	{
		String value;
		
		if(keyPool.get(KeyEvent.VK_UP) != null)
			value = "sobe";
		else if(keyPool.get(KeyEvent.VK_DOWN ) != null)
				value = "desce";
		else
			value = null;
		
		return value;
	}

	public void keyReleased(KeyEvent e) 
	{
		keyPool.remove(e.getKeyCode());	
	}

	public void keyTyped(KeyEvent arg0) {}
}
