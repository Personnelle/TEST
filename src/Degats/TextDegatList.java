package Degats;

import Degats.TextDegat.TYPE;
import Monstre.Mob;
import Personnages.Personnage;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Graphics;

public class TextDegatList {
    private List<TextDegat> liste;
    
    public TextDegatList() {
        liste = new ArrayList<>();
    }
    
    public void add(int value, TYPE type, Personnage p) {
        liste.add(new TextDegat(type, value, p));
    }
    
    public void add(int value, TYPE type, Mob m) {
        liste.add(new TextDegat(type, value, m));
    }
    
    public void affiche(Graphics g) {
        testRemove();
        for (TextDegat t : liste)
            t.affiche(g);
    }
    
    public void testRemove() {
        List<TextDegat> supp = new ArrayList<>();
        
        for (TextDegat t : liste) {
            if (t.getVarY() > 20)
                supp.add(t);
        }
        
        for (TextDegat t : supp)
            liste.remove(t);
    }
}
