package Scenes;

import BDD.Requete;
import Constantes.Ctes;
import Objets.Equipement;
import Personnages.Personnage;
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

public class SceneContinueGame extends Scene {
    private Image fond;
    private Image pageSuiv;
    private Image pagePrec;
    private Image retour;
    private Image cadreSelect;
    private Image jouer;
    private List<Image> cadres = new ArrayList<>();
    private List<Personnage> persos = new ArrayList<>();
    private Image supp;
    private Image supp_hover;
    private int page;
    private boolean isLastPage;
    private int selected;
    private int count;
    
    public SceneContinueGame() {
        super();
        setPriority(1);
    }
    
    @Override
    public String toString() { return "ContinueGame"; }
    
    @Override
    protected void CustomRender(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(fond, 0, 0);
        g.drawImage(retour, Ctes.CONTINUEGAME_X_RETOUR, Ctes.CONTINUEGAME_Y_RETOUR);
        if (page != 1)
            g.drawImage(pagePrec, Ctes.CONTINUEGAME_X_PREC, Ctes.CONTINUEGAME_Y_PREC);
        if (!isLastPage)
            g.drawImage(pageSuiv, Ctes.CONTINUEGAME_X_SUIV, Ctes.CONTINUEGAME_Y_SUIV);
        afficherCadres(g);
        if (count != 0) {
            g.drawImage(cadreSelect, Ctes.CONTINUEGAME_X_CADRESAVE, Ctes.CONTINUEGAME_Y_CADRESAVE + (selected - 1) * Ctes.CONTINUEGAME_ESP_CADRESAVE);
            g.drawImage(jouer, Ctes.CONTINUEGAME_X_JOUER, Ctes.CONTINUEGAME_Y_JOUER);
        }
        afficheSupp(gc.getInput(), g);
    }
    
