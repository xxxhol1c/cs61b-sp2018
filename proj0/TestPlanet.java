/** 
 * check the pairwise and planet constructor 
*/

public class TestPlanet {
    

    /** 
     * check the pairwise force between the two planet 
    */
    public static void main(String[] args) {
        checkPairwise();
    }
    
    
    /** 
     * check whether the result is within the error range 
    */
    private static void checkEquals(double actual, double expected, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: Expected " + expected + " and you gave " + actual);
        }
    }
    
    /**
     *  check the method works correctly
    */
    private static void checkPairwise() {
        System.out.println("Checking Pairwise Force ...");
        Planet sun = new Planet(1.0e12, 2.0e11, 0.0, 0.0, 2.0e30, "sun.gif");
        Planet saturn = new Planet(2.3e12, 9.5e11, 0.0, 0.0, 6.0e26, "saturn.gif");
        checkEquals(sun.calcForceExertedBy(saturn), 3.6e22, 0.1);
    }
}
