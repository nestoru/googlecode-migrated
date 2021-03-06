'''''''''''''''''''''''''''''''''''''''''''''''
'
' c:\scripts\events\sendEventErrorByEmail.vbs
'
' @Author: Nestor Urquiza
' @Created: 04/09/2011
'
'
' @Description: Alerts a Windows Admin there are errors in Event Viewer. To be used together with eventtriggers
'Use as below to report any errors from Event Viewer:
'
' eventtriggers /create /ru windowsUser /l "APPLICATION" /tr "Application Errors" /t error /tk "C:\scripts\events\sendEventErrorByEmail.vbs\"
'
'
' @Parameters
' 1. A prefix body message in case specific errors are to be sent 
' (a combination of batch and eventtriggers will do the trick)
'
'
' @Filters: I am filtering only "Application" events. Change the SQL query if you want to apply a different filter or not filter at all
' @Exclusions: To exclude literal lines add them to the file path specified by constant eventLogsExclusionsFilePath
'
'
''''''''''''''''''''''''''''''''''''''''''''''''

'Constants
strSmartHost = "krms.krco.com"
strSmartPort = 25
maxMinutes = 1
strComputer = "."
eventLogsExclusionsFilePath="eventLogsExclusions.txt"

'System config
Set wshShell = WScript.CreateObject( "WScript.Shell" )
strComputerName = wshShell.ExpandEnvironmentStrings( "%COMPUTERNAME%" )

'Parameters
Dim strBody
If (Wscript.Arguments.Count > 0) Then
  strBody = Wscript.Arguments(0)
End If


'Prepare email
Set objEmail = CreateObject("CDO.Message")
objEmail.From = "donotreply@krfs.com"
'objEmail.To = "bhubmon@krfs.com"
objEmail.To = "nurquiza@krfs.com"
objEmail.Subject = "[" & strComputerName & "] " & "Event Viewer Alert"
If (Not IsNull(strBody) And strBody <> "") Then
    objEmail.Textbody = strBody & ". "
End If


Set objWMIService = GetObject("winmgmts:" _
    & "{impersonationLevel=impersonate}//" & _
        strComputer & "\root\cimv2")
Set colLogFiles = objWMIService.ExecQuery _
    ("Select * from Win32_NTLogEvent " _
        & "Where Logfile='Application' and Type='Error'")

Dim exclusionFileExists
Set fso = CreateObject("Scripting.FileSystemObject")

exclusionFileExists=fso.FileExists(eventLogsExclusionsFilePath)
If exclusionFileExists = True Then
      Set eventLogsExclusionsFile = fso.OpenTextFile(eventLogsExclusionsFilePath)
      Dim arrFileLines()
      i = 0
      Do While not eventLogsExclusionsFile.AtEndOfStream 
        Redim Preserve arrFileLines(i)
        line =  eventLogsExclusionsFile.ReadLine()
        'Wscript.Echo "exclusion Line: " & line
        arrFileLines(i) = line
      loop    
End If

Dim logIt
For Each objLogFile in colLogFiles
    
    logIt = True
    'Check for most recent errors
    dtmInstallDate = objLogFile.TimeGenerated
    WMIDateStringToDate = CDate(Mid(dtmInstallDate, 5, 2) & "/" & _
     Mid(dtmInstallDate, 7, 2) & "/" & Left(dtmInstallDate, 4) _
         & " " & Mid (dtmInstallDate, 9, 2) & ":" & _
             Mid(dtmInstallDate, 11, 2) & ":" & Mid(dtmInstallDate, _
                 13, 2))

    deltaMinutes = DateDiff("N", WMIDateStringToDate, Now)
    'Wscript.Echo "deltaMinutes: " & deltaMinutes 
    If (deltaMinutes >= maxMinutes) Then
      logIt = False
    End If
    'Check for exclusions   
    If IsArray(arrFileLines) Then
      For Each line in arrFileLines
        If (InStr(1, objLogFile.Message,line, vbTextCompare) > 0) Then
            logIt = False
        End If
      Next
    End If
    If (logIt = True) Then
      details = WMIDateStringToDate  & " - [" & _
                 objLogFile.Type & "] " & _
                 objLogFile.Message
      'Wscript.Echo details
    End If               

Next


If (Not IsNull(details) And details <> "") Then
    objEmail.Textbody = objEmail.Textbody & details



    'Custom server
    objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/sendusing") = 2
    objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserver") = strSmartHost
    objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = strSmartPort
    objEmail.Configuration.Fields.Update

    'Send it
    objEmail.Send
End If
