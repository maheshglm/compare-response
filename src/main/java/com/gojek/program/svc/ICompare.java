package com.gojek.program.svc;

import com.gojek.program.mdl.ResponseSpec;

public interface ICompare {
    boolean compare(final ResponseSpec response1, final ResponseSpec response2);
}
