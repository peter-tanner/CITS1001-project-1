    /**
 * Equation represents a Bydysawd chemical equation. 
 * An equation can have multiple formulas on each side, 
 * e.g. X3 + Y2Z2 = ZX + Y2X2 + Z. 
 *
 * @author Lyndon While, Peter Tanner [23195279]
 * @version 2021
 */
import java.util.ArrayList;
import java.util.Collections;

public class Equation
{
    // the two sides of the equation 
    // there can be multiple formulas on each side 
    private ArrayList<Formula> lhs, rhs;

    /**
     * Parses s to construct an equation. s will contain a 
     * syntactically legal equation, e.g. X3 + Y2Z = ZX + Y2X4. 
     * s may contain whitespace between formulas **and symbols**. 
     */
    public Equation(String s)
    {
        String[] sides = s.split("=");
        this.lhs = parseSide(sides[0]);
        this.rhs = parseSide(sides[1]);
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
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == x) {
                indexes.add(i);
            }          
        }
        return indexes;
    }

    /**
     * Parses s as one side of an equation. 
     * s will contain a series of formulas separated by pluses, 
     * and it may contain whitespace between formulas and symbols. 
     * Added splitting functionality to account for extension (multiplier prefx)
     */
    public static ArrayList<Formula> parseSide(String s)
    {
        ArrayList<Formula> side = new ArrayList<>();
        String[] formulas = s.replaceAll("\\s","").split("\\+");
        for (String formulaString : formulas) {
            int mult = 1;
            int formulaStart = Formula.firstUC(formulaString);
            if (formulaStart > 0) {
                String multString = formulaString.substring(0,formulaStart);
                formulaString = formulaString.substring(formulaStart);
                mult = Integer.parseInt(multString);
            }
            Formula formula = new Formula(formulaString);
            side.addAll( Collections.nCopies(mult, formula) );
        }
        return side;
    }

    /**
     * Returns true if the equation is balanced, i.e. it has the 
     * same number of atoms of each Bydysawd element on each side. 
     */
    public boolean isValid()
    {
        Formula lhsFormula = sideSum(this.lhs);
        Formula rhsFormula = sideSum(this.rhs);
        return lhsFormula.isIsomer(rhsFormula);
    }
    private static Formula sideSum(ArrayList<Formula> side) {
        ArrayList<Term> totalFormulaTerms = new ArrayList<>();
        for (Formula formula : side) {
            totalFormulaTerms.addAll( formula.getTerms() );
        }
        Formula totalFormula = new Formula(totalFormulaTerms);
        totalFormula.standardise();
        return totalFormula;
    }

    /**
     * Returns the equation as a String.
     */
    public String display()
    {
        return ( join(this.lhs) + "=" + join(this.rhs) );
    }
    private static String join(ArrayList<Formula> side) {
        ArrayList<String> formulaArray = new ArrayList<>();
        for (Formula formula : side) {
            formulaArray.add( formula.display() );
        }
        return String.join("+",formulaArray);
    }
}
