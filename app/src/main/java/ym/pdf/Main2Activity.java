package ym.pdf;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.source.AssetSource;
import com.shockwave.pdfium.PdfiumCore;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements PDFAsyncTask.OnGetPdfListener, View.OnClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = (ListView) findViewById(R.id.lv_pdf);

        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = listView.getWidth() - listView.getPaddingLeft() - listView.getPaddingRight();
                int height = listView.getHeight() - listView.getPaddingTop() - listView.getPaddingBottom();
                PDFAsyncTask pdfAsyncTask = new PDFAsyncTask(new AssetSource("sample.pdf"),"sample.pdf", "", new PdfiumCore(Main2Activity.this), 0, height, width, Main2Activity.this);
                pdfAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
//        PDFAsyncTask pdfAsyncTask = new PDFAsyncTask(new AssetSource("sample.pdf"), "", new PdfiumCore(this), 0,this);
//        pdfAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onGetError() {

    }

    @Override
    public void onGetComplete(List<String> bitmap) {
//        PDFAdapter pdfAdapter = new PDFAdapter(bitmap);
//        listView.setAdapter(pdfAdapter);
        View headView = (View) LayoutInflater.from(this).inflate(R.layout.headview, null);
        View footView = (View) LayoutInflater.from(this).inflate(R.layout.footview, null);
        footView.findViewById(R.id.tv_one).setOnClickListener(this);
        footView.findViewById(R.id.tv_two).setOnClickListener(this);
        footView.findViewById(R.id.tv_three).setOnClickListener(this);
        listView.addHeaderView(headView);
        listView.addFooterView(footView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                Toast.makeText(this, "one text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_two:
                Toast.makeText(this, "two text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_three:
                Toast.makeText(this, "three text", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