    @Override
    protected void CustomUpdate(GameContainer gc, int t) throws SlickException {
        Input input = gc.getInput();
        
        if (input.isKeyDown(Input.KEY_R)) {
            Main.Game.manager.addSence(new SceneAccueil());
            Main.Game.manager.removeSence(this);
        }
        else if (input.isKeyDown(Input.KEY_S)) {
            if (!isLastPage) {
                try {
                    page++;
                    initPage();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (input.isKeyDown(Input.KEY_P)) {
            if (page != 1) {
                try {
                    page--;
                    initPage();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (input.isKeyDown(Input.KEY_J)) {
            try {
                Main.Game.manager.addSence(new SceneVillage(new Personnage(persos.get(selected - 1).getId(), false)));
                Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(persos.get(selected - 1).getId(), false)));
                Main.Game.manager.removeSence(this);
                Main.Game.manager.sort();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (input.isKeyPressed(Input.KEY_U)) {
            deleteSave(selected - 1);
        }
        else if (input.isKeyPressed(Input.KEY_UP)) {
            if (selected == 1) selected = count;
            else selected--;
        }
        else if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (selected == count) selected = 1;
            else selected++;
        }
        
        if (input.getMouseX() > Ctes.CONTINUEGAME_X_PREC && input.getMouseX() < Ctes.CONTINUEGAME_X_PREC + pagePrec.getWidth() && 
                input.getMouseY() > Ctes.CONTINUEGAME_Y_PREC && input.getMouseY() < Ctes.CONTINUEGAME_Y_PREC + pagePrec.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (page != 1) {
                    try {
                        page--;
                        initPage();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else {
                pageSuiv = new Image("ressources/continueGame/pageSuiv.png");
                pagePrec = new Image("ressources/continueGame/pagePrec_hover.png");
                retour = new Image("ressources/continueGame/retour.png");
                jouer = new Image("ressources/continueGame/jouer.png");
            }
        }
        else if (input.getMouseX() > Ctes.CONTINUEGAME_X_SUIV && input.getMouseX() < Ctes.CONTINUEGAME_X_SUIV + pageSuiv.getWidth() && 
                input.getMouseY() > Ctes.CONTINUEGAME_Y_SUIV && input.getMouseY() < Ctes.CONTINUEGAME_Y_SUIV + pageSuiv.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (!isLastPage) {
                    try {
                        page++;
                        initPage();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else {
                pageSuiv = new Image("ressources/continueGame/pageSuiv_hover.png");
                pagePrec = new Image("ressources/continueGame/pagePrec.png");
                retour = new Image("ressources/continueGame/retour.png");
                jouer = new Image("ressources/continueGame/jouer.png");
            }
        }
        else if (input.getMouseX() > Ctes.CONTINUEGAME_X_RETOUR && input.getMouseX() < Ctes.CONTINUEGAME_X_RETOUR + retour.getWidth() && 
                input.getMouseY() > Ctes.CONTINUEGAME_Y_RETOUR && input.getMouseY() < Ctes.CONTINUEGAME_Y_RETOUR + retour.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                Main.Game.manager.addSence(new SceneAccueil());
                Main.Game.manager.removeSence(this);
            }
            else {
                pageSuiv = new Image("ressources/continueGame/pageSuiv.png");
                pagePrec = new Image("ressources/continueGame/pagePrec.png");
                retour = new Image("ressources/continueGame/retour_hover.png");
                jouer = new Image("ressources/continueGame/jouer.png");
            }
        }
        else if (input.getMouseX() > Ctes.CONTINUEGAME_X_JOUER && input.getMouseX() < Ctes.CONTINUEGAME_X_JOUER + jouer.getWidth() && 
                input.getMouseY() > Ctes.CONTINUEGAME_Y_JOUER && input.getMouseY() < Ctes.CONTINUEGAME_Y_JOUER + jouer.getHeight()) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                try {
                    Main.Game.manager.addSence(new SceneVillage(new Personnage(persos.get(selected - 1).getId(), false)));
                    Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(persos.get(selected - 1).getId(), false)));
                    Main.Game.manager.removeSence(this);
                    Main.Game.manager.sort();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                pageSuiv = new Image("ressources/continueGame/pageSuiv.png");
                pagePrec = new Image("ressources/continueGame/pagePrec.png");
                retour = new Image("ressources/continueGame/retour.png");
                jouer = new Image("ressources/continueGame/jouer_hover.png");
            }
        }
        else {
            pageSuiv = new Image("ressources/continueGame/pageSuiv.png");
            pagePrec = new Image("ressources/continueGame/pagePrec.png");
            retour = new Image("ressources/continueGame/retour.png");
            jouer = new Image("ressources/continueGame/jouer.png");
            testMouse(input, gc.getGraphics());
        }
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        fond = new Image("ressources/continueGame/fond.png");
        pageSuiv = new Image("ressources/continueGame/pageSuiv.png");
        pagePrec = new Image("ressources/continueGame/pagePrec.png");
        retour = new Image("ressources/continueGame/retour.png");
        jouer = new Image("ressources/continueGame/jouer.png");
        supp = new Image("ressources/continueGame/supp.png");
        supp_hover = new Image("ressources/continueGame/supp_hover.png");
        cadreSelect = new Image("ressources/continueGame/cadreSelect.png");
        page = 1;
        try {
            initPage();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficherCadres(Graphics g) {
        for (int i = 0 ; i < cadres.size() ; i++) {
            g.drawImage(cadres.get(i), Ctes.CONTINUEGAME_X_CADRESAVE, Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
        }
        
        for (int i = 0 ; i < persos.size() ; i++) {
            afficherInfoPerso(g, persos.get(i), i);
            afficherStuffPerso(g, persos.get(i), i);
        }
    }
    
    public void initPage() throws SlickException, ClassNotFoundException, SQLException {
        cadres.clear();
        persos.clear();
        selected = 1;
        
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT COUNT(ID) FROM PERSONNAGE;");
        
        isLastPage = rs.getInt("COUNT(ID)") <= page * 5;
        if (!isLastPage) count = 5;
        else count = rs.getInt("COUNT(ID)") - 5 * (page - 1);
        
        rs = rq.select("SELECT * FROM PERSONNAGE ORDER BY ID;");
        int i = 0;
        while (rs.next()) {
            i++;
            if (i > (page - 1) * 5 && i <= page * 5) {
                cadres.add(new Image("ressources/continueGame/cadreSave.png"));
                persos.add(new Personnage(rs.getInt("ID"), false));
            }
        }
        
        rq.closeDB();
    }
    
    public void afficherInfoPerso(Graphics g, Personnage p, int i) {
        switch (p.getIdClass()) {
            case 1:
                g.drawString("Chevalier", Ctes.CONTINUEGAME_X_CLASS, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
                break;
            case 2:
                g.drawString("Assassin", Ctes.CONTINUEGAME_X_CLASS, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
                break;
            case 3:
                g.drawString("Sorcier", Ctes.CONTINUEGAME_X_CLASS, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
                break;
            case 4:
                g.drawString("Prêtre", Ctes.CONTINUEGAME_X_CLASS, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
                break;
        }
        g.drawString("Niv. " +p.getNiveau(), Ctes.CONTINUEGAME_X_NIV, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
        g.drawString("Honneur : " +p.getFame(), Ctes.CONTINUEGAME_X_FAME, Ctes.CONTINUEGAME_Y_TEXT + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
    }
    
    public void afficherStuffPerso(Graphics g, Personnage p, int i) {
        int j = 0;
        for (Equipement e : p.getInventaire().getObjetEquip()) {
            e.afficher(g, Ctes.CONTINUEGAME_X_CASE + j * Ctes.CONTINUEGAME_ESP_CASE, Ctes.CONTINUEGAME_Y_CASE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
            j++;
        }
    }
    
    public void testMouse(Input input, Graphics g) throws SlickException {
        int i = 0;
        for (Image im : cadres) {
            if (input.getMouseX() > Ctes.CONTINUEGAME_X_CADRESAVE && input.getMouseX() < Ctes.CONTINUEGAME_X_CADRESAVE + im.getWidth() && 
                    input.getMouseY() > Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE && 
                    input.getMouseY() < Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE + im.getHeight()) {
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    if (selected != i + 1) selected = i + 1;
                    else {
                        try {
                            Main.Game.manager.addSence(new SceneVillage(new Personnage(persos.get(selected - 1).getId(), false)));
                            Main.Game.manager.addSence(new SceneBarreInfo(new Personnage(persos.get(selected - 1).getId(), false)));
                            Main.Game.manager.removeSence(this);
                            Main.Game.manager.sort();
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(SceneNewGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            i++;
        }
        
        for (i = 0 ; i < cadres.size() ; i++) {
            if (input.getMouseX() > Ctes.CONTINUEGAME_X_SUPP && input.getMouseX() < Ctes.CONTINUEGAME_X_SUPP + supp.getWidth() && 
                    input.getMouseY() > Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE && 
                    input.getMouseY() < Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE + supp.getHeight()) {
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    deleteSave(i);
                }
            }
        }
    }
    
    public void deleteSave(int i) {
        try {
            int idPerso = persos.get(i).getId();
            
            Requete rq = new Requete();
            rq.request("DELETE FROM PERSONNAGE WHERE ID = " +idPerso+ ";");
            rq.request("DELETE FROM INVENTAIRE WHERE IDPERSO = " +idPerso+ ";");
            rq.request("DELETE FROM BANK WHERE IDPERSO = " +idPerso+ ";");
            
            rq.closeDB();
            
            if (count == 1) {
                if (page != 1) page--;
            }
            initPage();
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(SceneContinueGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficheSupp(Input input, Graphics g) {
        for (int i = 0 ; i < cadres.size() ; i++) {
            if (input.getMouseX() > Ctes.CONTINUEGAME_X_SUPP && input.getMouseX() < Ctes.CONTINUEGAME_X_SUPP + supp.getWidth() && 
                    input.getMouseY() > Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE && 
                    input.getMouseY() < Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE + supp.getHeight()) {
                g.drawImage(supp_hover, Ctes.CONTINUEGAME_X_SUPP, Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
            }
            else
                g.drawImage(supp, Ctes.CONTINUEGAME_X_SUPP, Ctes.CONTINUEGAME_Y_CADRESAVE + i * Ctes.CONTINUEGAME_ESP_CADRESAVE);
        }
    }
}