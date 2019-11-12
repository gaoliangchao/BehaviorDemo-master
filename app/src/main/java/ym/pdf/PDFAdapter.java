package ym.pdf;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Gavin.GaoTJ 20.06.2019
 * @description :
 */
public class PDFAdapter extends BaseAdapter {

    List<Bitmap> list = new ArrayList<>();

    public PDFAdapter(List<Bitmap> list) {
        this.list.clear();
        if (list != null && !list.isEmpty()) {
            this.list.addAll(list);
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent,false);
            viewHolder.ivPDF = convertView.findViewById(R.id.iv_pdf);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivPDF.setImageBitmap(list.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView ivPDF;
    }
}
