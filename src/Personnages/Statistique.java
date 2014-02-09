package Personnages;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistique {
    public enum ETATSTAT {ACT, MAXACT, MAX, NOUV};
    private ETATSTAT etat;
    private int hp;
    private int mp;
    private int atk;
    private int def;
    private int spd;
    private int dex;
    private int vit;
    private int wis;
    
    public Statistique(int id, ETATSTAT e) throws ClassNotFoundException, SQLException {
        if (e == ETATSTAT.ACT) {
            hp = 100;
            mp = 100;
            atk = 1;
            def = 0;
            spd = 1;
            dex = 1;
            vit = 1;
            wis = 1;
            etat = e;
        }
        else if (e == ETATSTAT.MAXACT) {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM PERSONNAGE WHERE ID = " +id+ ";");

            hp = rs.getInt("HP");
            mp = rs.getInt("MP");
            atk = rs.getInt("ATK");
            def = rs.getInt("DEF");
            spd = rs.getInt("SPD");
            dex = rs.getInt("DEX");
            vit = rs.getInt("VIT");
            wis = rs.getInt("WIS");
            etat = e;
            
            rq.closeDB();
        }
        else if (e == ETATSTAT.MAX) {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM INFOCLASS WHERE IDCLASS = " +id+ ";");

            hp = rs.getInt("HP");
            mp = rs.getInt("MP");
            atk = rs.getInt("ATK");
            def = rs.getInt("DEF");
            spd = rs.getInt("SPD");
            dex = rs.getInt("DEX");
            vit = rs.getInt("VIT");
            wis = rs.getInt("WIS");
            etat = e;

            rq.closeDB();
        }
        else if (e == ETATSTAT.NOUV) {
            hp = 100;
            mp = 100;
            atk = 1;
            def = 0;
            spd = 1;
            dex = 1;
            vit = 1;
            wis = 1;
            etat = ETATSTAT.MAXACT;
        }
    }

    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getSpd() { return spd; }
    public int getDex() { return dex; }
    public int getVit() { return vit; }
    public int getWis() { return wis; }
    public ETATSTAT getEtat() { return etat; }

    public void addHp(int hp) { this.hp += hp; }
    public void addMp(int mp) { this.mp += mp; }
    public void addAtk(int atk) { this.atk += atk; }
    public void addDef(int def) { this.def += def; }
    public void addSpd(int spd) { this.spd += spd; }
    public void addDex(int dex) { this.dex += dex; }
    public void addVit(int vit) {  this.vit += vit; }
    public void addWis(int wis) { this.wis += wis; }
    
    public void setHp(int hp) { this.hp = hp; }
    public void setMp(int mp) { this.mp = mp; }
    
    public void calcul(Personnage p) {
        if (etat == ETATSTAT.ACT) {
            hp = p.calculHpAct();
            mp = p.calculMpAct();
            atk = p.calculAtkAct();
            def = p.calculDefAct();
            spd = p.calculSpdAct();
            dex = p.calculDexAct();
            vit = p.calculVitAct();
            wis = p.calculWisAct();
        }
    }
}