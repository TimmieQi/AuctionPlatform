package com.example.auctionplatform.service;

public class Response <T>{
    private T data;
    private boolean isSuccess;
    private int errorCode;
    public String message;

    public static <K> Response<K> newSuccess(K data,String message){
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }
    public static Response<Void> newError(String errorMessage){
        Response<Void> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMessage);
        return response;
    }
    /**
     * 类似Response<Void> newError(String errorMessage)，但是K data会被设置为null
     */
    public static <K>Response<K> newErrorWithEmptyReturn(String errorMessage){
        Response<K> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMessage);
        response.setData(null);
        return response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
