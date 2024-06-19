import {
  BarcodeFormat,
  BrowserMultiFormatReader,
  DecodeHintType,
} from "@zxing/library";
import { useEffect, useRef, useState } from "react";

const Reader = () => {
  const [localStream, setLocalStream] = useState<MediaStream>();
  const Camera = useRef<HTMLVideoElement>(null);
  const hints = new Map();
  const formats = [
    BarcodeFormat.QR_CODE,
    BarcodeFormat.DATA_MATRIX,
    BarcodeFormat.CODE_128,
    BarcodeFormat.CODABAR,
    BarcodeFormat.EAN_13,
    BarcodeFormat.EAN_8,
    BarcodeFormat.CODE_39,
    BarcodeFormat.CODE_93,
  ];
  hints.set(DecodeHintType.POSSIBLE_FORMATS, formats);
  const Scan = new BrowserMultiFormatReader(hints, 500);

  useEffect(() => {
    navigator.mediaDevices
      .getUserMedia({
        video: { facingMode: "user" }, //전면
        // video: { facingMode: { exact: "environment" } }, //후면
      })
      .then((stream) => {
        console.log(stream);
        setLocalStream(stream);
      });
    return () => {
      Stop();
    };
  }, []);
  useEffect(() => {
    if (!Camera.current) return;
    if (localStream && Camera.current) {
      Scanning();
    }
    return () => {
      Stop();
    };
  }, [localStream]);
  const req = useRef<any>();
  const Scanning = async () => {
    // const t = await Scan.decodeOnce();
    console.log("scan");
    if (localStream && Camera.current) {
      try {
        const data = await Scan.decodeFromStream(
          localStream,
          Camera.current,
          (data, err) => {
            if (data) {
              setText(data.getText());
              // Scan.stopContinuousDecode();
            }
          }
        );
      } catch (error) {
        console.log(error);
      }
    }
  };
  const Stop = () => {
    if (localStream) {
      const vidTrack = localStream.getVideoTracks();
      vidTrack.forEach((track) => {
        localStream.removeTrack(track);
      });
    }
  };
  const [text, setText] = useState("");
  const sendTextToBackend = () => {
    if (localStream) {
      const vidTrack = localStream.getVideoTracks();
      vidTrack.forEach((track) => {
        localStream.removeTrack(track);
      });
    }
    // Here you can make a fetch request to send the text to the backend API
    fetch("/api/meetings/scan-qr", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify( text ), // Assuming your backend expects the text in JSON format
    })
      .then((response) => {
        if (response.ok) {
          alert("출석이 완료되었습니다.");
        } else {
          console.error("Failed to send text");
          alert("만료된 코드입니다.");
        }
      })
      .catch((error) => {
        console.error("Error sending text:", error);
        alert("만료된 코드입니다.");
      });
  };

  return (
    <div>
      <video ref={Camera} id="video" />
      <span>{text}</span>
      <button onClick={sendTextToBackend}>전송하기</button>
    </div>
  );
};
export default Reader;
