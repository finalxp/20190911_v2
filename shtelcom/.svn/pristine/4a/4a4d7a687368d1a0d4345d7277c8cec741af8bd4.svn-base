package cn.productivetech.shtelcom.enrol.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

	private static OkHttpClient okHttpClient = null;
	private static final Object locker = new Object();

	// 通过单例模式获取实例
	public static OkHttpClient getInstance() {

		if (okHttpClient == null) {

			// 同步代码
			synchronized (locker) {

				if (okHttpClient == null) {
					okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
							.writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
							.connectionPool(new ConnectionPool(50, 5, TimeUnit.MINUTES)).build();
				}
			}
		}
		return okHttpClient;
	}

	public static Response postRequest(String url, String data) throws IOException {
		OkHttpClient client = HttpUtil.getInstance();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, data);

		Request request = new Request.Builder().url(url).post(body).build();

		Call call = client.newCall(request);
		return call.execute();
	}
}
