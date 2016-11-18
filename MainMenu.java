import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class MainMenu extends JFrame 
{
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private JPanel painel,painelBotao,titlePane;
	private JButton[] botoes;
	private JLabel title,subtitle;
	private String pressedBut;
	private boolean pressedFlag;
	
	public MainMenu()
	{
		super("BreatHero");
		this.setSize(WIDTH,HEIGHT);
		this.setResizable(false);
		painel = criaPainelPrincipal();
		addListeners();
		
		this.add(painel);
		this.setVisible(true);	
	}


	private void addListeners()
	{
		botoes[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "novo";
				pressedFlag = true;				
			}
		
		});
		
		botoes[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "opcoes";
				pressedFlag = true;				
			}
		
		});
	
		botoes[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "continua";
				pressedFlag = true;
			}
		});
		
		botoes[3].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedBut = "recordes";
				pressedFlag = true;		
			}
		});
		
		botoes[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				pressedFlag = true;
				System.exit(0);
			}
		});
	}

	public void setPressedFlag(boolean pressedFlag) {
		this.pressedFlag = pressedFlag;
	}

	public boolean isPressedFlag() 
	{
		return pressedFlag;
	}

	public String getPressedBut() 
	{
		return pressedBut;
	}

	public JPanel criaPainelPrincipal()
	{

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		
		titlePane = new JPanel();
		subtitle = new JLabel("O Jogo da Respiração");
		title = new JLabel("       BreatHero");
		botoes = new JButton[5];
		botoes[0] = new JButton("Novo Jogo");
		botoes[1] = new JButton("Continuar Jogo");
		botoes[2] = new JButton("Opções");
		botoes[3] = new JButton("Recordes");
		botoes[4] = new JButton("Sair");
		painel = new JPanel();
		painelBotao = new JPanel();
		
		painel.setLayout(layout);
		painel.setSize(this.getSize());
		
		title.setFont(new Font("Dialog", Font.PLAIN, 30));
		subtitle.setFont(new Font("Dialog",Font.PLAIN,25));
		
		painelBotao.setLayout(new GridLayout(5,1,0,30));
		
		for(int i=0;i<5;i++)
			painelBotao.add(botoes[i]);
		
		titlePane.setLayout(new GridLayout(2,3,0,10));
		titlePane.add(title);
		titlePane.add(subtitle);
		
		g.anchor = GridBagConstraints.NORTH;
		g.gridy = -20;
		g.insets = new Insets(0,0,90,0);
		painel.add(titlePane,g);
		
		g.anchor = GridBagConstraints.CENTER;
		g.gridy = 1;
		painel.add(painelBotao,g);
		
		return painel;
		
	}
}