package Projectiles;

import BDD.Requete;
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
    
    public void addProjectiles(int idArme, Input input, float x, float y) {
        Vecteur vBase = new Vecteur(x, y, input.getMouseX(), input.getMouseY());
        int nbProj = 1;
        float angleCouvert = 0;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT BONUSSORTETNBPROJ, DUREESORTETANGLE FROM OBJET WHERE ID = " +idArme+ ";");
            
            nbProj = rs.getInt("BONUSSORTETNBPROJ");
            angleCouvert = rs.getFloat("DUREESORTETANGLE");
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectileList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        float angleFirstProj = vBase.getArgument() + angleCouvert/2;
        if (angleFirstProj >= 360) angleFirstProj -= 360;
        else if (angleFirstProj < 0) angleFirstProj += 360;
        
        float angleEntreProj = 0;
        if (nbProj != 1) angleEntreProj = angleCouvert / (nbProj - 1);
        
        for (int i = 0 ; i < nbProj ; i++)
            liste.add(new Projectile(idArme, x, y, new Vecteur(angleFirstProj + i * angleEntreProj)));
    }

    public List<Projectile> getListe() { return liste; }
    
    public void afficher(Graphics g) { 
        for (Projectile p : liste) p.afficher(g);
    }
    
    public void suppProj() {
        List<Projectile> supp = new ArrayList<>();
        
        for (Projectile p : liste) {
            if (p.isActive()) supp.add(p);
        }
        
        for (Projectile p : supp) liste.remove(p);
    }
}
