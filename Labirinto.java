import java.awt.Graphics2D;

public abstract class Labirinto
{
	public Labirinto(){}
	
	public Graphics2D paintComponent(Graphics2D g2) 
	{
		return g2;
	} 
	  
	public void geraLabirinto()	{}
	
	public void moveLabirinto(double offset){}
	
}