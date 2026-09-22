package delete;
//  TH3: Delete thư mục chứa danh sách thư mục con và trong thư mục con chứa danh sách file, các bạn 
// tham khảo ở hai trường hợp mình đã làm ở trên để giải quyết cho trường hợp thứ 3, có vấn đề gì thì
// các bạn cứ command ờ bên dưới.

import java.io.File;
import java.io.IOException;

    public class DeleteDirTH3 {
        public boolean deleteListFileInfolder(String source) throws IOException {
            File folder = new File(source);
            if (folder.exists()) {
                File[] listFile = folder.listFiles();
            if (listFile.length != 0) {
                for (File f : listFile) {
                    if(f.isFile()){
                        f.delete();
                    }
                    if(f.isDirectory()){
                        deleteListFileInfolder(f.getAbsolutePath());
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
        DeleteDirTH3 deleteDirTH3 = new DeleteDirTH3();
        deleteDirTH3.deleteListFileInfolder("/home/van-tien/Documents/pthtth/labs1/delete/TestDeleteDir");
    }
}