import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
    public boolean copyFile(String source, String dest) throws FileNotFoundException, IOException {
        File sourceFile = new File(source);
        File destFile = new File(dest);
        if (sourceFile.exists()) {
            FileOutputStream fos;
            try (FileInputStream fis = new FileInputStream(sourceFile)) {
                fos = new FileOutputStream(destFile);
                byte[] arr = new byte[1024];
                while ((fis.read(arr)) != -1) {
                    fos.write(arr);
                    fos.flush();
                }
            }
            fos.close();
            System.out.println("copy thành công");
            return true;
        } else {
            System.out.println("file nguồn không tồn tại");
            return false;
        }
    }


    public static void main(String[] args) throws IOException {
        CopyFile copyFile = new CopyFile();
        copyFile.copyFile("/home/van-tien/Documents/pthtth/labs1/a.txt", "/home/van-tien/Documents/pthtth/labs1/b.txt");
    }
}

