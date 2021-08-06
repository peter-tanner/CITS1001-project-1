import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * MarkProject1 tests Project 1.
 *
 * @author  Lyndon While
 * @version 2021 v1
 */
import java.util.ArrayList;

public class MarkProject1
{
    String[]   ss, fs;
    String[][] fs1;
    Term[]     ts;
    Formula[]  cs;
    String[]   es;
    Equation   eqn;
    String err; // used for error msgs

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        ss = new String[]  {"O", "U8", "G11", "S100", "W70000", "A987321"};
        ts = new Term[ss.length+1];
        ts[0] = new Term('P', Integer.MAX_VALUE / 234);
        for (int k = 1; k < ts.length; k++) ts[k] = new Term(ss[k-1]);
        
        fs = new String[]  {"P", "R3", "A34B", "BA34", "UK5P32", "C1000F100I10L", "EEEE"};
        cs = new Formula[fs.length];
        for (int k = 0; k < fs.length; k++)
            cs[k] = new Formula(fs[k]);
        
        fs1 = new String[][] {{"P"}, {"R2R", "RRR"}, {"A30BA4", "BA9A11A11A3", "BA3A31"}, {}
                             ,{"P21K4P9UKP2", "PKP14KP7KPKPKUP8", "KP5KP5KP12KP5KUP5"}
                             ,{"F25C750LI5F75I5C250", "FCLIF99I9C999"}
                             ,{"E2E2", "E3E", "EE3", "E4"}};
        
