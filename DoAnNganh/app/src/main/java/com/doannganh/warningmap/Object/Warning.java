package com.doannganh.warningmap.Object;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warning {

    private int id;
    private User uploader;
    private Address address;
    private String info;
    private LocalDateTime createdDateTime;
    private boolean active;
}
