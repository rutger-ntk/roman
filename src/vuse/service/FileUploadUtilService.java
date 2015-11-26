package vuse.service;

import javax.servlet.http.Part;

public class FileUploadUtilService {
	
	/**
	 * Extracts the file name from the Part object
	 * 
	 * @param Part instance holding one part/piece of the multipart
	 * @exception None 
	 * @return String value holding the name of the file to be uploaded
	 */
	public String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
	
	// More file upload or other business logic goes here such as:

}
