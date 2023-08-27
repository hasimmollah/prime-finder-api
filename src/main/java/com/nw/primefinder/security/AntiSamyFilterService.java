package com.nw.primefinder.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AntiSamyFilterService implements IXssFilterService{
    @Autowired
    private AntiSamy antiSamy;
    @Override
    public String filterString(String potentiallyDirtyParameter) {
        if (potentiallyDirtyParameter == null) {
            return null;
        }
        try {
            final CleanResults cr = this.antiSamy.scan(potentiallyDirtyParameter);
            if (cr.getNumberOfErrors() > 0) {
                this.printDebug(cr, potentiallyDirtyParameter);
            }
            return cr.getCleanHTML();
        } catch (final Exception e) {

            log.error("Error while xss processing " + e.getMessage()); //do not write stacktrace
            return StringEscapeUtils.escapeHtml4(potentiallyDirtyParameter); // secure fallback
        }
    }
    private void printDebug(final CleanResults cr, final String potentiallyDirtyParameter) {
        final StringBuilder builder = new StringBuilder("antisamy encountered problem with input:");
        builder.append(StringEscapeUtils.escapeHtml4(potentiallyDirtyParameter));
        builder.append("\n error");
        builder.append(cr.getErrorMessages());

        log.info(builder.toString());
    }
}
