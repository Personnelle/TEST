package Projectiles;

public class Vecteur {
    private float argument;
    
    public Vecteur(float x, float y, float xCursor, float yCursor) {
        argument = calculAngle(x, y, xCursor, yCursor);
    }

    public Vecteur(float argument) {
        this.argument = argument;
    }
    
    public float getCoefX() { return (float) Math.cos((2 * Math.PI * argument) / 360); }
    public float getCoefY() { return (float) Math.sin((2 * Math.PI * argument) / 360); }
    public float getArgument() { return argument; }
    
    private float calculAngle(float x, float y, float xCursor, float yCursor) {
        float a = xCursor - x, b = yCursor - y;
        float mod = (float) Math.sqrt(a*a + b*b);
        double angleRadian = Math.acos(a / mod);
        if (a == 0) {
            if (b > 0) angleRadian = 3 * Math.PI / 2;
            else if (b < 0) angleRadian = Math.PI / 2;
        }
        else if (b == 0) {
            if (a < 0) angleRadian = Math.PI;
            else angleRadian = 0;
        }
        else if (b < 0) angleRadian *= -1;
        float arg = (float) ((angleRadian * 360) / (2 * Math.PI));
        if (arg < 0) arg += 360;
        return arg;
    }
}