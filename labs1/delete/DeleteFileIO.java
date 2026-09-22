// Delete file trong Java
package delete;
import java.io.File;
    public class DeleteFileIO {
    private void deleteFile(String source) {
        File file = new File(source);
            if(file.exists()) {
                System.out.println("file ton tai");
                file.delete();
                System.out.print("xoa file thanh cong");
            }
        else {
            System.out.println("file khong ton tai");
        }
    }
    public static void main(String[] args) {
        DeleteFileIO deleteFileIO = new DeleteFileIO();
        deleteFileIO.deleteFile("/home/van-tien/Documents/pthtth/labs1/delete/demo.txt");
    }
}