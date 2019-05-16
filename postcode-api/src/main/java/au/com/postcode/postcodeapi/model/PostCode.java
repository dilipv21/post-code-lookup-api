package au.com.postcode.postcodeapi.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class PostCode {

    //https://en.wikipedia.org/wiki/Postcodes_in_Australia
    @NotNull
    private String postCode;

    @NotNull
    private String suburb;

    //Provide a Lookup Service
    @Pattern(regexp = "^(VIC|NSW|ACT|QLD|SA|WA|TAS|NT)$", message = "Invalid State")
    private String state;

}
