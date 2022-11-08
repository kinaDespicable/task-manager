package dev.taskManager.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletedResponse extends BaseResponse{

    @JsonProperty("deleted")
    private Object deletedObject;

    protected DeletedResponse(Builder builder) {
        super(builder);
        this.deletedObject = builder.deletedObject;
    }

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder extends BaseResponse.Builder<Builder>{

        private Object deletedObject;

        public Builder deleted(Object o){
            this.deletedObject = o;
            return this;
        }

        public DeletedResponse build(){
            return new DeletedResponse(this);
        }

        @Override
        public Builder getThis() {
            return this;
        }
    }

}
