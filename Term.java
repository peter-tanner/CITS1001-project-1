import java.util.Objects;

/**
 * Term represents one term in a Bydysawd chemical formula. 
 * A term has the symbol for a Bydysawd element, i.e. an upper-case letter, 
 * optionally followed by a positive integer, e.g. S or F460. 
 *
 * @author Lyndon While, Peter Tanner [23195279]
 * @version 2021
 */
public class Term
{
    // The element's symbol
    private char element;
    // How many atoms are there?
    private int count = 1;

    /**
     * Makes a term containing element and count.
     */
    public Term(char element, int count)
    {
        this.element = element;
        this.count = count;
    }
    
    /**
     * Parses s to construct a term. s will be a legal term with no whitespace, 
     * e.g. "S" or "F460". If there is no number, it represents one atom. 
     */
    public Term(String s)
    {
        String numStr = s.substring(1);
        this.element = s.charAt(0);
        if (numStr.length() > 0) {
            this.count = Integer.parseInt(numStr);
        }
    }

    /**
     * Returns the element symbol.
     */
    public char getElement()
    {
        return this.element;
    }

    /**
     * Returns the number of atoms.
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * Returns the term as a String. Omits the number if it is 1. 
     */
    public String display()
    {
        String displayString = String.valueOf(this.element);
        if (this.count == 1) {
            return displayString;
        }
        return (displayString+this.count);
    }
    
    @Override //Let's override so ArrayList<Term>.equals(ArrayList<T>) works.
    public boolean equals(Object other) {
        if ( !(other instanceof Term) ) {
            return false;
        }
        Term t2 = (Term) other;
        return (t2.getCount()==this.count && t2.getElement()==this.element);
    }
    @Override //NEED to override .hashCode() if overriding .equals() for HMaps.
    public int hashCode() {
        return Objects.hash(element, count);
    }
}