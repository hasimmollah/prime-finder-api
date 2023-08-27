package com.nw.primefinder.security;

public interface IXssFilterService {
     String filterString(final String potentiallyDirtyParameter);
}
