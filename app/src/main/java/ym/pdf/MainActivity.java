package ym.pdf;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.AssetSource;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfiumCore;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        PDFView pdfView = ((PDFView)findViewById(R.id.pdfview));

        pdfView.fromAsset("test.pdf").pageFitPolicy(FitPolicy.WIDTH).
                load();

//        PDFAsyncTask pdfAsyncTask = new PDFAsyncTask(new AssetSource("sample.pdf"), "", new PdfiumCore(this), 0,this);
//        pdfAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}
