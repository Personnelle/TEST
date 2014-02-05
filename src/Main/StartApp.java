package Main;


import Constantes.Ctes;
import java.sql.SQLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class StartApp {
    public static void main(String[] args) throws SlickException, SQLException, ClassNotFoundException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(Ctes.LARGEUR_ECRAN, Ctes.HAUTEUR_ECRAN, false);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.start();
    }
}