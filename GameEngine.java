import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameEngine extends JPanel
{
	private Labirinto lab;
	private Timer tim ;
	private int i = 0,pontuacao=0;
	private long max = 8;
	private boolean isPaused = false;
	
	

	public GameEngine(Labirinto lab, double x_position, double y_position, long speed)
	{
		tim = new Timer();
		this.lab = lab;
		lab.geraLabirinto(x_position);
		this.max = speed;
		tim.scheduleAtFixedRate(new TimerTask() 
		{	
			
			public void run() 
			{
				if(!isPaused)
				{
					if(i<300)
					{
						GameEngine.this.lab.moveLabirinto(i);
						i++;
					}
					else
						i=0;
					
					GameEngine.this.repaint();
					GameEngine.this.revalidate();
					pontuacao++;
				}
			}
		},0,(long)max);
		
	}
	
    public void paintComponent(Graphics g)
   	{	
    	Graphics2D g2 = (Graphics2D)g;
    	g2.clearRect(0, 0, getParent().getSize().width, getParent().getSize().height);
    	g2=lab.paintComponent(g2);  
	}
    
    public int getPontuacao() 
    {
		return pontuacao;
	}
    
    public void setPaused(boolean isPaused)
    {
		this.isPaused = isPaused;
	}
}
