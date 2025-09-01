package com.example.ministop.payload.response;

// Class DataResponse chính là một wrapper class (lớp bao bọc) để chuẩn hóa dữ liệu trả về từ API.
public class DataResponse {
    private int statusCode; // HTTP status code (ví dụ: 200, 400, 404, 500 …)
    private boolean isSuccess; // Cho biết API call thành công hay thất bại (true/false)
    private String description; // Mô tả ngắn gọn (ví dụ: "Get product list successfully" hoặc "Product not found")
    private Object data; // Chứa dữ liệu trả về thực sự (có thể là 1 object, list, hoặc null)

    public int getStatusCode() {
        return statusCode;
}

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
