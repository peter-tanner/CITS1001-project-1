/**
 * Formula represents a Bydysawd chemical formula. 
 * A formula is a sequence of terms, e.g. AX3YM67. 
 *
 * @author Lyndon While, Peter Tanner [23195279]
 * @version 2021
 */
import java.util.ArrayList;

public class Formula
{
    // the constituent terms of the formula
    private ArrayList<Term> terms = new ArrayList<>();

    /**
     * Makes a formula containing a copy of terms.
     */
    @SuppressWarnings("unchecked")
    public Formula(ArrayList<Term> terms)
    {
        this.terms = (ArrayList<Term>) terms.clone();
    }

    /**
     * Parses s to construct a formula. s will be a legal sequence 
     * of terms with no whitespace, e.g. "AX3YM67" or "Z".  
     * The terms in the field must be in the same order as in s. 
     */
    public Formula(String s)
    {
        String[] termsStr = s.split("(?=[^0-9])");//Split along terms (symbols).
        for (String term : termsStr) {
            this.terms.add(new Term(term));
        }
    }

    /**
     * Returns the terms of the formula.
     */
    public ArrayList<Term> getTerms()
    {
        return this.terms;
    }
    
    /**
     * Returns the index in s where the rightmost upper-case letter sits, 
     * e.g. lastTerm("AX3YM67") returns 4. 
     * Returns -1 if there are no upper-case letters. 
     */
    public static int lastUC(String s)
    {
        for (int i = s.length()-1; i >= 0; i--) {
            if (Character.isUpperCase( s.charAt(i) )) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Returns the index in s where the leftmost upper-case letter sits, 
     * e.g. lastTerm("AX3YM67") returns 4. 
     * Returns -1 if there are no upper-case letters. 
     * Should be faster than using Regex (s.replaceAll("[A-Z].*","").length())
     */
    public static int firstUC(String s)
    {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase( s.charAt(i) )) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Returns the total number of atoms of element in terms. 
     * e.g. if terms = <<W,2>,<X,1>,<W,5>>, countElement('W') returns 7, 
     * countElement('X') returns 1, and countElement('Q') returns 0.
     */
    public int countElement(char element)
    {
        int count = 0;
        for (Term term : this.terms) {
            if (term.getElement() == element) {
                count += term.getCount();
            }
        }
        return count;
    }

    /**
     * Puts terms in standardised form, where each element present is 
     * represented by exactly one term, and terms are in alphabetical order.
     * e.g. <<C,3>,<D,1>,<B,2>,<D,2>,<C,1>> becomes <<B,2>,<C,4>,<D,3>>.
     */
    public void standardise()
    {
        ArrayList<Term> termList = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            int elemTotal = this.countElement(c);
            if (elemTotal > 0) {
                termList.add( new Term(c, elemTotal) );
            }
        }
        this.terms = termList;
    }

    /**
     * Returns true if this formula and other are isomers, 
     * i.e. they contain the same number of every Bydysawd element. 
     */
    public boolean isIsomer(Formula other)
    {
        //Shallow copy to not (destructively) standardize originals
        Formula formula1 = new Formula(this.terms);
        Formula formula2 = new Formula(other.terms);
        formula1.standardise();
        formula2.standardise();
        ArrayList<Term> formula1Terms = formula1.getTerms();
        ArrayList<Term> formula2Terms = formula2.getTerms();
        return formula1Terms.equals(formula2Terms);
    }

    /**
     * Returns the formula as a String. 
     * e.g. if terms = <<B,22>,<E,1>,<D,3>>, it returns "B22ED3". 
     */
    public String display()
    {
        String formulaString = "";
        for (Term term : this.terms) {
            formulaString += term.display();
        }
        return formulaString;
    }  
}