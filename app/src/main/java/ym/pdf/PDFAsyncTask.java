package ym.pdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Gavin.GaoTJ 19.06.2019
 * @description :
 */
public class PDFAsyncTask extends AsyncTask<Void, Void, List<String>> {
    private final DocumentSource docSource;
    private String fileName;
    private final String password;
    private final PdfiumCore pdfiumCore;
    private final int firstPageIdx;
    private int viewWidth;
    private int viewHeight;
    private boolean cancelled;
    private PdfDocument pdfDocument;
    private OnGetPdfListener onGetPdfListener;
    /**
     * The actual width and height of the pages in the PDF document
     */
    private int actualPageWidth;
    private int actualPageHeight;

    public PDFAsyncTask(DocumentSource docSource, String fileName, String password, PdfiumCore pdfiumCore, int firstPageIdx, int height, int width, OnGetPdfListener onGetPdfListener) {
        this.docSource = docSource;
        this.fileName = fileName;
        this.cancelled = false;
        this.password = password;
        this.firstPageIdx = firstPageIdx;
        this.pdfiumCore = pdfiumCore;
        this.onGetPdfListener = onGetPdfListener;
        this.viewWidth = width;
        this.viewHeight = height;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> list = new ArrayList<>();
        Log.d("Gavin_PDF", "1 " +System.currentTimeMillis());
        try {
            pdfDocument = docSource.createDocument(App.getApplication().getApplicationContext(), pdfiumCore, password);
            // We assume all the pages are the same size
            int pageCount = pdfiumCore.getPageCount(pdfDocument);
            pdfiumCore.openPage(pdfDocument, 0);
            actualPageWidth = pdfiumCore.getPageWidth(pdfDocument, firstPageIdx);
            actualPageHeight = pdfiumCore.getPageHeight(pdfDocument, firstPageIdx);
            float[] floats = PDFUtils.calculateOptimalWidthAndHeight(viewWidth, viewHeight, actualPageWidth, actualPageHeight);
            Log.d("Gavin_PDF", "2 " +System.currentTimeMillis());
            for (int i = 0; i < pageCount; i++) {
                if (!pdfDocument.hasPage(i)) {
                    pdfiumCore.openPage(pdfDocument, i);
                }
                Bitmap render = Bitmap.createBitmap((int) floats[0], (int) floats[1], Bitmap.Config.ARGB_8888);
                pdfiumCore.renderPageBitmap(pdfDocument, render, i, 0, 0, render.getWidth(), render.getHeight());
//                File file = getFile();
                String bitmapFilePath = saveBitmap2Local(render,"contract_page_" + i);
                list.add(bitmapFilePath);
            }
            Log.d("Gavin_PDF", "3 " +System.currentTimeMillis());

            Log.d("Gavin_PDF", "doInBackground: actualPageWidth " + actualPageWidth + " _ actualPageHeight " + actualPageHeight + " _pageCount " + pageCount);
            return list;
        } catch (Throwable t) {
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<String> bitmap) {
        Log.d("Gavin_PDF", bitmap != null && !bitmap.isEmpty() ? bitmap.toString() : "bitmap is null");
        if (bitmap != null) {
            onGetPdfListener.onGetComplete(bitmap);
            return;
        }
        if (!cancelled) {
        }
    }

    /**
     * Get file from local storage.
     *
     * @param context  The context
     * @param fileName The name of file
     * @return FileBdo from local storage or <code>null</code> if not exists
     */
    public static File getFile(Context context, String fileName) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            final File file = new File(context.getExternalFilesDir(null), fileName);
            return file.exists() ? file : null;
        } else {
            return null;
        }
    }

    private String saveBitmap2Local(Bitmap bitmap, String bitmapPageName) {
        try {
            File file = new File(App.getApplication().getExternalCacheDir().getPath());
            if (!file.exists()) {
                file.mkdirs();
            }

            String localFile = file.getAbsolutePath() + File.separator + bitmapPageName + ".png";
            Log.i("test_sign", "图片全路径localFile = " + localFile);
            File f = new File(localFile);

            FileOutputStream fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return localFile;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        cancelled = true;
    }

    interface OnGetPdfListener {

        void onGetError();

        void onGetComplete(List<String> bitmap);
    }
}
