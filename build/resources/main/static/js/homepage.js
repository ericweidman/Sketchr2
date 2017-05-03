$('#logout').click(function() {
    $.ajax({
        url: '/logout',
        method: "POST",
        success: function(data) {
            window.location.href = "index.html";
        },
        error: function(error) {
            console.log(error);
        }
    });
})
