package Scenes;

import Carte.Carte;
import Carte.TeleporteurList;
import Constantes.Ctes;
import Monstre.MobList;
import Personnages.Personnage;
import Projectiles.ProjectileList;
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
    private Image popMob;
    private ProjectileList projPerso = new ProjectileList();
    private MobList mobliste;
    private boolean fight;
    private TeleporteurList telep;
    
    public SceneExte(int idMap, Personnage p) {
        super();
        try {
            setPriority(1);
            perso = p;
            c = new Carte(idMap);
            telep = new TeleporteurList(idMap);
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(SceneExte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() { return "Exte"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        c.afficher(g);
        if (!fight || (fight && mobliste.isFinish())) telep.affiche(g);
        g.drawImage(popMob, Ctes.CARTE_X_POPMOB, Ctes.CARTE_Y_POPMOB);
        perso.afficher(g);
        mobliste.afficher(g);
        projPerso.afficher(g);
        g.drawImage(backVillage, Ctes.LARGEUR_ECRAN - backVillage.getWidth(), 0);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        perso.deplacer(input, c);
        if (!fight || (fight && mobliste.isFinish())) {
            int id = telep.collision(perso);
            if (id != Ctes.NOMAP_VALUE) {
                Main.Game.manager.addSence(new SceneExte(id, perso));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            }
        }
        
        mobliste.deplacer(perso);
        if (perso.getVie() == 0) {
            perso.setVie(1);
            perso.setX(Ctes.VILLAGE_X_STARTPERSO);
            perso.setY(Ctes.VILLAGE_Y_STARTPERSO);
            Main.Game.manager.addSence(new SceneVillage(perso));
            Main.Game.manager.removeSence(this);
            Main.Game.manager.sort();
        }
        
        if (perso.getX() < Ctes.CARTE_X_POPMOB + popMob.getWidth() && perso.getX1() > Ctes.CARTE_X_POPMOB &&
                perso.getY() < Ctes.CARTE_Y_POPMOB + popMob.getHeight() && perso.getY1() > Ctes.CARTE_Y_POPMOB) {
            if (!fight) {
                mobliste.init();
                fight = true;
            }
        }
        
        projPerso.deplacer(c.getMurs());
        projPerso.collisionWithMob(mobliste.getListeMob());
        mobliste.testLoot(input, perso);
        
        if (input.isKeyPressed(Input.KEY_V)) {
            perso.setX(Ctes.VILLAGE_X_STARTPERSO);
            perso.setY(Ctes.VILLAGE_Y_STARTPERSO);
            Main.Game.manager.addSence(new SceneVillage(perso));
            Main.Game.manager.removeSence(this);
            Main.Game.manager.sort();
        }
        
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            perso.useSkill();
        }
        
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            setState(STATE.FREEZE_NEXT);
            Main.Game.manager.getSence("BarreInfo").setState(STATE.FREEZE_NEXT);
            Main.Game.manager.addSence(new SceneEchap(perso));
        }
        
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            int idArme = perso.tirer(input);
            if (idArme != -1) projPerso.addProjectiles(idArme, input, (perso.getX() + perso.getX1()) / 2, 
                    (perso.getY() + perso.getY1()) / 2, perso.getStatsAct().getAtk());
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        backVillage = new Image("ressources/carte/backVillage.png");
        popMob = new Image("ressources/carte/popMob.png");
        fight = false;
        mobliste = new MobList(c);
    }
    
    @Override
    public Personnage getPerso() { return perso; }
}
