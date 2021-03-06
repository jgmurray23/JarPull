package com.canonarius.jarpull.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;

public class ClientA {

    String urlToPullFrom;
    String filenameandpath;

    public static void main(String[] args) throws Exception {


        if( args.length == 2 ){

        }
        else{
            System.out.println(" provide URL and filenameandpath ");
                    return;
        }


        ClientA clientA = new ClientA();

        clientA.urlToPullFrom = args[0];
        clientA.filenameandpath = args[1];

        clientA.jarpull();

    }


    public void jarpull() throws Exception {

    HttpClient httpclient = new DefaultHttpClient();
    //HttpGet httpget = new HttpGet("https://s3.ca-central-1.amazonaws.com/jbhor/BCDesktopPrint.jar");
        HttpGet httpget = new HttpGet(urlToPullFrom);


    HttpResponse response = httpclient.execute(httpget);
    System.out.println(response.getStatusLine());
    HttpEntity entity = response.getEntity();
    if (entity != null) {
        InputStream instream = entity.getContent();
        try {
            BufferedInputStream bis = new BufferedInputStream(instream);

           // String filePath = "/Users/jgmurray/Desktop/BCDesktopPrint.jar" ;
            String filePath = filenameandpath ;
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            int inByte;
            while ((inByte = bis.read()) != -1 ) {
                bos.write(inByte);
            }
            bis.close();
            bos.close();
        } catch (IOException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            httpget.abort();
            throw ex;
        } finally {
            instream.close();
        }
        httpclient.getConnectionManager().shutdown();
    }
}


}
