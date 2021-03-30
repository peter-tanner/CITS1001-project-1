/**
 * Formula represents a Bydysawd chemical formula. 
 * A formula is a sequence of terms, e.g. AX3YM67. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Formula
{
    // the constituent terms of the formula
    private ArrayList<Term> terms;

    /**
     * Makes a formula containing a copy of terms.
     */
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
        this.terms = new ArrayList<>();
        String[] termsStr = s.split("(?=[A-Z])");
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
        //Let's not use stringbuilder for this one...
        char[] formulaChars = s.toCharArray();

        //Index in reverse - should yield better times on average but still O(n)
        for (int i = formulaChars.length-1; i >= 0; i--) {
            if (Character.isLetter( formulaChars[i] )) {
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
     * Converts the formula object to a hashmap with each element
     * character mapping to its coefficient
     * @return HashMap<Character,Integer>
     */
    public HashMap<Character,Integer> getHashMap() {
        HashMap<Character,Integer> hm = new HashMap<>();
        for (Term term : this.terms) {
            char element = term.getElement();
            int elementTotal = term.getCount();
            if (hm.get(element) != null) {
                elementTotal += hm.get(element);
            }
            hm.put(element, elementTotal);
        }
        return hm;
    }

    /**
     * Puts terms in standardised form, where each element present is 
     * represented by exactly one term, and terms are in alphabetical order.
     * e.g. <<C,3>,<D,1>,<B,2>,<D,2>,<C,1>> becomes <<B,2>,<C,4>,<D,3>>.
     */
    public void standardise()
    {
        HashMap<Character,Integer> hm = this.getHashMap();
        this.terms.clear();
        for (char element : hm.keySet()) {
            Term standardTerm = new Term(element, hm.get(element));
            this.terms.add(standardTerm);
        }
    }

    /**
     * Returns true iff this formula and other are isomers, 
     * i.e. they contain the same number of every Bydysawd element. 
     */
    public boolean isIsomer(Formula other)
    {
        HashMap<Character,Integer> hm2 = other.getHashMap();
        HashMap<Character,Integer> hm1 = this.getHashMap();

        if (hm2.equals(hm1)) {
            return true; 
        }
        return false;
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
