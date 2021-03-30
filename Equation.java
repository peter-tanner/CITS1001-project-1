    /**
 * Equation represents a Bydysawd chemical equation. 
 * An equation can have multiple formulas on each side, 
 * e.g. X3 + Y2Z2 = ZX + Y2X2 + Z. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Equation
{
    // the two sides of the equation 
    // there can be multiple formulas on each side 
    private ArrayList<Formula> lhs, rhs;

    /**
     * Parses s to construct an equation. s will contain a 
     * syntactically legal equation, e.g. X3 + Y2Z = ZX + Y2X4. 
     * s may contain whitespace between formulas and symbols. 
     */
    public Equation(String s)
    {
        String[] sides = s.split("=");
        this.lhs = parseSide(sides[0]);
        this.rhs = parseSide(sides[1]);
        //regex: Get rid of the whitespace.
    }

    /**
     * Returns the left-hand side of the equation.
     */
    public ArrayList<Formula> getLHS()
    {
        return this.lhs;
    }

    /**
     * Returns the right-hand side of the equation.
     */
    public ArrayList<Formula> getRHS()
    {
        return this.rhs;
    }
    
    /**
     * Returns the indices at which x occurs in s, 
     * e.g. indicesOf("ax34x", 'x') returns <1,4>. 
     */
    public static ArrayList<Integer> indicesOf(String s, char x)
    {
        ArrayList<Integer> indexes = new ArrayList<>();
        int i = 0;
        for (char c : s.toCharArray()) {
            if (c == x) {
                indexes.add(i);
            }
            i++;
        }
        return indexes;
    }
    

    public static int parseMultiplier(String s) {
        String numStr = "";
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                numStr += c;
            } else {
                break;
            }
        }
        if (numStr != "") {
            return Integer.parseInt(numStr);
        } else {
            return 1;
        }
    }

    /**
     * Parses s as one side of an equation. 
     * s will contain a series of formulas separated by pluses, 
     * and it may contain whitespace between formulas and symbols. 
     */
    public static ArrayList<Formula> parseSide(String sideStr)
    {
        ArrayList<Formula> side = new ArrayList<>();
        sideStr = sideStr.replaceAll("\\s","");
        for (String formulaStr : sideStr.split("\\+")) {
            int mult = parseMultiplier(formulaStr);
            formulaStr = formulaStr.replaceAll("^[0-9]+", "");
            Formula formula = new Formula(formulaStr);
            for (int i = 0; i < mult; i++) {
                side.add(formula);
            }
        }
        return side;

        // // Make an intentionally unbalanced equation (<sideStr>=Z).
        // // Since we're getting only one side of the equation (LEFT), right can
        // //      be anything (Z)
        // // We're doing this so that we can leverage the parser that I already
        // //      built into the constructor.
        // Equation eqn = new Equation(sideStr+"=Z");
        // return eqn.getLHS();
    }


    private HashMap<Character,Integer> sideSum(HashMap<Character,Integer> hm, ArrayList<Formula> side, int multiplier) {
        for (Formula formula : side) {
            HashMap<Character,Integer> formulaHM = formula.getHashMap();
            for (char elem : formulaHM.keySet()) {
                int total = multiplier*formulaHM.get(elem);
                if (hm.get(elem) != null) {
                    total += hm.get(elem);
                }
                hm.put(elem, total);
            }
        }
        return hm;
    }

    /**
     * Returns true iff the equation is balanced, i.e. it has the 
     * same number of atoms of each Bydysawd element on each side. 
     */
    public boolean isValid()
    {
        HashMap<Character,Integer> hm = new HashMap<>();

        hm = sideSum(hm, lhs, 1); //Get sum of coefficients of each elem on lhs
        hm = sideSum(hm, rhs, -1);//Subtract rhs coeffs from lhs (-1 multiplier)
        
        for (char elem : hm.keySet()) {
            // If sides are balanced, we expect that subtracting the
            //      coefficients of one side from the other equals zero.
            // So if an equation is invalid/unbalanced, subtracting one side
            //      from the other yields a non-zero sum, and we return false
            if (hm.get(elem) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the equation as a String.
     */
    public String display()
    {
        String displayStr = "";
        for (Formula formula : this.lhs) {
            displayStr += formula.display()+"+";
        }
        displayStr = displayStr.substring(0, displayStr.length()-1); //Remove trailing "+"
        displayStr += "=";
        for (Formula formula : this.rhs) {
            displayStr += formula.display()+"+";
        }
        displayStr = displayStr.substring(0, displayStr.length()-1); //Remove trailing "+"
        return displayStr;
    }
}
