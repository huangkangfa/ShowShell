package app.application.sdk_base_lib.util.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * 切片读取工具
 * Created by huangkangfa on 2017/5/12 0012.
 */
public class BitmapCutUtil {

    private int num_w=1;  //横向切片数量

    private int num_h=1;  //纵向切片数量

    private InputStream data;  //数据源

    private int width,height;

    private BitmapRegionDecoder bitmapRegionDecoder;
    private BitmapFactory.Options options;

    public BitmapCutUtil(Context context, String name, int num_w, int num_h){
        this.num_w=num_w;
        this.num_h=num_h;
        try {
            data = context.getAssets().open(name);
            //获得图片的宽、高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(data, null, tmpOptions);
            width = tmpOptions.outWidth;
            height = tmpOptions.outHeight;

            //设置显示图片的中心区域
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(data, false);
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getCutBitmap(int a,int b){
        return bitmapRegionDecoder.decodeRegion(new Rect(width*(a-1)/num_w,height*(b-1)/num_h,width *a/num_w, height*b/num_h), options);
    }
}
