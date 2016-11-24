package Game;

import Personagens.BreatHero;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import Labirinto.Labirinto;

@SuppressWarnings("serial")
public class GameEngine extends JPanel
{
	private Labirinto lab;
	private BreatHero boneco;
	private Timer tim ;
	private long pontuacao=0,scoreInicial = 0;
	private int x = 0,y = 0,max = 8, speed = 12;
	private int fase = 1;
	private boolean isPaused = false;
	private String vidas;
	private HashMap<Integer,Boolean> keyPool;
	
	
	public GameEngine(Labirinto lab, float x0, float y0, int speed,long scoreInicial,int fase)
	{
		tim = new Timer();
		this.keyPool = new HashMap<Integer,Boolean>();
		this.scoreInicial = scoreInicial;
		this.speed = speed;
		this.fase = fase;
		this.lab = lab;
		this.boneco = new BreatHero();
		lab.geraLabirinto();
		lab.moveLabirinto(x0);
		boneco.geraBreatHero();
		boneco.moveBreatHero(75);
		this.max = speed;
		
		tim.scheduleAtFixedRate(new TimerTask() 
		{	
			public void run() 
			{
				
				if(!isPaused)
				{
					boneco.moveBreatHero(y);
					if(lab.interceptaLabirinto(boneco,boneco.getX0(),y))
						System.out.println("Interceptou: "+x+"," +y);
					
					if(keyPool.get(KeyEvent.VK_UP) != null && y>0)
					{
						y--;
					}
					
					if(keyPool.get(KeyEvent.VK_DOWN) != null && y<getSize().height-boneco.getImagem().getHeight())
					{
						y++;
					}
					
					if(x<lab.getX1()*2)
					{
						lab.moveLabirinto(x);
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
    	g2.clearRect(0, 0, this.getSize().width, this.getSize().height);
    	g2=lab.paintComponent(g2);
    	g2=boneco.paintComponent(g2);	
	}
    
    public void perdeVida()
    {
    	switch(vidas)
    	{
    		case "■■■■■":
    			vidas = "■■■■ ";
    		break;
    		
    		case "■■■■ ":
    			vidas = "■■■  ";
    		break;
    		
    		case "■■■  ":
    			vidas = "■■   ";
    		break;
    			
    		case "■■   ":
    			vidas = "■    ";
    		break;
    		
    		case "■    ":
    			vidas = "     ";
    		break;
    	}
    	
    }

    public void ganhaVida()
    {
    	
    }
    
    public void movePersonagem(int offset)
    {
    	boneco.moveBreatHero(offset);
    }
    
    public long getPontuacao() 
    {
		return scoreInicial+pontuacao/speed;
	}
    
	public void setPaused(boolean isPaused)
    {
		this.isPaused = isPaused;
	}
    
    public int getX0() 
    {
		return x;
	}
    
    public int getY0() 
    {
		return y;
	}
    
    public int getSpeed() 
    {
		return speed;
	}

	public int getFase() 
	{
		return fase;
	}
	
	public void setKeyPool(HashMap<Integer, Boolean> keyPool) 
	{
		this.keyPool = keyPool;
	}

	
}
