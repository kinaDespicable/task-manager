package dev.taskManager.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedResponse extends BaseResponse{

    @JsonProperty("data")
    private Object createdObject;

    protected CreatedResponse(Builder builder) {
        super(builder);
        this.createdObject = builder.createdObject;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder extends BaseResponse.Builder<Builder>{

        private Object createdObject;

        public Builder data(Object o){
            this.createdObject = o;
            return this;
        }

        public CreatedResponse build(){
            return new CreatedResponse(this);
        }

        @Override
        public Builder getThis() {
            return this;
        }
    }
}
