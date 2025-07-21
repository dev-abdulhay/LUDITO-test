package org.ludito.app.rest.payload.req.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserUpdate {


    private String firstName;

    private String lastName;

    private String phone;


}
