package com.midas.app.activities;

import com.midas.app.exceptions.ApiException;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final PaymentProvider stripePaymentProvider;
  private final AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {

    Optional<Account> newCreatedAccount =
        stripePaymentProvider.createAccount(
            CreateAccount.builder()
                .userId(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .build());

    if (newCreatedAccount.isEmpty()) {
      throw new ApiException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "account creation failed while creating payment id for the user");
    }

    return newCreatedAccount.get();
  }
}
