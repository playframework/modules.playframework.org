function editAdminSection(template, successCallback, errorCallback) {
    $.prompt(template,
             { buttons: { Update: true, Cancel: false },
                 callback: function(e,buttonValue,m,form) {
                     if (buttonValue) {
                         $.post(form.targetUrl,
                                form)
                                 .success(
                                 function(data, textStatus, jqXHR) {
                                     for (var key in form) {
                                         $('#' + key + '-' + form.id).text(form[key]);
                                     }
                                     successCallback(form);
                                 })
                                 .error(
                                 function(jqXHR, textStatus, errorThrown) {
                                     var messageJson = JSON.parse(jqXHR.responseText);
                                     var message = '';
                                     for (var key in messageJson) {
                                         message += (key + ': ' + messageJson[key] + '<br>');
                                     }
                                     errorCallback(form, message);
                                 });
                     }
                 }});
}