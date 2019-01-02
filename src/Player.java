/*
 * This class stores an individual players stats for us
 * to use either for increasing, decreasing or printing.
 */

public class Player {
	private String playerName;
	private int points = 0; //player Points
	private int rebounds = 0; //player Rebounds
	private int assists = 0; //player Assist
	private int steals = 0; //player Steals
	private int blocks = 0; //player Blocks
	private int turnovers = 0; //player Turnovers
	
	/*
	 * Player constructor 
	 * @param name		individual player's name
	 */
	public Player (String name) {
		playerName = name;
	}
	
	/*
	 * Override toString method so representation of 
	 * object returns the player's name
	 * @return playerName		Individual player's name
	 */
	public String toString () {
		return playerName;
	}
	
	/*
	 * @return playerName		Individual player's name
	 */
	public String getName () {
		return playerName;
	}
	
	/*
	 * increases player points for each free throw event
	 */
	public void freeThrow () {
		points += 1;
	}
	
	/*
	 * decreases player points for each free throw event
	 */
	public void decreaseFreeThrow () {
		if (points <= 0) {}
		else {
			points -= 1;
		}
	}
	
	/*
	 * increases player points for each two pointer event
	 */
	public void twoPointer () {
		points += 2;
	}
	
	/*
	 * decreases player points for each two pointer event
	 */
	public void decreaseTwoPointer () {
		if (points <= 0) {}
		else {
			points -= 2;
		}
	}
	
	/*
	 * increases player points for each three pointer event
	 */
	public void threePointer () {
		points += 3;
	}
	
	/*
	 * decreases player points for each three pointer event
	 */
	public void decreaseThreePointer () {
		if (points <= 0) {}
		else {
			points -= 3;
		}
	}
	
	/*
	 * @return points		Individual player's points 
	 */
	public int getPoints () {
		return points;
	}
	
	/*
	 * increases player rebounds for each rebound event
	 */
	public void rebound () {
		rebounds += 1;
	}
	
	/*
	 * decreases player rebounds for each rebound event
	 */
	public void decreaseRebound () {
		if (rebounds <= 0) {}
		else {
			rebounds -= 1;
		}
	}
	
	/*
	 * @return rebounds		Individual player's rebounds
	 */
	public int getRebounds () {
		return rebounds;
	}
	
	/*
	 * increases player assist for each assist event
	 */
	public void assist () {
		assists += 1;
	}
	
	/*
	 * decreases player assists for each assist event
	 */
	public void decreaseAssist () {
		if (assists <= 0) {}
		else {
			assists -= 1;
		}
	}
	
	/*
	 * @return assists		Individual player's assist
	 */
	public int getAssists () {
		return assists;
	}
	
	/*
	 * increases player steals for each steal event
	 */
	public void steal () {
		steals += 1;
	}
	
	/*
	 * decreases player steals for each steal event
	 */
	public void decreaseSteal () {
		if (steals <= 0) {}
		else {
			steals -= 1;
		}
	}
	
	/*
	 * @return steals		Individual player's steals
	 */
	public int getSteals () {
		return steals;
	}
	
	/*
	 * increases player blocks for each block event
	 */
	public void block () {
		blocks += 1;
	}
	
	/*
	 * decreases player blocks for each block event
	 */
	public void decreaseBlock () {
		if (blocks <= 0) {}
		else {
			blocks -= 1;
		}
	}
	
	/*
	 * @return blocks		Individual player's blocks
	 */
	public int getBlocks () {
		return blocks;
	}
	
	/*
	 * increases player turnovers for each turnover event
	 */
	public void turnover () {
		turnovers += 1;
	}
	
	/*
	 * decreases player turnovers for each turnover event
	 */
	public void decreaseTurnover () {
		if (turnovers <= 0) {}
		else {
			turnovers -= 1;
		}
	}
	
	/*
	 * @return turnover		Individual player's turnovers
	 */
	public int getTurnovers () {
		return turnovers;
	}
}
