/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author pablo
 */
public  class CellPanel extends JPanel {
    /**
     * Este panel se muestran los participantes de cada excursión. Solo se muestra desde los paneles de las excursiones programadas.
     */
      private final int gap = 4;
      private int index;
      private String name;
      private String date;
      private final String format = "MM/dd/yyyy";
      private SimpleDateFormat sdf = new SimpleDateFormat(format);

      public CellPanel(String name, String telefono) {
         this.name = name;
         date = telefono;
         
      setBackground(new java.awt.Color(204, 255, 255));
         Border emptyBorder = BorderFactory.createEmptyBorder(gap, gap, gap, gap);
         Border lineBorder = BorderFactory.createLineBorder(Color.black);
         setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

         add(new JLabel(name), createGbc(0, 0));
         add(new JLabel(""), createGbc(1, 0));
         add(new JLabel(date), createGbc(0, 1));
      }

      private GridBagConstraints createGbc(int x, int y) {
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.gridx = x;
         gbc.gridy = y;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.weightx = 1.0;
         gbc.weighty = 1.0;

         if (x % 2 == 0) {
            gbc.anchor = GridBagConstraints.WEST;
         } else {
            gbc.anchor = GridBagConstraints.EAST;
         }
         return gbc;
      }
      
}