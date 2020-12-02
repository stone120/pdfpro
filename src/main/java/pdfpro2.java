import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Gus - gustavo.santos@jbaysolutions.com - http://gmsa.github.io/ - http://www.jbaysolutions.com
 * Date: 27-10-2015
 * Time: 14:52
 *
 * A sample class to add Image to PDF file using IText. Full details in http://blog.jbaysolutions.com/2015/10/27/adding-image-to-pdf-java-itext
 */
public class pdfpro2 {

    public static final String IMAGE_SRC = "./src/main/resources/dynaimc.jpg";
    public static final String PDF_SRC = "./src/main/resources/201128.pdf";
    public static final String PDF_DEST = "./target/output.pdf";


    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        //repairImage(IMAGE_SRC);
        addImageToPdf(PDF_SRC, PDF_DEST, IMAGE_SRC);
    }


    private static BufferedImage toBufferedImage(java.awt.Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_ARGB; // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.setBackground(new Color(0f,0f,0f,0f));

        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    public static void addImageToPdf(String srcPdf, String destPdf, String imagePath) throws IOException, DocumentException {

        // read image and transfer to PNG bytes
        BufferedImage src = ImageIO.read(new File(imagePath));
        BufferedImage image1 = toBufferedImage(src);
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        ImageIO.write(image1, "PNG", byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();

        // read pdf template
        PdfReader reader = new PdfReader(srcPdf);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPdf));
        PdfContentByte content = stamper.getOverContent(1);

        Rectangle mediabox = reader.getPageSize(1);
        System.out.print(mediabox.getWidth());
        System.out.print(mediabox.getHeight());

        // create Image object with PNG bytes
        Image image = Image.getInstance(bytes);
        image.scaleToFit(mediabox.getWidth(), mediabox.getHeight());
        image.setAbsolutePosition(10, 0);
        content.addImage(image);

        stamper.close();

    }
}