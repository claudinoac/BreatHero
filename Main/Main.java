package Main;      //Deifne o pacote ao qual pertence

import java.awt.GridBagConstraints; //Importa bibliotecas externas
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Game.JanelaDeJogo;          //Importa classes de outros pacotes
import Game.MenuOpcoes;
import IO.ManipulaDados;

public class Main 
{	
	public static void main(String[] args)
	{
		int velocidadeInicial = 1;  //Declara condições padrão de novo jogo
		int fase = 1;
		String joystick = "teclado";
		String modo = "desafio";
		
		MenuInicial menu;  //Declara variáveis dos tipos que montam o jogo
		JanelaDeJogo frame;
		ManipulaDados dado;
		
		menu = new  MenuInicial(); //Inicializa o menu inicial
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true)
		{
			while(!menu.isPressedFlag()){}   //Só prossegue o processamento se algum botão do menu for pressionado
			String option = menu.getPressedBut();  //Recupera o botão pressionado para saber que ação tomar
			switch(option)
			{
				case "novo":           //Inicia um novo jogo
					menu.dispose();    //Fecha a janela de menu
					float y0 = 0;
					switch(fase)  //Escolhe uma posição inicial para o personagem com base na fase escolhida
					{
						case 1:
							y0 = 200;
						break;
						
						case 2:
							y0 = 160;
						break;
					}
					frame = new JanelaDeJogo(0,y0,velocidadeInicial,0,fase,joystick,modo);  //Chama a janela de jogo
				break;
			
				case "continua":      //Abre a janela para escolha de um arquivo de continuação de jogo
					menu.dispose();   //Fecha a janela de menu
					dado = new ManipulaDados(); //Inicializa e carrega os dados do jogo salvo nas variáveis buffer de dado
					dado.carregaJogo();      
					frame = new JanelaDeJogo(dado.getX0(),dado.getY0(),dado.getVelocidade(),dado.getScoreInicial(),dado.getFase(),dado.getJoystick(),dado.getMode()); //Inicializa um jogo com os dados carregados no objeto 'dado'
					frame.setVisible(true);  //Seta visibilidade da tela de jogo
				break;
	
				case "opcoes":  //Permite escolha das possibilidades de se iniciar um novo jogo
					menu.setVisible(false); //Oculta a janela de menu
					MenuOpcoes menuOp = new MenuOpcoes();  //Inicializa menu de opções
					JOptionPane.showMessageDialog(null,menuOp, "Opções de Jogo", JOptionPane.PLAIN_MESSAGE); //Exibe menu de opções
					velocidadeInicial = menuOp.getVelocidadeInicial(); //Carrega os dados definidos no painel de opções
					fase = menuOp.getFase();
					System.out.println(velocidadeInicial);
					joystick = menuOp.getJoystick();
					modo = menuOp.getMode();
					menu.setVisible(true);   //Reabre menu inicial
				break;
			
				case "recordes":  //Exibe tela de recordes
					menu.setVisible(false); //Oculta menu inicial 
		
					JPanel painel = new JPanel();                    //Inicializa um painel grafico
					GridBagLayout layout = new GridBagLayout();      //Inicializa um layout
					GridBagConstraints g = new GridBagConstraints(); //Inicializa regras do layout criado
					painel.setLayout(layout);                        //Define esse layout para o painel
					dado = new ManipulaDados();                      //Inicializa e carrega os dados de recordes em buffer
					dado.carregaRecordes();
					JLabel[] labelNome = new JLabel[5];
					JTextField[] labelValores = new JTextField[5];
					long[] valores = dado.getRecordeJogadores();     //Armazena os dados de recorde em um buffer
					String[] nomes = dado.getNomeJogadores();		 //
					for(int i=0; i<5;i++)
					{
						g.gridy = i;                                 //Define uma linha para cada grupo Nome + Recorde
						g.gridx = 0;								 //Define os nomes na primeira coluna do GridBagLayout
						g.anchor = GridBagConstraints.EAST;          //Ancora os nomes no leste da célula do GridBagLayout que os mesmos pertencem 
						labelNome[i] = new JLabel(nomes[i]);         //Cria JLabels com os nomes 
						painel.add(labelNome[i],g);					 //Adiciona os JLabels com os nomes ao painel
						g.anchor = GridBagConstraints.WEST;          //Ancora os valores de recordes no oeste da células do GridBagLayout que os mesmos pertencem 
						g.gridx = 1;                                 //Define os valores na segunda coluna do GridBagLayout
						labelValores[i] = new JTextField(String.valueOf((int)valores[i]));  //Cria um JTextField com os valores de recorde
						labelValores[i].setEditable(false);          //Define os JTextFields criados como não-editáveis
						painel.add(labelValores[i],g);               //Adiciona os JTextFields ao painel
					}				
					
					JOptionPane.showMessageDialog(null,painel,"Recordes",JOptionPane.PLAIN_MESSAGE);  //Exibe o painel de opções
					menu.setVisible(true);  //Retorna para o menu inicial
				break;
					
				case "sair":
					System.exit(0);  //Termina o programa
				break;
				
				default:
					break;
			}
			option = null;               //Zera a variável que armazena o botão pressionado anteriormente no menu
			menu.setPressedFlag(false);  //Limpa a flag de pressionamento dos botões de menu
		}
	}  
}	