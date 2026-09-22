// TH2: Delete thư mục chứa Files
package delete;

import java.io.File;
import java.io.IOException;

public class DeleteListFileInfolder {
    public boolean deleteListFileInfolder(String source) {
        File folder = new File(source);
        // folder tồn tại
        if (folder.exists()) {
        // danh sách file
        File[] listFile = folder.listFiles();
        if (listFile.length != 0) {
            for (File f : listFile) {
                // file thì xóa
                if (f.isFile()) {
                f.delete();
            }
        }
        }
        folder.delete();
        System.out.println("Delete folder thành công!");
            return true;
        } else {
        System.out.println("folder không tồn tại");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        DeleteListFileInfolder deleteListFileInfolder = new DeleteListFileInfolder();
        deleteListFileInfolder.deleteListFileInfolder("/home/van-tien/Documents/pthtth/labs1/delete/TestDeleteDir");
    }
}