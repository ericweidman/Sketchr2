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


$('#save').click(function(event) {
  event.preventDefault();
  var title = $('input[class="nameImg"]').val();
  var drawing = canvas.toDataURL();
  var base64result = drawing.split(',')[1];
  $.ajax({
    url: '/save-image',
    method: 'POST',
    data: {
      title,
      base64result
    },
    error: function(error) {
      console.log(error.responseText);
    },
    statusCode: {
      500: function() {
        console.log("Internal Server Error")
      },
    }
  })
})
