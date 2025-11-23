package com.xm.autenticacion.controlador.consulta.testdatabuilder;

import com.xm.autenticacion.consulta.ComandoAutenticacion;

public class ComandoAutenticacionTestDataBuilder {
	private String idObjeto;
	private String nombre;
	private String nombreCompleto;
	private String correoElectronico;
	private String tokenDeAcceso;

	public ComandoAutenticacionTestDataBuilder() {
		this.idObjeto = "c17948b8-2f03-4795-848f-82f52011275c";
		this.nombre = "SimpOpera06";
		this.nombreCompleto = "SimpOpera06";
		this.correoElectronico = "SimpOpera06@XM.COM.CO";
		this.tokenDeAcceso = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiIzZWE3OTNlZi02YWRjLTQzMmUtYWUyNi0yOGJmNjkxZDY2MzQiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vYzk4MGU0MTAtMGI1Yy00OGJjLWJkMWEtOGI5MWNhYmM4NGJjL3YyLjAiLCJpYXQiOjE2Nzg3NzI0MjksIm5iZiI6MTY3ODc3MjQyOSwiZXhwIjoxNjc4Nzc2MzI5LCJhaW8iOiJFMlpnWU5oNFkrMkhwaW1sWnpKeW5salAzalJsQlFBPSIsImF6cCI6IjNlYTc5M2VmLTZhZGMtNDMyZS1hZTI2LTI4YmY2OTFkNjYzNCIsImF6cGFjciI6IjEiLCJvaWQiOiJiZmY1NDU4OC1iODRhLTQ4YTctOTQ1ZS1hYmFlNzRkMjM3YTAiLCJyaCI6IjAuQVE4QUVPU0F5VndMdkVpOUdvdVJ5cnlFdk8tVHB6N2NhaTVEcmlZb3Yya2RaalFQQUFBLiIsInN1YiI6ImJmZjU0NTg4LWI4NGEtNDhhNy05NDVlLWFiYWU3NGQyMzdhMCIsInRpZCI6ImM5ODBlNDEwLTBiNWMtNDhiYy1iZDFhLThiOTFjYWJjODRiYyIsInV0aSI6IjRLdGNmWlp2aTB1SmdYWGc3NGdUQVEiLCJ2ZXIiOiIyLjAifQ.alGb0vPTX2szUNvJOEJWT1-bws3x3tvzdli0FOH8roV0sDifqC93QtE7zRLhsrvg6sEYhRDII0hQTdtvbPyRfZwFlndahivPSlRBGEixKXkmjT-u4WLlavcTkTEPUkfD-F1Ar1OFDyX6XD3lXHecnd5GOEZbSQ2Mq6rTIPd66pdwa9ZY_WOnGzSno_HULgoM6LhlEDTlbdKMc6lJdit6KdnDWmScJAbVkAdm6RyzAHI2qdxdE6swyI-t0F116WlvfOHw2UK_Sf42FYipBGTBJYFBa9F-TOjQlxWezGC_rkJ3Aoq1ttw1u0tUR41H1C9K9cPfFin3mPEDLOci0HZGUg";
	}
	public ComandoAutenticacionTestDataBuilder conIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
		return this;
	}
	public ComandoAutenticacionTestDataBuilder conTokenDeAcceso(String tokenDeAcceso) {
		this.tokenDeAcceso = tokenDeAcceso;
		return this;
	}
	public ComandoAutenticacionTestDataBuilder conNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public ComandoAutenticacionTestDataBuilder conNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
		return this;
	}
	public ComandoAutenticacionTestDataBuilder conCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
		return this;
	}
	public ComandoAutenticacion construir() {
		return new ComandoAutenticacion(idObjeto, nombre, nombreCompleto, correoElectronico, tokenDeAcceso);
	}

}
