// ROS 연결을 위한 WebSocket 설정
const ros_auto = new ROSLIB.Ros({
  url: `ws://${window.ROBOT_IP}:9090`  // WebSocket 주소 사용
});

// WebSocket 연결 시 상태 출력
ros_auto.on('connection', function() {
  console.log('[Publisher] Connected to ROS server');
});

// WebSocket 연결 종료 시 상태 출력
ros_auto.on('close', function() {
  console.log('[Publisher] Connection closed');
});

const AutoService = new ROSLIB.Service({
  ros: ros_auto,
  name: LINE_TRACING_SERVICE,
  serviceType: 'jetbotmini_msgs/SetAuto'
});

let isAutoMode = false;  // 초기 상태는 라인트레이싱이 비활성화

// 라인트레이싱 시작 및 중지 함수
function setAuto(start) {
  var request = new ROSLIB.ServiceRequest({
    start: start  // 'start'를 true 또는 false로 설정
  });

  AutoService.callService(request, function(result) {
    console.log('Service call result:', result);
  });
}

// 버튼 클릭 시 라인트레이싱 시작/중지 토글
document.getElementById('toggleButton').addEventListener('click', function() {
  isAutoMode = !isAutoMode;  // 상태를 토글

  // 라인트레이싱 시작 또는 멈춤
  if (isAutoMode) {
    setAuto(true);  // 라인트레이싱 시작
    document.getElementById('toggleButton').textContent = 'Stop Line Tracing';  // 버튼 텍스트 변경
  } else {
    setAuto(false);  // 라인트레이싱 멈춤
    document.getElementById('toggleButton').textContent = 'Start Line Tracing';  // 버튼 텍스트 변경
  }
});