package com.indctr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class CommHttp {
    public static String sendPost(String url, String param) {
        int nresult = -1;
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.append(param);
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            nresult = 1;
        } catch (Exception e) {
            nresult = -1;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
            if (nresult == -1) {
                return "-1";
            }
        }
        return result;
    }
}
