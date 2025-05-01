package com.pragma.plaza_service.application.dto.response;

import com.pragma.plaza_service.domain.model.PaginationInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfoResponse<T> {

    private PaginationInfo<T> paginationInfo;

}
