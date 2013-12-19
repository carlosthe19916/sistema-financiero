package org.ventura.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrintServlet
 */
@WebServlet("/PrintServlet")
public class PrintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void myServletMethod(HttpServletRequest request, HttpServletResponse response) throws IOException{
        /*JasperReport jasperReport = null;
        JasperDesign jasperDesign = null;
        Map parameters = new HashMap();
        
        String path = getServletContext().getRealPath("/WEB-INF/");
        jasperDesign = JRXmlLoader.load(path+"/relative/path/to/MyReport.jrxml");
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters, **new DataSourceOfYourPreference**);                            
        OutputStream outStream = response.getOutputStream();
        response.setHeader("Content-Disposition","inline, filename=myReport.pdf");
        response.setContentType("application/pdf");
        response.setContentLength(byteStream.length);
        outStream.write(byteStream,0,byteStream.length);    
*/
}

}
