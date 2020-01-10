package pt.technic.apps.minesfinder;

import java.util.Random;

public class ItemMinefield {

    public static final int EMPTY = 0;
    // from 1 to 8 is the number of mines around
    public static final int COVERED = 9;
    public static final int QUESTION = 10;
    public static final int MARKED = 11;
    public static final int BUSTED = 12;

    private boolean[][] mines;
    private int[][] states;
    private int width;
    private int height;
    private int numMines;
    private int mineDetector;
	private int life;

    private Random random;

    private boolean firstPlay;
    private boolean playerDefeated;
    private boolean gameFinished;
    
    private long timeGameStarted;
    private long timeGameDuration;

	public ItemMinefield(int width, int height, int numMines) {
        if(numMines<=0){
            throw new IllegalArgumentException("Mines number must be bigger than 0");
        }
        else if(numMines>=width*height) {
            throw new IllegalArgumentException("Mines number must be smaller than width*height");
        }
        
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        mines = new boolean[width][height];
        states = new int[width][height];

        random = new Random();

        firstPlay = true;
        playerDefeated = false;
        gameFinished = false;
        mineDetector = 3;
        life = 3;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                states[x][y] = COVERED;
            }
        }
	}

	public void revealGrid(int x, int y) {
        if (states[x][y] == COVERED && !gameFinished) {
            if (firstPlay) {
                firstPlay = false;
                placeMines(x, y);
                timeGameStarted=System.currentTimeMillis();
            }

            if (mines[x][y]) {
                states[x][y] = MARKED;
                life--;
                if(life<0) {
                	//모든 지뢰를 보여주는 부분 추가

                    for(int i=0; i<width; i++) {
                    	for(int j=0; j<height; j++) {
                    		if(mines[i][j])
                    			states[i][j] = BUSTED;
                    	}
                    }

                	playerDefeated = true;
                    gameFinished = true;
                    timeGameDuration=System.currentTimeMillis()-timeGameStarted;
                    return;
                }
            }
            else {
                int minesAround = countMinesAround(x, y);
                states[x][y] = minesAround;

                if (minesAround == 0) {
                    revealGridNeighbors(x, y);
                }
                
                if(checkVictory()) {
                    gameFinished=true;
                    playerDefeated=false;
                    timeGameDuration=System.currentTimeMillis()-timeGameStarted;
                    return;
                }
            }
        }

	}
    public long getGameDuration(){
        if(firstPlay){
            return 0;
        }
        if(!gameFinished){
            return System.currentTimeMillis()-timeGameStarted; 
        }
        return timeGameDuration;
    }

    private void revealGridNeighbors(int x, int y) {
        for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
            for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++) {
                revealGrid(col, line);
            }
        }
    }

    public void setMineMarked(int x, int y) {
        if (states[x][y] == COVERED || states[x][y] == QUESTION) {
            states[x][y] = MARKED;
        }
    }

    public void setMineQuestion(int x, int y) {
        if (states[x][y] == COVERED || states[x][y] == MARKED) {
            states[x][y] = QUESTION;
        }
    }

    public void setMineCovered(int x, int y) {
        if (states[x][y] == MARKED || states[x][y] == QUESTION) {
            states[x][y] = COVERED;
        }
    }

    private boolean checkVictory() {
        boolean victory = true;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!mines[x][y]) {
                    victory = victory && states[x][y] >= 0 && states[x][y] < 9;
                }
            }
        }
        return victory;
    }

    private int countMinesAround(int x, int y) {
        int result = 0;
        for (int col = Math.max(0, x - 1); col < Math.min(width, x + 2); col++) {
            for (int line = Math.max(0, y - 1); line < Math.min(height, y + 2); line++) {
                if (mines[col][line]) {
                    result++;
                }
            }
        }
        return result - (mines[x][y] ? 1 : 0);
    }

    public boolean isPlayerDefeated() {
        return playerDefeated;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void placeMines(int plX, int plY) {
        // the plX and plY is the player's first play
        for (int i = 0; i < numMines; i++) {
            int x = 0;
            int y = 0;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (mines[x][y] || (x == plX && y == plY));
            mines[x][y] = true; //true : 지뢰있음
        }
    }

    public int getGridState(int x, int y) {
        return states[x][y];
    }

    public boolean hasMine(int x, int y) {
        return mines[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumMines() {
        return numMines;
    }

    //기능 2번을 위한 메소드
    public void retryGame() {
        playerDefeated = false;
        gameFinished = false;

        states = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                states[x][y] = COVERED;
            }
        }
        mines = new boolean[width][height];
        placeMines(width, height);
        mineDetector = 3;
        life = 3;
        timeGameStarted=System.currentTimeMillis();
    }
	
    public void useItem() {
    	this.mineDetector--;
    }
	public int getItem() {
		return mineDetector;
	}
	
	public int getLife() {
		return life;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
