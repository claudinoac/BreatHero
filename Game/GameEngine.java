package Game;  //Declara pacote ao qual pertence

import java.awt.Graphics;          //Importando classes de pacotes externos
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import Personagens.BreatHero;		//Importando classes de pacotes internos
import IO.Joystick;
import IO.Keyboard;
import Labirinto.Fase1;
import Labirinto.Fase2;
import Labirinto.Labirinto;

@SuppressWarnings("serial")
public class GameEngine extends JPanel
{
	private Labirinto lab;       //Entidades-base do jogo
	private BreatHero boneco;
	private Joystick joy;
	
	private Timer timerFrame;
	private Timer timerLabirinto;    //Timers
	
	private boolean isPaused;
	private double x = 0,y = 0;
	private long pontuacao=0;			//Atributos de condições iniciais
	private long scoreInicial = 0;
	private long periodoLabirintoCount = 0; 
	private int intersects=0;
	private int velocidade = 1;
	private int fase = 1;
	private int vidas = 10; //variável vidas é necessária além de vidasVisual para teste de game-over chamado em JanelaDeJogo
	private String vidasVisual = "■■■■■■■■■■";
	private String velocidadeVisual = "■■■■■";
	private String joystick = "teclado";
	private String modo = "desafio";
	
	
	public GameEngine(int fase, double x0, double y0, int velocidade,long scoreInicial,String joystick,String modo)
	{
		long periodoBreatHero = 5;          //Variáveis que definem a velocidade de execução do jogo
		long periodoLabirinto = 5;
		long periodoGanhaVida = 1000*periodoLabirinto;
		
		this.timerFrame = new Timer();      //Instancia timers
		this.timerLabirinto = new Timer();
		
		this.scoreInicial = scoreInicial;   //Inicializa condições iniciais
		this.velocidade = velocidade;
		this.fase = fase;
		this.boneco = new BreatHero();
		this.y = y0;
		this.x = x0;
		this.joystick = joystick;
		this.modo = modo;
		
		if(joystick.equals("teclado"))  //Define o tipo de teclado que será usado de acordo com a mensagem recebida do invocador
		{
			joy = new Keyboard();
		}
		
		switch(fase)					//Define a fase que será instanciada com base na mensagem recebida do invocador
		{
			case 1:
				this.lab = new Fase1();
				
				switch(velocidade)        //Como o período do labirinto varia para cada caso, as velocidades são consequentemente diferentes, necessitando tratamento individual para cada caso
				{	case 5:
						this.velocidadeVisual = "■■■■■";
						periodoLabirinto = 5;
					break;
					
					case 4:
						this.velocidadeVisual = "■■■■ ";
						periodoLabirinto = 6;
					break;
					
					case 3:
						this.velocidadeVisual = "■■■  ";
						periodoLabirinto = 7;
					break;
					
					case 2:
						this.velocidadeVisual = "■■   ";
						periodoLabirinto = 8;
					break;
					
					case 1:
						this.velocidadeVisual = "■    ";
						periodoLabirinto = 9;
					break;
					
					default:
						System.out.println("Err0r: Velocidade inválida! Setando velocidade para 1. . ."); //Caso houver um valor inválido de velocidade
						this.velocidadeVisual = "■    ";    //Seta velocidade padrão como 1
						this.velocidade = 1;
						periodoLabirinto = 9;
					break;
				}
			break;
			
			case 2:
				this.lab = new Fase2();
				
				switch(velocidade)
				{	
					case 5:
						this.velocidadeVisual = "■■■■■";
						periodoLabirinto = 2;
					break;
					
					case 4:
						this.velocidadeVisual = "■■■■ ";
						periodoLabirinto = 3;
					break;
					
					case 3:
						this.velocidadeVisual = "■■■  ";
						periodoLabirinto = 4;
					break;
					
					case 2:
						this.velocidadeVisual = "■■   ";
						periodoLabirinto = 5;
					break;
					
					case 1:
						this.velocidadeVisual = "■    ";
						periodoLabirinto = 6;
					break;
					
					default:
						System.out.println("Err0r: Velocidade inválida! Setando velocidade para 1. . ."); //No caso de haver um valor inválido de velocidade
						this.velocidadeVisual = "■    ";     //Seta velocidade padrão como 1
						this.velocidade = 1;
						periodoLabirinto = 6;
					break;
				}
			break;
			
			default:												//Caso não tenha sido escolhida uma fase válida
				System.out.println("Err0r: Fase inexistente!");   	//Mensagem de erro
				System.exit(0);									  	//O programa finaliza
			break;
				
		}
		periodoGanhaVida = (int)periodoLabirinto*1000;   		//Define o período de bonus (ganha uma vida a cada 1000*labirinto ms
		
		isPaused= true;  											//Inicializa o jogo pausado
		lab.geraLabirinto();										//Cria o labirinto
		lab.moveLabirinto(x);
		boneco.geraBreatHero();										//Cria o personagem
		boneco.moveBreatHero(y);
		GameEngine.this.repaint();									//Redesenha a tela				
		isPaused = true;											//Prossegue o jogo
		inicializaTimers(periodoBreatHero,periodoLabirinto,periodoGanhaVida);											//Inicializa os timers que controlam a dinâmica de jogo
	}
	
