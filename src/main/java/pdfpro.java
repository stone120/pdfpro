
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 * User: Gus - gustavo.santos@jbaysolutions.com - http://gmsa.github.io/ - http://www.jbaysolutions.com
 * Date: 27-10-2015
 * Time: 14:52
 *
 * A sample class to add Image to PDF file using IText. Full details in http://blog.jbaysolutions.com/2015/10/27/adding-image-to-pdf-java-itext
 */
public class pdfpro {

    public static final String IMAGE_SRC = "./data/dynaimc.jpg";
    public static final String IMAGE_DEST = "./data/dynaimc_new.png";
    public static final String PDF_SRC = "./data/201128.pdf";
    public static final String PDF_DEST = "./data/output.pdf";


    public static void main(String[] args) throws Exception {
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        //repairImage(IMAGE_SRC);
        addImageToPdf(PDF_SRC, PDF_DEST, IMAGE_SRC);
    }

    private static void repairImage (String imagePath)  throws  IOException{

        BufferedImage src = ImageIO.read(new File(imagePath));

        //final int color = src.getRGB(0, 0);
        //final java.awt.Image imageWithTransparency = makeColorTransparent(src, new Color(color));

        BufferedImage image1 = toBufferedImage(src);
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();

        try {
            //ImageIO.write(image1, "PNG", new File(IMAGE_DEST)); // ignore returned boolean
            ImageIO.write(image1, "PNG", byteArrayOutputStream);

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

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
//    public static java.awt.Image makeColorTransparent(final BufferedImage im, final Color color) {
//        final ImageFilter filter = new RGBImageFilter() {
//            // the color we are looking for (white)... Alpha bits are set to opaque
//            public int markerRGB = color.getRGB() | 0xFFFFFFFF;
//
//            public final int filterRGB(final int x, final int y, final int rgb) {
//                if ((rgb | 0xFF000000) == markerRGB) {
//                    // Mark the alpha bits as zero - transparent
//                    return 0x00FFFFFF & rgb;
//                } else {
//                    // nothing to do
//                    return rgb;
//                }
//            }
//        };
//
//        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
//        return Toolkit.getDefaultToolkit().createImage(ip);
//    }

    public static void addImageToPdf(String srcPdf, String destPdf, String imagePath) throws IOException, DocumentException {

        BufferedImage src = ImageIO.read(new File(imagePath));
        BufferedImage image1 = toBufferedImage(src);
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        ImageIO.write(image1, "PNG", byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();

        PdfReader reader = new PdfReader(srcPdf);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPdf));
        PdfContentByte content = stamper.getOverContent(1);

        Rectangle mediabox = reader.getPageSize(1);

        Image image = Image.getInstance(bytes);

        System.out.print(mediabox.getWidth());
        System.out.print(mediabox.getHeight());
        image.scaleToFit(mediabox.getWidth(), mediabox.getHeight());


        image.setAbsolutePosition(10, 0);
        content.addImage(image);
        

        stamper.close();

    }
}