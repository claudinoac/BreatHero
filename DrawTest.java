import javax.swing.JFrame;

public class DrawTest 
{	
	public static MainMenu menu;
	
	public static void main(String[] args) 
	{
	
	  menu = new  MainMenu();
	  menu.setVisible(true);
	  menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  while(true)
	  {
		  while(!menu.isPressedFlag())
		  {}
		  String option = menu.getPressedBut();
		  switch(option)
		  {
		  	case "novo":
		  		menu.setVisible(false);
		  		DrawFrame frame = new DrawFrame();
		  		frame.setVisible(true);
		  		menu.setPressedFlag(false);
			break;
			
			
		  	case "recordes":
		  		menu.setVisible(false);	
		  	break;	
		  	}
		  }  
	}
}	