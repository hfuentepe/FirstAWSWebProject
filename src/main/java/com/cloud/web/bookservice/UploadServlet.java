package com.cloud.web.bookservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Pagina embebida para subir ficheros
		response.setContentType("text/html; charset=US-ASCII");
		response.setCharacterEncoding("US-ASCII");
		PrintWriter writer = response.getWriter();
		writer.print("<html><head><title>Calculator</title></head><body>");
		writer.print("<form method='POST' enctype='multipart/form-data'><br>");
		writer.print("<input type='file' name='file'><input type='submit'>");
		writer.print("</form></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Subida de ficheros
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("No hay fichero!");
		}
		try {
			response.setContentType("text/plain; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("Processing upload:\n");
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : items) {
				uploadToS3(item);
				writer.printf("Fichero: '%s' del campo '%s' subido\n", item.getName(), item.getFieldName());
				writer.print("\n\n");
			}
		} catch (FileUploadException e) {
			throw new ServletException(e);
		}
	}

	private void uploadToS3(FileItem item) throws IOException {
		// Subida al S3
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().build();
		String bucketName = "hfuentepewebs3";
		s3client.putObject(new PutObjectRequest(bucketName, item.getName(), item.getInputStream(), new ObjectMetadata())
				.withGeneralProgressListener(progressEvent -> System.out
						.print((progressEvent.getBytesTransferred() * 100 / item.get().length) + "%")));
	}

}
