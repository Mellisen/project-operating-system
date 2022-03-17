package LoadRunner.handler;

import LoadRunner.game.Scene;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.StreamTokenizer;
import java.io.File;

public class LevelManager {
    private GameManager gameManager;
    private Scene scene;//scene du jeu
    private File file;//= new File("../files/level1.txt");
    private int index;//index du tableau
    private int length;//nombre d'élément par ligne


    public LevelManager(GameManager gameManager){
      try{
        this.gameManager = gameManager;
        this.scene = gameManager.getScene();
        this.index = 0;
        this.length = scene.getLenght();
        file = new File("LoadRunner/files/level" + gameManager.getLevel() + ".txt");
        BufferedReader obj = new BufferedReader(new FileReader(file));
        StreamTokenizer st = new StreamTokenizer(obj);
        while(st.nextToken()!=StreamTokenizer.TT_EOF){
          if(st.ttype==StreamTokenizer.TT_NUMBER){
            //pour avoir le numéro de la ligne, on divise l'index par le nombre de ligne
            //pour avoir le numéro de la colonne, on récuépère le reste de la division entre l'index et la longeur de la ligne
            scene.setValuePosition(index%length, (int)(index/length), (int)st.nval);
          }
          index++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

}
