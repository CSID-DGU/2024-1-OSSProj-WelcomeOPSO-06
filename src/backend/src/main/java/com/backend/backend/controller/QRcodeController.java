package com.backend.backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.QRcodeRequest;
import com.backend.backend.service.QRcodeService;

@RestController
@RequestMapping("/api")
public class QRcodeController {

    @Autowired
    private QRcodeService qrcodeService;
    
    @PostMapping("/meetings/{meetingId}/qr-code")
    public ResponseEntity<byte[]> qrGenerate(@PathVariable Long meetingId, @RequestBody QRcodeRequest dto,Principal principal) {
    
        try {
        String email = principal.getName();
        ResponseEntity<byte[]> qrCodeImage = qrcodeService.createQR(meetingId, dto, email);
        return qrCodeImage;
        } catch (Exception e) {
            // QR 코드 생성 중 오류가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}

