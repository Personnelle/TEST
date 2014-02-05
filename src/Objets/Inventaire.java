package Objets;

import BDD.Requete;
import Constantes.Ctes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Inventaire {
    private List<Objet> objetsInv = new ArrayList<>();
    private List<Equipement> objetsEquip = new ArrayList<>();
    
    public Inventaire(int idPerso) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM INVENTAIRE WHERE IDPERSO = " +idPerso+ ";");
        
        while (rs.next()) {
            if (rs.getInt("EQUIPE") == 1)
                objetsEquip.add(new Equipement(rs.getInt("IDOBJET")));
            else {
                if (rs.getInt("IDOBJET") < 0)
                    objetsInv.add(new Objet(rs.getInt("IDOBJET")));
                else
                    objetsInv.add(new Equipement(rs.getInt("IDOBJET")));
            }
        }
        Collections.sort(objetsEquip);
        
        rq.closeDB();
    }
    
    public Inventaire(boolean nouv, int idClass) throws ClassNotFoundException, SQLException, SlickException {
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT ID FROM OBJET WHERE IDCLASS = " +idClass+ " AND NIVEAU = 0;");
        
        while (rs.next())
            objetsEquip.add(new Equipement(rs.getInt("ID")));
        
        for (int i = 1 ; i <= 4 ; i++) {
            Equipement e = null;
            for (int j = 0 ; j < objetsEquip.size() ; j++) {
                if (objetsEquip.get(j).getType() == i)
                    e = objetsEquip.get(j);
            }
            if (e == null) {
                rs = rq.select("SELECT * FROM OBJET WHERE IDCLASS = " +idClass+ " AND NIVEAU = -1 AND TYPE = " +i+ ";");
                objetsEquip.add(new Equipement(rs.getInt("ID")));
            }
        }
        Collections.sort(objetsEquip);
        
        //A SUPPRIMER
        objetsInv.add(new Objet(-1));
        objetsInv.add(new Equipement(19));
        
        rq.closeDB();
    }
    
    public void afficherInBank(Graphics g) {
        for (int i = 0 ; i < objetsInv.size() ; i++)
            objetsInv.get(i).afficher(g, Ctes.BANK_X_SELECT + i * Ctes.BANK_ESP_SELECT, Ctes.BANK_Y_SELECT_INV);
    }
    
    public void afficherInMenu(Graphics g) {
        for (int i = 0 ; i < objetsEquip.size() ; i++)
            objetsEquip.get(i).afficher(g, Ctes.MENUBARRE_X_CASEEQUIP + i * Ctes.MENUBARRE_ESP_CASES, Ctes.MENUBARRE_Y_CASES);
        for (int i = 0 ; i < objetsInv.size() ; i++) 
            objetsInv.get(i).afficher(g, Ctes.MENUBARRE_X_CASEINV + i * Ctes.MENUBARRE_ESP_CASES, Ctes.MENUBARRE_Y_CASES); 
    }
    
    public List<Equipement> getObjetEquip() { return objetsEquip; }
    public List<Objet> getObjetInv() { return objetsInv; }
    
    public void ajouteStuff(Equipement e) {
        if (objetsEquip.get(e.getType() - 1).getNiveau() != -1)
            objetsInv.add(objetsEquip.get(e.getType() - 1));
        objetsEquip.remove(e.getType() - 1);
        objetsEquip.add(e);
        Collections.sort(objetsEquip);
    }
    
    public void ajouteInv(Objet o) {
        if (objetsInv.size() < 6) {
            if (o instanceof Equipement) {
                Equipement e = (Equipement) o;
                if (e.getNiveau() != -1)
                    objetsInv.add(e);
            }
            else
                objetsInv.add(o);
        }
    }
    
    public void retireStuff(Equipement e) {
        try {
            objetsEquip.remove(e.getType() - 1);
            
            Requete rq = new Requete();
            ResultSet rs = rq.select("SELECT ID FROM OBJET WHERE TYPE = " +e.getType()+ " AND IDCLASS = " +e.getIdClass()+ " AND NIVEAU = -1;");
            
            objetsEquip.add(new Equipement(rs.getInt("ID")));
            Collections.sort(objetsEquip);
            
            rq.closeDB();
        } catch (ClassNotFoundException | SQLException | SlickException ex) {
            Logger.getLogger(Inventaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void retireInv(Objet o) {
        objetsInv.remove(o);
    }
}
