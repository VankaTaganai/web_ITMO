package ru.itmo.wp.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class StatusForm {
    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    private long userId;

    @AssertTrue
    boolean isCorrectValue() {
        return "Enable".equals(status) || "Disable".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
