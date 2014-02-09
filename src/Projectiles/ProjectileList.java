package Projectiles;

import BDD.Requete;
import Carte.Mur;
import Degats.TextDegat.TYPE;
import Monstre.Mob;
import Personnages.Personnage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class ProjectileList {
    private List<Projectile> liste = new ArrayList<>();
    
    public ProjectileList() {}
    
    public void addProjectiles(int idArme, Input input, float x, float y, int bonusDegat) {
        Vecteur vBase = new Vecteur(x, y, input.getMouseX(), input.getMouseY());
        int nbProj = 1;
        float angleCouvert = 0;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT NBPROJ, ANGLE FROM PROJECTILE WHERE IDARME = " +idArme+ ";");
            
            nbProj = rs.getInt("NBPROJ");
            angleCouvert = rs.getFloat("ANGLE");
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectileList.class.getName()).log(Level.SEVERE, null, ex);
        }
        float angleFirstProj = vBase.getArgument() - angleCouvert/2;
        if (angleFirstProj >= 360) angleFirstProj -= 360;
        else if (angleFirstProj < 0) angleFirstProj += 360;
        
        float angleEntreProj = 0;
        if (nbProj != 1) angleEntreProj = angleCouvert / (nbProj - 1);
        
        for (int i = 0 ; i < nbProj ; i++)
            liste.add(new Projectile(idArme, x, y, new Vecteur(angleFirstProj + i * angleEntreProj), bonusDegat));
    }
    
    public void addProjectiles(int idMob, Personnage p, float x, float y, int bonusDegat) {
        Vecteur vBase = new Vecteur(x, y, (p.getX() + p.getX1()) / 2, (p.getY() + p.getY1()) / 2);
        int nbProj = 1;
        float angleCouvert = 0;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT NBPROJ, ANGLE FROM PROJECTILE WHERE IDMOB = " +idMob+ ";");
            
            nbProj = rs.getInt("NBPROJ");
            angleCouvert = rs.getFloat("ANGLE");
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectileList.class.getName()).log(Level.SEVERE, null, ex);
        }
        float angleFirstProj = vBase.getArgument() - angleCouvert/2;
        if (angleFirstProj >= 360) angleFirstProj -= 360;
        else if (angleFirstProj < 0) angleFirstProj += 360;
        
        float angleEntreProj = 0;
        if (nbProj != 1) angleEntreProj = angleCouvert / (nbProj - 1);
        
        for (int i = 0 ; i < nbProj ; i++)
            liste.add(new Projectile(idMob, x, y, new Vecteur(angleFirstProj + i * angleEntreProj), bonusDegat, true));
    }

    public List<Projectile> getListe() { return liste; }
    
    public void afficher(Graphics g) { 
        for (Projectile p : liste) p.afficher(g);
    }
    
    public void suppProj() {
        List<Projectile> supp = new ArrayList<>();
        
        for (Projectile p : liste) {
            if (!p.isActive()) supp.add(p);
        }
        
        for (Projectile p : supp) liste.remove(p);
    }
    
    public void deplacer(List<Mur> murs) {
        suppProj();
        for (Projectile p : liste) p.deplacer(murs);
    }
    
    public void collisionWithMob(List<Mob> mobs) {
        for (Projectile p : liste) {
            for (Mob m : mobs) {
                if (p.getX() < m.getX1() && p.getX1() > m.getX() && p.getY() < m.getY1() && p.getY1() > m.getY() && p.isActive()) {
                    TYPE t = TYPE.DAMAGE;
                    if (p.isTrueDamage()) t = TYPE.TRUEDAMAGE;
                    m.perdVie((int) ((Math.random() * 100) % (p.getDegatMax() - p.getDegatMin()) + p.getDegatMin()), t);
                    p.setActive(false);
                }
            }
        }
    }
    
    public void collisionWithPerso(Personnage perso) {
        for (Projectile p : liste) {
            if (p.getX() < perso.getX1() && p.getX1() > perso.getX() && p.getY() < perso.getY1() && 
                    p.getY1() > perso.getY() && p.isActive()) {
                TYPE t = TYPE.DAMAGE;
                if (p.isTrueDamage()) t = TYPE.TRUEDAMAGE;
                perso.perdVie((int) ((Math.random() * 100) % (p.getDegatMax() - p.getDegatMin()) + p.getDegatMin()), t);
                p.setActive(false);
            }
        }
    }
}
