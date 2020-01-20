package com.cloud.web.bookservice.model;

import java.util.Set;

public class Libro {

	private String isbn;
	private String titulo;
	private String autor;
	private String ignoroElCampo;
	private Set<String> categorias;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Set<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<String> categorias) {
		this.categorias = categorias;
	}

	public String getIgnoroElCampo() {
		return ignoroElCampo;
	}

	public void setIgnoroElCampo(String ignoroElCampo) {
		this.ignoroElCampo = ignoroElCampo;
	}
}
