import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;

public class TestJsonRpc {
    @Test
    public void get_status(){
        try {
            URL url = new URL("http://localhost:8500/rpc-server");
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            assertEquals("Method GET not implemented", in.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
