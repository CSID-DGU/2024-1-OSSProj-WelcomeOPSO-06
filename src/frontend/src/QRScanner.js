import React, { useState } from "react";
import Reader from "./QRScan.tsx";
const QRScanner = (props) => {
  const [data, setData] = useState("No result");

  return (
    <div>
      <Reader></Reader>
    </div>
  );
};

export default QRScanner;
