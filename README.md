# TgBot_WebClient

### Config:  
Fetching config from server at `spring.config.import` property.
For actuator settings use http://localhost:8100/actuator  
For `dev` profile `/env` endpoint enabled at that list properties at http://localhost:8100/actuator/env  
For `main` it is closed.  
Properties could be changed at `spring.config.import`=https://github.com/Tutritp01/sto-configurations  
`http://localhost:8080/actuator/refresh` should refresh properties for `dev`.
