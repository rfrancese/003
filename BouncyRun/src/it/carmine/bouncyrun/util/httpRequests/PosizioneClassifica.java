package it.carmine.bouncyrun.util.httpRequests;

public class PosizioneClassifica {

	private String nick,punteggio,difficolta;
	public PosizioneClassifica(String nick,String punteggio,String difficolta){
		this.nick=nick;
		this.punteggio=punteggio;
		this.difficolta=difficolta;
	}
	public String getNick(){
		return nick;
	}
	public String getP(){
		return punteggio;
	}
	public String getDiff(){
		return difficolta;
	}
}