	public void inicializaTimers(long periodoBreatHero,long periodoLabirinto, long periodoGanhaVida)
	{
		timerFrame.scheduleAtFixedRate(new TimerTask()      //Timer do frame: responsável por mover o personagem na tela, testar as interceptações do mesmo no labirinto, e tomar ações com base nas mensagens recebidas do joystick
		{	
			public void run() 
			{
				if(joy.acaoCtrl() == 'p')   //Verifica mensagens do joystick independente de estar pausado ou não o jogo
					setPaused(true);
				
				if(joy.acaoCtrl() == 'c')
					setPaused(false);
				
				if(!isPaused)  //Se o jogo não estiver pausado
				{
					boneco.moveBreatHero(y);      //Move o personagem para a posição y 
					if(lab.interceptaLabirinto(boneco,boneco.getX0(),(int)y))	//Verifica se o personagem intercepta o labirinto
					{
						intersects++;			
						if(intersects==90)	//Define que uma vida é perdida a cada 90 interseções (iterações ocorrem em um curtíssimo espaço de tempo, identificando cerca de 90 interseções por segundo)
						{
							perdeVida();
							intersects = 0;
						}
					}
					
					if(joy.acaoCtrl() == 's' && y>0)    //Verifica mensagens do joystick aguardando comando de subida ou descida, limitando sempre nos extremos
					{
						y--;
					}
					
					if(joy.acaoCtrl() == 'd' && y<getSize().height-boneco.getImagem().getHeight())
					{
						y++;
					}
				}
				//GameEngine.this.repaint();
			}
		},0,periodoBreatHero);
		
		timerLabirinto.scheduleAtFixedRate(new TimerTask()     //Timer do labirinto: responsável por mover o labirinto, adicionar uma vida de tempos em tempos e atualizar a pontuação
		{
			public void run()
			{
				if(!isPaused)
				{
					if(x<lab.getPeriodo())     //Move o labirinto um período e retorna pro zero, dando a sensação de continuidade num loop infinito
					{
						lab.moveLabirinto(x);
						x++;
					}
					else	
						x=0;
					
					periodoLabirintoCount++;
					if(periodoLabirintoCount==periodoGanhaVida)       //Conta periodoLabirintoCount iterações até dar uma vida ao jogador
					{
						periodoLabirintoCount=0;
						ganhaVida();
					}
					if(modo.equals("desafio"))  //Se tiver no modo desafio, incrementa a pontuação
						pontuacao++;
				}
				GameEngine.this.repaint();  //Redesenha o frame
			}
		}, 0, periodoLabirinto);
	}
	
    public void paintComponent(Graphics g)  //Responsável por desenhar tudo no painel
   	{	
    	Graphics2D g2 = (Graphics2D)g;
    	g2.clearRect(0, 0, this.getSize().width, this.getSize().height);
    	g2=lab.paintComponent(g2); //envia o gráfico pro objeto labirinto e recebe o mesmo com o labirinto impresso
    	g2=boneco.paintComponent(g2); //envia o gráfico pro objeto breathero e recebe o mesmo com o personagem impresso	
    	
	}
    
    public void ganhaVida()       //Metodo que usa casts entre char e String para aumentar uma vida no seu representante visual
    {
    	char[] aux = new char[11];
    	vidasVisual.getChars(0,vidasVisual.length()-1,aux,0); //Separa a string num array de char
    	int i=0;
    	while(aux[i] != ' ' && i < vidasVisual.length()-1)    //Procura a primeira casa sem vida
    		i++;
    	aux[i] = '■';										  //Adiciona um bloco de vida na primeira casa sem vida
    	vidasVisual = new String(aux);						  //Retorna a nova string para vidasVisual
    	
    	if(vidas!=10)										  //adiciona uma vida no contador de vidas caso não haja 10 ainda (no visual isso já é previsto)
    		vidas++;
    }

    public void perdeVida()     //Faz praticamente a mesma coisa que ganhaVida, por�m substitui um '�?' por um ' ' no ultimo indice com vida
    {
    	char[] aux = new char[11];
    	vidasVisual.getChars(0,vidasVisual.length()-1,aux,0); //Faz praticamente a mesma coisa que ganhaVida, por�m substitui um '�?' por um ' '
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
    
    public int getVidas()       							//Getters e Setters
    {
		return vidas;
	}

	public void movePersonagem(int offset)
    {
    	boneco.moveBreatHero(offset);
    }
    
    public long getPontuacao() 
    {
		return scoreInicial+pontuacao*velocidade;
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
