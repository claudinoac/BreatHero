package IO;

import java.awt.event.KeyEvent;      //Importa as classes da API event (externa)
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener,Joystick      //A classe Keyboard, além de ser um Joystick, é também um KeyListener
{
	private char acao = 'n';          //atributo que define a ação que o personagem tomar (inicializada com a ação de "não fazer nada")
	private boolean isPaused = true;  //atributo para saber se o jogo está pausado ou não
	
	public void keyPressed(KeyEvent e)						  //Se uma tecla foi apertada
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !isPaused)  //Se o jogo não está pausado e "espaço" foi pressionado
			acao = 'p';  									  //define a ação para ser tomada como "pausar o jogo"
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE && isPaused)   //Se o jogo está pausado e "espaço" foi pressionado
			acao = 'c'; 									  //define a ação para ser tomada como "continuar o jogo"
		
		if(e.getKeyCode() == KeyEvent.VK_UP)           		  //Se a tecla pressionada foi "pra cima"       
			acao = 's';										  //Define a ação a ser tomada como "subir um pixel"
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)				  //Se a tecla pressionada foi "pra baixo"	
			acao = 'd';										  //Define a ação a ser tomada como "descer um pixel"
	}
	  
	public void keyReleased(KeyEvent e)                      //Se a tecla apertada foi solta
	{
		acao = 'n';										     //Define a ação a ser tomada como "não fazer nada"
	}

	public void keyTyped(KeyEvent e) 
	{
	
	}

	public char acaoCtrl()
	{
		return acao;										//Getter para ação a ser tomada
	}
	
	public void setPaused(boolean value)
	{
		isPaused = value;
	}
	
}
