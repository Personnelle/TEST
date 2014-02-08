package Monstre;

import BDD.Requete;
import Carte.Carte;
import Personnages.Personnage;
import Projectiles.ProjectileList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobList {
    private List<Mob> listeMob;
    private Carte c;
    private ProjectileList listeProj;
    
    public MobList(Carte c) {
        listeMob = new ArrayList<>();
        this.c = c;
        listeProj = new ProjectileList();
    }
    
    public void init() {
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT IDMOB FROM CORRESPMOBMAP WHERE IDMAP = " +c.getId() + ";");
            
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
    
    public void deplacer(Personnage p) {
        testMobDead();
        for (Mob m : listeMob) m.deplacer(p, c);
        listeProj.deplacer(c.getMurs());
    }
    
    public void testMobDead() {
        List<Mob> supp = new ArrayList<>();
        
        for (Mob m : listeMob) {
            if (!m.isAlive()) supp.add(m);
        }
        
        for (Mob m : supp) listeMob.remove(m);
    }

    public List<Mob> getListeMob() { return listeMob; }
    public Carte getCarte() { return c; }
    public ProjectileList getListeProj() { return listeProj; }
    public boolean isFinish() { return listeMob.isEmpty(); }
}
