package com.example.ajedrez.View.Subjects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ajedrez.R;
import com.example.ajedrez.Utils.AlertsManager;
import com.example.ajedrez.View.BaseFragment;
import com.example.ajedrez.View.MainActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;
import java.util.Objects;

public class SubjectReaderFragment extends BaseFragment implements OnPageChangeListener, OnLoadCompleteListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public String fileName = "";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    public static SubjectReaderFragment newInstance(String fileName) {
        SubjectReaderFragment fragment = new SubjectReaderFragment();
        fragment.setFileName(fileName);
        return fragment;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_reader, container, false);
        pdfView = view.findViewById(R.id.pdfView);
        displayFromAsset(fileName);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
        try {
            pdfView.fromAsset(fileName)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)

                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(getContext()))
                    .load();
        } catch (Exception e) {
            AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title),getString(R.string.file_not_found), getContext());
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}