package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
//import models.RespuestaUsuarioJson;
import models.Usuario;
import util.ConexionJdbc;
import util.UsuarioControllerUtils;

@WebServlet("/usuarios")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UsuarioController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	UsuarioControllerUtils.configuraResponse(response);
        Connection conexion = null;
        
        try {
            conexion = ConexionJdbc.obtenerConexion();

            // Crear el objeto UsuarioDAO y obtener los usuarios
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            List<Usuario> listaUsuarios = usuarioDao.getUsuarios();

            // Convertir la lista de usuarios a JSON y enviarla como respuesta
            String json = new Gson().toJson(listaUsuarios);
            response.getWriter().write(json);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    } // GET
    
    
    protected void doGetID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	UsuarioControllerUtils.configuraResponse(response);
        Connection conexion = null;
        
        try {
            conexion = ConexionJdbc.obtenerConexion();
            
            // DA ERROR LA PRIMERA VEZ POR SER GET. OJO, TENERLO EN CUENTA.
            ////////////////// Obtengo el JSON con el usuario a Consultar
            BufferedReader miDato = request.getReader();
    		String miDatoFinal = miDato.readLine();
    		System.out.println("Dato Usuario FINAL => " + miDatoFinal);
    		
    		Gson gsonEntrada = new Gson();
    		Usuario objUsuario = gsonEntrada.fromJson(miDatoFinal,Usuario.class);
    		
    		System.out.println("ID --> " + objUsuario.getId());
    		///////////////// FIN Obtengo el JSON con el usuario a Consultar
    		
            
            // Crear el objeto UsuarioDAO y obtener el usuario
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            List<Usuario> listaUsuarios = usuarioDao.getUsuarioId(objUsuario.getId());

            // Convertir la lista de usuarios a JSON y enviarla como respuesta
            String json = new Gson().toJson(listaUsuarios);
            response.getWriter().write(json);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    } // GET ID
    
    
    protected void doGetAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UsuarioControllerUtils.configuraResponse(response);
        Connection conexion = null;
        
        try {
            conexion = ConexionJdbc.obtenerConexion();

            // Crear el objeto UsuarioDAO y obtener los usuarios
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            List<Usuario> listaUsuarios = usuarioDao.getUsuarios();

            // Convertir la lista de usuarios a JSON y enviarla como respuesta
            String json = new Gson().toJson(listaUsuarios);
            response.getWriter().write(json);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
				
    } // GET ALL
    
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean realizada = false;
        String mensaje = "NO OK";
		
        UsuarioControllerUtils.configuraResponse(response);
		Connection conexion = null;
		
        try {
            conexion = ConexionJdbc.obtenerConexion();
           
            // Obtengo el JSON con el usuario
    		Usuario objUsuario = UsuarioControllerUtils.obtenerJson(request);  		
            
            // Crear el objeto UsuarioDAO y obtener los usuarios
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            // Llamar al metodo de Modificar usuario
            realizada = usuarioDao.modificarUsuario(objUsuario.getId(), objUsuario.getNombre(), objUsuario.getEmail(), objUsuario.getPassword());
                     
            
            //Si devuelve true mensaje de OK, sino No OK.            
            if(realizada) { mensaje = "OK"; }           
            
            // DEVOLVER EL JSON
            UsuarioControllerUtils.devolverJson(mensaje, objUsuario, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
						
	} // PUT
    
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean realizada = false;
        String mensaje = "NO OK";
		
        UsuarioControllerUtils.configuraResponse(response);
		Connection conexion = null;
		
        try {
            conexion = ConexionJdbc.obtenerConexion();
            
            // Obtengo el JSON con el usuario
    		Usuario objUsuario = UsuarioControllerUtils.obtenerJson(request);   		
    		
            // Crear el objeto UsuarioDAO y obtener los usuarios
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            // Llamar al metodo de Borrar usuario
            realizada = usuarioDao.borrarUsuario(objUsuario.getId(), objUsuario.getNombre(), objUsuario.getEmail(), objUsuario.getPassword());
                                   

            //Si devuleve true mensaje de OK, sino No OK.            
            if(realizada) { mensaje = "OK"; }
            
            // DEVOLVER EL JSON
            UsuarioControllerUtils.devolverJson(mensaje, objUsuario, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
				
	} // DELETE
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Boolean realizada = false;
        String mensaje = "NO OK";
        
        UsuarioControllerUtils.configuraResponse(response);
		Connection conexion = null;
		
        try {
            conexion = ConexionJdbc.obtenerConexion();
            
            // Obtengo el JSON con el usuario
    		Usuario objUsuario = UsuarioControllerUtils.obtenerJson(request); 		
   
            // Crear el objeto UsuarioDAO y obtener los usuarios
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);                  
            
            // Miro si quiero crear un usuario o hacer Login
            if(objUsuario.getId() == 0) {
            	
            	// Llamar al metodo de Insertar usuario          	
                realizada = usuarioDao.insertarUsuario(objUsuario.getNombre(), objUsuario.getEmail(), objUsuario.getPassword());
                
            } else {
            	//System.out.println("ENTRA EN LOGIN");            	
            	realizada = usuarioDao.loginUsuario(objUsuario.getEmail(), objUsuario.getPassword());          	
            }                   
            
            //Si devuleve true mensaje de OK, sino No OK.            
            if(realizada) { mensaje = "OK"; }           
            
            // DEVOLVER EL JSON
            UsuarioControllerUtils.devolverJson(mensaje, objUsuario, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al conectar con la base de datos");
            
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		
    } // POST
    
    
} // FIN DE LA CLASE
