package Objets;

import BDD.Requete;
import Constantes.Ctes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SacLoot {
    private List<Objet> liste = new ArrayList<>();
    private float x;
    private float y;
    private Image imgSac;
    private Image ramasser;
    private Image cadre;
    
    public SacLoot(int idMob, float x, float y) {
        this.x = x;
        this.y = y;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM MOBLOOT WHERE IDMOB = " +idMob+ ";");
            
            while (rs.next()) {
                double rd = Math.random();
                if (rd < rs.getDouble("POURCENT"))
                    liste.add(new Objet(rs.getInt("IDOBJET")));
            }
            
            rq.closeDB();
            
            imgSac = new Image("ressources/autres/sac.png");
            ramasser = new Image("ressources/autres/ramasser.png");
            cadre = new Image("ressources/autres/cadreLoot.png");
        } catch (SlickException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SacLoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Objet> getListe() { return liste; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getX1() { return x + imgSac.getWidth(); }
    public float getY1() { return y + imgSac.getHeight(); }
    public Image getImgSac() { return imgSac; }
    public boolean isEmpty() { return liste.isEmpty(); }
    
    public void afficherSacs(Graphics g) {
        if (!isEmpty()) g.drawImage(imgSac, x, y);
    }
    
    public void afficherLoots(Graphics g) {
        g.drawImage(ramasser, Ctes.CARTE_X_RAMASSER, Ctes.CARTE_Y_RAMASSER);
        g.drawImage(cadre, Ctes.CARTE_X_CADRELOOT, Ctes.CARTE_Y_CADRELOOT);
        int i = 0, j = 0;
        for (Objet o : liste) {
            o.afficher(g, Ctes.CARTE_X_LOOTOBJ + i * Ctes.CARTE_ESP_LOOTOBJ, 
                    Ctes.CARTE_Y_LOOTOBJ + j * Ctes.CARTE_ESP_LOOTOBJ);
            i++;
            if (i == 3) {
                i = 0;
                j = 1;
            }
        }
    }
}
