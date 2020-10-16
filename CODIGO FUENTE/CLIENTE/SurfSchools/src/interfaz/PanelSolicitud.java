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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import org.json.JSONObject;
import protocolo.ClientProtocol;

public class PanelSolicitud extends JPanel {

	/**
	 * Por cada solicitud de alquiler se crea una instancia de esta clase.
	 */
    String nombre;
    String telefono;
    String cantidad;
    String fecha;
    String hora;
    String factura;
    String usuario;
    String alquiler;
    int idalquiler,aprobado,cancelado;
	public PanelSolicitud(String nombre, String telefono, String cantidad, String fecha, String hora, String factura, String usuario, int idalquiler,int aprobado, int cancelado, String alquiler) {
                this.nombre=nombre;
                this.telefono=telefono;
                this.cantidad=cantidad;
                this.fecha=fecha;
                this.hora=hora;
                this.factura=factura;
                this.usuario=usuario;
                this.idalquiler=idalquiler;
                this.aprobado=aprobado;
                this.cancelado=cancelado;
                this.alquiler=alquiler;
                
                
                
		setBorder(new LineBorder(new Color(173, 255, 47), 3, true));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(3, 2, 3, 4));
                setBackground(Color.WHITE);
                panel.setBackground(Color.WHITE);
		
		JLabel lblFecha = new JLabel("Usuario:");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblFecha);
		
		JLabel lbUsuario= new JLabel(nombre);
		panel.add(lbUsuario);
		
		JLabel label = new JLabel("Telefono:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(label);
		
		JLabel lbTlfn = new JLabel(telefono);
		panel.add(lbTlfn);
		
                if(!hora.equals("hora")){
                    fecha=fecha+" "+hora;
                }
		JLabel labelfech = new JLabel("Fecha:");
		labelfech.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(labelfech);
		
		
		JLabel lbFecha = new JLabel(fecha);
		panel.add(lbFecha);
		
		
		JLabel lblAdsfsdf = new JLabel(cantidad);
                if(alquiler.equals("tabla surf")){
                    lblAdsfsdf.setText(cantidad+" TABLAS");
                }else{
                     lblAdsfsdf.setText(cantidad+" KAYAKS");
                }
                if(hora.equals("clases")){
                    lblAdsfsdf.setText("CLASE INDIVIDUAL");
                }
		lblAdsfsdf.setFont(new Font("Verdana", Font.BOLD, 14));
		add(lblAdsfsdf, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 1, 0, 0));
                panel_1.setBackground(Color.WHITE);
		

		JLabel lblPrecio = new JLabel("     Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(lblPrecio);
		
		JLabel label_1 = new JLabel(factura+" €");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new GridLayout(2, 1, -40, 0));
                panel_2.setBackground(Color.WHITE);
		

		
		JButton btnAprobar = new JButton("Aprobar");
                
		btnAprobar.setBackground(new Color(50, 205, 50));
		panel_2.add(btnAprobar);
                btnAprobar.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      /**
                       * Al aprobar, se manda una petición al servidor y ya se mostraría solo en el apartado de aprobadas
                       */
                       JOptionPane.showMessageDialog(null, "Solicitud aprobada", "Aprobada", JOptionPane.INFORMATION_MESSAGE);
                       btnAprobar.setText("APROBADO");
                       btnAprobar.setEnabled(false);
                       ClientProtocol cpl=Client.getClientProtocol();
                       JSONObject respuesta=cpl.setAprobado(idalquiler);
                 }
                 });
            JButton btnCancelar = new JButton("Cancelar");
            if(aprobado==1){
                    btnAprobar.setEnabled(false);
                    btnCancelar.setText("Suspender");
                }
            btnCancelar.setBackground(new Color(255, 0, 0));
		panel_2.add(btnCancelar);
                
                 btnCancelar.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                      /**
                       * Si se cancela se manda una petición al servidor y se elimina del apartado de solicitudes y aprobadas.
                       */
                       JOptionPane.showMessageDialog(null, "Solicitud cancelada", "Cancelada", JOptionPane.INFORMATION_MESSAGE);
                       btnCancelar.setText("SUSPENDIDO");
                       btnCancelar.setEnabled(false);
                       ClientProtocol cpl=Client.getClientProtocol();
                       JSONObject respuesta=cpl.setCancelado(idalquiler);
                 }
                 });
               

	}
        
        

}
