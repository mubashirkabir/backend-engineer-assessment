package com.midas.app.providers.external.stripe;

import com.midas.app.models.Account;
import com.midas.app.models.ProviderType;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.stripe.exception.StripeException;
import com.stripe.param.AccountCreateParams;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class StripePaymentProvider implements PaymentProvider {
  private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);

  private final StripeConfiguration configuration;

  /** providerName is the name of the payment provider */
  @Override
  public ProviderType providerName() {
    return ProviderType.STRIPE;
  }

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Optional<Account> createAccount(CreateAccount details) {
    try {
      AccountCreateParams params =
          AccountCreateParams.builder()
              .setType(AccountCreateParams.Type.STANDARD)
              .setCountry("US")
              .setEmail("example@example.com")
              .build();

      com.stripe.model.Account account = com.stripe.model.Account.create(params);

      return Optional.of(
          Account.builder()
              .id(details.getUserId())
              .firstName(details.getFirstName())
              .lastName(details.getLastName())
              .email(details.getEmail())
              .providerType(ProviderType.STRIPE)
              .providerId(account.getId())
              .build());

    } catch (StripeException e) {
      logger.error("error while creating stripe account for the user id " + details.getUserId());
      return Optional.empty();
    }
  }
}
