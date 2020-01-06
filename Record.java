/*Gregory Danayan
 * gdanaya@uwo.ca
 * This is the Record class, it is responsible for keeping track of wins, losses, and the current winning or losing streak
 * Objects of type Record are in the Team and Player class
 */
public class Record {
	private int win = 0;
	private int loss = 0;
	private int streak = 0;
	//the incWin() method is responsible for incrementing the records win count and checking if the win being added
	//ends a losing streak or extends a winning streak
	public void incWin() {
		win++;
		if(streak >= 0) {
			streak++;
		}
		else {
			streak = 1;
		}
	}
	//the incLoss() method s responsible for incrementing the records loss count and checking if the loss being added
	//ends a winning streak or extends a lossing streak
	public void incLoss() {
		loss++;
		if(streak <= 0) {
			streak--;
		}
		else {
			streak = -1;
		}
	}
	//The decrease win function is only used when dealing with a teams last 10 games record
	//this is because in a teams last 10 games record the sum of wins and losses must be less
	//than or equal to 10
	public void decWin() {
		win--;
	}
	//The decrease loss function is only used when dealing with a teams last 10 games record
	//this is because in a teams last 10 games record the sum of wins and losses must be less
	//than or equal to 10
	public void decLoss() {
		loss--;
	}
	//The compare method takes in another record, opp, as a parameter, this method returns 1 if the record is better than 
	//opp, 0 if they are equal and -1 if opp has a better record. This is used when building the current standings of the league
	public int compare(Record opp) {
		float totalGames = this.win + this.loss;
		float totalOppGames = opp.getWin() + opp.getLoss();
		float comp = ((float)this.win)/totalGames - ((float)opp.getWin())/totalOppGames;
		if(comp > 0) {
			return 1;
		}
		else if(comp < 0) {
			return -1;
		}
		else {
			if(this.loss < opp.getLoss()) {
				return 1;
			}
			else if(this.loss > opp.getLoss()) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}
	//the toString() method is used to display a record as a string, this is used for displaying a teams different records when showing the current standings
	public String toString(){
		String result = this.win + "-" + this.loss;
		return result;
	}
	//getWin() returns the number of wins of a record
	public int getWin() {
		return win;
	}
	//getLoss() returns the number of losses of a record
	public int getLoss() {
		return loss;
	}
	//getStreak() returns a records current streak
	public int getStreak() {
		return streak;
	}
	//streakToString() returns the records current streak as a string nicely, this is used when displaying the current standings
	public String streakToString() {
		if(this.streak > 0) {
			return "Won " + streak;
		}
		else if(this.streak < 0) {
			return "Loss " + (-1)*streak;
		}
		else return "0";
	}
	//winPerct() is used when displaying a teams win Percentage in the standings
	public double winPerct() {
		return (double)this.win/(this.win + this.loss);
	}
}
