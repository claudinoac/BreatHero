import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManipulaDados 
{
	private JFileChooser janela = null;
	private String caminho = null;
	private FileNameExtensionFilter filter = null;
	private double x0=0,y0=0;
	private int speed0=12;
	private long scoreInicial =0;
	private int fase;
	
	public ManipulaDados()
	{
		this.janela = new JFileChooser();
		this.filter = new FileNameExtensionFilter("BreatHero Save States","bh");
		janela.setFileFilter(filter);
	}
	
	public void salvaJogo(double x0, double y0, int speed0, long scoreInicial, int fase) throws IOException
	{
		int retorno = janela.showSaveDialog(null);
		if(retorno == JFileChooser.APPROVE_OPTION)
		{
			caminho = janela.getSelectedFile().getAbsolutePath();
		}
		if(caminho != "")
		{
			FileWriter gravaArquivo = new FileWriter(caminho);
			PrintWriter writer = new PrintWriter(gravaArquivo);
			writer.println("Posição x inicial:");
			writer.println(x0);
			writer.println("Posição y inicial:");
			writer.println(y0);
			writer.println("Velocidade Inicial:");
			writer.println(speed0);
			writer.println("Score Inicial:");
			writer.println(scoreInicial);
			writer.println("Fase Atual:");
			writer.println(fase);
			writer.close();
			JOptionPane.showMessageDialog(null,"Jogo salvo com sucesso!","OK!",JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			throw new FileNotFoundException();
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
				String nomeArquivo = arquivo.getName();
				if(nomeArquivo == "")
				{
					JOptionPane.showMessageDialog(null, "Nenhum Arquivo Selecionado");
				}
				else
				{
					FileReader arq = new FileReader(arquivo);
					BufferedReader lerSave = new BufferedReader(arq);
					try
					{
						@SuppressWarnings("unused")
						String linha = lerSave.readLine();
						this.x0 = Double.parseDouble(lerSave.readLine());
						linha = lerSave.readLine();
						this.y0 = Double.parseDouble(lerSave.readLine());
						linha = lerSave.readLine();
						this.speed0 = Integer.parseInt(lerSave.readLine());
						linha = lerSave.readLine();
						this.scoreInicial = Long.parseLong(lerSave.readLine());
						linha = lerSave.readLine();
						this.fase = Integer.parseInt(lerSave.readLine());
						lerSave.close();
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null,"Erro ao ler arquivo","Error404",JOptionPane.ERROR_MESSAGE);
					}
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

	public int getSpeed0() 
	{
		return speed0;
	}

	public long getScoreInicial() 
	{
		return scoreInicial;
	}
	
	public int getFase() 
	{
		return fase;
	}	
}
