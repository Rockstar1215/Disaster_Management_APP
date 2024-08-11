package com.example.disaster.Askme;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disaster.Askme.Adapter.ChatMessageAdapter;
import com.example.disaster.Askme.Model.ChatMessage;
import com.example.disaster.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class chatbot extends AppCompatActivity {
    ListView listView;
    FloatingActionButton btnSend;
    EditText edtTextMsg;
    ImageView imageView;

    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        listView = findViewById(R.id.listView);
        btnSend = findViewById(R.id.btnSend);
        edtTextMsg = findViewById(R.id.edtTextMsg);
        imageView = findViewById(R.id.imageView);
        adapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtTextMsg.getText().toString();

                // Ensure that chat is not null before using it
                if (chat != null) {
                    String response = chat.multisentenceRespond(message);
                    if (TextUtils.isEmpty(message)) {
                        Toast.makeText(chatbot.this, "Please enter a query..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendMessage(message);
                    botsReply(response);
                    edtTextMsg.setText("");
                    listView.setSelection(adapter.getCount() - 1);
                } else {
                    // Handle the case where chat is null (e.g., not properly initialized)
                    Toast.makeText(chatbot.this, "Chat initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Dexter.withActivity(this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    custom();
                    chat = new Chat(bot); // Move the initialization here

                    Toast.makeText(chatbot.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                if (report.isAnyPermissionPermanentlyDenied()){
                    Toast.makeText(chatbot.this, "Please Grant all the Permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(chatbot.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    private void custom() {

        boolean available = isSDCARDAvailable();

        AssetManager assets = getResources().getAssets();
        File fileName = new File(chatbot.this.getFilesDir()+"/TBC/bots/askme0");

        boolean makeFile = fileName.mkdirs();

        if (fileName.exists()) {
            //Reading the file
            try {
                // Your existing code

                for (String dir : assets.list("askme0")) {
                    File subDir = new File(fileName.getPath() + "/" + dir);
                    boolean subDirCheck = subDir.mkdirs();

                    for (String file : assets.list("askme0/" + dir)) {
                        File newFile = new File(fileName.getPath() + "/" + dir + "/" + file);



                        if (newFile.exists()) {
                            // Log that the file already exists
                            continue;
                        }

                        // Your existing code for opening InputStream and creating OutputStream
                        InputStream in;
                        OutputStream out;
                        in = assets.open("askme0/" + dir + "/" + file);
                        out = new FileOutputStream(fileName.getPath()+"/"+dir+"/"+file);


                        // Copy file from assets to the destination directory
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }

                // Your existing code
            } catch (IOException e) {
                e.printStackTrace();
            }}
        //get the working directory
        MagicStrings.root_path = chatbot.this.getFilesDir()  + "/TBC";
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();

        bot = new Bot("askme0", MagicStrings.root_path, "chat");

        chat = new Chat(bot);
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        adapter.add(chatMessage);
    }

    private void botsReply(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        adapter.add(chatMessage);
    }


    public  boolean isSDCARDAvailable(){
        return chatbot.this.getFilesDir().equals(Environment.MEDIA_MOUNTED)?true:false;
    }

    //copying the file
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }


}