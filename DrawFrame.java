import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
class DrawFrame extends JFrame 
{
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	private long speed = 3;
	
	public DrawFrame() 
	{
	   setTitle("Tela de Jogo");
	   setSize(WIDTH, HEIGHT);
	   setResizable(false);
	   this.setVisible(true);
	   this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    
	   GameEngine g1 = new GameEngine(new Labirinto(),0,0,speed);
	   JMenuBar menu = new JMenuBar();
	   JButton file = new JButton("Arquivo");
	   JLabel pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
	   
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
	        			
	        		break;
	        		
	        		case JOptionPane.CANCEL_OPTION:
	        			try 
	        			{
	        				JOptionPane.showMessageDialog(null, "O jogo continuará após o ok");
	        				Thread.sleep(2000);
	        				g1.setPaused(false);
	        			}	 
	        			catch (InterruptedException e1){}
	        		break;
	            }
	        	
	        }
	   });
	   
	   file.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DrawFrame.this.dispose();	
			}
		
		});
	   menu.add(file);
	   menu.add(pontuacao);
	   this.setJMenuBar(menu);
	   this.add(g1);
	  /* while(true)
	   {
		   try 
		   {
			   Thread.sleep(1000);
		   } 
		   catch (InterruptedException e1) 
		   {}
		   pontuacao.setText("Pontuação: "+g1.getPontuacao()/(speed));
	   }
	   */
	}
}