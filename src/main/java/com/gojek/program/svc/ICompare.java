package com.gojek.program.utils;

import com.gojek.program.mdl.ResponseSpec;

public interface ICompare {
    boolean compare(final ResponseSpec response1, final ResponseSpec response2);
}
