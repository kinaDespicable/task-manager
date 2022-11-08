package dev.taskManager.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse{

    @JsonProperty("message")
    private String message;

    protected ErrorResponse(Builder builder) {
        super(builder);
        this.message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseResponse.Builder<Builder> {

        private String message;

        @Override
        public Builder getThis() {
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

    }
}
