import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.*;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.swing.text.html.parser.DocumentParser;
import java.awt.*;
import java.io.File;

public class BackgroundImage {
    public static final String DEST = "./target/images/background_image.pdf";

    public static final String IMAGE = "./src/main/resources/img/dynaimc.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new BackgroundImage().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest, new WriterProperties().setPdfVersion(PdfVersion.PDF_1_4)));
        ImageData image = ImageDataFactory.create(IMAGE);
        float width = image.getWidth();
        float height = image.getHeight();
        PageSize pageSize = new PageSize(width / 2, height / 2);
        pdfDoc.setDefaultPageSize(pageSize);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImageWithTransformationMatrix(image, width, 0, 0, height, 0, -height / 2, false);

        canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImageWithTransformationMatrix(image, width, 0, 0, height, 0, 0, false);

        canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImageWithTransformationMatrix(image, width, 0, 0, height, -width / 2, -height / 2, false);

        canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImageWithTransformationMatrix(image, width, 0, 0, height, -width / 2, 0, false);

        pdfDoc.close();
    }
}