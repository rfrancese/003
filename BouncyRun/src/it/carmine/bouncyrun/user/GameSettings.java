package it.carmine.bouncyrun.user;

public class GameSettings {
	private UserSettings us;
	private String difficult;
	
	public final String DIFFICULT_EASY="Easy";
	public final String DIFFICULT_NORMAL="Normal";
	public final String DIFFICULT_HARD="Hard";
	
	public void setUserSettings(UserSettings u){
		us=u;
	}
	public UserSettings getUserSettings(){
		return us;
	}
	public void setDifficult(String d){
		difficult=d;
	}
	
	public int getSleepDifficult(String diff){
		
		if(diff.equals(DIFFICULT_EASY))return 13;
		else if(diff.equals(DIFFICULT_NORMAL)) return 10;
		else if(diff.equals(DIFFICULT_HARD))return 5;
		
		return -1;
	}
	public String getDifficult(){
		return difficult;
	}
}
