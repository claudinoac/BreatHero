package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MenuInicial extends JFrame 
{
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	
	private JPanel painel,painelBotao,titlePane;
	private JButton[] botoes;
	private JLabel title,subtitle;
	private String pressedBut;
	private boolean pressedFlag;
	
	public MenuInicial()
	{
		super("BreatHero Menu");            //Seta nome do frame
		this.setSize(WIDTH,HEIGHT);         // "   tamanho do frame
		this.setResizable(false);           //Seta que esse frame n�o ser� redimension�vel
		painel = criaPainelPrincipal();     //Cria painel com bot�es e t�tulos 
		addListeners();						//Adiciona Listeners
		
		this.add(painel);					//Adiciona o painel a este frame
		this.setVisible(true);	 			//Seta este frame como vis�vel
	}

	private void addListeners()   //Adiciona listeners ao frame e aos bot�es
	{
		botoes[0].addActionListener(new ActionListener()  //Adiciona listener ao bot�o "novo jogo"
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "novo";                      //Passa a mensagem de qual bot�o foi pressionado para uma vari�vel que ser� lida externamente atrav�s de seu getter
				pressedFlag = true;						  //Seta flag de pressionamento de bot�o para uma vari�vel que ser� lida externamente atrav�s de seu getter
			}
		
		});
		
		botoes[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "opcoes";
				pressedFlag = true;				
			}
		
		});
	
		botoes[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "continua";
				pressedFlag = true;
			}
		});
		
		botoes[3].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "recordes";
				pressedFlag = true;		
			}
		});
		
		botoes[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "sair";
				pressedFlag = true;
			}
		});
	}
	
	public boolean isPressedFlag() //getter para a flag de pressionamento de bot�o
	{
		return pressedFlag;
	}

	public String getPressedBut() //getter para a variavel de identifica��o de bot�o pressionado
	{
		return pressedBut;
	}

	public JPanel criaPainelPrincipal()
	{
		GridBagLayout layout = new GridBagLayout();              //Cria��o de um layout do tipo GridBag
		GridBagConstraints g = new GridBagConstraints();         //Cria��o de diretivas para um layoutdo tipo GridBag
		
		titlePane = new JPanel();
		subtitle = new JLabel("O Jogo da Respira��o");
		title = new JLabel("�������BreatHero");
		botoes = new JButton[5];                   //Cria��o dos bot�es do menu
		botoes[0] = new JButton("Novo Jogo");
		botoes[1] = new JButton("Continuar Jogo");
		botoes[2] = new JButton("Op��es");
		botoes[3] = new JButton("Recordes");
		botoes[4] = new JButton("Sair");
		painel = new JPanel();                     //Cria��o do painel principal do menu
		painelBotao = new JPanel();                //Cria��o do painel que aloca os bot�es
		
		painel.setLayout(layout);
		painel.setSize(this.getSize());
		
		title.setFont(new Font("Dialog", Font.PLAIN, 30));         //Seta fonte do titulo do jogo
		subtitle.setFont(new Font("Dialog",Font.PLAIN,25));        //Seta a fonte do subtitulo do jogo
		
		painelBotao.setLayout(new GridLayout(5,1,0,25));           //Seta um layout do tipo Grid para o painel que aloca os bot�es, tendo esse 1 linha, 5 colunas espa�adas de 30pixels entre si
		
		for(int i=0;i<5;i++)									   //Adiciona os bot�es ao painel que os aloca
			painelBotao.add(botoes[i]);
		
		titlePane.setLayout(new GridLayout(2,3,0,10));             //Seta um layout do tipo Grid para o painel que aloca o titulo e o subtitulo, tendo esse 1 linha, 2 colunas espa�adas de 10pixels entre si
		titlePane.add(title);									   //Adiciona o titulo ao painel que os aloca
		titlePane.add(subtitle);								   // "       " subtitulo  "   "  "   "   "
		
		g.anchor = GridBagConstraints.NORTH;					   //Configura localiza��o de um componente que ser� alocado em um painel com GridBag, neste caso ancorado ao norte do layout
		g.insets = new Insets(0,0,60,0);						   //Configura espa�amento entre os setores norte e centro do GridBag
		painel.add(titlePane,g);                                   //Adiciona o painel de t�tulo ao painel principal com as configura��es definidas por g
		
		g.anchor = GridBagConstraints.CENTER;                      //Configura��es de g reformuladas para adi��o de outro componente ao painel com GridBag
		g.gridy = 1;											   //????
		painel.add(painelBotao,g);								   //Adiciona o painel de bot�es ao painel principal com as configura��es definidas por g
		
		return painel;											   //Retorna o painel com boto�es e titulos
	}
}