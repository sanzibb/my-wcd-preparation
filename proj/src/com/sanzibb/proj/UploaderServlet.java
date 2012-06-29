package com.sanzibb.proj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet(urlPatterns = "/fileUpload")
@MultipartConfig
public class UploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploaderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			String contentDisposition = part.getHeader("content-disposition");
			String fileName= getFileName(contentDisposition);
			System.out.println("contentDisposition: " + contentDisposition);
			System.out.println("fileName: " + fileName);			
			File dir=new File("E:\\JAVA_EE6_PROJECTS\\downloads");
			FileOutputStream outStream=new FileOutputStream(new File(dir,fileName));
			InputStream inStream = part.getInputStream();
			byte[] bytes=new byte[1024];
			int bytesRead=-1;
			while(true){
				bytesRead=inStream.read(bytes);
				if(bytesRead==-1){
					break;
				}else{
					outStream.write(bytes, 0, bytesRead);
				}				
			}
			inStream.close();
			outStream.close();	
			System.out.println(fileName+" has been uploaded to "+dir.getAbsolutePath());
		
		}
	}

	private String getFileName(String contentDisposition) {
		String[] arr = contentDisposition.split(";");
		for (String part : arr) {
           if(part.trim().startsWith("filename")){
        	   String filePath=part.substring(part.indexOf("=")+1).trim().replaceAll("\"", "");  
        	   System.out.println(filePath);
        	   if(filePath.contains("/")){
        		  return filePath.substring(filePath.lastIndexOf("/")+1); 
        	   }else if(filePath.contains("\\")){
         		  return filePath.substring(filePath.lastIndexOf("\\")+1); 
         	   }else{
         		   return filePath;
         	   }
           }
		}		
		return null;

	}

}
