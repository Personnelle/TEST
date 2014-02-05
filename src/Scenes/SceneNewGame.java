package Scenes;

import Constantes.Ctes;
import Personnages.Personnage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneNewGame extends Scene {
    private Image fond;
    private Image btnChevalier;
    private Image btnAssassin;
    private Image btnSorcier;
    private Image btnPretre;
    private Image btnRetour;
    
    public SceneNewGame() {
        super();
        setPriority(1);
    }
    
    @Override
    public String toString() { return "NewGame"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, 0, 0);
        g.drawImage(btnChevalier, Ctes.NEWGAME_X_BTNCHEVALIER, Ctes.NEWGAME_Y_BTNCHEVALIER);
        g.drawImage(btnAssassin, Ctes.NEWGAME_X_BTNASSASSIN, Ctes.NEWGAME_Y_BTNASSASSIN);
        g.drawImage(btnSorcier, Ctes.NEWGAME_X_BTNSORCIER, Ctes.NEWGAME_Y_BTNSORCIER);
        g.drawImage(btnPretre, Ctes.NEWGAME_X_BTNPRETRE, Ctes.NEWGAME_Y_BTNPRETRE);
        g.drawImage(btnRetour, Ctes.NEWGAME_X_BTNRETOUR, Ctes.NEWGAME_Y_BTNRETOUR);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        if (input.isKeyPressed(Input.KEY_C)) {
            try {
                Main.Game.manager.addSence(new SceneVillage(new Personnage(1, true)));
                Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(1, true)));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyPressed(Input.KEY_A)) {
            try {
                Main.Game.manager.addSence(new SceneVillage(new Personnage(2, true)));
                Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(2, true)));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyPressed(Input.KEY_S)) {
            try {
                Main.Game.manager.addSence(new SceneVillage(new Personnage(3, true)));
                Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(3, true)));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyPressed(Input.KEY_P)) {
            try {
                Main.Game.manager.addSence(new SceneVillage(new Personnage(4, true)));
                Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(4, true)));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyPressed(Input.KEY_R)) {
            Main.Game.manager.addSence(new SceneAccueil());
            Main.Game.manager.removeSence(this);
        }
        
        if (input.getMouseX() > Ctes.NEWGAME_X_BTNCHEVALIER && input.getMouseX() < Ctes.NEWGAME_X_BTNCHEVALIER + btnChevalier.getWidth() && 
                input.getMouseY() > Ctes.NEWGAME_Y_BTNCHEVALIER && input.getMouseY() < Ctes.NEWGAME_Y_BTNCHEVALIER + btnChevalier.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    Main.Game.manager.addSence(new SceneVillage(new Personnage(1, true)));
                    Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(1, true)));
                    Main.Game.manager.removeSence(this);
                    Main.Game.manager.sort();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                btnChevalier = new Image("ressources/newGame/chevalier_hover.png");
        }
        else if (input.getMouseX() > Ctes.NEWGAME_X_BTNASSASSIN && input.getMouseX() < Ctes.NEWGAME_X_BTNASSASSIN + btnAssassin.getWidth() && 
                input.getMouseY() > Ctes.NEWGAME_Y_BTNASSASSIN && input.getMouseY() < Ctes.NEWGAME_Y_BTNASSASSIN + btnAssassin.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    Main.Game.manager.addSence(new SceneVillage(new Personnage(2, true)));
                    Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(2, true)));
                    Main.Game.manager.removeSence(this);
                    Main.Game.manager.sort();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                btnAssassin = new Image("ressources/newGame/assassin_hover.png");
        }
        else if (input.getMouseX() > Ctes.NEWGAME_X_BTNSORCIER && input.getMouseX() < Ctes.NEWGAME_X_BTNSORCIER + btnSorcier.getWidth() && 
                input.getMouseY() > Ctes.NEWGAME_Y_BTNSORCIER && input.getMouseY() < Ctes.NEWGAME_Y_BTNSORCIER + btnSorcier.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    Main.Game.manager.addSence(new SceneVillage(new Personnage(3, true)));
                    Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(3, true)));
                    Main.Game.manager.removeSence(this);
                    Main.Game.manager.sort();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                btnSorcier = new Image("ressources/newGame/sorcier_hover.png");
        }
        else if (input.getMouseX() > Ctes.NEWGAME_X_BTNPRETRE && input.getMouseX() < Ctes.NEWGAME_X_BTNPRETRE + btnPretre.getWidth() && 
                input.getMouseY() > Ctes.NEWGAME_Y_BTNPRETRE && input.getMouseY() < Ctes.NEWGAME_Y_BTNPRETRE + btnPretre.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    Main.Game.manager.addSence(new SceneVillage(new Personnage(4, true)));
                    Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(4, true)));
                    Main.Game.manager.removeSence(this);
                    Main.Game.manager.sort();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                btnPretre = new Image("ressources/newGame/pretre_hover.png");
        }
        else if (input.getMouseX() > Ctes.NEWGAME_X_BTNRETOUR && input.getMouseX() < Ctes.NEWGAME_X_BTNRETOUR + btnRetour.getWidth() && 
                input.getMouseY() > Ctes.NEWGAME_Y_BTNRETOUR && input.getMouseY() < Ctes.NEWGAME_Y_BTNRETOUR + btnRetour.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.addSence(new SceneAccueil());
                Main.Game.manager.removeSence(this);
            }
            else
                btnRetour = new Image("ressources/newGame/retour_hover.png");
        }
        else {
            btnChevalier = new Image("ressources/newGame/chevalier.png");
            btnAssassin = new Image("ressources/newGame/assassin.png");
            btnSorcier = new Image("ressources/newGame/sorcier.png");
            btnPretre = new Image("ressources/newGame/pretre.png");
            btnRetour = new Image("ressources/newGame/retour.png");
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/newGame/fond.png");
        btnChevalier = new Image("ressources/newGame/chevalier.png");
        btnAssassin = new Image("ressources/newGame/assassin.png");
        btnSorcier = new Image("ressources/newGame/sorcier.png");
        btnPretre = new Image("ressources/newGame/pretre.png");
        btnRetour = new Image("ressources/newGame/retour.png");
    }
}