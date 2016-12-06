package IO; //Define o pacote para o qual pertence

public interface Joystick 
{
	public char acaoCtrl();               //Todo joystick deve definir uma ação a ser tomada
	
	public void setPaused(boolean value); //Todo joystick deve saber se o jogo está pausado
}
