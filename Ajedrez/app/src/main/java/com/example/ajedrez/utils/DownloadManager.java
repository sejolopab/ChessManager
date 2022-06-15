package com.example.ajedrez.utils;

import android.content.Context;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DownloadManager {

    private static DownloadManager instance = new DownloadManager();

    public static DownloadManager getInstance() {
        if(instance == null){
            instance = new DownloadManager();
        }
        return instance;
    }

    /**
     * Download a selected file from firebase and saves it on the download directory
     *
     * @param context - Application context at the moment
     * @param fileName -  Name of the file to download
     */
    public void download(Context context, String fileName) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference ref = storageRef.child("principiante/" + fileName);

        ref.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            downloadFile(context, fileName,DIRECTORY_DOWNLOADS,url);
        }).addOnFailureListener(e -> {

        });
    }

    private void downloadFile(Context context, String fileName, String destinationDirectory, String url) {
        android.app.DownloadManager downloadManager = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);

        request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName);

        downloadManager.enqueue(request);
    }
}
