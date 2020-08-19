import java.io.*;

/**
 * 对一个文件指定位置读写
 *
 * @author MR.ZHANG
 * @create 2019-12-26 10:33
 */
public class FileRandomWrite {
    public static void main(String[] args){
        File file = new File("D:\\copyfiles");
        File[] files = file.listFiles();
        RandomAccessFile randomAccessFile = null;

        for (File s : files) {
            String name = s.getName();
            String pcm = name.replace("wav", "pcm");
            File pcmFile = new File("D:\\pcmfiles\\" + pcm);
            try {
                randomAccessFile = new RandomAccessFile(s, "rw");
                FileOutputStream  fos = new FileOutputStream(pcmFile);
                int result = 0;
                try {
                    randomAccessFile.seek(44L);
                    byte[] b=new byte[1024];
                    while ((result = randomAccessFile.read(b)) > 0) {
                        fos.write(b);
                    }
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
