// ROS 연결 (출력)
const ros_mot = new ROSLIB.Ros({
    url: `ws://${window.ROBOT_IP}:9090`  // config.js에서 설정된 ROBOT_IP 사용
});

// 연결 시, 상태 출력
ros_mot.on('connection', function() {
    console.log('[Publisher] Connected to ROS server');
});

// 연결 종료 시, 상태 출력
ros_mot.on('close', function() {
    console.log('[Publisher] Connection closed');
});


// 모터 제어를 위한 ROS 토픽 설정
const motorControlClient = new ROSLIB.Service({
    ros: ros_mot,
    name: MOTOR_CONTROL_SERVICE,  // 사용되는 실제 토픽 이름
    serviceType: "jetbotmini_msgs/MotorControl"  // 서비스 타입
});


function sendCommand(command) {
    var request = new ROSLIB.ServiceRequest({
        command: command
    });

    motorControlClient.callService(request, function(result) {
        console.log('Result from service call:', result.data);  // 서비스 호출 결과 출력
    });

    console.log('Sent command:', command);
}

// 기존 sendCommand 함수는 그대로 사용

const activeKeys = new Set();  // 현재 눌린 키를 추적

document.addEventListener('keydown', function(event) {
    if (activeKeys.has(event.key)) return;  // 중복 호출 방지
    activeKeys.add(event.key);  // 키 등록

    switch(event.key.toLowerCase()) {  // 대소문자 구분 없이 처리
        case 'w':
            sendCommand('forward');
            break;
        case 's':
            sendCommand('backward');
            break;
        case 'a':
            sendCommand('left');
            break;
        case 'd':
            sendCommand('right');
            break;
        case ' ': // 스페이스바: 정지 명령
            event.preventDefault();  // 스크롤 방지
            sendCommand('stop');
            break;
        case 'f': // 빠른 속도
            sendCommand('speed fast');
            break;
        case 'l': // 느린 속도 (기존 's'는 뒤로 가는 키와 충돌하므로 'l'로 변경)
            sendCommand('speed slow');
            break;
        case 'n': // 보통 속도
            sendCommand('speed normal');
            break;
        default:
            break;
    }
});

// 키를 떼면 activeKeys에서 제거
document.addEventListener('keyup', function(event) {
    activeKeys.delete(event.key);
});
