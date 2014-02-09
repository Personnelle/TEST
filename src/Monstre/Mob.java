
package Monstre;

import BDD.Requete;
import Carte.Carte;
import Carte.Mur;
import Degats.TextDegat.TYPE;
import Degats.TextDegatList;
import Personnages.Personnage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mob {
    private int id;
    private Image img;
    private SpriteMob sprite;
    private float x;
    private float y;
    private int hp;
    private int hpMax;
    private int dex;
    private int atk;
    private int def;
    private int spd;
    private int xp;
    private float fame;
    private int rangeMove;
    private long lastTir = 0;
    private TextDegatList textDegats = new TextDegatList();
    
    public Mob(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM MOB WHERE ID = " +id+ ";");
            
            sprite = new SpriteMob(id);
            img = sprite.getBas();
            hpMax = rs.getInt("HP");
            hp = hpMax;
            dex = rs.getInt("DEX");
            atk = rs.getInt("ATK");
            def = rs.getInt("DEF");
            spd = rs.getInt("SPD");
            xp = rs.getInt("XP");
            fame = rs.getFloat("FAME");
            rangeMove = rs.getInt("RANGEMOVE");
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(Mob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() { return id; }
    public Image getImg() { return img; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getX1() { return x + img.getWidth(); }
    public float getY1() { return y + img.getHeight(); }
    public int getHp() { return hp; }
    public int getHpMax() { return hpMax; }
    public int getDex() { return dex; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getSpd() { return spd; }
    public int getXp() { return xp; }
    public float getFame() { return fame; }
    public int getRangeMove() { return rangeMove; }
    public boolean isAlive() { return hp > 0; }
    public TextDegatList getTextDegat() { return textDegats; }
    
    public void afficher(Graphics g) {
        if (isAlive()) 
            g.drawImage(img, x, y);
        textDegats.affiche(g);
    }
    
    public void deplacer(Personnage p, Carte c) {
        float distHaut = getY() - p.getY1();
        float distBas = p.getY() - getY1();
        float distGauche = getX() - p.getX1();
        float distDroite = p.getX() - getX1();
        
        if (distHaut > distGauche && distHaut > distDroite && distHaut > rangeMove) {
            y -= (1 + (2 * spd) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionHaut(m)) y = m.getY1();
            }
            img = sprite.getHaut();
        }
        else if (distGauche > distBas && distGauche > rangeMove) {
            x -= (1 + (2 * spd) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionGauche(m)) x = m.getX1();
            }
            img = sprite.getGauche();
        }
        else if (distBas > distDroite && distBas > rangeMove) {
            y += (1 + (2 * spd) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionBas(m)) y = m.getY() - img.getHeight();
            }
            img = sprite.getBas();
        }
        else if (distDroite > rangeMove) {
            x += (1 + (2 * spd) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionDroite(m)) x = m.getX() - img.getWidth();
            }
            img = sprite.getDroite();
        }
    }
    
    public boolean isInCollisionHaut(Mur m) {
        return getY() < m.getY1() && getY1() > m.getY1();
    }
    
    public boolean isInCollisionBas(Mur m) {
        return getY1() > m.getY() && getY() < m.getY();
    }
    
    public boolean isInCollisionDroite(Mur m) {
        return getX1() > m.getX() && getX() < m.getX();
    }
    
    public boolean isInCollisionGauche(Mur m) {
        return getX() < m.getX1() && getX1() > m.getX1();
    }
    
    public boolean tirer() {
        if ((System.currentTimeMillis() - lastTir) > (1000 / (1 + dex / 100))) {
            lastTir = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    
    public void perdVie(int degats, TYPE type) {
        int damage = degats;
        if (type == TYPE.DAMAGE) damage -= def;
        if (damage <= 0) damage = 1;
        if (hp - damage < 0) hp = 0;
        else hp -= damage;
        textDegats.add(damage, type, this);
    }
}