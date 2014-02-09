package Scenes;

import Carte.Carte;
import Carte.TeleporteurList;
import Constantes.Ctes;
import Degats.TextDegat;
import Degats.TextDegat.TYPE;
import Degats.TextDegatList;
import Personnages.Personnage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneVillage extends Scene {
    private Carte c;
    private Image imgBank;
    private Image menuBank;
    private Image fontaine;
    private Personnage perso;
    private boolean onBank;
    private long lastFontaine;
    private TeleporteurList telep;
    
    public SceneVillage(Personnage p) {
        super();
        try {
            setPriority(1);
            perso = p;
            c = new Carte(-1);
            telep = new TeleporteurList(-1);
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(SceneVillage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() { return "Village"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        c.afficher(g);
        telep.affiche(g);
        g.drawImage(fontaine, Ctes.VILLAGE_X_FONTAINE, Ctes.VILLAGE_Y_FONTAINE);
        g.drawImage(imgBank, Ctes.VILLAGE_X_IMGBANK, Ctes.VILLAGE_Y_IMGBANK);
        perso.afficher(g);
        if (onBank) g.drawImage(menuBank, Ctes.VILLAGE_X_MENUBANK, Ctes.VILLAGE_Y_MENUBANK);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        perso.deplacer(input, c);
        int id = telep.collision(perso);
        if (id != Ctes.NOMAP_VALUE) {
            Main.Game.manager.addSence(new SceneExte(id, perso));
            Main.Game.manager.removeSence(this);
            Main.Game.manager.sort();
        }
        if (perso.getX() + perso.getImg().getWidth() > Ctes.VILLAGE_X_IMGBANK && perso.getX() < Ctes.VILLAGE_X_IMGBANK + imgBank.getWidth() && 
                perso.getY() + perso.getImg().getHeight() > Ctes.VILLAGE_Y_IMGBANK && perso.getY() < Ctes.VILLAGE_Y_IMGBANK + imgBank.getHeight()) {
            onBank = true;
            if (input.isKeyDown(Input.KEY_B)) {
                onBank = false;
                setState(STATE.FREEZE_NEXT);
                Main.Game.manager.getSence("BarreInfo").setState(STATE.REFRESH);
                Main.Game.manager.addSence(new SceneBank(perso));
            }
        } else onBank = false;
        
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            setState(STATE.FREEZE_NEXT);
            Main.Game.manager.getSence("BarreInfo").setState(STATE.FREEZE_NEXT);
            Main.Game.manager.addSence(new SceneEchap(perso));
        }
        
        if (perso.getX() > Ctes.VILLAGE_X_FONTAINE && perso.getX() + perso.getImg().getWidth() < Ctes.VILLAGE_X_FONTAINE + fontaine.getWidth() && 
                perso.getY() > Ctes.VILLAGE_Y_FONTAINE && perso.getY() + perso.getImg().getHeight() < Ctes.VILLAGE_Y_FONTAINE + fontaine.getHeight()) {
            if (System.currentTimeMillis() - lastFontaine > 3000) {
                perso.heal(50);
                lastFontaine = System.currentTimeMillis();
            }
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        imgBank = new Image("ressources/village/bank.png");
        menuBank = new Image("ressources/village/menuBank.png");
        onBank = false;
        fontaine = new Image("ressources/village/fontaine.png");
        lastFontaine = System.currentTimeMillis();
    }
    
    @Override
    public Personnage getPerso() { return perso; }
}