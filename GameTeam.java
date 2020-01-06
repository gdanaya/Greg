/*Gregory Danayan
 * gdanaya@uwo.ca
 *The Game Team class stores all the stats from a teams game, ontop of all these stats
 *each GameTeam is associated to a team, an oppoent, whether or not the team was at home or away
 * list and the number of the players who played for the team that game.
 */
public class GameTeam {
	private int date;
	private int teamScore;
	private int oppScore;
	private char winOrLoss;
	private String opponent;
	private String teamName;
	private char homeAway;
	private String[] playerList;
	private int numPlayers;
	GameTeam(String team, String opp, int DOG, char WL, int score, int dif){
		//all of the variables are initialized
		//home or away is decided by is the opponent string passed through the parameter has an '@' before the opponents name
		this.teamName = team;
		if(opp.charAt(opp.length() - 5) == '@') {
			this.homeAway = 'A';
		}
		else {
			this.homeAway = 'H';
		}
		this.opponent = opp.substring(opp.length() - 3, opp.length());
		this.date = DOG;
		this.winOrLoss = WL;
		this.teamScore = score;
		this.oppScore = score - dif;
		this.numPlayers = 0;
		playerList = new String[13];
	}
	//addPlayer method takes a string of the players name who played for the team this game and added it to the playerList, then it updates
	//the number of players
	public void addPlayer(String newPlayer) {
		if(numPlayers >= 13) {
			System.out.println("Error only 13 players can play for an nba team per game");
		}
		
		playerList[numPlayers]= newPlayer;
		numPlayers += 1;
	}
	//getter methods for the rest of the stats
	public int getDate() {
		return this.date;
	}
	
	public int getTeamScore() {
		return this.teamScore;
	}
	
	public int getOppScore() {
		return this.oppScore;
	}
	
	public char getWL() {
		return this.winOrLoss;
	}
	
	public String getOpp() {
		return this.opponent;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public char getHA() {
		return this.homeAway;
	}
	
	public String[] getPlayerList() {;
		return this.playerList;
	}
	
	public int getNumPlayers() {
		return this.numPlayers;
	}
}
