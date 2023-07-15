package com.magnus.authenticationapi.token;

import com.magnus.authenticationapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional()
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
  Optional<UserToken> findByToken(String token);

  Optional<UserToken> findByUserAndValid(User user, Boolean valid);

  Optional<UserToken> findByUserAndValidAndToken(User user, Boolean valid, String string);

  @Modifying
  @Query("update UserToken u set u.valid = ?1 where u.id = ?2")
  void setValidById(boolean valid, Long tokenId);
}
