$(document).ready(function() {
    $("#settingsForm").submit(function() {
        var stringHour =  $("#dayTime").val();
        var hour = parseInt(stringHour);
        if(isNaN(hour) || hour < 0 || hour > 23) {
            alert("Hour should be between 0 and 23")
            return false;
        }
        return true;
    });
});