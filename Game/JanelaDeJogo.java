package Game;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

import IO.ManipulaDados;;

@SuppressWarnings("serial")
public class JanelaDeJogo extends JFrame 
{
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	private GameEngine g1;
	private JMenuBar menuBar;
	private JMenu menu;
	private JLabel pontuacao;
	private JLabel livesLabel;
	private JLabel nameLiveLabel;
	private String lives;
	private JMenuItem salvar;
	private JMenuItem carregar;
	private boolean isPaused = false;
	private HashMap<Integer,Boolean> keyPool;
	private int fase;

	public JanelaDeJogo() 
	{	
		this(50,0,10,0,1);
	}
	      
	public JanelaDeJogo(double x0,double y0, int speed, long scoreInicial,int fase) 
	{
		this.keyPool = new HashMap<Integer,Boolean>();
		this.fase = fase;
		try 
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e1) {}
				
		g1 = new GameEngine(fase,x0,y0,speed,scoreInicial);
		montaTela();
		addListeners();
		while(true)
		{
			if(!isPaused)
			{
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e1) 
				{
					System.out.println("Erro de renderização");
				}
				pontuacao.setText("Pontuação: "+g1.getPontuacao());
				g1.setKeyPool(keyPool);
			}
		}
	}
	
	public void addListeners()
	{
	   this.addKeyListener(new KeyAdapter()
	   {
		  
		  public void keyPressed(KeyEvent e)
		  {
			  keyPool.put(e.getKeyCode(), true);
			  if(keyPool.get(KeyEvent.VK_SPACE)!=null && !isPaused)
			   {
				  pausaJogo();
				  keyPool.remove(e.getKeyCode());
			   }
			  
			  if(keyPool.get(KeyEvent.VK_SPACE)!= null && isPaused)
			  {
				  continuaJogo();
				  keyPool.remove(e.getKeyCode());
			  }
		  }
		  
		  public void keyReleased(KeyEvent e)
		  {
			  keyPool.remove(e.getKeyCode());
		  }
	   });
		
	   this.addWindowListener(new WindowAdapter() 
	   {
		   public void windowClosing(WindowEvent e) 
	       {
	       	pausaJogo();
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
		       			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getSpeed(),g1.getPontuacao(),fase);
		       		}
		       		catch(Exception ex){}
		       	break;
		       		
		       	case JOptionPane.CANCEL_OPTION:
		       	break;
		          }
		       }
		   });
		   
	   salvar.addActionListener(new ActionListener()
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		pausaJogo();
	   		ManipulaDados dado = new ManipulaDados();
    		try
    		{
    			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getSpeed(),g1.getPontuacao(),fase);
    		}
    		catch(Exception ex){}
	   	}	
	   });
	   
	   carregar.addActionListener(new ActionListener()
	   {
	   	@Override
	   	public void actionPerformed(ActionEvent e)
	   	{
	   		pausaJogo();
	   		ManipulaDados dado = new ManipulaDados();
	   		dado.carregaJogo();
	   		fase = dado.getFase();
	   		remove(g1);
	   		g1 = new GameEngine(dado.getFase(),dado.getX0(),dado.getY0(),dado.getSpeed0(),dado.getScoreInicial());
	   		add(g1);
	   		continuaJogo();
	   	}
	   });
	  
	}  
	
	public void montaTela() 
	{
		setTitle("BreatHero");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menuBar = new JMenuBar();
		menu = new JMenu("Arquivo");
		pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
		salvar = new JMenuItem("Salvar Jogo");
		lives = "■■■■■■■■■■";
		nameLiveLabel = new JLabel("Lives: ");
		livesLabel = new JLabel(lives);
		carregar = new JMenuItem("Carregar Jogo");
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		int[] menuBarColWidth = {330,200,150};
		
		livesLabel.setForeground(Color.blue);
		menuBar.setLayout(layout);
		layout.columnWidths = menuBarColWidth;
		menu.add(salvar);
		menu.add(carregar);
		
		g.gridx = 0;
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(menu,g);
		
		g.fill = GridBagConstraints.RELATIVE;
		g.anchor = GridBagConstraints.WEST;
		g.gridx = 1;
		menuBar.add(nameLiveLabel,g);
		
		g.gridy = 0;
		g.gridx = 1;
		g.anchor = GridBagConstraints.CENTER;
		menuBar.add(livesLabel,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.fill = GridBagConstraints.HORIZONTAL;
	   	g.gridx = 2;
	   	menuBar.add(pontuacao,g);
	   
	   	setJMenuBar(menuBar);
	   	add(g1);
	}

	public void pausaJogo()
	{
		try
		{
			setTitle("BreatHero - Pausado (Pressione \"espaço\" para continuar)");
			g1.setPaused(true);
			isPaused = true;
		}
		catch(Exception exc){}
	}
	
	public void continuaJogo()
	{
		try
		{
			Thread.sleep(100);
			setTitle("BreatHero - Rodando");
			g1.setPaused(false);
			isPaused = false;
		}
		catch(Exception exc){}
	}
	
	public boolean isPaused()
	{
		boolean value = false;
		value |= (isPaused || g1.isPaused());
		return value;
	}
}
	  
