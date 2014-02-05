package Personnages;

import BDD.Requete;
import Carte.Carte;
import Carte.Mur;
import Constantes.Ctes;
import Objets.Equipement;
import Objets.Inventaire;
import Personnages.Statistique.ETATSTAT;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Personnage {
    private int id;
    private int niveau;
    private int xp;
    private int xpNeed;
    private int fame;
    private int vie;
    private int mana;
    private Statistique statsAct;
    private Statistique statsMaxAct;
    private Statistique statsMax;
    private float x;
    private float y;
    private int idClass;
    private SpritePerso sprite;
    private Image img;
    private Inventaire inventaire;
    
    public Personnage(int id, boolean nouveau) throws ClassNotFoundException, SQLException, SlickException {
        if (!nouveau) {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM PERSONNAGE WHERE ID = " +id+ ";");
            
            this.id = id;
            niveau = rs.getInt("NIVEAU");
            xp = rs.getInt("XP");
            xpNeed = niveau * (niveau + 1);
            fame = rs.getInt("FAME");
            idClass = rs.getInt("IDCLASS");

            x = Ctes.VILLAGE_X_STARTPERSO;
            y = Ctes.VILLAGE_Y_STARTPERSO;
            inventaire = new Inventaire(id);
            statsMaxAct = new Statistique(id, ETATSTAT.MAXACT);
            statsMax = new Statistique(idClass, ETATSTAT.MAX);
            statsAct = new Statistique(id, ETATSTAT.ACT);
            statsAct.calcul(this);
            vie = statsAct.getHp();
            mana = statsAct.getMp();
            
            rs = rq.select("SELECT SPRITE FROM INFOCLASS WHERE IDCLASS = " +idClass+ ";");
            
            sprite = new SpritePerso(rs.getString("SPRITE"));
            img = sprite.getBas();

            rq.closeDB();
        } else {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT MAX(ID) FROM PERSONNAGE;");
            
            this.id = rs.getInt("MAX(ID)") + 1;
            niveau = 1;
            xp = 0;
            xpNeed = niveau * (niveau + 1);
            fame = 0;
            idClass = id;
            inventaire = new Inventaire(true, idClass);
            statsMaxAct = new Statistique(id, ETATSTAT.NOUV);
            statsMax = new Statistique(idClass, ETATSTAT.MAX);
            statsAct = new Statistique(id, ETATSTAT.ACT);
            statsAct.calcul(this);
            vie = statsAct.getHp();
            mana = statsAct.getMp();
            x = Ctes.VILLAGE_X_STARTPERSO;
            y = Ctes.VILLAGE_Y_STARTPERSO;
            
            rs = rq.select("SELECT SPRITE FROM INFOCLASS WHERE IDCLASS = " +idClass+ ";");
            
            sprite = new SpritePerso(rs.getString("SPRITE"));
            img = sprite.getBas();
            
            rq.closeDB();
        }
    }
    
    public int getId() { return id; }
    public int getIdClass() { return idClass; }
    public int getNiveau() { return niveau; }
    public int getXp() { return xp; }
    public int getXpNeed() { return xpNeed; }
    public int getFame() { return fame; }
    public Statistique getStatsMax() { return statsMax; }
    public Statistique getStatsAct() { return statsAct; }
    public Statistique getStatsMaxAct() { return statsMaxAct; }
    public int getVie() { return vie; }
    public int getMana() { return mana; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getX1() { return x + img.getWidth(); }
    public float getY1() { return y + img.getHeight(); }
    public Image getImg() { return img; }
    public Inventaire getInventaire() { return inventaire; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    public void afficher(Graphics g) {
        g.drawImage(img, x, y);
    }
    
    public void deplacer(Input i, Carte c) {
        if (i.isKeyDown(Input.KEY_Z)) {
            y -= (1 + (2 * statsAct.getSpd()) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionHaut(m)) y = m.getY1();
            }
            img = sprite.getHaut();
        }
        if (i.isKeyDown(Input.KEY_Q)) {
            x -= (1 + (2 * statsAct.getSpd()) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionGauche(m)) x = m.getX1();
            }
            img = sprite.getGauche();
        }
        if (i.isKeyDown(Input.KEY_S)) {
            y += (1 + (2 * statsAct.getSpd()) / 100f);
            for (Mur m : c.getMurs()) {
                if (isInCollisionBas(m)) y = m.getY() - img.getHeight();
            }
            img = sprite.getBas();
        }
        if (i.isKeyDown(Input.KEY_D)) {
            x += (1 + (2 * statsAct.getSpd()) / 100f);
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
    
    public void addHp(int hp) { if (statsMaxAct.getHp() < statsMax.getHp()) statsMaxAct.addHp(hp); }
    public void addMp(int mp) { if (statsMaxAct.getMp() < statsMax.getMp()) statsMaxAct.addMp(mp); }
    public void addAtk(int atk) { if (statsMaxAct.getAtk() < statsMax.getAtk()) statsMaxAct.addAtk(atk); }
    public void addDef(int def) { if (statsMaxAct.getDef() < statsMax.getDef()) statsMaxAct.addDef(def); }
    public void addSpd(int spd) { if (statsMaxAct.getSpd() < statsMax.getSpd()) statsMaxAct.addSpd(spd); }
    public void addDex(int dex) { if (statsMaxAct.getDex() < statsMax.getDex()) statsMaxAct.addDex(dex); }
    public void addVit(int vit) { if (statsMaxAct.getVit() < statsMax.getVit()) statsMaxAct.addVit(vit); }
    public void addWis(int wis) { if (statsMaxAct.getWis() < statsMax.getWis()) statsMaxAct.addWis(wis); }
    
    public int calculHpAct() {
        int hp = statsMaxAct.getHp();
        for (Equipement e : inventaire.getObjetEquip())
            hp += e.getBonusHp();
        return hp;
    }
    
    public int calculMpAct() {
        int mp = statsMaxAct.getMp();
        for (Equipement e : inventaire.getObjetEquip())
            mp += e.getBonusMp();
        return mp;
    }
    
    public int calculAtkAct() {
        int atk = statsMaxAct.getAtk();
        for (Equipement e : inventaire.getObjetEquip())
            atk += e.getBonusAtk();
        return atk;
    }
    
    public int calculDefAct() {
        int def = statsMaxAct.getDef();
        for (Equipement e : inventaire.getObjetEquip())
            def += e.getBonusDef();
        return def;
    }
    
    public int calculSpdAct() {
        int spd = statsMaxAct.getSpd();
        for (Equipement e : inventaire.getObjetEquip())
            spd += e.getBonusSpd();
        return spd;
    }
    
    public int calculDexAct() {
        int dex = statsMaxAct.getDex();
        for (Equipement e : inventaire.getObjetEquip())
            dex += e.getBonusDex();
        return dex;
    }
    
    public int calculVitAct() {
        int vit = statsMaxAct.getVit();
        for (Equipement e : inventaire.getObjetEquip())
            vit += e.getBonusVit();
        return vit;
    }
    
    public int calculWisAct() {
        int wis = statsMaxAct.getWis();
        for (Equipement e : inventaire.getObjetEquip())
            wis += e.getBonusWis();
        return wis;
    }
    
    public void heal(int gainHp) {
        vie += gainHp;
        if (vie > statsAct.getHp())
            vie = statsAct.getHp();
    }
    
    public void tirer(Input input) {
        
    }
}