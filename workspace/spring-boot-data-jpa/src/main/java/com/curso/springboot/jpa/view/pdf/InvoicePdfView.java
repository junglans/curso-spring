package com.curso.springboot.jpa.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.curso.springboot.jpa.models.bean.InvoiceBean;
import com.curso.springboot.jpa.models.bean.InvoiceItemBean;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("invoices/detail")
/**
 * AbstractPdfView implementa la interfaz View
 * @author jf.jimenez
 *
 */
public class InvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InvoiceBean invoice = (InvoiceBean)model.get("invoice");
		
		PdfPTable clientDataTable = new PdfPTable(1);
		clientDataTable.setSpacingAfter(20);
		
		PdfPCell cell = new PdfPCell(new Phrase("Datos del Cliente"));
		cell.setBackgroundColor(new Color(184,218,255));
		cell.setPadding(8f);
		
		clientDataTable.addCell(cell);
		clientDataTable.addCell(invoice.getClient().getName() + " " + invoice.getClient().getSurname());
		clientDataTable.addCell(invoice.getClient().getEmail());
		
		PdfPTable invoiceDataTable = new PdfPTable(1);
		invoiceDataTable.setSpacingAfter(20);
		cell = new PdfPCell(new Phrase("Datos de la factura"));
		cell.setBackgroundColor(new Color(195,230,203));
		cell.setPadding(8f);
		invoiceDataTable.addCell(cell);
		invoiceDataTable.addCell("Folio: " + invoice.getId());
		invoiceDataTable.addCell("Descripción: " + invoice.getDescription());
		invoiceDataTable.addCell("Fecha: " + invoice.getCreationDate());
		
		document.add(clientDataTable);
		document.add(invoiceDataTable);
		
		// cuatro columnas
		PdfPTable detailDataTable = new PdfPTable(4);
		detailDataTable.setWidths(new float[] {3.5f, 1f,1f,1f});
		detailDataTable.addCell("Producto");
		detailDataTable.addCell("Precio");
		detailDataTable.addCell("Cantidad");
		detailDataTable.addCell("Total");
		
		for (InvoiceItemBean item: invoice.getItems()) {
			
			detailDataTable.addCell(item.getProduct().getName());
			detailDataTable.addCell(item.getProduct().getPrice().toString());
			
			cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
			detailDataTable.addCell(cell);
			
			detailDataTable.addCell(item.getTotalItem().toString());
			
		}
		
		PdfPCell footer = new PdfPCell(new Phrase("Total:"));
		footer.setColspan(3);
		footer.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		detailDataTable.addCell(footer);
		detailDataTable.addCell(invoice.getTotal().toString());
		
		document.add(detailDataTable);
		
	}

}
