package Main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Game.JanelaDeJogo;
import Game.MenuOpcoes;
import IO.ManipulaDados;

public class Main 
{	
	public static void main(String[] args)
	{
		int velocidadeInicial = 1;
		int fase = 1;
		String joystick = "teclado";
		String modo = "desafio";
		
		MenuInicial menu;
		JanelaDeJogo frame;
		ManipulaDados dado;
		menu = new  MenuInicial();
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true)
		{
			while(!menu.isPressedFlag()){}
			String option = menu.getPressedBut();
			switch(option)
			{
				case "novo":
					menu.dispose();
					float x0 = 0;
					switch(fase)
					{
						case 1:
							x0 = 200;
						break;
						
						case 2:
							x0 = 160;
						break;
					}
					frame = new JanelaDeJogo(0,x0,velocidadeInicial,0,fase,joystick,modo);
				break;
			
				case "continua":
					menu.dispose();
					dado = new ManipulaDados();
					dado.carregaJogo();
					frame = new JanelaDeJogo(dado.getX0(),dado.getY0(),dado.getVelocidade(),dado.getScoreInicial(),dado.getFase(),dado.getJoystick(),dado.getMode());
					frame.setVisible(true);
				break;
	
				case "opcoes":
					menu.setVisible(false);
					MenuOpcoes menuOp = new MenuOpcoes();
					JOptionPane.showMessageDialog(null,menuOp, "Opções de Jogo", JOptionPane.PLAIN_MESSAGE);
					velocidadeInicial = menuOp.getVelocidadeInicial();
					fase = menuOp.getFase();
					System.out.println(velocidadeInicial);
					joystick = menuOp.getJoystick();
					modo = menuOp.getMode();
					menu.setVisible(true);
				break;
			
				case "recordes":
					menu.setVisible(false);
					JPanel painel = new JPanel();
					GridBagLayout layout = new GridBagLayout();
					GridBagConstraints g = new GridBagConstraints();
					painel.setLayout(layout);
					dado = new ManipulaDados();
					dado.carregaRecordes();
					JLabel[] labelNome = new JLabel[5];
					JTextField[] labelValores = new JTextField[5];
					long[] valores = dado.getRecordeJogadores();
					String[] nomes = dado.getNomeJogadores();
					for(int i=0; i<5;i++)
					{
						g.gridy = i;
						g.gridx = 0;
						g.anchor = GridBagConstraints.EAST;
						labelNome[i] = new JLabel(nomes[i]);
						painel.add(labelNome[i],g);
						g.anchor = GridBagConstraints.WEST;
						g.gridx = 1;
						labelValores[i] = new JTextField(String.valueOf((int)valores[i]));
						labelValores[i].setEditable(false);
						painel.add(labelValores[i],g);
					}				
					
					JOptionPane.showMessageDialog(null,painel,"Recordes",JOptionPane.PLAIN_MESSAGE);
					menu.setVisible(true);
				break;
					
				case "sair":
					System.exit(0);
				break;
				
				default:
					break;
			}
			option = null;
			menu.setPressedFlag(false);
		}
	}  
}	