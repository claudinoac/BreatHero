package Game;   //Define o pacote para o qual pertence

import java.awt.Dimension;            //Importa classes das APIs awt e swing
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MenuOpcoes extends JPanel
{
	
	private JComboBox<String> velBox;   //Declarando componentes gráficos que retornam os dados para inicialização do jogo
	private JRadioButton fase1;
	private JRadioButton fase2;
	private JRadioButton teclado;
	private JRadioButton respirometro;
	private JRadioButton desafio;
	private JRadioButton zen;
	
	public MenuOpcoes()
	{
		String[] velocidades = {"1","2","3","4","5"};           //Define as velocidades do jogo
		JLabel velLabel = new JLabel("Velocidade Inicial: ");   //Label para velocidade inicial
		this.velBox = new JComboBox<String>(velocidades);       //ComboBox para escolha de velocidade inicial
		
		JLabel faseLabel = new JLabel("Fase: ");                //Label para fase
		ButtonGroup fase = new ButtonGroup();                   //Inicializa um grupo de botões para escolha da fase
		this.fase1 = new JRadioButton("1");						//Inicializa os botões radiais para escolha de fase
		this.fase2 = new JRadioButton("2");
		fase.add(fase1);										//Adiciona os botões de fase ao grupo
		fase.add(fase2);
		
		JLabel joyLabel = new JLabel("Joystick: ");             //Label para joystick
		ButtonGroup joystick = new ButtonGroup();				//Inicializa um grupo de botões para escolha do joystick
		this.teclado = new JRadioButton("Teclado");				//Inicializa os botões radiais para escolha de joystick
		this.respirometro = new JRadioButton("Respir�metro");
		joystick.add(teclado);									//Adiciona os botões de joystick ao grupo
		joystick.add(respirometro);
		
		JLabel modoLabel = new JLabel("Modo: ");                //Label para modo
		ButtonGroup modo = new ButtonGroup();					//Inicializa um grupo de botões para escolha do modo
		this.desafio = new JRadioButton("Desafio");				//Inicializa os botões radiais para escolha de modo
		this.zen = new JRadioButton("Zen");
		modo.add(desafio);										//Adiciona os botões de modo ao grupo
		modo.add(zen);
		
		//Opções predefinidas no painel de opções para não retornar erro
		this.velBox.setSelectedItem("1");						//Define o valor inicial do JComboBox como "1"
		this.fase1.setSelected(true);							//Define a fase inicial como fase 1
		this.teclado.setSelected(true);							//Define o teclado como joystick padrão
		this.desafio.setSelected(true);							//Define o modo padrão como "desafio"
		
		this.velBox.setMaximumSize(new Dimension(2,2));         //Define o tamanho máximo do JComboBox
		
		GridBagLayout layout = new GridBagLayout();				//Cria um layout do tipo GridBag
		GridBagConstraints g = new GridBagConstraints();		//Cria gerenciador de diretivas do GridBag
		int[] paneColWidth = {21,10,10,10};						//Define as larguras das colunas das células do GridBag
		layout.columnWidths = paneColWidth;						
		this.setLayout(layout);									//Define o GridBag como layout padrão
		
		g.gridx = 0;											//Define o Label de velocidade na celula de linha 0 e coluna 0, ancorada á esquerda
		g.gridy = 0;	
		g.anchor = GridBagConstraints.EAST;
		this.add(velLabel,g);									//Inclui o label ao painel usando as regras de g
		
																//Define o JComboBox na celula de linha 0, coluna 1, ancorada à esquerda
		g.gridx = 1;
		this.add(velBox,g);
		
		g.gridx = 0;											//Define o label de fase na celula de linha 1, coluna 0, ancorada à esquerda
		g.gridy = 1;
		this.add(faseLabel,g);									//Inclui o label ao painel usando as regras de g
		
		g.anchor = GridBagConstraints.WEST;						//Define os botões fase1 e fase2 nas celulas de linha 0 e colunas 1 e 2, ancorados à direita
		g.gridx = 1;
		this.add(fase1,g);
		g.gridx = 2;
		this.add(fase2,g);
		
		g.anchor = GridBagConstraints.EAST;						//Define o label de Joystick na celula de linha 2, coluna 0, ancorado à esquerda
		g.gridx = 0;
		g.gridy = 2;
		this.add(joyLabel,g);
		
		g.anchor = GridBagConstraints.WEST;						//Define os botões de teclado e joystick nas celulas de linha 2, colunas 1 e 2, ancorados à direita
		g.gridx = 1;
		this.add(teclado,g);
		g.gridx = 2;
		this.add(respirometro,g);
	
		g.anchor = GridBagConstraints.EAST;                     //Define o label de modo na celula de linha 3, coluna 0, ancorado à esquerda
		g.gridx = 0;
		g.gridy = 3;
		this.add(modoLabel,g);
		
		g.anchor = GridBagConstraints.WEST;						//Define os botões de modos nas celulas de linha 3, colunas 1 e 2, ancorados à direita
		g.gridx = 1;
		this.add(desafio,g);
		g.gridx = 2;
		this.add(zen,g);	
		this.setVisible(true);  								//Define esse painel como visível						
	}
	
	public int getVelocidadeInicial()             				//Getters para obtenção das definições escolhidos pelo usuário
	{
		return Integer.parseInt((String)velBox.getSelectedItem());
	}

	public int getFase() 
	{
		int fase = 1;
		if(fase1.isSelected())
			fase = 1;
		if(fase2.isSelected())
			fase = 2;
		
		return fase;
	}
	
	public String getMode()
	{
		String mode = "desafio";
			if(desafio.isSelected())
				mode = "desafio";
			
			if(zen.isSelected())
				mode = "zen";
			
			return mode;
	}
	
	public String getJoystick()
	{
		String joy = "teclado";
		
		if(teclado.isSelected())
			joy = "teclado";
		
		if(respirometro.isSelected())
			joy = "respirometro";
		
		return joy;
	}
}
