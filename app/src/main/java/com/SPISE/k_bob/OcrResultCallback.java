// OcrResultCallback.java
package com.SPISE.k_bob;

public interface OcrResultCallback {
    void onSuccess(String result);
    void onError(String error);
}