package com.magnus.authapi.token;

import com.magnus.authapi.common.BaseEntity;
import com.magnus.authapi.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserToken extends BaseEntity {
  @Id
  @SequenceGenerator(
      name = "token_sequence",
      sequenceName = "token_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "token_sequence"
  )
  private Long id;

  @ManyToOne()
  @JoinColumn(name="user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType;

  @Column(length = 512)
  private String token;

  private boolean valid = true;

  private LocalDateTime expiryDate;

  public UserToken(User user, TokenType tokenType, String token) {
    this.user = user;
    this.tokenType = tokenType;
    this.token = token;
  }
}
