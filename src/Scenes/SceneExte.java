package Scenes;

import Carte.Carte;
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

public class SceneExte extends Scene {
    private Personnage perso;
    private Carte c;
    private Image backVillage;
    
    public SceneExte(int idMap, Personnage p) {
        super();
        try {
            setPriority(1);
            perso = p;
            c = new Carte(0);
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(SceneExte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() { return "Exte"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        c.afficher(g);
        perso.afficher(g);
        g.drawImage(backVillage, Ctes.LARGEUR_ECRAN - backVillage.getWidth(), 0);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        perso.deplacer(input, c);
        if (input.isKeyPressed(Input.KEY_V)) {
            perso.setX(Ctes.VILLAGE_X_STARTPERSO);
            perso.setY(Ctes.VILLAGE_Y_STARTPERSO);
            Main.Game.manager.addSence(new SceneVillage(perso));
            Main.Game.manager.removeSence(this);
        }
        
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            perso.tirer(input);
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        backVillage = new Image("ressources/carte/backVillage.png");
    }
    
    public Personnage getPerso() { return perso; }
}
