package com.cloud.web.bookservice.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cloud.web.bookservice.model.Libro;

public class LibrosDynamoDBService {
	private static AmazonDynamoDB client;
	private static DynamoDBMapper mapper;
	static {
		client = AmazonDynamoDBClientBuilder.standard().build();
		mapper = new DynamoDBMapper(client);
	}

	public Libro getLibro(String isbn) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":isbn", new AttributeValue().withS(isbn));
		scanExpression.withFilterExpression("isbn = :isbn").withExpressionAttributeValues(values);
		// scanExpression.addExpressionAttributeNamesEntry("isbn", isbn);
		PaginatedScanList<Libro> libros = mapper.scan(Libro.class, scanExpression);
		return libros.get(0);
	}

	public Collection<Libro> getLibros() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		PaginatedScanList<Libro> libros = mapper.scan(Libro.class, scanExpression);
		return libros;
	}

	public void addLibro(Libro libro) {
		mapper.save(libro);
	}

	public void updateLibro(Libro libro) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":isbn", new AttributeValue().withS(libro.getIsbn()));
		scanExpression.withFilterExpression("isbn = :isbn").withExpressionAttributeValues(values);
		PaginatedScanList<Libro> libros = mapper.scan(Libro.class, scanExpression);
		Libro load = libros.get(0);
		if (load != null) {
			load.setAutor(libro.getAutor());
			load.setCategorias(libro.getCategorias());
			load.setTitulo(libro.getTitulo());
			mapper.save(libro);
		}
	}

	public void deleteLibro(String isbn) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
		values.put(":isbn", new AttributeValue().withS(isbn));
		scanExpression.withFilterExpression("isbn = :isbn").withExpressionAttributeValues(values);
		PaginatedScanList<Libro> libros = mapper.scan(Libro.class, scanExpression);
		if (libros.get(0) != null) {
			mapper.delete(libros.get(0));
		}
	}
}
