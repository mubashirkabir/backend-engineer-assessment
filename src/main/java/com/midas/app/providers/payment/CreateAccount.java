package com.midas.app.providers.payment;

import java.util.UUID;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateAccount {
  private UUID userId;
  private String firstName;
  private String lastName;
  private String email;
}
