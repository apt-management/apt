package com.login.login.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*") // CORS 오류시 해결법
@Controller
public class GateController {

    @GetMapping("/gate_control")
    public ResponseEntity<String> controlGate(@RequestParam String cmd) {
        if ("up".equalsIgnoreCase(cmd)) {
            return ResponseEntity.ok("Gate Raised");
        } else if ("lower".equalsIgnoreCase(cmd)) {
            return ResponseEntity.ok("Gate Closed");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Command");
        }
    }
}
