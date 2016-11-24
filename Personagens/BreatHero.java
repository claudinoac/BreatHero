package Personagens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class BreatHero 
{
	private BufferedImage imagem;
	private int x0 = 75;
	private int y0 = 0;
	private int y = 0;
	
	

	public BreatHero()
	{
		geraBreatHero();
	}
	
	public Graphics2D paintComponent(Graphics2D g2)
	{
		g2.drawImage(imagem,x0,y,null);
		return g2;
	}
	
	public int getX0() {
		return x0;
	}

	public BufferedImage getImagem() {
		return imagem;
	}

	public void geraBreatHero()
	{
		y=y0;
		try
		{
			imagem = ImageIO.read(new File("src/resources/bexiga.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("Imagem do BreatHero não encontrada");
		}
	}
	
	public void moveBreatHero(int offset)
	{
		y =  offset;
	}
	
	public int getY() 
	{
		return y;
	}
}
