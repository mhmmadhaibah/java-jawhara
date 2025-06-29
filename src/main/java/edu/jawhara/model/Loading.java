/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jawhara.model;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author mhmmadhaibah
 */
public class Loading
{
    public static void infiniteLoading(JPanel jPanel, String cardName)
    {
        CardLayout cardLayout = (CardLayout) jPanel.getLayout();
        
        cardLayout.show(jPanel, "infinitePanel");
        Timer timer = new Timer(0, evt -> {
            cardLayout.show(jPanel, cardName);
        });
        
        timer.setRepeats(false);
        timer.start();
    }
}
