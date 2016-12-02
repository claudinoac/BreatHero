package IO;  //Declara pacote ao qual pertence

import java.io.*; //Importando classes das APIs de I/O e Swing
import javax.swing.*; 
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManipulaDados 
{
	private JFileChooser janela = null;             //Variáveis do tipo objetos para manipulação de arquivos
	private String caminho = null;
	private FileNameExtensionFilter filter = null;
	private double x0=0,y0=0;                       //Variáveis de objeto para manipulação de dados carregados de arquivos de gravação de jogo e recordes
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
	
	public void salvaRecordes(String nome, long recorde) throws IOException  //Recebe o nome do ultimo jogador, testa se ele possui maior recorde que algum dos anteriores e atualiza os recordes.
	{
		boolean flagMudou = true;
		caminho = "src/resources/recordes.bhrc";
		carregaRecordes();
		FileWriter gravaArquivo = new FileWriter(caminho);			
		PrintWriter writer = new PrintWriter(gravaArquivo);
		
		for(int i=0; i<6; i++)
		{
			if(recorde > recordeJogadores[i] && flagMudou == true)  //Verifica se o recorde do jogador é maior que dos outros;
			{
				flagMudou = false;
				recordeJogadores[i] = recorde;
				nomeJogadores[i] = nome;
			}	
		}
		
		for(int i=0; i<6;i++)	//Atualiza o arquivo de recordes
		{
			writer.println(nomeJogadores[i]);
			writer.println(recordeJogadores[i]);
		}
		writer.close();
	}
	
	public void salvaJogo(double x_0, double y_0, int velocidade, long scoreAtual, int faseAtual,String joy,String modo) throws IOException  //Salva os dados de jogo
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
	
	public void carregaRecordes()  //Carrega o arquivo de recordes e guarda em variáveis de objeto para serem lidas externamente através de um getter
	{ 
		try  //Uma exceção pode ser lançada se o arquivo não existir
		{
			String caminho = "src/resources/recordes.bhrc";
			File arquivo = new File(caminho);
			FileReader arq = new FileReader(arquivo);
			BufferedReader lerSave = new BufferedReader(arq);
			
			for(int i=0;i<6;i++)   //Carrega os dados do arquivo em variáveis-buffer para serem posteriormente manipulados
			{
				try
				{
					nomeJogadores[i] = lerSave.readLine();
					recordeJogadores[i] = Long.parseLong(lerSave.readLine());
				}
				catch(java.lang.NumberFormatException e)  //trata uma exceção que pode ser lançada caso alguma linha seja vazia
				{
					nomeJogadores[i] = "";
					recordeJogadores[i] = 0;
				}
			}
			lerSave.close();
			
		}
		catch(IOException e) //Se a exceção foi lançada, nenhum arquivo foi carregado e uma mensagem de erro aparece
		{
			JOptionPane.showMessageDialog(null,"Error404","Erro ao ler arquivo de recordes",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	public void carregaJogo() //Carrega o arquivo de estado salvo e guarda os dados em variáveis de objeto para serem lidas externamente através de um getter
	{
		try //Uma exceção pode ser lançada caso o arquivo a ser carregado não existir
		{
			int retorno = janela.showOpenDialog(null);
			if(retorno == JFileChooser.APPROVE_OPTION) //Verifica se o usuário escolheu algum arquivo para carregar
			{
				File arquivo = janela.getSelectedFile();
				FileReader arq = new FileReader(arquivo);
				BufferedReader lerSave = new BufferedReader(arq);
				try //Uma exceção pode ser lançada caso alguma das linhas seja nula devido aos casts
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
				catch(IOException e) //Caso a exceção seja lançada, um painel de erro é exibido
				{
					JOptionPane.showMessageDialog(null,"Error404","Erro ao ler dados",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else //Se nenhum arquivo foi selecionado, um painel é exibido avisando isso
			{
				JOptionPane.showMessageDialog(null, "Nenhum Arquivo Selecionado");
			}
		}
		catch(Exception e){}
	}

	public double getX0()  //Geters e seters para variáveis utilizadas como buffer de dados
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
