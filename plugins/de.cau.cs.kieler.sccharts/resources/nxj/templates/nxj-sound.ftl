<#-- Beep -->
<#-- As output variable, plays a warning beep as long as the variable is true.

     Example for SCCharts:       
         output bool 
         @macro "Beep" warningBeep
     Optional:
         int: volume (10)
-->
<#macro Beep position>
<#if position=="output">
<#list parameters["Beep"] as parameters>
        // Play beep sound
        if(scchart.${parameters.varName}) {
            int lastVolume = Sound.getVolume();
            Sound.setVolume(${parameters.parameter1!10});
            Sound.beep();
            Sound.setVolume(lastVolume);
        }
</#list>        
</#if>
</#macro>

<#-- Buzz -->
<#-- As output variable, plays a low buzz sound as long as the variable is true.

     Example for SCCharts:
         ouput bool 
         @macro "Beep" warningBeep
     Optional:
         int: volume (10)
-->
<#macro Buzz position>
<#if position=="output">
<#list parameters["Buzz"] as parameters>
        // Play buzz sound
        if(scchart.${parameters.varName}) {
            int lastVolume = Sound.getVolume();
            Sound.setVolume(${parameters.parameter1!10});
            Sound.buzz();
            Sound.setVolume(lastVolume);
        }
</#list>        
</#if>
</#macro>

<#-- BeepSequence -->
<#-- As output variable, plays a beep sequence if the variable is true and afterwards sets the variable to false.
     The direction of the beep sequence can either be up or down.
     
     Example for SCCharts:
         ouput bool 
         @macro "BeepSequence", "up" playBeepSequence
     Optional:
         int: volume (10)
-->
<#macro BeepSequence position>
<#if position=="output">
<#list parameters["Buzz"] as parameters>
        // Play sequence of beep tones
        if(scchart.${parameters.varName}) {
            scchart.${parameters.varName} = false;
            int lastVolume = Sound.getVolume();
            Sound.setVolume(${parameters.parameter2!10});
            <#if (parameters.parameter1!"up")=="up">
            Sound.beepSequenceUp();
            <#else>
            Sound.beepSequence();
            </#if>
            Sound.setVolume(lastVolume);
        }
</#list>        
</#if>
</#macro>
