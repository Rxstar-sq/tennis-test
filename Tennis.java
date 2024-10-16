package hw2;

public class Tennis {
    /* Everything below this line is given to students, either because we
     haven't covered loops yet, or because we want to ensure very precise
     formatting so that we can test by comparing strings which are printed by
     the same function.  */
	
    /**
     * Prints out what the scoreboard must indicate.  It counts sets and games
     * in a natural way, with whole numbers.  However points within a game are
     * counted using the nonconsecutive numbers 0, 15, 30, 40.  When there is
     * a deuce, it is indicated as 40 for both players.  If one player has an
     * advantage, their score is represented as "AD", while the other player's
     * score is simultaneously indicated as "--".  When counting score in
     * tiebreak games, we simply use normal counting.
     */
	
	
	

	    private String playerAName, playerBName;
	    private int playerAPoints, playerBPoints, playerAGames, playerBGames, playerASets, playerBSets;
	    private boolean isBestOfFive, isPlayingTiebreaks, isGrandSlam, playerAServing;
	    private boolean tiebreak;
	    private boolean matchWon; // To lock further changes after match win

	    public Tennis(String playerAName, String playerBName, boolean isBestOfFive, boolean isPlayingTiebreaks, boolean isGrandSlam) {
	        this.playerAName = playerAName;
	        this.playerBName = playerBName;
	        this.isBestOfFive = isBestOfFive;
	        this.isPlayingTiebreaks = isPlayingTiebreaks;
	        this.isGrandSlam = isGrandSlam;
	        this.playerAServing = true;
	        this.playerAPoints = this.playerBPoints = 0;
	        this.playerAGames = this.playerBGames = 0;
	        this.playerASets = this.playerBSets = 0;
	        this.tiebreak = false;
	        this.matchWon = false;
	    }

	    public void winPoint(boolean playerAWins) {
	        if (matchWon) return; // end match
	        if (playerAWins) {
	            playerAPoints++;
	        } else {
	            playerBPoints++;
	        }
	        checkGameWin();
	    }

	    public void winGame(boolean playerAWins) {
	        if (matchWon) return; // end match
	        if (playerAWins) {
	            playerAGames++;
	        } else {
	            playerBGames++;
	        }
	        checkSetWin();
	        resetPoints();
	    }

	    public void winSet(boolean playerAWins) {
	        if (matchWon) return; // end match
	        if (playerAWins) {
	            playerASets++;
	        } else {
	            playerBSets++;
	        }
	        checkMatchWin();
	        resetGames();
	    }

	    public String getCallOut() {
	    	 int requiredSets = isBestOfFive ? 3 : 2;
	    	    if (playerASets == requiredSets) {
	    	        return "Game, Set and Match: " + getPlayerAName();
	    	    } else if (playerBSets == requiredSets) {
	    	        return "Game, Set and Match: " + getPlayerBName();
	    	    }
	    	    
	    	    // Check if a set is won
	    	    if (playerAGames >= 6 && playerAGames - playerBGames >= 2) {
	    	        return "Game and Set: " + getPlayerAName();
	    	    } else if (playerBGames >= 6 && playerBGames - playerAGames >= 2) {
	    	        return "Game and Set: " + getPlayerBName();
	    	    }


	    	    if (playerAPoints == playerBPoints) {
	    	        if (playerAPoints >= 3) {
	    	            return "Deuce";
	    	        } else {
	    	            return getPlayerAScore() + "-All";
	    	        }
	    	    } else if (playerAPoints >= 4 || playerBPoints >= 4) {
	    	        return "Advantage " + (playerAPoints > playerBPoints ? getPlayerAName() : getPlayerBName());
	    	    } else {
	    	        return getPlayerAScore() + "-" + getPlayerBScore();
	    	    }
	    }

	    public String getPlayerAName() {
	        return playerAName;
	    }

	    public String getPlayerBName() {
	        return playerBName;
	    }

	    public int getPlayerAGames() {
	        return playerAGames;
	    }

	    public int getPlayerBGames() {
	        return playerBGames;
	    }

	    public int getPlayerASets() {
	        return playerASets;
	    }

	    public int getPlayerBSets() {
	        return playerBSets;
	    }

	    public boolean getPlayerAServing() {
	        return playerAServing;
	    }

	    public String getPlayerAScore() {
	        if (tiebreak) return " " + playerAPoints;
	        if (playerAPoints >= 4 && Math.abs(playerAPoints - playerBPoints) >= 1) {
	            return " " + playerAPoints; 
	        }
	        switch (playerAPoints) {
	            case 0: return " 0";
	            case 1: return " 15";
	            case 2: return " 30";
	            case 3: return " 40";
	            default: return " AD";
	        }
	    }

