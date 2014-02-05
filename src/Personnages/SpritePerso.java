package Personnages;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpritePerso {
    private Image sprite;
    
    public SpritePerso(String chemin) {
        try {
            sprite = new Image(chemin);
        } catch (SlickException ex) {
            Logger.getLogger(SpritePerso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Image getHaut() { return sprite.getSubImage(75, 0, 25, 25); }
    public Image getBas() { return sprite.getSubImage(0, 0, 25, 25); }
    public Image getGauche() { return sprite.getSubImage(25, 0, 25, 25); }
    public Image getDroite() { return sprite.getSubImage(50, 0, 25, 25); }
}
