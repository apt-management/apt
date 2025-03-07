let buzzerState = 0;  // 초기 상태는 꺼짐 (0: OFF, 1: ON)

const ros_buz = new ROSLIB.Ros({
    url: `ws://${window.ROBOT_IP}:9090`  // WebSocket 주소 사용
});

// WebSocket 연결 시 상태 출력
ros_buz.on('connection', function() {
    console.log('[Publisher] Connected to ROS server');
});

// WebSocket 연결 종료 시 상태 출력
ros_buz.on('close', function() {
    console.log('[Publisher] Connection closed');
});

// 부저 제어 서비스 객체 생성
const buzzerService = new ROSLIB.Service({
    ros: ros_buz,
    name: BUZZER_SERVICE,  // 부저 서비스 이름
    serviceType: 'jetbotmini_msgs/Buzzer'  // 부저 서비스 타입
});

// 부저 상태 토글 함수
function toggleBuzzer() {
    buzzerState = (buzzerState === 0) ? 1 : 0;  // 부저 상태를 0에서 1로 또는 1에서 0으로 토글

    const request = new ROSLIB.ServiceRequest({
        buzzer: buzzerState  // 'buzzer' 필드를 사용하여 상태 전송
    });

    buzzerService.callService(request, function(response) {
        if (response.result) {
            console.log('Buzzer toggled:', buzzerState === 1 ? 'ON' : 'OFF');
            updateBuzzerStatus(buzzerState);  // 상태 업데이트
        } else {
            console.log('Buzzer toggle failed');
            updateBuzzerStatus(null);  // 실패 시 상태 갱신
        }
    });
}

// 부저 상태 업데이트 함수
function updateBuzzerStatus(state) {
    const buzzerStatusElement = document.getElementById("buzzer-status");

    if (state === 1) {
        buzzerStatusElement.textContent = "Buzzer is ON";
        playBuzzerSound();  // 부저 음 울리기
    } else if (state === 0) {
        buzzerStatusElement.textContent = "Buzzer is OFF";
    } else {
        buzzerStatusElement.textContent = "Failed to control Buzzer";
    }
}