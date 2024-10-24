
package com.AmazingDevOpsLMS.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Component;

/**
 *
 * @author samuel
 */
@Component
 public class LuluSMSClient {
      public String LuluSMSUrl = "";
      public String UserName = "";
      public String Password = "";
      public String From = "";
      public String To = "";
      public String SMS = "";
      public String SMSTypeID = "";


      public String SendSMS() throws Exception {

          String data = "";

          data = data + "&UserName=" + UserName;
          data = data + "&Password=" + Password;
          data = data + "&From=" + From;
          data = data + "&To=" + To;
          SMS = SMS.replace("&", "%26");
          data = data + "&SMS=" + SMS;

          String responseString = "";


          // Create a URLConnection
          URL url;


          url = new URL(LuluSMSUrl);


          HttpsURLConnection connection;
          connection = (HttpsURLConnection) url.openConnection();


          connection.setDoOutput(true);

          connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          connection.setRequestMethod("POST");

          // Post your content
          java.io.OutputStreamWriter stream = new OutputStreamWriter(connection.getOutputStream());

          stream.write(data);
          stream.flush();
          stream.close();


          // the remote server
          // It can lock forever and never times out
          InputStreamReader isr = new InputStreamReader(connection.getInputStream());
          BufferedReader brx = new BufferedReader(isr);
          BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));


          // String Buffer to store the data
          StringBuffer sb = new StringBuffer();

          // Read the first line into the buffer
          String str;
          str = br.readLine();

          // Read the rest of the lines into the buffer
          while (str != null) {
              sb.append(str);
              str = br.readLine();
          }

          responseString = sb.toString();
          br.close();

          connection.disconnect();
          return responseString;
      }
  }
