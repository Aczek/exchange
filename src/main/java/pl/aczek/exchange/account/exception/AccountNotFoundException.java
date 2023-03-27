package pl.aczek.exchange.account.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
    super("Account not found in database");
  }
}
