package pl.aczek.exchange.account.controller.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiError {

  private String message;

  public static ApiError from(RuntimeException runtimeException) {
    return ApiError.builder().message(runtimeException.getMessage()).build();
  }

}
