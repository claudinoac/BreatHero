package Labirinto;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import Personagens.BreatHero;


public class Fase1 extends Labirinto
{
	private float x0 = 0;
	private float x1 = 200;
	private float y0 = 175;
	private float y1 = 50;

	Line2D.Float[][] lines = new Line2D.Float[2][14];
	
	public Fase1(){}
	
	public Graphics2D paintComponent(Graphics2D g2) 
	{	
		for(int i=0; i<2; i++)	//desenha as linhas no grafico
		{
			for(int j=0;j<7; j+=2)
			{
				g2.draw(lines[i][2*j]);
				g2.draw(lines[i][2*j+1]);
			}
		}
		return g2;
	}
	    
	public void geraLabirinto()
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				lines[i][2*j] = new Line2D.Float(x0 + x1*(j), y1+(i+1)*y0, x0 + x1*(j+1),  y1+i*y0);     //cria as linhas com coef. angular > 0 
				lines[i][2*j+1] = new Line2D.Float(x0 + x1*(j+2), y1+(i+1)*y0, x0 + x1*(j+1),y1+ i*y0); //cria as linhas com coef. angular < 0 
			}
		}
	}
	
	public void moveLabirinto(float offset)
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				lines[i][2*j].setLine(x0 + x1*(j) - offset, y1+(i+1)*y0, x0 + x1*(j+1) - offset,  y1+i*y0);    //move as linhas com coef. angular > 0 
				lines[i][2*j+1].setLine(x0 + x1*(j+2) - offset, y1+(i+1)*y0, x0 + x1*(j+1) - offset,y1+ i*y0); //move as linhas com coef. angular < 0
			}
		}
	}
	
	public boolean interceptaLabirinto(BreatHero boneco,int x,int y)
	{
		Rectangle2D rect = new Rectangle2D.Float(x,y,boneco.getImagem().getWidth(),boneco.getImagem().getHeight());
		boolean interceptou = false;
		
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				interceptou |= lines[i][2*j].intersects(rect);
				interceptou |= lines[i][2*j+1].intersects(rect);
			}
		}
		
		return interceptou;
	}

	public float getX1() 
	{
		return x1;
	}
	
}
