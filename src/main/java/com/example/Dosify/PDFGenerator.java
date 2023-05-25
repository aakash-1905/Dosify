package com.example.Dosify;

import com.example.Dosify.model.Appointment;
import com.example.Dosify.model.Certificate;
import com.example.Dosify.repository.CertificateRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("pdfGenerator")
public class PDFGenerator {

    @Value("E:/PdfReportRepo/")
    private String pdfDir;

    @Value("Appointment Certificate")
    private String reportFileName;

    @Value("${reportFileNameDateFormat}")
    private String reportFileNameDateFormat;

    @Value("${localDateFormat}")
    private String localDateFormat;

//   name,age,dose1,dose2,date,AppointmentNo
    @Value("6")
    private int noOfColumns;

    @Value("${table.columnNames}")
    private List<String> columnNames;

    @Autowired
    CertificateRepository certificaterepository;

    private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

    public String generatePdfReport(Appointment app) {

        Document document = new Document();

        try {
            String address=getPdfNameWithDate();
            PdfWriter.getInstance(document, new FileOutputStream(address));

            document.open();
            addDocTitle(document);
            createTable(document,noOfColumns,app);
//            addFooter(document);
            document.close();
              return address;
        } catch (FileNotFoundException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     return "";
    }

    private void addDocTitle(Document document) throws DocumentException {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph(reportFileName, COURIER));
        p1.setAlignment(Element.ALIGN_CENTER);
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph("Certificate generated on " + localDateString, COURIER_SMALL));

        document.add(p1);

    }

    private void createTable(Document document, int noOfColumns,Appointment app) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 3);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(noOfColumns);

        for(int i=0; i<noOfColumns; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        getDbData(table,app);
        document.add(table);
    }

    private void getDbData(PdfPTable table, Appointment app) {

        Optional<Certificate> cer = certificaterepository.findByAppointmentNo(app.getAppointmentNo());
              if(cer.isEmpty())
                   return;
              Certificate list=cer.get();
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(list.getAppointmentNo());

            table.addCell(list.getUser().getName());

            table.addCell(""+list.getUser().getAge());

            if(list.getUser().isDose1Taken()){
                table.addCell("Yes");
            }
            else{
                table.addCell("No");
            }

            if(list.getUser().isDose2Taken()){
                table.addCell("Yes");
            }
            else{
                table.addCell("No");
            }
            table.addCell(""+list.getDateOfAppointment());
    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String getPdfNameWithDate() {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
        LocalTime myObj = LocalTime.now();
        return pdfDir+reportFileName+"-"+UUID.randomUUID()+""+".pdf";
    }
}