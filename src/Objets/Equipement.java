package Objets;

import BDD.Requete;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.newdawn.slick.SlickException;

public class Equipement extends Objet implements Comparable<Equipement> {
    private int type;
    private int idClass;
    private int niveau;
    private int bonusHp;
    private int bonusMp;
    private int bonusAtk;
    private int bonusDef;
    private int bonusSpd;
    private int bonusDex;
    private int bonusVit;
    private int bonusWis;
    private int bonusSortOuNbProj;
    private int dureeSortOuAngle;
    private int coutManaOuRange;
    private int degatMin;
    private int degatMax;
    private boolean perforant;
    
    public Equipement(int id) throws ClassNotFoundException, SQLException, SlickException {
        super(id);
        
        Requete rq = new Requete();
        ResultSet rs = rq.select("SELECT * FROM OBJET WHERE ID = " +id+ ";");
        
        type = rs.getInt("TYPE");
        idClass = rs.getInt("IDCLASS");
        niveau = rs.getInt("NIVEAU");
        bonusHp = rs.getInt("BONUSHP");
        bonusMp = rs.getInt("BONUSMP");
        bonusAtk = rs.getInt("BONUSATK");
        bonusDef = rs.getInt("BONUSDEF");
        bonusSpd = rs.getInt("BONUSSPD");
        bonusDex = rs.getInt("BONUSDEX");
        bonusVit = rs.getInt("BONUSVIT");
        bonusWis = rs.getInt("BONUSWIS");
        bonusSortOuNbProj = rs.getInt("BONUSSORTETNBPROJ");
        dureeSortOuAngle = rs.getInt("DUREESORTETANGLE");
        coutManaOuRange = rs.getInt("COUTMANAOURANGE");
        degatMin = rs.getInt("DEGATMIN");
        degatMax = rs.getInt("DEGATMAX");
        perforant = rs.getInt("PERFORANT") == 1;
        
        rq.closeDB();
    }
    
    @Override
    public int compareTo(Equipement compareObject) {
        if (getType() < compareObject.getType()) return -1;
        else if (getType() == compareObject.getType()) return 0;
        else return 1;
    }
    
    public int getType() { return type; }
    public int getNiveau() { return niveau; }
    public int getIdClass() { return idClass; }
    public int getBonusHp() { return bonusHp; }
    public int getBonusMp() { return bonusMp; }
    public int getBonusAtk() { return bonusAtk; }
    public int getBonusDef() { return bonusDef; }
    public int getBonusSpd() { return bonusSpd; }
    public int getBonusDex() { return bonusDex; }
    public int getBonusVit() { return bonusVit; }
    public int getBonusWis() { return bonusWis; }
    public int getBonusSortOuNbProj() { return bonusSortOuNbProj; }
    public int getDureeSortOuAngle() { return dureeSortOuAngle; }
    public int getCoutManaOuRange() { return coutManaOuRange; }
    public int getDegatMin() { return degatMin; }
    public int getDegatMax() { return degatMax; }
    public boolean isPerforant() { return perforant; }
}
