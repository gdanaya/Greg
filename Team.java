/*Gregory Danayan
 * gdanaya@uwo.ca
 * The Team class represents a team, each team belongs to a conference and a division, each team has a name, records associated with
 * them, a list of objects of type Player which consists of players who have played for the team this season, the number of players who
 * have played for them this season and the number of games they have played this season
 */
public class Team {
	
	private char confrence;
	private char division;
	private String teamName;
	private Record teamRecord;
	private Record awayRecord;
	private Record homeRecord;
	private Record lastTenGames;
	private Player[] playerList;
	private GameTeam[] gameList;
	private int numGames;
	private int numPlayers;
	Team(String name, char conf, char div){
		//initialize variables, and create empty lists for the games and players to be added to as the league builds
		this.confrence = conf;
		this.division = div;
		this.teamName = name;
		numGames = 0;
		numPlayers = 0;
		
		teamRecord = new Record();
		awayRecord = new Record();
		homeRecord = new Record();
		lastTenGames = new Record();
		playerList = new Player[30];
		gameList = new GameTeam[82];
	}
	//addPlayer method takes in a players name as a parameter and checks if they are already in the player list
	//if they are not in the player list the a new object of type Player is created with their name and team and
	//the list is updated and the newly created player is returned, else if the player is already in the list
	//the player is returned
	public Player addPlayer(String newPlayerName) {
		Player newPlayer = new Player(newPlayerName, teamName);
		if(numPlayers == 0) {
			playerList[0] = newPlayer;
			numPlayers++;
		}
		else if(numPlayers <= 30) {
			boolean inList = false;
			for(int i = 0; i < numPlayers; i++) {
				if(playerList[i].compare(newPlayer)) {
					inList = true;
					return playerList[i];
				}
			}
			if(!inList) {
				playerList[numPlayers] = newPlayer;
				numPlayers++;
			}
		}
		if(numPlayers >= 30) {
			System.out.println("Error, more than 30 players have now played for a team this season");
		}
		return newPlayer;
	}
	//the addGameTeam method takes a GameTeam object as a parameter and adds the game to the teams GameList
	//as well as update all the records
	public void addGameTeam(GameTeam newGame) {
		if(numGames < 82) {
			gameList[numGames] = newGame;
			char WL = newGame.getWL();
			char HA = newGame.getHA();
			if(WL == 'W') {
				teamRecord.incWin();
				if(HA == 'H') {
					homeRecord.incWin();
				}
				else {
					awayRecord.incWin();
				}
			}
			else {
				teamRecord.incLoss();
				if(HA == 'H') {
					homeRecord.incLoss();
				}
				else {
					awayRecord.incLoss();
				}
			}
			
			numGames += 1;
			
			if(numGames <= 10) {
				if(WL == 'W') {
					lastTenGames.incWin();
				}
				else {
					lastTenGames.incLoss();
				}
			}
			else {
				if(gameList[numGames - 11].getWL() == 'W') {
					if(WL == 'L') {
						lastTenGames.decWin();
						lastTenGames.incLoss();
					}
				}
				else {
					if(WL == 'W') {
						lastTenGames.decLoss();
						lastTenGames.incWin();
					}
				}
			}
		}
		else {
			System.out.println("Error, more than 82 games were added to a team, only 82 games are played in the regular season");
		}
	}
	//getter methods for all the data of the team
	public char getConf() {
		return this.confrence;
	}
	
	public char getDiv() {
		return division;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public Record getTeamRecord() {
		return teamRecord;
	}
	//getRecord returns the record of a team with specific filters, the opponent and home or away games
	public Record getRecord(String opp, char HA) {
		Record record = new Record();
		//for loop checks every game in the teams game list and checks if they are alid under the given filters
		for(int i = 0; i < this.getNumGames(); i++) {
			if((this.getGameList()[i].getHA() == HA || HA == 'N') && (this.getGameList()[i].getOpp().equals(opp) || opp.equals("ALL"))) {
				//if the game is valid under the filters it updates the record based on that games results
				if(this.getGameList()[i].getWL() == 'W') {
					record.incWin();
				}
				else {
					record.incLoss();
				}
			}
		}
		return record;
	}
	
	public Record getAwayRecord() {
		return awayRecord;
	}
	
	public Record getHomeRecord() {
		return homeRecord;
	}
	
	public Record getLastTenGames() {
		return lastTenGames;
	}
	
	public Player[] getPlayerList() {
		return playerList;
	}
	
	public GameTeam[] getGameList() {
		return gameList;
	}
	
	public int getNumGames() {
		return numGames;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	//getPPGTeam method returns a teams points per game under the given filters
	public double getPPGTeam(String opp, char HA) {
		//total points scored in games under the given filters
		double total = 0;
		//number of games that are valid under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGames(); i++) {
			if((this.getGameList()[i].getOpp().equals(opp) || opp.equals("ALL")) && (this.getGameList()[i].getHA() == HA || HA == 'N')) {
				//add to the totals
				total = total + this.getGameList()[i].getTeamScore();
				gameCount++;
			}
		}
		//returns the average points per game under the given filters 
		return total/gameCount;
	}
	//getPPGOpp method returns a teams opponents average points a game given the filters
	public double getPPGOpp(String opp, char HA) {
		//total points scored against the team under the given filters
		double total = 0;
		//number of games that are valid under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGames(); i++) {
			if((this.getGameList()[i].getOpp().equals(opp) || opp.equals("ALL")) && (this.getGameList()[i].getHA() == HA || HA == 'N')) {
				//update totals
				total = total + this.getGameList()[i].getOppScore();
				gameCount++;
			}
		}
		//returns the average number of points scored against the team under the given filters
		return total/gameCount;
	}
	//getWLByN method returns the percent of games the team has won by at least n points if n is positive or the percent of games
	//the team has lost by at least n points if n is negitive under the given filters
	public double getWLByN(String opp, char HA, int n) {
		//number of games won or lost by n under the given filters
		double totalByN = 0;
		//number of games the team has played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGames(); i++) {
			if((this.getGameList()[i].getOpp().equals(opp) || opp.equals("ALL")) && (this.getGameList()[i].getHA() == HA || HA == 'N')) {
				//update totals
				if(n < 0 && this.getGameList()[i].getTeamScore() - this.getGameList()[i].getOppScore() <= n) {
					totalByN++;
				}
				if(n > 0 && this.getGameList()[i].getTeamScore() - this.getGameList()[i].getOppScore() >= n) {
					totalByN++;
				}
				gameCount++;
			}
		}
		//return the percent of games won or lost by at least n under the given filters 
		return (totalByN/gameCount)*100;
	}
}
