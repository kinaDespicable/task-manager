package dev.taskManager.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
@SuppressWarnings("all")
public class BaseResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("status_code")
    private int statusCode;

    protected BaseResponse(Builder builder){
        this.timestamp = builder.timestamp;
        this.success = builder.success;
        this.status = builder.status;
        this.statusCode = builder.statusCode;
    }

    public static Builder builder(){
        return new Builder() {
            @Override
            public Builder getThis() {
                return this;
            }
        };
    }

    public abstract static class Builder<T extends Builder<T>> {

        private Instant timestamp;

        private boolean success;

        private HttpStatus status;

        private int statusCode;

        public abstract T getThis();

        public T timestamp(Instant i) {
            this.timestamp = i;
            return this.getThis();
        }

        public T success(boolean success) {
            this.success = success;
            return this.getThis();
        }

        public T status(HttpStatus status) {
            this.status = status;
            return this.getThis();
        }

        public T statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this.getThis();
        }

        public BaseResponse build() {
            return new BaseResponse(this);
        }
    }
}
