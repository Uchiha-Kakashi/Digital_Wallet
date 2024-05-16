package main.java.com.wallet.entities;

import java.util.Date;

public class Transaction {
    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer amount;
    private Boolean isSuccess;
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public String toString(String fromUsername, String toUsername){
        return "Transaction (" + fromUsername + " --> " + toUsername
                + ") Rs. " + this.getAmount() + " at " + this.getTime();
    }
}
