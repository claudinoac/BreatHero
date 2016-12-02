package Game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class MenuOpcoes extends JPanel
{
	private String[] velocidades = {"1","2","3","4","5"};
	private JComboBox<String> velBox;
	private ButtonGroup fase;
	private JRadioButton fase1;
	private JRadioButton fase2;
	
	private ButtonGroup joystick;
	private JRadioButton teclado;
	private JRadioButton respirometro;
	
	private ButtonGroup modo;
	private JRadioButton desafio;
	private JRadioButton zen;
	
	public MenuOpcoes()
	{
		
		JLabel velLabel = new JLabel("Velocidade Inicial: ");
		this.velBox = new JComboBox<String>(velocidades);
		
		JLabel faseLabel = new JLabel("Fase: ");
		this.fase = new ButtonGroup();
		this.fase1 = new JRadioButton("1");
		this.fase2 = new JRadioButton("2");
		this.fase.add(fase1);
		this.fase.add(fase2);
		
		JLabel joyLabel = new JLabel("Joystick: ");
		this.joystick = new ButtonGroup();
		this.teclado = new JRadioButton("Teclado");
		this.respirometro = new JRadioButton("Respirômetro");
		this.joystick.add(teclado);
		this.joystick.add(respirometro);
		
		JLabel modoLabel = new JLabel("Modo: ");
		this.modo = new ButtonGroup();
		this.desafio = new JRadioButton("Desafio");
		this.zen = new JRadioButton("Zen");
		this.modo.add(desafio);
		this.modo.add(zen);
		
		this.velBox.setSelectedItem("1");
		this.fase1.setSelected(true);
		this.teclado.setSelected(true);
		this.desafio.setSelected(true);
		
		this.velBox.setMaximumSize(new Dimension(2,2));
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		int[] paneColWidth = {21,10,10,10};
		
		layout.columnWidths = paneColWidth;
		this.setLayout(layout);
		
		g.gridx = 0;
		g.gridy = 0;
		g.anchor = GridBagConstraints.EAST;
		this.add(velLabel,g);
		
		//g.anchor = GridBagConstraints.CENTER;
		g.gridx = 1;
		this.add(velBox,g);
		
		g.gridx = 0;
		g.gridy = 1;
		g.anchor = GridBagConstraints.EAST;
		this.add(faseLabel,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridx = 1;
		this.add(fase1,g);
		g.gridx = 2;
		this.add(fase2,g);
		
		g.anchor = GridBagConstraints.EAST;
		g.gridx = 0;
		g.gridy = 2;
		this.add(joyLabel,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridx = 1;
		this.add(teclado,g);
		g.gridx = 2;
		this.add(respirometro,g);
	
		g.anchor = GridBagConstraints.EAST;
		g.gridx = 0;
		g.gridy = 3;
		this.add(modoLabel,g);
		
		g.anchor = GridBagConstraints.WEST;
		g.gridx = 1;
		this.add(desafio,g);
		g.gridx = 2;
		this.add(zen,g);	
		this.setVisible(true);
	}
	
	public int getVelocidadeInicial() 
	{
		return Integer.parseInt((String)velBox.getSelectedItem());
	}

	public int getFase() 
	{
		int fase = 1;
		if(fase1.isSelected())
			fase = 1;
		if(fase2.isSelected())
			fase = 2;
		
		return fase;
	}
	
	public String getMode()
	{
		String mode = "desafio";
			if(desafio.isSelected())
				mode = "desafio";
			
			if(zen.isSelected())
				mode = "zen";
			
			return mode;
	}
	
	public String getJoystick()
	{
		String joy = "teclado";
		
		if(teclado.isSelected())
			joy = "teclado";
		
		if(respirometro.isSelected())
			joy = "respirometro";
		
		return joy;
	}
}
