/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzwbinfajunit;

import static junit.framework.TestCase.assertEquals;
import org.junit.Test;


/**
 *
 * @author fupn26
 */
public class LZWBinFaTest {
    LZWBinFa binfa = new LZWBinFa();
    
    /**
     * Test of hozzarendel method, of class LZWBinFa.
     */
    @Test
    public void testHozzarendel() {
        char[] minta = "01111001001001000111".toCharArray();
        
        for(char betu: minta ){
            binfa.hozzarendel(betu);
        }
        
        assertEquals(4, binfa.getMelyseg());
        assertEquals(2.75, binfa.getAtlag(), 0.01);
        assertEquals(0.957427, binfa.getSzoras(), 0.000001);
    }    
    
}
