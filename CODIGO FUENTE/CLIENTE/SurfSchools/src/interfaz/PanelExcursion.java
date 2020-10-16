/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;
import cliente.Client;
/**
 *
 * @author Pablo
 */import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.json.JSONObject;
import protocolo.ClientProtocol;

public class PanelExcursion extends JPanel {

    /**
     * Se crea una instancia de esta clase por cada excursión creada por la escuela, las cuáles se muestran en la clase ExcursionPrincipal
     */
    String nombre;
    String descripcion;
    String viento;
    String lluvia;
    int id;

    public PanelExcursion(String nombre, String descripcion, String viento, String lluvia, int id) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.viento = viento;
        this.lluvia = lluvia;
        this.id = id;
       

        setBorder(new LineBorder(new Color(173, 255, 47), 3, true));
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(2, 2, 1, 2));
        setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);

        JLabel lblFecha = new JLabel("Lluvia permitida: ");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(lblFecha);

        String slluvia;
        if (lluvia.equals("0")) {
            slluvia = "no";
        } else {
            slluvia = "si";
        }

        JLabel lbUsuario = new JLabel(slluvia);
        panel.add(lbUsuario);

        JLabel label = new JLabel("Viento max:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(label);

        JLabel lbTlfn = new JLabel(viento + " km/h");
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
        
        JTextArea label_1 = new JTextArea(descripcion, 2, 8);
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
            public void actionPerformed(ActionEvent e) {
                /**
                 * Si se pulsa en programar se pide una fecha y numero máximo de participantes
                 */
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
        /**
         * Si cancelamos la actividad se suspende esa actividad y todas la programadas de esa.
         */
        btnCancelar.setBackground(new Color(255, 0, 0));
        panel_2.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Solicitud cancelada", "Cancelada", JOptionPane.INFORMATION_MESSAGE);
                btnCancelar.setText("CANCELADA");
                btnAprobar.setEnabled(false);
                btnCancelar.setEnabled(false);
                ClientProtocol cpl = Client.getClientProtocol();
                JSONObject respuesta = cpl.cancelarActividad(id);
            }
        });

    }

    public static boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
