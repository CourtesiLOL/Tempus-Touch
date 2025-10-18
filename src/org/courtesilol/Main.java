package org.courtesilol;

import java.lang.reflect.InvocationTargetException;

import java.net.URISyntaxException;
import javax.swing.SwingUtilities;
import org.courtesilol.ui.ClientUI;

/**
 *
 * @author Javier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            try {
                new ClientUI().run();
            } catch (URISyntaxException ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        });
    }
}
