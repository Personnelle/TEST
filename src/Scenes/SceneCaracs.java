package Scenes;

import Constantes.Ctes;
import Personnages.Personnage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneCaracs extends Scene {
    private Personnage perso;
    private Image fond;
    private Image barreXp;
    
    public SceneCaracs(Personnage p) {
        super();
        setPriority(1);
        perso = p;
    }
    
    @Override
    public String toString() { return "Caracs"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, Ctes.CARACS_X_FOND, Ctes.CARACS_Y_FOND);
        afficheClasse(perso, g);
        g.drawString("Niveau " +perso.getNiveau(), Ctes.CARACS_X_NIVEAU, Ctes.CARACS_Y_NIVEAU);
        if (perso.getNiveau() < 20) {
            g.drawString("Expérience : " +perso.getXp()+ "/" +perso.getXpNeed(), Ctes.CARACS_X_XP, Ctes.CARACS_Y_XP);
            int pourcentXp = perso.getXp() * 200 / perso.getXpNeed();
            g.drawImage(barreXp.getSubImage(0, 0, pourcentXp, barreXp.getHeight()), Ctes.CARACS_X_BARREXP, Ctes.CARACS_Y_BARREXP);
        }
        else {
            g.drawString("Expérience : MAX ", Ctes.CARACS_X_XP, Ctes.CARACS_Y_XP);
            g.drawImage(barreXp, Ctes.CARACS_X_BARREXP, Ctes.CARACS_Y_BARREXP);
        }
        g.drawString("Honneur : " +perso.getFame(), Ctes.CARACS_X_FAME, Ctes.CARACS_Y_FAME);
        
        g.drawString("Vie maximale : " +perso.getStatsAct().getHp()+ " (" +perso.getStatsMaxAct().getHp()+ " + " 
                +(perso.getStatsAct().getHp() - perso.getStatsMaxAct().getHp())+ ")", Ctes.CARACS_X_HP, Ctes.CARACS_Y_HP);
        if (perso.getStatsMaxAct().getHp() == perso.getStatsMax().getHp())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_HP);
        
        g.drawString("Mana maximale : " +perso.getStatsAct().getMp()+ " (" +perso.getStatsMaxAct().getMp()+ " + " 
                +(perso.getStatsAct().getMp() - perso.getStatsMaxAct().getMp())+ ")", Ctes.CARACS_X_MP, Ctes.CARACS_Y_MP);
        if (perso.getStatsMaxAct().getMp() == perso.getStatsMax().getMp())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_MP);
        
        g.drawString("Attaque : " +perso.getStatsAct().getAtk()+ " (" +perso.getStatsMaxAct().getAtk()+ " + " 
                +(perso.getStatsAct().getAtk() - perso.getStatsMaxAct().getAtk())+ ")", Ctes.CARACS_X_ATK, Ctes.CARACS_Y_ATK);
        if (perso.getStatsMaxAct().getAtk() == perso.getStatsMax().getAtk())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_ATK);
        
        g.drawString("Défense : " +perso.getStatsAct().getDef()+ " (" +perso.getStatsMaxAct().getDef()+ " + " 
                +(perso.getStatsAct().getDef() - perso.getStatsMaxAct().getDef())+ ")", Ctes.CARACS_X_DEF, Ctes.CARACS_Y_DEF);
        if (perso.getStatsMaxAct().getDef() == perso.getStatsMax().getDef())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_DEF);
        
        g.drawString("Vitesse : " +perso.getStatsAct().getSpd()+ " (" +perso.getStatsMaxAct().getSpd()+ " + " 
                +(perso.getStatsAct().getSpd() - perso.getStatsMaxAct().getSpd())+ ")", Ctes.CARACS_X_SPD, Ctes.CARACS_Y_SPD);
        if (perso.getStatsMaxAct().getSpd() == perso.getStatsMax().getSpd())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_SPD);
        
        g.drawString("Dextérité : " +perso.getStatsAct().getDex()+ " (" +perso.getStatsMaxAct().getDex()+ " + " 
                +(perso.getStatsAct().getDex() - perso.getStatsMaxAct().getDex())+ ")", Ctes.CARACS_X_DEX, Ctes.CARACS_Y_DEX);
        if (perso.getStatsMaxAct().getDex() == perso.getStatsMax().getDex())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_DEX);
        
        g.drawString("Vitalité : " +perso.getStatsAct().getVit()+ " (" +perso.getStatsMaxAct().getVit()+ " + " 
                +(perso.getStatsAct().getVit() - perso.getStatsMaxAct().getVit())+ ")", Ctes.CARACS_X_VIT, Ctes.CARACS_Y_VIT);
        if (perso.getStatsMaxAct().getVit() == perso.getStatsMax().getVit())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_VIT);
        
        g.drawString("Sagesse : " +perso.getStatsAct().getWis()+ " (" +perso.getStatsMaxAct().getWis()+ " + " 
                +(perso.getStatsAct().getWis() - perso.getStatsMaxAct().getWis())+ ")", Ctes.CARACS_X_WIS, Ctes.CARACS_Y_WIS);
        if (perso.getStatsMaxAct().getWis() == perso.getStatsMax().getWis())
            g.drawString("MAX", Ctes.CARACS_X_MAX, Ctes.CARACS_Y_WIS);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/caracs/fond.png");
        barreXp = new Image("ressources/caracs/barreXp.png");
    }
    
    public void afficheClasse(Personnage p, Graphics g) {
        switch (p.getIdClass()) {
            case 1:
                g.drawString("Chevalier", Ctes.CARACS_X_CLASS, Ctes.CARACS_Y_CLASS);
                break;
            case 2:
                g.drawString("Assassin", Ctes.CARACS_X_CLASS, Ctes.CARACS_Y_CLASS);
                break;
            case 3:
                g.drawString("Sorcier", Ctes.CARACS_X_CLASS, Ctes.CARACS_Y_CLASS);
                break;
            case 4:
                g.drawString("Prêtre", Ctes.CARACS_X_CLASS, Ctes.CARACS_Y_CLASS);
                break;
        }
    }
}
