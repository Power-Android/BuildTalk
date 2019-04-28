
package com.bjjy.buildtalk.core.http.interceptor;
import com.bjjy.buildtalk.utils.NetworkUtils;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author power
 * @date 2019/4/25 9:00 AM
 * @project BuildTalk
 * @description:
 */
public class OfflineCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected()) {
            int offlineCacheTime = 60 * 60 * 24 * 28;//离线的时候的缓存的过期时间,4周
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                    .removeHeader("Pragma")
                    .build();
        }
        return chain.proceed(request);
    }
}
