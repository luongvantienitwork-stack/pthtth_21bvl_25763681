import java.io.File;
import java.io.IOException;

public class FinFile {
    public void finFile(String source, String key) {
        File file = new File(source);
        
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getName().endsWith(key)) {
                    System.out.println(file.getAbsolutePath());
                }
            }
            File[] listFile = file.listFiles(); // hàm listFiles() ưu tiên quét thư mục trước files
            if (listFile != null) {
                for (File f : listFile) {
                    finFile(f.getAbsolutePath(), key);
                }
            }
        } else {
            System.out.println("source không tồn tại");
        }
    }

    public static void main(String[] args) throws IOException {
        FinFile finFile = new FinFile();
        finFile.finFile("/home/van-tien/Documents/pthtth/labs1/", ".txt");
    }
}

