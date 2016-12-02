package Labirinto;   //Declara pacote ao qual pertence

import java.awt.Graphics2D;    //Importa classes da API awt
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import Personagens.BreatHero; //Importa a classe BreatHero, que está no pacote Personagens


public class Fase1 extends Labirinto
{
	private static int x0 = 0;      //Coordenadas iniciais da primeira linha gerada no labirinto
	private static int x1 = 200;
	private static int y1 = 175;
	private static int y0 = 50;
	

	private Line2D.Double[][] lines = new Line2D.Double[2][8]; //Cria uma matriz de 16 retas
	
	public Fase1()  //Define o periodo do labirinto
	{
		super(2*x1);
	}
	
	public Graphics2D paintComponent(Graphics2D g2)  //Metodo para pintar o labirinto em um gráfico recebido externamente
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
	    
	public void geraLabirinto() //Gera o labirinto utilizando como base a primeira linha
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
	
	public void moveLabirinto(double offset)  //Move todas as linhas com o mesmo offset
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
	
	public boolean interceptaLabirinto(BreatHero boneco,int x,int y) //Verifica se o personagem intercepta o labirinto
	{
		Rectangle2D rect = new Rectangle2D.Double(x,y,boneco.getImagem().getWidth(),boneco.getImagem().getHeight());  //Cria um retângulo com base na imagem do personagem tendo exatamente as mesmas coordenadas
		boolean interceptou = false;
		
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<8; j++)
			{
				interceptou |= lines[i][j].intersects(rect);  //Verifica se o retangulo criado intercepta alguma das linhas, e caso intercepte, retorna um 'true'
			}
		}
		return interceptou;
	}
}
