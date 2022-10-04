package com.qzk.contentservice.exception;

/**
 * @Description 异常类
 * @Date 2022-10-04-14-32
 * @Author qianzhikang
 */
public class SecurityException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SecurityException(String message) {
        super(message);
    }
}
