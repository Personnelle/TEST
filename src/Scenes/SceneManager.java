package Scenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SceneManager {
    private List<Scene> sences;
    private GameContainer gc;
    
    public SceneManager(GameContainer gc) {
        this.gc = gc;
        sences = new ArrayList<>();
    }
    
    public void addSence(Scene sence) {
        sences.add(sence);
        try {
            sence.init(gc);
        } catch (SlickException e) {
            Collections.sort(sences);
        }
    }
    
    public void removeSence(Scene sence) { sences.remove(sence); }
    
    public void removeAll() {
        while (!sences.isEmpty()) sences.remove(0);
    }
    
    public boolean removeSence(String sence) {
        for (int i = 0 ; i < sences.size() ; i++) {
            if (sences.get(i).toString().equals(sence)) {
                sences.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public Scene getSence(String sence) {
        for (int i = 0 ; i < sences.size() ; i++) {
            if (sences.get(i).toString().equals(sence)) return sences.get(i);
        }
        return null;
    }
    
    public void sort() { Collections.sort(sences); }
    public void clear() { sences = new ArrayList<>(); }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        for (int i = 0 ; i < sences.size() ; i++) sences.get(i).render(gc, g);
    }

    public void update(GameContainer gc, int t) throws SlickException {
        for (int i = 0 ; i < sences.size() ; i++) sences.get(i).update(gc, t);
    }
}