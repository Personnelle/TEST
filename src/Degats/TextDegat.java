package Degats;

import Personnages.Personnage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextDegat {
    public enum TYPE {HEAL, DAMAGE, TRUEDAMAGE};
    
    private TYPE type;
    private int value;
    private int varY;
    private Personnage perso;
    private long lastTick;

    public TextDegat(TYPE type, int value, Personnage p) {
        this.type = type;
        this.value = value;
        varY = 0;
        perso = p;
        lastTick = System.currentTimeMillis() - 3000;
    }
    
    public void TextDegat(TextDegat t) {
        type = t.getType();
        value = t.getValue();
        varY = t.getVarY();
        perso = t.getPerso();
    }

    public TYPE getType() { return type; }
    public int getValue() { return value; }
    public int getVarY() { return varY; }
    public Personnage getPerso() { return perso; }
    
    public void affiche(Graphics g) {
        if (type == TYPE.HEAL) {
            g.setColor(Color.green);
            String text = "+" + Integer.toString(value);
            int width = g.getFont().getWidth(text);
            int height = g.getFont().getHeight(text);
            g.drawString(text, perso.getX() + (perso.getImg().getWidth() - width) / 2, perso.getY() - height - 2 * varY);
            g.setColor(Color.white);
        }
        else if (type == TYPE.DAMAGE) {
            g.setColor(Color.red);
            String text = "+" + Integer.toString(value);
            int width = g.getFont().getWidth(text);
            int height = g.getFont().getHeight(text);
            g.drawString(text, perso.getX() + (perso.getImg().getWidth() - width) / 2, perso.getY() - height - 2 * varY);
            g.setColor(Color.white);
        }
        else if (type == TYPE.TRUEDAMAGE) {
            g.setColor(Color.yellow);
            String text = "+" + Integer.toString(value);
            int width = g.getFont().getWidth(text);
            int height = g.getFont().getHeight(text);
            g.drawString(text, perso.getX() + (perso.getImg().getWidth() - width) / 2, perso.getY() - height - 2 * varY);
            g.setColor(Color.white);
        }
        if (System.currentTimeMillis() - lastTick > 30) {
            lastTick = System.currentTimeMillis();
            varY++;
        }
    }
}
