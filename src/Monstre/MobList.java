package Monstre;

import BDD.Requete;
import Carte.Carte;
import Constantes.Ctes;
import Objets.Equipement;
import Objets.Objet;
import Objets.SacLoot;
import Personnages.Personnage;
import Projectiles.Projectile;
import Projectiles.ProjectileList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MobList {
    private List<Mob> listeMob;
    private Carte c;
    private ProjectileList listeProj;
    private List<SacLoot> listeLoots;
    private int lootAffich;
    private boolean affichInfoBulle;
    private int numInfoBulle;
    private int selected;
    
    public MobList(Carte c) {
        listeMob = new ArrayList<>();
        this.c = c;
        listeProj = new ProjectileList();
        listeLoots = new ArrayList<>();
        lootAffich = -1;
        affichInfoBulle = false;
        numInfoBulle = 0;
        selected = -1;
    }
    
    public void init() {
        try {
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT * FROM CORRESPMOBMAP WHERE IDMAP = " +c.getId() + ";");
            
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
    
    public void afficher(Graphics g) throws SlickException {
        for (Mob m : listeMob) m.afficher(g);
        listeProj.afficher(g);
        for (SacLoot s : listeLoots) s.afficherSacs(g);
        if (lootAffich != -1) listeLoots.get(lootAffich).afficherLoots(g);
        if (affichInfoBulle) afficheInfoBulle(g);
    }
    
    public void deplacer(Personnage p) {
        testMobDead(p);
        tirer(p);
        for (Mob m : listeMob) m.deplacer(p, c);
        listeProj.deplacer(c.getMurs());
        listeProj.collisionWithPerso(p);
        setLootAffich(p);
    }
    
    public void testMobDead(Personnage p) {
        List<Mob> supp = new ArrayList<>();
        
        for (Mob m : listeMob) {
            if (!m.isAlive()) {
                supp.add(m);
                p.gainXp(m.getXp());
                p.gainFame(m.getFame());
                listeLoots.add(new SacLoot(m.getId(), m.getX(), m.getY()));
            }
        }
        
        for (Mob m : supp) listeMob.remove(m);
    }
    
    public void setLootAffich(Personnage p) {
        lootAffich = -1;
        
        List<SacLoot> l = new ArrayList<>();
        for (SacLoot s : listeLoots) {
            if (s.isEmpty()) l.add(s);
        }
        for (SacLoot s : l) listeLoots.remove(s);
        
        int i = 0;
        for (SacLoot s : listeLoots) {
            if (p.getX() < s.getX1() && p.getX1() > s.getX() && p.getY() < s.getY1() && p.getY1() > s.getY())
                lootAffich = i;
            i++;
        }
    }

    public List<Mob> getListeMob() { return listeMob; }
    public Carte getCarte() { return c; }
    public ProjectileList getListeProj() { return listeProj; }
    public boolean isFinish() { return listeMob.isEmpty(); }
    
    public void testLoot(Input input, Personnage p) {
        if (lootAffich != -1) {
            boolean isTrue = false;
            for (int j = 0 ; j < 2 ; j++) {
                for (int i = 0 ; i < 3 ; i++) {
                    if (input.getMouseX() > Ctes.CARTE_X_LOOTOBJ + i * Ctes.CARTE_ESP_LOOTOBJ && 
                            input.getMouseX() < Ctes.CARTE_X_LOOTOBJ + i * Ctes.CARTE_ESP_LOOTOBJ + Ctes.CARTE_TAILLE_CASELOOT && 
                            input.getMouseY() > Ctes.CARTE_Y_LOOTOBJ + j * Ctes.CARTE_ESP_LOOTOBJ && 
                            input.getMouseY() < Ctes.CARTE_Y_LOOTOBJ + j * Ctes.CARTE_ESP_LOOTOBJ + Ctes.CARTE_TAILLE_CASELOOT) {
                        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                            if (selected == i + j * 3) {
                                if (p.getInventaire().getObjetInv().size() < 6 && 
                                        listeLoots.get(lootAffich).getListe().size() > selected) {
                                    p.getInventaire().getObjetInv().add(listeLoots.get(lootAffich).getListe().get(selected));
                                    listeLoots.get(lootAffich).getListe().remove(selected);
                                }
                            }
                            else {
                                selected = i + j * 3;
                                affichInfoBulle = true;
                                isTrue = true;
                            }
                        }
                        else {
                            affichInfoBulle = true;
                            isTrue = true;
                            numInfoBulle = i + j * 3;
                        }
                    }
                }
            }
            if (!isTrue)
                affichInfoBulle = false;
            
            if (input.isKeyPressed(Input.KEY_R)) {
                if (p.getInventaire().getObjetInv().size() < 6 && 
                        listeLoots.get(lootAffich).getListe().size() > 0) {
                    p.getInventaire().getObjetInv().add(listeLoots.get(lootAffich).getListe().get(0));
                    listeLoots.get(lootAffich).getListe().remove(0);
                }
            }
        }
        else
            affichInfoBulle = false;
    }
    
    public void afficheInfoBulle(Graphics g) throws SlickException {
        if (listeLoots.get(lootAffich).getListe().size() > numInfoBulle) {
            Objet o = listeLoots.get(lootAffich).getListe().get(numInfoBulle);
            if (o instanceof Equipement) afficheInfoStuff((Equipement) o, g);
            else afficheInfoObjet(o, g);
        }
    }
    
    public void afficheInfoStuff(Equipement e, Graphics g) throws SlickException {
        Image infoBulle = new Image("ressources/barreInfo/fondInfoBulle.png");
        g.drawImage(infoBulle, Ctes.INFOBULLE_X_FOND, Ctes.INFOBULLE_Y_FOND); 
        int width = g.getFont().getWidth(e.getNom());
        g.drawString(e.getNom(), Ctes.INFOBULLE_X_FOND + (infoBulle.getWidth() - width) / 2, Ctes.INFOBULLE_Y_NOMBOJET);
        g.drawString(e.getNiveau()+"", Ctes.INFOBULLE_X_NIVEAU, Ctes.INFOBULLE_Y_NIVEAU);
        
        int i = 0;
        if (e.getType() == 1) {
            Projectile p = new Projectile(e.getId());
            String desc = "Projectile : " +p.getNbProj();
            if (p.isPerforant()) desc += " - Perforant";
            g.drawString(desc, Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            if (p.isTrueDamage()) {
                g.drawString("Ignore défense adverse", Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
                i++;
            }
            g.drawString("Angle de tir : " +p.getAngle()+"°", Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString("Portée : " +p.getRange()+ "m", Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString("Dégâts : " +p.getDegatMin()+ " - " +p.getDegatMax(), Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i += 2;
        }
        else if (e.getType() == 4) {
            int widthMana = g.getFont().getWidth(e.getCoutMana()+" MP");
            g.drawString(e.getCoutMana()+" MP", Ctes.INFOBULLE_X_FOND + infoBulle.getWidth() - widthMana - 
                    (Ctes.INFOBULLE_X_NIVEAU - Ctes.INFOBULLE_X_FOND), Ctes.INFOBULLE_Y_NIVEAU);
            
            String desc = "Augmente votre ";
            if (e.getIdClass() == 1) { desc += "défense "; }
            else if (e.getIdClass() == 2) { desc += "dextérité "; }
            else if (e.getIdClass() == 3) { desc += "attaque "; }
            else if (e.getIdClass() == 4) { desc += "régénération "; }
            desc = desc + "de"; 
            String desc2 = e.getBonusSort() + " pendant " +e.getDureeSort()+ " secondes.";
            g.drawString(desc, Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString(desc2, Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i += 2;
        }
        
        int j = 0;
        if (e.getBonusHp() != 0) {
            g.drawString("+"+e.getBonusHp()+" Vie max.", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusMp() != 0) {
            g.drawString("+"+e.getBonusMp()+" Mana max.", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusAtk() != 0) {
            g.drawString("+"+e.getBonusAtk()+" Attaque", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusDef() != 0) {
            g.drawString("+"+e.getBonusDef()+" Défense", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusSpd() != 0) {
            g.drawString("+"+e.getBonusSpd()+" Vitesse", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusDex() != 0) {
            g.drawString("+"+e.getBonusDex()+" Dextérité", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusVit() != 0) {
            g.drawString("+"+e.getBonusVit()+" Vitalité", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
        if (e.getBonusWis() != 0) {
            g.drawString("+"+e.getBonusWis()+" Sagesse", Ctes.INFOBULLE_X_DESC + j * Ctes.INFOBULLE_2X_DESC, 
                    Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            if (j == 0) j = 1;
            else j = 0;
            if (j == 0) i++;
        }
    }
    
    public void afficheInfoObjet(Objet o, Graphics g) throws SlickException {
        Image infoBulle = new Image("ressources/barreInfo/fondInfoBulle.png");
        g.drawImage(infoBulle, Ctes.INFOBULLE_X_FOND, Ctes.INFOBULLE_Y_FOND); 
        int width = g.getFont().getWidth(o.getNom());
        g.drawString(o.getNom(), Ctes.INFOBULLE_X_FOND + (infoBulle.getWidth() - width) / 2, Ctes.INFOBULLE_Y_NOMBOJET);
        
        width = g.getFont().getWidth(o.getDesc());
        if (width > infoBulle.getWidth() - 20) {
            String phraseEntiere = o.getDesc();
            int i = 0;
            while (!"".equals(phraseEntiere)) {
                String phraseEntiereTest = phraseEntiere;
                String aEcrire = "";
                boolean ecrire = false;
                
                while (!ecrire) {
                    String test;
                    if (phraseEntiereTest.indexOf(" ") != -1) {
                        test = phraseEntiereTest.substring(0, phraseEntiereTest.indexOf(" ")+1);
                        if (g.getFont().getWidth(aEcrire + test) < infoBulle.getWidth() - 20) {
                            aEcrire += test;
                            phraseEntiereTest = phraseEntiereTest.substring(test.length());
                        }
                        else 
                            ecrire = true;
                    }
                    else {
                        if (g.getFont().getWidth(aEcrire + phraseEntiereTest) < infoBulle.getWidth() - 20) {
                            aEcrire += phraseEntiereTest;
                            phraseEntiereTest = "";
                            ecrire = true;
                        }
                        else
                            ecrire = true;
                    }
                }
                width = g.getFont().getWidth(aEcrire);
                g.drawString(aEcrire, Ctes.INFOBULLE_X_FOND + (infoBulle.getWidth() - width) / 2, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
                phraseEntiere = phraseEntiereTest;
                i++;
            }
        }
        else
            g.drawString(o.getDesc(), Ctes.INFOBULLE_X_FOND + (infoBulle.getWidth() - width) / 2, Ctes.INFOBULLE_Y_DESC);
    }
}
