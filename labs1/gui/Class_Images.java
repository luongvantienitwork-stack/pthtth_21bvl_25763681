// Ghi file ảnh
package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Class_Images {
    public static byte[] readFile(File path) {
        try (FileInputStream fis = new FileInputStream(path);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Class_Images.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void saveFile(File path, String tfile, byte[] bfile) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bfile));
            ImageIO.write(img, tfile, path);
        } catch (IOException ex) {
            Logger.getLogger(Class_Images.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        String dir = "/home/van-tien/eclipse-workspace/pthtth/labs1/gui/";

        File input = new File(dir + "image.png");
        byte[] bfile = readFile(input);

        if (bfile != null) {
            System.out.println("Đọc ảnh thành công, kích thước: " + bfile.length + " bytes");

            File output = new File(dir + "image_copy.png");
            saveFile(output, "png", bfile);
            
            System.out.println("Ghi ảnh copy thành công!");
        } else {
            System.err.println("Không đọc được file ảnh!");
        }
    }
}