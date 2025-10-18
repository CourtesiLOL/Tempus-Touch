package org.courtesilol.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 *
 * @author Javier
 */
public class ClientUI {

    public static Image DEFAULT_IMG;
    private TimeFrame timeFrame;
    private boolean isOpen;

    public ClientUI() {
        isOpen = false;
    }

    public void run() throws URISyntaxException {
        // Verifica si el sistema soporta la bandeja del sistema
        if (!SystemTray.isSupported()) {
            System.out.println("Error: Youre system not support task panel.");
            return;
        }
    
        Image img;
        try {
            InputStream inputStream = getClass().getResourceAsStream("cloud.png");
            if (inputStream == null) {
                System.out.println("Error: Image is null");
                return;
            }
            img = ImageIO.read(inputStream);
        } catch (IOException ex) {
            return;
        }
        // Crea un icono
        DEFAULT_IMG = img;

        // Crea un menú contextual
        PopupMenu popupMenu = new PopupMenu();

        // Añade una opción para salir
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popupMenu.add(exitItem);

        // Crea el objeto de la bandeja del sistema
        TrayIcon trayIcon = new TrayIcon(DEFAULT_IMG, "Tempus Touch", popupMenu);

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                if (!isOpen) {
                    if (timeFrame == null) {
                        timeFrame = new TimeFrame(ClientUI.this);
                    }

                    isOpen = true;
                    timeFrame.setVisible(true);

                } else {
                    isOpen = false;
                    timeFrame.setVisible(false);
                }
            }
        });

        // Habilita el tooltip
        trayIcon.setImageAutoSize(true);

        // Añade el icono a la bandeja del sistema
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Error: Can't put the icon: " + e.getMessage());
        }

        // Muestra un mensaje de notificación (opcional)
        //trayIcon.displayMessage("Bienvenido", "La aplicación se está ejecutando en segundo plano", TrayIcon.MessageType.INFO);
    }

    public void setIsOpen() {
        isOpen = !isOpen;
    }
}