        es = new String[] {"P3=P+P+P", "ZX2+XZ=Z2X3", "V3+G3=GV+VG+GV", "AB+B3C2+C4A=A2B4+C6", 
                           "QUIE+T=Q2T5", "R89+S9+E=O11X+W34", "A13+B500+B56=A11B+AB222+AB333"};
    }

    @Test
    public void testTerm1()
    {
        assertEquals("",     'P', ts[0].getElement());
        assertEquals("", 9177280, ts[0].getCount());
    }

    @Test
    public void testTerm2()
    {
        assertEquals("",    'O', ts[1].getElement());
        assertEquals("",      1, ts[1].getCount());
        assertEquals("",    'U', ts[2].getElement());
        assertEquals("",      8, ts[2].getCount());
        assertEquals("",    'G', ts[3].getElement());
        assertEquals("",     11, ts[3].getCount());
        assertEquals("",    'S', ts[4].getElement());
        assertEquals("",    100, ts[4].getCount());
        assertEquals("",    'W', ts[5].getElement());
        assertEquals("",  70000, ts[5].getCount());
        assertEquals("",    'A', ts[6].getElement());
        assertEquals("", 987321, ts[6].getCount());
    }

    @Test
    public void testTermDisplay()
    {
        assertTrue("", ts[0].display().equals("P9177280"));
        for (int k = 1; k < ts.length; k++)
            assertTrue("" + k, ts[k].display().equals(ss[k-1]));
    }

    @Test
    public void testFormula()
    {
        for (int k = 0; k < 2; k++)
        {
            err = "" + k;
            assertEquals(err,           1, cs[k].getTerms().size());
            assertFalse (err,              cs[k].getTerms().get(0) == null);
            assertEquals(err, 'P' + 2 * k, cs[k].getTerms().get(0).getElement());
            assertEquals(err,   1 + 2 * k, cs[k].getTerms().get(0).getCount());
        }
        
        for (int k = 2; k < 4; k++)
        {
            err = "" + k;
            assertEquals(err,   2, cs[k].getTerms().size());
            assertFalse (err,      cs[k].getTerms().get(0) == null);
            assertEquals(err, 'A', cs[k].getTerms().get(k-2).getElement());
            assertEquals(err,  34, cs[k].getTerms().get(k-2).getCount());
            assertFalse (err,      cs[k].getTerms().get(1) == null);
            assertEquals(err, 'B', cs[k].getTerms().get(3-k).getElement());
            assertEquals(err,   1, cs[k].getTerms().get(3-k).getCount());
        }
    
        assertEquals("",   3, cs[4].getTerms().size());
        assertFalse ("",      cs[4].getTerms().get(0) == null);
        assertEquals("", 'U', cs[4].getTerms().get(0).getElement());
        assertEquals("",   1, cs[4].getTerms().get(0).getCount());
        assertFalse ("",      cs[4].getTerms().get(1) == null);
        assertEquals("", 'K', cs[4].getTerms().get(1).getElement());
        assertEquals("",   5, cs[4].getTerms().get(1).getCount());
        assertFalse ("",      cs[4].getTerms().get(2) == null);
        assertEquals("", 'P', cs[4].getTerms().get(2).getElement());
        assertEquals("",  32, cs[4].getTerms().get(2).getCount());
        
        assertEquals("",   4, cs[5].getTerms().size());
        for (int k = 0; k < 4; k++)
        {
            err = "" + k;
            assertFalse (err,                              cs[5].getTerms().get(k) == null);
            assertEquals(err,                 'C' + 3 * k, cs[5].getTerms().get(k).getElement());
            assertEquals(err, 1000 / (int) Math.pow(10,k), cs[5].getTerms().get(k).getCount());
        }
        
        assertEquals("",   4, cs[6].getTerms().size());
        for (int k = 0; k < 4; k++)
        {
            err = "" + k;
            assertFalse (err,      cs[6].getTerms().get(k) == null);
            assertEquals(err, 'E', cs[6].getTerms().get(k).getElement());
            assertEquals(err,   1, cs[6].getTerms().get(k).getCount());
        }
    }

    @Test
    public void testlastUC()
    {
        String[] xs = {"", "2", "?", " ", "34", "to", "  ", "    ", "xyzwxyz"};
        String[] ys = {"", "3", "h2", "abc", "a2D3", "    ", ".#$/", "rf3RF", "HEllO!"};
        for (int x = 0; x < xs.length; x++)
            for (String y : ys)
            {
                err = x + " " + y;
                assertEquals(err,         -1, Formula.lastUC(y.toLowerCase() + xs[x].toLowerCase())); // no upper-case
                String s = (char) ('A' + x) + xs[x].toLowerCase();
                assertEquals(err, y.length(), Formula.lastUC(y               + s)); // the added char is the rightmost
                assertEquals(err, y.length(), Formula.lastUC(y.toLowerCase() + s));
                assertEquals(err, y.length(), Formula.lastUC(y.toUpperCase() + s));
            }
    }

    @Test
    public void testcountElement()
    {
        boolean b = true;
        char upto = 'L';
        int n = 7;
        String[] s = new String[n];
        s[0] = "A";
        for (int k = 1; k <= upto - 'A'; k++)
        {
            char el = (char) (upto + k % ('Z' - upto + 1));
            // System.out.println(s + " " + el);
            assertEquals(s + " " + el, 0, new Formula(s[0]).countElement(el));
            int count = 0;
            for (int c = 0; c < n-1; c++)
            {
                for (int j = 0; j <= s[c].length() - c; j++)
                    if (j == s[c].length() || Character.isUpperCase(s[c].charAt(j)))
                    {
                        s[c+1] = s[c].substring(0,j) + el + k + s[c].substring(j,s[c].length());
                        // System.out.println(s[c+1] + " " + el + " " + (count + k));
                        assertEquals(s[c+1] + " " + el + " " + (count + k), count + k, 
                                     new Formula(s[c+1]).countElement(el));
                    }
                count += k;
            }
            if (b) s[0] =  s[0] + (char) ('A' + k) + (k + 1);
            else   s[0] = "" + (char) ('A' + k) + (k + 101) + s[0];
            b = !b;
        }
    }
    
    // returns true iff f1 and f2 are identical 
    private static boolean identicalFormulas(Formula f1, Formula f2)
    {
        if (f1 == null           || f1.getTerms() == null) return false;
        if (f2 == null           || f2.getTerms() == null) return false;
        if (f1.getTerms().size() != f2.getTerms().size())  return false;
        for (int k = 0; k < f1.getTerms().size(); k++)
        {
            if (f1.getTerms().get(k) == null      || f2.getTerms().get(k) == null)      return false;
            if (f1.getTerms().get(k).getElement() != f2.getTerms().get(k).getElement()) return false;
            if (f1.getTerms().get(k).getCount()   != f2.getTerms().get(k).getCount())   return false;
        }
        return true;
    }

    @Test
    public void teststandardise()
    {
        for (int k = 0; k < 2; k++)
        {
            err = "" + k;
            cs[k].standardise();
            assertEquals(err,           1, cs[k].getTerms().size());
            assertFalse (err,              cs[k].getTerms().get(0) == null);
            assertEquals(err, 'P' + 2 * k, cs[k].getTerms().get(0).getElement());
            assertEquals(err,   1 + 2 * k, cs[k].getTerms().get(0).getCount());
        }
        
        for (int k = 2; k < 4; k++)
        {
            err = "" + k;
            cs[k].standardise();
            assertEquals(err,   2, cs[k].getTerms().size());
            assertFalse (err,      cs[k].getTerms().get(0) == null);
            assertEquals(err, 'A', cs[k].getTerms().get(0).getElement());
            assertEquals(err,  34, cs[k].getTerms().get(0).getCount());
            assertFalse (err,      cs[k].getTerms().get(1) == null);
            assertEquals(err, 'B', cs[k].getTerms().get(1).getElement());
            assertEquals(err,   1, cs[k].getTerms().get(1).getCount());
        }
    
        cs[4].standardise();
        assertEquals("",   3, cs[4].getTerms().size());
        assertFalse ("",      cs[4].getTerms().get(2) == null);
        assertEquals("", 'U', cs[4].getTerms().get(2).getElement());
        assertEquals("",   1, cs[4].getTerms().get(2).getCount());
        assertFalse ("",      cs[4].getTerms().get(0) == null);
        assertEquals("", 'K', cs[4].getTerms().get(0).getElement());
        assertEquals("",   5, cs[4].getTerms().get(0).getCount());
        assertFalse ("",      cs[4].getTerms().get(1) == null);
        assertEquals("", 'P', cs[4].getTerms().get(1).getElement());
        assertEquals("",  32, cs[4].getTerms().get(1).getCount());
        
        cs[5].standardise();
        assertEquals("",   4, cs[5].getTerms().size());
        for (int k = 0; k < 4; k++)
        {
            err = "" + k;
            assertFalse (err,                              cs[5].getTerms().get(k) == null);
            assertEquals(err,                 'C' + 3 * k, cs[5].getTerms().get(k).getElement());
            assertEquals(err, 1000 / (int) Math.pow(10,k), cs[5].getTerms().get(k).getCount());
        }
        
        cs[6].standardise();
        assertEquals("",   1, cs[6].getTerms().size());
        assertFalse ("",      cs[6].getTerms().get(0) == null);
        assertEquals("", 'E', cs[6].getTerms().get(0).getElement());
        assertEquals("",   4, cs[6].getTerms().get(0).getCount());
        
        for (int k = 0; k < fs1.length; k++)
            for (String s : fs1[k])
            {
                Formula f = new Formula(s);
                f.standardise();
                assertTrue(k + " " + s, identicalFormulas(cs[k], f));
            }
    }
    
    @Test
    public void testisIsomer()
    {
        for (int k = 0; k < fs1.length; k++)
            for (String s : fs1[k])
                assertTrue(k + " " + s, cs[k].isIsomer(new Formula(s)));
        for (int k = 1; k < fs1.length; k++) // first one has only one char
            for (String s : fs1[k])
                assertFalse(k + " " + s, cs[k].isIsomer(new Formula(s.substring(0,s.length()-1))));
        for (int k = 0; k < fs1.length; k++) 
            for (String s : fs1[k])
            {
                err = k + " " + s;
                assertFalse(err, cs[k].isIsomer(new Formula("D" + s)));
                assertFalse(err, cs[k].isIsomer(new Formula(s + "B")));
                assertFalse(err, cs[k].isIsomer(new Formula(s + "2")));
            }
            
        for (int k = 0; k < cs.length; k++)
            for (int j = 0; j < cs.length; j++)
                assertEquals(k + " " + j, j == k || j == 2 && k == 3 || k == 2 && j == 3, cs[k].isIsomer(cs[j]));
        
        assertTrue ("", cs[4].isIsomer(new Formula("P32K5U")));
        assertTrue ("", cs[4].isIsomer(new Formula("P32KK4U")));
        assertTrue ("", cs[4].isIsomer(new Formula("KKKKKP2P23UP7")));
        assertFalse("", cs[4].isIsomer(new Formula("UM5P32")));
        assertFalse("", cs[4].isIsomer(new Formula("UK5P33")));
        assertFalse("", cs[4].isIsomer(new Formula("U2K5P32")));
    }
    
    @Test
    public void testFormulaDisplay()
    {
        for (int k = 0; k < cs.length; k++)
            assertTrue("" + k, cs[k].display().equals(fs[k]));
    } 
    
    @Test
    public void testEquation()
    {
        String s;
        s = es[0];
        for (int x : new int[] {3,2,5,2,8,2,12,11,15,2})
        {
            err = es[0];
            // System.out.println(s);
            eqn = new Equation(es[0]);
            assertEquals(err,   1, eqn.getLHS().size());
            assertEquals(err,   1, eqn.getLHS().get(0).getTerms().size());
            assertEquals(err, 'P', eqn.getLHS().get(0).getTerms().get(0).getElement());
            assertEquals(err,   3, eqn.getLHS().get(0).getTerms().get(0).getCount());
            assertEquals(err,   3, eqn.getRHS().size());
            for (int k = 0; k < 3; k++)
            {
                err = s + " " + k;
                assertEquals(err,   1, eqn.getRHS().get(k).getTerms().size());
                assertEquals(err, 'P', eqn.getRHS().get(k).getTerms().get(0).getElement());
                assertEquals(err,   1, eqn.getRHS().get(k).getTerms().get(0).getCount());
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
        
        s = es[1];
        for (int x : new int[] {7,7,6,4,3,8,5,3,3})
        {
            err = s;
            // System.out.println(s);
            eqn = new Equation(s);
            assertEquals(err,   2, eqn.getLHS().size());
            assertEquals(err,   2, eqn.getLHS().get(0).getTerms().size());
            assertEquals(err, 'Z', eqn.getLHS().get(0).getTerms().get(0).getElement());
            assertEquals(err,   1, eqn.getLHS().get(0).getTerms().get(0).getCount());
            assertEquals(err, 'X', eqn.getLHS().get(0).getTerms().get(1).getElement());
            assertEquals(err,   2, eqn.getLHS().get(0).getTerms().get(1).getCount());
            assertEquals(err, 'X', eqn.getLHS().get(1).getTerms().get(0).getElement());
            assertEquals(err,   1, eqn.getLHS().get(1).getTerms().get(0).getCount());
            assertEquals(err, 'Z', eqn.getLHS().get(1).getTerms().get(1).getElement());
            assertEquals(err,   1, eqn.getLHS().get(1).getTerms().get(1).getCount());
            assertEquals(err,   1, eqn.getRHS().size());
            assertEquals(err,   2, eqn.getRHS().get(0).getTerms().size());
            assertEquals(err, 'Z', eqn.getRHS().get(0).getTerms().get(0).getElement());
            assertEquals(err,   2, eqn.getRHS().get(0).getTerms().get(0).getCount());
            assertEquals(err, 'X', eqn.getRHS().get(0).getTerms().get(1).getElement());
            assertEquals(err,   3, eqn.getRHS().get(0).getTerms().get(1).getCount());
            s = s.substring(0,x) + " " + s.substring(x);
        }
        
        s = es[2];
        for (int x : new int[] {12,11,9,8,3,2,8,7,0})
        {
            // System.out.println(s);
            eqn = new Equation(s);
            assertEquals(s, 2, eqn.getLHS().size());
            for (int k = 0; k < 2; k++)
            {
                err = s + " " + k;
                assertEquals(err,                1, eqn.getLHS().get(k).getTerms().size());
                assertEquals(err, 'V' - k % 2 * 15, eqn.getLHS().get(k).getTerms().get(0).getElement());
                assertEquals(err,                3, eqn.getLHS().get(k).getTerms().get(0).getCount());
            }
            assertEquals("" + x, 3, eqn.getRHS().size());
            for (int k = 0; k < 3; k++)
            {
                err = s + " " + k;
                assertEquals(err,                2, eqn.getRHS().get(k).getTerms().size());
                assertEquals(err, 'G' + k % 2 * 15, eqn.getRHS().get(k).getTerms().get(0).getElement());
                assertEquals(err,                1, eqn.getRHS().get(k).getTerms().get(0).getCount());
                assertEquals(err, 'V' - k % 2 * 15, eqn.getRHS().get(k).getTerms().get(1).getElement());
                assertEquals(err,                1, eqn.getRHS().get(k).getTerms().get(1).getCount());
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
        
        s = es[3];
        for (int x : new int[] {2,4,9,11,15,17,22,24,0})
        {
            // System.out.println(s);
            eqn = new Equation(s);
            assertEquals(s, 3, eqn.getLHS().size());
            for (int k = 0; k < 3; k++)
            {
                assertEquals(s + " " + k, 2, eqn.getLHS().get(k).getTerms().size());
                for (int j = 0; j < 2; j++)
                {
                    err = s + " " + k + " " + j;
                    assertEquals(err, 'A' + (k + j) % 3, eqn.getLHS().get(k).getTerms().get(j).getElement());
                    assertEquals(err, (int) Math.pow(2,k) - (k - j == 1 ? 4 * j - 1: 0), 
                                 eqn.getLHS().get(k).getTerms().get(j).getCount());
                }
            }
            assertEquals(s, 2, eqn.getRHS().size());
            for (int k = 0; k < 2; k++)
            {
                assertEquals(s + " " + k, 2 - k, eqn.getRHS().get(k).getTerms().size());
                for (int j = 0; j < 2 - k; j++)
                {
                    err = s + " " + k + " " + j;
                    assertEquals(err,   'A' + 2 * k + j, eqn.getRHS().get(k).getTerms().get(j).getElement());
                    assertEquals(err, 2 + 4 * k + 2 * j, eqn.getRHS().get(k).getTerms().get(j).getCount());
                }
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
        
        s = es[4];
        for (int x : new int[] {4,8,6,8,4,12,7,11,0})
        {
            // System.out.println(s);
            eqn = new Equation(s);
            assertEquals(s,   2, eqn.getLHS().size());
            assertEquals(s,   4, eqn.getLHS().get(0).getTerms().size());
            for(int k = 0; k < 4; k++)
            {
                err = s + " " + k;
                assertEquals(err, "QUIE".charAt(k), eqn.getLHS().get(0).getTerms().get(k).getElement());
                assertEquals(err,                1, eqn.getLHS().get(0).getTerms().get(k).getCount());
            }
            assertEquals(s,   1, eqn.getLHS().get(1).getTerms().size());
            assertEquals(s, 'T', eqn.getLHS().get(1).getTerms().get(0).getElement());
            assertEquals(s,   1, eqn.getLHS().get(1).getTerms().get(0).getCount());
            assertEquals(s,   1, eqn.getRHS().size());
            assertEquals(s,   2, eqn.getRHS().get(0).getTerms().size());
            assertEquals(s, 'Q', eqn.getRHS().get(0).getTerms().get(0).getElement());
            assertEquals(s,   2, eqn.getRHS().get(0).getTerms().get(0).getCount());
            assertEquals(s, 'T', eqn.getRHS().get(0).getTerms().get(1).getElement());
            assertEquals(s,   5, eqn.getRHS().get(0).getTerms().get(1).getCount());
            s = s.substring(0,x) + " " + s.substring(x);
        }
    }
    
    // returns a comment on the lists xs and ys
    private String samelist(ArrayList<Integer> xs, ArrayList<Integer> ys)
    {
        if (xs == null)             return "1st list is null";
        if (ys == null)             return "2nd list is null";
        if (xs.size() != ys.size()) return "lists have different lengths";
        for (int k = 0; k < xs.size(); k++)
            if (xs.get(k).intValue() != ys.get(k).intValue()) 
               return "first difference is at Index " + k; 
        return "same lists";
    }
    
    @Test
    public void testindicesOf()
    {
        int n = 4;
        boolean b = true;
        String[] s = new String[n];
        s[0] = "";
        char find = '?';
        ArrayList<Integer> res = new ArrayList<>();
        for (String t : new String[] {"a", " a", "a ", " a "})
        {
            assertTrue("NEW", samelist(res, Equation.indicesOf(t, 'A')).equals("same lists"));
            assertTrue("NEW", samelist(res, Equation.indicesOf(t.toUpperCase(), 'a')).equals("same lists"));
            res.add(t.indexOf('a'));
            assertTrue("NEW", samelist(res, Equation.indicesOf(t, 'a')).equals("same lists"));
            assertTrue("NEW", samelist(res, Equation.indicesOf(t.toUpperCase(), 'A')).equals("same lists"));
            res.clear();
        }
        for (int k = 0; k < 8; k++)
        {
            // System.out.println(s[0] + " " + find);
            assertTrue("" + k, samelist(res, Equation.indicesOf(s[0], find)).equals("same lists"));
            for (int j = 0; j <= s[0].length(); j++)
            {
                s[1] = s[0].substring(0,j) + find + s[0].substring(j);
                res.add(j);
                // System.out.println(s[1] + " " + find);
                assertTrue("" + k + " " + j, samelist(res, Equation.indicesOf(s[1], find)).equals("same lists"));
                for (int i = j+1; i <= s[1].length(); i++)
                {
                    s[2] = s[1].substring(0,i) + find + s[1].substring(i);
                    res.add(i);
                    // System.out.println(s[2] + " " + find);
                    assertTrue("" + k + " " + j + " " + i, samelist(res, Equation.indicesOf(s[2], find)).equals("same lists"));
                    for (int m = i+1; m <= s[2].length(); m++)
                    {
                        s[3] = s[2].substring(0,m) + find + s[2].substring(m);
                        res.add(m);
                        // System.out.println(s[3] + " " + find);
                        assertTrue("" + k + " " + j + " " + i + " " + m, samelist(res, Equation.indicesOf(s[3], find)).equals("same lists"));
                        res.remove(2);
                    }
                    res.remove(1);
                }
                res.remove(0);
            }
            if (b) s[0] = s[0] + (char) ('a' + k);
            else   s[0] = (char) ('a' + k) + s[0];
            b = !b;
        }
    }
    
    @Test
    public void testparseSide()
    {
        ArrayList<Formula> c; 
        ArrayList<Term> ts;
        String s;
        
        c = Equation.parseSide("Q"); 
        assertEquals("", 1, c.size()); 
        ts = c.get(0).getTerms();
        assertEquals("", 1, ts.size()); 
        assertEquals("", 'Q', ts.get(0).getElement()); 
        assertEquals("",   1, ts.get(0).getCount()); 
        
        c = Equation.parseSide("WA12"); 
        assertEquals("", 1, c.size()); 
        ts = c.get(0).getTerms();
        assertEquals("", 2, ts.size()); 
        for (int k = 0; k < 2; k++)
        {
            err = "" + k;
            assertEquals(err, 'W' - k * 22, ts.get(k).getElement()); 
            assertEquals(err,   1 + k * 11, ts.get(k).getCount()); 
        }
        
        s = "J4+T14";
        for (int x : new int[] {3,2,5,2,7,2,9,2})
        {
            // System.out.println(s);
            c = Equation.parseSide(s); 
            assertEquals(s, 2, c.size()); 
            for (int k = 0; k < 2; k++)
            {
                err = s + " " + k;
                ts = c.get(k).getTerms();
                assertEquals(err,            1, ts.size()); 
                assertEquals(err, 'J' + k * 10, ts.get(0).getElement()); 
                assertEquals(err,   4 + k * 10, ts.get(0).getCount()); 
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
        
        s = "ZG2Z3+A12F27+C22G9+E32H3";
        for (int x : new int[] {5,6,21,21,20,14,14,17,8,8,8,8,8,8,8})
        {
            err = s;
            // System.out.println(s);
            c = Equation.parseSide(s); 
            assertEquals(err,   4, c.size()); 
            ts = c.get(0).getTerms();
            assertEquals(err,   3, ts.size()); 
            for (int k = 0; k < 3; k++)
            {
                err = s + " " + k;
                assertEquals(err, 'Z' - 19 * (k % 2), ts.get(k).getElement()); 
                assertEquals(err,              k + 1, ts.get(k).getCount()); 
            }
            for (int k = 1; k < 4; k++)
            {
                err = s + " " + k;
                ts = c.get(k).getTerms();
                assertEquals(err,                        2, ts.size());
                assertEquals(err,          'A' + 2 * (k-1), ts.get(0).getElement()); 
                assertEquals(err,               2 + 10 * k, ts.get(0).getCount()); 
                assertEquals(err,                  'E' + k, ts.get(1).getElement()); 
                assertEquals(err, 81 / (int) Math.pow(3,k), ts.get(1).getCount()); 
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
    }
    
    @Test
    public void testextension()
    {
        ArrayList<Formula> c; 
        ArrayList<Term> ts;
        String s;
        
        for (int k = 2; k < 40; k += 6)
        {
            s = k + "Q";
            // System.out.println(s);
            c = Equation.parseSide(s); 
            assertEquals(s, k, c.size()); 
            for (int j = 0; j < k; j++)
            {
                err = s + " " + j;
                ts = c.get(j).getTerms();
                assertEquals(err, 1, ts.size()); 
                assertEquals(err, 'Q', ts.get(0).getElement()); 
                assertEquals(err,   1, ts.get(0).getCount()); 
            }
        }
    
        for (int n = 3; n < 400000; n *= 10)
        {
            s = n + "WA12";
            // System.out.println(s);
            c = Equation.parseSide(s); 
            assertEquals(s, n, c.size()); 
            for (int j = 0; j < n; j++)
            {
                ts = c.get(j).getTerms();
                assertEquals(s + " " + j, 2, ts.size()); 
                for (int k = 0; k < 2; k++)
                {
                    err = s + " " + k + " " + j;
                    assertEquals(err, 'W' - k * 22, ts.get(k).getElement()); 
                    assertEquals(err,   1 + k * 11, ts.get(k).getCount()); 
                }
            }
        }
        
        for (int i = 2; i < 10; i++)
            for (int j = 2; j < 10; j++) // must both be single-digit numbers
            {
                s = i + "J4+" + j + "T14";
                for (int x : new int[] {4,4,4,4,3,3,3})
                {
                    // System.out.println(s);
                    c = Equation.parseSide(s); 
                    assertEquals(s, i + j, c.size()); 
                    for (int k = 0; k < i; k++)
                    {
                        err = s + " " + k;
                        ts = c.get(k).getTerms();
                        assertEquals(err,   1, ts.size()); 
                        assertEquals(err, 'J', ts.get(0).getElement()); 
                        assertEquals(err,   4, ts.get(0).getCount()); 
                    }
                    for (int k = i; k < i+j; k++)
                    {
                        err = s + " " + k;
                        ts = c.get(k).getTerms();
                        assertEquals(err,   1, ts.size()); 
                        assertEquals(err, 'T', ts.get(0).getElement()); 
                        assertEquals(err,  14, ts.get(0).getCount()); 
                    }
                s = s.substring(0,x) + " " + s.substring(x);
            }
        }
        
        s = "4ZG2Z3+4A12F27+4C22G9+4E32H3";
        for (int x : new int[] {6,7,24,24,23,17,17,19,9,9,9,9,9,9,9})
        {
            // System.out.println(s);
            c = Equation.parseSide(s); 
            assertEquals(s,   16, c.size()); 
            for (int n = 0; n < 4; n++)
            {
                ts = c.get(n).getTerms();
                assertEquals(s + " " + n,   3, ts.size()); 
                for (int k = 0; k < 3; k++)
                {
                    err = s + " " + n + " " + k;
                    assertEquals(err, 'Z' - 19 * (k % 2), ts.get(k).getElement()); 
                    assertEquals(err,              k + 1, ts.get(k).getCount()); 
                }
            }
            for (int n = 4; n < 16; n++)
            {
                err = s + " " + n;
                ts = c.get(n).getTerms();
                int k = n / 4;
                assertEquals(err,                        2, ts.size());
                assertEquals(err,          'A' + 2 * (k-1), ts.get(0).getElement()); 
                assertEquals(err,               2 + 10 * k, ts.get(0).getCount()); 
                assertEquals(err,                  'E' + k, ts.get(1).getElement()); 
                assertEquals(err, 81 / (int) Math.pow(3,k), ts.get(1).getCount()); 
            }
            s = s.substring(0,x) + " " + s.substring(x);
        }
    }
    
    @Test
    public void testisValid()
    {
        for (int k = 0; k < es.length; k++)
        {
            assertEquals(es[k], k <= 3 || k >= 6, new Equation(es[k]).isValid());
            String s = es[k] + 6;
            assertFalse(s, new Equation(s).isValid());
            s = "L" + es[k];
            assertFalse(s, new Equation(s).isValid());
        }
        // based on the original testing classes
        assertTrue("", new Equation("S + B = SB").isValid());
        assertTrue("", new Equation("J + B + BJ2 = J3B2").isValid());
        assertTrue("", new Equation("Y + DC2 + DC2 = YDDC + C3").isValid());
        assertTrue("", new Equation("A + BC2 + BC2 = AB2C + C3").isValid());
        
        assertFalse("", new Equation("A + B = ABB").isValid());
        assertFalse("", new Equation("A + B + BB = AB+AB").isValid());
        assertFalse("", new Equation("A + BC2 + BC2 = AB + BC + C5").isValid());
        assertFalse("", new Equation("A + BR2 + BR2 = CB2R + R3").isValid());
    }

    @Test
    public void testEquationDisplay()
    {
        for (String s : es)
            assertTrue(s, new Equation(s).display().replace(" ","").equals(s));
    }
}
