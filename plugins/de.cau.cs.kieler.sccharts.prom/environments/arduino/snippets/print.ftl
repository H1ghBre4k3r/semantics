<#-- SerialRate -->
<#-- As input or output variable, sets the rate for serial communication.
     This is done only once in the initialization.

     Example for SCCharts:
         @Wrapper SerialRate, "9600"
         output int serialRate; -->
<#macro SerialRate baud>
    <@init>
        Serial.begin(${baud});
    </@>
</#macro>

<#-- Print -->
<#-- As output variable, print the variable to the output.
         
     Example for SCCharts:
         @Wrapper Print
         output string text; -->
<#macro Print>
    <@output>
        // Print to display
        if(${varname}) {
            Serial.println(${varname});
        }
    </@>
</#macro>
