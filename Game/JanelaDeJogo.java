package Game;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import IO.ManipulaDados;

@SuppressWarnings("serial")
public class JanelaDeJogo extends JFrame 
{
	private final int WIDTH = 700;
	private final int HEIGHT = 480;
	private GameEngine g1;
	private JLabel pontuacao;
	private JLabel livesLabel;
	private JLabel velLabel;
	private JMenuItem salvar;
	private JMenuItem carregar;
	private Timer atualizaBarra;

	      
	public JanelaDeJogo(double x0,double y0, int velocidade, long scoreInicial,int fase,String joystick,String modo) 
	{
		
		this.atualizaBarra = new Timer();
		
		g1 = new GameEngine(fase,x0,y0,velocidade,scoreInicial, joystick,modo);
		montaTela();
		addListeners();
		g1.setPaused(true);
		repaint();
		atualizaBarraTimer();
	}
	
	public void atualizaBarraTimer()
	{
		atualizaBarra.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run() 
			{
				if(!g1.isPaused())
				{
					pontuacao.setText("Pontuação: "+g1.getPontuacao());
					livesLabel.setText(g1.getVidasVisual());
					if(g1.getVidas() == 0)
					{
						pausaJogo();
						gameOver();
					}
				}
				
			}
		}, 0, 100);
	}
	
	public void addListeners()
	{
	   this.addKeyListener(g1.getJoystick());
		
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
		       			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getVelocidade(),g1.getPontuacao(),g1.getFase(),g1.getJoyType(),g1.getMode());
		       		}
		       		catch(Exception ex)
		       		{
		       			JOptionPane.showMessageDialog(null, "Erro","Erro ao Salvar arquivo",JOptionPane.ERROR_MESSAGE);
		       		}
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
    			dado.salvaJogo(g1.getX0(),g1.getY0(),g1.getVelocidade(),g1.getPontuacao(),g1.getFase(),g1.getJoyType(),g1.getMode());
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null, "Erro","Erro ao Salvar arquivo",JOptionPane.ERROR_MESSAGE);
    		}
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
	   		remove(g1);
	   		g1 = new GameEngine(dado.getFase(),dado.getX0(),dado.getY0(),dado.getVelocidade(),dado.getScoreInicial(),dado.getJoystick(),dado.getMode());
	   		add(g1);
	   		repaint();
	   		continuaJogo();
	   		pausaJogo();
	   	}
	   });
	}  
	
	public void montaTela() 
	{
		setTitle("BreatHero - Aperte \"espaço\" para iniciar");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Arquivo");
		salvar = new JMenuItem("Salvar Jogo");
		carregar = new JMenuItem("Carregar Jogo");
		JLabel nameVelLabel = new JLabel("Velocidade: ");
		velLabel = new JLabel(g1.getVelocidadeVisual());
		JLabel nameLiveLabel = new JLabel("Vidas: ");
		livesLabel = new JLabel(g1.getVidasVisual());
		
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		
		int[] menuBarColWidth = {130,10,130,10,170,150};
		
		pontuacao = new JLabel("Pontuação: "+g1.getPontuacao());
		
		livesLabel.setForeground(Color.BLUE);
		layout.columnWidths = menuBarColWidth;
		menuBar.setLayout(layout);
		menu.add(salvar);
		menu.add(carregar);
		
		g.fill = GridBagConstraints.RELATIVE;
		g.gridx = 0;
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(menu,g);
		
		
		g.gridx = 1;
		g.anchor = GridBagConstraints.EAST;
		menuBar.add(nameVelLabel,g);
		
		g.gridx = 2;
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(velLabel,g);
		
		
		g.anchor = GridBagConstraints.EAST;
		g.gridx = 3;
		menuBar.add(nameLiveLabel,g);
		
		g.gridy = 0;
		g.gridx = 4;
		g.anchor = GridBagConstraints.WEST;
		menuBar.add(livesLabel,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.fill = GridBagConstraints.HORIZONTAL;
	   	g.gridx = 5;
	   	menuBar.add(pontuacao,g);
	   
	   	setJMenuBar(menuBar);
	   	add(g1);
	   	setVisible(true);
	}

	public void pausaJogo()
	{
		setTitle("BreatHero - Pausado (Pressione \"espaço\" para continuar)");
		g1.setPaused(true);
	}
	
	public void continuaJogo()
	{
		setTitle("BreatHero - Rodando (Pessione \"espaço\" para pausar)");
		g1.setPaused(false);
	}
	
	
	public void gameOver()
	{
		try
		{
			String nomeJogador = null;
			JOptionPane.showMessageDialog(null, "Game Over","Você perdeu todas as vidas!",JOptionPane.WARNING_MESSAGE);
			nomeJogador = JOptionPane.showInputDialog(null,"Digite seu nome:","Game Over",JOptionPane.QUESTION_MESSAGE);
		
			if(!nomeJogador.endsWith(": "))
				nomeJogador+=": ";
		
			ManipulaDados dado = new ManipulaDados();
			try
			{
				dado.salvaRecordes(nomeJogador, g1.getPontuacao());
			}
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(null,"Erro ao salvar arquivo de recordes", "Erro",JOptionPane.ERROR_MESSAGE);
			}
			System.exit(0);
		}
		catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Erro! Você não quis colocar seu nome para os recordes :(","Erro!",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
	  
