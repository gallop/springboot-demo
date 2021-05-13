package com.gallop.apidoc.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    int pageNumber;

    int pageSize;

    int totalPage;

    long totalSize;

    List<T> list;

}
