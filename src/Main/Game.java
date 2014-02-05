package Main;


import Scenes.SceneAccueil;
import Scenes.SceneManager;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Game extends BasicGame {
    public static SceneManager manager;
    
    public Game() {
        super("Game");
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        manager.render(gc, g);
    }
    
    @Override
    public void update(GameContainer gc, int t) throws SlickException {
        manager.update(gc, t);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        manager = new SceneManager(gc);
        manager.addSence(new SceneAccueil());
    }
}