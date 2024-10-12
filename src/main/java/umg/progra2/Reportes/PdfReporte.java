package umg.progra2.Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import umg.progra2.DataBase.Model.ProductoModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class PdfReporte {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Edgar Guillermo Chinchilla Chinchilla 0905-23-13243", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addProductTable(Document document, List<ProductoModel> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRows(table, productos); // Usar el método addRows
        document.add(table);
    }

    private void addRows(PdfPTable table, List<ProductoModel> productos) {
        for (ProductoModel producto : productos) {
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            DecimalFormat df = new DecimalFormat("#.00");
            String cantidadFormateada = df.format(producto.getExistencia() * producto.getPrecio());
            table.addCell(new Phrase(cantidadFormateada, NORMAL_FONT));
        }
    }

    private void addRowsGroup(PdfPTable table, List<ProductoModel> productos) {
        String currentOrigen = null;
        double groupTotalPrecio = 0.0;
        int groupTotalExistencia = 0;
        double Precioindividual = 0.0;

        DecimalFormat df = new DecimalFormat("#.00");

        BaseColor greenColor = BaseColor.LIGHT_GRAY;
        Font boldFont = new Font(NORMAL_FONT.getFamily(), NORMAL_FONT.getSize(), Font.BOLD);

        for (ProductoModel producto : productos) {
            if (currentOrigen == null) {
                currentOrigen = producto.getOrigen();
                PdfPCell groupCell = new PdfPCell(new Phrase("Grupo: " + currentOrigen, NORMAL_FONT));
                groupCell.setColspan(6);
                table.addCell(groupCell);

            } else if (!producto.getOrigen().equals(currentOrigen)) {

                PdfPCell totalCellLabel = new PdfPCell(new Phrase("Grupo " + currentOrigen, boldFont));
                totalCellLabel.setColspan(4);
                totalCellLabel.setBackgroundColor(greenColor);
                table.addCell(totalCellLabel);

                PdfPCell totalExistenciaCell = new PdfPCell(new Phrase(String.valueOf(groupTotalExistencia), boldFont));
                totalExistenciaCell.setBackgroundColor(greenColor);
                table.addCell(totalExistenciaCell);

                PdfPCell totalPrecioCell = new PdfPCell(new Phrase(df.format(groupTotalPrecio), boldFont));
                totalPrecioCell.setBackgroundColor(greenColor);
                table.addCell(totalPrecioCell);

                groupTotalPrecio = 0.0;
                groupTotalExistencia = 0;

                currentOrigen = producto.getOrigen();

                PdfPCell groupCell = new PdfPCell(new Phrase("Grupo: " + currentOrigen, NORMAL_FONT));
                groupCell.setColspan(6);
                table.addCell(groupCell);
            }

            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));

            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));


            Precioindividual = producto.getExistencia() * producto.getPrecio();
            table.addCell(new Phrase(df.format(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            String cantidadFormateada = df.format(Precioindividual);
            table.addCell(new Phrase(cantidadFormateada, NORMAL_FONT));


            groupTotalPrecio += Precioindividual;
            groupTotalExistencia += producto.getExistencia();
        }


        if (currentOrigen != null) {

            PdfPCell totalCellLabel = new PdfPCell(new Phrase("Total Grupo " + currentOrigen, boldFont));
            totalCellLabel.setColspan(4);
            totalCellLabel.setBackgroundColor(greenColor);
            table.addCell(totalCellLabel);

            PdfPCell totalExistenciaCell = new PdfPCell(new Phrase(String.valueOf(groupTotalExistencia), boldFont));
            totalExistenciaCell.setBackgroundColor(greenColor);
            table.addCell(totalExistenciaCell);

            PdfPCell totalPrecioCell = new PdfPCell(new Phrase(df.format(groupTotalPrecio), boldFont));
            totalPrecioCell.setBackgroundColor(greenColor);
            table.addCell(totalPrecioCell);
        }
    }

    public void generateProductReport(List<ProductoModel> productos, String outputPath, boolean agrupar) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        addTitle(document);

        // Llamar al método adecuado según el valor de "agrupar"
        if (agrupar) {
            addProductTableGrouped(document, productos);
        } else {
            addProductTable(document, productos);
        }

        document.close();
    }

    private void addProductTableGrouped(Document document, List<ProductoModel> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRowsGroup(table, productos); // Usar el método addRowsGroup
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        String[] columnTitles = {"ID", "Descripción", "Origen", "Precio", "Existencias", "Precio Total"};
        for (String columnTitle : columnTitles) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.GREEN);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
            table.addCell(header);
        }
    }


}
