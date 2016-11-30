package Main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.JanelaDeJogo;
import IO.ManipulaDados;

public class Main 
{	
	
	
	public static void main(String[] args)
	{
		MenuInicial menu;
		JanelaDeJogo frame;
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
					frame = new JanelaDeJogo();
				break;
			
				case "continua":
					menu.dispose();
					ManipulaDados dado = new ManipulaDados();
					dado.carregaJogo();
					frame = new JanelaDeJogo(dado.getX0(),dado.getY0(),dado.getPeriodoLabirinto(),dado.getScoreInicial(),dado.getFase());
					frame.setVisible(true);
				break;
	
				case "opcoes":
					menu.setVisible(false);
					JPanel painelOpcoes = new JPanel();
					JPanel painelLabels = new JPanel();
					JPanel painelCoisas = new JPanel();
					JLabel vel = new JLabel("Velocidade Inicial: ");
					JLabel fase = new JLabel("Fase: ");
					JLabel joy = new JLabel("Joystick: ");
					JLabel mode = new JLabel("Modo: ");
					GridBagConstraints g = new GridBagConstraints();
					painelLabels.setLayout(new GridBagLayout());
					int[] menuBarLinHeight = {3,3,3,3};
					painelLabels.add(vel);
					painelLabels.add(fase);
					painelLabels.add(joy);
					painelLabels.add(mode);
					painelOpcoes.add(painelLabels);
					
					JOptionPane.showMessageDialog(null,painelOpcoes, "Opções de Jogo", JOptionPane.PLAIN_MESSAGE);
					menu.setVisible(true);
					
				break;
			
				case "recordes":
					menu.setVisible(false);
					JPanel recordes = new JPanel();
					recordes.setLayout(new BoxLayout(recordes,BoxLayout.Y_AXIS));
					JLabel[] labels= new JLabel[4];
					for(int i=0; i<4; i++)
					{
						labels[i] = new JLabel(""+i);
						recordes.add(labels[i]);
					}
					JOptionPane.showMessageDialog(null,recordes,"Recordes",JOptionPane.PLAIN_MESSAGE);
					
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