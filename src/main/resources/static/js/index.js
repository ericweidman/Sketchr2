$('#createUser').submit(function(event) {
  event.preventDefault();
  var user = {};
  user.userName = $('input[name=newUser]').val();
  user.passwordHash = $('input[name=newPassword]').val();
  $.ajax({
    url: '/create-user',
    method: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(user),
    dataType: 'text',
    success: function(data) {
      console.log(data);
      window.location.href = "homepage.html";
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

$('#loginUser').submit(function(event) {
  event.preventDefault();
  var user = {};
  user.userName = $('input[name=loginUser]').val();
  user.passwordHash = $('input[name=loginPassword]').val();
  $.ajax({
    url: '/login',
    method: 'POST',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(user),
    dataType: 'text',
    success: function(data) {
      console.log(data);
      window.location.href = "homepage.html";
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

$('#hideCreate').click(function() {
  $('#createUser').toggle();
  $('#loginUser').toggle();
  $('#hideCreate').toggle();
  $('#alreadyhasaccount').toggle();
});

$('#alreadyhasaccount').click(function() {
  $('#createUser').toggle();
  $('#loginUser').toggle();
  $('#hideCreate').toggle();
  $('#alreadyhasaccount').toggle();
})
