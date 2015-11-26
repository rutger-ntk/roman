package vuse.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import vuse.service.FileUploadUtilService;
import vuse.service.ImageSnapListener;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;

/**
 * Servlet implementation class FileUploadUtil
 */
@WebServlet("/FileUploadUtil")
@MultipartConfig(location="/home/rnikolov/LunaWorkProjects/VUSETool/WebContent/", 
								fileSizeThreshold=1024*1024*2, maxFileSize=1024*1024*10, 
								maxRequestSize=1024*1024*50)
public class FileUploadUtilController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadUtilController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * 													HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * 													HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		boolean isEmpty = true;		//are all parts empty?
		boolean noFile = false;   //was a file chosen?
		
		String fileName = null;  // uploaded file's name
	  
		//Web App's Context Path
		String contextPath = getServletContext().getInitParameter("contextPath");
		
		FileUploadUtilService fileUploadUtilService = new FileUploadUtilService();
		
	  // Extract all parts from the multipart/form-data of the index.jsp
		for (Part part : request.getParts()) {
			fileName = fileUploadUtilService.extractFileName(part);				
			
			// Checks to see if all parts are empty, 
			if(part.getSize() != 0) {
				isEmpty = false;
			} 
			
		  // Processing the user input using the file upload option
			if(!fileName.equalsIgnoreCase("")) {		
				part.write(fileName);								
				} 
			else {
						noFile = true;
				}
			}
		
		HttpSession session = request.getSession();   // get the current session
		
	  // If all parts are empty redirect back to the same .jsp(index.jsp)
		// till user provides an actual file for processing, continue otherwise
		if (isEmpty || noFile) {
			request.setAttribute("error", "error");
			request.getRequestDispatcher("index.jsp").forward(request, response);			
		}		
		else {
			// New video is 
			ImageSnapListener.setmLastPtsWrite();
			// Send the uploaded video for media processing 
			IMediaReader mediaReader = 
					ToolFactory.makeReader(contextPath + fileName);

      // stipulate that we want BufferedImages created in BGR 24bit color space
      mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
      
      mediaReader.addListener(new ImageSnapListener());

      // read out the contents of the media file and
      // dispatch events to the attached listener
      while (mediaReader.readPacket() == null) ;
            
      List<String> screenshotsList = ImageSnapListener.getScreenshotsList();
      ImageSnapListener.setScreenshotsList();
      
      // sets all data for preview
			session.setAttribute("videoFile", fileName);
			session.setAttribute("screenshots", screenshotsList);
			session.setAttribute("listSize", screenshotsList.size());
			request.getRequestDispatcher("screenshots.jsp").forward(request, response);
		}
	}
}
