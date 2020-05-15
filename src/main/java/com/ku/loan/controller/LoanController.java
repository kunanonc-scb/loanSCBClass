package com.ku.loan.controller;

import com.ku.loan.constants.LoanError;
import com.ku.loan.constants.Response;
import com.ku.loan.exception.LoanException;
import com.ku.loan.model.LoanInfo;
import com.ku.loan.model.ResponseModel;
import com.ku.loan.model.StatusModel;
import com.ku.loan.service.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    private static final Logger log = LogManager.getLogger(LoanController.class.getName());
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/info/{id}")
    public HttpEntity<ResponseModel> getLoanInfoByCustomerId(@PathVariable Long id) throws Exception {
        log.info("Get loan info by customer Id: {}", id);
        ResponseModel responseModel = new ResponseModel();

        try {

            LoanInfo loanInfo = loanService.getLoanInfoById(id);
            StatusModel status = new StatusModel(
                    Response.SUCCESS_CODE.getContent(),
                    Response.SUCCESS.getContent()
            );
            responseModel.setStatus(status);
            responseModel.setData(loanInfo);

            return ResponseEntity.ok(responseModel);
        } catch (LoanException e) {
            log.error("Exception by Id {}", id);
            LoanError loanError = e.getLoanError();
            StatusModel status = new StatusModel(
                    loanError.getCode(),
                    loanError.getMessage()
            );
            responseModel.setStatus(status);
            responseModel.setData(loanError);

            return  ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            log.error("Exception by Id {}", id);
            LoanError loanError = LoanError.GET_LOAN_INFO_EXCEPTION;
            StatusModel status = new StatusModel(
                    loanError.getCode(),
                    loanError.getMessage()
            );
            responseModel.setStatus(status);
            responseModel.setData(loanError.getMessage());
            return responseModel.build(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
