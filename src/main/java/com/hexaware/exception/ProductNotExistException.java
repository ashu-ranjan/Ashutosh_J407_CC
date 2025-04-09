package com.hexaware.exception;

public class ProductNotExistException extends RuntimeException {
  public ProductNotExistException(String message) {
    super(message);
  }

  public ProductNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductNotExistException(Throwable cause) {
    super(cause);
  }

  public ProductNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ProductNotExistException() {
  }
}
