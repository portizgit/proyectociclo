/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;
import cliente.Client;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import protocolo.ClientProtocol;
/**
 * Este panel representa a cada excursión programada, y clase colectiva programada de la escuela.
 * Cada elemento que devuelve el servidor de una excursión programada o de una clase colectiva programada se crea una instancia de esta clase.
 * @author pablo
 */
public class PanelExcProgramada extends JPanel {

    /**
     * Create the panel.
     */
    String nombre;
    String fecha;
    String participantes;
    ClientProtocol cpl;
    int id;

    public PanelExcProgramada(String nombre, String fecha, String participantes, int id,int pactual) {
        cpl = Client.ptl;
        this.nombre = nombre;
        this.fecha = fecha;
        this.participantes = participantes;
        this.id = id;

        setBorder(new LineBorder(new Color(173, 255, 47), 3, true));
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(2, 2, 1, 2));
        setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);

        JLabel lblFecha = new JLabel("Participantes max: ");
        lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(lblFecha);

        JLabel lbUsuario = new JLabel(participantes);
        panel.add(lbUsuario);

        JLabel label = new JLabel("Participantes actual:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel.add(label);

        JLabel lbTlfn = new JLabel(pactual+"");
        panel.add(lbTlfn);

        JLabel lblAdsfsdf = new JLabel(nombre);

        lblAdsfsdf.setFont(new Font("Verdana", Font.BOLD, 14));
        add(lblAdsfsdf, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.WEST);
        panel_1.setLayout(new GridLayout(1, 2, 0, 0));
        panel_1.setBackground(Color.WHITE);

        JLabel lblPrecio = new JLabel("Fecha");
        lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel_1.add(lblPrecio);

        JLabel label_1 = new JLabel(fecha);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

        panel_1.add(label_1);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.EAST);
        panel_2.setLayout(new GridLayout(3, 1, -40, 0));
        panel_2.setBackground(Color.WHITE);

        JButton btnAprobar = new JButton("Ver participantes");

        btnAprobar.setBackground(new Color(50, 205, 50));
        panel_2.add(btnAprobar);
        btnAprobar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * Si se pulsa el botón para ver participantes se obtienen los participantes mediante una petición al array, y se crea un
                 * array con un panel por cada celda, cada celda contiene el nombre y el teléfono del participante.
                 */
                try {
                    JPanel outerWrapperPanel = new JPanel(new BorderLayout());
                    JPanel listContainer = new JPanel();
                    listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
                    outerWrapperPanel.add(listContainer, BorderLayout.PAGE_START);
                    JFrame frame = new JFrame("JScrollPanels2");
                    frame.setLayout(new BorderLayout());
                    frame.add(new JScrollPane(outerWrapperPanel), BorderLayout.CENTER);

                    JSONObject respuesta = cpl.getParticipantes(id);
                    JSONArray array = respuesta.getJSONArray("participantes");
                    int solicitudes = 0;
                    String nombre, telefono;
                    if (array.length() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay participantes", "PARTICIPANTES", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject row = array.getJSONObject(i);
                            nombre = row.getString("nombre");
                            telefono = row.getString("telefono");

                            listContainer.add(new CellPanel(nombre, telefono));
                            listContainer.revalidate();
                            listContainer.repaint();

                        }
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(PanelExcProgramada.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton btnCancelar = new JButton("Suspender");

        btnCancelar.setBackground(new Color(255, 0, 0));
        panel_2.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * Si pulsamos suspender se nos pide un mensaje y asunto que se mandará a cada participante de la actividad y se suspende la actividad
                 * 
                 */
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
        
          JButton btnMensaje = new JButton("Mensaje");

        panel_2.add(btnMensaje);
        btnMensaje.addActionListener(new ActionListener() {
            /**
             * Pulsando mensaje, le mandamos a cada participante un mensaje.
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

    }
  
}
