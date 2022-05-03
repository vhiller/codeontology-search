package com.codeontologysearch.codeontologysearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    String subject;
    String label;
    String supertype;
}
