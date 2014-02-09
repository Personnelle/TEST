package Monstre;

import BDD.Requete;
import Carte.Carte;
import Objets.SacLoot;
import Personnages.Personnage;
import Projectiles.ProjectileList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;

public class MobList {
    private List<Mob> listeMob;
    private Carte c;
    private ProjectileList listeProj;
    private List<SacLoot> listeLoots;
    private int lootAffich;
    
    public MobList(Carte c) {
        listeMob = new ArrayList<>();
        this.c = c;
        listeProj = new ProjectileList();
        listeLoots = new ArrayList<>();
        lootAffich = -1;
    }
    
    public void init() {
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM CORRESPMOBMAP WHERE IDMAP = " +c.getId() + ";");
            
            while (rs.next()) listeMob.add(new Mob(rs.getInt("IDMOB"), rs.getFloat("POPX"), rs.getFloat("POPY")));
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MobList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tirer(Personnage p) {
        for (Mob m : listeMob) {
            if (m.tirer()) 
                listeProj.addProjectiles(m.getId(), p, (m.getX() + m.getX1()) / 2, (m.getY() + m.getY1()) / 2, m.getAtk());
        }
    }
    
    public void afficher(Graphics g) {
        for (Mob m : listeMob) m.afficher(g);
        listeProj.afficher(g);
        for (SacLoot s : listeLoots) s.afficherSacs(g);
        if (lootAffich != -1) listeLoots.get(lootAffich).afficherLoots(g);
    }
    
    public void deplacer(Personnage p) {
        testMobDead(p);
        tirer(p);
        for (Mob m : listeMob) m.deplacer(p, c);
        listeProj.deplacer(c.getMurs());
        listeProj.collisionWithPerso(p);
        setLootAffich(p);
    }
    
    public void testMobDead(Personnage p) {
        List<Mob> supp = new ArrayList<>();
        
        for (Mob m : listeMob) {
            if (!m.isAlive()) {
                supp.add(m);
                p.gainXp(m.getXp());
                p.gainFame(m.getFame());
                listeLoots.add(new SacLoot(m.getId(), m.getX(), m.getY()));
            }
        }
        
        for (Mob m : supp) listeMob.remove(m);
    }
    
    public void setLootAffich(Personnage p) {
        lootAffich = -1;
        
        List<SacLoot> l = new ArrayList<>();
        for (SacLoot s : listeLoots) {
            if (s.isEmpty()) l.add(s);
        }
        for (SacLoot s : l) listeLoots.remove(s);
        
        int i = 0;
        for (SacLoot s : listeLoots) {
            if (p.getX() < s.getX1() && p.getX1() > s.getX() && p.getY() < s.getY1() && p.getY1() > s.getY())
                lootAffich = i;
            i++;
        }
    }

    public List<Mob> getListeMob() { return listeMob; }
    public Carte getCarte() { return c; }
    public ProjectileList getListeProj() { return listeProj; }
    public boolean isFinish() { return listeMob.isEmpty(); }
}
