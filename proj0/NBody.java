/* simulate a universe */
public class NBody {
    /**
     * read the file name and return the radius
     */
    public static double readRadius(String path) {
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    /** 
     * read the file name and construct the array of all planets
    */
    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int n = in.readInt();
        in.readDouble();
        Planet[] allPlanets = new Planet[n];
        for (int i = 0; i < n; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            allPlanets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return allPlanets;
    }

    /* draw the initial universe */    
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int n = planets.length;

    
        /* draw the background and the planets */
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (Planet planet : planets) {
            planet.draw();
        }

        
        /* a graphics technique to prevent flickering in the animation. */
        StdDraw.enableDoubleBuffering();
        for (double time = 0; time < T; time += dt) {
            double[] xForce = new double [n];
            double[] yForce = new double [n]; 
            
            for (int i = 0; i < n; i++) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int j = 0; j < n; j++) {
                planets[j].update(dt, xForce[j], yForce[j]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet planet : planets) {
                planet.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
        }
        
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
      
    }

}