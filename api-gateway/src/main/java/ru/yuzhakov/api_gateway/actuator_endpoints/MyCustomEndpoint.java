package ru.yuzhakov.api_gateway.actuator_endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "customEndpoint")
public class MyCustomEndpoint {

    @ReadOperation
    public CustomResponse customMethod() {
        return new CustomResponse("Everything is awesome!", 42);
    }

    public static class CustomResponse {
        private String message;
        private int number;

        public CustomResponse(String message, int number) {
            this.message = message;
            this.number = number;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }


}
