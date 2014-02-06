package BDD;

import java.sql.SQLException;

public class ExecQuery {
    
    public ExecQuery() {
        
    }
    
    public void execQ() throws ClassNotFoundException, SQLException {
        Requete rq = new Requete();
        
        rq.request("DROP TABLE MUR;");
        rq.request("CREATE TABLE MUR(ID NUMBER, IDMAP NUMBER, X NUMBER, Y NUMBER, IMG VARCHAR2(60), "
                + "CONSTRAINT PK_MUR PRIMARY KEY (ID), CONSTRAINT FK_MUR FOREIGN KEY (IDMAP) REFERENCES CARTE(ID));");
        rq.request("INSERT INTO MUR VALUES (0, -1, 0, 0, 'ressources/mur/haut.png');");
        rq.request("INSERT INTO MUR VALUES (1, -1, 0, 548, 'ressources/mur/bas.png');");
        rq.request("INSERT INTO MUR VALUES (2, -1, 0, 0, 'ressources/mur/gauche.png');");
        rq.request("INSERT INTO MUR VALUES (3, -1, 798, 0, 'ressources/mur/droite.png');");
        
        rq.request("DROP TABLE INFOCLASS;");
        rq.request("CREATE TABLE INFOCLASS(IDCLASS NUMBER, HP NUMBER, MP NUMBER, ATK NUMBER, DEF NUMBER, SPD NUMBER, "
                + "DEX NUMBER, VIT NUMBER, WIS NUMBER, SPRITE VARCHAR2(60), CONSTRAINT PK_INFOCLASS PRIMARY KEY (IDCLASS));");
        rq.request("INSERT INTO INFOCLASS VALUES (1, 700, 200, 50, 25, 40, 30, 40, 20, 'ressources/personnage/sprite_chevalier.png');");
        rq.request("INSERT INTO INFOCLASS VALUES (2, 650, 220, 70, 20, 60, 50, 30, 25, 'ressources/personnage/sprite_assassin.png');");
        rq.request("INSERT INTO INFOCLASS VALUES (3, 600, 280, 70, 10, 50, 50, 20, 40, 'ressources/personnage/sprite_sorcier.png');");
        rq.request("INSERT INTO INFOCLASS VALUES (4, 600, 300, 50, 10, 50, 40, 20, 50, 'ressources/personnage/sprite_pretre.png');");
        
        rq.request("DROP TABLE CARTE;");
        rq.request("CREATE TABLE CARTE(ID NUMBER, IMG VARCHAR2(50), CONSTRAINT PK_CARTE PRIMARY KEY (ID));");
        rq.request("INSERT INTO CARTE VALUES(-1, 'ressources/carte/village.png');");
        rq.request("INSERT INTO CARTE VALUES(0, 'ressources/carte/carte0.png');");
        
        rq.request("DROP TABLE PERSONNAGE;");
        rq.request("CREATE TABLE PERSONNAGE(ID NUMBER, IDCLASS NUMBER, NIVEAU NUMBER, XP NUMBER, FAME NUMBER, HP NUMBER, MP NUMBER, ATK NUMBER, "
                + "DEF NUMBER, SPD NUMBER, DEX NUMBER, VIT NUMBER, WIS NUMBER, "
                + "CONSTRAINT PK_PERSONNAGE PRIMARY KEY (ID));");
        
        rq.request("DROP TABLE OBJET;");
        rq.request("CREATE TABLE OBJET(ID NUMBER, NIVEAU NUMBER, TYPE NUMBER, IDCLASS NUMBER, BONUSHP NUMBER, BONUSMP NUMBER, BONUSATK NUMBER, "
                + "BONUSDEF NUMBER, BONUSSPD NUMBER, BONUSDEX NUMBER, BONUSVIT NUMBER, BONUSWIS NUMBER, IMG VARCHAR2(50), NOM VARCHAR2(50), "
                + "BONUSSORTETNBPROJ NUMBER, DUREESORTETANGLE NUMBER, COUTMANAOURANGE NUMBER, DEGATMIN NUMBER, DEGATMAX NUMBER, "
                + "PERFORANT NUMBER, SPEEDPROJ NUMBER, DESC VARCHAR2(80), "
                + "CONSTRAINT PK_EQUIPEMENT PRIMARY KEY (ID));");
        rq.request("INSERT INTO OBJET VALUES(-1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 'ressources/objets/potion_dex.png', 'Potion de Dextérité', 0, 0, 0, 0, 0, 0, 0, 'Augmente de façon permanente votre dextérité.');");
        rq.request("INSERT INTO OBJET VALUES(0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(1, -1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(2, -1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(3, -1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(4, -1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(5, -1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(6, -1, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(7, -1, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(8, -1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(9, -1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(10, -1, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(11, -1, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(12, -1, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(13, -1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(14, -1, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(15, -1, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(16, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/armes/epee_t0.png', 'Epée de débutant', 3, 60, 50, 10, 20, 0, 1, '');");
        rq.request("INSERT INTO OBJET VALUES(17, 0, 2, 1, 0, 0, 0, 2, 0, 0, 0, 0, 'ressources/equip/armures/lourde_t0.png', 'Armure de débutant', 0, 0, 0, 0, 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(18, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/armes/epee_t1.png', 'Epée en bois', 1, 0, 50, 15, 25, 0, 1, '');");
        rq.request("INSERT INTO OBJET VALUES(19, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/spell/shield_t1.png', 'Bouclier en bois', 2, 2, 70, 0, 0, 0, 0, '');");
        
        rq.request("DROP TABLE INVENTAIRE;");
        rq.request("CREATE TABLE INVENTAIRE(IDPERSO NUMBER, IDOBJET NUMBER, EQUIPE NUMBER, "
                + "CONSTRAINT FK_INVENTAIRE_PERSO FOREIGN KEY (IDPERSO) REFERENCES PERSONNAGE(ID), "
                + "CONSTRAINT FK_INVENTAIRE_OBJET FOREIGN KEY (IDOBJET) REFERENCES OBJET(ID));");
        
        rq.request("DROP TABLE BANK;");
        rq.request("CREATE TABLE BANK(IDPERSO NUMBER, IDOBJET NUMBER, "
                + "CONSTRAINT FK_BANK_PERSO FOREIGN KEY (IDPERSO) REFERENCES PERSONNAGE(ID), "
                + "CONSTRAINT FK_BANK_OBJET FOREIGN KEY (IDOBJET) REFERENCES OBJET(ID));");
        
        rq.request("DROP TABLE TELEPORTEUR;");
        rq.request("CREATE TABLE TELEPORTEUR(ID NUMBER, IDMAP NUMBER, X NUMBER, Y NUMBER, IDMAPDEST NUMBER, XDEST NUMBER, YDEST NUMBER, "
                + "CONSTRAINT PK_TELEPORTEUR PRIMARY KEY (ID));");
        rq.request("INSERT INTO TELEPORTEUR VALUES (0, -1, 500, 500, 0, 500, 500);");

        rq.closeDB();
    }
}