package pl.aczek.exchange.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.aczek.exchange.account.exception.AccountAlreadyExistsException;
import pl.aczek.exchange.account.exception.AccountNotFoundException;
import pl.aczek.exchange.account.exception.CurrencyAccountNotFoundException;
import pl.aczek.exchange.account.exception.InsufficientFundsException;
import pl.aczek.exchange.account.exception.InvalidOperationException;
import pl.aczek.exchange.exchange.rate.exception.ExchangeRateNotFoundException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(value = ExchangeRateNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ApiError exchangeRateNotFoundExceptionHandler(ExchangeRateNotFoundException ex,
      WebRequest webRequest) {
    log.error("Exchange rate not found, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }

  @ExceptionHandler(value = AccountNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ApiError accountNotFoundExceptionHandler(AccountNotFoundException ex,
      WebRequest webRequest) {
    log.error("Account not found, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }

  @ExceptionHandler(value = CurrencyAccountNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ApiError currencyAccountNotFoundHandler(CurrencyAccountNotFoundException ex,
      WebRequest webRequest) {
    log.error("Currency account not found, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }

  @ExceptionHandler(value = AccountAlreadyExistsException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public ApiError accountAlreadyExistsExceptionHandler(AccountAlreadyExistsException ex,
      WebRequest webRequest) {
    log.error("Account already in database, creation failed, ex {}, request {}", ex, webRequest,
        ex);
    return ApiError.from(ex);
  }

  @ExceptionHandler(value = InsufficientFundsException.class)
  @ResponseStatus(code = HttpStatus.FORBIDDEN)
  public ApiError insufficientFundsExceptionHandler(InsufficientFundsException ex,
      WebRequest webRequest) {
    log.error("Insufficient funds on currency account, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }

  @ExceptionHandler(value = InvalidOperationException.class)
  @ResponseStatus(code = HttpStatus.FORBIDDEN)
  public ApiError invalidOperationExceptionHandler(InvalidOperationException ex,
      WebRequest webRequest) {
    log.error("Insufficient funds on currency account, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }


  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError runtimeExceptionHandler(RuntimeException ex, WebRequest webRequest) {
    log.error("Runtime exception, ex {}, request {}", ex, webRequest, ex);
    return ApiError.from(ex);
  }


}
