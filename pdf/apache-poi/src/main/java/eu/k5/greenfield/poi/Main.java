package eu.k5.greenfield.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;

public class Main {
	public static void main(String[] args) throws IOException {
		// Blank Document
		InputStream fileReader = new FileInputStream(new File("empty.docx"));
		//XWPFDocument document = new XWPFDocument(fileReader);
		XWPFDocument emptyDocument = new XWPFDocument(fileReader);

		XWPFStyles styles = emptyDocument.getStyles();
		
		XWPFDocument document = new XWPFDocument();
		XWPFStyles newStyles = document.createStyles();
		
		XWPFStyle style = styles.getStyle("Normal");
		newStyles.setSpellingLanguage("English");

		newStyles.addStyle(style);
		
		
		title(document);

		convert(document);

		// Write the Document in file system
//		FileOutputStream out = new FileOutputStream(new File("createdocument.docx"));
//		document.write(out);
//		out.close();
		System.out.println("createdocument.docx written successully");
	}

	private static void title(XWPFDocument document) {
//		XWPFStyles styles = document.createStyles();
//		styles.setSpellingLanguage("English");
//		CTFonts def = CTFonts.Factory.newInstance();
//		styles.setDefaultFonts(def);
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleRun = title.createRun();
		titleRun.setText("Build Your REST API with Spring");
		titleRun.setColor("009933");
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);
	}

	private static void convert(XWPFDocument document) throws XWPFConverterException, IOException {
		String outputFile = "c:/data/TEST.pdf";
		File outFile = new File(outputFile);
		outFile.createNewFile();
		OutputStream out = new FileOutputStream(outFile);
		PdfOptions options = null;
		PdfConverter.getInstance().convert(document, out, options);
	}
}
