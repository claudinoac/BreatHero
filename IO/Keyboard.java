package IO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener,Joystick      //A classe Keyboard, al�m de ser um Joystick, � tamb�m um KeyListener
{
	private char acao = 'n';          //atributo que define a a��o que o personagem tomar� (inicializada com a a��o de "n�o fazer nada")
	private boolean isPaused = true;  //atributo para saber se o jogo est� pausado ou n�o
	
	public void keyPressed(KeyEvent e)						  //Se uma tecla foi apertada
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !isPaused)  //Se o jogo n�o est� pausado e "espa�o" foi pressionado
			acao = 'p';  									  //define a a��o para ser tomada como "pausar o jogo"
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE && isPaused)   //Se o jogo est� pausado e "espa�o" foi pressionado
			acao = 'c'; 									  //define a a��o para ser tomada como "continuar o jogo"
		
		if(e.getKeyCode() == KeyEvent.VK_UP)           		  //Se a tecla pressionada foi "pra cima"       
			acao = 's';										  //Define a a��o a ser tomada como "subir um pixel"
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)				  //Se a tecla pressionada foi "pra baixo"	
			acao = 'd';										  //Define a a��o a ser tomada como "descer um pixel"
	}
	  
	public void keyReleased(KeyEvent e)                      //Se a tecla apertada foi solta
	{
		acao = 'n';										     //Define a a��o a ser tomada como "n�o fazer nada"
	}

	public void keyTyped(KeyEvent e) 
	{
	
	}

	public char acaoCtrl()
	{
		return acao;										//Getter para a��o a ser tomada
	}
	
	public void setPaused(boolean value)
	{
		isPaused = value;
	}
	
}
