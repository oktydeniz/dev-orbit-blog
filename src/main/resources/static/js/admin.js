$(window).on('load', function() {

    $('.deleteButton').click(function(e) {
        deleteItem(e);
    });

    function deleteItem(e) {
        var id = $(e.target).data('id');
        if (confirm("Are you sure you want to delete this item?")) {
        $.ajax({
             url: '/orbit/delete/' + id,
             type: 'POST',
             success: function(result) {

             },
             error: function(error) {

             }
         });
        }
    }

    $('.editButton').click(function() {
        var itemId = $(this).data('id');
        editItem(itemId);
        $('#editModal').modal('show');
    });

    $('#editForm').on('submit', function(event) {
        event.preventDefault();

        var formData = new FormData(this);
        var category = $('#categories').val()
        formData.append('category', category);
        $.ajax({
            url: '/orbit/upload',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                console.log('Success:', response);
            },
            error: function(xhr, status, error) {
                console.log('Error:', xhr.responseText);
            }
        });
    });

    /*
    $('#saveChanges-edit').click(function() {
        var content = $('#summernote').summernote('code');
        var category = $('#categories').val()
        var slug = $('#slug').val()
        var title = $('#title').val()
        var url = $('#url').val()
        var readTime = $('#readTime').val()
        var itemId = $('#article-model').data().id;
        var data = {
            content: content,
            category: category,
            slug: slug,
            title: title,
            url: url,
            readTime: parseInt(readTime)
        };
        fetch('/orbit/edit/' + itemId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
            if (response.ok) {

            } else {

                throw new Error('Server responded with a non-OK status');
            }
        })
            .then((d) => {
           console.log(d)
        })
            .catch(error => {
            console.log(error)
        });
    });
*/

    $('.close').click(function() {
        window.history.back();
    });

    $('#cancel').click(function() {
        window.history.back();
    });

    function editItem(itemId) {
        fetch('/orbit/edit/' + itemId, {
            method: 'GET',
        })
            .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Something went wrong');
        })
            .then(data => {
            $('#title').val(data.menu.title);
            $('#slug').val(data.menu.slug);
            $('#url').val(data.menu.url);
            $('#orderIndex').val(data.menu.orderIndex);

            if (data.subMenu != null && data.subMenu.length > 0) {
                console.log(data.subMenu);
            }

        })
            .catch(error => {
            console.error('Error:', error);
        });
    }

    $('#summernote').summernote({
        height: 300,
        minHeight: null,
        maxHeight: null,
        focus: true,
        toolbar: [
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['font', ['strikethrough', 'superscript', 'subscript']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']]
        ]
    });

    $('#imageInput').on('change', function(event) {
        var file = event.target.files[0];

        if (file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var img = $('<img>').attr('src', e.target.result).css({width: '300px', height: 'auto'});
                $('#imageContainer').html(img);
            };

            reader.readAsDataURL(file);
        }
    });

    /*$('#saveNew-NEW').click(function() {
        var content = $('#summernote').summernote('code');
        var category = $('#categories').val()
        var slug = $('#slug').val()
        var title = $('#title').val()
        var url = $('#url').val()
        var readTime = $('#readTime').val()

        var data = {
            content: content,
            category: category,
            slug: slug,
            title: title,
            url: url,
            readTime: parseInt(readTime)
        };

        fetch('/orbit/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
            if (response.ok) {
                window.location.href = '/orbit';

            }
            throw new Error('Something went wrong');
        })
            .then(data => {

        })
            .catch(error => {
            console.error('Error:', error);
        });
    }); */

    $('.showMessage').click(function() {
        var itemId = $(this).data('id');
        fetch('/orbit/message/' + itemId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Server responded with a status: ' + response.status);
            }
        })
            .then(data => {
            data = data.data;
            $('#name').val(data.fullName);
            $('#mail').val(data.mail);
            $('#content').val(data.content);
            $('#about').val(data.about);
            $('#editModal').modal('show');
        })
            .catch(error => {
            console.error('Error:', error);
        });
    });


    $("#closePopup").click(function(){
        $("#editModal").modal('hide');
    });
});

