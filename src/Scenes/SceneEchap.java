package Scenes;

import BDD.Requete;
import Constantes.Ctes;
import Objets.Equipement;
import Objets.Objet;
import Personnages.Personnage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneEchap extends Scene {
    private Image fond;
    private Image reprendre;
    private Image save;
    private Image quitter;
    private Personnage perso;
    
    public SceneEchap(Personnage p) {
        super();
        setPriority(1);
        perso = p;
    }
    
    @Override
    public String toString() { return "Echap"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, Ctes.ECHAP_X_FOND, Ctes.ECHAP_Y_FOND);
        g.drawImage(reprendre, Ctes.ECHAP_X_REPRENDRE, Ctes.ECHAP_Y_REPRENDRE);
        g.drawImage(save, Ctes.ECHAP_X_SAVE, Ctes.ECHAP_Y_SAVE);
        g.drawImage(quitter, Ctes.ECHAP_X_QUITTER, Ctes.ECHAP_Y_QUITTER);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        if (input.isKeyDown(Input.KEY_R)) {
            Scene s = Main.Game.manager.getSence("Village");
            if (s == null)
                s = Main.Game.manager.getSence("Exte");
            s.setState(STATE.ON);
            Main.Game.manager.getSence("BarreInfo").setState(STATE.ON);
            Main.Game.manager.removeSence(this);
            gc.getInput().clearKeyPressedRecord();
        }
        else if (input.isKeyDown(Input.KEY_S)) {
            try {
                sauvegarde();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneEchap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyDown(Input.KEY_Q)) {
            try {
                sauvegarde();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneEchap.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Game.manager.removeAll();
            Main.Game.manager.addSence(new SceneAccueil());
        }
        
        if (input.getMouseX() > Ctes.ECHAP_X_REPRENDRE && input.getMouseX() < Ctes.ECHAP_X_REPRENDRE + reprendre.getWidth() && 
                input.getMouseY() > Ctes.ECHAP_Y_REPRENDRE && input.getMouseY() < Ctes.ECHAP_Y_REPRENDRE + reprendre.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Scene s = Main.Game.manager.getSence("Village");
                if (s == null)
                    s = Main.Game.manager.getSence("Exte");
                s.setState(STATE.ON);
                Main.Game.manager.getSence("BarreInfo").setState(STATE.ON);
                Main.Game.manager.removeSence(this);
                gc.getInput().clearKeyPressedRecord();
            }
            else {
                reprendre = new Image("ressources/echap/reprendre_hover.png");
                save = new Image("ressources/echap/save.png");
                quitter = new Image("ressources/echap/saveQuit.png");
            }
        }
        else if (input.getMouseX() > Ctes.ECHAP_X_SAVE && input.getMouseX() < Ctes.ECHAP_X_SAVE + save.getWidth() && 
                input.getMouseY() > Ctes.ECHAP_Y_SAVE && input.getMouseY() < Ctes.ECHAP_Y_SAVE + save.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    sauvegarde();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneEchap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                reprendre = new Image("ressources/echap/reprendre.png");
                save = new Image("ressources/echap/save_hover.png");
                quitter = new Image("ressources/echap/saveQuit.png");
            }
        }
        else if (input.getMouseX() > Ctes.ECHAP_X_QUITTER && input.getMouseX() < Ctes.ECHAP_X_QUITTER + quitter.getWidth() && 
                input.getMouseY() > Ctes.ECHAP_Y_QUITTER && input.getMouseY() < Ctes.ECHAP_Y_QUITTER + quitter.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    sauvegarde();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneEchap.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Game.manager.removeAll();
                Main.Game.manager.addSence(new SceneAccueil());
            }
            else {
                reprendre = new Image("ressources/echap/reprendre.png");
                save = new Image("ressources/echap/save.png");
                quitter = new Image("ressources/echap/saveQuit_hover.png");
            }
        }
        else {
            reprendre = new Image("ressources/echap/reprendre.png");
            save = new Image("ressources/echap/save.png");
            quitter = new Image("ressources/echap/saveQuit.png");
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/echap/fond.png");
        reprendre = new Image("ressources/echap/reprendre.png");
        save = new Image("ressources/echap/save.png");
        quitter = new Image("ressources/echap/saveQuit.png");
    }
    
    public void sauvegarde() throws ClassNotFoundException, SQLException {
        perso.sauvegarde();
    }
}
