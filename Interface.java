/*Gregory Danayan
 * gdanaya@uwo.ca
 * This is the league class, it is responsible for creating a league from the information in the text files.
 * It stores a list of the Teams in the league, and the number of teams in the league. It also contains the methods
 * needed to get the current division and conference standings, to get a players stats, a teams stats, the players on
 * a specified team and to change a given stat to a string
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
//Interface class contains the the main method that calls the stat explorer class constructor
public class Interface extends JFrame{
	public static void main(String args[]) throws IOException{
		League nba = new League();
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				new StatExplorer(nba);
			}
		});
	}
}


class StatExplorer extends JFrame{
	//all of the objects are declared
	JFrame jf;
	
	int choosingTeamTeamStats = 0, choosingTeamPlayerStats = 0, teamsDisplayed = 0, choosingTeamFilter = 0;
	
	private static JLabel statDisplay, display, teamDisplay;

	JButton buttonATL, buttonBOS, buttonBKN, buttonCHA, buttonCHI, buttonCLE, buttonDET,
			buttonIND, buttonMIA, buttonMIL, buttonNYK, buttonORL, buttonPHI, buttonTOR,
			buttonWAS, buttonDAL, buttonDEN, buttonGSW, buttonHOU, buttonLAC, buttonLAL,
			buttonMEM, buttonMIN, buttonNOP, buttonOKC, buttonPHX, buttonPOR, buttonSAC,
			buttonSAS, buttonUTA, confStandButton, divStandButton, teamStats, playerStats, 
			filterOpp, removeFilters, filterHome, filterAway;
	JButton[] teamButtonList;
	
	DefaultListModel<String> playerList;
	
	private JList<String> list;
	
	private String selectedTeam, filteredTeam;
	private char filteredHA;
	
	StatExplorer(League nba){
		//the frame, buttons, lists and labels are initialized
		jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("NBA Stat Master");
		
		buttonATL = new JButton(new ImageIcon("ATL.jpg"));
		buttonBOS = new JButton(new ImageIcon("BOS.jpg"));
		buttonBKN = new JButton(new ImageIcon("BKN.jpg"));
		buttonCHA = new JButton(new ImageIcon("CHA.jpg"));
		buttonCHI = new JButton(new ImageIcon("CHI.jpg"));
		buttonCLE = new JButton(new ImageIcon("CLE.jpg"));
		buttonDET = new JButton(new ImageIcon("DET.jpg"));
		buttonIND = new JButton(new ImageIcon("IND.jpg"));
		buttonMIA = new JButton(new ImageIcon("MIA.jpg"));
		buttonMIL = new JButton(new ImageIcon("MIL.jpg"));
		buttonNYK = new JButton(new ImageIcon("NYK.jpg")); 
		buttonORL = new JButton(new ImageIcon("ORL.jpg")); 
		buttonPHI = new JButton(new ImageIcon("PHI.jpg")); 
		buttonTOR = new JButton(new ImageIcon("TOR.jpg"));
		buttonWAS = new JButton(new ImageIcon("WAS.jpg"));
		buttonDAL = new JButton(new ImageIcon("DAL.jpg"));
		buttonDEN = new JButton(new ImageIcon("DEN.jpg"));
		buttonGSW = new JButton(new ImageIcon("GSW.jpg"));
		buttonHOU = new JButton(new ImageIcon("HOU.jpg"));
		buttonLAC = new JButton(new ImageIcon("LAC.jpg"));
		buttonLAL = new JButton(new ImageIcon("LAL.jpg"));
		buttonMEM = new JButton(new ImageIcon("MEM.jpg"));
		buttonMIN = new JButton(new ImageIcon("MIN.jpg"));
		buttonNOP = new JButton(new ImageIcon("NOP.jpg")); 
		buttonOKC = new JButton(new ImageIcon("OKC.jpg")); 
		buttonPHX = new JButton(new ImageIcon("PHX.jpg")); 
		buttonPOR = new JButton(new ImageIcon("POR.jpg"));
		buttonSAC = new JButton(new ImageIcon("SAC.jpg"));
		buttonSAS = new JButton(new ImageIcon("SAS.jpg")); 
		buttonUTA = new JButton(new ImageIcon("UTA.jpg"));
		confStandButton = new JButton("Conference Standings");
		divStandButton = new JButton("Division Standings");
		teamStats = new JButton("Team Stats");
		playerStats = new JButton("Player Stats");
		filterOpp = new JButton("Opponent Filter");
		removeFilters = new JButton("Remove Filters");
		filterHome = new JButton("Home Filter");
		filterAway = new JButton("Away Filter");
		
		display = new JLabel("");
		teamDisplay = new JLabel();
		playerList = new DefaultListModel<String>();
		list = new JList<String>(playerList);
		selectedTeam = "";
		filteredTeam = "ALL";
		filteredHA = 'N';
		//a team button list is creatd for easy access to all the buttons
		teamButtonList = new JButton[] {buttonBOS, buttonBKN, buttonNYK, buttonPHI, buttonTOR, buttonCHI, buttonCLE,
			buttonDET, buttonIND, buttonMIL, buttonATL, buttonCHA, buttonMIA, buttonORL,
			buttonWAS, buttonDEN, buttonMIN, buttonOKC, buttonPOR, buttonUTA, buttonGSW,
			buttonLAC, buttonLAL, buttonPHX, buttonSAC, buttonDAL, buttonHOU, buttonMEM,
			buttonNOP, buttonSAS};
		//add all the buttons to the display and update where on the screen they will be displayed
		jf.add(teamDisplay);
		jf.add(confStandButton);
		jf.add(divStandButton);
		jf.add(teamStats);
		jf.add(playerStats);
		jf.add(list);
		
		jf.add(filterOpp);
        filterOpp.setFont(new Font("Serif", Font.PLAIN, 40));
		filterOpp.setBounds(1420 - filterOpp.getPreferredSize().width*3/2, 0, filterOpp.getPreferredSize().width, filterOpp.getPreferredSize().height);
		filterOpp.setVisible(false);
		
		jf.add(filterHome);
		filterHome.setFont(new Font("Serif", Font.PLAIN, 40));
        filterHome.setBounds(1420 - filterOpp.getPreferredSize().width/2, 0, filterOpp.getPreferredSize().width, filterOpp.getPreferredSize().height);
		filterHome.setVisible(false);
        
		jf.add(filterAway);
		filterAway.setFont(new Font("Serif", Font.PLAIN, 40));
		filterAway.setBounds(1420 + filterOpp.getPreferredSize().width/2, 0, filterOpp.getPreferredSize().width, filterOpp.getPreferredSize().height);
		filterAway.setVisible(false);
		
		jf.add(removeFilters);
		removeFilters.setFont(new Font("Serif", Font.PLAIN, 40));
		removeFilters.setBounds(1420 - filterOpp.getPreferredSize().width*3/2, filterOpp.getPreferredSize().height, filterOpp.getPreferredSize().width*3, removeFilters.getPreferredSize().height);
		removeFilters.setVisible(false);
		
		teamDisplay.setBounds(1420 - teamDisplay.getPreferredSize().width, filterOpp.getPreferredSize().height + removeFilters.getPreferredSize().height, teamDisplay.getPreferredSize().width, teamDisplay.getPreferredSize().height);
		
		jf.add(display);
		
		statDisplay = new JLabel("");
		jf.add(statDisplay);
		//when the conference standings button is clicked the conference standings will be displayed in the statDisplay on the right half of the screen
		confStandButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String result = nba.getConfStandings();
				String c = "";
				//remove all the buttons not needed from the screen and reset all of the filters and current states to their defaults
				c = "<html><pre>" + result + "<pre><html>";
				statDisplay.setText(c);
				statDisplay.setFont(new Font("Serif", Font.PLAIN, 17));
				statDisplay.setBounds(1040, 0, 1000, 900);
				display.setText("");
				choosingTeamTeamStats = 0;
				choosingTeamPlayerStats = 0;
				choosingTeamFilter = 0;
				removeTeamsFromFrame();
				
				teamDisplay.setVisible(false);
				filterHome.setVisible(false);
				filterAway.setVisible(false);
				removeFilters.setVisible(false);
				filterOpp.setVisible(false);
				list.setVisible(false);
				statDisplay.setVisible(true);
			}
			
		});
		//when the division standings button is clicked the division standings will be displayed in the statDisplay on the right half of the screen
		divStandButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String result = nba.getDivStandings();
				String c = "";
				//remove all the buttons not needed from the screen and reset all of the filters and current states to their defaults
				c = "<html><pre>" + result + "<pre><html>";
				statDisplay.setText(c);
				statDisplay.setFont(new Font("Serif", Font.PLAIN, 14));
				statDisplay.setBounds(1116, 0, 1000, 900);
				display.setText("");
				choosingTeamTeamStats = 0;
				choosingTeamPlayerStats = 0;
				choosingTeamFilter = 0;
				removeTeamsFromFrame();
				
				teamDisplay.setVisible(false);
				filterHome.setVisible(false);
				filterAway.setVisible(false);
				removeFilters.setVisible(false);
				filterOpp.setVisible(false);
				list.setVisible(false);
				statDisplay.setVisible(true);
			}
			
		});
		//when the team stats button is clicked all of the filters will be reset to their defaults,
		//the current state of the system will be updated and the team buttons become displayed
		teamStats.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				statDisplay.setVisible(false);
				if(teamsDisplayed == 0) {
					addTeamsFromFrame(jf);
				}
				
				choosingTeamTeamStats = 1;
				choosingTeamPlayerStats = 0;
				choosingTeamFilter = 0;
				//the display is updated to what the user needs to do
				display.setText("Pick the team you would like the stats for");
				display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
				
				teamDisplay.setVisible(false);
				filterHome.setVisible(true);
				filterAway.setVisible(true);
				removeFilters.setVisible(true);
				filterOpp.setVisible(true);
				list.setVisible(false);
			}
		});
		//when the playerStats button is clicked on the filters will be reset to their defaults
		//the current state of the system is updated to choosing the team for player stats,
		//the team buttons are displayed and the display is updated to what the user needs to do
		playerStats.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				statDisplay.setVisible(false);
				if(teamsDisplayed == 0) {
					addTeamsFromFrame(jf);
				}
				
				choosingTeamPlayerStats = 1;
				if(choosingTeamTeamStats == 1) {
					choosingTeamTeamStats = 0;
				}
				display.setText("Pick the team the player is on");
				display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
				
				teamDisplay.setVisible(false);
				choosingTeamFilter = 0;
				list.setVisible(false);
				playerList.clear();
				selectedTeam = "";
				filteredTeam = "ALL";
				filteredHA = 'N';				
			}
		});
		//when the list of players is being used the current player being highlighted has their stats displayed 
		list.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) {
        		
        		String curSelection = list.getSelectedValue();
        		if(curSelection != null) {
        			statDisplay.setText("<html><pre>" + nba.getPlayerStats(selectedTeam, curSelection, filteredTeam, filteredHA) + "<html><pre>");
       			}
       			statDisplay.setFont(new Font("Serif", Font.PLAIN, 30));
       			statDisplay.setBounds(970, 100, 1000, 900);
       			statDisplay.setVisible(true);
       			
        	}
        });
		//when the filter opponent button is clicked on the teams in the league are displayed
		//and the state of the buttons is updated, if the list of players is displayed it is removed from the screen
		//and the display of what to do is updated
		filterOpp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				display.setText("Pick your filtered opponent");
				display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
				choosingTeamFilter = 1;
				list.setVisible(false);
				addTeamsFromFrame(jf);
			}
			
		});
		//when the filter home button is clicked on the home away filter variable is updated
		filterHome.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				filteredHA = 'H';
			}
			
		});
		//when the filter away button is clicked on the home away filter variable is updated
		filterAway.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				filteredHA = 'A';
			}
			
		});
		//when the remove filters button is clicked all of the filters will be reset to their defaults the the titles of the
		//stats being displayed will update
		removeFilters.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				choosingTeamFilter = 0;
				filteredTeam = "ALL";
				filteredHA = 'N';
				if(choosingTeamPlayerStats == 1) {
					display.setText("Player stats");
				}
				else if(choosingTeamTeamStats == 1) {
					display.setText("Pick the team you would like the stats for");
				}
				display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
			}
			
		});
		//Create every teams button and add an action Listener,
		//each teams actionListener calls the same method, but the method acts differently
		//based on the team being passed through as a parameter and the current filters that
		//are applied
		buttonATL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "ATL");
			}
		}); 
		buttonBOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "BOS");
			}
		}); 
		buttonBKN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "BKN");
			}
		}); 
		buttonCHA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "CHA");
			}
		}); 
		buttonCHI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "CHI");
			}
		}); 
		buttonCLE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "CLE");
			}
		}); 
		buttonDET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "DET");
			}
		});
		buttonIND.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "IND");
			}
		}); 
		buttonMIA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "MIA");
			}
		}); 
		buttonMIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "MIL");
			}
		}); 
		buttonNYK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "NYK");
			}
		}); 
		buttonORL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "ORL");
			}
		}); 
		buttonPHI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "PHI");
			}
		}); 
		buttonTOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "TOR");
			}
		});
		buttonWAS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "WAS");
			}
		}); 
		buttonDAL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "DAL");
			}
		}); 
		buttonDEN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "DEN");
			}
		}); 
		buttonGSW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "GSW");
			}
		}); 
		buttonHOU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "HOU");
			}
		}); 
		buttonLAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "LAC");
			}
		}); 
		buttonLAL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "LAL");
			}
		});
		buttonMEM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "MEM");
			}
		}); 
		buttonMIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "MIN");
			}
		}); 
		buttonNOP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "NOP");
			}
		}); 
		buttonOKC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "OKC");
			}
		}); 
		buttonPHX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "PHX");
			}
		}); 
		buttonPOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "POR");
			}
		}); 
		buttonSAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "SAC");
			}
		});
		buttonSAS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "SAS");
			}
		}); 
		buttonUTA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamButtonAction(nba, "UTA");
			}
		});
		//set the fonts and positions of the buttons
		jf.setLayout(null);
		confStandButton.setFont(new Font("Serif", Font.PLAIN, 40));
		divStandButton.setFont(new Font("Serif", Font.PLAIN, 40));
		teamStats.setFont(new Font("Serif", Font.PLAIN, 40));
		playerStats.setFont(new Font("Serif", Font.PLAIN, 40));
		display.setFont(new Font("Serif", Font.PLAIN, 40));
		
		confStandButton.setBounds(460 - confStandButton.getPreferredSize().width, 0, confStandButton.getPreferredSize().width, confStandButton.getPreferredSize().height);
		divStandButton.setBounds(460, 0, confStandButton.getPreferredSize().width, confStandButton.getPreferredSize().height);
		teamStats.setBounds(460 - confStandButton.getPreferredSize().width, confStandButton.getPreferredSize().height, confStandButton.getPreferredSize().width, teamStats.getPreferredSize().height);
		playerStats.setBounds(460, confStandButton.getPreferredSize().height, confStandButton.getPreferredSize().width, teamStats.getPreferredSize().height);
		display.setBounds(460 - 156, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
		
		jf.setSize(1920,900);
		jf.setVisible(true);
	}
	//the addTeamsFromFrame method, changesthe visibility of all the teams buttons to true
	private void addTeamsFromFrame(JFrame jf) {
		for(int i = 0; i < teamButtonList.length; i++) {
			jf.add(teamButtonList[i]);
		}
		int k = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 5; j ++) {
				teamButtonList[k].setBounds(184*j, 200+ 110*i, 184, 110);
				teamButtonList[k].setVisible(true);
				k++;
			}
		}
		//and updates the teamsDisplayed variable
		teamsDisplayed = 1;
	}
	//the removeTeamsFromFrame method, changes the visibility of all the teams buttons to false
	private void removeTeamsFromFrame() {
		int k = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 5; j ++) {
				teamButtonList[k].setVisible(false);
				k++;
			}
		}
		//and updates the teamsDisplayed variable
		teamsDisplayed = 0;
	}
	//the displayPlayerList method displayed the teams player list of the current team the user wishes to know the stats of their players
	private void displayPlayerList(League nba, String titleText) {
		//this updates the display title to the stats currently being displayed and removes the team byttons from the display
		display.setText(titleText);
		display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
		removeTeamsFromFrame();
		//update the list of players being displayed
		String[] result = nba.getTeamsPlayers(selectedTeam);
        for(int i = 0; i < result.length; i ++) {
        	playerList.addElement(result[i]);
        }
        //display all of the filter buttons
        filterOpp.setVisible(true);				
        filterHome.setVisible(true);
        filterAway.setVisible(true);
        removeFilters.setVisible(true);
        //display the updated list
        list.setVisible(true);
        list.setBounds(230, 200, 460, 700);
        list.setFont(new Font("Serif", Font.PLAIN, 22));
	}
	//the displayTeamStats method takes in the team and league as parameters, then puts the teams
	//stats under the filters on the statDisplay
	private void displayTeamStats(String team, League nba) {
		statDisplay.setText("<html><pre>" + nba.getTeamStats(team, filteredTeam, filteredHA) + "<html><pre>");
		statDisplay.setFont(new Font("Serif", Font.PLAIN, 25));
		statDisplay.setBounds(970, 200, 1000, 700);
		statDisplay.setVisible(true);
	}
	//the teamButtonAction method is the method called each time a teams button is clicked on
	//this method does different things based on the state the program is in
	private void teamButtonAction(League nba, String buttonClicked) {
		//if you have previously clicked on the team stats button your program will
		//go into this if statement
		if(choosingTeamTeamStats == 1) {
			//if you are choosing the team you wish to see the stats of you will proceed into this if statement
			if(choosingTeamFilter == 0) {
				selectedTeam = buttonClicked;
				displayTeamStats(selectedTeam, nba);
			}
			//otherwise you have clicked on the opponent filter button, this means you are seeing the team buttons
			//because you are trying to apply a opponent filter and the program wants to know what team you want to see
			//other teams stats against
			else {
				//update the filtered team and change the display name to the name of the current stats
				//being displayed
				filteredTeam = buttonClicked;
				String homeAway = "";
				if(filteredHA == 'H') {
					homeAway = " at home";
				}
				else if(filteredHA == 'A') {
					homeAway = " away from home";
				}
				display.setText(selectedTeam + " Stats VS. " + filteredTeam + homeAway);
				choosingTeamFilter = 0;
				//call the display team stats method that will update the statsDisplay
				displayTeamStats(selectedTeam, nba);
			}
		}
		//similarly if you have previously clicked on the Player Stats button your program will proceed here
		else if(choosingTeamPlayerStats == 1){
			list.setVisible(true);
			removeTeamsFromFrame();
			//if you are looking for a teams player stats under the current filters the program will then display the list of players on the team
			if(choosingTeamFilter == 0) {
				selectedTeam = buttonClicked;
				displayPlayerList(nba, "Player stats");
			}
			//other wise the opponent filter for player stats was being applied so update the opponent filter to the team that was just clicked on
			else {
				filteredTeam = buttonClicked;
				display.setText(selectedTeam + " Player stats VS. " + buttonClicked);
        		display.setBounds(460 - display.getPreferredSize().width/2, 200 - display.getPreferredSize().height, display.getPreferredSize().width, display.getPreferredSize().height);
			}
		}
		//display the teams logo of the player or teams stats being displayed
		teamDisplay.setIcon(new ImageIcon(selectedTeam + ".jpg"));
		teamDisplay.setVisible(true);
		teamDisplay.setBounds(1495 - teamDisplay.getPreferredSize().width, filterOpp.getPreferredSize().height + removeFilters.getPreferredSize().height, teamDisplay.getPreferredSize().width, teamDisplay.getPreferredSize().height);
	}

}