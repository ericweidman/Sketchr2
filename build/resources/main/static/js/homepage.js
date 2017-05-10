$(document).ready(function() {
  $.ajax({
    url: '/user-images',
    method: 'GET',
    dataType: 'json',
    success: function(data) {
      $('images').html('');
      data.forEach(function(element, ndx) {
        var filename = element.fileName;
        var name = element.title;
        var image = new Image();
        image.src = filename;
        image.classList.add('picture');
        images.setAttribute('data-id', element.id);
        $('#images').prepend('<div class="profilePic"><button class="delThis" onclick="deleteThis(this)" data-id="' + element.id + '">delete</button></div>');
        $('#images').prepend(image);
        $('#images').prepend(name);
      })
    }
  })
})

function deleteImg(id) {
  $.ajax({
    method: 'DELETE',
    url: '/delete-image/' + id,
    success: function() {
      console.log('deleted!');
      window.location.reload();
    }
  });
}

function deleteThis(elem) {
  if (confirm('Are you sure you want to delete this image?')) {
    var id = $(elem).data('id');
    console.log('this is the id', id);
    $("*[data-id=" + id + "]").remove();
    deleteImg(id);
  } else {
    alert('image not deleted');
  }
}

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

$('#canvas').click(function() {
  window.location.href = "canvas.html";
});
