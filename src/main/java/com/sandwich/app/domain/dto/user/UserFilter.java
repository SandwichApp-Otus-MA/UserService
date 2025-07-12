package com.sandwich.app.domain.dto.user;

import com.sandwich.app.domain.dto.pagination.AdvancedFieldFilter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserFilter {

    private AdvancedFieldFilter<UUID> id;
    private AdvancedFieldFilter<String> lastName;
    private AdvancedFieldFilter<String> firstName;
    private AdvancedFieldFilter<String> middleName;
    private AdvancedFieldFilter<LocalDate> birthDate;
    private AdvancedFieldFilter<String> email;
    private AdvancedFieldFilter<String> phoneNumber;

}
