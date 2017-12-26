package com.sdk.okhttp.callback;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sdk.okhttp.listener.DownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**********************************************************
 * @文件名称：CommonFileCallback.java
 * @文件作者：renzhiqiang
 * @创建时间：2016年1月23日 下午5:32:01
 * @文件描述：专门处理文件下载回调
 * @修改历史：2016年1月23日创建初始版本
 **********************************************************/
public class DownloadCallback implements Callback {
	private static final String DEFAULT_SAVE_PATH=Environment.getExternalStorageDirectory()+"/cache/";
	/**
	 * 将其它线程的数据转发到UI线程
	 */
	private static final int PROGRESS_MESSAGE = 0x01;
	private Handler mDeliveryHandler;
	private DownloadListener mListener;
	private String mFilePath;
	private int mProgress;

	public DownloadCallback(DownloadListener listener,String filePath) {
		this.mListener = listener;
		this.mFilePath = filePath;
		this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case PROGRESS_MESSAGE:
					mListener.onProgress((int) msg.obj);
					break;
				}
			}
		};
	}

	public DownloadCallback(String fileName,DownloadListener listener) {
		this(listener,DEFAULT_SAVE_PATH+fileName);
	}

	@Override
	public void onFailure(final Call call, final IOException e) {
		mDeliveryHandler.post(new Runnable() {
			@Override
			public void run() {
				mListener.onFailure(e);
			}
		});
	}

	@Override
	public void onResponse(Call call, Response response){
		try {
			final File file = handleResponse(response);
			mDeliveryHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (file != null) {
                        mListener.onSuccess(file);
                    } else {
                        mListener.onFailure(new Exception("download file is null"));
                    }
                }
            });
		} catch (Exception e) {
			mListener.onFailure(e);
		}
	}

	/**
	 * 此时还在子线程中，不则调用回调接口
	 * 
	 * @param response
	 * @return
	 */
	private File handleResponse(Response response) {
		if (response == null) {
			return null;
		}

		InputStream inputStream = null;
		File file = null;
		FileOutputStream fos = null;
		byte[] buffer = new byte[2048];
		int length = -1;
		int currentLength = 0;
		double sumLength = 0;
		try {
			checkLocalFilePath(mFilePath);
			file = new File(mFilePath);
			fos = new FileOutputStream(file);
			inputStream = response.body().byteStream();
			sumLength = (double) response.body().contentLength();

			while ((length = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
				currentLength += length;
				mProgress = (int) (currentLength* 100 / sumLength);
				mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
			}
			fos.flush();
		} catch (Exception e) {
			file = null;
		} finally {
			try {
				fos.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	//用来检查文件路径是否已经存在
	private void checkLocalFilePath(String localPath) {
		File dir=new File(localPath.substring(0,localPath.lastIndexOf("/")+1));
		if(!dir.exists()){
			dir.mkdir();
		}
		File file=new File(localPath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}