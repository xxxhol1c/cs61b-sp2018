public class Planet {
    
    private double G = 6.67e-11; // the gravitational constant
    
    public double xxPos;  // current x position
    public double yyPos;  // current y position
    public double xxVel;  // current velocity in the x direction
    public double yyVel;  // current velocity in the y direction
    public double mass;   // current mass
    public String imgFileName; // the name of the file that corresponding to the image


    /* construct a Planet */

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    } 
    
    /** 
     * construct a copy 
    */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /**  
     * calculate the distance 
    */
    public double calcDistance(Planet p) {
        double disX = p.xxPos - xxPos;
        double disY = p.yyPos - yyPos;
        double r = Math.sqrt(disX * disX  + disY * disY);
        return r;
    }

    /** calculate the force exerted from Planet P  
     * Dont use pow method 
    */
    public double calcForceExertedBy(Planet p) {
        double r = calcDistance(p);
        double F = G * mass * p.mass / (r * r);
        return F;
    }

    /** calculate the x-component of this force 
     *  Dont use ABS method 
    */
    public double calcForceExertedByX(Planet p) {
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        double disX = p.xxPos - xxPos;
        double fX = F * disX / r;
        return fX;
    }
    
    /**
     * calculate the y-component of this force 
    */
    public double calcForceExertedByY(Planet p) {
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        double disY = p.yyPos - yyPos;
        double fY = F * disY / r;
        return fY;
    }
    
    /**
     * calculate the net x-force of the all planets except the target planet 
    */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double fNetX = 0;
        for (int i = 0; i < allPlanets.length; i++) {
            if (this.equals(allPlanets[i])) {
                continue;
            } else {
                fNetX = fNetX + calcForceExertedByX(allPlanets[i]);
            }
        }
        return fNetX; 
    }
    
    /**
     * calculate the net y-force of the all planets except the target planet 
    */
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double fNetY = 0;
        for (int i = 0; i < allPlanets.length; i++) {
            if (this.equals(allPlanets[i])) {
                continue;
            } else {
                fNetY = fNetY + calcForceExertedByY(allPlanets[i]);
            }
        }
        return fNetY; 
    }

    /** 
     * update the new velocity and the new position 
    */
    public void update(double t, double fX, double fY) {
        double aNetX = fX / mass;
        double aNetY = fY / mass;
        xxVel = xxVel + t * aNetX;
        yyVel = yyVel + t * aNetY;
        xxPos = xxPos + t * xxVel;
        yyPos = yyPos + t * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }







}