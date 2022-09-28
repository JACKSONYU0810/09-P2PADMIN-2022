package com.jackson.seal2022.service;

import com.jackson.seal2022.model.CreditorVO;

/**
 * ClassName: SealService
 * Package: com.jackson.seal2022.service
 * Description:
 *
 * @Date: 9/14/2022 2:55 PM
 * @Author: JacksonYu
 */
public interface SealService {
    String pdfSeal(CreditorVO creditorVO);
}
