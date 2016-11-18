import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Labirinto /*extends JPanel*/
{
	private double x0 = 0;
	private double x1 = 150;
	private double y0 = 150;
	private double y1 = 75;

	Line2D.Double[][] lines = new Line2D.Double[2][14];
	
	public Graphics2D paintComponent(Graphics2D g2) 
	{	
      
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				g2.draw(lines[i][2*j]);
				g2.draw(lines[i][2*j+1]);
				
			}
		}
		return g2;
	}
	    
	public void geraLabirinto(double x_init)
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				lines[i][2*j] = new Line2D.Double(x0 + x1*(j) - x_init, y1+(i+1)*y0, x0 + x1*(j+1)- x_init,  y1+i*y0);
				lines[i][2*j+1] = new Line2D.Double(x0 + x1*(j+2) - x_init, y1+(i+1)*y0, x0 + x1*(j+1) - x_init,y1+ i*y0);
			}
			
		}
		
		
	}
	
	public void moveLabirinto(double offset)
	{
		for(int i=0; i<2; i++)	
		{
			for(int j=0;j<7; j+=2)
			{
				lines[i][2*j].setLine(x0 + x1*(j) - offset, y1+(i+1)*y0, x0 + x1*(j+1) - offset,  y1+i*y0);
				lines[i][2*j+1].setLine(x0 + x1*(j+2) - offset, y1+(i+1)*y0, x0 + x1*(j+1) - offset,y1+ i*y0);
			}
		}
	}
	
}