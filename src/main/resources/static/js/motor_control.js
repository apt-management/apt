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