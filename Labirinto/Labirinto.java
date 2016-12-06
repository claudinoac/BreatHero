package Labirinto;

import java.awt.Graphics2D;  //Importa a classe Graphics2D, da awt (externa)

import Personagens.BreatHero; //Importa a classe BreatHero de Personagens (interno)

public abstract class Labirinto  //Classe molde para fases do jogo
{
	private double periodo;
	
	public Labirinto(double periodo)  //Inicializa o labirinto com seu período fixo
	{
		this.periodo = periodo;
	}

	public double getPeriodo()  //Getter para o periodo
	{
		return periodo;
	}
	
	public abstract Graphics2D paintComponent(Graphics2D g2); //Metodo para pintar o labirinto em um gráfico recebido externamente
	  
	public abstract void geraLabirinto();                 //Construtor do labirinto
	
	public abstract void moveLabirinto(double offset);     //Move o labirinto através de um offset
	
	public abstract boolean interceptaLabirinto(BreatHero boneco,int x,int y); 	//retorna uma flag que informa se o personagem interceptou o labirinto
	
}