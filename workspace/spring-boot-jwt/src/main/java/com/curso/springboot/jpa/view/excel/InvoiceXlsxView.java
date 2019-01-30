package com.curso.springboot.jpa.view.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.curso.springboot.jpa.models.bean.InvoiceBean;
import com.curso.springboot.jpa.models.bean.InvoiceItemBean;

@Component("invoices/detail.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		InvoiceBean invoice = (InvoiceBean) model.get("invoice");
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		MessageSourceAccessor mensajes =  getMessageSourceAccessor();
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensajes.getMessage("text.invoice.view.client.data"));
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(invoice.getClient().getName() + " " + invoice.getClient().getSurname());
		
		row = sheet.createRow(2);
		cell= row.createCell(0);
		cell.setCellValue(invoice.getClient().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.invoice.view.invoice.product"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.invoice.view.invoice.price") + ": " +  invoice.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.invoice.view.invoice.quantity") + ": " + invoice.getDescription());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.invoice.view.invoice.total") + ": " + invoice.getCreationDate());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(mensajes.getMessage("text.invoice.view.invoice.product"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.invoice.view.invoice.price"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.invoice.view.invoice.quantity"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.invoice.view.invoice.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum = 10;
		
		for(InvoiceItemBean item: invoice.getItems()) {
			Row fila = sheet.createRow(rownum ++);
			cell = fila.createCell(0);
			cell.setCellValue(item.getProduct().getName());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProduct().getPrice());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getQuantity());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.getTotalItem());
			cell.setCellStyle(tbodyStyle);
		}
		
		Row filatotal = sheet.createRow(rownum);
		cell = filatotal.createCell(2);
		cell.setCellValue(mensajes.getMessage("text.invoice.view.invoice.total") + ": ");
		cell.setCellStyle(tbodyStyle);
		
		cell= filatotal.createCell(3);
		cell.setCellValue(invoice.getTotal());
		cell.setCellStyle(tbodyStyle);
		
	}

}
