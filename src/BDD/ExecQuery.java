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
        rq.request("INSERT INTO MUR VALUES (4, 0, 0, 0, 'ressources/mur/haut.png');");
        rq.request("INSERT INTO MUR VALUES (5, 0, 0, 548, 'ressources/mur/bas.png');");
        rq.request("INSERT INTO MUR VALUES (6, 0, 0, 0, 'ressources/mur/gauche.png');");
        rq.request("INSERT INTO MUR VALUES (7, 0, 798, 0, 'ressources/mur/droite.png');");
        rq.request("INSERT INTO MUR VALUES (8, 1, 0, 0, 'ressources/mur/haut.png');");
        rq.request("INSERT INTO MUR VALUES (9, 1, 0, 548, 'ressources/mur/bas.png');");
        rq.request("INSERT INTO MUR VALUES (10, 1, 0, 0, 'ressources/mur/gauche.png');");
        rq.request("INSERT INTO MUR VALUES (11, 1, 798, 0, 'ressources/mur/droite.png');");
        rq.request("INSERT INTO MUR VALUES (12, 2, 0, 0, 'ressources/mur/haut.png');");
        rq.request("INSERT INTO MUR VALUES (13, 2, 0, 548, 'ressources/mur/bas.png');");
        rq.request("INSERT INTO MUR VALUES (14, 2, 0, 0, 'ressources/mur/gauche.png');");
        rq.request("INSERT INTO MUR VALUES (15, 2, 798, 0, 'ressources/mur/droite.png');");
        
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
        rq.request("INSERT INTO CARTE VALUES(1, 'ressources/carte/carte1.png');");
        rq.request("INSERT INTO CARTE VALUES(2, 'ressources/carte/carte2.png');");
        
        rq.request("DROP TABLE PERSONNAGE;");
        rq.request("CREATE TABLE PERSONNAGE(ID NUMBER, IDCLASS NUMBER, NIVEAU NUMBER, XP NUMBER, FAME NUMBER, HP NUMBER, MP NUMBER, ATK NUMBER, "
                + "DEF NUMBER, SPD NUMBER, DEX NUMBER, VIT NUMBER, WIS NUMBER, "
                + "CONSTRAINT PK_PERSONNAGE PRIMARY KEY (ID));");
        
        rq.request("DROP TABLE OBJET;");
        rq.request("CREATE TABLE OBJET(ID NUMBER, NIVEAU NUMBER, TYPE NUMBER, IDCLASS NUMBER, BONUSHP NUMBER, BONUSMP NUMBER, BONUSATK NUMBER, "
                + "BONUSDEF NUMBER, BONUSSPD NUMBER, BONUSDEX NUMBER, BONUSVIT NUMBER, BONUSWIS NUMBER, IMG VARCHAR2(50), NOM VARCHAR2(50), "
                + "BONUSSORT NUMBER, DUREESORT NUMBER, COUTMANA NUMBER, DESC VARCHAR2(80), "
                + "CONSTRAINT PK_EQUIPEMENT PRIMARY KEY (ID));");
        rq.request("INSERT INTO OBJET VALUES(-8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 'ressources/objets/potion_wis.png', 'Potion de Sagesse', 0, 0, 0, 'Augmente de façon permanente votre sagesse.');");
        rq.request("INSERT INTO OBJET VALUES(-7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 'ressources/objets/potion_vit.png', 'Potion de Vitalité', 0, 0, 0, 'Augmente de façon permanente votre vitalité.');");
        rq.request("INSERT INTO OBJET VALUES(-6, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 'ressources/objets/potion_dex.png', 'Potion de Dextérité', 0, 0, 0, 'Augmente de façon permanente votre dextérité.');");
        rq.request("INSERT INTO OBJET VALUES(-5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 'ressources/objets/potion_spd.png', 'Potion de Vitesse', 0, 0, 0, 'Augmente de façon permanente votre vitesse.');");
        rq.request("INSERT INTO OBJET VALUES(-4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 'ressources/objets/potion_def.png', 'Potion de Défense', 0, 0, 0, 'Augmente de façon permanente votre défense.');");
        rq.request("INSERT INTO OBJET VALUES(-3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 'ressources/objets/potion_atk.png', 'Potion d''Attaque', 0, 0, 0, 'Augmente de façon permanente votre attaque.');");
        rq.request("INSERT INTO OBJET VALUES(-2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 'ressources/objets/potion_mp.png', 'Potion de Mana', 0, 0, 0, 'Augmente de façon permanente votre mana maximale.');");
        rq.request("INSERT INTO OBJET VALUES(-1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 'ressources/objets/potion_hp.png', 'Potion de Vie', 0, 0, 0, 'Augmente de façon permanente votre vie maximale.');");
        rq.request("INSERT INTO OBJET VALUES(0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(1, -1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(2, -1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(3, -1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(4, -1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(5, -1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(6, -1, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(7, -1, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(8, -1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(9, -1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(10, -1, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(11, -1, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(12, -1, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(13, -1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(14, -1, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(15, -1, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/noitem.png', 'itemvide', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(16, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/armes/epee_t0.png', 'Epée de débutant', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(17, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 'ressources/equip/armes/epee_t1.png', 'Epée en bois', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(18, 2, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 'ressources/equip/armes/epee_t2.png', 'Epée en bronze', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(19, 3, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 'ressources/equip/armes/epee_t3.png', 'Epée en bronze renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(20, 4, 1, 1, 0, 0, 2, 0, 0, 1, 0, 0, 'ressources/equip/armes/epee_t4.png', 'Epée argent', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(21, 5, 1, 1, 0, 0, 2, 0, 1, 1, 0, 0, 'ressources/equip/armes/epee_t5.png', 'Epée en argent renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(22, 6, 1, 1, 0, 0, 3, 0, 1, 1, 0, 0, 'ressources/equip/armes/epee_t6.png', 'Epée en or', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(23, 7, 1, 1, 0, 0, 3, 0, 1, 1, 1, 0, 'ressources/equip/armes/epee_t7.png', 'Epée en or renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(24, 8, 1, 1, 0, 0, 3, 0, 2, 2, 1, 0, 'ressources/equip/armes/epee_t8.png', 'Epée de l''ombre', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(25, 9, 1, 1, 0, 0, 4, 0, 2, 2, 1, 0, 'ressources/equip/armes/epee_t9.png', 'Epée de l''ombre renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(26, 10, 1, 1, 0, 0, 4, 0, 3, 2, 2, 0, 'ressources/equip/armes/epee_t10.png', 'Epée d''émeraude', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(27, 11, 1, 1, 0, 0, 5, 0, 3, 2, 2, 0, 'ressources/equip/armes/epee_t11.png', 'Epée d''émeraude renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(28, 12, 1, 1, 0, 0, 5, 0, 3, 3, 2, 0, 'ressources/equip/armes/epee_t12.png', 'Epée de saphir', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(29, 13, 1, 1, 0, 0, 6, 0, 3, 3, 2, 0, 'ressources/equip/armes/epee_t13.png', 'Epée de saphir renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(30, 14, 1, 1, 0, 0, 6, 0, 3, 3, 3, 0, 'ressources/equip/armes/epee_t14.png', 'Epée sanguine', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(31, 15, 1, 1, 0, 0, 7, 0, 3, 5, 3, 0, 'ressources/equip/armes/epee_t15.png', 'Epée sanguine renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(32, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/armures/lourde_t0.png', 'Armure de débutant', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(33, 1, 2, 1, 5, 0, 0, 0, 0, 0, 0, 0, 'ressources/equip/armures/lourde_t1.png', 'Armure en bois', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(34, 2, 2, 1, 5, 0, 0, 1, 0, 0, 0, 0, 'ressources/equip/armures/lourde_t2.png', 'Armure en bronze', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(35, 3, 2, 1, 10, 0, 0, 1, 0, 0, 0, 0, 'ressources/equip/armures/lourde_t3.png', 'Armure en bronze renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(36, 4, 2, 1, 10, 0, 0, 2, 1, 0, 0, 0, 'ressources/equip/armures/lourde_t4.png', 'Armure argent', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(37, 5, 2, 1, 15, 0, 0, 2, 1, 0, 1, 0, 'ressources/equip/armures/lourde_t5.png', 'Armure en argent renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(38, 6, 2, 1, 20, 0, 0, 2, 1, 0, 1, 0, 'ressources/equip/armures/lourde_t6.png', 'Armure en or', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(39, 7, 2, 1, 20, 0, 0, 3, 1, 0, 2, 0, 'ressources/equip/armures/lourde_t7.png', 'Armure en or renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(40, 8, 2, 1, 25, 0, 0, 3, 1, 0, 2, 0, 'ressources/equip/armures/lourde_t8.png', 'Armure de l''ombre', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(41, 9, 2, 1, 25, 0, 0, 4, 2, 0, 2, 0, 'ressources/equip/armures/lourde_t9.png', 'Armure de l''ombre renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(42, 10, 2, 1, 25, 0, 0, 4, 2, 0, 3, 0, 'ressources/equip/armures/lourde_t10.png', 'Armure d''émeraude', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(43, 11, 2, 1, 40, 0, 0, 5, 2, 0, 3, 0, 'ressources/equip/armures/lourde_t11.png', 'Armure d''émeraude renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(44, 12, 2, 1, 40, 0, 0, 5, 3, 0, 4, 0, 'ressources/equip/armures/lourde_t12.png', 'Armure de saphir', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(45, 13, 2, 1, 50, 0, 0, 6, 3, 0, 5, 0, 'ressources/equip/armures/lourde_t13.png', 'Armure de saphir renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(46, 14, 2, 1, 50, 0, 0, 8, 3, 0, 6, 0, 'ressources/equip/armures/lourde_t14.png', 'Armure sanguine', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(47, 15, 2, 1, 60, 0, 0, 10, 3, 0, 8, 0, 'ressources/equip/armures/lourde_t15.png', 'Armure sanguine renforcée', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(48, 1, 4, 1, 0, 0, 0, 1, 0, 0, 0, 0, 'ressources/equip/spell/shield_t1.png', 'Bouclier en bois', 2, 2, 70, '');");
        rq.request("INSERT INTO OBJET VALUES(49, 2, 4, 1, 5, 0, 0, 1, 0, 0, 0, 0, 'ressources/equip/spell/shield_t2.png', 'Bouclier en bronze', 2, 3, 70, '');");
        rq.request("INSERT INTO OBJET VALUES(50, 3, 4, 1, 10, 0, 0, 2, 0, 0, 1, 0, 'ressources/equip/spell/shield_t3.png', 'Bouclier en bronze renforcé', 3, 3, 75, '');");
        rq.request("INSERT INTO OBJET VALUES(51, 4, 4, 1, 10, 0, 0, 2, 0, 0, 1, 0, 'ressources/equip/spell/shield_t4.png', 'Bouclier argent', 3, 3, 60, '');");
        rq.request("INSERT INTO OBJET VALUES(52, 5, 4, 1, 15, 0, 0, 3, 0, 0, 1, 0, 'ressources/equip/spell/shield_t5.png', 'Bouclier en argent renforcé', 4, 4, 65, '');");
        rq.request("INSERT INTO OBJET VALUES(53, 6, 4, 1, 20, 0, 0, 3, 0, 0, 2, 0, 'ressources/equip/spell/shield_t6.png', 'Bouclier en or', 5, 4, 65, '');");
        rq.request("INSERT INTO OBJET VALUES(54, 7, 4, 1, 25, 0, 0, 4, 0, 0, 2, 0, 'ressources/equip/spell/shield_t7.png', 'Bouclier en or renforcé', 6, 5, 75, '');");
        rq.request("INSERT INTO OBJET VALUES(55, 8, 4, 1, 25, 0, 0, 4, 0, 0, 3, 0, 'ressources/equip/spell/shield_t8.png', 'Bouclier de l''ombre', 6, 6, 70, '');");
        rq.request("INSERT INTO OBJET VALUES(56, 9, 4, 1, 30, 0, 0, 5, 0, 0, 3, 0, 'ressources/equip/spell/shield_t9.png', 'Bouclier de l''ombre renforcé', 7, 6, 65, '');");
        rq.request("INSERT INTO OBJET VALUES(57, 10, 4, 1, 30, 0, 0, 5, 0, 0, 4, 0, 'ressources/equip/spell/shield_t10.png', 'Bouclier d''émeraude', 8, 7, 75, '');");
        rq.request("INSERT INTO OBJET VALUES(58, 11, 4, 1, 35, 0, 0, 6, 1, 0, 4, 0, 'ressources/equip/spell/shield_t11.png', 'Bouclier d''émeraude renforcé', 9, 7, 80, '');");
        rq.request("INSERT INTO OBJET VALUES(59, 12, 4, 1, 35, 0, 0, 7, 1, 0, 5, 0, 'ressources/equip/spell/shield_t12.png', 'Bouclier de saphir', 10, 7, 80, '');");
        rq.request("INSERT INTO OBJET VALUES(60, 13, 4, 1, 35, 0, 0, 7, 2, 0, 5, 0, 'ressources/equip/spell/shield_t13.png', 'Bouclier de saphir renforcé', 12, 7, 90, '');");
        rq.request("INSERT INTO OBJET VALUES(61, 14, 4, 1, 40, 0, 0, 8, 2, 0, 6, 0, 'ressources/equip/spell/shield_t14.png', 'Bouclier sanguin', 14, 8, 95, '');");
        rq.request("INSERT INTO OBJET VALUES(62, 15, 4, 1, 40, 0, 0, 8, 3, 0, 6, 0, 'ressources/equip/spell/shield_t15.png', 'Bouclier sanguin renforcé', 15, 8, 100, '');");
        rq.request("INSERT INTO OBJET VALUES(63, 1, 3, 1, 0, 0, 1, 1, 1, 1, 1, 1, 'ressources/equip/bagues/ring_t1.png', 'Bague en bronze', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(64, 2, 3, 1, 0, 0, 2, 1, 2, 2, 2, 2, 'ressources/equip/bagues/ring_t2.png', 'Bague en argent', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(65, 3, 3, 1, 0, 0, 3, 2, 3, 3, 3, 3, 'ressources/equip/bagues/ring_t3.png', 'Bague en or', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(66, 4, 3, 1, 5, 5, 4, 2, 4, 4, 4, 4, 'ressources/equip/bagues/ring_t4.png', 'Bague de l''ombre', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(67, 5, 3, 1, 10, 10, 5, 3, 5, 5, 5, 5, 'ressources/equip/bagues/ring_t5.png', 'Bague d''émeraude', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(68, 6, 3, 1, 15, 15, 6, 3, 6, 6, 6, 6, 'ressources/equip/bagues/ring_t6.png', 'Bague de saphir', 0, 0, 0, '');");
        rq.request("INSERT INTO OBJET VALUES(69, 7, 3, 1, 25, 25, 7, 4, 7, 7, 7, 7, 'ressources/equip/bagues/ring_t7.png', 'Bague sanguine', 0, 0, 0, '');");
        
        rq.request("DROP TABLE PROJECTILE;");
        rq.request("CREATE TABLE PROJECTILE(ID NUMBER, IDARME NUMBER, IDMOB NUMBER, DEGATMIN NUMBER, DEGATMAX NUMBER, NBPROJ NUMBER, "
                + "ANGLE NUMBER, RANGE NUMBER, PERFORANT NUMBER, TRUEDAMAGE NUMBER, SPEED NUMBER, IMG VARCHAR2(60), "
                + "CONSTRAINT PK_PROJECTILE PRIMARY KEY (ID));");
        rq.request("INSERT INTO PROJECTILE VALUES (0, 16, -1, 50, 60, 36, 360, 500, 0, 0, 1, 'ressources/projectiles/arme16.png');");
        //rq.request("INSERT INTO PROJECTILE VALUES (0, 16, -1, 10, 15, 1, 0, 50, 0, 0, 1, 'ressources/projectiles/arme16.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (1, 17, -1, 12, 17, 1, 0, 50, 0, 0, 1, 'ressources/projectiles/arme17.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (2, 18, -1, 15, 20, 1, 0, 50, 0, 0, 1.1, 'ressources/projectiles/arme18.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (3, 19, -1, 18, 25, 1, 0, 50, 0, 0, 1.1, 'ressources/projectiles/arme19.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (4, 20, -1, 20, 28, 1, 0, 50, 0, 0, 1.2, 'ressources/projectiles/arme20.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (5, 21, -1, 25, 35, 1, 0, 50, 0, 0, 1.2, 'ressources/projectiles/arme21.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (6, 22, -1, 30, 40, 1, 0, 50, 0, 0, 1.2, 'ressources/projectiles/arme22.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (7, 23, -1, 35, 47, 1, 0, 50, 0, 0, 1.3, 'ressources/projectiles/arme23.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (8, 24, -1, 42, 60, 1, 0, 50, 0, 0, 1.3, 'ressources/projectiles/arme24.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (9, 25, -1, 48, 65, 1, 0, 75, 0, 0, 1.3, 'ressources/projectiles/arme25.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (10, 26, -1, 55, 70, 1, 0, 75, 0, 0, 1.3, 'ressources/projectiles/arme26.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (11, 27, -1, 65, 80, 1, 0, 75, 0, 0, 1.3, 'ressources/projectiles/arme27.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (12, 28, -1, 75, 95, 1, 0, 75, 0, 0, 1.5, 'ressources/projectiles/arme28.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (13, 29, -1, 45, 55, 2, 15, 75, 0, 0, 1.5, 'ressources/projectiles/arme29.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (14, 30, -1, 55, 67, 2, 15, 75, 0, 0, 1.5, 'ressources/projectiles/arme30.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (15, 31, -1, 65, 80, 2, 15, 100, 0, 1, 1.7, 'ressources/projectiles/arme31.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (200, -1, 0, 10, 15, 1, 0, 50, 0, 0, 1, 'ressources/projectiles/mob0.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (201, -1, 1, 10, 15, 1, 0, 100, 0, 0, 1, 'ressources/projectiles/mob1.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (202, -1, 2, 10, 15, 1, 0, 100, 0, 0, 1, 'ressources/projectiles/mob2.png');");
        rq.request("INSERT INTO PROJECTILE VALUES (203, -1, 3, 12, 17, 1, 0, 400, 0, 0, 1, 'ressources/projectiles/mob3.png');");
        
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
        rq.request("INSERT INTO TELEPORTEUR VALUES (1, 0, 8, 270, 1, 731, 270);");
        rq.request("INSERT INTO TELEPORTEUR VALUES (2, 0, 380, 8, 2, 380, 481);");
        rq.request("INSERT INTO TELEPORTEUR VALUES (3, 1, 761, 270, 0, 44, 270);");
        rq.request("INSERT INTO TELEPORTEUR VALUES (4, 2, 380, 511, 0, 380, 44);");
        
        rq.request("DROP TABLE MOB;");
        rq.request("CREATE TABLE MOB(ID NUMBER, HP NUMBER, DEX NUMBER, ATK NUMBER, DEF NUMBER, SPD NUMBER, XP NUMBER, "
                + "FAME NUMBER, RANGEMOVE NUMBER, "
                + "CONSTRAINT PK_MOB PRIMARY KEY (ID));");
        rq.request("INSERT INTO MOB VALUES(0, 100, 0, 0, 0, 0, 1, 1, 5)");
        rq.request("INSERT INTO MOB VALUES(1, 120, 2, 1, 1, 2, 2, 1, 5)");
        rq.request("INSERT INTO MOB VALUES(2, 130, 2, 2, 2, 2, 2, 2, 5)");
        rq.request("INSERT INTO MOB VALUES(3, 120, 4, 4, 0, 0, 2, 2, 300)");
        
        rq.request("DROP TABLE SPRITEMOB;");
        rq.request("CREATE TABLE SPRITEMOB(IDMOB NUMBER, IMG VARCHAR2(60), FIRSTX NUMBER, FIRSTH NUMBER, FIRSTW NUMBER, "
                + "SECONDX NUMBER, SECONDH NUMBER, SECONDW NUMBER, THIRDX NUMBER, THIRDH NUMBER, THIRDW NUMBER, "
                + "FOURTHX NUMBER, FOURTHH NUMBER, FOURTHW NUMBER, "
                + "CONSTRAINT PK_SPRITEMOB PRIMARY KEY (IDMOB), "
                + "CONSTRAINT FK_SPRITEMOB FOREIGN KEY (IDMOB) REFERENCES MOB(ID));");
        rq.request("INSERT INTO SPRITEMOB VALUES(0, 'ressources/mobs/mob0.png', 0, 25, 25, 25, 25, 25, 50, 25, 25, 75, 25, 25);");
        rq.request("INSERT INTO SPRITEMOB VALUES(1, 'ressources/mobs/mob1.png', 0, 25, 25, 25, 25, 21, 46, 25, 21, 67, 25, 21);");
        rq.request("INSERT INTO SPRITEMOB VALUES(2, 'ressources/mobs/mob2.png', 0, 25, 19, 19, 25, 19, 38, 25, 19, 57, 25, 19);");
        rq.request("INSERT INTO SPRITEMOB VALUES(3, 'ressources/mobs/mob3.png', 0, 25, 25, 25, 25, 22, 47, 25, 22, 69, 25, 25);");
        
        rq.request("DROP TABLE CORRESPMOBMAP;");
        rq.request("CREATE TABLE CORRESPMOBMAP(IDMAP NUMBER, IDMOB NUMBER, POPX NUMBER, POPY NUMBER, "
                + "CONSTRAINT FK_CORRESPMAP FOREIGN KEY (IDMAP) REFERENCES CARTE(ID), "
                + "CONSTRAINT FK_CORRESPMOB FOREIGN KEY (IDMOB) REFERENCES MOB(ID));");
        rq.request("INSERT INTO CORRESPMOBMAP VALUES(0, 0, 30, 30);");
        rq.request("INSERT INTO CORRESPMOBMAP VALUES(0, 0, 520, 520);");
        rq.request("INSERT INTO CORRESPMOBMAP VALUES(0, 2, 30, 520);");
        rq.request("INSERT INTO CORRESPMOBMAP VALUES(0, 3, 520, 30);");
        
        rq.request("DROP TABLE MOBLOOT;");
        rq.request("CREATE TABLE MOBLOOT(IDMOB NUMBER, IDOBJET NUMBER, POURCENT NUMBER, "
                + "CONSTRAINT FK_MOBLOOT FOREIGN KEY (IDMOB) REFERENCES MOB(ID), "
                + "CONSTRAINT FK_OBJETLOOT FOREIGN KEY (IDOBJET) REFERENCES OBJET(ID));");
        rq.request("INSERT INTO MOBLOOT VALUES (0, 18, 0.8);");
        
        rq.closeDB();
    }
}