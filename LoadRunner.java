import LoadRunner.game.Player;
import LoadRunner.game.Scene;

import LoadRunner.handler.ThreadManager;
import LoadRunner.handler.GameManager;
import LoadRunner.handler.GameState;
import LoadRunner.handler.LoadingManager;
import LoadRunner.handler.LevelManager;
import LoadRunner.handler.EnemiesManager;

import LoadRunner.thread.RefreshScene;
import LoadRunner.thread.EnemyThread;
import LoadRunner.thread.RegenSceneThread;

public class LoadRunner {
    public static void main(String[] args) {
        System.out.println("\033[H\033[2J");//supprime tout ce qu'il y a dans la console auparavant
        System.out.println("Load Runner | Runnig ...");
        Player player1 = new Player(100, "BastienLPB", 1);
        Player player2 = new Player(0, "SimonLPB", 2);
        LoadingManager loading = new LoadingManager(18,34);
        Scene scene = new Scene(18,34);//les valeurs 17 et 36 sont faites pour coller avec les méthodes de création des escaliers =>17-1(pour le bord)= 4 escaliers
        GameManager gameManager = new GameManager(scene);
        gameManager.setGameMode(loading.selectGameMode());
        //lors de la récupération du mode de jeu, on set les joueurs
        if(gameManager.getGameMode()==1){
          scene.set1Player(player1);
        }else{
          scene.set2Players(player1,player2);
        }
        gameManager.setLevel(loading.getLevel());
        gameManager.start();
        LevelManager levelManager = new LevelManager(gameManager);
        ThreadManager threadManager = new ThreadManager();
        EnemiesManager enemiesManager = new EnemiesManager(gameManager, threadManager);
        RefreshScene refresh = new RefreshScene(gameManager);
        RegenSceneThread regenScene = new RegenSceneThread(gameManager);
        threadManager.addThread(refresh);
        threadManager.addThread(regenScene);
        threadManager.startThreads();
    }
}
