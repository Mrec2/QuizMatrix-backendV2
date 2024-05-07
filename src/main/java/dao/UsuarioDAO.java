package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gestor.GestorQueries;
import models.Usuario;

public class UsuarioDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> getUsuarios() throws SQLException {
    	
        List<Usuario> usuarios = new ArrayList<>();
        
        //final String sql = "SELECT * FROM usuarios";
        final String sql = GestorQueries.obtenerQuerie("queryUSUARIOS");
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {      	
        	
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int usuarioId = Integer.parseInt(resultSet.getString("id"));
                    String usuarioNombre = resultSet.getString("nombre");
                    String usuarioEmail = resultSet.getString("email");
                    String usuarioPassword = resultSet.getString("password");
                    Usuario usuario = new Usuario(usuarioId, usuarioNombre, usuarioEmail, usuarioPassword);
                    usuarios.add(usuario);
                }
            }
            
        }
        
        return usuarios;
    }
    
    
    // Método para obtener un usuario
    public List<Usuario> getUsuarioId(int id) throws SQLException {
    	
        List<Usuario> usuarios = new ArrayList<>();
        
        //final String sql = "SELECT * FROM usuarios WHERE id = ?";
        final String sql = GestorQueries.obtenerQuerie("queryUSUARIOID");
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	
        	statement.setInt(1, id); // VALOR 1
        	
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int usuarioId = Integer.parseInt(resultSet.getString("id"));
                    String usuarioNombre = resultSet.getString("nombre");
                    String usuarioEmail = resultSet.getString("email");
                    String usuarioPassword = resultSet.getString("password");
                    Usuario usuario = new Usuario(usuarioId, usuarioNombre, usuarioEmail, usuarioPassword);
                    usuarios.add(usuario);
                }
            }
            
        }
        
        return usuarios;
    }
    
    
    // Método para insertar un usuario
    public Boolean insertarUsuario(String nombre, String email, String password) throws SQLException {
        
    	Boolean resultado = false;
    	
    	//final String sql = "INSERT INTO usuarios VALUES (null, ?, ?, ?)";  	
        final String sql = GestorQueries.obtenerQuerie("queryINSERTAR");
    	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	
        	statement.setString(1, nombre);
        	statement.setString(2, email);
            statement.setString(3, password);
        	
        	// Informacion en https://old.chuidiang.org/java/mysql/PreparedStatement-java-mysql.php
            int resultSet = statement.executeUpdate();

            // Si todo OK devuelve 1
            if(resultSet == 1) {
            	resultado = true;
            }
                       
        } catch (SQLException e) {
            e.printStackTrace();           
        }
        
        return resultado;
        
    } // METODO PARA INSERTAR
    
    
    // Método para modificar un usuario
    public Boolean modificarUsuario(int id, String nombre, String email, String password) throws SQLException {
    	
    	Boolean resultado = false;
        
        //final String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ? WHERE id = ?";
        final String sql = GestorQueries.obtenerQuerie("queryMODIFICAR");
    	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	     	
        	statement.setString(1, nombre);
        	statement.setString(2, email);
        	statement.setString(3, password);
            statement.setInt(4, id);
        	
            int resultSet = statement.executeUpdate();
            
            // Si todo OK devuelve 1
            if(resultSet == 1) {
            	resultado = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();           
        }
        
        return resultado;
    } // METODO PARA MODIFICAR
    
    
    // Método para borrar un usuario
    public Boolean borrarUsuario(int id, String nombre, String email, String password) throws SQLException {
    	
    	Boolean resultado = false;
        
        //final String sql = "DELETE FROM usuarios WHERE id = ?";
        final String sql = GestorQueries.obtenerQuerie("queryBORRAR");
    	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
        	
            int resultSet = statement.executeUpdate();
            
            // Si todo OK devuelve 1
            if(resultSet == 1) {
            	resultado = true;
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();           
        }
        
        return resultado;
    } // METODO PARA BORRAR
    
    
    // Método para hacer login con un usuario
    public Boolean loginUsuario(String email, String password) throws SQLException {
    	
    	Boolean respuesta = false;
        
        //final String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";        
        final String sql = GestorQueries.obtenerQuerie("queryLOGIN");
            	
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);
        	
            ResultSet resultSet = statement.executeQuery();           
            	
            respuesta = resultSet.next();           	
            
        } catch (SQLException e) {
            e.printStackTrace();           
        }
        
        System.out.println("RESULTADO LOGIN VALOR --> " + respuesta);
        return respuesta;
        
    } // METODO PARA LOGIN
    
}
