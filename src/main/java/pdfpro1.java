import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;

import java.io.*;

/**
 * User: Gus - gustavo.santos@jbaysolutions.com - http://gmsa.github.io/ - http://www.jbaysolutions.com
 * Date: 27-10-2015
 * Time: 14:52
 *
 * A sample class to add Image to PDF file using IText. Full details in http://blog.jbaysolutions.com/2015/10/27/adding-image-to-pdf-java-itext
 */
public class pdfpro1 {
    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        addImageToPdf(workingDir + "/src/main/resources/201128.pdf", workingDir + "/target/output.pdf", workingDir + "/src/main/resources/dynaimc.jpg");
    }
    private static Image getWatermarkedImage(PdfDocument pdfDoc, Image img, String watermark) {
        float width = img.getImageScaledWidth();
        float height = img.getImageScaledHeight();
        PdfFormXObject template = new PdfFormXObject(new Rectangle(width, height));
        new Canvas(template, pdfDoc)
                .add(img)
                .setBackgroundColor(DeviceGray.WHITE)

                .setFontColor(DeviceGray.BLACK)
                .showTextAligned(watermark, width / 2, height / 2, TextAlignment.CENTER, (float) Math.PI / 6)
                .close();
        return new Image(template);
    }
    public static void addImageToPdf(String srcPdf, String destPdf, String imagePath) throws IOException, DocumentException {

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter("data/test.pdf"));
        Document doc = new Document(pdfDoc);
        Image image = getWatermarkedImage(pdfDoc, new Image(ImageDataFactory.create(imagePath)), "Bruno");

        File imageFile = new File(imagePath);

        InputStream is = new FileInputStream(imageFile);
        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        int i;
        byte[] data = new byte[1024];
        while ((i = is.read(data, 0, data.length)) != -1) {
            bis.write(data, 0, i);
        }
        bis.flush();
        is.close();
        Image img = new Image(ImageDataFactory.create(bis.toByteArray()));

        doc.add(img);
        doc.close();

    }
}