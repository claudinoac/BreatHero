package Main;

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
				frame = new JanelaDeJogo(dado.getX0(),dado.getY0(),dado.getSpeed0(),dado.getScoreInicial(),dado.getFase());
				frame.setVisible(true);
			break;
	
			case "opcoes":
				
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
				JOptionPane.showMessageDialog(null,recordes,"Recordes",JOptionPane.INFORMATION_MESSAGE);
				menu.setVisible(true);				
				
				
			break;
			
			case "sair":
				System.exit(0);
			break;
			
		}
	}  
}	