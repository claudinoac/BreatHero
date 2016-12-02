package IO;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManipulaDados 
{
	private JFileChooser janela = null;
	private String caminho = null;
	private FileNameExtensionFilter filter = null;
	private double x0=0,y0=0;
	private int velocidade=12;
	private long scoreInicial =0;
	private int fase;
	private String joystick;
	private String mode;
	private String[] nomeJogadores = new String[6];
	private long[] recordeJogadores = new long[6];
	
	public ManipulaDados()    //Construtor da classe - Instancia Objetos necessários para utilização
	{
		this.janela = new JFileChooser();
		this.filter = new FileNameExtensionFilter("BreatHero Save States","bh");
		janela.setFileFilter(filter);
	}
	
	public void salvaRecordes(String nome, long recorde) throws IOException
	{
		boolean flagMudou = true;
		caminho = "src/resources/recordes.bhrc";
		carregaRecordes();
		FileWriter gravaArquivo = new FileWriter(caminho);			
		PrintWriter writer = new PrintWriter(gravaArquivo);
		
		for(int i=0; i<6; i++)
		{
			if(recorde > recordeJogadores[i] && flagMudou == true)
			{
				flagMudou = false;
				recordeJogadores[i] = recorde;
				nomeJogadores[i] = nome;
			}	
		}
		
		for(int i=0; i<6;i++)
		{
			writer.println(nomeJogadores[i]);
			writer.println(recordeJogadores[i]);
		}
		writer.close();
	}
	
	public void salvaJogo(double x_0, double y_0, int velocidade, long scoreAtual, int faseAtual,String joy,String modo) throws IOException  //Salva o jogo como
	{
		int retorno = janela.showSaveDialog(null);
		if(retorno == JFileChooser.APPROVE_OPTION)
		{
			caminho = janela.getSelectedFile().getAbsolutePath();
			if(!caminho.endsWith(".bh"))
			{	
				caminho = caminho +".bh";
			}
		}
		if(caminho != "")
		{
			FileWriter gravaArquivo = new FileWriter(caminho);
			PrintWriter writer = new PrintWriter(gravaArquivo);
			writer.println("Posição x inicial:");
			writer.println(x_0);
			writer.println("Posição y inicial:");
			writer.println(y_0);
			writer.println("Velocidade Inicial:");
			writer.println(velocidade);
			writer.println("Score Inicial:");
			writer.println(scoreAtual);
			writer.println("Fase Atual:");
			writer.println(faseAtual);
			writer.println("Joystick:");
			writer.println(joy);
			writer.println("Modo de Jogo:");
			writer.println(modo);
			writer.close();
			JOptionPane.showMessageDialog(null,"Jogo salvo com sucesso!","OK!",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			throw new FileNotFoundException();
		}
	}
	
	public void carregaRecordes()
	{ 
		try
		{
			String caminho = "src/resources/recordes.bhrc";
			File arquivo = new File(caminho);
			FileReader arq = new FileReader(arquivo);
			BufferedReader lerSave = new BufferedReader(arq);
			
			for(int i=0;i<6;i++)
			{
				try
				{
					nomeJogadores[i] = lerSave.readLine();
					recordeJogadores[i] = Long.parseLong(lerSave.readLine());
				}
				catch(java.lang.NumberFormatException e)
				{
					nomeJogadores[i] = "";
					recordeJogadores[i] = 0;
				}
			}
			lerSave.close();
			
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Error404","Erro ao ler arquivo de recordes",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	public void carregaJogo()
	{
		try
		{
			int retorno = janela.showOpenDialog(null);
			if(retorno == JFileChooser.APPROVE_OPTION)
			{
				File arquivo = janela.getSelectedFile();
				FileReader arq = new FileReader(arquivo);
				BufferedReader lerSave = new BufferedReader(arq);
				try
				{
					lerSave.readLine();
					this.x0 = Double.parseDouble(lerSave.readLine());
					lerSave.readLine();
					this.y0 = Double.parseDouble(lerSave.readLine());
					lerSave.readLine();
					this.velocidade = Integer.parseInt(lerSave.readLine());
					lerSave.readLine();
					this.scoreInicial = Long.parseLong(lerSave.readLine());
					lerSave.readLine();
					this.fase = Integer.parseInt(lerSave.readLine());
					lerSave.readLine();
					this.joystick = lerSave.readLine();
					lerSave.readLine();
					this.mode = lerSave.readLine();
					lerSave.close();
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(null,"Error404","Erro ao ler arquivo",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "Nenhum Arquivo Selecionado");
			}
		}
		catch(Exception e){}
	}

	public double getX0() 
	{
		return x0;
	}

	public double getY0() 
	{
		return y0;
	}

	public int getVelocidade() 
	{
		return velocidade;
	}

	public long getScoreInicial() 
	{
		return scoreInicial;
	}
	
	public int getFase() 
	{
		return fase;
	}

	public String getJoystick() 
	{
		return joystick;
	}	
	
	public String getMode()
	{
		return mode;
	}
	
	public String[] getNomeJogadores()
	{
		return nomeJogadores;
	}

	public long[] getRecordeJogadores() 
	{
		return recordeJogadores;
	}

}
