/*Gregory Danayan
 * gdanaya@uwo.ca
 * This is the league class, it is responsible for creating a league from the information in the text files.
 * It stores a list of the Teams in the league, and the number of teams in the league. It also contains the methods
 * needed to get the current division and conference standings, to get a players stats, a teams stats, the players on
 * a specified team and to change a given stat to a string
 */
import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileReader;
import java.io.*;
public class League {
	private Team[] league = null;
	int numTeams = 0;
	League() throws IOException{
		//The league constructor reads from the division text file first, this creates all the teams and inputs what division
		//and conference the team is in
		if(league == null){
			//create a list of Team objects of length 30, the number of teams in the NBA
			league = new Team[30];
			File divisionFile = new File("Divisions.txt");
			BufferedReader br = new BufferedReader(new FileReader(divisionFile)); 
			//read from the division text file and initialize all the teams
			String st; 
			while ((st = br.readLine()) != null) { 
				  String[] curTeam;
				  curTeam = st.split("\t");
				  if(numTeams < 30) {
					  league[numTeams] = new Team(curTeam[0], curTeam[1].charAt(0), curTeam[2].charAt(0));
					  numTeams++;
				  }
			} 
			br.close();
			//read from the teamList.txt file to add and create all the GameTeam objects to the correspoinding Team and update all the teams stats
			File teamListFile = new File("TeamList.txt");
			br = new BufferedReader(new FileReader(teamListFile));
			
			st = br.readLine();
			while((st = br.readLine()) != null){
				String[] curGame;
				curGame = st.split("\t");
				//correctly format the date the game is played to YYYYMMDD
				String[] curGameDate = curGame[2].split("/");
				int date = Integer.parseInt(curGameDate[2])*10000 + Integer.parseInt(curGameDate[0])*100 + Integer.parseInt(curGameDate[1]);
				GameTeam newGame = new GameTeam(curGame[0], curGame[1], date, curGame[3].charAt(0), Integer.parseInt(curGame[4]), Integer.parseInt(curGame[5]));
				boolean inserted = false;
				int i = 0;
				//find the team in the league that the newly created game corresponds to and add it to their gameList
				while(!inserted && i < numTeams) {
					if(league[i].getTeamName().equals(curGame[0])){
						league[i].addGameTeam(newGame);
						inserted = true;
					}
					i++;
				}
			}
			br.close();
			//read from the PlayerList.txt file to create the player if they have yet to play a game yet and to add the newly created
			//GamePlayer object to their gameList
			File playerListFile = new File("PlayerList.txt");
			br = new BufferedReader(new FileReader(playerListFile));
			
			st = br.readLine();
			while((st = br.readLine()) != null) {
				String[] curPlayer;
				curPlayer = st.split("\t");
				boolean playerAdded = false;
				int i = 0;
				while(!playerAdded && i < numTeams) {
					//find the team the player plays for
					if(league[i].getTeamName().equals(curPlayer[1])) {
						//add the player the team plays for to the team, this returns the player if they are already on the team
						//if they are not it creates a new player and returns it
						Player thisPlayer = league[i].addPlayer(curPlayer[0]);
						//correctly format the date the game was played to YYYYMMDD
						String[] curPlayerDate = curPlayer[3].split("/");
						int date = Integer.parseInt(curPlayerDate[2])*10000 + Integer.parseInt(curPlayerDate[0])*100 + Integer.parseInt(curPlayerDate[1]);
						//add the game to the players ameList
						thisPlayer.addGamePlayer(new GamePlayer(curPlayer[0], curPlayer[1], curPlayer[2], date, curPlayer[4].charAt(0), Double.parseDouble(curPlayer[5]), Integer.parseInt(curPlayer[6]), Integer.parseInt(curPlayer[7]), Integer.parseInt(curPlayer[8]), Integer.parseInt(curPlayer[9]), Integer.parseInt(curPlayer[10]), Integer.parseInt(curPlayer[11]), Integer.parseInt(curPlayer[12]), Integer.parseInt(curPlayer[13]),Integer.parseInt(curPlayer[14]), Integer.parseInt(curPlayer[15]), Integer.parseInt(curPlayer[16]), Integer.parseInt(curPlayer[17])));
						playerAdded = true;
						//add the players name to the teams list of players who played in that game
						//this will be used in future updates where there will be an additional filter that you can
						//chose to look at a teams stats with or without certain players playing
						for(int j = 0; j < league[i].getNumGames(); j++) {
							if(league[i].getGameList()[j].getDate() == date) {
								league[i].getGameList()[j].addPlayer(thisPlayer.getPlayerName());
							}
						}
					}
					i++;
				}
			}
		}
	}
	//the getCondStandings method returns a string of the current conference standings
	public String getConfStandings() {
		String result = "\t\t\tEastern Conference Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
		//create a temporary list of teams so the order of the original list is not tampered with
		Team[] tempLeague = new Team[30];
		for(int i = 0; i < 30; i++) {
			tempLeague[i] = league[i];
		}
		Team temp;
		double gamesBack = 0;
		for(int i = 0; i < 30; i++) {
			for(int j = i + 1; j % 15 != 0; j++) {
				//sort the teams based on their conferences
				if(tempLeague[i].getTeamRecord().compare(tempLeague[j].getTeamRecord()) < 0) {
					temp = tempLeague[i];
					tempLeague[i] = tempLeague[j];
					tempLeague[j] = temp;
				}
			}
		}
		//format and create the string being returned
		for(int i = 0; i < 30; i++) {
			if(i % 15 == 0) {
				gamesBack = 0;
			}
			else {
				gamesBack += (tempLeague[i-1].getTeamRecord().getWin() - tempLeague[i].getTeamRecord().getWin())*(0.5) - (tempLeague[i-1].getTeamRecord().getLoss() - tempLeague[i].getTeamRecord().getLoss())*(0.5);
			}
			Double wp = tempLeague[i].getTeamRecord().winPerct();
			String swp = wp.toString();
			if(swp.length() > 5) {
				swp = swp.substring(0, 4);
			}
			//add the teams stats needed for the standings
			result = result + (i%15 + 1) + ".\t" + tempLeague[i].getTeamName() + "\t" + gamesBack + "\t" + swp + "\t" +tempLeague[i].getTeamRecord().toString() + "\t" + tempLeague[i].getHomeRecord().toString() + "\t" + tempLeague[i].getAwayRecord().toString() + "\t" + tempLeague[i].getLastTenGames().toString() + "\t" + tempLeague[i].getTeamRecord().streakToString() + "\n";
			if(i == 14) {
				result = result + "\n" + "\t\t\tWestern Conference Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
		}
		return result;
	}
	//the method getDivStandings does not take any parameters and creates the standings of teams in their divisions
	public String getDivStandings() {
		String result = "";
		//create a temporary league so you do not edit the order of the original list
		Team[] tempLeague = new Team[30];
		for(int i = 0; i < 30; i++) {
			tempLeague[i] = league[i];
		}
		Team temp;
		double gamesBack = 0;
		for(int i = 0; i < 30; i++) {
			//each division has 5 teams so order the teams rankings in the list from best to worst for every 5 indexes
			for(int j = i + 1; j % 5 != 0; j++) {
				if(tempLeague[i].getTeamRecord().compare(tempLeague[j].getTeamRecord()) < 0) {
					temp = tempLeague[i];
					tempLeague[i] = tempLeague[j];
					tempLeague[j] = temp;
				}
			}
		}
		//format and create the string being returned to the correct format
		for(int i = 0; i < 30; i++) {
			if(i == 0) {
				result = result + "\n" + "\t\t\tAtlantic Dvision Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			else if(i == 5) {
				result = result + "\t\t\tCentral Division Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			else if(i == 10) {
				result = result + "\t\t\tSoutheast Division Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			else if(i == 15) {
				result = result + "\t\t\tNorthwest Division Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			else if(i == 20) {
				result = result + "\t\t\tPacific Division Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			else if(i == 25) {
				result = result + "\t\t\tSouthwest Division Standings\n\tTeam\tGB\tW%\tW-L\tHome\tAway\tLast10\tStreak\n";
			}
			if(i % 5 == 0) {
				gamesBack = 0;
			}
			else {
				gamesBack += (tempLeague[i-1].getTeamRecord().getWin() - tempLeague[i].getTeamRecord().getWin())*(0.5) - (tempLeague[i-1].getTeamRecord().getLoss() - tempLeague[i].getTeamRecord().getLoss())*(0.5);
			}
			Double wp = tempLeague[i].getTeamRecord().winPerct();
			String swp = wp.toString();
			if(swp.length() > 5) {
				swp = swp.substring(0, 4);
			}
			//add the teams stats in the correct order to be displayed
			result = result + (i%5 + 1) + ".\t" + tempLeague[i].getTeamName() + "\t" + gamesBack + "\t" + swp + "\t" +tempLeague[i].getTeamRecord().toString() + "\t" + tempLeague[i].getHomeRecord().toString() + "\t" + tempLeague[i].getAwayRecord().toString() + "\t" + tempLeague[i].getLastTenGames().toString() + "\t" + tempLeague[i].getTeamRecord().streakToString() + "\n";
		}
		return result;
	}
	//method getTeamsPlayers takes a team name as a parameters and returns a list of Objects of type String with all the players who have 
	//played for the team this season
	public String[] getTeamsPlayers(String teamName) {
		boolean teamFound = false;
		int i = 0;
		//find the team specified
		while(!teamFound && i < 30) {
			if(league[i].getTeamName().equals(teamName)) {
				teamFound = true;
			}
			else {
				i++;
			}
		}
		String[] result = new String[league[i].getNumPlayers()];
		for(int j = 0; j < league[i].getNumPlayers(); j++) {
			result[j] = league[i].getPlayerList()[j].getPlayerName();
		}
		return result;
	}
	//the getTeamStats method takes a team and filters as parameters and returns the teams stats
	public String getTeamStats(String team, String opp, char HA) {
		boolean teamFound = false;
		int i = 0;
		String result = "\t\t" + team + " team stats:\n";
		//find the team in the list of teams
		while(!teamFound && i < 30) {
			if(league[i].getTeamName().equals(team)) {
				teamFound = true;
			}
			else {
				i++;
			}
		}
		if(teamFound) {
			//get the teams stats and convert them to strings
			String sPPG = statToString(league[i].getPPGTeam(opp, HA));
			String sOPPG = statToString(league[i].getPPGOpp(opp, HA));
			String sWP11P = statToString(league[i].getWLByN(opp, HA, 11)) + "%";
			String sWP6P = statToString(league[i].getWLByN(opp, HA, 6)) + "%";
			String sWP1T5P = statToString(league[i].getWLByN(opp, HA, 1) - league[i].getWLByN(opp, HA, 6)) + "%";
			String sLP1T5P = statToString(league[i].getWLByN(opp, HA, -1) - league[i].getWLByN(opp, HA, -6)) + "%";
			String sLP6P = statToString(league[i].getWLByN(opp, HA, -6)) + "%";
			String sLP11P = statToString(league[i].getWLByN(opp, HA, -11)) + "%";
			
			if(league[i].getRecord(opp, HA).getWin() == 0 && league[i].getRecord(opp, HA).getLoss() == 0) {
				return "These teams have yet to face each other";
			}
			//create a string to return with all the teams stats formatted correctly 
			result = result + "\tRecord:\t" + league[i].getRecord(opp, HA).toString() + "\nPPG:\t" + sPPG + "\t\tOPPG:\t" + sOPPG + "\nW%11+:\t" + sWP11P + "\t\tW%6+:\t" + sWP6P + "\nW%1-5:\t" + sWP1T5P + "\t\tL%1-5:\t" + sLP1T5P + "\nL%6+:\t" + sLP6P + "\t\tL%11+:\t" + sLP11P + "\nStreak:\t" + league[i].getRecord(opp, HA).streakToString();
		}
		else {
			return "Team not found";
		}
		return result;
	}
	//the method getPlayerStats takes in a team, player and filters as parameters, then returns a string of a players stats
	public String getPlayerStats(String team, String player, String opp, char HA) {
		String result = player + " player stats:\n";
		boolean teamFound = false;
		int i = 0;
		//find the team the player is on in the list of teams
		while(!teamFound && i < 30) {
			if(league[i].getTeamName().equals(team)) {
				teamFound = true;
			}
			else {
				i++;
			}
		}
		if(teamFound) {
			
			boolean playerFound = false;
			int j = 0;
			//find the player in the list of the teams players
			while(!playerFound && j < league[i].getNumPlayers()) {
				if(league[i].getPlayerList()[j].getPlayerName().equals(player)) {
					playerFound = true;
				}
				else {
					j++;
				}
			}
			if(playerFound) {
				//once the player is found, get all the players stats and convert them to strings
				String sppg = statToString(league[i].getPlayerList()[j].getPPG(opp, HA));
				String spp36 = statToString(league[i].getPlayerList()[j].getPP36(opp, HA));
				String srpg = statToString(league[i].getPlayerList()[j].getReboundsPG(opp, HA));
				String srp36 = statToString(league[i].getPlayerList()[j].getReboundsP36(opp, HA));
				String sapg = statToString(league[i].getPlayerList()[j].getAssistsPG(opp, HA));
				String sap36 = statToString(league[i].getPlayerList()[j].getAssistsP36(opp, HA));
				String sbpg = statToString(league[i].getPlayerList()[j].getBlocksPG(opp, HA));
				String sbp36 = statToString(league[i].getPlayerList()[j].getBlocksP36(opp, HA));
				String sMinutesPG = statToString(league[i].getPlayerList()[j].getMinutesPG(opp, HA));
				String sPlusMinusPG = statToString(league[i].getPlayerList()[j].getPMPG(opp, HA));
				String sPercentPMP = statToString(league[i].getPlayerList()[j].getPercentPMP(opp, HA)) + "%";
				String sThreePPer = statToString(league[i].getPlayerList()[j].getThreePPer(opp, HA)) + "%";
				String sThreePPG = statToString(league[i].getPlayerList()[j].getThreePPG(opp, HA));
				String sFieldGPer = statToString(league[i].getPlayerList()[j].getFieldGPer(opp, HA)) + "%";
				String sFieldGPG = statToString(league[i].getPlayerList()[j].getFieldGPG(opp, HA));
				String sFreeTPer = statToString(league[i].getPlayerList()[j].getFreeTPer(opp, HA)) + "%";
				String sFreeTPG = statToString(league[i].getPlayerList()[j].getFreeTPG(opp, HA));
				
				if(league[i].getRecord(opp, HA).getWin() == 0 && league[i].getRecord(opp, HA).getLoss() == 0) {
					return "These teams have yet to face each other";
				}
				else if(league[i].getPlayerList()[j].getPlayerRecord(opp, HA).getWin() == 0 && league[i].getPlayerList()[j].getPlayerRecord(opp, HA).getLoss() == 0) {
					return "This player has yet to play against: " + opp;
				}
				//create a string to return formated correctly to be displayed
				result = result + "\nTeams Record:\t" + league[i].getRecord(opp, HA).toString() + "\tPlayers Record:\t" + league[i].getPlayerList()[j].getPlayerRecord(opp, HA).toString() + "\nPPG:\t" + sppg + "\t\tPP36:\t" + spp36 + "\nRPG:\t" + srpg + "\t\tRP36:\t" + srp36 + "\nAPG:\t" + sapg + "\t\tAP36:\t" + sap36 + "\nBPG:\t" + sbpg + "\t\tBP36:\t" + sbp36 + "\nMinPG:\t" + sMinutesPG + "\t\t+/-:\t" + sPlusMinusPG + "\nFG PG:\t" + sFieldGPG + "\t\tFG%:\t" + sFieldGPer + "\n3PT PG:\t" + sThreePPG + "\t\t3PT%:\t" + sThreePPer + "\nFT PG:\t" + sFreeTPG + "\t\tFT%:\t" + sFreeTPer + "\n(+)+/-:\t" + sPercentPMP;
			}
			else {
				return "Player not Found";
			}
				
			return result;
		}
		else {
			return "Team not found";
		}
	}
	//the statToString method takes in a double as a parameter and rounds it to the nearest hundredth then returns the rounded
	//number as a string
	private String statToString(Double stat) {
		DecimalFormat df1 = new DecimalFormat("0.##");
		return df1.format(stat);
	}
}
