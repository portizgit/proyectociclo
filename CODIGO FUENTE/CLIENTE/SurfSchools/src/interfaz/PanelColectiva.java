/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import cliente.Client;
import static interfaz.PanelExcursion.isFechaValida;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import org.json.JSONObject;
import protocolo.ClientProtocol;

/**
 *
 * @author pablo
 * En este panel representa a cada clase colectiva creada por una escuela. Es decir, cada hijo del panel donde se muestran las clases colectivas
 * creadas por la escuela en la clase ClasesColectivas
 */
public class PanelColectiva extends JPanel {

    /**
     * Create the panel.
     */
    String nombre;
    String descripcion;
    String nivel;
    String precio;
    int id;

    public PanelColectiva(String descripcion, String nivel, String precio, int id) {

        this.nombre = "CLASE COLECTIVA";
        this.descripcion = descripcion;
        this.precio = precio;
        this.nivel = nivel;
        this.id = id;

        setBorder(new LineBorder(new Color(173, 255, 47), 3, true));
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(2, 2, 1, 2));
        setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);

        JLabel lblFecha = new JLabel("Precio: ");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(lblFecha);

        JLabel lbUsuario = new JLabel(precio);
        panel.add(lbUsuario);

        JLabel label = new JLabel("Nivel: ");
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(label);

        JLabel lbTlfn = new JLabel(nivel);
        panel.add(lbTlfn);

        JLabel lblAdsfsdf = new JLabel(nombre);

        lblAdsfsdf.setFont(new Font("Verdana", Font.BOLD, 14));
        add(lblAdsfsdf, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.WEST);
        panel_1.setLayout(new GridBagLayout());
        panel_1.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel lblPrecio = new JLabel("Descripción:");
        lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0;

        panel_1.add(lblPrecio, constraints);
       
        JTextArea label_1 = new JTextArea(descripcion, 4, 9);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = 0;
        label_1.setLineWrap(true);
        label_1.setEditable(false);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(label_1);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel_1.add(scroll, constraints);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.EAST);
        panel_2.setLayout(new GridLayout(2, 1, -40, 0));
        panel_2.setBackground(Color.WHITE);

        JButton btnAprobar = new JButton("Programar");

        btnAprobar.setBackground(new Color(50, 205, 50));
        panel_2.add(btnAprobar);
        btnAprobar.addActionListener(new ActionListener() {
            /**
             * Si pulsamos en el botón de programar se nos pedirá una fecha y un numero máximo de participantes que se nos validará, y si es correcto
             * mandará una petición al sevidor para crearla.
             * @param e 
             */
            public void actionPerformed(ActionEvent e) {
                boolean correcto = true;
                Date fechaprg;
                String participantes="";
                String fecha = JOptionPane.showInputDialog("Introduzca fecha en la que se realizará (DD-MM-AAAA)");
                if (!isFechaValida(fecha)) {
                    correcto = false;
                    JOptionPane.showMessageDialog(null, "El formato de fecha no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    try {
                        fechaprg = formatoFecha.parse(fecha);
                        String factual = formatoFecha.format(new Date());
                        Date actual = formatoFecha.parse(factual);
                        
                        /**
                        SimpleDateFormat formato2=new SimpleDateFormat("yyyyMMdd");
                        System.out.println(formato2.format(fechaprg));
                        */
                        if (fechaprg.before(actual)) {
                            JOptionPane.showMessageDialog(null, "La fecha es anterior a la actual", "Error", JOptionPane.ERROR_MESSAGE);
                             correcto=false;
                        }else{
                             participantes = JOptionPane.showInputDialog("Introduzca número máximo de participantes: ");
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(PanelExcursion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   

                    if (participantes.equals("")) {
                        correcto = false;
                        JOptionPane.showMessageDialog(null, "Deben introducir un número", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

                if (correcto) {
                    ClientProtocol cpl = Client.getClientProtocol();
                    JSONObject respuesta = cpl.programarActividad(id, fecha, participantes);

                    JOptionPane.showMessageDialog(null, "Excursion programada", "Programada", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.setBackground(new Color(255, 0, 0));
        panel_2.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            /**
             * Si se pulsa en cancelar, se cancela la actividad y por lo tanto también todas sus programadas
             * @param e 
             */
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Solicitud cancelada", "Cancelada", JOptionPane.INFORMATION_MESSAGE);
                btnCancelar.setText("CANCELADA");
                btnCancelar.setEnabled(false);
                ClientProtocol cpl = Client.getClientProtocol();
                JSONObject respuesta=cpl.cancelarActividad(id);
            }
        });

    }
   
}
