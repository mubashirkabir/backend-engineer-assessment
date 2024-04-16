package com.midas.app.providers.payment;

import com.midas.app.models.Account;
import com.midas.app.models.ProviderType;
import java.util.Optional;

public interface PaymentProvider {

  /** providerName is the name of the payment provider */
  ProviderType providerName();

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Optional<Account> createAccount(CreateAccount details);
}
