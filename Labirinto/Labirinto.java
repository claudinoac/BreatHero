package Labirinto;

import java.awt.Graphics2D;
import Personagens.BreatHero;

public abstract class Labirinto extends Object  //Classe molde para fases do jogo
{
	public Labirinto(){}    //Construtor vazio
	
	public abstract Graphics2D paintComponent(Graphics2D g2); 
	  
	public abstract void geraLabirinto();                 //Construtor do labirinto
	
	public abstract void moveLabirinto(float offset);     //Move o labirinto através de um offset
	
	public abstract boolean interceptaLabirinto(BreatHero boneco,int x,int y); //retorna uma flag que informa se algo interceptou o labirinto
	
	public abstract float getX1();
}