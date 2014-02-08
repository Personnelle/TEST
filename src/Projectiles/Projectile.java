package Projectiles;

import BDD.Requete;
import Carte.Mur;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Projectile {
    private int idArme;
    private int degatMin;
    private int degatMax;
    private int range;
    private boolean perforant;
    private boolean truedamage;
    private float x;
    private float y;
    private Vecteur v;
    private Image img;
    private boolean active;
    private float speed;
    
    private int nbProj; //Use for infobulle
    private float angle; //Use for infobulle

    public Projectile(int idArme) {
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM PROJECTILE WHERE IDARME = " +idArme+ ";");
            
            degatMin = rs.getInt("DEGATMIN");
            degatMax = rs.getInt("DEGATMAX");
            range = rs.getInt("RANGE");
            perforant = (rs.getInt("PERFORANT") == 1);
            truedamage = (rs.getInt("TRUEDAMAGE") == 1);
            nbProj = rs.getInt("NBPROJ");
            angle = rs.getFloat("ANGLE");
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Projectile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Projectile(int idArme, float x, float y, Vecteur v, int bonusDegat) {
        this.idArme = idArme;
        active = true;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM PROJECTILE WHERE IDARME = " +idArme+ ";");
            
            degatMin = rs.getInt("DEGATMIN");
            degatMax = rs.getInt("DEGATMAX");
            range = rs.getInt("RANGE");
            perforant = (rs.getInt("PERFORANT") == 1);
            truedamage = (rs.getInt("TRUEDAMAGE") == 1);
            img = new Image(rs.getString("IMG"));
            speed = rs.getFloat("SPEED");
            img.setRotation(v.getArgument());
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(Projectile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.v = v;
        if (v.getCoefX() < 0) {
            if (Math.abs(v.getCoefX()) < Math.abs(v.getCoefY())) {
                this.x = x - img.getWidth() / 2;
                if (v.getCoefY() > 0) this.y = y;
                else this.y = y - img.getHeight();
            }
            else {
                this.x = x - img.getWidth();
                this.y = y - img.getHeight() / 2;
            }
        }
        else {
            if (Math.abs(v.getCoefX()) < Math.abs(v.getCoefY())) {
                this.x = x - img.getWidth() / 2;
                if (v.getCoefY() > 0) this.y = y;
                else this.y = y - img.getHeight();
            }
            else {
                this.x = x;
                this.y = y - img.getHeight() / 2;
            }
        }
    }
    
    public Projectile(int idMob, float x, float y, Vecteur v, int bonusDegat, boolean mob) {
        this.idArme = idMob;
        active = true;
        
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM PROJECTILE WHERE IDMOB = " +idMob+ ";");
            
            degatMin = rs.getInt("DEGATMIN");
            degatMax = rs.getInt("DEGATMAX");
            range = rs.getInt("RANGE");
            perforant = (rs.getInt("PERFORANT") == 1);
            truedamage = (rs.getInt("TRUEDAMAGE") == 1);
            img = new Image(rs.getString("IMG"));
            speed = rs.getFloat("SPEED");
            img.setRotation(v.getArgument());
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(Projectile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.v = v;
        if (v.getCoefX() < 0) {
            if (Math.abs(v.getCoefX()) < Math.abs(v.getCoefY())) {
                this.x = x - img.getWidth() / 2;
                if (v.getCoefY() > 0) this.y = y;
                else this.y = y - img.getHeight();
            }
            else {
                this.x = x - img.getWidth();
                this.y = y - img.getHeight() / 2;
            }
        }
        else {
            if (Math.abs(v.getCoefX()) < Math.abs(v.getCoefY())) {
                this.x = x - img.getWidth() / 2;
                if (v.getCoefY() > 0) this.y = y;
                else this.y = y - img.getHeight();
            }
            else {
                this.x = x;
                this.y = y - img.getHeight() / 2;
            }
        }
    }

    public int getIdArme() { return idArme; }
    public int getDegatMin() { return degatMin; }
    public int getDegatMax() { return degatMax; }
    public int getRange() { return range; }
    public boolean isPerforant() { return perforant; }
    public boolean isTrueDamage() { return truedamage; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getX1() { return x + img.getWidth(); }
    public float getY1() { return y + img.getHeight(); }
    public Vecteur getV() { return v; }
    public Image getImg() { return img; }
    public boolean isActive() { return active; }
    public int getNbProj() { return nbProj; }
    public float getAngle() { return angle; }
    
    public void afficher(Graphics g) {
        if (isActive()) g.drawImage(img, x, y);
    }
    
    public void deplacer(List<Mur> murs) {
        x += v.getCoefX() * speed;
        y += v.getCoefY() * speed;
        range -= speed;
        if (range <= 0) active = false;
        testCollision(murs);
    }
    
    public void testCollision(List<Mur> murs) {
        if (!perforant) {
            boolean b = false;
            for (Mur m : murs) {
                if (getX() < m.getX1() && getX1() > m.getX() && getY() < m.getY1() && getY1() > m.getY())
                    b = true;
            }
            if (b) active = false;
        }
    }
}
