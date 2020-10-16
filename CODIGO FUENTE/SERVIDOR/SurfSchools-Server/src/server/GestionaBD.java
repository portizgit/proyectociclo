/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author pablo
 */
public class GestionaBD {

    public Connection connection;
    String driver = "com.mysql.jdbc.Driver";
    String user = "surfschool";
    String password = "surfschool";
    String url = "jdbc:mysql://localhost/surfschools";

    /**
     *
     * En esta clase se encuentran todos los metodos que se utilizan directamente contra la BD
     *
     */
    public GestionaBD() {
        conectaBD();
    }

    public void conectaBD() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            Class.forName(driver);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject registroescuela(
            String nombre, String gerente, String latitud, String longitud, String direccion, String descripcion, String usuario, String contrasena, boolean colectivas, boolean individuales, boolean excursiones, boolean alquilerkayak, boolean alquilersurf) throws JSONException {
        int vcolec, vindv, vexc, vakayak, vasurf;
        System.out.println(colectivas + " " + individuales + " " + excursiones + " " + alquilerkayak + " " + alquilersurf);
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            String query = "SELECT * FROM escuela WHERE usuarioescuela = ? OR nombre=? OR direccion=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, direccion);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "yaexiste");
                return respuesta;
            }

            if (colectivas) {
                vcolec = 1;
            } else {
                vcolec = 0;
            }

            if (individuales) {
                vindv = 1;
            } else {
                vindv = 0;
            }

            if (excursiones) {
                vexc = 1;
            } else {
                vexc = 0;
            }

            if (alquilerkayak) {
                vakayak = 1;
            } else {
                vakayak = 0;
            }

            if (alquilersurf) {
                vasurf = 1;
            } else {
                vasurf = 0;
            }

