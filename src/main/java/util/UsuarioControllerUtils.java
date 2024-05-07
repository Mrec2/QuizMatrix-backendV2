package util;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.RespuestaUsuarioJson;
import models.Usuario;

public class UsuarioControllerUtils {

	public static void configuraResponse(HttpServletResponse response) {
    	
    	// Configurar encabezados CORS para permitir solicitudes desde cualquier origen
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        // Configurar el tipo de contenido de la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
    
    public static Usuario obtenerJson(HttpServletRequest request) throws IOException {
    	
        BufferedReader miDato = request.getReader();
		String miDatoFinal = miDato.readLine();
		System.out.println("Dato Usuario FINAL => " + miDatoFinal);
		
		Gson gsonEntrada = new Gson();
		
		return gsonEntrada.fromJson(miDatoFinal,Usuario.class);				
    }
    
    public static void devolverJson(String mensaje, Usuario objUsuario, HttpServletResponse response) throws IOException {
    	
    	RespuestaUsuarioJson respuestaJson = new RespuestaUsuarioJson(mensaje, objUsuario);
        String json = new Gson().toJson(respuestaJson);
        response.getWriter().write(json);
        
    }
	
}
