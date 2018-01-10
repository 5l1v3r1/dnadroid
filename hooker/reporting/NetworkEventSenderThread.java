package com.dnadroid.hooker.reporting;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Queue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dnadroid.hooker.SubstrateMain;
import com.dnadroid.hooker.common.InterceptEvent;

public class NetworkEventSenderThread implements Runnable {

  private static final int VERY_LONG_SLEEP_TIME = 2000;
  
  /**
   * Long sleep time (when connection fails with remote server)
   */
  private static final int LONG_SLEEP_TIME = 1000;

  /**
   * Short sleep time (when no event is available)
   */
  private static final int SHORT_SLEEP_TIME = 300;

  // Events to send
  private Queue<InterceptEvent> events;

  // The HttpClient
  private HttpClient httpClient;
  // Target URI
  private URI targetURI;

  // Flag to raise if you want this thread to stop
  private boolean finish;

  private int nbErrors = 0;
  
  private String esIndex;
  private String esDoctype;

  private String host;
  private int port;


  public NetworkEventSenderThread(String host, int port, String esIndex, String esDoctype, Queue<InterceptEvent> events) {
    this.events = events;
    this.host = host;
    this.port = port;
    this.esIndex = esIndex;
    this.esDoctype = esDoctype;

    this.httpClient = new DefaultHttpClient();
    this.finish = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {

    if (this.events == null) {
      SubstrateMain.log("EventSenderThread (" + Thread.currentThread().getPriority()
          + ") fails to start since no Queue is available. ");
      return;
    }

    SubstrateMain.log("EventSenderThread (" + Thread.currentThread().getPriority()
        + ") has started.");

    int sleepTime;

    while (!this.isFinish()) {
    	
      sleepTime = 0;

      // !!!
      // This may cause a problem when we launch from Snapshot. We have to be careful that snapshot 
      // doesn't have any IDXP
      // Retrieves the current id XP
      //this.idXP = fetchIDXPFromSdcard();

      InterceptEvent eventToSend = this.events.poll();
      if (eventToSend != null) {
        if (!this.send(eventToSend)) {
          this.events.add(eventToSend);
          sleepTime = LONG_SLEEP_TIME;
          nbErrors++;
        }
      } else {
        sleepTime = SHORT_SLEEP_TIME;
      }

      if ( (nbErrors > 10) && (nbErrors % 5 == 0) ) {
        SubstrateMain.log("[E] Too many network errors, we stop the thread...");
        sleepTime = VERY_LONG_SLEEP_TIME;
      }

      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          SubstrateMain.log("[E] Error while sleeping the EventSenderThread", e);
        }
      }
    }
  }

  public void stopThread() {
    this.finish = true;
  }

  public boolean send(InterceptEvent event) {

    try {
      this.targetURI = new URI("http://"+host+":"+port+"/"+this.esIndex.toLowerCase()+"/"+this.esDoctype.toLowerCase()+"?parent=" + event.getIDXP()+"&op_type=create");
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    SubstrateMain.log("Sending Event to "+this.targetURI);

    boolean result = false;
    StringEntity se;
    try {
      se = new StringEntity(event.toJson());

      HttpPost httpPost = new HttpPost(this.targetURI);
      httpPost.setEntity(se);
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      // Executes POST request to the given URL
      HttpResponse httpResponse = this.httpClient.execute(httpPost);

      // Receives response as inputStream
      InputStream inputStream = httpResponse.getEntity().getContent();

      // Converts inputStream to string
      // TODO: we should parse the elasticsearch result
      result = inputStream != null;

      SubstrateMain.log("Event succesfully sent !");

      if (inputStream != null) {
        inputStream.close();
      }

    } catch (UnsupportedEncodingException e) {
      SubstrateMain.log(
          "Impossible to send data to remote database due to encoding issues (" + e.getMessage()
              + ")", e);
    } catch (IOException e) {
      SubstrateMain.log("Impossible to connect to the remote database, check the connectivity ("
          + e.getMessage() + ")", e);
    }
    return result;
  }

  /**
   * @return the finish
   */
  public boolean isFinish() {
    return finish;
  }

}