            PreparedStatement nuevaEscuela = connection.prepareStatement("INSERT INTO escuela (nombre, gerente, latitud, longitud, direccion, descripcion, usuarioescuela, contrasenaescuela, colectivas, individuales, excursiones, alquilerkayak, alquilersurf) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            nuevaEscuela.setString(1, nombre);
            nuevaEscuela.setString(2, gerente);
            nuevaEscuela.setString(3, latitud);
            nuevaEscuela.setString(4, longitud);
            nuevaEscuela.setString(5, direccion);
            nuevaEscuela.setString(6, descripcion);
            nuevaEscuela.setString(7, usuario);
            nuevaEscuela.setString(8, contrasena);
            nuevaEscuela.setInt(9, vcolec);
            nuevaEscuela.setInt(10, vindv);
            nuevaEscuela.setInt(11, vexc);
            nuevaEscuela.setInt(12, vakayak);
            nuevaEscuela.setInt(13, vasurf);

            int nrows = nuevaEscuela.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

                PreparedStatement datosInicio = connection.prepareStatement("INSERT INTO material(nombre, escuela_idescuela)  VALUES(?,(SELECT idescuela FROM escuela WHERE usuarioescuela= ? ))");

                datosInicio.setString(1, "tabla surf");
                datosInicio.setString(2, usuario);

                nrows = datosInicio.executeUpdate();

                
                datosInicio.setString(1, "kayak");
                datosInicio.setString(2, usuario);
                

                nrows = datosInicio.executeUpdate();
                
                datosInicio.setString(1, "clases");
                datosInicio.setString(2, usuario);
                

                nrows = datosInicio.executeUpdate();

                query = "SELECT * FROM material WHERE escuela_idescuela= (SELECT idescuela FROM escuela WHERE nombre=?)";

                PreparedStatement preparedStatement2 = connection.prepareStatement(query);
                preparedStatement2.setString(1, nombre);
                ResultSet rs1 = preparedStatement2.executeQuery();
                int idkayak = 0;
                int idsurf = 0;
                int idclases= 0;
                while (rs1.next()) {

                    System.out.println(rs1.getInt(1) + " " + rs1.getString(2));
                    if (rs1.getString(2).equals("tabla surf")) {
                        idsurf = rs1.getInt(1);
                    }
                    if (rs1.getString(2).equals("kayak")) {
                        idkayak = rs1.getInt(1);
                    }
                    if (rs1.getString(2).equals("clases")) {
                        idclases = rs1.getInt(1);
                    }
                }
                System.out.println(idkayak + " " + idsurf);

                PreparedStatement datosInicio3 = connection.prepareStatement("INSERT INTO material_caracteristicas_relacion VALUES(?,1,?)");
                datosInicio3.setInt(1, idkayak);
                datosInicio3.setString(2, "0");
                nrows = datosInicio3.executeUpdate();

                PreparedStatement datosInicio4 = connection.prepareStatement("INSERT INTO material_caracteristicas_relacion  VALUES(?,2,?)");
                datosInicio4.setInt(1, idkayak);
                datosInicio4.setString(2, "0");
                nrows = datosInicio4.executeUpdate();

                datosInicio3.setInt(1, idsurf);
                datosInicio3.setString(2, "0");
                nrows = datosInicio3.executeUpdate();

                datosInicio4.setInt(1, idsurf);
                datosInicio4.setString(2, "0");
                nrows = datosInicio4.executeUpdate();
                
                PreparedStatement datosInicio5 = connection.prepareStatement("INSERT INTO material_caracteristicas_relacion  VALUES(?,3,?)");
                datosInicio5.setInt(1, idclases);
                datosInicio5.setString(2, "0");
                nrows = datosInicio5.executeUpdate();

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            respuesta.put("resultado", ex.toString());
            return respuesta;

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public JSONObject loginescuela(String nombre, String contrasena) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM escuela WHERE usuarioescuela = ? AND contrasenaescuela = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, contrasena);

                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "incorrecto");
                } else {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject setPosibilidadSurfear(String posibilidad, String usuarioescuela) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            int vposib;
            if (posibilidad.equals("no")) {
                vposib = 2;
            } else {
                vposib = 1;
            }
            try {

                PreparedStatement actualizaEscuela = connection.prepareStatement("UPDATE escuela SET posibilidadsurfear=? WHERE usuarioescuela= ? ");
                actualizaEscuela.setInt(1, vposib);
                actualizaEscuela.setString(2, usuarioescuela);

                int nrows = actualizaEscuela.executeUpdate();
                if (nrows > 0) {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                }

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject actualizaescuela(String nombre, String gerente, String latitud, String longitud, String direccion, String descripcion, String usuario, boolean colectivas, boolean individuales, boolean excursiones, boolean alquilerkayak, boolean alquilersurf) {
        int vcolec, vindv, vexc, vakayak, vasurf;

        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");

            if (colectivas) {
                vcolec = 1;
            } else {
                vcolec = 0;
            }

            if (individuales) {
                vindv = 1;
            } else {
                vindv = 0;
            }

            if (excursiones) {
                vexc = 1;
            } else {
                vexc = 0;
            }

            if (alquilerkayak) {
                vakayak = 1;
            } else {
                vakayak = 0;
            }

            if (alquilersurf) {
                vasurf = 1;
            } else {
                vasurf = 0;
            }

            PreparedStatement actualizaEscuela = connection.prepareStatement("UPDATE escuela SET nombre=?, gerente= ?, latitud= ?, longitud=?,direccion=?,descripcion=?,colectivas=?,individuales=?,excursiones=?,alquilerkayak=?,alquilersurf=? WHERE usuarioescuela= ? ");
            actualizaEscuela.setString(1, nombre);
            actualizaEscuela.setString(2, gerente);
            actualizaEscuela.setString(3, latitud);
            actualizaEscuela.setString(4, longitud);
            actualizaEscuela.setString(5, direccion);
            actualizaEscuela.setString(6, descripcion);
            actualizaEscuela.setInt(7, vcolec);
            actualizaEscuela.setInt(8, vindv);
            actualizaEscuela.setInt(9, vexc);
            actualizaEscuela.setInt(10, vakayak);
            actualizaEscuela.setInt(11, vasurf);
            actualizaEscuela.setString(12, usuario);

            int nrows = actualizaEscuela.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return respuesta;

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public JSONObject actualizausuario(String usuario, String nombre, String tlfn, String email, String contrasena) {
        int vcolec, vindv, vexc, vakayak, vasurf;

        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");

            PreparedStatement actualizaUsuario = connection.prepareStatement("UPDATE usuario SET nombre=?, email=?,telefono=?,contrasenausuario=? WHERE usuario= ? ");
            actualizaUsuario.setString(1, nombre);
            actualizaUsuario.setString(2, email);
            actualizaUsuario.setString(3, tlfn);
            actualizaUsuario.setString(4, contrasena);
            actualizaUsuario.setString(5, usuario);

            int nrows = actualizaUsuario.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return respuesta;

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public JSONObject getEscuelas() {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM escuela ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject escuela = new JSONObject();

                    String nombreescuela = rs.getString(2);
                    String latitud = rs.getString(4);
                    String longitud = rs.getString(5);
                    String direccion = rs.getString(6);
                    String descripcion = rs.getString(7);
                    int possurfear = rs.getInt(8);

                    escuela.put("nombre", nombreescuela);
                    escuela.put("latitud", latitud);
                    escuela.put("longitud", longitud);
                    escuela.put("descripcion", descripcion);
                    escuela.put("direccion", direccion);
                    escuela.put("posibilidad", possurfear);

                    array.put(escuela);
                }
                respuesta = new JSONObject();
                respuesta.put("escuelas", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getEscuelasSeguidor(String usuario) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM escuela e, seguidor s WHERE e.idescuela=s.escuela_idescuela AND s.usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario= ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usuario);
                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject escuela = new JSONObject();

                    String nombreescuela = rs.getString(2);
                    String latitud = rs.getString(4);
                    String longitud = rs.getString(5);
                    String direccion = rs.getString(6);
                    String descripcion = rs.getString(7);
                    int possurfear = rs.getInt(8);

                    escuela.put("nombre", nombreescuela);
                    escuela.put("latitud", latitud);
                    escuela.put("longitud", longitud);
                    escuela.put("descripcion", descripcion);
                    escuela.put("direccion", direccion);
                    escuela.put("posibilidad", possurfear);

                    array.put(escuela);
                }
                respuesta = new JSONObject();
                respuesta.put("escuelas", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject datosescuela(String usuario) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM escuela WHERE usuarioescuela = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String nombreescuela = rs.getString(2);
                    String gerente = rs.getString(3);
                    String latitud = rs.getString(4);
                    String longitud = rs.getString(5);
                    String direccion = rs.getString(6);
                    String descripcion = rs.getString(7);
                    int posibilidadsurfear = rs.getInt(8);
                    String contrasenaescuela = rs.getString(10);
                    int colectivas = rs.getByte(11);
                    int individuales = rs.getByte(12);
                    int excursiones = rs.getByte(13);
                    int alquilerkayak = rs.getByte(14);
                    int alquilersurf = rs.getByte(15);

                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                    respuesta.put("nombre", nombreescuela);
                    respuesta.put("gerente", gerente);
                    respuesta.put("latitud", latitud);
                    respuesta.put("longitud", longitud);
                    respuesta.put("direccion", direccion);
                    respuesta.put("posibilidadsurfear", posibilidadsurfear);
                    respuesta.put("descripcion", descripcion);
                    respuesta.put("usuario", usuario);
                    respuesta.put("contrasena", contrasenaescuela);
                    respuesta.put("colectivas", colectivas);
                    respuesta.put("individuales", individuales);
                    respuesta.put("excursiones", excursiones);
                    respuesta.put("alquilerkayak", alquilerkayak);
                    respuesta.put("alquilersurf", alquilersurf);

                }

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject datosUsuario(String usuario) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM usuario WHERE usuario = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String nombre = rs.getString(2);
                    String email = rs.getString(3);
                    String telefono = rs.getString(4);
                    String contrasena = rs.getString(6);

                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                    respuesta.put("nombre", nombre);
                    respuesta.put("email", email);
                    respuesta.put("telefono", telefono);
                    respuesta.put("contrasena", contrasena);

                }

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject datosescuelaNombre(String nombreescuela) {
        JSONObject respuesta = null;
        try {

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM escuela WHERE nombre = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String nombreescuela2 = rs.getString(2);
                    String gerente = rs.getString(3);
                    String latitud = rs.getString(4);
                    String longitud = rs.getString(5);
                    String direccion = rs.getString(6);
                    String descripcion = rs.getString(7);
                    int posibilidadsurfear = rs.getInt(8);
                    String contrasenaescuela = rs.getString(10);
                    int colectivas = rs.getByte(11);
                    int individuales = rs.getByte(12);
                    int excursiones = rs.getByte(13);
                    int alquilerkayak = rs.getByte(14);
                    int alquilersurf = rs.getByte(15);

                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                    respuesta.put("nombre", nombreescuela2);
                    respuesta.put("gerente", gerente);
                    respuesta.put("latitud", latitud);
                    respuesta.put("longitud", longitud);
                    respuesta.put("direccion", direccion);
                    respuesta.put("posibilidadsurfear", posibilidadsurfear);
                    respuesta.put("descripcion", descripcion);
                    respuesta.put("contrasena", contrasenaescuela);
                    respuesta.put("colectivas", colectivas);
                    respuesta.put("individuales", individuales);
                    respuesta.put("excursiones", excursiones);
                    respuesta.put("alquilerkayak", alquilerkayak);
                    respuesta.put("alquilersurf", alquilersurf);

                }

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject setPrecios(String usuario, String phora, String pdia, String alquiler) {
        JSONObject respuesta = new JSONObject();
        try {

            respuesta.put("resultado", "incorrecto");
            try {

                if (alquiler.equals("clases")) {
                    String query = "UPDATE material_caracteristicas_relacion SET valor=? WHERE caracteristicas_material_idcaracteristicas_material=3 AND material_idmaterial= (SELECT idmaterial FROM material m,escuela e WHERE m.escuela_idescuela=e.idescuela AND e.usuarioescuela=? AND m.nombre=? )";

                    PreparedStatement actualizaprecio = connection.prepareStatement(query);

                    actualizaprecio.setString(1, phora);
                    actualizaprecio.setString(2, usuario);
                    actualizaprecio.setString(3, alquiler);

                    int nrows = actualizaprecio.executeUpdate();

                    if (nrows > 0) {
                        respuesta = new JSONObject();
                        respuesta.put("resultado", "correcto");
                        return respuesta;
                    }
                } else {
                    String query = "UPDATE material_caracteristicas_relacion SET valor=? WHERE caracteristicas_material_idcaracteristicas_material=1 AND material_idmaterial= (SELECT idmaterial FROM material m,escuela e WHERE m.escuela_idescuela=e.idescuela AND e.usuarioescuela=? AND m.nombre=? )";

                    PreparedStatement actualizaprecioHora = connection.prepareStatement(query);
                    String query2 = "UPDATE material_caracteristicas_relacion SET valor=? WHERE caracteristicas_material_idcaracteristicas_material=2 AND material_idmaterial= (SELECT idmaterial FROM material m,escuela e WHERE m.escuela_idescuela=e.idescuela AND e.usuarioescuela=? AND m.nombre=? )";

                    PreparedStatement actualizaprecioDia = connection.prepareStatement(query2);

                    actualizaprecioHora.setString(1, phora);
                    actualizaprecioHora.setString(2, usuario);
                    actualizaprecioHora.setString(3, alquiler);

                    int nrows = actualizaprecioHora.executeUpdate();

                    actualizaprecioDia.setString(1, pdia);
                    actualizaprecioDia.setString(2, usuario);
                    actualizaprecioDia.setString(3, alquiler);

                    int nrows2 = actualizaprecioDia.executeUpdate();

                    if (nrows > 0 && nrows2 > 0) {
                        respuesta = new JSONObject();
                        respuesta.put("resultado", "correcto");
                    }
                }
            } catch (SQLException sq) {
                System.out.println(sq.toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject dejarSeguir(String nombreescuela, String usuario) {
        JSONObject respuesta = new JSONObject();
        try {

            respuesta.put("resultado", "incorrecto");
            try {
                String query = "DELETE FROM seguidor WHERE escuela_idescuela=(SELECT idescuela FROM escuela WHERE nombre=?) AND usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?)";

                PreparedStatement dejarSeguir = connection.prepareStatement(query);

                dejarSeguir.setString(1, nombreescuela);
                dejarSeguir.setString(2, usuario);

                int nrows = dejarSeguir.executeUpdate();
                if (nrows > 0) {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                }
            } catch (SQLException sq) {
                System.out.println(sq.toString());
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject programarActividad(int idactividad, String participantes, String fecha) {
        JSONObject respuesta = null;
        try {
            int nparticipantes = Integer.parseInt(participantes);
            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            PreparedStatement programarActividad = connection.prepareStatement("INSERT INTO actividad_programada(fecha,participantes_max,actividad_idactividad) VALUES (?,?,?)");
            programarActividad.setString(1, fecha);
            programarActividad.setInt(2, nparticipantes);
            programarActividad.setInt(3, idactividad);

            int nrows = programarActividad.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject setAlquiler(String usuario, String nomescuela, int cantidad, String fecha, String hora, String material, String factura, String notas) {
        JSONObject respuesta = null;
        try {
            String tipo;
            if (!hora.equals("0")) {
                tipo = "hora";
            } else {
                tipo = "dia";
            }
            if(material.equals("clases")){
                tipo="indv";
            }
            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            PreparedStatement nuevoAlquiler = connection.prepareStatement("INSERT INTO alquiler(cantidad,fecha,factura,usuario_idusuario,material_idmaterial,tipo,hora,descripcion) VALUES (?,?,?,(SELECT idusuario FROM usuario WHERE usuario=?),(SELECT m.idmaterial FROM material m, escuela e WHERE m.escuela_idescuela=e.idescuela AND e.nombre=? AND m.nombre=?),?,?,?)");
            nuevoAlquiler.setInt(1, cantidad);
            nuevoAlquiler.setString(2, fecha);
            nuevoAlquiler.setString(3, factura);
            nuevoAlquiler.setString(4, usuario);
            nuevoAlquiler.setString(5, nomescuela);
            nuevoAlquiler.setString(6, material);
            nuevoAlquiler.setString(7, tipo);
            nuevoAlquiler.setString(8, hora);
            nuevoAlquiler.setString(9,notas);

            int nrows = nuevoAlquiler.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject apuntarActividad(String usuario, int id) {
        JSONObject respuesta = null;
        try {
            int part = 0;
            int maxpart = 0;
            String query = "SELECT COUNT(*),ap.participantes_max  FROM usuario_actividad_relacion uar , actividad_programada ap WHERE uar.actividad_programada_idactividad_programada=? AND uar.actividad_programada_idactividad_programada=ap.idactividad_programada";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                part = rs.getInt(1);
                maxpart = rs.getInt(2);
            }
            if (part >= maxpart) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "maxpart");
                return respuesta;

            } else {

                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
                PreparedStatement nuevoParticipante = connection.prepareStatement("INSERT INTO usuario_actividad_relacion VALUES ((SELECT idusuario FROM usuario WHERE usuario=?),?)");
                nuevoParticipante.setString(1, usuario);
                nuevoParticipante.setInt(2, id);

                int nrows = nuevoParticipante.executeUpdate();
                if (nrows > 0) {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");

                } else {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "incorrecto");
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", "repetido");
                return respuesta;
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getPrecios(String usuario) {
      JSONObject respuesta = new JSONObject();
        try {
            String phoraSurf = "", phoraKayak = "", pdiaSurf = "", pdiaKayak = "", pClase = "";

            respuesta.put("resultado", "incorrecto");
            String query = "select cm.nombre, mcr.valor FROM material m, material_caracteristicas_relacion mcr, caracteristicas_material cm WHERE mcr.material_idmaterial=m.idmaterial AND cm.idcaracteristicas_material= mcr.caracteristicas_material_idcaracteristicas_material AND m.nombre=? AND m.escuela_idescuela= (SELECT idescuela FROM escuela WHERE usuarioescuela=?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "kayak");
                preparedStatement.setString(2, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String nombre = rs.getString(1);
                    if (nombre.equals("precioHora")) {
                        phoraKayak = rs.getString(2);
                    }
                    if (nombre.equals("precioDia")) {
                        pdiaKayak = rs.getString(2);
                    }

                }

                preparedStatement.setString(1, "tabla surf");
                preparedStatement.setString(2, usuario);

                ResultSet rss = preparedStatement.executeQuery();
                while (rss.next()) {
                    String nombre = rss.getString(1);
                    if (nombre.equals("precioHora")) {
                        phoraSurf = rss.getString(2);
                    }
                    if (nombre.equals("precioDia")) {
                        pdiaSurf = rss.getString(2);
                    }
                }

                preparedStatement.setString(1, "clases");
                preparedStatement.setString(2, usuario);

                ResultSet rsss = preparedStatement.executeQuery();
                while (rsss.next()) {
                    String nombre = rsss.getString(1);
                    if (nombre.equals("precio")) {
                        pClase = rsss.getString(2);
                    }

                }

                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
                respuesta.put("surfhora", phoraSurf);
                respuesta.put("kayakhora", phoraKayak);
                respuesta.put("surfdia", pdiaSurf);
                respuesta.put("kayakdia", pdiaKayak);
                respuesta.put("clases", pClase);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }

    public JSONObject newExcursion(String nombre, String descripcion, String lluvia, String vientomax, String usuarioescuela) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");
            String tipo = "excursion";

            PreparedStatement newExcursion = connection.prepareStatement("INSERT INTO actividad (nombre,tipo,descripcion,escuela_idescuela) VALUES (?,?,?,(SELECT idescuela FROM escuela WHERE usuarioescuela=?))", Statement.RETURN_GENERATED_KEYS);
            newExcursion.setString(1, nombre);
            newExcursion.setString(2, tipo);
            newExcursion.setString(3, descripcion);
            newExcursion.setString(4, usuarioescuela);
            int nrows = newExcursion.executeUpdate();

            ResultSet rs = newExcursion.getGeneratedKeys();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }

            PreparedStatement newExcursion2 = connection.prepareStatement("INSERT INTO actividad_caracteristicas_relacion VALUES (?,?,?)");
            newExcursion2.setInt(1, id);
            newExcursion2.setInt(2, 1);
            newExcursion2.setString(3, lluvia);
            nrows = newExcursion2.executeUpdate();

            newExcursion2.setInt(1, id);
            newExcursion2.setInt(2, 2);
            newExcursion2.setString(3, vientomax);
            nrows = newExcursion2.executeUpdate();

            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject newColectiva(String nivel, String descripcion, String precio, String usuarioescuela) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");
            String tipo = "excursion";

            PreparedStatement newColectiva = connection.prepareStatement("INSERT INTO actividad (nombre,tipo,descripcion,escuela_idescuela) VALUES ('CLASE COLECTIVA','colectiva',?,(SELECT idescuela FROM escuela WHERE usuarioescuela=?))", Statement.RETURN_GENERATED_KEYS);
            newColectiva.setString(1, descripcion);
            newColectiva.setString(2, usuarioescuela);
            int nrows = newColectiva.executeUpdate();

            ResultSet rs = newColectiva.getGeneratedKeys();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }

            PreparedStatement newExcursion2 = connection.prepareStatement("INSERT INTO actividad_caracteristicas_relacion VALUES (?,?,?)");
            newExcursion2.setInt(1, id);
            newExcursion2.setInt(2, 3);
            newExcursion2.setString(3, precio);
            nrows = newExcursion2.executeUpdate();

            newExcursion2.setInt(1, id);
            newExcursion2.setInt(2, 4);
            newExcursion2.setString(3, nivel);
            nrows = newExcursion2.executeUpdate();

            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject getExcursiones(String nombreescuela) {
        JSONObject respuesta = null;
        try {

            int id, activa;
            String nombre, descripcion, poslluvia = "", vientomax = "";

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT a.idactividad, a.nombre,a.descripcion,a.activa FROM actividad a, escuela e WHERE a.tipo='excursion' AND a.escuela_idescuela=e.idescuela AND e.nombre=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject excursion = new JSONObject();
                    id = rs.getInt(1);
                    nombre = rs.getString(2);
                    descripcion = rs.getString(3);
                    activa = rs.getInt(4);

                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT valor FROM actividad_caracteristicas_relacion WHERE actividad_idactividad=? AND caracteristicas_actividad_idcaracteristicas_actividad=?");
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.setInt(2, 1);
                    ResultSet rs1 = preparedStatement2.executeQuery();
                    while (rs1.next()) {
                        poslluvia = rs1.getString(1);
                    }
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.setInt(2, 2);
                    ResultSet rs2 = preparedStatement2.executeQuery();

                    while (rs2.next()) {
                        vientomax = rs2.getString(1);
                    }
                    excursion.put("id", id);
                    excursion.put("nombre", nombre);
                    excursion.put("descripcion", descripcion);
                    excursion.put("poslluvia", poslluvia);
                    excursion.put("vientomax", vientomax);
                    excursion.put("activa", activa);
                    array.put(excursion);
                }
                respuesta = new JSONObject();
                respuesta.put("excursiones", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    public JSONObject getAlertas(String nombreescuela){
         JSONObject respuesta = null;
        try {

            int idactividad,idprogramada,activaprog, activa;
            String nombre, descripcion, poslluvia = "", vientomax = "",fecha,direccion;

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT ap.idactividad_programada,a.idactividad, a.nombre, ap.fecha, a.activa, ap.activa, e.direccion FROM actividad a, actividad_programada ap, escuela e WHERE a.tipo='excursion' AND ap.actividad_idactividad=a.idactividad AND a.escuela_idescuela=e.idescuela AND e.nombre=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject excursion = new JSONObject();
                    idprogramada = rs.getInt(1);
                    idactividad=rs.getInt(2);
                    nombre = rs.getString(3);
                    fecha=rs.getString(4);
                    activa = rs.getInt(5);
                    activaprog = rs.getInt(6);
                    direccion= rs.getString(7);

                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT valor FROM actividad_caracteristicas_relacion WHERE actividad_idactividad=? AND caracteristicas_actividad_idcaracteristicas_actividad=?");
                    preparedStatement2.setInt(1, idactividad);
                    preparedStatement2.setInt(2, 1);
                    ResultSet rs1 = preparedStatement2.executeQuery();
                    while (rs1.next()) {
                        poslluvia = rs1.getString(1);
                    }
                    preparedStatement2.setInt(1, idactividad);
                    preparedStatement2.setInt(2, 2);
                    ResultSet rs2 = preparedStatement2.executeQuery();

                    while (rs2.next()) {
                        vientomax = rs2.getString(1);
                    }
                    
                    if(activa==0 && activaprog==0){
                    CityName ns = new CityName(direccion);
                    String scity = ns.getCity();
                    String city = "";
                    if (!scity.equals("") || city != null) {
                        city = scity.trim();
                    }


                    query = "SELECT * FROM api WHERE ciudad=?";

                    PreparedStatement preparedStatement3 = connection.prepareStatement(query);
                    preparedStatement3.setString(1, city);

                    ResultSet rs4 = preparedStatement3.executeQuery();
                    String codigo="";
                    if (rs4.next()) {
                        System.out.println(rs4.getString(1) + " " + rs4.getString(2));
                        codigo=rs4.getString(2);
                    }

                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    System.out.println(fecha);
                    Date fechaprg = formatoFecha.parse(fecha);
                    SimpleDateFormat formato2 = new SimpleDateFormat("yyyyMMdd");
                    String fechaformat = formato2.format(fechaprg);
                    

                    Meteo xsm = new Meteo(codigo, fechaformat);
                    String[] result = xsm.getResultado();
                    
                    boolean enviar=false;
                    if (result[0] == null) {
                        System.out.println("No se puede obtener la meteorología para esa fecha");
                    } else {
                        String plluvia=result[1];
                        String pviento=result[0];
                        String fplluvia="0";
                        if(Double.parseDouble(plluvia)>0){
                            fplluvia="1";
                        }
                        System.out.println("Poslluvia: "+poslluvia+" Vientomax: "+vientomax+" Lluvia: "+result[1]+" Viento: "+result[0]);
                         if((poslluvia.equals("0")&&fplluvia.equals("1"))&&(Double.parseDouble(pviento)>Double.parseDouble(vientomax))){
                            excursion.put("descripcion","Lloverá el día de la excursión y el viento tendrá una velocidad mayor a la permitida");
                            enviar=true;
                        }else if(poslluvia.equals("0")&&fplluvia.equals("1")){
                            excursion.put("descripcion","Lloverá el día de la excursión");
                            enviar=true;
                        }else if((Double.parseDouble(pviento)>Double.parseDouble(vientomax))){
                            enviar=true;
                            excursion.put("descripcion","Habrá vientos con velocidades superiores a la máxima marcada");
                        }
      

                    }
                    if(enviar){
                        excursion.put("id", idprogramada);
                        excursion.put("nombre", nombre);
                        excursion.put("fecha", fecha);
                        excursion.put("poslluvia", poslluvia);
                        excursion.put("vientomax", vientomax);
                        excursion.put("pviento",result[0]);
                        excursion.put("plluvia",result[1]);
                         array.put(excursion);
                    }
                    
                   
                   
                    }
                }
                respuesta = new JSONObject();
                respuesta.put("excursiones", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                 Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
             }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getExcursionesProgramadas(String nombreescuela) {
        JSONObject respuesta = null;
        try {

            int idactividad,idprogramada,activaprog, activa;
            String nombre, descripcion, poslluvia = "", vientomax = "",fecha,direccion;

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT ap.idactividad_programada,a.idactividad, a.nombre, ap.fecha, a.activa. ap.activa, e.direccion FROM actividad a, actividad_programada ap, escuela e WHERE a.tipo='excursion' AND ap.actividad_idactividad=a.idactvidad AND a.escuela_idescuela=e.idescuela AND e.nombre=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject excursion = new JSONObject();
                    idprogramada = rs.getInt(1);
                    idactividad=rs.getInt(2);
                    nombre = rs.getString(3);
                    fecha=rs.getString(4);
                    activa = rs.getInt(5);
                    activaprog = rs.getInt(6);
                    direccion= rs.getString(7);

                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT valor FROM actividad_caracteristicas_relacion WHERE actividad_idactividad=? AND caracteristicas_actividad_idcaracteristicas_actividad=?");
                    preparedStatement2.setInt(1, idactividad);
                    preparedStatement2.setInt(2, 1);
                    ResultSet rs1 = preparedStatement2.executeQuery();
                    while (rs1.next()) {
                        poslluvia = rs1.getString(1);
                    }
                    preparedStatement2.setInt(1, idactividad);
                    preparedStatement2.setInt(2, 2);
                    ResultSet rs2 = preparedStatement2.executeQuery();

                    while (rs2.next()) {
                        vientomax = rs2.getString(1);
                    }
                    excursion.put("id", idprogramada);
                    excursion.put("nombre", nombre);
                    excursion.put("fecha", fecha);
                    excursion.put("poslluvia", poslluvia);
                    excursion.put("vientomax", vientomax);
                    excursion.put("activa", activa);
                    excursion.put("activaprog", activaprog);
                    excursion.put("direccion",direccion);
                    array.put(excursion);
                }
                respuesta = new JSONObject();
                respuesta.put("excursiones", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    

    public JSONObject getColectivas(String nombreescuela) {
        JSONObject respuesta = null;
        try {

            int id, activa;
            String descripcion, nivel = "", precio = "";

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT a.idactividad, a.nombre,a.descripcion,a.activa FROM actividad a, escuela e WHERE a.tipo='colectiva' AND a.escuela_idescuela=e.idescuela AND e.nombre=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject colectiva = new JSONObject();
                    id = rs.getInt(1);
                    descripcion = rs.getString(3);
                    activa = rs.getInt(4);

                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT valor FROM actividad_caracteristicas_relacion WHERE actividad_idactividad=? AND caracteristicas_actividad_idcaracteristicas_actividad=?");
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.setInt(2, 3);
                    ResultSet rs1 = preparedStatement2.executeQuery();
                    while (rs1.next()) {
                        precio = rs1.getString(1);
                    }
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.setInt(2, 4);
                    ResultSet rs2 = preparedStatement2.executeQuery();

                    while (rs2.next()) {
                        nivel = rs2.getString(1);
                    }
                    colectiva.put("id", id);
                    colectiva.put("descripcion", descripcion);
                    colectiva.put("nivel", nivel);
                    colectiva.put("precio", precio);
                    colectiva.put("activa", activa);
                    array.put(colectiva);
                }
                respuesta = new JSONObject();
                respuesta.put("colectivas", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getActividadesProgramadas(String nombreescuela, String tipo) {
      JSONObject respuesta = null;
        try {
            int id;
            String nombre, fecha, descripcion;
            int participantes, activa, activaprog;

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT ap.idactividad_programada,a.nombre,a.descripcion,ap.fecha,ap.participantes_max,a.activa,ap.activa FROM actividad a, actividad_programada ap, escuela e WHERE a.idactividad=ap.actividad_idactividad AND a.escuela_idescuela=e.idescuela AND e.nombre=? AND a.tipo=? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);
                preparedStatement.setString(2, tipo);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject actividad = new JSONObject();
                    id = rs.getInt(1);
                    nombre = rs.getString(2);
                    fecha = rs.getString(4);
                    descripcion = rs.getString(3);
                    participantes = rs.getInt(5);
                    activa = rs.getInt(6);
                    activaprog = rs.getInt(7);

                    actividad.put("id", id);
                    actividad.put("nombre", nombre);
                    actividad.put("fecha", fecha);
                    actividad.put("descripcion", descripcion);
                    actividad.put("participantes", participantes);
                    actividad.put("activa", activa);
                    actividad.put("activaprog", activaprog);
                   
                    
                    String query2="SELECT COUNT(*) FROM usuario_actividad_relacion WHERE actividad_programada_idactividad_programada=?";
                    PreparedStatement pparticipantes=connection.prepareStatement(query2);
                    pparticipantes.setInt(1,id);
                    ResultSet rss=pparticipantes.executeQuery();
                    int pactual=0;
                    while(rss.next()){
                        pactual=rss.getInt(1);
                    }
                    actividad.put("pactual",pactual);
                    
                     array.put(actividad);
                }
                respuesta = new JSONObject();
                respuesta.put("actividades", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getActividadesProgramadasCliente(String usuario, String nombreescuela, String tipo) {
        JSONObject respuesta = null;
        try {
            int id;
            String nombre, fecha, descripcion, escuela;
            int participantes, activa,activaprog;

            if (tipo.equals("excursion")) {

                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
                String query = "SELECT ap.idactividad_programada,a.nombre,a.descripcion,ap.fecha,ap.participantes_max,a.activa, ap.activa FROM actividad a, actividad_programada ap, escuela e WHERE a.idactividad=ap.actividad_idactividad AND a.escuela_idescuela=e.idescuela AND e.nombre=? AND a.tipo=? AND ap.idactividad_programada NOT IN (SELECT actividad_programada_idactividad_programada FROM usuario_actividad_relacion WHERE usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?))";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);
                preparedStatement.setString(2, tipo);
                preparedStatement.setString(3, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject actividad = new JSONObject();
                    id = rs.getInt(1);
                    nombre = rs.getString(2);
                    fecha = rs.getString(4);
                    descripcion = rs.getString(3);
                    participantes = rs.getInt(5);
                    activa = rs.getInt(6);
                    activaprog= rs.getInt(7);

                    actividad.put("id", id);
                    actividad.put("nombre", nombre);
                    actividad.put("fecha", fecha);
                    actividad.put("descripcion", descripcion);
                    actividad.put("participantes", participantes);
                    actividad.put("activa", activa);
                    actividad.put("activaprog",activaprog);
                    array.put(actividad);
                }
                respuesta = new JSONObject();
                respuesta.put("actividades", array);

            } else if (tipo.equals("colectiva")) {

                String nivel, precio = "";
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
                String query = "SELECT ap.idactividad_programada,a.nombre,a.descripcion,ap.fecha,ap.participantes_max,a.activa,acr.valor, ap.activa FROM actividad a, actividad_programada ap, escuela e,actividad_caracteristicas_relacion acr WHERE a.idactividad=ap.actividad_idactividad AND a.idactividad=acr.actividad_idactividad AND acr.caracteristicas_actividad_idcaracteristicas_actividad=4 AND a.escuela_idescuela=e.idescuela AND e.nombre=? AND a.tipo=? AND ap.idactividad_programada NOT IN (SELECT actividad_programada_idactividad_programada FROM usuario_actividad_relacion WHERE usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?))";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombreescuela);
                preparedStatement.setString(2, tipo);
                preparedStatement.setString(3, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject actividad = new JSONObject();
                    id = rs.getInt(1);
                    nombre = rs.getString(2);
                    fecha = rs.getString(4);
                    descripcion = rs.getString(3);
                    participantes = rs.getInt(5);
                    activa = rs.getInt(6);
                    nivel = rs.getString(7);
                    activaprog=rs.getInt(8);

                    String query2 = "SELECT valor FROM actividad_caracteristicas_relacion WHERE actividad_idactividad=(SELECT actividad_idactividad FROM actividad_programada WHERE idactividad_programada=?) AND caracteristicas_actividad_idcaracteristicas_actividad=? ";

                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, id);
                    preparedStatement2.setInt(2, 3);

                     ResultSet rs2 = preparedStatement2.executeQuery();
                    while (rs2.next()) {
                        precio = rs2.getString(1);
                    }
                    actividad.put("id", id);
                    actividad.put("precio", precio);
                    actividad.put("nombre", nombre);
                    actividad.put("fecha", fecha);
                    actividad.put("descripcion", descripcion);
                    actividad.put("participantes", participantes);
                    actividad.put("activa", activa);
                    actividad.put("nivel", nivel);
                    actividad.put("activaprog",activaprog);
                    array.put(actividad);
                }
                respuesta = new JSONObject();
                respuesta.put("actividades", array);

            }

        } catch (JSONException | SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getActividadesProgramadasParticipaCliente(String usuario, String tipo) throws JSONException {
            JSONObject respuesta = null;
        try {
            int id;
            String nombre, fecha, descripcion, escuela;
            int participantes, activa,activaprog;
            if (tipo.equals("colectiva")) {
                respuesta = new JSONObject();
                String nivel;
                respuesta.put("resultado", "incorrecto");
                String query = "SELECT e.nombre, ap.idactividad_programada,a.nombre,a.descripcion,ap.fecha,ap.participantes_max,a.activa,acr.valor,ap.activa FROM actividad a, actividad_programada ap, escuela e,actividad_caracteristicas_relacion acr WHERE a.idactividad=ap.actividad_idactividad AND a.idactividad=acr.actividad_idactividad AND acr.caracteristicas_actividad_idcaracteristicas_actividad=4 AND a.escuela_idescuela=e.idescuela AND a.tipo=? AND ap.idactividad_programada IN (SELECT actividad_programada_idactividad_programada FROM usuario_actividad_relacion WHERE usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?)) ";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, tipo);
                    preparedStatement.setString(2, usuario);
                    ResultSet rs = preparedStatement.executeQuery();
                    JSONArray array = new JSONArray();
                    while (rs.next()) {
                        JSONObject actividad = new JSONObject();
                        escuela = rs.getString(1);
                        id = rs.getInt(2);
                        nombre = rs.getString(3);
                        fecha = rs.getString(5);
                        descripcion = rs.getString(4);
                        participantes = rs.getInt(6);
                        activa = rs.getInt(7);
                        nivel=rs.getString(8);
                        activaprog=rs.getInt(9);

                        actividad.put("id", id);
                        actividad.put("nombre", nombre);
                        actividad.put("fecha", fecha);
                        actividad.put("escuela", escuela);
                        actividad.put("descripcion", descripcion);
                        actividad.put("participantes", participantes);
                        actividad.put("activa", activa);
                        actividad.put("activaprog",activaprog);
                        actividad.put("nivel",nivel);
                        array.put(actividad);
                    }
                    respuesta = new JSONObject();
                    respuesta.put("actividades", array);
                } catch (SQLException ex) {
                    Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
                String query = "SELECT e.nombre,ap.idactividad_programada,a.nombre,a.descripcion,ap.fecha,ap.participantes_max,a.activa,ap.activa FROM actividad a, actividad_programada ap, escuela e WHERE a.idactividad=ap.actividad_idactividad AND a.escuela_idescuela=e.idescuela AND a.tipo=? AND ap.idactividad_programada IN (SELECT actividad_programada_idactividad_programada FROM usuario_actividad_relacion WHERE usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?))";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, tipo);
                    preparedStatement.setString(2, usuario);
                    ResultSet rs = preparedStatement.executeQuery();
                    JSONArray array = new JSONArray();
                    while (rs.next()) {
                        JSONObject actividad = new JSONObject();
                        escuela = rs.getString(1);
                        id = rs.getInt(2);
                        nombre = rs.getString(3);
                        fecha = rs.getString(5);
                        descripcion = rs.getString(4);
                        participantes = rs.getInt(6);
                        activa = rs.getInt(7);
                        activaprog=rs.getInt(8);
                        
                        actividad.put("id", id);
                        actividad.put("nombre", nombre);
                        actividad.put("fecha", fecha);
                        actividad.put("escuela", escuela);
                        actividad.put("descripcion", descripcion);
                        actividad.put("participantes", participantes);
                        actividad.put("activa", activa);
                        actividad.put("activaprog",activaprog);
                        array.put(actividad);
                    }
                    respuesta = new JSONObject();
                    respuesta.put("actividades", array);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject cancelarActividadCliente(String nombre, int id) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement cancelarActividad = connection.prepareStatement("DELETE FROM usuario_actividad_relacion WHERE usuario_idusuario=(SELECT idusuario FROM usuario WHERE usuario=?) AND actividad_programada_idactividad_programada=? ");
            cancelarActividad.setString(1, nombre);
            cancelarActividad.setInt(2, id);
            int nrows = cancelarActividad.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject suspenderActividad(String escuela, int id, String mensaje, String asunto) {
        JSONObject respuesta = new JSONObject();
        int idescuela = 0;
        try {
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT idseguidor FROM seguidor WHERE usuario_idusuario IN (SELECT usuario_idusuario FROM usuario_actividad_relacion WHERE  actividad_programada_idactividad_programada=?) AND escuela_idescuela=(SELECT idescuela FROM escuela WHERE nombre=?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, escuela);

            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Integer> participantes = new ArrayList<>();
            while (rs.next()) {
                participantes.add(rs.getInt(1));

            }

            query = "SELECT idescuela FROM escuela WHERE nombre=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, escuela);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idescuela = rs.getInt(1);

            }
            int nrows = 0;
            PreparedStatement cancelarActividad = connection.prepareStatement("INSERT INTO mensaje(asunto,contenido,fecha,seguidor_idseguidor,escuela_idescuela) VALUES (?,?,?,?,?)");
            for (int i = 0; i < participantes.size(); i++) {
                cancelarActividad.setString(1, asunto);
                cancelarActividad.setString(2, mensaje);
                cancelarActividad.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                cancelarActividad.setInt(4, participantes.get(i));
                cancelarActividad.setInt(5, idescuela);
                nrows = cancelarActividad.executeUpdate();

            }
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

            cancelarActividad = connection.prepareStatement("UPDATE actividad_programada SET activa=1 WHERE idactividad_programada= ? ");
            cancelarActividad.setInt(1, id);
            nrows = cancelarActividad.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }
    
     public JSONObject enviarMsn(String escuela, int id, String mensaje, String asunto) {
        JSONObject respuesta = new JSONObject();
        int idescuela = 0;
        try {
            respuesta.put("resultado", "incorrecto");
            String query = "SELECT idseguidor FROM seguidor WHERE usuario_idusuario IN (SELECT usuario_idusuario FROM usuario_actividad_relacion WHERE  actividad_programada_idactividad_programada=?) AND escuela_idescuela=(SELECT idescuela FROM escuela WHERE nombre=?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, escuela);

            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Integer> participantes = new ArrayList<>();
            while (rs.next()) {
                participantes.add(rs.getInt(1));

            }

            query = "SELECT idescuela FROM escuela WHERE nombre=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, escuela);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idescuela = rs.getInt(1);

            }
            int nrows = 0;
            PreparedStatement cancelarActividad = connection.prepareStatement("INSERT INTO mensaje(asunto,contenido,fecha,seguidor_idseguidor,escuela_idescuela) VALUES (?,?,?,?,?)");
            for (int i = 0; i < participantes.size(); i++) {
                cancelarActividad.setString(1, asunto);
                cancelarActividad.setString(2, mensaje);
                cancelarActividad.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                cancelarActividad.setInt(4, participantes.get(i));
                cancelarActividad.setInt(5, idescuela);
                nrows = cancelarActividad.executeUpdate();

            }
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

           

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject getParticipantes(int id) {
        JSONObject respuesta = null;
        String query;
        try {
            String nombre, telefono;
            int participantes;

            respuesta = new JSONObject();
            respuesta.put("resultado", "incorrecto");
            query = "SELECT u.nombre,u.telefono FROM usuario u, usuario_actividad_relacion ua WHERE   ua.usuario_idusuario=u.idusuario AND  ua.actividad_programada_idactividad_programada=?;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject participante = new JSONObject();
                    nombre = rs.getString(1);
                    telefono = rs.getString(2);
                    participante.put("nombre", nombre);
                    participante.put("telefono", telefono);
                    array.put(participante);
                }
                respuesta = new JSONObject();
                respuesta.put("participantes", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject getAlquiler(String usuario, String tipoalquiler) {
        JSONObject respuesta = new JSONObject();
        try {
            String phoraSurf = "", phoraKayak = "", pdiaSurf = "", pdiaKayak = "";

            respuesta.put("resultado", "incorrecto");
            String query = "SELECT u.nombre,u.usuario, u.telefono, a.cantidad, a.fecha, a.hora, a.factura, a.tipo, a.idalquiler, a.aprobado, a.cancelado, a.descripcion,a.realizado FROM alquiler a, material m, escuela e, usuario u WHERE m.idmaterial=a.material_idmaterial AND a.usuario_idusuario=u.idusuario AND m.nombre=? AND m.escuela_idescuela=e.idescuela AND e.usuarioescuela=?  ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, tipoalquiler);
                preparedStatement.setString(2, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject alquiler = new JSONObject();

                    alquiler.put("nombre", rs.getString(1));
                    alquiler.put("usuario", rs.getString(2));
                    alquiler.put("telefono", rs.getString(3));
                    alquiler.put("cantidad", rs.getInt(4));
                    alquiler.put("fecha", rs.getString(5));
                    alquiler.put("hora", rs.getString(6));
                    alquiler.put("factura", rs.getString(7));
                    alquiler.put("tipo", rs.getString(8));
                    alquiler.put("idalquiler", rs.getInt(9));
                    alquiler.put("aprobado", rs.getByte(10));
                    alquiler.put("cancelado", rs.getByte(11));
                    alquiler.put("notas",rs.getString(12));
                    alquiler.put("realizado",rs.getByte(13));

                    array.put(alquiler);
                }
                respuesta = new JSONObject();
                respuesta.put("alquileres", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }

    public JSONObject getMensajes(String usuario) {
        JSONObject respuesta = new JSONObject();
        try {

            respuesta.put("resultado", "incorrecto");
            String query = "SELECT m.fecha,m.asunto,m.contenido,m.idmensaje,e.nombre,m.leido FROM mensaje m, escuela e, usuario u, seguidor s WHERE m.escuela_idescuela=e.idescuela AND m.seguidor_idseguidor=s.idseguidor AND s.usuario_idusuario=u.idusuario AND u.usuario=? ORDER BY m.leido";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                JSONArray array = new JSONArray();
                while (rs.next()) {
                    JSONObject mensaje = new JSONObject();

                    mensaje.put("fecha", rs.getString(1));
                    mensaje.put("asunto", rs.getString(2));
                    mensaje.put("contenido", rs.getString(3));
                    mensaje.put("id", rs.getInt(4));
                    mensaje.put("escuela", rs.getString(5));
                    mensaje.put("visto", rs.getInt(6));

                    array.put(mensaje);
                }
                respuesta = new JSONObject();
                respuesta.put("mensajes", array);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }
    
    public JSONObject getContadorMensajes(String usuario){
          JSONObject respuesta = new JSONObject();
        try {

            respuesta.put("resultado", "incorrecto");
            String query = "SELECT COUNT(*) FROM mensaje m, escuela e, usuario u, seguidor s WHERE m.escuela_idescuela=e.idescuela AND m.seguidor_idseguidor=s.idseguidor AND s.usuario_idusuario=u.idusuario AND u.usuario=? AND m.leido IS NULL";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, usuario);

                ResultSet rs = preparedStatement.executeQuery();
                int contador=0;
                while (rs.next()) {
                    
                    contador=rs.getInt(1);
                }
                respuesta = new JSONObject();
                respuesta.put("contador", contador);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public JSONObject getAlquilerCliente(String usuario, String tipoalquiler) {
        JSONObject respuesta = new JSONObject();
        try {
            if (tipoalquiler.equals("all")) {
                respuesta.put("resultado", "incorrecto");
                String query = "SELECT e.nombre, a.cantidad, a.fecha, a.hora, a.factura, a.tipo, a.idalquiler, a.aprobado, a.cancelado,a.visto, a.descripcion,m.nombre,a.realizado  FROM alquiler a, material m, escuela e, usuario u WHERE m.idmaterial=a.material_idmaterial AND a.usuario_idusuario=u.idusuario AND m.escuela_idescuela=e.idescuela AND u.usuario=?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, usuario);

                    ResultSet rs = preparedStatement.executeQuery();
                    JSONArray array = new JSONArray();
                    while (rs.next()) {
                        JSONObject alquiler = new JSONObject();

                        alquiler.put("nombre", rs.getString(1));
                        alquiler.put("cantidad", rs.getInt(2));
                        alquiler.put("fecha", rs.getString(3));
                        alquiler.put("hora", rs.getString(4));
                        alquiler.put("factura", rs.getString(5));
                        alquiler.put("tipo", rs.getString(6));
                        alquiler.put("idalquiler", rs.getInt(7));
                        alquiler.put("aprobado", rs.getByte(8));
                        alquiler.put("cancelado", rs.getByte(9));
                        alquiler.put("visto", rs.getByte(10));
                        alquiler.put("descripcion", rs.getString(11));
                        alquiler.put("material",rs.getString(12));
                        alquiler.put("realizado",rs.getByte(13));

                        array.put(alquiler);
                    }
                    respuesta = new JSONObject();
                    respuesta.put("alquileres", array);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                String phoraSurf = "", phoraKayak = "", pdiaSurf = "", pdiaKayak = "";

                respuesta.put("resultado", "incorrecto");
                String query = "SELECT e.nombre, a.cantidad, a.fecha, a.hora, a.factura, a.tipo, a.idalquiler, a.aprobado, a.cancelado,a.visto, a.descripcion,a.realizado  FROM alquiler a, material m, escuela e, usuario u WHERE m.idmaterial=a.material_idmaterial AND a.usuario_idusuario=u.idusuario AND m.nombre=? AND m.escuela_idescuela=e.idescuela AND u.usuario=?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, tipoalquiler);
                    preparedStatement.setString(2, usuario);

                    ResultSet rs = preparedStatement.executeQuery();
                    JSONArray array = new JSONArray();
                    while (rs.next()) {
                        JSONObject alquiler = new JSONObject();

                        alquiler.put("nombre", rs.getString(1));
                        alquiler.put("cantidad", rs.getInt(2));
                        alquiler.put("fecha", rs.getString(3));
                        alquiler.put("hora", rs.getString(4));
                        alquiler.put("factura", rs.getString(5));
                        alquiler.put("tipo", rs.getString(6));
                        alquiler.put("idalquiler", rs.getInt(7));
                        alquiler.put("aprobado", rs.getByte(8));
                        alquiler.put("cancelado", rs.getByte(9));
                        alquiler.put("visto", rs.getByte(10));
                        alquiler.put("descripcion", rs.getString(11));
                        alquiler.put("realizado",rs.getByte(12));

                        array.put(alquiler);
                    }
                    respuesta = new JSONObject();
                    respuesta.put("alquileres", array);

                } catch (SQLException ex) {
                    Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }

    public JSONObject logincliente(String nombre, String contrasena) {
        JSONObject respuesta = new JSONObject();
        try {

            respuesta.put("resultado", "incorrecto");
            String query = "SELECT * FROM usuario WHERE usuario = ? AND contrasenausuario = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, contrasena);

                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "incorrecto");
                } else {
                    System.out.println("RESULTADO CORRECTO");
                    respuesta = new JSONObject();
                    respuesta.put("resultado", "correcto");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public JSONObject registrocliente(String usuario, String nombre, String tlfn, String email, String contrasena) {

        JSONObject respuesta = null;
        try {

            String query = "SELECT * FROM usuario WHERE usuario = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "yaexiste");
                return respuesta;
            }

            PreparedStatement nuevoCliente = connection.prepareStatement("INSERT INTO usuario (nombre,email,telefono,usuario,contrasenausuario) VALUES(?,?,?,?,?)");
            nuevoCliente.setString(1, nombre);
            nuevoCliente.setString(2, email);
            nuevoCliente.setString(3, tlfn);
            nuevoCliente.setString(4, usuario);
            nuevoCliente.setString(5, contrasena);

            int nrows = nuevoCliente.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    public JSONObject getPreciosCliente(String nombre) {
        JSONObject respuesta = new JSONObject();
        try {
            String phoraSurf = "", phoraKayak = "", pdiaSurf = "", pdiaKayak = "";

            respuesta.put("resultado", "incorrecto");
            String query = "select cm.nombre, mcr.valor FROM material m, material_caracteristicas_relacion mcr, caracteristicas_material cm WHERE mcr.material_idmaterial=m.idmaterial AND cm.idcaracteristicas_material= mcr.caracteristicas_material_idcaracteristicas_material AND m.nombre=? AND m.escuela_idescuela= (SELECT idescuela FROM escuela WHERE nombre=?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "kayak");
                preparedStatement.setString(2, nombre);

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String nom = rs.getString(1);
                    if (nom.equals("precioHora")) {
                        phoraKayak = rs.getString(2);
                    }
                    if (nom.equals("precioDia")) {
                        pdiaKayak = rs.getString(2);
                    }
                }

                PreparedStatement pSurf = connection.prepareStatement(query);
                pSurf.setString(1, "tabla surf");
                pSurf.setString(2, nombre);

                ResultSet rss = pSurf.executeQuery();
                while (rss.next()) {
                    String nom = rss.getString(1);
                    if (nom.equals("precioHora")) {
                        phoraSurf = rss.getString(2);
                    }
                    if (nom.equals("precioDia")) {
                        pdiaSurf = rss.getString(2);
                    }
                }
                pSurf.setString(1, "clases");
                pSurf.setString(2, nombre);
                String pClases="";
                ResultSet rsss = pSurf.executeQuery();
                while (rsss.next()) {
                    String nom = rsss.getString(1);
                    if (nom.equals("precio")) {
                        pClases = rsss.getString(2);
                    }
                  
                }

                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
                respuesta.put("surfhora", phoraSurf);
                respuesta.put("kayakhora", phoraKayak);
                respuesta.put("surfdia", pdiaSurf);
                respuesta.put("kayakdia", pdiaKayak);
                respuesta.put("pclases",pClases);

            } catch (SQLException ex) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;

    }
    
     public JSONObject setMensajeVisto(int id) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement setVisto = connection.prepareStatement("UPDATE mensaje SET leido=1 WHERE idmensaje= ? ");
            setVisto.setInt(1, id);
            int nrows = setVisto.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return respuesta;
        
     }  

    public JSONObject setAlquilerVisto(int id) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement setVisto = connection.prepareStatement("UPDATE alquiler SET visto=1 WHERE idalquiler= ? ");
            setVisto.setInt(1, id);
            int nrows = setVisto.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }
    
      public JSONObject setAlquilerRealizado(int id) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement setVisto = connection.prepareStatement("UPDATE alquiler SET realizado=1 WHERE idalquiler= ? ");
            setVisto.setInt(1, id);
            int nrows = setVisto.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }


    public JSONObject setAprobado(int idalquiler) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement aprobarAlquiler = connection.prepareStatement("UPDATE alquiler SET aprobado=1 WHERE idalquiler= ? ");
            aprobarAlquiler.setInt(1, idalquiler);
            int nrows = aprobarAlquiler.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject setCancelado(int idalquiler) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement aprobarAlquiler = connection.prepareStatement("UPDATE alquiler SET cancelado=1 WHERE idalquiler= ? ");
            aprobarAlquiler.setInt(1, idalquiler);
            int nrows = aprobarAlquiler.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject cancelarActividad(int id) {
        JSONObject respuesta = new JSONObject();
        try {
            respuesta.put("resultado", "incorrecto");

            PreparedStatement cancelaActividad = connection.prepareStatement("UPDATE actividad SET activa=1 WHERE idactividad= ? ");
            cancelaActividad.setInt(1, id);
            int nrows = cancelaActividad.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);

        }

        return respuesta;
    }

    public JSONObject seguirEscuela(String usuariocliente, String nombreescuela) {

        JSONObject respuesta = null;
        try {

            String query = "SELECT * FROM escuela e, seguidor s, usuario u WHERE s.escuela_idescuela=e.idescuela AND s.usuario_idusuario=u.idusuario AND e.nombre=? AND u.usuario=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nombreescuela);
            preparedStatement.setString(2, usuariocliente);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "yaexiste");
                return respuesta;
            }

            PreparedStatement nuevoSeguidor = connection.prepareStatement("INSERT INTO seguidor (usuario_idusuario,escuela_idescuela) VALUES((SELECT idusuario FROM usuario WHERE usuario=?),(SELECT idescuela FROM escuela WHERE nombre=?))");
            nuevoSeguidor.setString(1, usuariocliente);
            nuevoSeguidor.setString(2, nombreescuela);

            int nrows = nuevoSeguidor.executeUpdate();
            if (nrows > 0) {
                respuesta = new JSONObject();
                respuesta.put("resultado", "correcto");

            } else {
                respuesta = new JSONObject();
                respuesta.put("resultado", "incorrecto");
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            respuesta = new JSONObject();
            try {
                respuesta.put("resultado", ex.toString());
            } catch (JSONException ex1) {
                Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (JSONException ex) {
            Logger.getLogger(GestionaBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

}
