package com.example.yoursy.wew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Suggestion extends AppCompatActivity {

    EditText sugName, sugText;
    Button sugSendButton;

    String address = "adebaraskidiz@gmail.com", to = "adebara.sulaimon@yahoo.com",
            msg, subs;
    String pass = "Hay0bami";

    ProgressDialog pdialog = null;
    Context context = null;
    Session session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        sugName = (EditText) findViewById(R.id.sugName);
        sugText = (EditText) findViewById(R.id.sugText);
        sugSendButton = (Button) findViewById(R.id.sugSendButton);

        sendSuggestion();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
    }

    public void sendSuggestion() {
        sugSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });
    }

    public void checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            sendSugs();
        } else {
            Toast.makeText(Suggestion.this, " There is no internet connection the device! ", Toast.LENGTH_SHORT).show();
        }
    }


    public void checkFields() {
        String name = sugName.getText().toString();
        String suggestion = sugText.getText().toString();

        if (name.isEmpty() || equals(null)) {
            Toast.makeText(Suggestion.this, " Enter your name ", Toast.LENGTH_SHORT).show();
        } else if (suggestion.isEmpty() || equals(null)) {
            Toast.makeText(Suggestion.this, " Type your suggestion ", Toast.LENGTH_SHORT).show();
        } else {
            checkInternet();
        }
    }

    public void sendSugs() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(address, pass);

            }

        });

        pdialog = ProgressDialog.show(context, "", "Sending....", true);

        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute();
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        String name = "SUGGESTION FROM " + sugName.getText().toString().toUpperCase();
        String suggestion = sugText.getText().toString();

        @Override
        protected String doInBackground(String... arg0) {
            try {


                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(address));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(name);
                message.setContent(suggestion, "text/html; charset=utf-8");

                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(),
                    "Suggestion has been Sent to the development team!...", Toast.LENGTH_LONG).show();

            sugName.setText("");
            sugText.setText("");
            super.onPostExecute(result);
        }
    }

}
