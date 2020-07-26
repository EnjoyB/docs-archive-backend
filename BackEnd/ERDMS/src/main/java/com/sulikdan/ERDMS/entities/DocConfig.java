package com.sulikdan.ERDMS.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Class DocConfig is used for storing & contains configuration used in OCR tool/framework.
 * The reason to nest all this properties inside class was to avoid sending uncountable amount of params.
 *
 * @author Daniel Šulik
 * @version 1.0
 * @since 25-Jul-20
 */
@Getter
@Setter
public class DocConfig {

    // OCR properties
    Boolean highQuality;
    Boolean multiPage;
    String lang;

    // Doc properties
    Boolean scanImmediately;

}
