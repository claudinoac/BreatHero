package Personagens;                	 //Declara o pacote para o qual pertence

import java.awt.Graphics2D;				 //Importa classes das APIs awt, I/O e imageI/O
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class BreatHero 
{
	private BufferedImage imagem; //Declara buffer da imagem do personagem
	private int x0 = 75;          //Define posição fixa do personagem em x
	private int y = 210;          //Define posição do personagem em y
	
	public BreatHero()
	{
		geraBreatHero();
	}
	
	public Graphics2D paintComponent(Graphics2D g2)  //Metodo que recebe um gráfico externo e retorna o mesmo com o personagem desenhado
	{
		g2.drawImage(imagem,x0,y,null);   //desenha a imagem no gráfico
		return g2;
	}

	public void geraBreatHero()
	{
		try
		{
			imagem = ImageIO.read(new File("src/resources/bexiga.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("Imagem do BreatHero não encontrada");
		}
	}
	
	public void moveBreatHero(double y02)    //Setter para a posição y do personagem
	{
		y =  (int)y02;
	}
	
	public int getY0()     //Getters e setters
	{
		return y;
	}
	
	public int getX0()
	{
		return x0;
	}
	
	public BufferedImage getImagem()   
	{
		return imagem;
	}
}
