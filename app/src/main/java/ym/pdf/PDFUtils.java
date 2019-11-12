package ym.pdf;

/**
 * @author : Gavin.GaoTJ 18.06.2019
 * @description :
 */
public class PDFUtils {

    public static float[] calculateOptimalWidthAndHeight(float viewWidth, float viewHeight, float pageWidth, float pageHeight) {

        float maxWidth = viewWidth;
        float maxHeight = viewHeight;
        float w = pageWidth;
        float h = pageHeight;
        float ratio = w / h;
        w = maxWidth;
        h = (float) Math.floor(maxWidth / ratio);
        if (maxHeight == 0) {
            h = (float) Math.floor(maxWidth / ratio);
        } else if (h > maxHeight) {
            h = maxHeight;
            w = (float) Math.floor(maxHeight * ratio);
        }
        return new float[]{w, h};
    }
}
