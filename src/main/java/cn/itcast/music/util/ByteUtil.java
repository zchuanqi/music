package cn.itcast.music.util;

import java.io.*;

public class ByteUtil {
    public static void inputStream_to_file(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
