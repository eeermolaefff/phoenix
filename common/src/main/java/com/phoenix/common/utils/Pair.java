package com.phoenix.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<F, S> {
    public final F first;
    public final S second;
}
