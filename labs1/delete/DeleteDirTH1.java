// TH1: Delete thư mục rỗng
package delete;

import java.io.File;
import java.io.IOException;

public class DeleteDirTH1 {
    public boolean deleteEmptyFolder(String source)
    {
        File folder = new File(source);
        if(folder.exists())
        {
            folder.delete();
            System.out.println("folder ton tai\n xoa folder thanh cong");
            return true;
        }
        else
        {
            System.out.println("folder khong ton tai");
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        DeleteDirTH1 DeleteDirTH1 = new DeleteDirTH1();
        DeleteDirTH1.deleteEmptyFolder("/home/van-tien/Documents/pthtth/labs1/delete/TestDeleteDir");
    }
}