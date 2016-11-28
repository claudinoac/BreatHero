package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import Personagens.BreatHero;
import Labirinto.Fase1;
import Labirinto.Fase2;
import Labirinto.Labirinto;

@SuppressWarnings("serial")
public class GameEngine extends JPanel
{
	private Labirinto lab;
	private BreatHero boneco;
	private Timer timerFrame , timerLabirinto;
	private long pontuacao=0,scoreInicial = 0,periodoBreatHero = 5,periodoLabirinto = 5;
	private double x = 0,y = 0;
	private boolean isPaused = false;
	private HashMap<Integer,Boolean> keyPool;
	private String vidas = "■■■■■■■■■■";
	private int intersects=0;
	
	
	public GameEngine(int fase, double x0, double y0, long periodoLabirinto,long scoreInicial)
	{
		timerFrame = new Timer();
		timerLabirinto = new Timer();
		this.keyPool = new HashMap<Integer,Boolean>();
		this.scoreInicial = scoreInicial;
		this.periodoLabirinto = periodoLabirinto;
		this.boneco = new BreatHero();
		this.y = y0;
		this.x = x0;
		
		switch(fase)
		{
			case 1:
				this.lab = new Fase1();
			break;
			
			case 2:
				this.lab = new Fase2();
			break;
			
			default:
				System.out.println("Err0r: Fase Inexistente!");
				System.exit(0);
			break;
				
		}
		isPaused= true;
		lab.geraLabirinto();
		lab.moveLabirinto(x);
		boneco.geraBreatHero();
		boneco.moveBreatHero(y);
		GameEngine.this.repaint();
		
		timerFrame.scheduleAtFixedRate(new TimerTask() 
		{	
			public void run() 
			{
				if(!isPaused)
				{
					boneco.moveBreatHero(y);
					if(lab.interceptaLabirinto(boneco,boneco.getX0(),(int)y))
					{
						System.out.println("Interceptou: "+x+"," +y);
						intersects++;
						if(intersects==90)
						{
							perdeVida();
							intersects = 0;
						}
					}
					
					if(keyPool.get(KeyEvent.VK_UP) != null && y>0)
					{
						y--;
					}
					
					if(keyPool.get(KeyEvent.VK_DOWN) != null && y<getSize().height-boneco.getImagem().getHeight())
					{
						y++;
					}
					GameEngine.this.repaint();
				}
			}
		},0,periodoBreatHero);
		
		timerLabirinto.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				if(!isPaused)
				{
					if(x<lab.getPeriodo())
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
		}, 0, periodoLabirinto);
	}
	
    public void paintComponent(Graphics g)
   	{	
    	Graphics2D g2 = (Graphics2D)g;
    	g2.clearRect(0, 0, this.getSize().width, this.getSize().height);
    	g2=lab.paintComponent(g2);
    	g2=boneco.paintComponent(g2);	
    	
	}
    
    public void ganhaVida()
    {
    	char[] aux = new char[11];
    	vidas.getChars(0, 9, aux, 0);
    	System.out.println(aux);
    	int i=0;
    	while(aux[i] != ' ' && i < 9)
    		i++;
    	aux[i] = '■';
    	vidas = aux.toString();
    }

    public void perdeVida()
    {
    	char[] aux = new char[11];
    	aux = vidas.toCharArray();
    	System.out.println(aux);
    	int i=0;
    	while(aux[i] != ' ' && i < vidas.length()-1)
    		i++;
    	if(aux[i] != ' ')
    		aux[i] = ' ';
    	else
    		aux[i-1] = ' ';
    	vidas = aux.toString();
    	
    }
    
    public void movePersonagem(int offset)
    {
    	boneco.moveBreatHero(offset);
    }
    
    public long getPontuacao() 
    {
		return scoreInicial+pontuacao/periodoLabirinto;
	}
    
	public void setPaused(boolean isPaused)
    {
		this.isPaused = isPaused;
	}
    
    public boolean isPaused() 
    {
		return isPaused;
	}

	public double getX0() 
    {
		return x;
	}
    
    public double getY0() 
    {
		return y;
	}
    
    public long getPeriodoLabirinto() 
    {
		return periodoLabirinto;
	}
	
	public String getVidas() 
	{
		return vidas;
	}

	public void setKeyPool(HashMap<Integer, Boolean> keyPool) 
	{
		this.keyPool = keyPool;
	}
}
