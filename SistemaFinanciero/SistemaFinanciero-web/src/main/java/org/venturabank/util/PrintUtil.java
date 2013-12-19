package org.venturabank.util;

import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

public class PrintUtil {

	public JasperReport getJasperReport(String url){
		JasperReport jr = null;
		try {
			jr = (JasperReport) JRLoader.loadObjectFromFile(url);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jr;
	}
	
	public JasperPrint getJasperPrint(JasperReport jr, Map<String,Object> parameters){
		JasperPrint jp = null;
		try {
			jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jp;
	}
	
	public void print() {
		           
        JasperReport jasperReport = getJasperReport(null);
        JasperPrint jasperPrint = getJasperPrint(jasperReport, null);
        saveJRPrint(jasperPrint, null);

        PrintRequestAttributeSet printRequestAttributeSet = getPrintRequest();
        PrintServiceAttributeSet printServiceAttributeSet = getPrintService();
        
		JRPrintServiceExporter exporter = new JRPrintServiceExporter();

		exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,"C:\\Users\\TOSHIBA\\git\\sistema-financiero\\SistemaFinanciero\\SistemaFinanciero-web\\src\\main\\java\\org\\ventura\\reports\\PrintServiceReport.jrprint");
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,printRequestAttributeSet);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,printServiceAttributeSet);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,Boolean.FALSE);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,Boolean.TRUE);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveJRPrint(JasperPrint jp, String url){
		try {
			JRSaver.saveObject(jp, url);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public PrintRequestAttributeSet getPrintRequest(){
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		
        printRequestAttributeSet.add(MediaSizeName.ISO_A7);

        return printRequestAttributeSet;
	}
	
	public PrintServiceAttributeSet getPrintService(){
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName("HP LaserJet 1020", null));
        
        return printServiceAttributeSet;
	}
}
