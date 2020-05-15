package com.ku.loan.service;

import com.ku.loan.constants.LoanError;
import com.ku.loan.constants.Response;
import com.ku.loan.exception.LoanException;
import com.ku.loan.model.LoanInfo;
import com.ku.loan.model.ResponseModel;
import com.ku.loan.model.StatusModel;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoanService {
    private static final Logger log = LogManager.getLogger(LoanService.class.getName());

    public LoanInfo getLoanInfoById(Long id) throws Exception {
        log.info("Get loan info by Id: {}", id);
        LoanInfo loanInfo = new LoanInfo();

        if (id.equals(1L)) {
            loanInfo.setId(1L);
            loanInfo.setStatus("OK");
            loanInfo.setAccountPayable("102-222-2200");
            loanInfo.setAccountReceivable("102-333-2020");
            loanInfo.setPrincipalAmount(3_400_000.00);

        } else if (id.equals(2L)) {
            log.info("Id: {}", id);
            throw new LoanException(
                    LoanError.GET_LOAN_INFO_NOT_FOUND,
                    HttpStatus.BAD_REQUEST);
        } else {
            log.info("Id: {}", id);
            throw new Exception("Test throw new exception");
        }

        return loanInfo;
    }
}
