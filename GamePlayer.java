/*Gregory Danayan
 *gdanaya@uwo.ca
 *The GamePlayer class stores the result and stats of a player performance in a game they played in.
 *on top of all the stats each GamePlayer class is associated to a player, and the team the player is on, as well as who the game was agaisnt
 *and weather or not they were home or away.
 */
public class GamePlayer {
	
	private int date;
	private int points;
	private int fieldGM;
	private int fieldGA;
	private int threePM;
	private int threePA;
	private int freeTM;
	private int freeTA;
	private int rebounds;
	private int assists;
	private int steals;
	private int blocks;
	private int plusMinus;
	private double minutes;
	private char winOrLoss;
	private String opponent;
	private String playerName;
	private String teamName;
	private char homeAway;
	
	GamePlayer(String name, String team, String opp, int DOG, char WL, double NOM, int NOP, int FGM, int FGA, int TPM, int TPA, int FTM, int FTA, int REB, int AST, int STL, int BLK, int PM){
		//all of the variables are initialized to the game stats and data passed through the constructor.
		this.date = DOG;
		this.points = NOP;
		this.fieldGA = FGA;
		this.fieldGM = FGM;
		this.threePA = TPA;
		this.threePM = TPM;
		this.freeTA = FTA;
		this.freeTM = FTM;
		this.rebounds = REB;
		this.assists = AST;
		this.steals = STL;
		this.blocks = BLK;
		this.plusMinus = PM;
		this.minutes = NOM;
		this.winOrLoss = WL;
		this.playerName = name;
		this.teamName = team;
		//home or away is decided by is the opponent string passed through the parameter has an '@' before the opponents name
		if(opp.charAt(opp.length() - 5) == '@') {
			this.homeAway = 'A';
		}
		else {
			this.homeAway = 'H';
		}
		//opponent is initialized as what comes after the '@' charater in the string passed through the parameter
		this.opponent = opp.substring(opp.length() - 3, opp.length());
	}
	//Getter methods for all of the data in the GamePlayer
	public int getDate() {
		return this.date;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public int getFieldGA() {
		return this.fieldGA;
	}
	
	public int getFieldGM() {
		return this.fieldGM;
	}
	
	public int getThreePA() {
		return this.threePA;
	}
	
	public int getThreePM() {
		return this.threePM;
	}
		
	public int getFreeTA() {
		return this.freeTA;
	}
		
	public int getFreeTM() {
		return this.freeTM;
	}
		
	public int getRebounds() {
		return this.rebounds;
	}
		
	public int getAssists() {
		return this.assists;
	}
		
	public int getSteals() {
		return this.steals;
	}
	
	public int getBlocks() {
		return this.blocks;
	}
		
	public int getPlusMinus() {
		return this.plusMinus;
	}
		
	public double getMinutes() {
		return this.minutes;
	}
	
	public char getResult() {
		return this.winOrLoss;
	}
	
	public String getOpponent() {
		return this.opponent;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public char getHomeAway() {
		return this.homeAway;
	}
}
