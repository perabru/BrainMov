package com.brunosjc.brainmov.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;
import com.brunosjc.brainmov.modelo.Videos;
import com.brunosjc.brainmov.utilidade.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;



public class VideosPacienteActivity extends AppCompatActivity {

    private Object resp;
    Videos videos ;

    private WebView webView;

    private Button btnEscolherVideo;
    private Button btnUpload;

    private ImageView imageView;
    private TextView txtLink;




    String urlImage = "";


    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    ProgressDialog progressDialog;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    ArrayList<String> arrayList  = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_paciente);
        btnEscolherVideo = findViewById(R.id.btnEscolherVideo);

        imageView = findViewById(R.id.imageView);
        txtLink = findViewById(R.id.txtLink);

        //webView = findViewById(R.id.webView);
        btnUpload = findViewById(R.id.btnFazerUpload);

        listView = findViewById(R.id.listViewFotos);

       popularListaDeFotos();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarArquivo();
            }
        });


      btnEscolherVideo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {



              SelectImage();

          }
      });


    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


                try {

                    Picasso.get().load(arrayList.get(i)).into(imageView);

                    ClipData clip = ClipData.newPlainText("url", arrayList.get(i));

                    clipboard.setPrimaryClip(clip);
                    Toast toast = Toast.makeText(getApplicationContext(), "Link is copied", Toast.LENGTH_SHORT);
                    toast.show();


                }catch (Exception ex){
                    Toast
                            .makeText(VideosPacienteActivity.this,
                                    "Link vazio ou inexistente!",
                                    Toast.LENGTH_SHORT)
                            .show();

                }

            }
        });


    }





    private void SelectImage()
    {

        Intent i = getIntent();
        resp = i.getSerializableExtra("info");

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }



    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media

                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if (filePath != null) {

            // Code for showing progressDialog while uploading
            progressDialog
                    = new ProgressDialog(this);

            progressDialog.setTitle("Preparando imagem");
            progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.ic_local_florist_black_24dp,null));
            progressDialog.show();

            // Defining the child of storageReference
            final StorageReference ref  = storageReference.child("images/"+ UUID.randomUUID().toString());



            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            urlImage = task.getResult().toString();
                                            txtLink.setText(urlImage);
                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog

                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(VideosPacienteActivity.this,
                                                    "Imagem pronta faça o upload",
                                                    Toast.LENGTH_SHORT)
                                            .show();




                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(VideosPacienteActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");


                                }
                            });





        }

    }


    private void carregarArquivo() {



        if(txtLink.getText().toString() != null) {



             FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mDatabaseRef = database.getReference();

             videos = new Videos();


            /*mDatabaseRef.child("fotos").child(Base64Custom.codificarBase64(resp.toString())).
                    child("link").setValue(txtLink.getText().toString());*/

            videos.setIdUsuario(Base64Custom.codificarBase64(resp.toString()));
            videos.setLink(txtLink.getText().toString());
            videos.salvarVideo();

            Toast
                    .makeText(VideosPacienteActivity.this,
                            "Upload realizado com sucesso!",
                            Toast.LENGTH_SHORT)
                    .show();
        }


    }


    private void popularListaDeFotos(){

        Intent i = getIntent();
        resp = i.getSerializableExtra("info");

        Log.d("RESP", resp.toString());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fotos").child(Base64Custom.codificarBase64(resp.toString()));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot resultSnapshot: dataSnapshot.getChildren()){

                    Log.d("FOTO", resultSnapshot.child("link").getValue().toString());

                    arrayList.add(resultSnapshot.child("link").getValue().toString());




                   // webView.loadUrl(resultSnapshot.child("link").getValue().toString());


                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(arrayAdapter);



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro ao ler banco de dados", Toast.LENGTH_LONG).show();

            }

        });


    }


    }







