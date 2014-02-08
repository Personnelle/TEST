package Monstre;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpriteMob {
    private Image sprite;
    private int firstX;
    private int firstHeight;
    private int firstWidth;
    private int secondX;
    private int secondHeight;
    private int secondWidth;
    private int thirdX;
    private int thirdHeight;
    private int thirdWidth;
    private int fourthX;
    private int fourthHeight;
    private int fourthWidth;
    
    public SpriteMob(int idMob) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM SPRITEMOB WHERE IDMOB = " +idMob+ ";");
        
        sprite = new Image(rs.getString("IMG"));
        firstX = rs.getInt("FIRSTX");
        firstHeight = rs.getInt("FIRSTH");
        firstWidth = rs.getInt("FIRSTW");
        secondX = rs.getInt("SECONDX");
        secondHeight = rs.getInt("SECONDH");
        secondWidth = rs.getInt("SECONDW");
        thirdX = rs.getInt("THIRDX");
        thirdHeight = rs.getInt("THIRDH");
        thirdWidth = rs.getInt("THIRDW");
        fourthX = rs.getInt("FOURTHX");
        fourthHeight = rs.getInt("FOURTHH");
        fourthWidth = rs.getInt("FOURTHW");
        
        rq.closeDB();
    }
    
    public Image getBas() { return sprite.getSubImage(firstX, 0, firstWidth, firstHeight); }
    public Image getGauche() { return sprite.getSubImage(secondX, 0, secondWidth, secondHeight); }
    public Image getDroite() { return sprite.getSubImage(thirdX, 0, thirdWidth, thirdHeight); }
    public Image getHaut() { return sprite.getSubImage(fourthX, 0, fourthWidth, fourthHeight); }
}
