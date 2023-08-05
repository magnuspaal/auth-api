package com.magnus.authenticationapi.controllers;

import com.magnus.authenticationapi.auth.AuthenticationService;
import com.magnus.authenticationapi.auth.dto.AuthenticationResponse;
import com.magnus.authenticationapi.config.ApiProperties;
import com.magnus.authenticationapi.upload.UploadService;
import com.magnus.authenticationapi.upload.dto.UploadImageResponse;
import com.magnus.authenticationapi.user.User;
import com.magnus.authenticationapi.user.UserRepository;
import com.magnus.authenticationapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UploadService uploadService;
  private final AuthenticationService authenticationService;
  private final UserService userService;
  private final UserRepository userRepository;

  @PostMapping("/{id}/upload-image")
  public ResponseEntity<User> uploadImage(
      @PathVariable Long id,
      @RequestParam(name = "image") MultipartFile image
  ) {
    User user = authenticationService.getAuthenticatedUser();
    if (!Objects.equals(user.getId(), id)) {
      return ResponseEntity.status(403).build();
    }
    String fileName = uploadService.uploadFile(image);
    User dbUser = userService.getUserById(user.getId());
    dbUser.setImageName(fileName);
    System.out.println(dbUser);
    return ResponseEntity.ok(userRepository.save(dbUser));
  }
}
