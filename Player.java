/*Gregory Danayan
 * gdanaya@uwo.ca
 * Player class represents a player, each player has a name, a team they play for, a list of the games they have played in and the number of games
 * they have played in
 */
public class Player {
	
	private String playerName;
	private String teamName;
	private GamePlayer[] gameList;
	private int numGamesPlayed;
	
	Player(String player, String team){
		//initialize the variables
		this.playerName = player;
		this.teamName = team;
		numGamesPlayed = 0;
		gameList = new GamePlayer[82];
	}
	//the getPlayerRecord class returns a players record under the given filters
	public Record getPlayerRecord(String opp, char HA) {
		Record playerRecord = new Record();
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				if(this.getGamesList()[i].getResult() == 'W')
					playerRecord.incWin();
				else {
					playerRecord.incLoss();
				}
			}
		}
		return playerRecord;
	}
	//the addGamePlayer method adds games to the players gamelist and increments the game count
	public void addGamePlayer(GamePlayer game){
		if(numGamesPlayed >= 82) {
			System.out.println("Error, more than 82 games were added to a players game list");
		}
		gameList[numGamesPlayed] = game;
		numGamesPlayed += 1;
	}
	//getter methods for a players data
	public int getNumGamesPlayed() {
		return this.numGamesPlayed;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public GamePlayer[] getGamesList() {
		return gameList;
	}
	//compare method compare a players name to another players name, this is used when added players to a team
	//in order to make sure no duplicates are created. returns true if they have the same name and false otherwise
	public boolean compare(Player otherPlayer) {
		if(this.playerName.equals(otherPlayer.getPlayerName()) && this.teamName.equals(otherPlayer.getTeamName())) {
			return true;
		}
		else {
			return false;
		}
	}
	//getPPG returns the players points per game under the given filters
	public double getPPG(String opp, char HA) {
		//total number of points the player has scored under the given filters
		double totalPoints = 0;
		//total number of games the player has played in under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalPoints += this.getGamesList()[i].getPoints();
				gameCount++;
			}
		}
		//returns the average points per game of the player under the given filters
		return totalPoints/gameCount;
	}
	//getPP36 returns the players points per 36 minutes played given the given filters
	//a players stats per 36 minutes played is important when comparing players efficiencies when the players
	//have different minutes per game averages
	public double getPP36(String opp, char HA) {
		//total number of points scored by the player under the given filters
		double totalPoints = 0;
		//total number of minutes played by the player under the given filters
		double totalMinutes = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalPoints = totalPoints + this.getGamesList()[i].getPoints();
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
			}
		}
		//return the players average number of points per 3 minutes
		return totalPoints/(totalMinutes/36);
	}
	//the getMinutesPG method returns a players average minutes per game
	public double getMinutesPG(String opp, char HA) {
		//total number of minutes played under the given filters
		double totalMinutes = 0;
		//total number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
				gameCount++;
			}
		}
		//return the average number of minutes a game under the given filters
		return totalMinutes/gameCount;
	}
	//the getReboundsPG method returns a players average number of rebounds per game under the given filters
	public double getReboundsPG(String opp, char HA) {
		//total number of rebounds under the given filters
		double totalRebounds = 0;
		//total number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalRebounds = totalRebounds + this.getGamesList()[i].getRebounds();
				gameCount++;
			}
		}
		//return the average number of rebounds per game
		return totalRebounds/gameCount;
	}
	//returns the average number of rebounds a player has per 36 minutes of play
	public double getReboundsP36(String opp, char HA) {
		//total number of rebounds under the given filters
		double totalRebounds = 0;
		//total minutes played under the given filters
		double totalMinutes = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalRebounds = totalRebounds + this.getGamesList()[i].getRebounds();
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
			}
		}
		return totalRebounds/(totalMinutes/36);
	}
	//the getAssistsPG method returns a players average number of Assists per game under the given filters
	public double getAssistsPG(String opp, char HA) {
		//total number of Assists under the given filters
		double totalAssists = 0;
		//total games played in under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalAssists = totalAssists + this.getGamesList()[i].getAssists();
				gameCount++;
			}
		}
		//return the average number of assists per game under the given filters
		return totalAssists/gameCount;
	}
	//getAssistsP36 returns a players average number of assists per 36 minutes of play
	public double getAssistsP36(String opp, char HA) {
		//total number of assists under the given filters
		double totalAssists = 0;
		//total number of minutes played under the given filters
		double totalMinutes = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalAssists = totalAssists + this.getGamesList()[i].getAssists();
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
			}
		}
		//return the average number of assists per 36 minutes of play
		return totalAssists/(totalMinutes/36);
	}
	//the getBlocksPG method returns a players average number of blocks per game under the given filters
	public double getBlocksPG(String opp, char HA) {
		//total number of blocks under the given filters
		double totalBlocks = 0;
		//total number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalBlocks = totalBlocks + this.getGamesList()[i].getBlocks();
				gameCount++;
			}
		}
		//return average number of blocks per game under the given filters
		return totalBlocks/gameCount;
	}
	//the getBlocksP36 returns the average number of blocks per 36 minutes of play
	public double getBlocksP36(String opp, char HA) {
		//total blocks under the given filters
		double totalBlocks = 0;
		//total minutes played under the given filters
		double totalMinutes = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalBlocks = totalBlocks + this.getGamesList()[i].getBlocks();
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
			}
		}
		//return the average number of blocks per 36 minutes of play
		return totalBlocks/(totalMinutes/36);
	}
	//the getStealsPG method returns a players average number of steals per game under the given filters
	public double getStealsPG(String opp, char HA) {
		//total number of steals under the given filters
		double totalSteals = 0;
		//total number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalSteals = totalSteals + this.getGamesList()[i].getSteals();
				gameCount++;
			}
		}
		//return the average number of steals per game under the given filters
		return totalSteals/gameCount;
	}
	//returns the average number of steals per 36 minutes of play under the given filters
	public double getStealsP36(String opp, char HA) {
		//total number of steals under the given filters
		double totalSteals = 0;
		//total number of minutes played under the given filters
		double totalMinutes = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalSteals = totalSteals + this.getGamesList()[i].getSteals();
				totalMinutes = totalMinutes + this.getGamesList()[i].getMinutes();
			}
		}
		//return the average number of steals per 36 minutes of play under the given filters
		return totalSteals/(totalMinutes/36);
	}
	//returns the average plus minus of a player per game under the given filters
	//a plus minus of a player represents the number of points the team scored more than the opponent (or less if it is negative) in that game
	//while the player was on the court
	public double getPMPG(String opp, char HA) {
		//total of the players plus minus through all the games given the filters
		double totalPM = 0;
		//total number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalPM = totalPM + this.getGamesList()[i].getPlusMinus();
				gameCount++;
			}
		}
		//return the average plus minus under the given filters
		return totalPM/gameCount;
	}
	
	//returns the percent of games the player has with a positive plus minus under the given filters
	public double getPercentPMP(String opp, char HA) {
		//total number of games the player has had with a positive plus minus under the given filters
		double count = 0;
		//number of games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				if(this.getGamesList()[i].getPlusMinus() > 0) {
					count = count + 1;
				}
				gameCount++;
			}
		}
		//return the percent of games with a positive plus minus
		return (count/gameCount)*100;
	}
	//getFieldGPer returns a players field goal percentage under the given filters
	public double getFieldGPer(String opp, char HA) {
		//total number of field goals made under the given filters
		double fieldGMade = 0;
		//total number of field goals attempted under the given filters
		double fieldGAtmpt = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				fieldGMade = fieldGMade + this.getGamesList()[i].getFieldGM();
				fieldGAtmpt = fieldGAtmpt + this.getGamesList()[i].getFieldGA();
			}
		}
		//if no field goals have been attempted return 0
		if(fieldGAtmpt == 0) {
			return 0;
		}
		//other wise return the field goal percentage 
		return (fieldGMade/fieldGAtmpt)*100;
	}
	//the getFieldGPG method returns the average number of field goals a player makes a game under the given filters
	public double getFieldGPG(String opp, char HA) {
		//total number of field goals made under the given filters
		double totalFGM = 0;
		//total games played under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalFGM = totalFGM + this.getGamesList()[i].getFieldGM();
				gameCount++;
			}
		}
		//return field goals per game under the given filters
		return totalFGM/gameCount;
	}
	//the getThreePPer returns a players three pointers percentage under the given filters
	public double getThreePPer(String opp, char HA) {
		//total number of Three Pointers made
		double threePMade = 0;
		//total number of Three Pointers attempted
		double threePAtmpt = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				threePMade = threePMade + this.getGamesList()[i].getThreePM();
				threePAtmpt = threePAtmpt + this.getGamesList()[i].getThreePA();
			}
		}
		//if no three pointers were attempted return 0
		if(threePAtmpt == 0) {
			return 0;
		}
		//otherwise return the three point percentage
		return (threePMade/threePAtmpt)*100;
	}
	//the getThreePPG method returns a players average number of Three pointers per game under the given filters
	public double getThreePPG(String opp, char HA) {
		//total number of three pointers made under the given filters
		double totalTPM = 0;
		//total number of games played in under the given filters
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalTPM = totalTPM + this.getGamesList()[i].getThreePM();
				gameCount++;
			}
		}
		//return average number of three pointers made per game under the given filters
		return totalTPM/gameCount;
	}
	//the getFreeTPer method returns a players free throw percentage under the given filters
	public double getFreeTPer(String opp, char HA) {
		//total number of free throws made under the given filters
		double freeTMade = 0;
		//total number of free throws attempted under the given filters
		double freeTAtmpt = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				freeTMade = freeTMade + this.getGamesList()[i].getFreeTM();
				freeTAtmpt = freeTAtmpt + this.getGamesList()[i].getFreeTA();
			}
		}
		//if the player has yet to attempt a free throw return 0
		if(freeTAtmpt == 0) {
			return 0;
		}
		//return the players free throw percentage under the given filters
		return (freeTMade/freeTAtmpt)*100;
	}
	//the getFreeTPG method returns a players average number of free throws made per game under the given filters
	public double getFreeTPG(String opp, char HA) {
		//total number of free throws made under the given filters
		double totalFreeTM = 0;
		//total number of games played under the given filter
		double gameCount = 0;
		for(int i = 0; i < this.getNumGamesPlayed(); i++) {
			if((this.getGamesList()[i].getHomeAway() == HA || HA == 'N') && (this.getGamesList()[i].getOpponent().equals(opp) || opp.equals("ALL"))) {
				//update totals
				totalFreeTM = totalFreeTM + this.getGamesList()[i].getFreeTM();
				gameCount++;
			}
		}
		//return average free throws made per game under the given filters
		return totalFreeTM/gameCount;
	}
	
}
