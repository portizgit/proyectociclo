/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import cliente.Client;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import protocolo.ClientProtocol;

/**
 *
 * @author pablo
 */
/**
 * Esta clase maneja las Clases Individuales de la escuela, el funcionamiento es muy parecido al de los alquileres, cuando llega una petición
 * se muestra en el panel y se puede aprobar o suspender. Si se aprueba se mostrará también en el apartado de aprobadas.
 * @author pablo
 */
public class ClasesIndividuales extends javax.swing.JPanel {
    String precio,usuarioescuela;
    ClientProtocol cpl;
    String lista="aprob";
    boolean activo=false;
    String alquiler="clases";
    /**
     * Creates new form ClasesColectivas
     */
    public ClasesIndividuales(String usuarioescuela) {
        this.usuarioescuela=usuarioescuela;
        cpl=Client.getClientProtocol();
        initComponents();
        colocaValores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxPrecio = new javax.swing.JTextField();
        bEditaPrecio = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelSolicitudes = new javax.swing.JPanel();
        bGuardar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        bAtras = new javax.swing.JButton();
        jlNotif = new javax.swing.JLabel();
        labelLista = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-login.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("CLASES INDIVIDUALES");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Precio:");

        bEditaPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pencil.png"))); // NOI18N
        bEditaPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditaPrecioActionPerformed(evt);
            }
        });

        jLabel4.setText("€/hora");

        javax.swing.GroupLayout panelSolicitudesLayout = new javax.swing.GroupLayout(panelSolicitudes);
        panelSolicitudes.setLayout(panelSolicitudesLayout);
        panelSolicitudesLayout.setHorizontalGroup(
            panelSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );
        panelSolicitudesLayout.setVerticalGroup(
            panelSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelSolicitudes);

        bGuardar.setText("Guardar");
        bGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGuardarActionPerformed(evt);
            }
        });

        bAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/atras.png"))); // NOI18N
        bAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAtrasActionPerformed(evt);
            }
        });

        jlNotif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notification.png"))); // NOI18N
        jlNotif.setEnabled(false);

        labelLista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelLista.setText("APROBADAS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(86, 86, 86)
                .addComponent(jLabel2)
                .addGap(0, 189, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bEditaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(bGuardar))
                .addGap(63, 63, 63)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bAtras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlNotif)
                .addGap(75, 75, 75))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelLista)
                .addGap(289, 289, 289))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bAtras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(labelLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bEditaPrecio)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jtxPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)))
                                .addGap(34, 34, 34)
                                .addComponent(bGuardar))))
                    .addComponent(jlNotif))
                .addGap(0, 21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
/**
     * Al pulsar en el botón para editar el precio de la clase las cuales son siempre de 1h, se pide un nuevo precio y se guarda en la variable global.
     * Se activa el botón de guardar.
     * @param evt 
     */
    private void bEditaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditaPrecioActionPerformed
           String sprecio = JOptionPane.showInputDialog("Introduzca nuevo precio de la hora de clase: ");
        if (sprecio.equals("")) {
            JOptionPane.showMessageDialog(this, "No puede dejar el campo vacío", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            jtxPrecio.setText(sprecio);
            precio=sprecio;
            bGuardar.setEnabled(true);
        }
                             
    }//GEN-LAST:event_bEditaPrecioActionPerformed
    /**
     * Este método es el que se encarga de mostrar las solicitudes aprobadas de clases en el panel de la derecha.
     * Se obtienen las solicitudes y se crea un objeto Panel que será el que se va a mostrar.
     */
    private void initAprobadas(){
       //CODIGO PRUEBA
       
             
               
        //
         panelSolicitudes.setLayout(new BoxLayout(panelSolicitudes, BoxLayout.Y_AXIS));
          try {
     
         String nombre,telefono,cantidad,fecha,hora,factura,usuario,notas;
         int icantidad,idalquiler,aprobado,cancelado,realizado;
         JSONObject respuesta = cpl.getAlquileres(usuarioescuela, "clases");
            JSONArray array = respuesta.getJSONArray("alquileres");
           int solicitudes=0;
        for (int i = 0; i < array.length(); i++) {
           
                 JSONObject row = array.getJSONObject(i);
                 nombre=row.getString("nombre");
                 telefono=row.getString("telefono");
                 icantidad=row.getInt("cantidad");
                 cantidad=Integer.toString(icantidad);
                 fecha=row.getString("fecha");
                 hora="clases";
                 factura=row.getString("factura");
                 usuario=row.getString("usuario");
                 idalquiler=row.getInt("idalquiler");
                 aprobado=row.getInt("aprobado");
                 cancelado=row.getInt("cancelado");
                 notas=row.getString("notas");
                 realizado=row.getInt("realizado");
                 if(aprobado!=1&&cancelado!=1){
                     solicitudes++;
                 }else if(cancelado!=1&&realizado!=1){
                 PanelSolicitudIndv newPanel = new PanelSolicitudIndv(nombre,telefono,cantidad,fecha,hora,factura,usuario,idalquiler,aprobado,cancelado,alquiler,notas);
                panelSolicitudes.add(newPanel);
                 }
                 
                 if(solicitudes>0){
                     jlNotif.setEnabled(true);
                     activo=true;
                 }else{
                     jlNotif.setEnabled(false);
                     activo=false;
                 }
              
               panelSolicitudes.revalidate();
                 
            
        }
      setBackground(new java.awt.Color(204, 255, 255));
      
         
        
        panelSolicitudes.setVisible(true);
         } catch (JSONException ex) {
                 Logger.getLogger(AlquilerSurf.class.getName()).log(Level.SEVERE, null, ex);
             }

    }
    /**
     * Este método es el que se encarga de mostrar las solicitudes de alquiler en el panel de la derecha.
     * Se obtienen las solicitudes y se crea un objeto Panel que será el que se va a mostrar.
     */
    private void initSolicitudes(){
         panelSolicitudes.setLayout(new BoxLayout(panelSolicitudes, BoxLayout.Y_AXIS));
          try {
     
         String nombre,telefono,cantidad,fecha,hora,factura,usuario,notas;
         int icantidad,idalquiler,aprobado,cancelado;
         JSONObject respuesta = cpl.getAlquileres(usuarioescuela, "clases");
            JSONArray array = respuesta.getJSONArray("alquileres");
           int solicitudes=0;
        for (int i = 0; i < array.length(); i++) {
           
                 JSONObject row = array.getJSONObject(i);
                 nombre=row.getString("nombre");
                 telefono=row.getString("telefono");
                 icantidad=row.getInt("cantidad");
                 cantidad=Integer.toString(icantidad);
                 fecha=row.getString("fecha");
                 hora="clases";
                 factura=row.getString("factura");
                 usuario=row.getString("usuario");
                 idalquiler=row.getInt("idalquiler");
                 aprobado=row.getInt("aprobado");
                 cancelado=row.getInt("cancelado");
                 notas=row.getString("notas");
                 if(aprobado!=1 && cancelado!=1){
                     solicitudes++;
                 PanelSolicitudIndv newPanel = new PanelSolicitudIndv(nombre,telefono,cantidad,fecha,hora,factura,usuario,idalquiler,aprobado,cancelado,alquiler,notas);
                panelSolicitudes.add(newPanel);
                 }
            
        }
       
        panelSolicitudes.setVisible(true);
         } catch (JSONException ex) {
                 Logger.getLogger(AlquilerSurf.class.getName()).log(Level.SEVERE, null, ex);
             }
      
    }
    /*
     * Obtiene los precios definidos por la escuela y los coloca en las cajas de texto correspondiente. Y determina el funcionamiento al pulsar el label que dnd se mostrará si 
     * tenemos o no una nueva solicitud
     */
    public void colocaValores(){
        try{
          JSONObject respuesta=cpl.getPrecios(usuarioescuela);
            if(respuesta.getString("resultado").equals("incorrecto")){
                JOptionPane.showMessageDialog(this, "Error al recuperar datos", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                precio=respuesta.getString("clases");
                if(!precio.equals("0")){
                  
                    jtxPrecio.setText(precio);
                    jtxPrecio.setEditable(false);
                }else{
                    jtxPrecio.setText("No definido");
                    jtxPrecio.setEditable(false);
                }
              
                
               initAprobadas();
               
                jlNotif.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if(lista.equals("aprob")){
                        if(activo){
                        panelSolicitudes.removeAll();
                        initSolicitudes();
                        labelLista.setText("SOLICITUDES");
                        lista="solic";
                        jlNotif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/list.png")));
                        }
                    }else{
                        panelSolicitudes.removeAll();
                        initAprobadas();
                        labelLista.setText("APROBADAS");
                        lista="aprob";
                        jlNotif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notification.png")));
                    }
                }
            }); 
                
            }} catch (JSONException ex) {
            Logger.getLogger(ClasesIndividuales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     /**
 * Al pulsar en el botón guardar se actualizan los precios de esa escuela
 * @param evt 
 */
    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
          try {
            JSONObject respuesta=cpl.setPrecios(usuarioescuela,precio,"0","clases");
            if(respuesta.getString("resultado").equals("correcto")){
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
            } else{
                 JOptionPane.showMessageDialog(this, "Error al actualizar", "ERROR", JOptionPane.ERROR_MESSAGE);
             } 
        } catch (JSONException ex) {
            Logger.getLogger(ClasesIndividuales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bGuardarActionPerformed
  /**
     * Cuando se pulsa el botón de atrás se vuelve a la pantalla principal
     * @param evt 
     */
    private void bAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtrasActionPerformed
         PrincipalFrame f = (PrincipalFrame) SwingUtilities.windowForComponent(bAtras);
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        f.setBounds(ss.width / 2 - Principal.width / 2,
                ss.height / 2 - Principal.height / 2,
                Principal.width, Principal.height);
        Principal pp = new Principal(usuarioescuela);
        f.setContentPane(pp);
        f.pack();
        f.setSize(958, 725);
        f.setVisible(true);        
    }//GEN-LAST:event_bAtrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAtras;
    private javax.swing.JButton bEditaPrecio;
    private javax.swing.JButton bGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlNotif;
    private javax.swing.JTextField jtxPrecio;
    private javax.swing.JLabel labelLista;
    private javax.swing.JPanel panelSolicitudes;
    // End of variables declaration//GEN-END:variables
}
