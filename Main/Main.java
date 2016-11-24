package Main;

import javax.swing.JFrame;
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
			break;
			
			case "sair":
				System.exit(0);
			break;
			
		}
	}  
}	