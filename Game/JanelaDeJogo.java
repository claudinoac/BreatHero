package Game; //Define o pacote para o qual pertence

import java.awt.Color;                //Importando classes das APIs awt, I/O, Util e Swing (externas)
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

import IO.ManipulaDados;			//Importando classe ManipulaDados, pertencente ao pacote IO (interno)

@SuppressWarnings("serial")
public class JanelaDeJogo extends JFrame 
{
	private final int WIDTH = 700;    //Define dimensões da tela de jogo
	private final int HEIGHT = 480;
	
	private GameEngine g1;			//Inicialização de atributos dinamicamente modificáveis ao longo do jogo
	private JLabel pontuacao;		
	private JLabel livesLabel;
	private JLabel velLabel;
	private JMenuItem salvar;
	private JMenuItem carregar;
	private Timer atualizaBarra;

	      
	public JanelaDeJogo(double x0,double y0, int velocidade, long scoreInicial,int fase,String joystick,String modo) 
	{
		this.atualizaBarra = new Timer();     //Criação de um tipo timer para atualizar a barra de menus, que contém as vidas, pontuação e velocidade do jogo
		
		g1 = new GameEngine(fase,x0,y0,velocidade,scoreInicial, joystick,modo);  //Inicialização de um game engine
		montaTela();                                                             //Monta a tela de jogo
		addListeners();															 //Cria e adiciona os listeners de janela de jogo e dos botões da barra de menus
		g1.setPaused(true);														 //Inicializa o jogo pausado
		repaint();																 //Repinta o frame para evitar tela branca
		atualizaBarraTimer();													 //Inicialização e definição da rotina do timer
	}
	
	public void atualizaBarraTimer()
	{
		atualizaBarra.scheduleAtFixedRate(new TimerTask()                //Atualiza a pontuação, as vidas e lança o game over quando as vidas acabam
		{
			@Override
			public void run() 
			{
				if(!g1.isPaused())
				{
					pontuacao.setText("Pontuação: "+g1.getPontuacao());  //Atualiza a pontuação
					livesLabel.setText(g1.getVidasVisual());			 //Atualiza o nro de vidas
					if(g1.getVidas() == 0)								 //Se o nro de vidas for 0, encerra o jogo
					{
						pausaJogo();
						gameOver();
					}
				}
				
			}
		}, 0, 100);
	}
	
