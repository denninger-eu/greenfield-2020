package eu.k5.greenfield.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
//needed jars: apache poi and it's dependencies
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class MainConvert {

	public static void main(String[] args) throws Exception {

		String docPath = "c:/data/x.docx";
		String pdfPath = "c:/data/WordDocument.pdf";

		InputStream in = new FileInputStream(new File(docPath));
		XWPFDocument document = new XWPFDocument(in);
		PdfOptions options = PdfOptions.create();
		OutputStream out = new FileOutputStream(new File(pdfPath));
		PdfConverter.getInstance().convert(document, out, options);

		//document.document.close();
		out.close();

	}
}
