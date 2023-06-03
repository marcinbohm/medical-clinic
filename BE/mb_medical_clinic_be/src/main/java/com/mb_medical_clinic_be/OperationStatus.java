package com.mb_medical_clinic_be;

import java.util.ArrayList;
import java.util.List;

public class OperationStatus {

    private String targetClassName;
    private Integer recordId;
    private String operationName;
    private Boolean success;
    private List<String> msgList;


    public OperationStatus() {
    }

    public OperationStatus(String targetClassName, String operationName) {
        this.targetClassName = targetClassName;
        this.operationName = operationName;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public OperationStatus setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
        return this;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public OperationStatus setRecordId(Integer recordId) {
        this.recordId = recordId;
        return this;
    }

    public String getOperationName() {
        return operationName;
    }

    public OperationStatus setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public OperationStatus setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public List<String> getMsgList() {
        // lazy init
        if(msgList == null) msgList = new ArrayList<String>();
        return msgList;
    }

    public OperationStatus addMessage(String message) {
        getMsgList().add(message);
        return this;
    }
}

