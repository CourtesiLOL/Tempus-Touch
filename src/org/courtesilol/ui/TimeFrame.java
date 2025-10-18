package org.courtesilol.ui;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpResponse.BodyHandlers;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import org.courtesilol.City;
import org.courtesilol.DeserialiceTime;
import org.courtesilol.WeatherApiFactory;
import org.courtesilol.dto.SampleTime;
import org.xml.sax.SAXException;

/**
 *
 * @author Javier
 */
public class TimeFrame extends JFrame {

    //UI Components
    private JPanel panel;
    private JLabel name;
    private JLabel timeDescription;
    private JLabel imageTime;
    private JLabel windKPH;
    private JLabel temperature;
    private JLabel temperatureFeels;
    private JLabel uv;
    private JComboBox<City> comboBox;
    private City lastCity;

    //net
    private HttpClient client;
    private HttpRequest request;

    //Data managment
    private DeserialiceTime dsTime;

    public TimeFrame(ClientUI clientUi) {

        configureFrame();
        configureComponents();
        addComponents();
        addEvents(clientUi);
        requestTime();
        
    }

    private void configureFrame() {
        setPreferredSize(new Dimension(600, 400));
        setResizable(false);
        setSize(getPreferredSize());

        setIconImage(ClientUI.DEFAULT_IMG);
        setLocationRelativeTo(null);
    }

    private void configureComponents() {
        name = new JLabel("unknown name");
        
        City[] citys = City.values();
        lastCity = citys[0];
        comboBox = new JComboBox(citys);
        
        timeDescription = new JLabel("unknown description");
        imageTime = new JLabel("Unknown image");
        windKPH = new JLabel("unknown wind");
        temperature = new JLabel("unknown temperature");
        temperatureFeels = new JLabel("unknown temperature feels");
        uv = new JLabel("unknown uv");
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(getPreferredSize());
    }

    private void addComponents() {
        panel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();

        // --- LEFT COLUMN: image ---
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 6;            // spans all rows on the right
        c.weightx = 0.4;             // wider column so the image looks better
        c.weighty = 1.0;             // takes up all available vertical space
        c.fill = GridBagConstraints.BOTH; // image can expand
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 15); // margin around the image

        // Scale the image to approximate panel size
        ImageIcon icon = new ImageIcon(ClientUI.DEFAULT_IMG);
        Image scaledImg = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        imageTime.setIcon(new ImageIcon(scaledImg));
        imageTime.setText(null);
        panel.add(imageTime, c);

        // --- RIGHT COLUMN: information ---
        c.gridheight = 1;
        c.gridx = 1;
        c.weightx = 0.6;             // wider column for text
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST; // aligned top-left
        c.insets = new Insets(5, 0, 5, 10); // margin between texts

        // Selector
        c.gridy = 0;
        c.weighty = 0.15;  
        panel.add(comboBox, c);
        
        // Name
        c.gridy = 1;
        c.weighty = 0.15;           // distribute vertical space between labels
        panel.add(name, c);

        // Description
        c.gridy = 2;
        panel.add(timeDescription, c);

        // Wind
        c.gridy = 3;
        panel.add(windKPH, c);

        // Temperature
        c.gridy = 4;
        panel.add(temperature, c);

        // Feels like temperature
        c.gridy = 5;
        panel.add(temperatureFeels, c);

        // UV
        c.gridy = 6;
        panel.add(uv, c);

        // Add the panel to the JFrame
        add(panel);
    }

    private void addEvents(ClientUI client) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.setIsOpen();
            }
        });
        
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                City newCity = (City) e.getItem();
                
                if (newCity != lastCity) {
                    lastCity = newCity;
                    requestTime();
                }
                
            }
        });
    }

    private void requestTime() {
        comboBox.setEnabled(false);
        if (client == null)
            client = HttpClient.newHttpClient();
        
        request = HttpRequest.newBuilder()
                .uri(URI.create(WeatherApiFactory.sampleDataCity((City) comboBox.getSelectedItem())))
                .GET()
                .build();

        var future = client.sendAsync(request, BodyHandlers.ofInputStream());
        future.thenAccept(response -> {
            try {
                if (dsTime == null) {
                    dsTime = new DeserialiceTime();
                }
                setData(dsTime.sampleDataDeserialice(response.body()));
                
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });
    }

    private void setData(SampleTime data) {
        
        name.setText("City: "+data.name());
        timeDescription.setText("Description: "+data.condition().text());
        windKPH.setText("Wind: "+data.wind_kph()+"kp/h");
        temperature.setText("Temp: "+data.temp_c()+"ºC");
        temperatureFeels.setText("Temp feels: "+data.feelslike_c()+"ºC");
        uv.setText("uv: "+data.uv());
        
        comboBox.setEnabled(true);
        
        var iconUri = URI.create(data.condition().icon());
        request = HttpRequest.newBuilder()
                .uri(iconUri)
                .GET()
                .build();
        

        var future = client.sendAsync(request, BodyHandlers.ofByteArray());
        future.thenAccept(response -> {
            ImageIcon icon = new ImageIcon(response.body());
            Image scaledImg = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            imageTime.setIcon(new ImageIcon(scaledImg));
        });
                
        
    }
}
