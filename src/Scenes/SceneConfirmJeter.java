package Scenes;

import Constantes.Ctes;
import Objets.Inventaire;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SceneConfirmJeter extends Scene {
    private Image fond;
    private Image oui;
    private Image non;
    private Inventaire inv;
    private int ind;
    private boolean stuff;
    
    public SceneConfirmJeter(Inventaire inventaire, int i, boolean equip) {
        super();
        setPriority(1);
        inv = inventaire;
        ind = i;
        stuff = equip;
    }
    
    @Override
    public String toString() { return "ConfirmJeter"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        int width;
        g.drawImage(fond, Ctes.CONFIRMJETER_X_FOND, Ctes.CONFIRMJETER_Y_FOND);
        g.drawImage(oui, Ctes.CONFIRMJETER_X_OUI, Ctes.CONFIRMJETER_Y_OUI);
        g.drawImage(non, Ctes.CONFIRMJETER_X_NON, Ctes.CONFIRMJETER_Y_NON);
        width = g.getFont().getWidth("Voulez vous vraiment détruire :");
        g.drawString("Voulez vous vraiment détruire :", Ctes.CONFIRMJETER_X_FOND + (fond.getWidth() - width) / 2, Ctes.CONFIRMJETER_Y_TEXT);
        if (stuff) {
            width = g.getFont().getWidth(inv.getObjetEquip().get(ind).getNom());
            g.drawString(inv.getObjetEquip().get(ind).getNom(), Ctes.CONFIRMJETER_X_FOND + (fond.getWidth() - width) / 2, Ctes.CONFIRMJETER_Y_NOMOBJET);
        }
        else {
            width = g.getFont().getWidth(inv.getObjetInv().get(ind).getNom());
            g.drawString(inv.getObjetInv().get(ind).getNom(), Ctes.CONFIRMJETER_X_FOND + (fond.getWidth() - width) / 2, Ctes.CONFIRMJETER_Y_NOMOBJET);
        }
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        if (input.isKeyPressed(Input.KEY_O)) {
            if (stuff) 
                inv.retireStuff(inv.getObjetEquip().get(ind));
            else
                inv.retireInv(inv.getObjetInv().get(ind));
            quitScene();
        }
        else if (input.isKeyPressed(Input.KEY_N)) 
            quitScene();
        
        if (input.getMouseX() > Ctes.CONFIRMJETER_X_OUI && input.getMouseX() < Ctes.CONFIRMJETER_X_OUI + oui.getWidth() && 
                input.getMouseY() > Ctes.CONFIRMJETER_Y_OUI && input.getMouseY() < Ctes.CONFIRMJETER_Y_OUI + oui.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (stuff) 
                    inv.retireStuff(inv.getObjetEquip().get(ind));
                else
                    inv.retireInv(inv.getObjetInv().get(ind));
                quitScene();
            }
            else {
                oui = new Image("ressources/confirmJeter/oui_hover.png");
                non = new Image("ressources/confirmJeter/non.png");
            }
        }
        else if (input.getMouseX() > Ctes.CONFIRMJETER_X_NON && input.getMouseX() < Ctes.CONFIRMJETER_X_NON + non.getWidth() && 
                input.getMouseY() > Ctes.CONFIRMJETER_Y_NON && input.getMouseY() < Ctes.CONFIRMJETER_Y_NON + non.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                quitScene();
            }
            else {
                oui = new Image("ressources/confirmJeter/oui.png");
                non = new Image("ressources/confirmJeter/non_hover.png");
            }
        }
        else {
            oui = new Image("ressources/confirmJeter/oui.png");
            non = new Image("ressources/confirmJeter/non.png");
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/confirmJeter/fond.png");
        oui = new Image("ressources/confirmJeter/oui.png");
        non = new Image("ressources/confirmJeter/non.png");
    }
    
    public void quitScene() {
        Scene s = Main.Game.manager.getSence("Village");
        if (s == null)
            s = Main.Game.manager.getSence("Exte");
        s.setState(STATE.ON);
        Main.Game.manager.getSence("BarreInfo").setState(STATE.ON);
        Main.Game.manager.removeSence(this);
        Main.Game.manager.sort();
    }
}
