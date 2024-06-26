package com.backend.backend.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.domain.AttendStatus;
import com.backend.backend.dto.AttendRequest;
import com.backend.backend.entity.Attend;
import com.backend.backend.service.AttendService;

@RestController
@RequestMapping("/api")
public class ScanController {

    @Autowired
    private AttendService attendService;

    @PostMapping("/meetings/scan-qr")
    
    public ResponseEntity<String> scanQRCode(@RequestBody String scannedQRCode, Principal principal) {
        try {
            String email = principal.getName();//참여자
            // 스캔된 큐알 코드에서 정보 추출
            String[] qrCodeInfo = scannedQRCode.replaceAll("^\"|\"$", "").split(", ");
            Long meetingId = null;
            LocalDate date = null;
            LocalDateTime expirationTime = null; // 만료 시간 변수 추가
            AttendStatus attendStatus = null;

            for (String info : qrCodeInfo) {
                String[] keyValue = info.split(": ");
                if (keyValue.length != 2) {
                    return ResponseEntity.badRequest().body("잘못된 QR 코드 형식입니다.");
                }
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                
                /*
                if (key.equals("MeetingId")) {
                    meetingId = Long.parseLong(value);
                } else if (key.equals("Date")) {
                    date = LocalDate.parse(value);
                
                } else if (key.equals("AttendStatus")) {
                    attendStatus = AttendStatus.valueOf(value);
                } else if (key.equals("Expiration")) { // 만료 시간 정보 추출
                    expirationTime = LocalDateTime.parse(value,DateTimeFormatter.ISO_DATE_TIME);
                } */
                switch (key) {
                    case "MeetingId":
                        meetingId = Long.parseLong(value);
                        //System.out.println(meetingId);
                        break;
                    case "Date":
                        date = LocalDate.parse(value);
                        //System.out.println(date);
                        break;
                    case "AttendStatus":
                        attendStatus = AttendStatus.valueOf(value);
                        //System.out.println(attendStatus);
                        break;
                    case "ExpirationTime":
                        expirationTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                        //System.out.println(expirationTime);
                        break;
                    default:
                        return ResponseEntity.badRequest().body("잘못된 QR 코드 키입니다: " + key);
                }
            }

            // 만료 시간 확인: 만료 시간이 현재 시간보다 이전이면 만료된 것으로 처리
            if (expirationTime != null && LocalDateTime.now().isAfter(expirationTime)) {
                return ResponseEntity.badRequest().body("스캔된 QR 코드가 만료되었습니다.");
            }

            AttendRequest dto = new AttendRequest(null,date,attendStatus);
            Attend checked = attendService.checkIn(meetingId, dto, email);

            return ResponseEntity.ok().body("출석체크가 완료되었습니다. 출석상태: "+attendStatus.toString());
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<String>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
