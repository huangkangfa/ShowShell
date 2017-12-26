package app.application.sdk_base_lib.util.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huangkangfa on 2017/10/12.
 */

public class BitmapDealUtil {
    /**
     * Get bitmap from specified image path
     * 从指定的图像路径获取位图
     *
     * @param imgPath
     * @return
     */
    public Bitmap getBitmapByPath(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     * 将位图存储到指定的图像路径中
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public void saveBitmap(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height. Used to get thumbnail
     * 根据像素压缩图像，这将修改图像宽度/高度。 用于缩略图
     *
     * @param imgPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public Bitmap compressBitmapByRatio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height. Used to get thumbnail
     * 根据大小压缩图像，这将修改图像宽度/高度。 用于缩略图
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public Bitmap compressBitmapByRatio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.PNG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * 自定义压缩比进行压缩
     *
     * @param imgPath
     * @param partsNum
     * @return
     */
    public Bitmap compressBitmapByRatio(String imgPath, int partsNum) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = partsNum;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * 自定义压缩比进行压缩
     *
     * @param image
     * @param partsNum
     * @return
     */
    public Bitmap compressBitmapByRatio(Bitmap image, int partsNum) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.PNG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = partsNum;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param maxSize
     * @return
     */
    public static Bitmap compressBitmapByQuality(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.PNG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * Compress by quality,  and generate image to the path specified
     * 根据质量压缩，并将图像生成指定的路径
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public void compressBitmapByQualityWithSave(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.PNG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.PNG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * Compress by quality,  and generate image to the path specified
     * 根据质量压缩，并将图像生成指定的路径
     *
     * @param imgPath
     * @param outPath
     * @param maxSize             target will be compressed to be smaller than this size.(kb)
     * @param needsDeleteOriginal Whether delete original file after compress
     * @throws IOException
     */
    public void compressBitmapByQuality(String imgPath, String outPath, int maxSize, boolean needsDeleteOriginal) throws IOException {
        compressBitmapByQualityWithSave(getBitmapByPath(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDeleteOriginal) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Ratio and generate thumb to the path specified
     * 比较并生成指定路径的缩略图
     *
     * @param image
     * @param outPath
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @throws FileNotFoundException
     */
    public void compressBitmapByRatioWithSave(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = compressBitmapByRatio(image, pixelW, pixelH);
        saveBitmap(bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     * 比较并生成指定路径的缩略图
     *
     * @param imgPath
     * @param outPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public void compressBitmapByRatioWithSave(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = compressBitmapByRatio(imgPath, pixelW, pixelH);
        saveBitmap(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }


//    /*将byte[]转换成InputStream*/
//    public static InputStream byte2InputStream(byte[] b) {
//        ByteArrayInputStream bais = new ByteArrayInputStream(b);
//        return bais;
//    }
//
//    /*将InputStream转换成byte[]*/
//    public static byte[] inputStream2Bytes(InputStream is) {
//        String str = "";
//        byte[] readByte = new byte[1024];
//        int readCount = -1;
//        try {
//            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
//                str += new String(readByte).trim();
//            }
//            return str.getBytes();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /*将Bitmap转换成InputStream*/
//    public static InputStream bitmap2InputStream(Bitmap bm, int quality) { //quality=100表示保留100%品质
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
//        InputStream is = new ByteArrayInputStream(baos.toByteArray());
//        return is;
//    }
//
//    /*将InputStream转换成Bitmap*/
//    public static Bitmap inputStream2Bitmap(InputStream is) {
//        return BitmapFactory.decodeStream(is);
//    }
//
//    /*Drawable转换成InputStream*/
//    public static InputStream drawable2InputStream(Drawable d, int quality) {
//        Bitmap bitmap = drawable2Bitmap(d);
//        return bitmap2InputStream(bitmap, quality);
//    }
//
//    /*InputStream转换成Drawable*/
//    public static Drawable InputStream2Drawable(InputStream is) {
//        Bitmap bitmap = inputStream2Bitmap(is);
//        return bitmap2Drawable(bitmap);
//    }
//
//    /*Drawable转换成byte[]*/
//    public static byte[] drawable2Bytes(Drawable d) {
//        Bitmap bitmap = drawable2Bitmap(d);
//        return bitmap2Bytes(bitmap);
//    }
//
//    /*byte[]转换成Drawable*/
//    public static Drawable bytes2Drawable(byte[] b) {
//        Bitmap bitmap = bytes2Bitmap(b);
//        return bitmap2Drawable(bitmap);
//    }
//
//    /*Bitmap转换成byte[]*/
//    public static byte[] bitmap2Bytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }
//
//    /*byte[]转换成Bitmap*/
//    public static Bitmap bytes2Bitmap(byte[] b) {
//        if (b.length != 0) {
//            return BitmapFactory.decodeByteArray(b, 0, b.length);
//        }
//        return null;
//    }
//
//    /*Drawable转换成Bitmap*/
//    public static Bitmap drawable2Bitmap(Drawable drawable) {
//        Bitmap bitmap = Bitmap
//                .createBitmap(
//                        drawable.getIntrinsicWidth(),
//                        drawable.getIntrinsicHeight(),
//                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                                : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }
//
//    /*Bitmap转换成Drawable*/
//    public static Drawable bitmap2Drawable(Bitmap bitmap) {
//        BitmapDrawable bd = new BitmapDrawable(bitmap);
//        Drawable d = (Drawable) bd;
//        return d;
//    }
//
//    /**
//     * 图片转成string
//     *
//     * @param bitmap
//     * @return
//     */
//    public static String bitmapToString64(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] appicon = baos.toByteArray();// 转为byte数组
//        return Base64.encodeToString(appicon, Base64.DEFAULT);
//
//    }
//
//    /**
//     * string转成bitmap
//     *
//     * @param st
//     */
//    public static Bitmap string64ToBitmap(String st) {
//        // OutputStream out;
//        Bitmap bitmap = null;
//        try {
//            // out = new FileOutputStream("/sdcard/aa.jpg");
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(st, Base64.DEFAULT);
//            bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            return bitmap;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 图片文件转换为base64编码的字符串
//     *
//     * @param imgFile  图片文件
//     */
//    public static String fileToString64(File imgFile) {
//        InputStream in = null;
//        byte[] data = null;
//        //读取图片字节数组
//        try{
//            in = new FileInputStream(imgFile);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        //对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        String result = encoder.encode(data);
//        return result;//返回Base64编码过的字节数组字符串
//    }
}
