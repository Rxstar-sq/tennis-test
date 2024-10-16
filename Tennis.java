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
	
	
	
    private int playerAPoints, playerBPoints;
    private int playerAGames, playerBGames;
    private int playerASets, playerBSets;
    private boolean playerAServing;
    private boolean tiebreak;
    private String playerAName, playerBName;
    private boolean isBestOfFive, isPlayingTiebreaks, isGrandSlam;

    // Constructor
    public Tennis(String playerAName, String playerBName, boolean playerAServing, boolean tiebreak, boolean isBestOfFive) {
        this.playerAPoints = this.playerBPoints = 0;
        this.playerAGames = this.playerBGames = 0;
        this.playerASets = this.playerBSets = 0;
        this.playerAName = playerAName;
        this.playerBName = playerBName;
        this.playerAServing = playerAServing;
        this.tiebreak = tiebreak;
        this.isBestOfFive = isBestOfFive;
        this.isPlayingTiebreaks = false;
        this.isGrandSlam = false;
    }

    // Accessor methods
    public String getPlayerAName() {
        return playerAName;
    }

    public String getPlayerBName() {
        return playerBName;
    }

    public boolean getPlayerAServing() {
        return playerAServing;
    }

    public int getPlayerASets() {
        return playerASets;
    }

    public int getPlayerBSets() {
        return playerBSets;
    }

    public int getPlayerAGames() {
        return playerAGames;
    }

    public int getPlayerBGames() {
        return playerBGames;
    }

    public String getPlayerAScore() {
        if (tiebreak) {
            return String.valueOf(playerAPoints);
        }
        switch (playerAPoints) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "AD";
        }
    }

    public String getPlayerBScore() {
        if (tiebreak) {
            return String.valueOf(playerBPoints);
        }
        switch (playerBPoints) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "AD";
        }
    }

    // Mutator methods
    public void winPoint(boolean playerAWins) {
        if (playerAWins) {
            playerAPoints++;
        } else {
            playerBPoints++;
        }
        checkGameWin();
    }

    public void winGame(boolean playerAWins) {
        if (playerAWins) {
            playerAGames++;
        } else {
            playerBGames++;
        }
        checkSetWin();
        resetPoints();
    }

    public void winSet(boolean playerAWins) {
        if (playerAWins) {
            playerASets++;
        } else {
            playerBSets++;
        }
        checkMatchWin();
        resetGames();
    }

    // Check if a game is won
    private void checkGameWin() {
        if (tiebreak) {
            if (playerAPoints >= 7 && playerAPoints - playerBPoints >= 2) {
                winGame(true);
                tiebreak = false;
            } else if (playerBPoints >= 7 && playerBPoints - playerAPoints >= 2) {
                winGame(false);
                tiebreak = false;
            }
        } else {
            if (playerAPoints >= 4 && playerAPoints - playerBPoints >= 2) {
                winGame(true);
            } else if (playerBPoints >= 4 && playerBPoints - playerAPoints >= 2) {
                winGame(false);
            }
        }
    }

    // Check if a set is won
    private void checkSetWin() {
        if (playerAGames >= 6 && playerAGames - playerBGames >= 2) {
            winSet(true);
        } else if (playerBGames >= 6 && playerBGames - playerAGames >= 2) {
            winSet(false);
        } else if (playerAGames == 6 && playerBGames == 6 && isPlayingTiebreaks) {
            tiebreak = true;
        }
    }

    // Check if a match is won
    private void checkMatchWin() {
        if ((isBestOfFive && playerASets >= 3) || (!isBestOfFive && playerASets >= 2)) {
            System.out.println("Game, Set and Match: " + getPlayerAName());
        } else if ((isBestOfFive && playerBSets >= 3) || (!isBestOfFive && playerBSets >= 2)) {
            System.out.println("Game, Set and Match: " + getPlayerBName());
        }
    }

    // Reset points after a game
    private void resetPoints() {
        playerAPoints = playerBPoints = 0;
        toggleServer();
    }

    // Reset games after a set
    private void resetGames() {
        playerAGames = playerBGames = 0;
    }

    // Toggle server after a game
    private void toggleServer() {
        playerAServing = !playerAServing;
    }

    // Callout logic for the game
    public String getCallOut() {
        if (tiebreak) {
            if (playerAPoints == playerBPoints) {
                return playerAPoints + "-All";
            } else if (playerAPoints > playerBPoints) {
                return playerAPoints + "-" + playerBPoints + " " + playerAName;
            } else {
                return playerBPoints + "-" + playerAPoints + " " + playerBName;
            }
        } else {
            if (playerAPoints == playerBPoints) {
                if (playerAPoints >= 3) {
                    return "Deuce";
                } else {
                    return getPlayerAScore() + "-All";
                }
            } else if (playerAPoints >= 4 || playerBPoints >= 4) {
                return "Advantage " + (playerAPoints > playerBPoints ? playerAName : playerBName);
            } else {
                return getPlayerAScore() + "-" + getPlayerBScore();
            }
        }
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