	public void addListeners()
	{
	   this.addKeyListener(g1.getJoystick());    //Adiciona Keyboard como listener de tecla padrão
		
	   this.addWindowListener(new WindowAdapter() 	//Ao tentar fechar a janela de jogo, pausa o jogo e sugere salvamento do mesmo
	   {
		   public void windowClosing(WindowEvent e) 
	       {
	       	pausaJogo();   //Pausa o jogo
	       	int option = JOptionPane.showConfirmDialog(null,"Salvar", "Deseja salvar o jogo?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);  //Lança um painel para saber se o usuário deseja gravar o jogo
	
	       	switch(option)  													 //Toma ações com base no feedback do usuário
	       	{
		       	case JOptionPane.NO_OPTION: 									//Caso ele não queira salvar o jogo
		       		System.exit(0);												//O programa encerra
		       	break;
		       	
		       	case JOptionPane.YES_OPTION: 									//Caso ele queira salvar
		       		ManipulaDados dado = new ManipulaDados(); 					//Inicializa o tipo ManipulaDados e tenta salvar o jogo com os dados atuais fornecidos pelo gameEngine
		       		try  														//Uma exceção pode ser lançada se o arquivo for aberto enquanto a gravação é feita nele
		       		{
		       			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getVelocidade(),g1.getPontuacao(),g1.getFase(),g1.getJoyType(),g1.getMode());
		       		}
		       		catch(IOException ex) 										//Caso essa exceção seja gerada
		       		{
		       			JOptionPane.showMessageDialog(null, "Erro","Erro ao Salvar arquivo",JOptionPane.ERROR_MESSAGE);  //Exibe uma mensagem de erro na tela
		       		}
		       	break;
		       		
		       	case JOptionPane.CANCEL_OPTION: 								//Caso ele queira voltar ao jogo
		       	break;
		          }
		       }
		   });
		   
	   salvar.addActionListener(new ActionListener()   //Efetua a ação de salvar o jogo caso o botão "salvar jogo" seja pressionado
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		pausaJogo(); 
	   		ManipulaDados dado = new ManipulaDados();
    		try
    		{
    			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getVelocidade(),g1.getPontuacao(),g1.getFase(),g1.getJoyType(),g1.getMode());
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null, "Erro","Erro ao Salvar arquivo",JOptionPane.ERROR_MESSAGE);
    		}
	   	}	
	   });
	   
	   carregar.addActionListener(new ActionListener()    //Carrega o jogo caso o botão "carregar jogo" tenha sido pressionado
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		pausaJogo();								
	   		ManipulaDados dado = new ManipulaDados();
	   		dado.carregaJogo();
	   		remove(g1);    //Encerra o gameEngine atual
	   		g1 = new GameEngine(dado.getFase(),dado.getX0(),dado.getY0(),dado.getVelocidade(),dado.getScoreInicial(),dado.getJoystick(),dado.getMode()); //Inicializa um novo GameEngine com os dados carregados
	   		add(g1);  //Readiciona o gameEngine ao painel
	   		repaint();	//Repinta a tela
	   		pausaJogo(); //Pausa o jogo
	   	}
	   });
	}  
	
	public void montaTela()  //Monta o primeiro frame
	{
		setTitle("BreatHero - Aperte \"espaço\" para iniciar"); //Define o titulo do primeiro frame
		setSize(WIDTH, HEIGHT);									//Define tamanho do frame de jogo
		setResizable(false);									//Define o frame como não-redimensionável
		setLocationRelativeTo(null);							//Inicializa  o frame no centro da tela
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	//Impede o frame de tomar ações caso o botão de sair seja pressionado, passando essa tarefa ao WindowListener, declarado anteriormente
		
		JMenuBar menuBar = new JMenuBar();                 		//Inicializa componentes gráficos da barra de menus 
		JMenu menu = new JMenu("Arquivo");
		salvar = new JMenuItem("Salvar Jogo");
		carregar = new JMenuItem("Carregar Jogo");
		JLabel nameVelLabel = new JLabel("Velocidade: ");
		velLabel = new JLabel(g1.getVelocidadeVisual());
		JLabel nameLiveLabel = new JLabel("Vidas: ");
		livesLabel = new JLabel(g1.getVidasVisual());
		pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
		
		GridBagLayout layout = new GridBagLayout();             //Cria um layout para a barra de menus
		GridBagConstraints g = new GridBagConstraints();		//Cria diretivas para o layout criado
		
		int[] menuBarColWidth = {130,10,130,10,170,150};		//Larguras das colunas do GridBagLayout
		
		
		
		livesLabel.setForeground(Color.BLUE);					//Define a cor do gráfico visual que representa o nro de vidas 
		layout.columnWidths = menuBarColWidth;					//Define as laguras das colunas do GridBagLayout
		menuBar.setLayout(layout);								//Define o GridBagLayout como padrão
		menu.add(salvar);										//Adiciona os botões salvar e carregar ao painel menu
		menu.add(carregar);
		
		g.fill = GridBagConstraints.RELATIVE;					//Define o alocamento do componente da célula como relativo ao seu tamanho
		g.gridx = 0;											//Define o menu arquivo na celula de indice zero, ancorada à direita
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(menu,g);
		
		
		g.gridx = 1;											//Define o label de velocidades e seta o mesmo na celula de indice 1, ancorada à esquerda
		g.anchor = GridBagConstraints.EAST;
		menuBar.add(nameVelLabel,g);
		
		g.gridx = 2;											//Define o gráfico visual da velocidade na celula de indice 2, ancorada à direita
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(velLabel,g);
		
		
		g.anchor = GridBagConstraints.EAST;						//Define o label de vidas e seta o mesmo na celula de indice 3, ancorada à esquerda
		g.gridx = 3;
		menuBar.add(nameLiveLabel,g);
		
		g.gridy = 0;									
		g.gridx = 4;											//Define o gráfico visual das vidas na celula de indice 4, ancorda à direita
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(livesLabel,g);
		
		g.anchor = GridBagConstraints.CENTER;					//Define a pontuação na celula de indice 5, ancorada ao centro da célula, distribuida horizontalmente o máximo possível
		g.fill = GridBagConstraints.HORIZONTAL;
	   	g.gridx = 5;
	   	menuBar.add(pontuacao,g);
	   
	   	setJMenuBar(menuBar);
	   	add(g1);
	   	setVisible(true);
	}

	public void pausaJogo()
	{
		setTitle("BreatHero - Pausado (Pressione \"espaço\" para continuar)");		//Mostra legenda na borda da janela
		g1.setPaused(true);
	}
	
	public void continuaJogo()
	{
		setTitle("BreatHero - Rodando (Pessione \"espaço\" para pausar)");			//Mostra legenda na borda da janela
		g1.setPaused(false);
	}
	
	
	public void gameOver()
	{
		try	//Uma exceção do tipo NullPointerException pode ser lançada se o jogador não quiser colocar o nome
		{
			String nomeJogador = null;
			JOptionPane.showMessageDialog(null, "Game Over","Você perdeu todas as vidas!",JOptionPane.WARNING_MESSAGE);
			nomeJogador = JOptionPane.showInputDialog(null,"Digite seu nome:","Game Over",JOptionPane.QUESTION_MESSAGE);
		
			if(!nomeJogador.endsWith(": "))
				nomeJogador+=": ";
		
			ManipulaDados dado = new ManipulaDados();   //Abre o manipulador de arquivos
			try //Pode ser lançada uma exceção do tipo IOException caso haja problemas no meio da gravação
			{
				dado.salvaRecordes(nomeJogador, g1.getPontuacao());		//Tenta salvar a pontuação do jogador caso ela seja maior que alguma do arquivo de recordes
			}
			catch(IOException e)  //Lança uma caixa de diálogo informando que houve erro na gravação do arquivo
			{
				JOptionPane.showMessageDialog(null,"Erro ao salvar arquivo de recordes", "Erro",JOptionPane.ERROR_MESSAGE);
			}
			System.exit(0);
		}
		catch(NullPointerException e) //Lança uma caixa de diálogo caso o jogador não queira salvar a sua pontuação
		{
			JOptionPane.showMessageDialog(null, "Erro! Você não quis colocar seu nome para os recordes :(","Erro!",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
	  
