package com.mboaeat.order.domain;

import com.mboaeat.common.translation.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TranslatableString implements Translatable, ChangeAble<TranslatableString>, Serializable {

    @Column(name = "NAME_FR")
    private String french;

    @Column(name = "NAME_EN")
    private String english;

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    @Transient
    public boolean hasFrench() {
        return StringUtils.isNotBlank(french);
    }

    @Override
    @Transient
    public boolean hasEnglish() {
        return StringUtils.isNotBlank(english);
    }

    @Override
    @Transient
    public boolean isBlank() {
        return !isNotBlank();
    }

    @Override
    @Transient
    public boolean isNotBlank() {
        return hasFrench() || hasEnglish();
    }

    @Override
    public String asString(Language language) {
        return getString(language);
    }

    private String getString(Language language){
        switch (language){
            case FRENCH:
                if (hasFrench()){
                    return french;
                }
                break;
            case ENGLISH:
                if (hasEnglish()){
                    return english;
                } else if(hasFrench()){
                    return french;
                }
                break;
            default:
                return null;
        }
        return null;
    }
}
