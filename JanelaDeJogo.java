import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
class JanelaDeJogo extends JFrame 
{
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	private GameEngine g1;
	private JMenuBar menuBar;
	private JMenu menu;
	private JLabel pontuacao;
	private JMenuItem salvar;
	private JMenuItem carregar;
	private boolean isPaused = false;

	public JanelaDeJogo() 
	{
	   setTitle("Menu");
	   setSize(WIDTH, HEIGHT);
	   setResizable(false);
	   this.setVisible(true);
	   this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	   this.g1 = new GameEngine(new Fase1(),0,0,5,0,1);
	   this.menuBar = new JMenuBar();
	   this.menu = new JMenu("Arquivo");
	   this.pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
	   this.salvar = new JMenuItem("Salvar Jogo");
	   this.carregar = new JMenuItem("Carregar Jogo");
	   
	   this.menu.add(salvar);
	   this.menu.add(carregar);
	   this.menuBar.add(menu);
	   this.menuBar.add(pontuacao);
	   this.setJMenuBar(menuBar);
	   this.add(g1);
	   
	   
	   addListeners();
	   
	   while(true)
	   {
		   try 
		   {
			   Thread.sleep(100);
		   } 
		   catch (InterruptedException e1) 
		   {}
		   pontuacao.setText("Pontuação: "+g1.getPontuacao());
	   }
	}
	      
	public JanelaDeJogo(double x0,double y0, int speed, long scoreInicial,int fase) 
	{
		setTitle("Menu");
		   setSize(WIDTH, HEIGHT);
		   setResizable(false);
		   this.setVisible(true);
		   this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		   this.g1 = new GameEngine(new Fase1(),x0,y0,speed,scoreInicial,fase);
		   this.menuBar = new JMenuBar();
		   this.menu = new JMenu("Arquivo");
		   this.pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
		   this.salvar = new JMenuItem("Salvar Jogo");
		   this.carregar = new JMenuItem("Carregar Jogo");
		   
		   this.menu.add(salvar);
		   this.menu.add(carregar);
		   this.menuBar.add(menu);
		   this.menuBar.add(pontuacao);
		   this.setJMenuBar(menuBar);
		   this.add(g1);
		   
		   addListeners();
		   
		   while(!isPaused)
		   {
			   try 
			   {
				   Thread.sleep(100);
			   } 
			   catch (InterruptedException e1) 
			   {}
			   pontuacao.setText("Pontuação: "+g1.getPontuacao());
		   }
	}
	
	public void addListeners()
	{
	   this.addWindowListener(new WindowAdapter() 
	   {
	        public void windowClosing(WindowEvent e) {
	        	g1.setPaused(true);
	        	int option = JOptionPane.showConfirmDialog(null,"Salvar", "Deseja salvar o jogo?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
	        	switch(option)
	        	{
		        	case JOptionPane.NO_OPTION:
		        		System.exit(0);
		        	break;
		        	
		        	case JOptionPane.YES_OPTION:
		        		ManipulaDados dado = new ManipulaDados();
		        		try
		        		{
		        			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getSpeed(),g1.getPontuacao(),g1.getFase());
			        		continuaJogo();
		        		}
		        		catch(Exception ex)
		        		{
		        			continuaJogo();
		        		}
		        	break;
		        		
		        	case JOptionPane.CANCEL_OPTION:
		        		continuaJogo();
		        	break;
		           }
		        }
		   });
		   
	   salvar.addActionListener(new ActionListener()
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		g1.setPaused(true);
	   		isPaused = true;
	   		ManipulaDados dado = new ManipulaDados();
    		try
    		{
    			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getSpeed(),g1.getPontuacao(),g1.getFase());
        		continuaJogo();
    		}
    		catch(Exception ex)
    		{
    			continuaJogo();
    		}	   		
	   	}
	   			
	   });
	   
	   carregar.addActionListener(new ActionListener()
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		g1.setPaused(true);
	   		isPaused = true;
	   		ManipulaDados dado = new ManipulaDados();
			dado.carregaJogo();
			g1 = new GameEngine(new Fase1(),dado.getX0(),dado.getY0(),dado.getSpeed0(),dado.getScoreInicial(),dado.getFase());	
			g1.setPaused(false);
			isPaused = false;
			add(g1);
	   	}
	   			
	   });
	}  
	
	public void continuaJogo()
	{
		try
		{
			JOptionPane.showMessageDialog(null, "Pressione Ok para continuar");
			Thread.sleep(100);
			g1.setPaused(false);
			isPaused = false;
		}
		catch(Exception exc){}
	}
}
	  
