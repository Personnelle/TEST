package Scenes;

import Constantes.Ctes;
import Objets.Equipement;
import Objets.Objet;
import Personnages.Personnage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneBarreInfo extends Scene {
    private Image fond;
    private Image caracs;
    private Image barreVie;
    private Image barreMana;
    private Image selection;
    private boolean caracOpen;
    private Personnage perso;
    private int pourcentVie;
    private int pourcentMana;
    private boolean selectStuff;
    private int selected;
    private boolean showInfoBulle;
    private int posInfoBulle;
    private boolean infoBulleOnStuff;
    
    public SceneBarreInfo(Personnage p) {
        super();
        setPriority(0);
        perso = p;
    }
    
    @Override
    public String toString() { return "BarreInfo"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, Ctes.MENUBARRE_X_BARRE, Ctes.MENUBARRE_Y_BARRE);
        perso.getInventaire().afficherInMenu(g);
        g.drawImage(caracs, Ctes.MENUBARRE_X_CARACS, Ctes.MENUBARRE_Y_CARACS);
        pourcentVie = perso.getVie() * 100 / perso.getStatsAct().getHp();
        g.drawImage(barreVie.getSubImage(0, 0, pourcentVie, barreVie.getHeight()), Ctes.MENUBARRE_X_VIE, Ctes.MENUBARRE_Y_VIE);
        pourcentMana = perso.getMana() * 100 / perso.getStatsAct().getMp();
        g.drawImage(barreMana.getSubImage(0, 0, pourcentMana, barreMana.getHeight()), Ctes.MENUBARRE_X_MANA, Ctes.MENUBARRE_Y_MANA);
        if (selectStuff)
            g.drawImage(selection, Ctes.MENUBARRE_X_CASEEQUIP + (selected - 1) * Ctes.MENUBARRE_ESP_CASES - 2, Ctes.MENUBARRE_Y_CASES - 2);
        else
            g.drawImage(selection, Ctes.MENUBARRE_X_CASEINV + (selected - 1) * Ctes.MENUBARRE_ESP_CASES - 2, Ctes.MENUBARRE_Y_CASES - 2);
        if (showInfoBulle)
            afficheInfoBulle(g);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        refreshPerso();
        Input input = gc.getInput();
        
        if (input.isKeyPressed(Input.KEY_C)) {
            if (caracOpen) {
                Scene s = Main.Game.manager.getSence("Village");
                if (s == null)
                    s = Main.Game.manager.getSence("Exte");
                s.setState(STATE.ON);
                Main.Game.manager.removeSence("Caracs");
                caracOpen = false;
            }
            else {
                Scene s = Main.Game.manager.getSence("Village");
                if (s == null)
                    s = Main.Game.manager.getSence("Exte");
                s.setState(STATE.FREEZE_NEXT);
                Main.Game.manager.addSence(new SceneCaracs(perso));
                Main.Game.manager.sort();
                caracs = new Image("ressources/barreInfo/caracs_open.png");
                caracOpen = true;
            }
        }
        else if (input.isKeyPressed(Input.KEY_TAB)) {
            selectStuff = !selectStuff;
            selected = 1;
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            if (selectStuff) {
                if (selected == 4) selected = 1;
                else selected++;
            }
            else {
                if (selected == 6) selected = 1;
                else selected++;
            }
        }
        else if (input.isKeyPressed(Input.KEY_LEFT)) {
            if (selectStuff) {
                if (selected == 1) selected = 4;
                else selected--;
            }
            else {
                if (selected == 1) selected = 6;
                else selected--;
            }
        }
        else if (input.isKeyPressed(Input.KEY_1)) {
            if (selected != 1) selected = 1;
            else {
                if (selectStuff) useStuff();
                else useInv();
            }
        }
        else if (input.isKeyPressed(Input.KEY_2)) {
            if (selected != 2) selected = 2;
            else {
                if (selectStuff) useStuff();
                else useInv();
            }
        }
        else if (input.isKeyPressed(Input.KEY_3)) {
            if (selected != 3) selected = 3;
            else {
                if (selectStuff) useStuff();
                else useInv();
            }
        }
        else if (input.isKeyPressed(Input.KEY_4)) {
            if (selected != 4) selected = 4;
            else {
                if (selectStuff) useStuff();
                else useInv();
            }
        }
        else if (input.isKeyPressed(Input.KEY_5)) {
            if (!selectStuff) useInv();
        }
        else if (input.isKeyPressed(Input.KEY_6)) {
            if (!selectStuff) useInv();
        }
        else if (input.isKeyPressed(Input.KEY_E)) {
            if (selectStuff) useStuff();
            else useInv();
        }
        else if (input.isKeyPressed(Input.KEY_G)) {
            if (selectStuff) jeterStuff();
            else jeterInv();
        }
        
        if (input.getMouseX() > Ctes.MENUBARRE_X_CARACS && input.getMouseX() < Ctes.MENUBARRE_X_CARACS + caracs.getWidth() && 
                input.getMouseY() > Ctes.MENUBARRE_Y_CARACS && input.getMouseY() < Ctes.MENUBARRE_Y_CARACS + caracs.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (caracOpen) {
                    Scene s = Main.Game.manager.getSence("Village");
                    if (s == null)
                        s = Main.Game.manager.getSence("Exte");
                    s.setState(STATE.ON);
                    Main.Game.manager.removeSence("Caracs");
                    caracOpen = false;
                }
                else {
                    Scene s = Main.Game.manager.getSence("Village");
                    if (s == null)
                        s = Main.Game.manager.getSence("Exte");
                    s.setState(STATE.FREEZE_NEXT);
                    Main.Game.manager.addSence(new SceneCaracs(perso));
                    Main.Game.manager.sort();
                    caracs = new Image("ressources/barreInfo/caracs_open.png");
                    caracOpen = true;
                }
            }
            else
                caracs = new Image("ressources/barreInfo/caracs_open.png");
        }
        else {
            if (!caracOpen)
                caracs = new Image("ressources/barreInfo/caracs.png");
            testMouse(input);
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/barreInfo/barre.png");
        caracs = new Image("ressources/barreInfo/caracs.png");
        barreVie = new Image("ressources/barreInfo/barreVie.png");
        barreMana = new Image("ressources/barreInfo/barreMana.png");
        selection = new Image("ressources/barreInfo/selection.png");
        caracOpen = false;
        selectStuff = false;
        selected = 1;
        showInfoBulle = false;
        posInfoBulle = 1;
        infoBulleOnStuff = true;
    }
    
    public void refreshPerso() {
        Scene s = Main.Game.manager.getSence("Village");
        if (s == null)
            s = Main.Game.manager.getSence("Exte");
        
        perso = s.getPerso();
    }
    
    public void useStuff() {
        if (selected <= perso.getInventaire().getObjetEquip().size()) {
            if (perso.getInventaire().getObjetInv().size() < 6) {
                perso.getInventaire().ajouteInv(perso.getInventaire().getObjetEquip().get(selected - 1));
                perso.getInventaire().retireStuff(perso.getInventaire().getObjetEquip().get(selected - 1));
                perso.getStatsAct().calcul(perso);
            }
        }
    }
    
    public void useInv() {
        if (selected <= perso.getInventaire().getObjetInv().size()) {
            if (perso.getInventaire().getObjetInv().get(selected - 1) instanceof Equipement) {
                Equipement e = (Equipement) perso.getInventaire().getObjetInv().get(selected - 1);
                if (e.getIdClass() == perso.getIdClass()) {
                    perso.getInventaire().ajouteStuff(e);
                    perso.getInventaire().retireInv(perso.getInventaire().getObjetInv().get(selected - 1));
                    perso.getStatsAct().calcul(perso);
                }
            }
            else {
                if (perso.getInventaire().getObjetInv().get(selected - 1).use(perso)) {
                    perso.getInventaire().retireInv(perso.getInventaire().getObjetInv().get(selected - 1));
                    perso.getStatsAct().calcul(perso);
                }
            }
        }
    }
    
    public void jeterStuff() {
        if (selected <= perso.getInventaire().getObjetEquip().size()) {
            if (perso.getInventaire().getObjetEquip().get(selected - 1).getNiveau() != -1) {
                Main.Game.manager.addSence(new SceneConfirmJeter(perso.getInventaire(), selected - 1, true));
                Scene s = Main.Game.manager.getSence("Village");
                if (s == null)
                    s = Main.Game.manager.getSence("Exte");
                s.setState(STATE.FREEZE_NEXT);
                setState(STATE.FREEZE_NEXT);
            }
        }
    }
    
    public void jeterInv() {
         if (selected <= perso.getInventaire().getObjetInv().size()) {
             Main.Game.manager.addSence(new SceneConfirmJeter(perso.getInventaire(), selected - 1, false));
             Scene s = Main.Game.manager.getSence("Village");
            if (s == null)
                s = Main.Game.manager.getSence("Exte");
            s.setState(STATE.FREEZE_NEXT);
            setState(STATE.FREEZE_NEXT);
         }
    }
    
    public void testMouse(Input input) {
        boolean isTrue = false;
        for (int i = 0 ; i < 4 ; i++) {
            if (input.getMouseX() > Ctes.MENUBARRE_X_SELECTSTUFF + i * Ctes.MENUBARRE_ESP_CASES && 
                    input.getMouseX() < Ctes.MENUBARRE_X_SELECTSTUFF + i * Ctes.MENUBARRE_ESP_CASES + Ctes.MENUBARRE_TAILLE_CASE && 
                    input.getMouseY() > Ctes.MENUBARRE_Y_SELECT && input.getMouseY() < Ctes.MENUBARRE_Y_SELECT + Ctes.MENUBARRE_TAILLE_CASE) {
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    if (selected == i + 1 && selectStuff)
                        useStuff();
                    else {
                        selectStuff = true;
                        selected = i + 1;
                        showInfoBulle = true;
                        isTrue = true;
                    }
                }
                else {
                    showInfoBulle = true;
                    isTrue = true;
                    posInfoBulle = i + 1;
                    infoBulleOnStuff = true;
                }
            }
        }
        for (int i = 0 ; i < 6 ; i++) {
            if (input.getMouseX() > Ctes.MENUBARRE_X_SELECTINV + i * Ctes.MENUBARRE_ESP_CASES && 
                    input.getMouseX() < Ctes.MENUBARRE_X_SELECTINV + i * Ctes.MENUBARRE_ESP_CASES + Ctes.MENUBARRE_TAILLE_CASE && 
                    input.getMouseY() > Ctes.MENUBARRE_Y_SELECT && input.getMouseY() < Ctes.MENUBARRE_Y_SELECT + Ctes.MENUBARRE_TAILLE_CASE) {
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    if (selected == i + 1 && !selectStuff)
                        useInv();
                    else {
                        selectStuff = false;
                        selected = i + 1;
                        showInfoBulle = true;
                        isTrue = true;
                    }
                }
                else {
                    showInfoBulle = true;
                    isTrue = true;
                    posInfoBulle = i + 1;
                    infoBulleOnStuff = false;
                }
            }
        }
        if (!isTrue)
            showInfoBulle = false;
    }
    
    public void afficheInfoBulle(Graphics g) throws SlickException {
        if (infoBulleOnStuff) {
            Equipement e = perso.getInventaire().getObjetEquip().get(posInfoBulle - 1);
            if (e.getNiveau() != -1) {
                afficheInfoStuff(e, g);
            }
        }
        else {
            if (perso.getInventaire().getObjetInv().size() >= posInfoBulle) {
                Objet o = perso.getInventaire().getObjetInv().get(posInfoBulle - 1);
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
            String desc = "Projectile : " +e.getBonusSortOuNbProj();
            if (e.isPerforant()) desc += " - Perforant";
            g.drawString(desc, Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString("Angle de tir : " +e.getDureeSortOuAngle()+"°", Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString("Portée : " +e.getCoutManaOuRange()+ "m", Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i++;
            g.drawString("Dégâts : " +e.getDegatMin()+ " - " +e.getDegatMax(), Ctes.INFOBULLE_X_DESC, Ctes.INFOBULLE_Y_DESC + i * Ctes.INFOBULLE_ESP_LIGNES);
            i += 2;
        }
        else if (e.getType() == 4) {
            int widthMana = g.getFont().getWidth(e.getCoutManaOuRange()+" MP");
            g.drawString(e.getCoutManaOuRange()+" MP", Ctes.INFOBULLE_X_FOND + infoBulle.getWidth() - widthMana - 
                    (Ctes.INFOBULLE_X_NIVEAU - Ctes.INFOBULLE_X_FOND), Ctes.INFOBULLE_Y_NIVEAU);
            
            String desc = "Augmente votre ";
            if (e.getIdClass() == 1) { desc += "défense "; }
            else if (e.getIdClass() == 2) { desc += "dextérité "; }
            else if (e.getIdClass() == 3) { desc += "attaque "; }
            else if (e.getIdClass() == 4) { desc += "régénération "; }
            desc = desc + "de"; 
            String desc2 = e.getBonusSortOuNbProj() + " pendant " +e.getDureeSortOuAngle()+ " secondes.";
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