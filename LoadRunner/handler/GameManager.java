package LoadRunner.handler;

import LoadRunner.events.KeyboardEvent;
import LoadRunner.game.Player;
import LoadRunner.game.Scene;
import LoadRunner.thread.RefreshScene;
import LoadRunner.handler.LevelManager;
import LoadRunner.handler.EnemiesManager;
import LoadRunner.thread.EnemyThread;
import LoadRunner.thread.RegenSceneThread;

import java.awt.*;

public class GameManager {

    private Player player1;
    private Player player2;
    private final Scene scene;
    private int gamemode;
    private GameState gameState;
    private int level;

    public GameManager(Scene scene, GameState gameState) {
        this.player1 = scene.getPlayer1();
        this.player2 = scene.getPlayer2();
        this.scene = scene;
        this.gameState = gameState;
    }

    //lors du lancement de la partie, les joueurs choisis auparavant sont ajoutés au GameManager
    public void start() {
        LevelManager levelManager = new LevelManager(this);
        ThreadManager threadManager = new ThreadManager();
        EnemiesManager enemiesManager = new EnemiesManager(this, threadManager);
        RefreshScene refresh = new RefreshScene(this);
        RegenSceneThread regenScene = new RegenSceneThread(this);
        threadManager.addThread(refresh);
        threadManager.addThread(regenScene);

        if(getGameMode()==1){
            scene.set1Player(player1);
            gameState = GameState.SOLOGAME;
        }else{
            scene.set2Players(player1,player2);
            gameState = GameState.MULTIGAME;
        }
        threadManager.startThreads();
    }

    public void end(){
        gameState = GameState.END;
    }


    public Scene getScene() {
        return this.scene;
    }

    public void setGameMode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getGameMode() {
        return this.gamemode;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setLevel(int level){
      this.level = level;
    }

    public int getLevel(){
      return this.level;
    }

}
