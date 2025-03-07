window.onload = function() {
    // ROS 연결 설정
    const ros_vol = new ROSLIB.Ros({
        url: window.ROSCAR_WS_URL // config.js에서 지정한 WebSocket 주소 사용
    });

    // ROS 연결 성공 시
    ros_vol.on('connection', function() {
        console.log('[Subscriber] Connected to ROS server');
    });

    // ROS 연결 끊겼을 때
    ros_vol.on('close', function() {
        console.log('[Subscriber] Connection closed');
    });

    // 배터리 상태 토픽 구독
    const voltageTopic = new ROSLIB.Topic({
        ros: ros_vol,
        name: BATTERY_TOPIC,  // config.js에서 설정한 배터리 토픽 이름 사용
        messageType: 'jetbotmini_msgs/Battery'  // 메시지 타입
    });

    // 배터리 토픽에서 수신된 데이터 처리
    voltageTopic.subscribe(function(message) {
        const voltage = message.Voltage;
        console.log('[Subscriber] Received voltage:', voltage);

        // 전압을 9.6V ~ 12.6V 범위로 백분위수로 변환
        let percentage = 0;
        if (voltage >= 9.6 && voltage <= 12.6) {
            percentage = ((voltage - 9.6) / (12.6 - 9.6)) * 100;
        } else if (voltage > 12.6) {
            percentage = 100;  // 12.6V 이상은 100%로 처리
        } else {
            percentage = 0;  // 9.6V 이하로 떨어지면 0%로 처리
        }

        // 화면에 전압과 백분위 표시
        const voltageElement = document.getElementById('voltageDisplay');
        voltageElement.innerText = ` ${percentage.toFixed(2)}% (${voltage.toFixed(2)} V)`;

        // 전압 상태에 따라 텍스트 색상 변경
        if (voltage >= 12.6) {
            voltageElement.style.color = 'green';
        } else if (voltage >= 10.0) {
            voltageElement.style.color = 'orange';
        } else {
            voltageElement.style.color = 'red';
        }
    });
};
