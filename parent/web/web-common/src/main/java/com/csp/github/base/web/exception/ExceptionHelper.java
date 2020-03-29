package com.csp.github.base.web.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 陈少平
 * @date 2020-03-20 20:06
 */
@Slf4j
public class ExceptionHelper {
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new DiscardOldestPolicy());

    public static void logException(Throwable e) {
        threadPool.execute(() -> log.error(getMessage(e)));
    }

    public static String getMessage(Throwable e) {
        String str = null;
        try (StringWriterEnhance sw = new StringWriterEnhance(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            str = sw.toString();
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
        }
        return str;
    }

}
