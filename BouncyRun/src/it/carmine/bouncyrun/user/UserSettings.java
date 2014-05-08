package it.carmine.bouncyrun.user;

public class UserSettings {
	private String nick;
	
	public UserSettings(String n){
		nick=n;
	}
	public UserSettings(){
		
	}
	public void setNick(String n){
		nick=n;
	}
	public String getNick(){
		return nick;
	}
}
