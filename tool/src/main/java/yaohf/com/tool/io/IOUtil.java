package yaohf.com.tool.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created  2017/3/10.
 */
public class IOUtil {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
