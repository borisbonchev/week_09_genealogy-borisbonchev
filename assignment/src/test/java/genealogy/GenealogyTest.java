/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class GenealogyTest {
    
    /**
     * Test with self, genealogy.Genealogy.
     */
    @Test
    public void testSelf() {
        //TODO 1 test Self
        Genealogy genealogy = new Genealogy();

        assertThat(genealogy.getAncestry(Genealogy.class.getName()))
                .contains("public genealogy.Genealogy", "public java.lang.Object", "class hierarchy of [genealogy.Genealogy]");
    }

    /**
     * Test with javax.swing.JButton. and assert that all ancestors are present. 
     */
    @Test
    public void genealogyJButton() {
        //TODO 2 test with JButton
        Genealogy genealogy = new Genealogy();

        assertThat(genealogy.getAncestry(JButton.class.getName()))
                .contains("javax.swing.JButton", "java.lang.Object", "java.awt.Component", "java.awt.Container",
                        "javax.swing.JComponent", "javax.swing.AbstractButton");
    }
    
    /**
     * Test that main does not throw any exception.
     */
    @Test
    public void testMain() {
        String[] args = { "samples.Student" };
        assertThatCode( () -> {
            Genealogy.main( args );
        } ).doesNotThrowAnyException();
    }

    /**
     * This test is for coverage of the main method. 
     * To make it a 'meaningfull' test, we ensure that application does
     * not throw an exception. 
     */
    @Test
    public void testMainExceptionsCaught() {
        String[] args = { "samples.NoStudent" };
        assertThatCode( () -> {
            Genealogy.main( args );
        } ).doesNotThrowAnyException();
    }
    
    /**
     * This test is for coverage of the main method. 
     * To make it a 'meaningfull' test, we ensure that application does
     * not throw an exception. 
     */
    @Test
    public void noArgsNoException() {
        assertThatCode( () -> {
            Genealogy.main( new String[0] );
        } ).doesNotThrowAnyException();
    }
}
