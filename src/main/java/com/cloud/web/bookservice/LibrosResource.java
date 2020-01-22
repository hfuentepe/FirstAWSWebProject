package com.cloud.web.bookservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloud.web.bookservice.model.Libro;
import com.cloud.web.bookservice.services.LibrosDynamoDBService;

@Path("libros")
public class LibrosResource {
	private static final Log LOGGER = LogFactory.getLog(LibrosResource.class);
	private static LibrosDynamoDBService librosService = new LibrosDynamoDBService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLibros() {
		LOGGER.info("Devolviendo lista de libros");
		return Response.status(200).entity(librosService.getLibros()).build();
	}

	@GET
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLibroById(@PathParam("isbn") String isbn) {
		LOGGER.info("Devolviendo lista de libros");
		return Response.status(200).entity(librosService.getLibro(isbn)).build();
	}

	@POST
	public void addLibro(Libro libro) {
		librosService.addLibro(libro);
	}

	@PUT
	public void updateLibro(Libro libro) {
		librosService.updateLibro(libro);
	}

	@DELETE
	@Path("/{isbn}")
	public void deleteLibro(@PathParam("isbn") String isbn) {

		librosService.deleteLibro(isbn);
	}
}