	    public String getPlayerBScore() {
	        if (tiebreak) return " " + playerBPoints;
	        if (playerBPoints >= 4 && Math.abs(playerBPoints - playerAPoints) >= 1) {
	            return " " + playerBPoints;  
	        }
	        switch (playerBPoints) {
	            case 0: return " 0";
	            case 1: return " 15";
	            case 2: return " 30";
	            case 3: return " 40";
	            default: return " AD";
	        }
	    }

	    private void checkGameWin() {
	        if (tiebreak) {
	        	if (playerAGames == 6 && playerBGames == 6) {
	                tiebreak = true;  // Start the tiebreak game
	            } else if (playerAGames >= 6 && playerAGames - playerBGames >= 2) {
	                winSet(true);  // Player A wins the set
	            } else if (playerBGames >= 6 && playerBGames - playerAGames >= 2) {
	                winSet(false);  // Player B wins the set
	            }
	        } else {
	            // Advantage system: Must win by at least 2 games after 6-6
	            if (playerAGames >= 6 && playerAGames - playerBGames >= 2) {
	                winSet(true);  // Player A wins the set
	            } else if (playerBGames >= 6 && playerBGames - playerAGames >= 2) {
	                winSet(false);  // Player B wins the set
	            }
	        }
	    }

	    private void checkSetWin() {
	        if (playerAGames >= 6 && playerAGames - playerBGames >= 2) {
	            winSet(true);
	        } else if (playerBGames >= 6 && playerBGames - playerAGames >= 2) {
	            winSet(false);
	        } else if (playerAGames == 6 && playerBGames == 6 && isPlayingTiebreaks) {
	            tiebreak = true;
	        }
	    }

	    private void checkMatchWin() {
	        int requiredSets = isBestOfFive ? 3 : 2;
	        if (playerASets >= requiredSets) {
	            matchWon = true; // end match
	        } else if (playerBSets >= requiredSets) {
	            matchWon = true; // end match
	        }
	    }

	    private void resetPoints() {
	        playerAPoints = playerBPoints = 0;
	        toggleServer();
	    }

	    private void resetGames() {
	        playerAGames = playerBGames = 0;
	    }

	    private void toggleServer() {
	        playerAServing = !playerAServing;
	    }

    
    @Override
       
    public String toString() {
	String playerAServingIndicator;
	String playerBServingIndicator;
	if (getPlayerAServing()) {
	    playerAServingIndicator = "S>";
	    playerBServingIndicator = "  ";
	} else {
	    playerAServingIndicator = "  ";
	    playerBServingIndicator = "S>";
	}
	String returned =
	    String.format(
                "%2s %-12s %2d %2d %6s\n%2s %-12s %2d %2d %6s\n",
		playerAServingIndicator,
		getPlayerAName(),
		getPlayerASets(),
		getPlayerAGames(),
		getPlayerAScore(),
		playerBServingIndicator,
		getPlayerBName(),
		getPlayerBSets(),
		getPlayerBGames(),
		getPlayerBScore());
	// System.out.println(returned);
	return returned;
    }

    /**
     * For testing purposes, converts a string of a's and b's into a sequence
     * of calls to winPoint, using an argument of true if the corresponding
     * character is an a, and false if the corresponding character is a b.
     * Provides a convenient way to run many winPoint method calls with
     * very concise notation.
     *
     * @param pointList - "script" that is converted into winPoint method
     * calls.
     */
    public void runPoints(String pointList) {
	for (int i = 0; i < pointList.length(); ++i) {
	    if (pointList.charAt(i) == 'a') {
		winPoint(true);
	    } else if (pointList.charAt(i) == 'b') {
		winPoint(false);
	    } else {
		// skip the character silently
	    }
	}
    }

    /**
     * For testing purposes, converts a string of a's and b's into a sequence
     * of calls to winGame, using an argument of true if the corresponding
     * character is an a, and false if the corresponding character is a b.
     * Provides a convenient way to run many winGame method calls with
     * very concise notation.
     *
     * @param gameList - "script" that is converted into winGame method
     * calls.
     */
    public void runGames(String gameList) {
	for (int i = 0; i < gameList.length(); ++i) {
	    if (gameList.charAt(i) == 'a') {
		winGame(true);
	    } else if (gameList.charAt(i) == 'b') {
		winGame(false);
	    } else {
		// skip the character silently
	    }
	}
    }

    /**
     * For testing purposes, converts a string of a's and b's into a sequence
     * of calls to winSet, using an argument of true if the corresponding
     * character is an a, and false if the corresponding character is a b.
     * Provides a convenient way to run many winSet method calls with
     * very concise notation.
     *
     * @param setList - "script" that is converted into winSet method calls.
     */
    public void runSets(String setList) {
	for (int i = 0; i < setList.length(); ++i) {
	    if (setList.charAt(i) == 'a') {
		winSet(true);	
	    } else if (setList.charAt(i) == 'b') {	
		winSet(false);
	    } else {
		// skip the character silently
	    }
	}
    }
}
