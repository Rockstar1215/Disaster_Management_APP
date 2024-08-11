package com.example.disaster.Post;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.disaster.Adapters.RequestAdapter;
import com.example.disaster.DataModels.RequestDataModel;
import com.example.disaster.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Post extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter requestAdapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        firestore = FirebaseFirestore.getInstance();
        TextView makeRequestButton = findViewById(R.id.make_request_button);
        makeRequestButton.setOnClickListener(view -> startActivity(new Intent(Post.this, MakeRequestActivity.class)));

        requestDataModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestAdapter = new RequestAdapter(requestDataModels, this);
        recyclerView.setAdapter(requestAdapter);

        fetchPosts();
    }

    private void fetchPosts() {
        firestore.collection("posts").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String message = document.getString("message");
                    String imageUrl = document.getString("imageUrl");

                    RequestDataModel requestDataModel = new RequestDataModel();
                    requestDataModel.setMessage(message);
                    requestDataModel.setUrl(imageUrl);

                    requestDataModels.add(requestDataModel);
                }
                requestAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Post.this, "Error getting posts: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
