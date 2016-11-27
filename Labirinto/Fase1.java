package Labirinto;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import Personagens.BreatHero;


public class Fase1 extends Labirinto
{
	private static int x0 = 0;
	private static int x1 = 200;
	private static int y1 = 175;
	private static int y0 = 50;
	

	private Line2D.Double[][] lines = new Line2D.Double[2][8]; //Cria uma matriz de 16 retas
	
	public Fase1()
	{
		super(2*x1);
	}
	
	public Graphics2D paintComponent(Graphics2D g2) 
	{	
		for(int i=0; i<2; i++)	//desenha as linhas no grafico
		{
			for(int j=0;j<8; j++)
			{
				g2.draw(lines[i][j]);
			}
		}
		return g2;
	}
	    
	public void geraLabirinto()
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0; j<4; j++)
			{
				lines[i][2*j] = new Line2D.Double(x0 + x1*(2*j), y0+(i+1)*y1, x0 + x1*(2*j+1),  y0+i*y1);     //cria as linhas com coef. angular > 0 
				lines[i][2*j+1] = new Line2D.Double(x0 + x1*(2*j+2), y0+(i+1)*y1, x0 + x1*(2*j+1),y0+ i*y1); //cria as linhas com coef. angular < 0 
			}
		}
	}
	
	public void moveLabirinto(double offset)
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0; j<4; j++)
			{
				lines[i][2*j].setLine(x0 + x1*(2*j) - offset, y0+(i+1)*y1, x0 + x1*(2*j+1) - offset,  y0+i*y1);    //move as linhas com coef. angular > 0 
				lines[i][2*j+1].setLine(x0 + x1*(2*j+2) - offset, y0+(i+1)*y1, x0 + x1*(2*j+1) - offset,y0+ i*y1); //move as linhas com coef. angular < 0
			}
		}
	}
	
	public boolean interceptaLabirinto(BreatHero boneco,int x,int y)
	{
		Rectangle2D rect = new Rectangle2D.Double(x,y,boneco.getImagem().getWidth(),boneco.getImagem().getHeight());
		boolean interceptou = false;
		
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<8; j++)
			{
				interceptou |= lines[i][j].intersects(rect);
			}
		}
		return interceptou;
	}
}
