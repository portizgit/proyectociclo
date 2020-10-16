/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import cliente.Client;
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
import org.json.JSONException;
import org.json.JSONObject;
import protocolo.ClientProtocol;


public class PanelNotificacion extends JPanel {

    /**
     * Por cada notificación mostrada en la clase Notificaciones, se crea una instancia de esta clase.
     */
    String nombre;
    String descripcion;
    String viento;
    String lluvia;
    String vientoactual;
    String lluviaactual;
    String fecha;
    int id;

    public PanelNotificacion(String nombre, String descripcion, String viento, String lluvia,String vientoactual, String lluviaactual,String fecha, int id) {
        this.vientoactual=vientoactual;
        this.lluviaactual=lluviaactual;
        this.nombre = nombre;
        this.fecha=fecha;
        this.descripcion = descripcion;
        this.viento = viento;
        this.lluvia = lluvia;
        this.id = id;
    

        setBorder(new LineBorder(new Color(173, 255, 47), 3, true));
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(4, 2, 1, 2));
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
        
        
        ///////////
                
        JLabel plluvia = new JLabel("Lluvia pronosticada: ");
        plluvia.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(plluvia);

        

        JLabel llactual = new JLabel(lluviaactual+ " mm");
        panel.add(llactual);

        JLabel lviento = new JLabel("Viento pronosticado:");
        lviento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(lviento);

        JLabel lbViento = new JLabel(vientoactual + " km/h");
        panel.add(lbViento);

        JLabel lblAdsfsdf = new JLabel(nombre+" - "+fecha);

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
        /**
         *
         *
         * JLabel label_1 = new JLabel(factura+" €"); label_1.setFont(new
         * Font("Tahoma", Font.PLAIN, 16)); panel_1.add(label_1);
         *
         */

        //
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

        JButton btnAprobar = new JButton("Mensaje");

        btnAprobar.setBackground(new Color(50, 205, 50));
        panel_2.add(btnAprobar);
        btnAprobar.addActionListener(new ActionListener() {
            /**
             * Pulsando en mensaje, podemos enviar un mensaje a cada participante de la actividad de la que sea la notificación.
             * @param e 
             */
            public void actionPerformed(ActionEvent e) {
                boolean correcto=true;
                String msn = JOptionPane.showInputDialog("Introduzca mensaje a los participantes (OBLIGATORIO)");
                if(msn.equals("")){
                    JOptionPane.showMessageDialog(null, "Debe introducir un mensaje", "ERROR", JOptionPane.ERROR_MESSAGE);
                    correcto=false;
                }
                
                String ast= JOptionPane.showInputDialog("Introduzca asunto del mensaje (OBLIGATORIO)");
                if(ast.equals("")){
                    JOptionPane.showMessageDialog(null, "Debe introducir un asunto", "ERROR", JOptionPane.ERROR_MESSAGE);
                    correcto=false;
                }
                if(correcto){
                    ClientProtocol cpl = Client.getClientProtocol();
                    JSONObject respuesta=cpl.enviarMsn(Client.nombreescuela,id,msn,ast);
                    try {
                        if(respuesta.getString("resultado").equals("correcto")){
                            JOptionPane.showMessageDialog(null, "Mensaje enviado", "Enviado", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(PanelExcProgramada.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
                }
            }
        });
        JButton btnCancelar = new JButton("Suspender");

        btnCancelar.setBackground(new Color(255, 0, 0));
        panel_2.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            /**
             * Al pulsar suspender, se pide un mensaje y asunto para justificar la suspensión que se enviará a cada participante y se
             * suspende la actividad.
             * @param e 
             */
            public void actionPerformed(ActionEvent e) {
                boolean correcto=true;
                String msn = JOptionPane.showInputDialog("Introduzca mensaje para justificar la suspensión (OBLIGATORIO)");
                if(msn.equals("")){
                    JOptionPane.showMessageDialog(null, "Debe introducir un mensaje", "ERROR", JOptionPane.ERROR_MESSAGE);
                    correcto=false;
                }
                
                String ast= JOptionPane.showInputDialog("Introduzca asunto del mensaje (OBLIGATORIO)");
                if(ast.equals("")){
                    JOptionPane.showMessageDialog(null, "Debe introducir un asunto", "ERROR", JOptionPane.ERROR_MESSAGE);
                    correcto=false;
                }
                if(correcto){
                    ClientProtocol cpl = Client.getClientProtocol();
                    JSONObject respuesta=cpl.suspenderActividad(Client.nombreescuela,id,msn,ast);
                    try {
                        if(respuesta.getString("resultado").equals("correcto")){
                            JOptionPane.showMessageDialog(null, "Solicitud cancelada", "Cancelada", JOptionPane.INFORMATION_MESSAGE);
                            btnCancelar.setText("SUSPENDIDO");
                            btnCancelar.setEnabled(false);
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(PanelExcProgramada.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
                }
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
    /**
     *
     * public static void main(String[]args){ JFrame f = new JFrame();
     *
     * PanelExcursion ep = new PanelExcursion("nombre","descripcion descripcion
     * sgdfgsdfgsdfg","1000","1"); f.setContentPane(ep); f.setVisible(true); }
     *
     */
}
