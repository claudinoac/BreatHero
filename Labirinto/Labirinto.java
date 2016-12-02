package Labirinto;

import java.awt.Graphics2D;

import Personagens.BreatHero;

public abstract class Labirinto  //Classe molde para fases do jogo
{
	private double periodo;
	
	public Labirinto(double periodo)
	{
		this.periodo = periodo;
	}

	public double getPeriodo()
	{
		return periodo;
	}
	
	public abstract Graphics2D paintComponent(Graphics2D g2); 
	  
	public abstract void geraLabirinto();                 //Construtor do labirinto
	
	public abstract void moveLabirinto(double offset);     //Move o labirinto através de um offset
	
	public abstract boolean interceptaLabirinto(BreatHero boneco,int x,int y); 	//retorna uma flag que informa se algo interceptou o labirinto
	
}