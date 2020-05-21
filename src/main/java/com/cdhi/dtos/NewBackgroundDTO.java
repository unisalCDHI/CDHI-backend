package com.cdhi.dtos;

import com.cdhi.domain.enums.Background;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBackgroundDTO {
    Background background;
}
