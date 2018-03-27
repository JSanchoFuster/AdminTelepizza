/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class Conexion {
    private String url;
    private String usuario;
    private String pass;
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Conexion() {
        this.url = "jdbc:mysql://192.168.4.115:3310/telepizza";
        this.usuario = "jsancho";
        this.pass = "Admin1234";
    }
    
    

    public Conexion(String url, String usuario, String pass) {
        this.url = url;
        this.usuario = usuario;
        this.pass = pass;
    }
    
    private void conectar(){
    
        
        try {
            con = (Connection) DriverManager.getConnection(url,usuario,pass);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        

    }
    private void desconectar(){
        try {           
            con.close();                   
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean anadirPizza(String nombre){
        boolean nuevo = false;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select nombre from pizza where nombre='"+nombre+"';");
            if (!rs.next()){
                st.executeUpdate("Insert into pizza values('0','"+nombre+"');");
                nuevo = true;
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevo;
    }
    
    public boolean borrarPizza(String nombre){
        boolean borrado = false;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select nombre from pizza where nombre='"+nombre+"';");
            if (rs.next()){
                st.executeUpdate("Delete from pizza where nombre='"+nombre+"';");
                borrado = true;
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borrado;
    }
    
    public double verTotal(){
        double dinero=0;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) from pedido;");
            if (rs.next()){
                dinero = rs.getDouble(1);
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dinero;
    }
    
    public double verTotalAgrupado(){
        double dinero=0;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) from pedido where fecha=CURDATE();");
            if (rs.next()){
                dinero = rs.getDouble(1);
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dinero;
    }
    
    public boolean anadirCliente(String nombre,int telefono,String direccion){
        boolean nuevo = false;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select nombre from cliente where telefono="+telefono+";");
            if (!rs.next()){
                st.executeUpdate("Insert into cliente values('0','"+nombre+"',"+telefono+",'"+direccion+"');");
                nuevo = true;
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevo;
    }
    
     public boolean borrarCliente(int telefono){
        boolean borrado = false;
        try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select telefono from cliente where telefono="+telefono+";");
            if (rs.next()){
                st.executeUpdate("Delete from cliente where telefono='"+telefono+"';");
                borrado = true;
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borrado;
    }
     
     public String verPizzas(){
         String aux = "";
          try {           
            conectar();
            st = con.createStatement();
            rs = st.executeQuery("Select nombre from pizza;");
            while (rs.next()){
               aux+=rs.getString(1);
               aux+="\n";
            }
            desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
         return aux;
     }
}