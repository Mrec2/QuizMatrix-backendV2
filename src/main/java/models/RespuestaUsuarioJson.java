package models;


public class RespuestaUsuarioJson {

	private String mensaje;
	private Usuario usuario;
	
	
	public RespuestaUsuarioJson(String mensaje, Usuario usuario) {
		
		this.mensaje = mensaje;
		this.usuario = usuario;
		
	}
	
	
	// GETTERS y SETTERS
	
    //Atributo mensaje
    public String getMensaje() {
    	return mensaje;
    }
    
    public void setNombre(String mensaje) {
    	this.mensaje = mensaje;
    }
    
    
    //Atributo listaUsuarios
    public Usuario getUsuario() {
    	return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
    	this.usuario = usuario;
    }
    
    
}
