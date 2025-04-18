package dev.hudsonprojects.api.common.lib;

import dev.hudsonprojects.api.common.lib.util.ThreadUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RetryableExecutor {

    public static final BiFunction<Long, Integer, Long> DELAY_MULTIPLIED_BY_ATTEMPTS_POLICY = (currentAttempt, delay) -> (currentAttempt+1) * delay;

    private final List<Class<? extends Exception>> exceptions;
    private final long delayMillis;
    private final int maxAttempts;
    private final BiFunction<Long, Integer, Long> delayPolicy;

    private RetryableExecutor(Builder builder) {
        this.exceptions = new ArrayList<>(builder.exceptions);
        this.delayMillis = builder.delayMillis;
        this.maxAttempts = builder.maxAttempts;
        this.delayPolicy = builder.delayPolicy;
    }

    public <T> T get(Callable<T> callable, Consumer<Exception> exceptionLogger) throws Exception {
        Exception lastException = null;
        for(int i = 0; i < maxAttempts; i++){
            try {
                return callable.call();
            } catch (Exception e){
                lastException = e;
                boolean retry = shouldRetry(i, exceptionLogger, e);
                if(!retry){
                    throw e;
                }
            }
        }

        throw lastException != null ? lastException : new Exception();
    }

    private boolean shouldRetry(int currentAttempt, Consumer<Exception> exceptionLogger, Exception e) {
        if(currentAttempt == maxAttempts - 1){
            return false;
        }
        boolean retry = false;
        for(var supportedException : exceptions){
            if(supportedException.isAssignableFrom(e.getClass())){
                retry = true;
                if(exceptionLogger != null){
                    exceptionLogger.accept(e);
                }
                long delay = delayMillis;
                if(delayPolicy != null){
                    delay = delayPolicy.apply(delay, currentAttempt);
                }
                ThreadUtil.delay(delay);
                break;
            }
        }
        return retry;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long delayMillis = 0;
        private int maxAttempts = 3;
        private final List<Class<? extends Exception>> exceptions = new ArrayList<>();
        private BiFunction<Long, Integer, Long> delayPolicy;

        private Builder() {}

        public Builder delayMillis(long delayMillis) {
            this.delayMillis = delayMillis;
            return this;
        }

        public Builder maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder supportedExceptions(Collection<Class<? extends Exception>> supportedExceptions) {
            this.exceptions.addAll(supportedExceptions);
            return this;
        }

        @SafeVarargs
        public final Builder supportedExceptions(Class<? extends Exception> ... supportedExceptions) {
            this.exceptions.addAll(List.of(supportedExceptions));
            return this;
        }

        public Builder delayPolicy(BiFunction<Long, Integer, Long> delayPolicy) {
            this.delayPolicy = delayPolicy;
            return this;
        }

        public RetryableExecutor build() {
            return new RetryableExecutor(this);
        }
    }
}
