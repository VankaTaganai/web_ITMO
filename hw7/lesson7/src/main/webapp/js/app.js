window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

ajaxWrapper = function (data, successResponse) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: function (response) {
            if (response["redirect"]) {
                if (response["error"]) {
                    alert("jopa")
                    $.notify(response["error"])
                }
                location.href = response["redirect"];
            } else if (response["error"]) {
                $.notify(response["error"])
            } else {
                successResponse(response);
            }
        }
    });
}