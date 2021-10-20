package com.example.unicorngladiators;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpReq extends AppCompatActivity implements View.OnClickListener{
    private TextView contentView;
    private Button startGameBtn;
    private int status;
    private StringBuffer content = new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_req);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.startGameBtn = (Button) findViewById(R.id.viewContent);
        startGameBtn.setOnClickListener(this);

        this.contentView = (TextView) findViewById(R.id.textView);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            default:
                try {
                    URL url = new URL("https://example.com");

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    status = con.getResponseCode();
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    content = new StringBuffer();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();

                }

                contentView.setText("Status Code: "+this.status+"\n\nContent:\n\n"+
                        Html.fromHtml(content.toString()));
                break;
        }
    }
}
