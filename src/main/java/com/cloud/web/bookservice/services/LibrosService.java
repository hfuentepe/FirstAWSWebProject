package com.cloud.web.bookservice.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cloud.web.bookservice.model.Libro;

public class LibrosService {

	private static Map<String, Libro> biblioteca;
	static {
		biblioteca = new HashMap<String, Libro>();
		Libro l1 = new Libro();
		l1.setIsbn("111");
		l1.setTitulo("ESDLA");
		l1.setAutor("JRR Tolkien");
		l1.setCategorias(Stream.of("Aventuras", "Accion").collect(Collectors.toCollection(HashSet::new)));
		Libro l2 = new Libro();
		l2.setIsbn("1112");
		l2.setTitulo("ESDLA 2");
		l2.setAutor("JRR Tolkien");
		l2.setCategorias(Stream.of("Aventuras", "Accion").collect(Collectors.toCollection(HashSet::new)));
		biblioteca.put(l1.getIsbn(), l1);
		biblioteca.put(l2.getIsbn(), l2);
	}

	public Libro getLibro(String isbn) {
		return biblioteca.get(isbn);
	}

	public Collection<Libro> getLibros() {
		return biblioteca.values();
	}

	public void addLibro(Libro libro) {
		if (!biblioteca.containsKey(libro.getIsbn())) {
			biblioteca.put(libro.getIsbn(), libro);
		}
	}

	public void updateLibro(Libro libro) {
		if (biblioteca.containsKey(libro.getIsbn())) {
			biblioteca.put(libro.getIsbn(), libro);
		}
	}

	public void deleteLibro(String isbn) {
		biblioteca.remove(isbn);
	}

}
