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
	private long pontuacao=0,scoreInicial = 0;
	private int x = 0,y = 0,max = 8, speed = 12;
	private int x0=0,y0=0,fase=1;
	private boolean isPaused = false;
	
	public GameEngine(Labirinto lab, double x_position, double y_position, int speed,long scoreInicial,int fase)
	{
		this.scoreInicial = scoreInicial;
		this.speed = speed;
		this.fase = fase;
		tim = new Timer();
		this.lab = lab;
		lab.geraLabirinto();
		lab.moveLabirinto(x_position);
		this.max = speed;
		tim.scheduleAtFixedRate(new TimerTask() 
		{	
			
			public void run() 
			{
				if(!isPaused)
				{
					if(x<300)
					{
						GameEngine.this.lab.moveLabirinto(x);
						x++;
					}
					else
						x=0;
					
					GameEngine.this.repaint();
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
    	x0 = x;
    	y0 = y;
    	
	}
    
    public long getPontuacao() 
    {
		return scoreInicial+pontuacao/speed;
	}
    
    public void setPontuacao(long pontuacao) 
    {
		this.pontuacao = pontuacao;
	}

	public void setPaused(boolean isPaused)
    {
		this.isPaused = isPaused;
	}
    
    public int getX0() 
    {
		return x0;
	}
    
    public int getY0() 
    {
		return y0;
	}
    
    public int getSpeed() 
    {
		return speed;
	}

	public int getFase() 
	{
		return fase;
	}
}
