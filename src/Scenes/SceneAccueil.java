package Scenes;

import BDD.ExecQuery;
import Constantes.Ctes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneAccueil extends Scene {
    private Image fond;
    private Image btnNew;
    private Image btnContinue;
    private Image btnQuit;
    private ExecQuery ex; //A supprimer a la fin
    
    public SceneAccueil() {
        super();
        setPriority(1);
    }
    
    @Override
    public String toString() { return "Accueil"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, 0, 0);
        g.drawImage(btnNew, Ctes.ACCUEIL_X_BTNNEW, Ctes.ACCUEIL_Y_BTNNEW);
        g.drawImage(btnContinue, Ctes.ACCUEIL_X_BTNCONTINUE, Ctes.ACCUEIL_Y_BTNCONTINUE);
        g.drawImage(btnQuit, Ctes.ACCUEIL_X_BTNQUIT, Ctes.ACCUEIL_Y_BTNQUIT);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();

        if (input.getMouseX() > Ctes.ACCUEIL_X_BTNNEW && input.getMouseX() < Ctes.ACCUEIL_X_BTNNEW + btnNew.getWidth() && 
                input.getMouseY() > Ctes.ACCUEIL_Y_BTNNEW && input.getMouseY() < Ctes.ACCUEIL_Y_BTNNEW + btnNew.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.addSence(new SceneNewGame());
                Main.Game.manager.removeSence(this);
            }
            else
                btnNew = new Image("ressources/accueil/new_hover.png");
        }
        else if (input.getMouseX() > Ctes.ACCUEIL_X_BTNCONTINUE && input.getMouseX() < Ctes.ACCUEIL_X_BTNCONTINUE + btnContinue.getWidth() && 
                input.getMouseY() > Ctes.ACCUEIL_Y_BTNCONTINUE && input.getMouseY() < Ctes.ACCUEIL_Y_BTNCONTINUE + btnContinue.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.addSence(new SceneContinueGame());
                Main.Game.manager.removeSence(this);
            }
            else
                btnContinue = new Image("ressources/accueil/cont_hover.png");
        }
        else if (input.getMouseX() > Ctes.ACCUEIL_X_BTNQUIT && input.getMouseX() < Ctes.ACCUEIL_X_BTNQUIT + btnQuit.getWidth() && 
                input.getMouseY() > Ctes.ACCUEIL_Y_BTNQUIT && input.getMouseY() < Ctes.ACCUEIL_Y_BTNQUIT + btnQuit.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.removeAll();
                gc.exit();
            }
            else
                btnQuit = new Image("ressources/accueil/quit_hover.png");
        }
        else {
            btnNew = new Image("ressources/accueil/new.png");
            btnContinue = new Image("ressources/accueil/cont.png");
            btnQuit = new Image("ressources/accueil/quit.png");
        }
        
        if (input.isKeyPressed(Input.KEY_N)) {
            Main.Game.manager.addSence(new SceneNewGame());
            Main.Game.manager.removeSence(this);
        }
        else if (input.isKeyPressed(Input.KEY_C)) {
            Main.Game.manager.addSence(new SceneContinueGame());
            Main.Game.manager.removeSence(this);
        }
        else if (input.isKeyPressed(Input.KEY_Q)) {
            Main.Game.manager.removeAll();
            gc.exit();
        }
        
        //A supprimer a la fin du projet
        if (input.isKeyPressed(Input.KEY_A)) {
            try {
                ex = new ExecQuery();
                ex.execQ();
            } catch (ClassNotFoundException | SQLException exe) {
                Logger.getLogger(SceneAccueil.class.getName()).log(Level.SEVERE, null, exe);
            }
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/accueil/fond.png");
        btnNew = new Image("ressources/accueil/new.png");
        btnContinue = new Image("ressources/accueil/cont.png");
        btnQuit = new Image("ressources/accueil/quit.png");
    }
}