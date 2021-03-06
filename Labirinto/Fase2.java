package Labirinto;    //Declara pacote ao qual pertence

import java.awt.Graphics2D;  //Importa classes da API awt (externas)
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import Personagens.BreatHero; //Importa a classe BreatHero, que est� no pacote Personagens (interno)

public class Fase2 extends Labirinto 
{		
	private static int x0 = 0; //Coordenadas da primeira linha
	private static int x1 = 300;
	private static int y0 = 50;
	private static int y1 = 170;
	
	private Line2D[][] lines = new Line2D.Double[2][8];
	
	public Fase2()
	{
		super(4*x1);
	}
	
	public Graphics2D paintComponent(Graphics2D g2)  //Pinta as linhas em um gráfico recebido externamente
	{	
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<8; j++)
			{
				g2.draw(lines[i][j]); //Desenha linha por linha
			}
		}
		return g2;
	}

	@Override
	public void geraLabirinto() //Construtor do labirinto
	{
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<2; j++)
			{
				lines[i][4*j] = new Line2D.Double(x0+x1*(4*j),y0+y1*i,x0+x1*(4*j+1),y0+(i+1)*y1);
				lines[i][4*j+1] = new Line2D.Double(x0+x1*(4*j+1),y0+y1*(i+1),x0+x1*(4*j+2),y0+y1*(i+1));
				lines[i][4*j+2] = new Line2D.Double(x0+x1*(4*j+2),y0+y1*i,x0+x1*(4*j+3),y0+y1*(i+1));
				lines[i][4*j+3] = new Line2D.Double(x0+x1*(4*j+3),y0+y1*i,x0+x1*(4*j+4),y0+y1*i);
			}
		}
	}

	@Override
	public void moveLabirinto(double offset) //Move todo o labirinto por um offset
	{
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<2; j++)
			{
				lines[i][4*j].setLine(x0+x1*(4*j)-offset,y0+y1*i,x0+x1*(4*j+1)-offset,y0+(i+1)*y1);
				lines[i][4*j+1].setLine(x0+x1*(4*j+1)-offset,y0+y1*(i+1),x0+x1*(4*j+2)-offset,y0+y1*(i+1));
				lines[i][4*j+2].setLine(x0+x1*(4*j+2)-offset,y0+y1*(i+1),x0+x1*(4*j+3)-offset,y0+y1*i);
				lines[i][4*j+3].setLine(x0+x1*(4*j+3)-offset,y0+y1*i,x0+x1*(4*j+4)-offset,y0+y1*i);
			}
			
		}
		
	}

	@Override
	public boolean interceptaLabirinto(BreatHero boneco, int x, int y) //retorna uma flag avisando se o personagem intercepta alguma das retas
	{
		boolean interceptou = false;
		Rectangle2D rect = new Rectangle2D.Double(x,y,boneco.getImagem().getWidth(),boneco.getImagem().getHeight());
		
		
		for(int i=0; i<2; i++)	
		{
			for(int j=0; j<8; j++)
			{
				interceptou |= lines[i][j].intersects(rect);
			}
		}
		
		return interceptou;
	}
}
