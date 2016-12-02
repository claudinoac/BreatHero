package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import Personagens.BreatHero;
import IO.Joystick;
import IO.Keyboard;
import Labirinto.Fase1;
import Labirinto.Fase2;
import Labirinto.Labirinto;

@SuppressWarnings("serial")
public class GameEngine extends JPanel
{
	private Labirinto lab;
	private BreatHero boneco;
	private Joystick joy;
	private Timer timerFrame , timerLabirinto;
	private long pontuacao=0,scoreInicial = 0,periodoBreatHero = 5,periodoLabirinto = 5,periodoLabirintoCount = 0;
	private double x = 0,y = 0;
	private boolean isPaused;
	private String vidasVisual = "■■■■■■■■■■";
	private String velocidadeVisual = "     ";
	private String joystick = "teclado";
	private String modo = "desafio";
	private int intersects=0,periodoGanhaVida = 10000,velocidade = 1,fase = 1,vidas =10;
	
	
	public GameEngine(int fase, double x0, double y0, int velocidade,long scoreInicial,String joystick,String modo)
	{
		this.timerFrame = new Timer();
		this.timerLabirinto = new Timer();
		this.scoreInicial = scoreInicial;
		this.velocidade = velocidade;
		this.fase = fase;
		this.boneco = new BreatHero();
		this.y = y0;
		this.x = x0;
		this.joystick = joystick;
		this.modo = modo;
		
		if(joystick.equals("teclado"))
		{
			joy = new Keyboard();
		}
		
		switch(fase)
		{
			case 1:
				this.lab = new Fase1();
				
				switch(velocidade)
				{	case 5:
						this.velocidadeVisual = "■■■■■";
						this.periodoLabirinto = 5;
					break;
					
					case 4:
						this.velocidadeVisual = "■■■■ ";
						this.periodoLabirinto = 6;
					break;
					
					case 3:
						this.velocidadeVisual = "■■■  ";
						this.periodoLabirinto = 7;
					break;
					
					case 2:
						this.velocidadeVisual = "■■   ";
						this.periodoLabirinto = 8;
					break;
					
					case 1:
						this.velocidadeVisual = "■    ";
						this.periodoLabirinto = 9;
					break;
					
					default:
						this.velocidadeVisual = "■    ";
						this.periodoLabirinto = 9;
						this.periodoLabirinto = 6;
				}
			break;
			
			case 2:
				this.lab = new Fase2();
				
				switch(velocidade)
				{	
					case 5:
						this.velocidadeVisual = "■■■■■";
						this.periodoLabirinto = 2;
					break;
					
					case 4:
						this.velocidadeVisual = "■■■■ ";
						this.periodoLabirinto = 3;
					break;
					
					case 3:
						this.velocidadeVisual = "■■■  ";
						this.periodoLabirinto = 4;
					break;
					
					case 2:
						this.velocidadeVisual = "■■   ";
						this.periodoLabirinto = 5;
					break;
					
					case 1:
						this.velocidadeVisual = "■    ";
						this.periodoLabirinto = 6;
					break;
					
					default:
						System.out.println("Err0r: Velocidade inválida! Setando velocidade para 1. . .");
						this.velocidadeVisual = "■    ";
						this.periodoLabirinto = 8;
					break;
				}
			break;
			
			default:
				System.out.println("Err0r: Fase inexistente!");
				System.exit(0);
			break;
				
		}
		this.periodoGanhaVida = (int)periodoLabirinto*1000;
		
		isPaused= true;
		lab.geraLabirinto();
		lab.moveLabirinto(x);
		boneco.geraBreatHero();
		boneco.moveBreatHero(y);
		GameEngine.this.repaint();
		
		if(joy.acaoCtrl() != 'n'){}
		isPaused = false;
		inicializaTimers();
	}
	
	public void inicializaTimers()
	{
		timerFrame.scheduleAtFixedRate(new TimerTask() 
		{	
			public void run() 
			{
				if(joy.acaoCtrl() == 'p')
					setPaused(true);
				
				if(joy.acaoCtrl() == 'c')
					setPaused(false);
				
				if(!isPaused)
				{
					boneco.moveBreatHero(y);
					if(lab.interceptaLabirinto(boneco,boneco.getX0(),(int)y))
					{
						intersects++;
						if(intersects==90)
						{
							perdeVida();
							intersects = 0;
						}
					}
					
					if(joy.acaoCtrl() == 's' && y>0)
					{
						y--;
					}
					
					if(joy.acaoCtrl() == 'd' && y<getSize().height-boneco.getImagem().getHeight())
					{
						y++;
					}
				}
				GameEngine.this.repaint();
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
					
					periodoLabirintoCount++;
					if(periodoLabirintoCount==periodoGanhaVida)
					{
						periodoLabirintoCount=0;
						ganhaVida();
					}
					if(modo.equals("desafio"))
						pontuacao++;
				}
				GameEngine.this.repaint();
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
    	vidasVisual.getChars(0,vidasVisual.length()-1,aux,0);
    	int i=0;
    	while(aux[i] != ' ' && i < vidasVisual.length()-1)
    		i++;
    	aux[i] = '■';
    	vidasVisual = new String(aux);
    	
    	if(vidas!=10)
    		vidas++;
    }

    public void perdeVida()
    {
    	char[] aux = new char[11];
    	vidasVisual.getChars(0,vidasVisual.length()-1,aux,0);
    	int i=0;
    	while(aux[i] != ' ' && i < vidasVisual.length()-1)
    		i++;
    	if(aux[i] != ' ')
    		aux[i] = ' ';
    	else if(i!=0)
    		aux[i-1] = ' ';
    	vidasVisual = new String(aux);
    	
    	if(vidas!=0)
    		vidas --;
    }
    
    public int getVidas() 
    {
		return vidas;
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
		joy.setPaused(isPaused);
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
    	
	public String getVidasVisual() 
	{
		return vidasVisual;
	}

	public String getVelocidadeVisual() 
	{
		return velocidadeVisual;
	}
	
	public int getVelocidade()
	{
		return velocidade;
	}

	public String getMode() 
	{
		return modo;
	}

	public String getJoyType() 
	{
		return joystick;
	}
	
	public int getFase() 
	{
		return fase;
	}

	public KeyListener getJoystick() 
	{
		return (KeyListener)joy;
	}
}
