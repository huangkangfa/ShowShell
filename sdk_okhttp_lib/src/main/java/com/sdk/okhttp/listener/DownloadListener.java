package com.sdk.okhttp.listener;

/**
 * @author vision
 * @function 监听下载进度
 */
public interface DownloadListener extends CallbackBaseListener {
	public void onProgress(int progress);
}
