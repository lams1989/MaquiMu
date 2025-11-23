package com.xm.autenticacion.servicio.testdatabuilder;

import com.xm.autenticacion.modelo.entidad.Usuario;

public class UsuarioTestDataBuilder {

	private String idObjeto;
	private String nombre;
	private String nombreCompleto;
	private String correoElectronico;
	private String tokenDeAcceso;

	public UsuarioTestDataBuilder() {
		this.idObjeto = "91f643ca-45e7-435a-b166-2224b2685372";
		this.nombre = "SimpOpera01";
		this.nombreCompleto = "SimpOpera01";
		this.correoElectronico = "103552452@XM.COM.CO";
		this.tokenDeAcceso = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiIzZWE3OTNlZi02YWRjLTQzMmUtYWUyNi0yOGJmNjkxZDY2MzQiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vYzk4MGU0MTAtMGI1Yy00OGJjLWJkMWEtOGI5MWNhYmM4NGJjL3YyLjAiLCJpYXQiOjE2NzgzODkyMDYsIm5iZiI6MTY3ODM4OTIwNiwiZXhwIjoxNjc4MzkzMTA2LCJhaW8iOiJBVFFBeS84VEFBQUE4V1Q3Y3dsbVM2a3BMdkEzT3hqd3FJWlE1cks0SDdpQlkrbVEzSDVoWUdqcXVSZVhiL28zWm51RWNiRFJiMXZCIiwiZ3JvdXBzIjpbIk5vcm1hbGVzIl0sIm5hbWUiOiJTaW1wT3BlcmEwMSIsIm5vbmNlIjoiMThlMDJkOGUtM2E4Yi00MmFlLTg5MDctNzc5ZmMwZjM3NWE0Iiwib2lkIjoiOTFmNjQzY2EtNDVlNy00MzVhLWIxNjYtMjIyNGIyNjg1MzcyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiU2ltcE9wZXJhMDFAWE0uQ09NLkNPIiwicmgiOiIwLkFROEFFT1NBeVZ3THZFaTlHb3VSeXJ5RXZPLVRwejdjYWk1RHJpWW92MmtkWmpRUEFBYy4iLCJzdWIiOiJlakc3X1k1ME43ZDdWdmV1ZXl5aGxXWHBOYXE4N0k0OWNrM3RTZXhTUWNRIiwidGlkIjoiYzk4MGU0MTAtMGI1Yy00OGJjLWJkMWEtOGI5MWNhYmM4NGJjIiwidXRpIjoicHpocWVMOEFPa2UyQzZxcUI3WHpBQSIsInZlciI6IjIuMCJ9.on0K3VojyHOmNqzjxQ4mU4jHLnlQi3IXvul0rWhgsVhBeizGqQ3Z-a3eEKuD3ceEAZqgVhzjQFigIjXnsGGERxeiK0D1ejIO3ErZYVIm_NVv4YP0DkU7bzmZ_Oy4kYUu2stpfgopBthz4TPPTBih-wyXDWUWRNpw3lRZXalhkXq-EMFqmMsO3hFtjCyq_tj1K-QbaKkXVFfcwuDK9Ai8nLrsPd6jLB4vlrQHR_zLJ-7RAJURwvDhWBp_Y6pmwCYfzIGvkZ2O-6HKapBryaPzo_qSTELJmKcOci-Ra9eYkzmFZLzckVNaZoYXh36C6NYJ9VkhrN-9Gd56zvpge2Ah1g";
	}

	public UsuarioTestDataBuilder conIdObjeto(String idObjeto){
		this.idObjeto = idObjeto;
		return this;
	}

	public UsuarioTestDataBuilder conNombre(String nombre){
		this.nombre = nombre;
		return this;
	}

	public UsuarioTestDataBuilder conNombreCompleto(String nombreCompleto){
		this.nombreCompleto = nombreCompleto;
		return this;
	}

	public UsuarioTestDataBuilder conCorreoElectronico(String correoElectronico){
		this.correoElectronico = correoElectronico;
		return this;
	}

	public UsuarioTestDataBuilder conTokenDeAcceso(String tokenDeAcceso){
		this.tokenDeAcceso = tokenDeAcceso;
		return this;
	}

	public Usuario construir() {
		return new Usuario(idObjeto, nombre, nombreCompleto, correoElectronico, tokenDeAcceso);
	}

}
