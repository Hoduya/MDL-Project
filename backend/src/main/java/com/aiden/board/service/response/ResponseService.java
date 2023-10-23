package com.aiden.board.service.response;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aiden.board.dto.ApiResponse.CommonResponse;
import com.aiden.board.dto.ApiResponse.ListDataResponse;
import com.aiden.board.dto.ApiResponse.SingleDataResponse;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
public class ResponseService {
	
	@AllArgsConstructor
	private enum Status {
		SUCCESS("success", "성공"),
	    FAIL("fail", "실패"),
	    ERROR("error", "에러");
		
		private String type;
		private String message;
	}

    // 단일 데이터 결과 처리 메소드
    public <T> SingleDataResponse<T> getSingleDataResponse(T data) {
        SingleDataResponse<T> result = new SingleDataResponse<>();
        result.setData(data);
        setSuccessResponse(result);
        return result;
    }

    // 리스트 데이터 결과 처리 메서드
    public <T> ListDataResponse<T> getListDataResponse(List<T> list) {
        ListDataResponse<T> result = new ListDataResponse<>();
        result.setData(list);
        setSuccessResponse(result);
        return result;
    }

    // 성공 결과만 처리
    public CommonResponse getSuccessResponse() {
        CommonResponse response = new CommonResponse();
        setSuccessResponse(response);
        return response;
    }

    // 실패 결과만 처리
    public CommonResponse getFailResponse(String msg) {
        CommonResponse response = new CommonResponse();
        setFailResponse(response, msg);
        return response;
    }
    
    // 에러 결과만 처리
    public CommonResponse getErrorResponse(String msg) {
        CommonResponse response = new CommonResponse();
        setErrorResponse(response, msg);
        return response;
    }

    // API 요청 성공 응답 모델 세팅
    private void setSuccessResponse(CommonResponse response) {
    	response.setStatus(Status.SUCCESS.type);
    	response.setMessage(Status.SUCCESS.message);
    }

    // API 요청 실패 응답 모델 세팅
    private void setFailResponse(CommonResponse response, String msg) {
    	response.setStatus(Status.FAIL.type);
    	response.setMessage(msg);
    }
    
    // API 요청 에러 응답 모델 세팅
    private void setErrorResponse(CommonResponse response, String msg) {
    	response.setStatus(Status.ERROR.type);
    	response.setMessage(msg);
    }
}
