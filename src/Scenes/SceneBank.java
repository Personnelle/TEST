package Scenes;

import BDD.Requete;
import Constantes.Ctes;
import Objets.Bank;
import Objets.Equipement;
import Objets.Objet;
import Personnages.Personnage;
import Projectiles.Projectile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneBank extends Scene {
    private Personnage perso;
    private Image fermer;
    private Image fond;
    private Image selection;
    private Image stocker;
    private Image retirer;
    private int selectX;
    private int selectY;
    private Bank bank;
    private boolean showInfoBulle;
    private int posInfoBulle;
    private boolean infoBulleOnInv;
    
    public SceneBank(Personnage p) {
        super();
        setPriority(1);
        perso = p;
        try {
            bank = new Bank(p.getId());
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(SceneBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() { return "Bank"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, Ctes.BANK_X_FOND, Ctes.BANK_Y_FOND);
        g.drawImage(fermer, Ctes.BANK_X_FERMER, Ctes.BANK_Y_FERMER);
        g.drawImage(stocker, Ctes.BANK_X_STOCKER, Ctes.BANK_Y_STOCKER);
        g.drawImage(retirer, Ctes.BANK_X_RETIRER, Ctes.BANK_Y_RETIRER);
        perso.getInventaire().afficherInBank(g);
        bank.afficher(g);
        if (selectY != 7) 
            g.drawImage(selection, Ctes.BANK_X_SELECT + (selectX - 1) * Ctes.BANK_ESP_SELECT, Ctes.BANK_Y_SELECT + (selectY - 1) * Ctes.BANK_ESP_SELECT);
        else g.drawImage(selection, Ctes.BANK_X_SELECT + (selectX - 1) * Ctes.BANK_ESP_SELECT, Ctes.BANK_Y_SELECT_INV);
        if (showInfoBulle)
            afficheInfoBulle(g);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_F)) {
            Main.Game.manager.getSence("Village").setState(STATE.ON);
            updateTable();
            Main.Game.manager.removeSence(this);
            Main.Game.manager.getSence("BarreInfo").setState(STATE.ON);
            gc.getInput().clearKeyPressedRecord();
        }
        else if (input.isKeyPressed(Input.KEY_LEFT)) {
            if (selectX == 1) selectX = 6;
            else selectX -= 1;
        }
        else if (input.isKeyPressed(Input.KEY_UP)) {
            if (selectY == 1) selectY = 7;
            else selectY -= 1;
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            if (selectX == 6) selectX = 1;
            else selectX += 1;
        }
        else if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (selectY == 7) selectY = 1;
            else selectY += 1;
        }
        else if (input.isKeyPressed(Input.KEY_S)) {
            if (selectY == 7 && bank.getListe().size() < 36 && perso.getInventaire().getObjetInv().size() >= selectX) {
                bank.add(perso.getInventaire().getObjetInv().get(selectX - 1));
                perso.getInventaire().retireInv(perso.getInventaire().getObjetInv().get(selectX - 1));
            }
        }
        else if (input.isKeyPressed(Input.KEY_R)) {
            int pos = (selectX + (selectY - 1) * 6) - 1;
            if (selectY != 7 && perso.getInventaire().getObjetInv().size() < 6 && pos < bank.getListe().size()) {
                perso.getInventaire().ajouteInv(bank.getListe().get(pos));
                bank.getListe().remove(pos);
            }
        }
        
        if (input.getMouseX() > Ctes.BANK_X_FERMER && input.getMouseX() < Ctes.BANK_X_FERMER + fermer.getWidth() && 
                input.getMouseY() > Ctes.BANK_Y_FERMER && input.getMouseY() < Ctes.BANK_Y_FERMER + fermer.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.getSence("Village").setState(STATE.ON);
                Main.Game.manager.removeSence(this);
                Main.Game.manager.getSence("BarreInfo").setState(STATE.ON);
                gc.getInput().clearKeyPressedRecord();
            }
            else {
                fermer = new Image("ressources/bank/fermer_hover.png");
                stocker = new Image("ressources/bank/stocker.png");
                retirer = new Image("ressources/bank/retirer.png");
            }
        }
        else if (input.getMouseX() > Ctes.BANK_X_STOCKER && input.getMouseX() < Ctes.BANK_X_STOCKER + stocker.getWidth() && 
                input.getMouseY() > Ctes.BANK_Y_STOCKER && input.getMouseY() < Ctes.BANK_Y_STOCKER + stocker.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (selectY == 7 && bank.getListe().size() < 36 && perso.getInventaire().getObjetInv().size() >= selectX) {
                    bank.add(perso.getInventaire().getObjetInv().get(selectX - 1));
                    perso.getInventaire().retireInv(perso.getInventaire().getObjetInv().get(selectX - 1));
                }
            }
            else {
                fermer = new Image("ressources/bank/fermer.png");
                stocker = new Image("ressources/bank/stocker_hover.png");
                retirer = new Image("ressources/bank/retirer.png");
            }
        }
        else if (input.getMouseX() > Ctes.BANK_X_RETIRER && input.getMouseX() < Ctes.BANK_X_RETIRER + retirer.getWidth() && 
                input.getMouseY() > Ctes.BANK_Y_RETIRER && input.getMouseY() < Ctes.BANK_Y_RETIRER + retirer.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                int pos = (selectX + (selectY - 1) * 6) - 1;
                if (selectY != 7 && perso.getInventaire().getObjetInv().size() < 6 && pos < bank.getListe().size()) {
                    perso.getInventaire().ajouteInv(bank.getListe().get(pos));
                    bank.getListe().remove(pos);
                }
            }
            else {
                fermer = new Image("ressources/bank/fermer.png");
                stocker = new Image("ressources/bank/stocker.png");
                retirer = new Image("ressources/bank/retirer_hover.png");
            }
        }
        else {
            fermer = new Image("ressources/bank/fermer.png");
            stocker = new Image("ressources/bank/stocker.png");
            retirer = new Image("ressources/bank/retirer.png");
        }
        testMouse(input);
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fermer = new Image("ressources/bank/fermer.png");
        fond = new Image("ressources/bank/fond.png");
        selection = new Image("ressources/bank/selection.png");
        stocker = new Image("ressources/bank/stocker.png");
        retirer = new Image("ressources/bank/retirer.png");
        selectX = 1;
        selectY = 1;
        showInfoBulle = false;
        posInfoBulle = 1;
        infoBulleOnInv = true;
    }
    
    public void updateTable() {
        try {
            Requete rq = new Requete();
            
            rq.request("DELETE FROM BANK WHERE IDPERSO = " +perso.getId()+ ";");
            
            for (Objet o : bank.getListe())
                rq.request("INSERT INTO BANK VALUES (" +perso.getId()+ ", " +o.getId()+ ");");
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SceneBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testMouse(Input input) {
        boolean isTrue = false;
        for (int i = 0 ; i < 6 ; i++) {
            if (input.getMouseX() > Ctes.BANK_X_SELECT + i * Ctes.BANK_ESP_SELECT && 
                    input.getMouseX() < Ctes.BANK_X_SELECT + i * Ctes.BANK_ESP_SELECT + Ctes.BANK_TAILLE_CASE) {
                for (int j = 0 ; j < 7 ; j++) {
                    if (j == 6) {
                        if (input.getMouseY() > Ctes.BANK_Y_SELECT_INV && input.getMouseY() < Ctes.BANK_Y_SELECT_INV + Ctes.BANK_TAILLE_CASE) {
                            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                                if (!(selectX == i + 1 && selectY == 7)) {
                                    selectX = i + 1;
                                    selectY = 7;
                                }
                                else {
                                    if (selectY == 7 && bank.getListe().size() < 36 && perso.getInventaire().getObjetInv().size() >= selectX) {
                                        bank.add(perso.getInventaire().getObjetInv().get(selectX - 1));
                                        perso.getInventaire().retireInv(perso.getInventaire().getObjetInv().get(selectX - 1));
                                    }
                                }
                            }
                            else {
                                showInfoBulle = true;
                                isTrue = true;
                                posInfoBulle = i;
                                infoBulleOnInv = true;
                            }
                        }
                    }
                    else {
                        if (input.getMouseY() > Ctes.BANK_Y_SELECT + j * Ctes.BANK_ESP_SELECT && 
                                input.getMouseY() < Ctes.BANK_Y_SELECT + j * Ctes.BANK_ESP_SELECT + Ctes.BANK_TAILLE_CASE) {
                            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                                if (!(selectX == i + 1 && selectY == j + 1)) {
                                    selectX = i + 1;
                                    selectY = j + 1;
                                }
                                else {
                                    int pos = (selectX + (selectY - 1) * 6) - 1;
                                    if (selectY != 7 && perso.getInventaire().getObjetInv().size() < 6 && pos < bank.getListe().size()) {
                                        perso.getInventaire().ajouteInv(bank.getListe().get(pos));
                                        bank.getListe().remove(pos);
                                    }
                                }
                            }
                            else {
                                showInfoBulle = true;
                                isTrue = true;
                                posInfoBulle = i + j * 6;
                                infoBulleOnInv = false;
                            }
                        }
                    }
                }
            }
        }
        
        if (!isTrue)
            showInfoBulle = false;
    }
    
    public void afficheInfoBulle(Graphics g) throws SlickException {
        if (infoBulleOnInv) {
            if (perso.getInventaire().getObjetInv().size() > posInfoBulle) {
                Objet o = perso.getInventaire().getObjetInv().get(posInfoBulle);
                if (o instanceof Equipement)
                    afficheInfoStuff((Equipement) o, g);
                else
                    afficheInfoObjet(o, g);
            }
        }
        else {
            if (bank.getListe().size() > posInfoBulle) {
                Objet o = bank.getListe().get(posInfoBulle);
                if (o instanceof Equipement)
                    afficheInfoStuff((Equipement) o, g);
                else
                    afficheInfoObjet(o, g);
            }
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
