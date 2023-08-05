package com.magnus.authapi.upload;

import com.fasterxml.jackson.databind.JsonNode;

import com.magnus.authapi.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UploadService {

  private final ApiProperties apiProperties;
  public String uploadFile(MultipartFile file) {
    RestTemplate restTemplate = new RestTemplate();

    String fileServerUrl = apiProperties.getFileServerUrl();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", file.getResource());

    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
    ResponseEntity<JsonNode> response = restTemplate.postForEntity(fileServerUrl + "/api/v1/upload", entity, JsonNode.class);
    JsonNode map = response.getBody();
    return Objects.requireNonNull(map).get("fileName").asText();
  }
}
