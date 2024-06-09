package com.backend.backend.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backend.backend.domain.AttendStatus;
import com.backend.backend.dto.QRcodeRequest;
import com.backend.backend.entity.Meeting;
import com.backend.backend.entity.User;
import com.backend.backend.repository.MeetingRepository;
import com.backend.backend.repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


@Service
public class QRcodeService {

    // @Autowired
    // private AttendRepository attendRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    // @Autowired
    // private ParticipantRepository participantRepository;
    
    public ResponseEntity<byte[]> createQR(Long meetingId,QRcodeRequest dto, String email) throws Exception {

        User user = userRepository.findByEmail(email).orElse(null);
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        LocalDate date = dto.getDate();
        LocalDateTime attendanceTime = dto.getAttendanceTime();
        LocalDateTime lateTime = dto.getLateTime();

        int width = 200;
        int height = 200;

        if (meeting.getUser().getId() != user.getId()){
            throw new IllegalArgumentException("모임 주최 권한이 없습니다.");
        }

        // 현재 시간 가져오기
        LocalDateTime currentTime = LocalDateTime.now();
        AttendStatus attendStatus;
        // 출석 시간과 현재 시간 비교
        if (currentTime.isBefore(attendanceTime)) {
            // 출석 시간 이전인 경우 출석 처리
            attendStatus = AttendStatus.PRESENT;
        } else if (currentTime.isBefore(lateTime)) {
            // 지각 시간 이전인 경우 지각 처리
            attendStatus = AttendStatus.LATE;
        } else {
            // 지각 시간 이후인 경우 결석 처리
            attendStatus = AttendStatus.ABSENCE;
        }

        if(attendStatus==AttendStatus.ABSENCE){
            throw new IllegalStateException("출석 시간이 만료되어 큐알코드를 생성할 수 없습니다.");
        }
        // 만료 시간 설정: QR 코드 생성 후 15초 후
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(600);
        // QR 코드 생성
        
        // QR 코드 생성에 사용할 텍스트 생성
        String text = "MeetingId: " + meetingId + ", Date: " + date.toString() + ", AttendStatus: " + attendStatus.toString() + ", ExpirationTime: " + expirationTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);

        // 바이트 배열로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(encode, "PNG", outputStream);
        byte[] qrImageBytes = outputStream.toByteArray();

        // 생성된 QR 코드 반환
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImageBytes);
    }

    
}
